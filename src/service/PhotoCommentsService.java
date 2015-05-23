package service;



public interface PhotoCommentsService {
    public String addPhotoComment(long photoId, String cookieUser, String value);
    public void setDeletedPhotoComment(long commentId, String cookieUser);
}
