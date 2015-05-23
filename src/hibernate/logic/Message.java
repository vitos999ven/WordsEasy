package hibernate.logic;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Витос
 */
@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;
    @Column(name = "date")
    private Long date;
    @Column(name = "from_id")
    private String from_id;
    @Column(name = "to_id")
    private String to_id;
    @Column(name = "value")
    private String value;
    @Column(name = "user")
    private String user;
    @Column(name = "unread")
    private Boolean unread;
    @Column(name = "deleted")
    private boolean deleted;

    public Message() {
    }

    public Message(Long id, Long date, String from_id, String to_id, String value, String user) {
        this.id = id;
        this.date = date;
        this.from_id = from_id;
        this.to_id = to_id;
        this.value = value;
        this.user = user;
        this.unread = true;
        this.deleted = false;
    }

    public Message(Long id, Long date, String from_id, String to_id, String value, String user, Boolean unread) {
        this.id = id;
        this.date = date;
        this.from_id = from_id;
        this.to_id = to_id;
        this.value = value;
        this.user = user;
        this.unread = unread;
        this.deleted = false;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public Long getDate() {
        return this.date;
    }

    public void setFrom_id(String id) {
        this.from_id = id;
    }

    public String getFrom_id() {
        return this.from_id;
    }

    public void setTo_id(String id) {
        this.to_id = id;
    }

    public String getTo_id() {
        return this.to_id;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUser() {
        return this.user;
    }

    public void setUnread(Boolean unread) {
        this.unread = unread;
    }

    public Boolean getUnread() {
        return this.unread;
    }

    public String getOtherUser() {
        return (to_id.equals(user)) ? from_id : to_id;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean getDeleted() {
        return this.deleted;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Message) {
            Message other = (Message) obj;
            return ((this.id.equals(other.id)) && (this.date.equals(other.date))
                    && (this.from_id.equals(other.from_id)) && (this.to_id.equals(other.to_id))
                    && (this.value.equals(other.value)) && (this.user.equals(other.user))
                    && (this.unread.equals(other.unread)) && (this.deleted == other.deleted));
        }

        return false;
    }

}
