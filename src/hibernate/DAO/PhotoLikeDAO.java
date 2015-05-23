package hibernate.DAO;


import hibernate.logic.PhotoLike;
import hibernate.logic.User;
import java.sql.SQLException;
import java.util.List;
         
         
public interface PhotoLikeDAO {
    public void addPhotoLike(PhotoLike photoLike) throws SQLException;
    public void updatePhotoLike(PhotoLike photoLike) throws SQLException;
    public PhotoLike getPhotoLikeById(Long id) throws SQLException;
    public PhotoLike getPhotoLike(Long photo_id, User user_from) throws SQLException;
    public PhotoLike getPhotoLike(Long photo_id, String userLogin_from) throws SQLException;
    public List<PhotoLike> getAllPhotoLikes() throws SQLException;
    public List<PhotoLike> getAllPhotoLikes(User user, boolean withDeleted) throws SQLException;
    public List<PhotoLike> getAllPhotoLikes(String user, boolean withDeleted) throws SQLException;
    public List<PhotoLike> getAllPhotoLikes(Long photo_id, boolean withDeleted) throws SQLException;
    public List<PhotoLike> getAllPhotoLikesAfterLogin(Long photo_id, String login, long count, boolean withDeleted) throws SQLException;
    public List<PhotoLike> getAllPhotoLikesBeforeLogin(Long photo_id, String login, long count, boolean withDeleted) throws SQLException;
    public void setDeletedPhotoLikeById(Long id) throws SQLException; 
    public void setDeletedPhotoLike(Long photo_id, String user_from) throws SQLException;
    public void setDeletedAllPhotoLikes(User user) throws SQLException;
    public void setDeletedAllPhotoLikes(String user) throws SQLException;
    public void setDeletedAllPhotoLikes(Long photo_id) throws SQLException;
    public void deletePhotoLikeById(Long id) throws SQLException; 
    public void deletePhotoLike(Long photo_id, User user_from) throws SQLException;
    public void deletePhotoLike(Long photo_id, String user_from) throws SQLException;
    public void deleteAllPhotoLikes(User user) throws SQLException;
    public void deleteAllPhotoLikes(String user) throws SQLException;
    public void deleteAllPhotoLikes(Long photo_id) throws SQLException;
    public void deleteAllPhotoLikes() throws SQLException;
   
}
