package hibernate;

import hibernate.logic.Message;
import hibernate.logic.PhotoComment;
import hibernate.logic.PhotoDescription;
import hibernate.logic.PhotoLike;
import hibernate.logic.User;
import hibernate.util.Factory;
import java.sql.SQLException;
import java.util.GregorianCalendar;
import java.util.List;


public class ManualTesting {
    public static void main(String[] args) throws SQLException, InterruptedException{     
        //changeAllUserToUserMessagesUnread();
      
        getAllPhotoLikes();
        System.exit(0);
    }
    
  
       
    
    
    //***********************************     
    //USER TESTS
    //***********************************
    public static void printUsersList(List<User> users) {
        System.out.println("\n*USERS*");
        for (User m : users) {
            System.out.println("*************************\nlogin: "+m.getUsername()+", password: "+m.getPassword()+", sex: "+m.getSex()+", birthday: "+m.getBirthday().getTime()+", city: "+m.getCity()+", about: "+m.getAbout()+", avatar: "+m.getAvatar()+", AGE: "+m.getAge());
        }
        System.out.println("*************************\n");
    }
    
    private static void getAllUsers() throws SQLException{
        List<User> users = Factory.getInstance().getUserDAO().getUsersBySearchBeforeLogin("user", "user5", 11);   
        printUsersList(users);
    }
    private static void addUser() throws SQLException{
        //Factory.getInstance().getUserDAO().addUser(new User("Loader", "ewirowevuxcviop339", AccessEnum.USER.getValue(), (byte)1, new GregorianCalendar(1976, 9, 17), "", "", (long) 1));
        getAllUsers();
    }
    
    private static void updateUser() throws SQLException{
        //Factory.getInstance().getUserDAO().updateUser(new User("admin2", "0000", AccessEnum.ADMIN.getValue(), (byte)2, new GregorianCalendar(1976, 1, 23), "asd", "baba", (long) 1));
        getAllUsers();
    }
    
    private static void getUserByLogin() throws SQLException{
        User m = Factory.getInstance().getUserDAO().getUser("uio");
        System.out.println(m);
        //if (m != null) System.out.println("*************************\nlogin: "+m.getUsername()+", password: "+m.getPassword()+", access: "+m.getAccess()+", sex: "+m.getSex()+", birthday: "+m.getBirthday()+", city: "+m.getCity()+", about: "+m.getAbout()+", avatar: "+m.getAvatar()+", AGE: "+m.getAge()+"\n*************************\n");
    }
      
    private static void deleteUserByLogin() throws SQLException{
        String login = "sdfsdfsd";
        System.out.print("\n\n*BEGORE DELETE OF "+login+" USER*   ");
        getAllUsers();
        getAllMessages();
        getAllPhotoDescriptions();
        getAllPhotoLikes();
        Factory.getInstance().getUserDAO().deleteUserByLogin(login);
        System.out.print("\n\n*AFTER DELETE OF "+login+" USER*   ");
        getAllUsers();
        getAllMessages();
        getAllPhotoDescriptions();
        getAllPhotoLikes();
    }
   
    

    
    //***********************************
    //MESSAGES TESTS
    //***********************************
    public static void printMessagesList(List<Message> message) {
        System.out.println("\n*MESSAGES*");
        for (Message m : message) {
            System.out.println("*************************\n(id) "+m.getId()+", (date) "+m.getDate()+", (from_user) "+m.getFrom_id()+", (to_user) "+m.getTo_id()+", (value) "+m.getValue()+", (user) "+m.getUser()+", (unread) "+m.getUnread()+", (delete) "+m.getDeleted());
        }
        System.out.println("*************************\n");
    }
    
    private static void addMessage() throws SQLException{
        GregorianCalendar c = new GregorianCalendar();
        Factory.getInstance().getMessageDAO().addMessage(new Message(null, c.getTimeInMillis(), "admin1", "admin2", "sasdsddsd", null));
        getAllMessages();
    }
    
    private static void getMessageById() throws SQLException{
        Long id = (long) 11;
        getAllMessages();
        Message m = Factory.getInstance().getMessageDAO().getMessageById(id);
        System.out.println("\n*MESSAGE "+id+"*");
        if (m!= null) System.out.println("?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?\n(id) "+m.getId()+", (date) "+m.getDate()+", (from_user) "+m.getFrom_id()+", (to_user) "+m.getTo_id()+", (value) "+m.getValue()+", (user) "+m.getUser()+", (unread) "+m.getUnread()+", (deleted) "+m.getDeleted()+ "\n?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?\n");
       
    }
    
    private static void getAllMessages() throws SQLException{
        List<Message> messages = Factory.getInstance().getMessageDAO().getAllMessages();
        printMessagesList(messages);
    }
    
    private static void getAllMessagesByUser() throws SQLException{
        System.out.print("\n*ALL*");
        getAllMessages();
        String login = "admin4"; 
        User user = Factory.getInstance().getUserDAO().getUser(login);
        List<Message> messages = Factory.getInstance().getMessageDAO().getAllMessages(user, false, true);
        System.out.print("\n*SELECTED FOR "+login+" USER*");
        printMessagesList(messages);
    }
    
    private static void getAllUserToUserMessages() throws SQLException{
        String firstLogin = "admin2", secondLogin = "god";
        getAllMessages();
        List<Message> messages = Factory.getInstance().getMessageDAO().getUserToUserMessagesBeforeId(firstLogin, secondLogin, -1, 20, true);
        System.out.println("\n*USERs "+firstLogin+" USER "+firstLogin+" toUSER "+secondLogin+" MESSAGES*");
        printMessagesList(messages);
    }
    
    private static void changeAllUserToUserMessagesUnread() throws SQLException{
        String firstLogin = "admin5", secondLogin = "admin2";
        User first = Factory.getInstance().getUserDAO().getUser(firstLogin);
        User second = Factory.getInstance().getUserDAO().getUser(secondLogin);
        Factory.getInstance().getMessageDAO().changeAllUserToUserMessagesUnread(first, second);
        getAllMessages();
        
        List<Message> messages = Factory.getInstance().getMessageDAO().getAllUserToUserMessages(first, second, true);
        System.out.println("\n*USERs "+firstLogin+" USER "+firstLogin+" toUSER "+secondLogin+" MESSAGES*");
        printMessagesList(messages);
    }
    
    private static void getDialogsOfAllUsersWhoTalkedWith() throws SQLException{
        String login = "god";
        User user = Factory.getInstance().getUserDAO().getUser(login);
        getAllMessages();
        List<Message> messages = Factory.getInstance().getMessageDAO().getDialogsOfAllUsersWhoTalkedWithBeforeId(user, -1, 11, false);
        System.out.println("\n*USERs "+login+" DIALOGS*\nUnread: "+ Factory.getInstance().getMessageDAO().getNumberOfUnreadDialogs(login));
        printMessagesList(messages);
    }
    
    private static void deleteMessageById() throws SQLException{
        System.out.print("\n*BEFORE DELETE*");
        getAllMessages();
        Long id = (long) 3;
        Factory.getInstance().getMessageDAO().deleteMessageById(id);
        System.out.print("\n*AFTER DELETE OF "+id+" MESSAGE*   ");
        getAllMessages();
    }
    
    private static void deleteAllMessagesByUser() throws SQLException{
        System.out.print("\n*BEFORE DELETE*");
        getAllMessages();
        String login = "god";
        User user = Factory.getInstance().getUserDAO().getUser(login);
        Factory.getInstance().getMessageDAO().deleteAllMessages(user);
        System.out.print("\n*AFTER DELETE OF "+login+" USERs Messages*   ");
        getAllMessages();
    }
    
   
    
    //***********************************     
    //PHOTODESCRIPTION TESTS
    //***********************************
    public static void printPhotoDescriptionList(List<PhotoDescription> photoDescriptions) {
        System.out.println("\n*PhotoDESCRIPTIONS*");
        for (PhotoDescription m : photoDescriptions) {
            System.out.println("*************************\n(id) "+m.getId()+", (date) "+m.getDate()+", (user) "+m.getUser()+", (description) "+m.getDescription()+", (deleted) "+m.getDeleted());
        }
        System.out.println("*************************\n");
    }
    
    private static void addPhotoDescription() throws SQLException{
        PhotoDescription m = new PhotoDescription(
                null,//id
                "admin2",//user
                "sdfdsfv");//description
       System.out.println("?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?\n"
               + "NEW DESCRIPTION(id) "+m.getId()+", (user) "+m.getUser()+", (description) "+m.getDescription()+
               "\n?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?\n");
        
        Factory.getInstance().getPhotoDescriptionDAO().addPhotoDescription(m);
        getAllPhotoDescriptions();
    }
        
    private static void updatePhotoDescription() throws SQLException{
        
        System.out.println("*BEFORE UPDATE*");
        getAllPhotoDescriptions();
        Long id = (long) 10;
        String value = "kkdf;dskf;s";
        System.out.println("* UPDATE ID = "+id+", DESCRIPTION = '"+value+"' *");
        Factory.getInstance().getPhotoDescriptionDAO().updatePhotoDescription(id, value);
        getAllPhotoDescriptions();
    }
       
    private static void getPhotoDescriptionById() throws SQLException{
        Long id = (long) 8;
        getAllPhotoDescriptions();
        PhotoDescription m = Factory.getInstance().getPhotoDescriptionDAO().getPhotoDescriptionById(id);
        System.out.println("\n*ADVCOMMENT "+id+"*");
        if (m!= null)  System.out.println("?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?\n"
               + "DESCRIPTION(id) "+m.getId()+", (user) "+m.getUser()+", (description) "+m.getDescription()+
               "\n?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?\n");
       }
    
   
    
    private static void getAllPhotoDescriptions() throws SQLException{
        List<PhotoDescription> photoDescriptions = Factory.getInstance().getPhotoDescriptionDAO().getAllPhotoDescriptions();
        printPhotoDescriptionList(photoDescriptions);
    }
    
    private static void getAllPhotoDescriptionsByLogin() throws SQLException{
        getAllPhotoDescriptions();
        List<PhotoDescription> photoDescriptions = Factory.getInstance().getPhotoDescriptionDAO().getAllPhotoDescriptions("testUser1", true);
        printPhotoDescriptionList(photoDescriptions);
    }
    
    private static void deletePhotoDescriptionById() throws SQLException{
        System.out.print("\n*BEFORE DELETE*");
        getAllPhotoDescriptions();
        long id = 3;
        Factory.getInstance().getPhotoDescriptionDAO().deletePhotoDescriptionById(id);
        System.out.print("\n*AFTER DELETE OF "+id+" DESCRIPTION*   ");
        getAllPhotoDescriptions();
    } 
    
    
    private static void deletePhotoDescriptionsByUser() throws SQLException{
        System.out.print("\n*BEFORE DELETE*");
        getAllPhotoDescriptions();
        String user = "testUser1";
        Factory.getInstance().getPhotoDescriptionDAO().deleteAllPhotoDescriptions(user);
        System.out.print("\n*AFTER DELETE OF "+user+" USERs photo DESCRIPTIONs*   ");
        getAllPhotoDescriptions();
    } 
    
    
    
    //***********************************     
    //PHOTOLIKES TESTS
    //***********************************
    public static void printPhotoLikeList(List<PhotoLike> photoLikes) {
        System.out.println("\n*PhotoLikes*");
        for (PhotoLike m : photoLikes) {
            System.out.println("*************************\n(id) "+m.getId()+", (date) "+m.getDate()+", (photo_id) "+m.getPhotoId()+", (user_from) "+m.getUserFrom() + ", (deleted) "+m.getDeleted());
        }
        System.out.println("*************************\n");
      
    }
    
    private static void addPhotoLike() throws SQLException{
        
        PhotoLike m = new PhotoLike(
                null,//id
                new Long(10),//photo_id
                "admin5");//user_from
       System.out.println("?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?\n"
               + "NEW PhotoLIKE(id) "+m.getId()+", (photo_id) "+m.getPhotoId()+", (user_from) "+m.getUserFrom()+
               "\n?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?\n");
        
        Factory.getInstance().getPhotoLikeDAO().addPhotoLike(m);
        getAllPhotoLikes();
    }
    
    public static void getPhotoLikeById() throws SQLException{
        Long id = (long) 10;
        getAllPhotoLikes();
        PhotoLike m = Factory.getInstance().getPhotoLikeDAO().getPhotoLikeById(id);
        System.out.println("\n*PhotoLIKE "+id+"*");
        if (m!= null)  System.out.println("?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?\n"
               + "PhotoLIKE(id) "+m.getId()+", (photo_id) "+m.getPhotoId()+", (user_from) "+m.getUserFrom()+
               "\n?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?\n");
        }
    
    public static void getPhotoLikeByPhotoAndUser() throws SQLException{
        Long id = (long) 9;
        String user_from = "admin1";
        getAllPhotoLikes();
        PhotoLike m = Factory.getInstance().getPhotoLikeDAO().getPhotoLikeById(id);
        System.out.println("\n*"+user_from+" USERs PhotoLIKE FOR "+id+" ID PHOTO*");
        if (m!= null)  System.out.println("?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?\n"
               + "PhotoLIKE(id) "+m.getId()+", (photo_id) "+m.getPhotoId()+", (user_from) "+m.getUserFrom()+
               "\n?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?\n");
        }
    
    private static void getAllPhotoLikes() throws SQLException{
        List<PhotoLike> photoLikes = Factory.getInstance().getPhotoLikeDAO().getAllPhotoLikesBeforeLogin((long) 179, "user26", 11, false);  
        printPhotoLikeList(photoLikes);
    }  
    
    private static void getAllPhotoLikesAndDescriptionsByUser() throws SQLException{
        getAllPhotoLikes();
        List<PhotoLike> photoLikes = Factory.getInstance().getPhotoLikeDAO().getAllPhotoLikes("admin5", true);
        printPhotoLikeList(photoLikes);
    } 
    
    private static void getAllPhotoLikesByPhotoId() throws SQLException{
        getAllPhotoLikes();
        List<PhotoLike> photoLikes = Factory.getInstance().getPhotoLikeDAO().getAllPhotoLikes(new Long(9), true);
        printPhotoLikeList(photoLikes);
    } 
    
    private static void deletePhotoLikeById() throws SQLException{
        System.out.print("\n*BEFORE DELETE*");
        getAllPhotoLikes();
        long id = 3;
        Factory.getInstance().getPhotoLikeDAO().deletePhotoLikeById(id);
        System.out.print("\n*AFTER DELETE OF "+id+" Like*   ");
        getAllPhotoLikes();
    } 
    
    private static void deletePhotoLike() throws SQLException{
        System.out.print("\n*BEFORE DELETE*");
        getAllPhotoLikes();
        long id = 5;
        String str = "god";
        Factory.getInstance().getPhotoLikeDAO().deletePhotoLike(id, str);
        System.out.print("\n*AFTER DELETE OF "+id+" "+str+" Users Like*   ");
        getAllPhotoLikes();
    }    
    
    private static void deleteAllPhotoLikesByUser() throws SQLException{
        System.out.print("\n*BEFORE DELETE*");
        getAllPhotoLikes();
        String user = "user1";
        Factory.getInstance().getPhotoLikeDAO().deleteAllPhotoLikes(user);
        System.out.print("\n*AFTER DELETE OF "+user+" USERs photo Likes*   ");
        getAllPhotoLikes();
    }
    

    
    //***********************************     
    //PHOTOCOMMENTS TESTS
    //***********************************
    public static void printPhotoCommentList(List<PhotoComment> photoComments) {
        System.out.println("\n*PhotoCOMMENTS*");
        for (PhotoComment m : photoComments) {
            System.out.println("*************************\n(id) "+m.getId()+", (photo_id) "+m.getPhotoId()+", (date) "+m.getDate()+", (user_from) "+m.getUserFrom()+", (value) "+m.getValue());
        }
        System.out.println("*************************\n");
    }
    
    private static void addPhotoComment() throws SQLException{
        GregorianCalendar c = new GregorianCalendar();
        
        PhotoComment m = new PhotoComment(
                null,//id
                new Long(84),//photo_id
                c.getTimeInMillis(),//date
                "admin8",//user_from
                "рsолxcxzc");//value
       System.out.println("?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?\n"
               + "NEW PhotoCOMMENT(id) "+m.getId()+", (photo_id) "+m.getPhotoId()+", (date) "+m.getDate()+", (user_from) "+m.getUserFrom()+", (value) "+m.getValue()+
               "\n?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?\n");
        
        Factory.getInstance().getPhotoCommentDAO().addPhotoComment(m);
        getAllPhotoComments();
    }    
    
    public static void getPhotoCommentById() throws SQLException{
        Long id = (long) 3;
        getAllPhotoComments();
        PhotoComment m = Factory.getInstance().getPhotoCommentDAO().getPhotoCommentById(id);
        System.out.println("\n*PhotoComment "+id+"*");
        if (m!= null)  System.out.println("?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?\n"
               + "PhotoCOMMENT(id) "+m.getId()+", (photo_id) "+m.getPhotoId()+", (date) "+m.getDate()+", (user_from) "+m.getUserFrom()+", (value) "+m.getValue()+
               "\n?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?&?\n");
       } 
    
    private static void getAllPhotoComments() throws SQLException{
        List<PhotoComment> photoComments = Factory.getInstance().getPhotoCommentDAO().getAllPhotoComments();
        printPhotoCommentList(photoComments);
    } 
    
    private static void getAllPhotoCommentsByUser() throws SQLException{
        getAllPhotoComments() ;
        String user = "admin5";
        List<PhotoComment> photoComments = Factory.getInstance().getPhotoCommentDAO().getAllPhotoComments(user, true);
        System.out.println("**"+user+" USERs PHOTOCOMMENTS**");
        printPhotoCommentList(photoComments);
    }    
    
    private static void getAllPhotoCommentsByPhoto() throws SQLException{
        getAllPhotoComments() ;
        Long id = (long) 99;
        List<PhotoComment> photoComments = Factory.getInstance().getPhotoCommentDAO().getAllPhotoComments(id, true);
        System.out.println("**"+id+" ID PHOTOs COMMENTS**");
        printPhotoCommentList(photoComments);
    } 
    
    private static void deletePhotoCommentById() throws SQLException{
        System.out.print("\n*BEFORE DELETE*");
        getAllPhotoComments();
        long id = 3;
        Factory.getInstance().getPhotoCommentDAO().deletePhotoCommentById(id);
        System.out.print("\n*AFTER DELETE OF "+id+" PhotoCOMMENT*   ");
        getAllPhotoComments();
    } 
    
    
}
