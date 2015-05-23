package service.Impl;

import hibernate.DAO.UserDAO;
import hibernate.logic.UserRole;
import hibernate.logic.UserStatusEnum;
import hibernate.util.Factory;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsService")
public class WordsEasyUserDetailsService implements UserDetailsService {

    @Transactional
    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {

        hibernate.logic.User user = null;
        try {
            user = Factory.getInstance().getUserDAO().getUser(username);
        } catch (SQLException ex) {
            Logger.getLogger(WordsEasyUserDetailsService.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<GrantedAuthority> authorities = null;
        if (user != null) {
            authorities = buildUserAuthority(user.getUserRole());
        }
        return buildUserForAuthentication(user, authorities);

    }

    private User buildUserForAuthentication(hibernate.logic.User user, List<GrantedAuthority> authorities) {
        return new User(user.getUsername(), user.getPassword(), 
                (user.getStatus() == UserStatusEnum.NORMAL.getValue()), true, true, true, authorities);
    }

    private List<GrantedAuthority> buildUserAuthority(Set<UserRole> userRoles) {

        Set<GrantedAuthority> setAuths = new HashSet<>();

        for (UserRole userRole : userRoles) {
            setAuths.add(new SimpleGrantedAuthority(userRole.getRole()));
        }

        List<GrantedAuthority> Result = new ArrayList<>(setAuths);

        return Result;
    }

}
