package hibernate.DAO;


import hibernate.logic.PhotoDescription;
import hibernate.logic.User;
import java.sql.SQLException;
import java.util.List;
         
         
public interface PhotoDescriptionDAO {
    public void addPhotoDescription(PhotoDescription photoDescription) throws SQLException;
    public void updatePhotoDescription(PhotoDescription photoDescription) throws SQLException;
    public void updatePhotoDescription(Long id, String description) throws SQLException;
    public PhotoDescription getPhotoDescriptionById(Long id) throws SQLException;
    public List<PhotoDescription> getAllPhotoDescriptions() throws SQLException;
    public List<PhotoDescription> getAllPhotoDescriptions(User user, boolean withDeleted) throws SQLException;
    public List<PhotoDescription> getAllPhotoDescriptions(String login, boolean withDeleted) throws SQLException;
    public List<PhotoDescription> getPhotoDescriptionsBeforeId(User user, long id, long count, boolean withDeleted) throws SQLException;
    public List<PhotoDescription> getPhotoDescriptionsBeforeId(String login, long id, long count, boolean withDeleted) throws SQLException;
    public void deletePhotoDescriptionById(Long id) throws SQLException;
    public void setDeletedPhotoDescriptionById(Long id) throws SQLException;
    public void deleteAllPhotoDescriptions(User user) throws SQLException;
    public void setDeletedAllPhotoDescriptions(User user) throws SQLException;
    public void deleteAllPhotoDescriptions(String user) throws SQLException;
    public void setDeletedAllPhotoDescriptions(String user) throws SQLException;
    public void deleteAllPhotoDescriptions() throws SQLException;
}
