package server.image.image.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

@Entity
@Table(name = "uri")
public class URI {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer imageUid;

    public Integer getImage_uid() {
        return imageUid;
    }

    public void setImage_uid(Integer imageUid) {
        this.imageUid = imageUid;
    }

    private String filename;

    private String reg_date;

    private String contentType;

    private String userNickName;

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getfile() {
        return filename;
    }

    public void setFile(String nameString) {
        this.filename = nameString;
    }

    public String getDate() {
        return reg_date;
    }

    public void setDate(String dateString) {
        this.reg_date = dateString;
    }

}
