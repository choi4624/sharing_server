package server.image.image.Repository;


import org.springframework.data.jpa.repository.JpaRepository ;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

import server.image.image.Model.URI;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface URIrepo extends JpaRepository <URI, Long> {

    Optional<URI> findByuid(Integer uid);
}
