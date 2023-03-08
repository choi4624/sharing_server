package server.image.image.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.persistence.Id;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import server.image.image.Model.URI;
import server.image.image.Repository.URIrepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class UriController{
  @Autowired
  private URIrepo URIrepo;

  @GetMapping("/view")
  public ResponseEntity<List<URI>> imageDB(@RequestParam(required = false) String title){
    try{
      List<URI> uriList = new ArrayList<URI>();

      if(title == null)
        URIrepo.findAll().forEach(uriList::add);
      else
        URIrepo.findAll().forEach(uriList::add);

      if(uriList.isEmpty()){
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
      return new ResponseEntity<>(uriList, HttpStatus.OK);
    }
    catch(Exception e){
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

  }

  @GetMapping("/view/{uid}")
  public ResponseEntity<URI> imageViewUri(@PathVariable("uid") Integer uid){
    Optional<URI> uriData = URIrepo.findByuid(uid);
    
    if(uriData.isPresent()){
      return new ResponseEntity<>(uriData.get(),HttpStatus.OK);
    }
    else{
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @GetMapping(value = "/viewsql/{uid}",  produces = { MediaType.APPLICATION_JSON_VALUE })
  public Optional<URI> imageSql(@PathVariable("uid") Integer uid){
    return URIrepo.findByuid(uid);
  }
  /*
  @PostMapping("/view/{uid}")
  public URI getImageByUid(@PathVariable("uid") Long uid){
    final URI uri = URI.builder()
  }
   */
}