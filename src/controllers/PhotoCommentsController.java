package controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import service.PhotoCommentsService;


@Controller
public class PhotoCommentsController {
    
    @Autowired
    private PhotoCommentsService photoCommentsService;
    
    @RequestMapping(value = "/addphotocomment", method = RequestMethod.POST)
    public @ResponseBody String addPhotoComment(
            @CookieValue(value = "username", defaultValue = "") String cookieUser,
            @RequestParam(value = "value", defaultValue = "") String value,
            @RequestHeader(value = "photoId", defaultValue = "0") Long photoId){
        value = value.replace("!:.", "&");
        return photoCommentsService.addPhotoComment(photoId, cookieUser, value);
    }
        
    @RequestMapping(value = "/deletephotocomment", method = RequestMethod.DELETE)
    public @ResponseBody void deletePhotoComment(
            @CookieValue(value = "username", defaultValue = "") String cookieUser,
            @RequestParam(value = "value", defaultValue = "") String value,
            @RequestHeader(value = "commentId", defaultValue = "0") Long commentId){
        photoCommentsService.setDeletedPhotoComment(commentId, cookieUser);
    }
}
