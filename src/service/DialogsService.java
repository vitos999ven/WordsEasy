package service;


public interface DialogsService {
    public String getDialogMessagesByJson(String other, long lastMessageId, boolean getOldMessages, String cookieUser);
    public String getDialogsByJson(String user, long lastMessageId, int count);
    public void addMessage(String user, String other, String value);
    public String getLastMessageIdByJson(String user, String other);
    public long getLastDialogsMessageId(String user);
    public void changeAllUserToUserMessagesUnread(String user, String other);
    public int getNumberOfUnreadDialogs(String user);
    public void setDeletedDialog(String user, String other);
}
