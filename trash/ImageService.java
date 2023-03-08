package Service;


import server.image.image.Model.URI;
import server.image.image.Repository.URIrepo;
import org.springframework.stereotype.Service;


public class ImageService {
    private final URIrepo uriRepo;

    public URIrepo uri(final Integer uid){
        this.uid = uid;
    }

    public URI view(Long uid){
        return uriRepo.findById(uid).get();
    }
}
