

package hibernate.logic;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name="photocomments")
public class PhotoComment {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name="id", unique = true, nullable = false)
    private Long id;
    @Column(name="photo_id")
    private Long photo_id;
    @Column(name="date")
    private Long date;
    @Column(name="user_from")
    private String user_from;
    @Column(name="value")
    private String value;
    @Column(name="deleted")
    private boolean deleted;
    
    
    public PhotoComment(){}
    
    public PhotoComment(Long id, Long photo_id,Long date, String user_from, String value){
      this.id = id;
      this.photo_id = photo_id;
      this.date = date;
      this.user_from = user_from;
      this.value = value;
      this.deleted = false;
    } 
    
    public void setId(Long id){
        this.id = id;
    }
    
    public Long getId(){
        return this.id;
    }
   
    
    public void setPhotoId(Long photo){
        this.photo_id = photo;
    }
    
    public Long getPhotoId(){
        return this.photo_id;
    }    
    
    public void setDate(Long date){
       
        this.date = date;
    }
    
    public Long getDate(){
        return this.date;
    }
    
   public void setUserFrom(String id){
        this.user_from = id;
    }
    
    public String getUserFrom(){
        return this.user_from;
    }
    
    public void setValue(String value){
        this.value = value;
    }
    
    public String getValue(){
        return this.value;
    }
    
    public void setDeleted(boolean deleted){
        this.deleted = deleted;
    }
    
    public boolean getDeleted(){
        return this.deleted;
    }
    
   @Override
    public boolean equals(Object obj){
        if (obj instanceof PhotoComment){
            PhotoComment other = (PhotoComment)obj;
            return ((this.photo_id.equals(other.photo_id)) && (this.id.equals(other.id))
                    && (this.user_from.equals(other.user_from)) && (this.date.equals(other.date))
                    && (this.value.equals(other.value)) && (this.deleted == other.deleted));
        }
        
        return false;
    }
}
