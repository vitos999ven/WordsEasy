package controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import service.DialogsService;


@Controller
public class DialogsController {
    
    @Autowired
    private DialogsService dialogsService;
    
    @RequestMapping("/changeunread")
    public @ResponseBody void changeMessagesUnread(
            @CookieValue(value = "username", defaultValue = "") String user,
            @RequestHeader(value="other") String other){
        dialogsService.changeAllUserToUserMessagesUnread(user, other);
    }
    
    @RequestMapping("/getoldmessages")
    public @ResponseBody String getOldMessages(
            @CookieValue(value = "username", defaultValue = "") String user,
            @RequestHeader(value="other", defaultValue = "") String other, 
            @RequestHeader(value = "lastId", defaultValue = "-1") long lastMessageId){
        return dialogsService.getDialogMessagesByJson(other, lastMessageId, true, user);
    }
    
    @RequestMapping("/getnewmessages")
    public @ResponseBody String getNewMessages(
            @CookieValue(value = "username", defaultValue = "user") String user,
            @RequestHeader(value="other", defaultValue = "other") String other, 
            @RequestHeader(value = "lastId", defaultValue = "-1") long lastMessageId){
        return dialogsService.getDialogMessagesByJson(other, lastMessageId, false, user);
    }
    
    @RequestMapping(value = "/addmessage", method = RequestMethod.POST)
    public @ResponseBody void addMessage(
            @CookieValue(value = "username", defaultValue = "") String user,
            @RequestHeader(value = "other") String other, 
            @RequestParam(value = "value", required = false) String value){
        value = value.replace("!:.", "&");
        dialogsService.addMessage(user, other, value);
    }
    
    @RequestMapping("/getdialogs")
    public @ResponseBody String getDialogs(
            @CookieValue(value = "username", defaultValue = "") String user,
            @RequestHeader(value = "lastId", defaultValue = "-1") long lastId){
        return dialogsService.getDialogsByJson(user, lastId, 11);
    }
    
    @RequestMapping("/getlastmessageid")
    public @ResponseBody String getLastMessageId(
            @CookieValue(value = "username", defaultValue = "") String user,
            @RequestHeader(value="other") String other){
        return dialogsService.getLastMessageIdByJson(user, other);
    }
    
    @RequestMapping("/getlastdialogsmessageid")
    public @ResponseBody String getLastDialogsMessageId(
            @CookieValue(value = "username", defaultValue = "") String user){
        return "" + dialogsService.getLastDialogsMessageId(user);
    }
    
    @RequestMapping(value = "/numberofdialogs")
    public @ResponseBody String numberOfUnreadDialogs(
            @CookieValue(value = "username", defaultValue = "") String user){
        return "" + dialogsService.getNumberOfUnreadDialogs(user);
    }
    
    @RequestMapping("/deletedialog")
    public @ResponseBody void deleteDialog(
            @CookieValue(value = "username", defaultValue = "") String user,
            @RequestHeader(value="other") String other){
        dialogsService.setDeletedDialog(user, other);
    }
    
}
