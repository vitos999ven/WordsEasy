package service.Impl;


import hibernate.logic.PhotoDescription;
import hibernate.logic.PhotoLike;
import hibernate.logic.SexEnum;
import hibernate.logic.User;
import hibernate.util.Factory;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;
import service.JsonObject;
import service.UsersService;


@Service
public class UsersServiceImpl implements UsersService{
    
    private JsonObject json;
    
    @Transactional
    @Override
    public String getUserByJson(String user, long lastPhotoId, int count, String cookieUser) {
        json = new JsonObject();
        try{
            List<PhotoDescription> photos = Factory.getInstance().getPhotoDescriptionDAO().getPhotoDescriptionsBeforeId(user, lastPhotoId, count, false);
            boolean NoMorePhotos = true;
            if (photos.size() == count){
                NoMorePhotos = false;
                photos.remove(photos.get(count - 1));
            }
            if (lastPhotoId == -1){
                User m = Factory.getInstance().getUserDAO().getUser(user);
                json.put("login", m.getUsername());
                //json.put("access", m.getAccess());
                json.put("sex", m.getSex());
                json.put("birthday", m.getBirthday().getTimeInMillis());
                json.put("city", m.getCity());
                json.put("about", m.getAbout());
                json.put("avatar", m.getAvatar());
                json.put("last_online_time", m.getLastOnlineTime());
                json.put("status", m.getStatus());
                json.put("age", m.getAge());
                json.put("length", photos.size());
                json.put("noMore", NoMorePhotos);
            } else {
                json.put("login", user);
                json.put("length", photos.size());
                json.put("noMore", NoMorePhotos);
            }
            for (int i = 0; i < photos.size(); i++){
                PhotoDescription photo = photos.get(i);
                JsonObject photoJson = new JsonObject();
                int likeNumber = Factory.getInstance().getPhotoLikeDAO().getAllPhotoLikes(photo.getId(), false).size();
                int commentNumber = Factory.getInstance().getPhotoCommentDAO().getAllPhotoComments(photo.getId(), false).size();
                PhotoLike likeByCookieUser = Factory.getInstance().getPhotoLikeDAO().getPhotoLike(photo.getId(), cookieUser);
                photoJson.put("id", photo.getId());
                photoJson.put("likeNumber", likeNumber);
                photoJson.put("commentNumber", commentNumber);
                photoJson.put("likedByUser", (likeByCookieUser != null));
                json.put(i, photoJson.toJsonString());
            }
        }catch(SQLException e){}
        return json.toJsonString();
    }

    @Transactional
    @Override
    public String getUsersByJson(String search, String city, SexEnum sex, int ageFrom, int ageTo, String lastLogin, int count, String cookieUser) {
        json = new JsonObject();
        try{
            List<User> users = Factory.getInstance().getUserDAO().getUsersBySearchAfterLogin(search, sex, ageFrom, ageTo, city, lastLogin, count);
            boolean NoMoreUsers = true;
            if (users.size() == count){
                NoMoreUsers = false;
                users.remove(users.get(count - 1));
            } 
            json.put("length", users.size());
            json.put("noMore", NoMoreUsers);
            for (int i = 0; i < users.size(); i++){
                User m = users.get(i);
                JsonObject userJson = new JsonObject();
                userJson.put("login", m.getUsername());
                //userJson.put("access", m.getAccess());
                userJson.put("sex", m.getSex());
                userJson.put("birthday", m.getBirthday().getTimeInMillis());
                userJson.put("city", m.getCity());
                userJson.put("about", m.getAbout());
                userJson.put("avatar", m.getAvatar());
                userJson.put("last_online_time", m.getLastOnlineTime());
                userJson.put("status", m.getStatus());
                userJson.put("age", m.getAge());
                json.put(i, userJson.toJsonString());
            }
        }catch(SQLException e){}
        return json.toJsonString();    
    }

    @Transactional
    @Override
    public String getUsersByJson(String search, String lastLogin, boolean next, int count) {
        json = new JsonObject();
        Boolean NoMoreElementsBefore = true, NoMoreElementsAfter = true;
        List<User> users;
        try{
            if (next){
                users = Factory.getInstance().getUserDAO().getUsersBySearchAfterLogin(search, lastLogin, count);   
                if (!lastLogin.equals("")) NoMoreElementsBefore = false;
                if (users.size() == count){
                    NoMoreElementsAfter = false; 
                    users.remove(users.get(count - 1));
                }
            } else {
                users = Factory.getInstance().getUserDAO().getUsersBySearchBeforeLogin(search, lastLogin, count);  
                NoMoreElementsAfter = false;
                if (users.size() == count){
                    NoMoreElementsBefore = false;
                    users.remove(users.get(0));
                }
            }
            json.put("length", users.size());
            json.put("noMoreBefore", NoMoreElementsBefore);
            json.put("noMoreAfter", NoMoreElementsAfter);
            for (int i = 0; i < users.size(); i++){
                User m = users.get(i);
                JsonObject userJson = new JsonObject();
                userJson.put("login", m.getUsername());
                userJson.put("sex", m.getSex());
                userJson.put("city", m.getCity());
                userJson.put("age", m.getAge());
                userJson.put("avatar", m.getAvatar());
                json.put(i, userJson.toJsonString());
            }
        }catch(SQLException e){}
        return json.toJsonString();      
    }

    @Transactional
    @Override
    public Boolean checkLoginUnique(String login) {
        try{
            User checkUser = Factory.getInstance().getUserDAO().getUser(login);
            return (checkUser == null);
        }catch(SQLException e){
            return true;
        }
    }

    @Transactional
    @Override
    public Boolean addNewUser(String login, String password, byte sex, Calendar birthday, String city, String about) {
        try{
            //User newUser = new User(login, password, (byte)0, sex, birthday, city,about, new Long(0));
            //Factory.getInstance().getUserDAO().addUser(newUser);
            User checkUser = Factory.getInstance().getUserDAO().getUser(login);
            return (checkUser != null);
        }catch(SQLException e){
            return false;
        }
    }

    @Transactional
    @Override
    public Boolean checkPassword(String login, String password) {
        try{
            User checkUser = Factory.getInstance().getUserDAO().getUser(login);
            if ((checkUser != null) && (checkUser.getPassword().equals(password))) {
                return true;
            }
        }catch(SQLException e){
        }
        return false;
    }

    @Transactional
    @Override
    public Long getUserAvatarId(String login) {
        try{
            Long avatar = Factory.getInstance().getUserDAO().getUser(login).getAvatar();
            return avatar;
        }catch(NullPointerException | SQLException e){
        }
        return (long) 0;
    }
}
