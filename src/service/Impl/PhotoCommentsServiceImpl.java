package service.Impl;


import hibernate.logic.PhotoComment;
import hibernate.logic.PhotoDescription;
import hibernate.logic.User;
import hibernate.util.Factory;
import java.sql.SQLException;
import java.util.GregorianCalendar;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;
import service.JsonObject;
import service.PhotoCommentsService;


@Service
public class PhotoCommentsServiceImpl implements PhotoCommentsService{

    private JsonObject json;
    
    @Transactional
    @Override
    public String addPhotoComment(long photoId, String cookieUser, String value) {
        json = new JsonObject();
        try{
            GregorianCalendar c = new GregorianCalendar();
            Factory.getInstance().getPhotoCommentDAO().addPhotoComment(new PhotoComment(null, photoId, c.getTimeInMillis(), cookieUser, value));
            PhotoComment m = Factory.getInstance().getPhotoCommentDAO().getLastPhotoComment(cookieUser);
            User userFrom = Factory.getInstance().getUserDAO().getUser(m.getUserFrom());
            json.put("id", m.getId());
            json.put("date", m.getDate());
            json.put("photoId", m.getPhotoId());
            json.put("userFrom", m.getUserFrom());
            json.put("avatarFrom", userFrom.getAvatar());
            json.put("value", m.getValue());
        }catch(SQLException e){}
        return json.toJsonString();
    }

    @Transactional
    @Override
    public void setDeletedPhotoComment(long commentId, String cookieUser) {
        try{
            PhotoComment comment = Factory.getInstance().getPhotoCommentDAO().getPhotoCommentById(commentId);
            PhotoDescription photo = Factory.getInstance().getPhotoDescriptionDAO().getPhotoDescriptionById(comment.getPhotoId());
            if (!comment.getUserFrom().equals(cookieUser) && !photo.getUser().equals(cookieUser)) return;
            Factory.getInstance().getPhotoCommentDAO().setDeletedPhotoCommentById(commentId);
        }catch(SQLException e){}
    }
    
}
