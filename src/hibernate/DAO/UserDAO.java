package hibernate.DAO;


import hibernate.logic.SexEnum;
import hibernate.logic.User;
import java.sql.SQLException;
import java.util.List;
         
         
public interface UserDAO {
    public void addUser(User user) throws SQLException;
    public void updateUser(User user) throws SQLException;
    public User getUser(String login) throws SQLException;
    public void setUserAvatarByPhotoDescriptionId(User user, long photoId) throws SQLException;
    public void setUserAvatarByPhotoDescriptionId(String user, long photoId) throws SQLException;
    public void setUserLastOnlineTime(User user, long time) throws SQLException;
    public void setUserLastOnlineTime(String user, long time) throws SQLException;
    public List<User> getAllUsers() throws SQLException;
    public List<User> getUsersBySearchAfterLogin(String search, SexEnum sex, int minAge, int maxAge, String city, String login, long count) throws SQLException;
    public List<User> getUsersBySearchAfterLogin(String search, String login, long count) throws SQLException;
    public List<User> getUsersBySearchBeforeLogin(String search, String login, long count) throws SQLException;
    public List<User> getUsersAfterLogin(String login, long count) throws SQLException; 
    public void deleteUserByLogin(String login) throws SQLException; 
    
}
