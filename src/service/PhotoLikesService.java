package service;



public interface PhotoLikesService {
    public String getPhotoLikesByJson(long photoId, boolean next, String lastUser, int count);
    public void addPhotoLike(long photoId, String user);
    public void setDeletedPhotoLike(long photoId, String user);
}
