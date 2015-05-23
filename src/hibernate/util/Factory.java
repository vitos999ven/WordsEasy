package hibernate.util;


import hibernate.DAO.Impl.MessageDAOImpl;
import hibernate.DAO.Impl.PhotoCommentDAOImpl;
import hibernate.DAO.Impl.PhotoDescriptionDAOImpl;
import hibernate.DAO.Impl.PhotoLikeDAOImpl;
import hibernate.DAO.Impl.UserDAOImpl;
import hibernate.DAO.Impl.UserRoleDAOImpl;
import hibernate.DAO.MessageDAO;
import hibernate.DAO.PhotoCommentDAO;
import hibernate.DAO.PhotoDescriptionDAO;
import hibernate.DAO.PhotoLikeDAO;
import hibernate.DAO.UserDAO;
import hibernate.DAO.UserRoleDAO;


public class Factory {
    
    private static class FactoryHolder{
        private final static Factory instance = new Factory();
    }
    
    private static class MessageDAOHolder{
        private final static MessageDAO messageDAO = new MessageDAOImpl();
    }
    
    private static class PhotoCommentDAOHolder{
        private final static PhotoCommentDAO photoCommentDAO = new PhotoCommentDAOImpl();
    }
    
    private static class PhotoDescriptionDAOHolder{
        private final static PhotoDescriptionDAO photoDescriptionDAO = new PhotoDescriptionDAOImpl();
    }
    
    private static class PhotoLikeDAOHolder{
        private final static PhotoLikeDAO photoLikeDAO = new PhotoLikeDAOImpl();
    }
    
    private static class UserDAOHolder{
        private final static UserDAO userDAO = new UserDAOImpl();
    }
    
    private static class UserRoleDAOHolder{
        private final static UserRoleDAO userRoleDAO = new UserRoleDAOImpl();
    }
    
    public static Factory getInstance(){
        return FactoryHolder.instance;
    }
     
    public MessageDAO getMessageDAO(){
        return MessageDAOHolder.messageDAO;
    }

    public PhotoCommentDAO getPhotoCommentDAO(){
        return PhotoCommentDAOHolder.photoCommentDAO;
    }
    
    public PhotoDescriptionDAO getPhotoDescriptionDAO(){
        return PhotoDescriptionDAOHolder.photoDescriptionDAO;
    }
    
    public PhotoLikeDAO getPhotoLikeDAO(){
        return PhotoLikeDAOHolder.photoLikeDAO;
    }
    
    public UserDAO getUserDAO(){
        return UserDAOHolder.userDAO;
    }
    
    public UserRoleDAO getUserRoleDAO(){
        return UserRoleDAOHolder.userRoleDAO;
    }
    
}
