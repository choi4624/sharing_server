package server.image.image.Repository;


import org.springframework.data.jpa.repository.JpaRepository ;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import server.image.image.Model.URI;


import java.util.Optional;

@Repository
public interface URIrepo extends JpaRepository <URI, Long> {
    @Query(value = "Select uid, filename, reg_date FROM URI WHERE UID = ?1", nativeQuery = true)
    Optional<URI> findByUid(@Param("_uid") Long uid);

    @Query(value = "select uid from URI", nativeQuery = true)
    public Optional<URI> showTables();

    @Query(value = "select uid, filename, reg_date from URI", nativeQuery = true)
    public Optional<URI> selectAllData();


    @Query(value = "Select uid, filename, reg_date FROM URI WHERE UID = 100000", nativeQuery = true)
    Optional<URI> basicSelect();
}
