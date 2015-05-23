package service.Impl;

import hibernate.logic.PhotoLike;
import hibernate.logic.User;
import hibernate.util.Factory;
import java.sql.SQLException;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;
import service.JsonObject;
import service.PhotoLikesService;


@Service
public class PhotoLikesServiceImpl implements PhotoLikesService{

    private JsonObject json;
    
    @Transactional
    @Override
    public String getPhotoLikesByJson(long photoId, boolean next, String lastUser, int count) {
        json = new JsonObject();
        try{
            Boolean NoMoreElementsBefore = true, NoMoreElementsAfter = true;
            List<PhotoLike> likes;
            if (next){
                likes = Factory.getInstance().getPhotoLikeDAO().getAllPhotoLikesAfterLogin(photoId, lastUser, count, false);   
                if (!lastUser.equals("")) 
                    NoMoreElementsBefore = false;
                if (likes.size() == count){
                    NoMoreElementsAfter = false;
                    likes.remove(likes.get(count - 1));
                }
            }else{
                likes = Factory.getInstance().getPhotoLikeDAO().getAllPhotoLikesBeforeLogin(photoId, lastUser, count, false);   
                NoMoreElementsAfter = false;
                if (likes.size() == count){
                    NoMoreElementsBefore = false;
                    likes.remove(likes.get(0));
                }
            }
            json.put("length", likes.size());
            json.put("photoId", photoId);
            json.put("noMoreBefore", NoMoreElementsBefore);
            json.put("noMoreAfter", NoMoreElementsAfter);
            for (int i = 0; i < likes.size(); i++){
                PhotoLike m = likes.get(i);
                JsonObject likeJson = new JsonObject();
                User userFrom = Factory.getInstance().getUserDAO().getUser(m.getUserFrom());
                likeJson.put("id", m.getId());
                likeJson.put("date", m.getDate());
                likeJson.put("userFrom", m.getUserFrom());
                likeJson.put("avatarFrom", userFrom.getAvatar());
                json.put(i, likeJson.toJsonString());
            }
        }catch(SQLException e){}
        return json.toJsonString();
    }

    @Transactional
    @Override
    public void addPhotoLike(long photoId, String user) {
        try{
            Factory.getInstance().getPhotoLikeDAO().addPhotoLike(new PhotoLike(null, photoId, user));
        }catch(SQLException e){}
    }

    @Transactional
    @Override
    public void setDeletedPhotoLike(long photoId, String user) {
        try{
            Factory.getInstance().getPhotoLikeDAO().setDeletedPhotoLike(photoId, user);
        }catch(SQLException e){}
    }
    
}
