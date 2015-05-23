

package hibernate.logic;

import java.util.GregorianCalendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name="photolikes")
public class PhotoLike {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name="id", unique = true, nullable = false)
    private Long id;
    @Column(name="date")
    private Long date;
    @Column(name="photo_id")
    private Long photo_id;
    @Column(name="user_from")
    private String user_from;
    @Column(name="deleted")
    private boolean deleted;
    
    public PhotoLike(){}
    
    public PhotoLike(Long id, Long date, Long photo_id, String user_from){
      this.id = id;
      this.date = date;
      this.photo_id = photo_id;
      this.user_from = user_from;
      this.deleted = false;
    }  
    
    public PhotoLike(Long id, Long photo_id, String user_from){
      GregorianCalendar c = new GregorianCalendar();
      this.id = id;
      this.date = c.getTimeInMillis();
      this.photo_id = photo_id;
      this.user_from = user_from;
      this.deleted = false;
    }  
    
    
    public void setId(Long id){
        this.id = id;
    }
    
    public Long getId(){
        return this.id;
    }
    
     public void setDate(Long date){
        this.date = date;
    }
    
    public Long getDate(){
        return this.date;
    }
    
    public void setPhotoId(Long photo){
        this.photo_id = photo;
    }
    
    public Long getPhotoId(){
        return this.photo_id;
    }    
    
   public void setUserFrom(String user){
        this.user_from = user;
    }
    
    public String getUserFrom(){
        return this.user_from;
    }
    
    public void setDeleted(boolean deleted){
        this.deleted = deleted;
    }
    
    public boolean getDeleted(){
        return this.deleted;
    }
    
    @Override
    public boolean equals(Object obj){
        if (obj instanceof PhotoLike){
            PhotoLike other = (PhotoLike)obj;
            return ((this.photo_id.equals(other.photo_id)) && (this.date.equals(other.date)) 
                    && (this.id.equals(other.id)) && (this.user_from.equals(other.user_from))
                     && (this.deleted == other.deleted));
        }
        
        return false;
    }
}
