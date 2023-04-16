package server.image.image.Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;

import server.image.image.Model.URI;
import server.image.image.Repository.URIrepo;

/*
 * 웰컴 
 * 이 주석을 보는 자 희망을 버려라 
 * 
 * 스프링 모르는 사람을 위한 주석 
 * 
 * @RestController < 기본적으로 response를 json 방식으로 던져주는 컨트롤러에요, 여기선 그렇게 씀 
 * @Controller < 여기선 html을 리턴하거나 뭐 다른 타입 등등을 리턴하는 컨트롤러에요. 
 * 
 * 근데 이건 일종의 api 서버라서 uriController의 이미지 부분만 잘 보시면 될듯 
 * 
 */

@RestController
@RequestMapping(path = "image") // 여기 아래 서비스들은 전부 root/image/<인자> 이렇게 써야 해요
public class UriController {
  @Autowired // Autowired는 뭔지는 설명은 어려운데 그냥 uri database랑 잘 연결하는 그런거
  private URIrepo URIrepo;
  // repository에 달려 있는 jparepo 의 기능 (intelisense 쓰면 나오는 메소드들 ) + 맘대로 작성한 네이티브 쿼리를
  // 쓸 수 있게 해줘요

  @GetMapping("/view") // 실제로 안씀
  public ResponseEntity<List<URI>> imageDB(@RequestParam(required = false) String title) {
    try {
      Optional<URI> uriList;

      if (title == null) {
        // URIrepo.showTables();
        uriList = URIrepo.selectAllData();
      } else {
        // URIrepo.showTables();
        uriList = URIrepo.selectAllData();
      }

      if (uriList.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

  }

  @GetMapping(value = "/sql/view/{uid}") // 이미지의 uid를 던지면 해당 uid의 모든 정보가 나와요
  public Optional<URI> imageSql(@PathVariable("uid") Long uid) {
    /*
     * PathVariable을 쓰시면 "" 안에 있는게 {} 안에 있는거랑 일치하는걸 가져와서 뒤에 적은 (long) 타입으로 변수를 만들어요
     */
    Optional<URI> uriData = URIrepo.findByUid(uid); // jpa repo에 있는 findByUid 메소드를 써서 return
    // uriData에 정확히 어떤게 들어가 있는지를 볼려면 <URI> 에 있는 정보를 알아야 하는데 Model/URI.java를 참고해주세요
    // getter setter는 검색...

    // 그 uid 있으면 json 방식으로 데이터 던짐 없으면 empty
    if (uriData.isPresent()) {
      return URIrepo.findByUid(uid);
    } else {
      return Optional.empty();
    }
  }

  // native sql 구문 테스트 용으로 만듬 안씀
  @GetMapping(value = "/basic")
  public Optional<URI> basicSql() {
    return URIrepo.basicSelect();
  }
  // 안쓰는 애들은 서버에 실제로 보내대가 먼일이 생길지 몰라요

  // 테스트 json 파일 던지기, 스프링 리소스에 저장되어 있음
  @GetMapping(value = "/json", produces = MediaType.APPLICATION_JSON_VALUE)
  public Object jsonReturn() {
    Resource resource = new ClassPathResource("/static/json/webjson.json");
    try {
      ObjectMapper mapper = new ObjectMapper();
      return mapper.readValue(resource.getInputStream(), Object.class);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  // filepath 와 관련된 부분은 application.properties를 참고해야 해요
  // 윈도우용 경로가 아니라 리눅스용 경로로 되어 있으니 제대로 이미지 안나감, 업로드는 임시 폴더로 대충 됨
  @Value("${spring.servlet.multipart.location}")
  String filePath;

  // image/upload >> html 기준으로 submit이 되면 아래 작업을 수행함
  /* 
   * 
   */
  @PostMapping(value = "/upload")
  public ResponseEntity<?> uploadImage(@RequestParam("filename") List<MultipartFile> fList,
      @RequestParam(value = "content", required = false) String contentString,
      @RequestParam(value = "user", required = false, defaultValue = "i4624") String userString) {
    // post 이기 때문에 content 필드랑 user 필드를 대충 uri에 명시하지 않고 넣을 수 있어요.
    // multipartfile을 여러개 한번에 받을 수 있게 되어있습니다.

    SimpleDateFormat SimpleDateFormat = new SimpleDateFormat("yyyyMMdd"); // date 부분을 db에 넣기 위해 입력, 자바의 기본 date 메소드로 날짜만
                                                                          // 가져올 것임
    String current_date = SimpleDateFormat.format(new Date());
    boolean SUCCESS = true; // 기본적으로 true인데, 실패하면 false 되어서 http 500 인터널 에러를 리턴
    System.out.println("simple"); // debug console

    /*
     * for each를 사용해 fList를 하나씩 MultipartFile 속성의 filename으로 넣습니다.
     * 그 파일네임 안에는 파일네임이랑 바이너리 데이터가 있어서 업로드
     * 혹시 몰라 확장자 정보 db에 넣고 그 정보도 긁어오기 함
     * 개수 제한은 따로 없음.
     * 
     */
    for (MultipartFile filename : fList) {
      System.out.println("multiparts upload");
      StringBuilder sb = new StringBuilder(); // 스트링 빌더를 쓰면 된다는데 정확히 뭔지는 모름

      String extentionString = getExtension(filename);

      // 빈 파일이름으로 업로드 한 경우
      if (filename.isEmpty()) {
        sb.append(current_date);
        sb.append("_" + new Date());
      } else {
        // sb.append(current_date); //안씀 현재 시간을 읽는다는게 64bit 형 시간 단위를 가져오는 것 같음
        sb.append(filename.getOriginalFilename());
      }

      if (!filename.isEmpty()) {
        File dest = new File("" + sb.toString()); // 경로와 관련된 부분은 application.properties 을 참고하시오
        try {
          filename.transferTo(dest); // multipartfile을 자바 파일로 한 다음 바이너리 업로드 스프링의 업로드 메소드일거임 아마
        } catch (IllegalStateException e) {
          e.printStackTrace();
          SUCCESS = false;
          System.out.println("err in state");
        } catch (IOException e) {
          e.printStackTrace();
          SUCCESS = false;
          System.out.println("err in IO");
        }
      }

      // database에 등록
      if (!filename.isEmpty()) {
        System.out.println("uri try");
        URI uri = new URI();
        URIrepo.save(uri);
        uri.setImage_uid(uri.getImage_uid());
        uri.setFile(sb.toString());
        uri.setDate(current_date);
        uri.setContentType(extentionString);
        uri.setUserNickName(userString);
        URIrepo.save(uri); // uid가 null 이면 새로 인서트함, uid 지정되어 있으면 update함 uri에 넘겨진건 null도 아니고 비어 있으면 오류날 수
                           // 있음.

      }

    }

    if (SUCCESS) {
      return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
    } else {
      return new ResponseEntity<String>("ERROR in upload", HttpStatus.INTERNAL_SERVER_ERROR);
    }

  }

  // 확장자 가져가는 부분만 메소드 분리
  public String getExtension(MultipartFile multipartFile) {
    String extension = StringUtils.getFilenameExtension(multipartFile.getOriginalFilename());
    return extension;
  }

  // 이미지 보는거 raw version
  @GetMapping(value = "/view/{filename}.{extension}", produces = MediaType.IMAGE_PNG_VALUE)
  public ResponseEntity<byte[]> viewEntity(@PathVariable("filename") String filename,
      @PathVariable("extension") String extension) throws IOException {

    // 이미지를 적당히 가져가서 보여주는 기능 아래의 uid 기반으로도 가져갈때 사용함
    InputStream imageStream = new FileInputStream(filePath + "/" + filename + "." + extension);
    byte[] imageByteArray = imageStream.readAllBytes(); // 스트림 기반으로 바이트를 읽어서 전송합니다.
    imageStream.close(); // 스트림은 열면 닫아야 함
    return new ResponseEntity<byte[]>(imageByteArray, HttpStatus.OK);
  }

  @GetMapping(value = "/imageid/{uid}", produces = MediaType.IMAGE_PNG_VALUE)
  public ResponseEntity<?> viewEntity(@PathVariable("uid") Long uid) throws IOException {

    // jpa repo의 기본 메소드를 통해 uri의 db정보를 가져온 다음, 위의 raw version과 똑같이 작동
    Optional<URI> uriData = URIrepo.findById(uid);

    // get get get
    String filename = uriData.get().getfile().toString();
    String extension = uriData.get().getContentType().toString();

    try {
      System.out
          .println("file name is " + filename + "file extension is " + extension + " \n !!sql successfully loaded!!");
    } catch (Exception e) {
      return new ResponseEntity<String>(" sql viewing in error, may be no data", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // raw version 참고, 대신 파일이름이 확장자에 다 들어가 있어서 그 부분은 뗌
    InputStream imageStream = new FileInputStream(filePath + "/" + filename);
    byte[] imageByteArray = imageStream.readAllBytes();
    imageStream.close();
    return new ResponseEntity<byte[]>(imageByteArray, HttpStatus.OK);
  }

  // json 방식으로 정보 리턴하기
  @GetMapping(value = "/sql/recent/{username}/{count}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Object uploadList(@PathVariable("count") Integer count, @PathVariable("username") String usernameString) {

    // System.out.println(usernameString);
    // System.out.println(count);

    List<?> uriData = URIrepo.recentUpload(usernameString, count); // repository에 명시된 sql 실행

    // 대충 만들어진 리스트 형태로 전송인데 기본적으로 json 포맷에 맞춰서 전송할 것임 @Restcontroller 이니까
    return uriData;

  }

}