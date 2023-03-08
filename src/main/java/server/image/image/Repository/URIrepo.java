package server.image.image.Repository;


import org.springframework.data.jpa.repository.JpaRepository ;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

import server.image.image.Model.URI;


public interface URIrepo extends JpaRepository <URI, Long> {
    
}
