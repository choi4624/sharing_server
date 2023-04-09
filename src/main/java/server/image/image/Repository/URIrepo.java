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
    @Query(value = "Select Image_uid, filename, reg_date, contentType FROM URI WHERE Image_uid = ?1", nativeQuery = true)
    Optional<URI> findByUid(@Param("_Image_uid") Long uid);

    @Query(value = "select Image_uid from URI", nativeQuery = true)
    public Optional<URI> showTables();

    @Query(value = "select Image_uid, filename, reg_date, contentType from URI", nativeQuery = true)
    public Optional<URI> selectAllData();


    @Query(value = "Select Image_uid, filename, reg_date, contentType FROM URI WHERE Image_uid = 100000", nativeQuery = true)
    Optional<URI> basicSelect();
}
