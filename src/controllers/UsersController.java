package controllers;


import hibernate.logic.SexEnum;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import service.UsersService;


@Controller
public class UsersController {
    
    @Autowired
    private UsersService usersService;
    
    @RequestMapping("/getuser")
    public @ResponseBody String getUser(
            @CookieValue(value = "username", defaultValue = "") String cookieUser,
            @RequestHeader(value = "user", defaultValue = "") String user,
            @RequestHeader(value = "lastId", defaultValue = "-1") long lastId){
        return usersService.getUserByJson(user, lastId, 21, cookieUser);
    }
    
    @RequestMapping("/getusers")
    public @ResponseBody String getUsers(
            @CookieValue(value = "username", defaultValue = "") String cookieUser,
            @RequestParam(value = "search", defaultValue = "") String search, 
            @RequestParam(value = "city", defaultValue = "") String city,
            @RequestHeader(value = "sex", defaultValue = "BOTH") String sex,
            @RequestHeader(value = "ageFrom", defaultValue = "0") int ageFrom,
            @RequestHeader(value = "ageTo", defaultValue = "150") int ageTo,
            @RequestHeader(value = "lastLogin", defaultValue = "") String lastLogin){
        search = search.replace("!:.", "&");
        city = city.replace("!:.", "&");
        return usersService.getUsersByJson(search, city, SexEnum.valueOf(sex), ageFrom, ageTo, lastLogin, 21, cookieUser);
    }
    
    @RequestMapping("/fastsearch")
    public @ResponseBody String getUsersByFastSearch(
            @CookieValue(value = "username", defaultValue = "") String cookieUser,
            @RequestParam(value = "search", defaultValue = "") String search, 
            @RequestHeader(value = "next", defaultValue = "false") boolean next,
            @RequestHeader(value = "Login", defaultValue = "") String lastLogin){
        search = search.replace("!:.", "&");
        return usersService.getUsersByJson(search, lastLogin, next, 11);
    }
    
    @RequestMapping("/login")
    public @ResponseBody String logIn(
            @RequestParam(value = "username", defaultValue = "") String login, 
            @RequestParam(value = "password", defaultValue = "") String password,
            HttpServletResponse response){
        Boolean input = usersService.checkPassword(login, password);
        if (input) {
            response.addCookie(new Cookie("username", login));
            response.addCookie(new Cookie("useravatar", usersService.getUserAvatarId(login).toString()));
        }
        return input.toString();
    }
    
    @RequestMapping("/checkloginunique")
    public @ResponseBody String checkLoginUnique(
            @RequestParam(value = "username", defaultValue = "") String login){
        return usersService.checkLoginUnique(login).toString();
    }
    
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public @ResponseBody String signUp(
            @RequestHeader(value = "username", defaultValue = "") String login, 
            @RequestHeader(value = "password", defaultValue = "") String password,
            @RequestParam(value = "city", defaultValue = "") String city,
            @RequestHeader(value = "sex", defaultValue = "BOTH") String sexString,
            @RequestHeader(value = "day", defaultValue = "0") int day,
            @RequestHeader(value = "month", defaultValue = "0") int month,
            @RequestHeader(value = "year", defaultValue = "2000") int year,
            @RequestParam(value = "about", defaultValue = "") String about,
            HttpServletResponse response){
        city = city.replace("!:.", "&");
        about = about.replace("!:.", "&");
        byte sex = SexEnum.valueOf(sexString).getValue();
        Calendar birthday = new GregorianCalendar(year, month, day);
        Boolean success = usersService.addNewUser(login, password, sex, birthday, city, about);
        if (success){
            response.addCookie(new Cookie("username", login));
            response.addCookie(new Cookie("useravatar", "0"));
        }
        return success.toString();
    }
    
}
