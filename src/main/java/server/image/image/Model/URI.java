package server.image.image.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class URI {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    
    private Integer uid;

    private String filename;
    
    private String regdate;
    
    public Integer getUIDInteger(Integer uidInteger){
        return uid;
    }

    public void setUID(Integer uriString){
        this.uid = uriString;
    }

    public String getfile(){
        return filename;
    }

    public void setFile(String nameString){
        this.filename = nameString;
    }

    public String getDate(){
        return regdate;
    }

    public void setDate(String dateString){
        this.regdate = dateString;
    }
}
