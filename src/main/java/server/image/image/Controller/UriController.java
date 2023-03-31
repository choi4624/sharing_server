package server.image.image.Controller;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;

import server.image.image.Model.URI;
import server.image.image.Repository.URIrepo;


@RestController
@RequestMapping(path="") 
public class UriController{
  @Autowired
  private URIrepo URIrepo;

  @GetMapping("/view")
  public ResponseEntity<List<URI>> imageDB(@RequestParam(required = false) String title){
    try{
      Optional<URI> uriList;

      if(title == null)
        {
          //URIrepo.showTables();
        uriList = URIrepo.selectAllData();}
      else{
        //URIrepo.showTables();
        uriList = URIrepo.selectAllData();
      }
    
        if(uriList.isEmpty()){
          return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.OK);
      }
    catch(Exception e){
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
      }

  }

  @GetMapping(value = "/viewsql/{uid}")
  public Optional<URI> imageSql(@PathVariable("uid") Long uid){
    Optional<URI> uriData = URIrepo.findByUid(uid);

    if(uriData.isPresent()){
      return URIrepo.findByUid(uid);
    }
    else{
      return Optional.empty();
    }
  }

  @GetMapping(value = "/basic")
  public Optional<URI> basicSql(){
    return URIrepo.basicSelect();
  }

  @GetMapping(value = "/json", produces = MediaType.APPLICATION_JSON_VALUE)
  public Object jsonReturn(){
        Resource resource = new ClassPathResource("/static/json/webjson.json");
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(resource.getInputStream(), Object.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


  @PostMapping(value = "/test/image/upload")
  public String fileUpload(@RequestParam("file") MultipartFile file) {
    
    
    return "/image/upload";
    
  }


  @CrossOrigin
  @PostMapping(value = "/image/upload")
  public ResponseEntity<?> uploadImage ( @RequestParam MultipartFile filename ){
    
    Date date = new Date();
    StringBuilder sb = new StringBuilder();

    if (filename.isEmpty()) {
      sb.append("none");
    } else {
      sb.append(date.getTime());
      sb.append(filename.getOriginalFilename());
    }

    if (!filename.isEmpty()) {
      File dest = new File("" + sb.toString());
      try {
        filename.transferTo(dest);
      } catch (IllegalStateException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      }
    
    return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);  
    }
    return new ResponseEntity<String>("ERROR in upload", HttpStatus.INTERNAL_SERVER_ERROR);

  }
  
  @GetMapping(value = "view/{filename}.{extension}", produces = MediaType.IMAGE_PNG_VALUE)
	public ResponseEntity<byte[]> viewEntity(@PathVariable("filename") String filename, @PathVariable("extension") String extension) throws IOException {
		

    InputStream imageStream = new FileInputStream("./image/Storage/webupload/" + filename + "." + extension);
    byte[] imageByteArray = imageStream.readAllBytes();
		imageStream.close();
		return new ResponseEntity<byte[]>(imageByteArray, HttpStatus.OK);
	}
  /*
  @PostMapping("/view/{uid}")

  public URI getImageByUid(@PathVariable("uid") Long uid){
    final URI uri = URI.builder()
  }
   */ 

}