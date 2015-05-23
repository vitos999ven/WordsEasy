package controllers;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import service.PhotoDescriptionsService;


@Controller
public class PhotoDescriptionsController {
    
    @Autowired
    private PhotoDescriptionsService photoDescriptionsService;
    
    @RequestMapping("/getphoto")
    public @ResponseBody String getPhoto(
            @CookieValue(value = "username", defaultValue = "") String cookieUser,
            @RequestHeader(value = "photoId", defaultValue = "-1") long photoId){
        return photoDescriptionsService.getPhotoByJson(photoId, (long) -1, 11, cookieUser);
    }
    
    @RequestMapping("/getmorephotocomments")
    public @ResponseBody String getMorePhotoComments(
            @CookieValue(value = "username", defaultValue = "") String cookieUser,
            @RequestHeader(value = "photoId", defaultValue = "-1") long photoId,
            @RequestHeader(value = "lastCommentId", defaultValue = "-1") long lastCommentId){
        return photoDescriptionsService.getPhotoByJson(photoId, lastCommentId, 11, cookieUser);
    }
    
    @RequestMapping(value = "/setavatar", method = RequestMethod.POST)
    public @ResponseBody String setAvatarByPhotoId(
            @CookieValue(value = "username", defaultValue = "") String cookieUser,
            @RequestHeader(value = "photoId", defaultValue = "-1") Long photoId,
            HttpServletResponse response){
        Boolean success = photoDescriptionsService.setUserAvatarByPhotoDescriptionId(photoId, cookieUser);
        if (success){
            response.addCookie(new Cookie("useravatar", photoId.toString()));
        }
        return success.toString();
    }
    
    @RequestMapping(value = "/deletephoto", method = RequestMethod.DELETE)
    public @ResponseBody void setDeletedPhotoDescription(
            @CookieValue(value = "username", defaultValue = "") String cookieUser,
            @CookieValue(value = "useravatar", defaultValue = "0") long cookieAvatar,
            @RequestHeader(value = "photoId", defaultValue = "0") Long photoId,
            HttpServletResponse response){
        photoDescriptionsService.setDeletedPhotoDescriptionById(photoId, cookieUser, cookieAvatar);
        if (photoId == cookieAvatar){
            response.addCookie(new Cookie("useravatar", "0"));
        }
    }
    
    @RequestMapping(value = "/loadphoto", headers = "content-type=multipart/*", method = RequestMethod.POST)
    public @ResponseBody void loadPhoto(
            @CookieValue(value = "username", defaultValue = "") String cookieUser,
            @RequestParam(value = "photo") MultipartFile photo,
            @RequestParam(value = "description", defaultValue = "") String description){
        photoDescriptionsService.loadPhoto(photo, description, cookieUser);
    }
}
