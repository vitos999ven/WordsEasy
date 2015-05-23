package controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import service.PhotoLikesService;


@Controller
public class PhotoLikesController {
    
    @Autowired
    private PhotoLikesService photoLikesService;
    
    @RequestMapping(value = "/getphotolikes", method = RequestMethod.GET)
    public @ResponseBody String getUsersByFastSearch(
            @CookieValue(value = "username", defaultValue = "") String cookieUser,
            @RequestHeader(value = "photoId", defaultValue = "0") Long photoId, 
            @RequestHeader(value = "next", defaultValue = "false") boolean next,
            @RequestHeader(value = "lastUser", defaultValue = "") String lastUser){
        return photoLikesService.getPhotoLikesByJson(photoId, next, lastUser, 11);
    }
    
    @RequestMapping(value = "/addphotolike", method = RequestMethod.POST)
    public @ResponseBody void addPhotoComment(
            @CookieValue(value = "username", defaultValue = "") String cookieUser,
            @RequestHeader(value = "photoId", defaultValue = "0") Long photoId){
        photoLikesService.addPhotoLike(photoId, cookieUser);
    }
        
    @RequestMapping(value = "/deletephotolike", method = RequestMethod.DELETE)
    public @ResponseBody void deletePhotoComment(
            @CookieValue(value = "username", defaultValue = "") String cookieUser,
            @RequestHeader(value = "photoId", defaultValue = "0") Long photoId){
        photoLikesService.setDeletedPhotoLike(photoId, cookieUser);
    }
}

