package hibernate.DAO;


import hibernate.logic.UserRole;
import java.sql.SQLException;
import java.util.Set;
         
         
public interface UserRoleDAO {
    public void addUserRole(UserRole userRole) throws SQLException;
    public void updateUserRole(UserRole userRole) throws SQLException;
    public UserRole getUserRole(int userRoleId) throws SQLException;
    public Set<UserRole> getUserRoles(String user) throws SQLException;
    public void deleteUserRole(int userRoleId) throws SQLException; 
    public void deleteUserRoles(String user) throws SQLException; 
    
}
