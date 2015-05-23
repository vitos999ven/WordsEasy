package hibernate.DAO;

import java.sql.SQLException;
import java.util.List;
import hibernate.logic.Message;
import hibernate.logic.User;
         
         
public interface MessageDAO {
    public void addMessage(Message message) throws SQLException;
    public void updateMessage(Message message) throws SQLException;
    public Message getMessageById(Long id) throws SQLException;
    public void changeMessageUnread(Long id) throws SQLException;
    public void changeAllUserToUserMessagesUnread(User user, User other) throws SQLException;
    public void changeAllUserToUserMessagesUnread(String user, String other) throws SQLException;
    public List<Message> getAllMessages() throws SQLException;
    public List<Message> getAllMessages(User user, boolean onlyUsers, boolean withDeleted) throws SQLException;
    public List<Message> getAllMessages(String user, boolean onlyUsers, boolean withDeleted) throws SQLException;
    public List<Message> getAllUserToUserMessages(User firstUser, User secondUser, boolean withDeleted) throws SQLException;
    public List<Message> getAllUserToUserMessages(String firstUser, String secondUser, boolean withDeleted) throws SQLException;
    public List<Message> getUserToUserMessagesBeforeId(User firstUser, User secondUser, long id, long count, boolean withDeleted) throws SQLException;
    public List<Message> getUserToUserMessagesBeforeId(String firstUser, String secondUser, long id, long count, boolean withDeleted) throws SQLException;
    public List<Message> getAllUserToUserMessagesFromId(User firstUser, User secondUser, long lastId, boolean withDeleted) throws SQLException;
    public List<Message> getAllUserToUserMessagesFromId(String firstUser, String secondUser , long lastId, boolean withDeleted) throws SQLException;
    public Long getLastUserToUserMessageId(User firstUser, User secondUser) throws SQLException;
    public Long getLastUserToUserMessageId(String firstUser, String secondUser) throws SQLException;
    public List<Message> getDialogsOfAllUsersWhoTalkedWith(User user, boolean withDeleted) throws SQLException;
    public List<Message> getDialogsOfAllUsersWhoTalkedWith(String user, boolean withDeleted) throws SQLException;
    public List<Message> getDialogsOfAllUsersWhoTalkedWithBeforeId(User user, long id, long count, boolean withDeleted) throws SQLException;
    public List<Message> getDialogsOfAllUsersWhoTalkedWithBeforeId(String user, long id, long count, boolean withDeleted) throws SQLException;
    public Long getLastDialogsMessageId(User user) throws SQLException;
    public Long getLastDialogsMessageId(String user) throws SQLException;
    public int getNumberOfUnreadDialogs(User user) throws SQLException;
    public int getNumberOfUnreadDialogs(String user) throws SQLException;
    public void deleteMessageById(Long id) throws SQLException; 
    public void deleteAllMessages(User user) throws SQLException; 
    public void deleteAllMessages(String user) throws SQLException; 
    public void deleteDialog(User user, User other) throws SQLException; 
    public void deleteDialog(String user, String other) throws SQLException; 
    public void setDeletedMessageById(Long id) throws SQLException; 
    public void setDeletedAllMessages(User user) throws SQLException; 
    public void setDeletedAllMessages(String user) throws SQLException; 
    public void setDeletedDialog(User user, User other) throws SQLException; 
    public void setDeletedDialog(String user, String other) throws SQLException;
}
