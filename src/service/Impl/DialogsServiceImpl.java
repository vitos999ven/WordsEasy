package service.Impl;


import hibernate.logic.Message;
import hibernate.util.Factory;
import java.sql.SQLException;
import java.util.GregorianCalendar;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;
import service.DialogsService;
import service.JsonObject;


@Service
public class DialogsServiceImpl implements DialogsService {

    private JsonObject json;
    
    @Transactional
    @Override
    public String getDialogMessagesByJson(String other, long lastMessageId, boolean getOldMessages, String cookieUser) {
        json = new JsonObject();
        try{
            List<Message> dialog;
            if (getOldMessages) { 
                dialog = Factory.getInstance().getMessageDAO().getUserToUserMessagesBeforeId(cookieUser ,other, lastMessageId, 20, false);
                Boolean NoMoreMessages = true;
                if (dialog.size() == 20){
                        NoMoreMessages = false;
                        dialog.remove(dialog.get(19));
                }
                json.put("other", other);
                json.put("length", dialog.size());
                json.put("noMore", NoMoreMessages);
                if (lastMessageId == -1) {
                    json.put("otherAvatar", Factory.getInstance().getUserDAO().getUser(other).getAvatar());
                }
            } else {
                dialog = Factory.getInstance().getMessageDAO().getAllUserToUserMessagesFromId(cookieUser, other, lastMessageId, false);   
                json.put("other", other);
                json.put("length", dialog.size());
            }
            for (int i = 0; i < dialog.size(); i++){
                Message m = dialog.get(i);
                JsonObject messageJson = new JsonObject();
                messageJson.put("id", m.getId());
                messageJson.put("date", m.getDate());
                messageJson.put("from_id", m.getFrom_id());
                messageJson.put("to_id", m.getTo_id());
                messageJson.put("value", m.getValue());
                messageJson.put("user", m.getUser());
                messageJson.put("unread", m.getUnread());
                json.put(i, messageJson.toJsonString());
            }
        }catch(SQLException e){}
        return json.toJsonString(); 
    }

    @Transactional
    @Override
    public String getDialogsByJson(String user, long lastMessageId, int count) {
        json = new JsonObject();
        try{
            List<Message> dialogs = Factory.getInstance().getMessageDAO().getDialogsOfAllUsersWhoTalkedWithBeforeId(user, lastMessageId, count, false);
            boolean NoMoreDialogs = true;
            if (dialogs.size() == count){
                NoMoreDialogs = false;
                dialogs.remove(dialogs.get(count - 1));
            }
            json.put("length", dialogs.size());
            json.put("noMore", NoMoreDialogs);
            for (int i = 0; i < dialogs.size(); i++){
                Message m = dialogs.get(i);
                JsonObject dialogJson = new JsonObject();
                String other = (m.getUser().equals(m.getTo_id())) ? m.getFrom_id() : m.getTo_id();
                long otherAvatar = Factory.getInstance().getUserDAO().getUser(other).getAvatar();
                dialogJson.put("id", m.getId());
                dialogJson.put("date", m.getDate());
                dialogJson.put("from_id", m.getFrom_id());
                dialogJson.put("to_id", m.getTo_id());
                dialogJson.put("value", m.getValue());
                dialogJson.put("user", m.getUser());
                dialogJson.put("otherAvatar", otherAvatar);
                dialogJson.put("unread", m.getUnread());
                json.put(i, dialogJson.toJsonString());
            }
        }catch(SQLException e){}
        return json.toJsonString(); 
    }
    
    @Transactional
    @Override
    public void addMessage(String user, String other, String value) {
        Message message = new Message(null, new GregorianCalendar().getTimeInMillis(), user, other, value, user);
        try{
            Factory.getInstance().getMessageDAO().addMessage(message);
        }catch(SQLException e){}
    }

    @Transactional
    @Override
    public String getLastMessageIdByJson(String user, String other) {
        json = new JsonObject();
        try{
            long lastMessageId = Factory.getInstance().getMessageDAO().getLastUserToUserMessageId(user, other);
            json.put("id", lastMessageId);
            if (lastMessageId != -1) {
                json.put("unread", Factory.getInstance().getMessageDAO().getMessageById(lastMessageId).getUnread());
            }
        }catch(SQLException e){}
        return json.toJsonString(); 
    }

    @Transactional
    @Override
    public long getLastDialogsMessageId(String user) {
        long lastId = (long) -1;
        try{
            lastId = Factory.getInstance().getMessageDAO().getLastDialogsMessageId(user);
        }catch(SQLException e){}
        return lastId;
    }
    
    @Transactional
    @Override
    public void changeAllUserToUserMessagesUnread(String user, String other) {
        try{
            Factory.getInstance().getMessageDAO().changeAllUserToUserMessagesUnread(user, other);
        }catch(SQLException e){}
    }
    
    @Transactional
    @Override
    public int getNumberOfUnreadDialogs(String user) {
        int number = 0;
        try{
            number = Factory.getInstance().getMessageDAO().getNumberOfUnreadDialogs(user);
            GregorianCalendar c = new GregorianCalendar();
            Factory.getInstance().getUserDAO().setUserLastOnlineTime(user, c.getTimeInMillis());
        }catch(SQLException e){}
        return number;
    }
    
    @Transactional
    @Override
    public void setDeletedDialog(String user, String other) {
        try{
            Factory.getInstance().getMessageDAO().setDeletedDialog(user, other);
        }catch(SQLException e){}
    }

}
