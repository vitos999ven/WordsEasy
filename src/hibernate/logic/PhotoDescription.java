

package hibernate.logic;

import java.util.GregorianCalendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;




@Entity
@Table(name="photodescriptions")
public class PhotoDescription {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name="id", unique = true, nullable = false)
    private Long id;
    @Column(name="date")
    private Long date;
    @Column(name="user")
    private String user;
    @Column(name="description")
    private String description;
    @Column(name="deleted")
    private boolean deleted;
    
    
    public PhotoDescription(){}
    
    public PhotoDescription(Long id, Long date, String user, String description){
      this.id = id;
      this.date = date;
      this.user = user;
      this.description = description;
      this.deleted = false;
    }
    
    public PhotoDescription(Long id, String user, String description){
      GregorianCalendar c = new GregorianCalendar();
      this.id = id;
      this.date = c.getTimeInMillis();
      this.user = user;
      this.description = description;
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
    
    public void setUser(String id){
        this.user = id;
    }
    
    public String getUser(){
        return this.user;
    }
        
    
    public void setDescription(String description){
        this.description = description;
    }
    
    public String getDescription(){
        return this.description;
    }
    
    public void setDeleted(boolean deleted){
        this.deleted = deleted;
    }
    
    public boolean getDeleted(){
        return this.deleted;
    }
    
    @Override
    public boolean equals(Object obj){
        if (obj instanceof PhotoDescription){
            PhotoDescription other = (PhotoDescription)obj;
            return ((this.description.equals(other.description))&& (this.date.equals(other.date)) && (this.id.equals(other.id)) 
                    && (this.user.equals(other.user)) && (this.deleted == other.deleted));
        }
        
        return false;
    }
}
