package service;


import org.springframework.web.multipart.MultipartFile;



public interface PhotoDescriptionsService {
    public String getPhotoByJson(long photoId, long lastCommentId, int count, String cookieUser);
    public Boolean setUserAvatarByPhotoDescriptionId(long photoId, String cookieUser);
    public void setDeletedPhotoDescriptionById(long photoId, String cookieUser, long cookieAvatar);
    public void loadPhoto(MultipartFile photo, String description, String cookieUser);
}
