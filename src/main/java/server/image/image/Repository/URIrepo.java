package server.image.image.Repository;


import org.springframework.data.jpa.repository.JpaRepository ;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

import server.image.image.Model.URI;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface URIrepo extends JpaRepository <URI, Long> {
    @Query(value = "Select uid, filename, reg_date from URI where uid in uid", nativeQuery = true)
    Optional<URI> findByUid(Long uid);

    @Query(value = "select uid from URI", nativeQuery = true)
    public Optional<URI> showTables();


    @Query(value = "select uid, filename, reg_date from URI", nativeQuery = true)
    public Optional<URI> selectAllData();

    Optional<URI> findByUid(Integer uid);

    @Query(value = "Select filename, UID, reg_date FROM URI WHERE UID = 100000", nativeQuery = true)
    Optional<URI> basicSelect();
}
