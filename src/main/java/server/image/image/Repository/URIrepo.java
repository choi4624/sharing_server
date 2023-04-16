package server.image.image.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import server.image.image.Model.URI;

import java.util.List;
import java.util.Optional;

/*
 *  private String filename; 

    private String reg_date;

    private String contentType; ( 이건 content_Type으로 sql에 넣어야함 )

    private String userNickName; ( 이건 user_nick_name으로 sql에 넣어야함 )
 */
@Repository
public interface URIrepo extends JpaRepository<URI, Long> {
    @Query(value = "Select Image_uid, filename, reg_date, content_type FROM URI WHERE Image_uid = ?1", nativeQuery = true)
    Optional<URI> findByUid(@Param("_Image_uid") Long uid);

    @Query(value = "select Image_uid from URI", nativeQuery = true)
    public Optional<URI> showTables();

    @Query(value = "select Image_uid, filename, reg_date, content_type from URI", nativeQuery = true)
    public Optional<URI> selectAllData();

    @Query(value = "Select Image_uid, filename, reg_date, content_type FROM URI WHERE Image_uid = 100000", nativeQuery = true)
    Optional<URI> basicSelect();
    // 위 쿼리들은 초창기 쓰레기 쿼리

    // 최근 업로드된 데이터 return
    @Query(value = "Select image_uid, filename FROM uri WHERE uri.user_nick_name = ?1 order by Image_uid desc limit ?2", nativeQuery = true)
    List<?> recentUpload(String usernameString, Integer count);

}
