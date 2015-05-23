package hibernate.DAO;


import hibernate.logic.PhotoComment;
import hibernate.logic.User;
import java.sql.SQLException;
import java.util.List;
         
         
public interface PhotoCommentDAO {
    public void addPhotoComment(PhotoComment photoComment) throws SQLException;
    public void updatePhotoComment(PhotoComment photoComment) throws SQLException;
    public PhotoComment getPhotoCommentById(Long id) throws SQLException;
    public PhotoComment getLastPhotoComment(String user_from) throws SQLException;
    public List<PhotoComment> getAllPhotoComments() throws SQLException;
    public List<PhotoComment> getAllPhotoComments(User user_from, boolean withDeleted) throws SQLException;
    public List<PhotoComment> getAllPhotoComments(String user_from, boolean withDeleted) throws SQLException;
    public List<PhotoComment> getAllPhotoComments(Long photo_id, boolean withDeleted) throws SQLException;
    public List<PhotoComment> getPhotoCommentsBeforeId(long photo_id, long comment_id, long count, boolean withDeleted) throws SQLException;
    public void deletePhotoCommentById(Long id) throws SQLException; 
    public void setDeletedPhotoCommentById(Long id) throws SQLException; 
    public void deleteAllPhotoComments(User user) throws SQLException;
    public void deleteAllPhotoComments(String user) throws SQLException;
    public void deleteAllPhotoComments(Long photo_id) throws SQLException;
    public void setDeletedAllPhotoComments(User user) throws SQLException;
    public void setDeletedAllPhotoComments(String user) throws SQLException;
    public void setDeletedAllPhotoComments(Long photo_id) throws SQLException;
    public void deleteAllPhotoComments() throws SQLException;
}
