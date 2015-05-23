package service.Impl;


import hibernate.logic.PhotoComment;
import hibernate.logic.PhotoDescription;
import hibernate.logic.PhotoLike;
import hibernate.logic.User;
import hibernate.util.Factory;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.SQLException;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import service.JsonObject;
import service.PhotoDescriptionsService;


@Service
public class PhotoDescriptionsServiceImpl implements PhotoDescriptionsService{

    private JsonObject json;
    
    @Transactional
    @Override
    public String getPhotoByJson(long photoId, long lastCommentId, int count, String cookieUser) {
        json = new JsonObject();
        try{
            List<PhotoComment> comments = Factory.getInstance().getPhotoCommentDAO().getPhotoCommentsBeforeId(photoId, lastCommentId, count, false);
            boolean NoMorePhotos = true;
            PhotoDescription photo = Factory.getInstance().getPhotoDescriptionDAO().getPhotoDescriptionById(photoId);
            if (comments.size() == count){
                NoMorePhotos = false;
                comments.remove(comments.get(count - 1));
            }
            json.put("length", comments.size());
            json.put("photoId", photoId);
            json.put("user", photo.getUser());
            json.put("noMore", NoMorePhotos);
            if (lastCommentId == -1){
                User user = Factory.getInstance().getUserDAO().getUser(photo.getUser());
                PhotoLike likeByUser = Factory.getInstance().getPhotoLikeDAO().getPhotoLike(photoId, cookieUser);
                int likeNumber = Factory.getInstance().getPhotoLikeDAO().getAllPhotoLikes(photoId, false).size();
                int commentNumber = Factory.getInstance().getPhotoCommentDAO().getAllPhotoComments(photoId, false).size();
                json.put("userAvatar", user.getAvatar());
                json.put("likeNumber", likeNumber);
                json.put("commentNumber", commentNumber);
                json.put("description", photo.getDescription());
                json.put("likedByUser", (likeByUser != null));
            }
            for (int i = 0; i < comments.size(); i++){
                PhotoComment m = comments.get(i);
                JsonObject commentJson = new JsonObject();
                User userFrom = Factory.getInstance().getUserDAO().getUser(m.getUserFrom());
                commentJson.put("id", m.getId());
                commentJson.put("date", m.getDate());
                commentJson.put("photoId", m.getPhotoId());
                commentJson.put("userFrom", m.getUserFrom());
                commentJson.put("avatarFrom", userFrom.getAvatar());
                commentJson.put("value", m.getValue());
                json.put(i, commentJson.toJsonString());
            }
        }catch(NullPointerException | SQLException e){}
        return json.toJsonString();
    }

    @Transactional
    @Override
    public Boolean setUserAvatarByPhotoDescriptionId(long photoId, String cookieUser) {
        try{
            PhotoDescription photo = Factory.getInstance().getPhotoDescriptionDAO().getPhotoDescriptionById(photoId);
            if ((photo != null) && (photo.getUser().equals(cookieUser))){
                Factory.getInstance().getUserDAO().setUserAvatarByPhotoDescriptionId(cookieUser, photoId);
                return true;
            }
        }catch(SQLException e){}
        return false;
    }

    @Transactional
    @Override
    public void setDeletedPhotoDescriptionById(long photoId, String cookieUser, long cookieAvatar) {
        try{
            Factory.getInstance().getPhotoDescriptionDAO().setDeletedPhotoDescriptionById(photoId);
            if (photoId == cookieAvatar){
                User m = Factory.getInstance().getUserDAO().getUser(cookieUser);
                m.setAvatar(new Long(0));
                Factory.getInstance().getUserDAO().updateUser(m);
            }
        }catch(SQLException e){}
    }

    @Transactional
    @Override
    public void loadPhoto(MultipartFile photo, String description, String cookieUser) {
        if (photo.isEmpty()) return;
        try{
            BufferedImage lowImage = ImageIO.read(photo.getInputStream());
            BufferedImage bigImage = lowImage;
            BufferedImage normalImage = null;
            if (lowImage.getHeight() > 768 || lowImage.getWidth() > 1024){
                lowImage = normalImage = getResizedImage(lowImage, 768, 1024);
            }
            lowImage = getResizedImage(lowImage, 300, 300);
            GregorianCalendar c = new GregorianCalendar();
            PhotoDescription desc = new PhotoDescription( null, c.getTimeInMillis(),"Loader","&user:" + cookieUser + "&" + description);
            
            Factory.getInstance().getPhotoDescriptionDAO().addPhotoDescription(desc);
            
            List <PhotoDescription> descs = Factory.getInstance().getPhotoDescriptionDAO().getAllPhotoDescriptions("Loader", false);
            for (PhotoDescription i : descs){
                if ((i.getDescription().equals("&user:" + cookieUser + "&" + description)) && (i.getDate() == c.getTimeInMillis())){
                    desc = i;
                    break;
                }    
            }
            
            String rootPath = "C:/проги/apache-tomcat-8.0.14/webapps/Lets1/images/";
            long photoId = desc.getId();
            ImageIO.write(lowImage, "jpg", new File(rootPath + photoId + "low.jpg"));
            String Path = rootPath + photoId +".jpg";
            if (normalImage != null){
                ImageIO.write(normalImage, "jpg", new File(Path));
                Path = rootPath + photoId +"big.jpg";
            }
            ImageIO.write(bigImage, "jpg", new File(Path));
                
                //File uploadedFile = new File(getServletContext().getRealPath(Path));
                //uploadedFile.createNewFile();
                //fileItem.write(uploadedFile);
            
            desc.setUser(cookieUser);
            desc.setDescription(description);
            TimeUnit.SECONDS.sleep(5);
            Factory.getInstance().getPhotoDescriptionDAO().updatePhotoDescription(desc);
        }catch(SQLException e){
        }catch(Exception e){}    
    }

    
    public BufferedImage getResizedImage(BufferedImage image, int newHeight, int newWidth){
        int height = image.getHeight();
        int width = image.getWidth();
        double ScaleFactor = 0.9;
        BufferedImage newImage = image;
        while(height > newHeight || width > newWidth){
            height = (int)(height*ScaleFactor);
            width = (int)(width*ScaleFactor);
            newImage = resize(newImage, width, height);
        }
        return newImage;
    }
    
    
    public BufferedImage resize(BufferedImage image, int width, int height){
        BufferedImage bufferedImage = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D;
        graphics2D = bufferedImage.createGraphics();
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
        graphics2D.fillRect(0, 0, width, height);
        graphics2D.drawImage(image, 0, 0,width, height, null);//.getScaledInstance(width, height, Image.SCALE_SMOOTH)
	
        if(graphics2D != null) {
            graphics2D.dispose();
        }       
	return bufferedImage;
    }
    
}
