package hibernate.DAO.Impl;

import hibernate.DAO.UserRoleDAO;
import hibernate.logic.UserRole;
import hibernate.util.Factory;
import hibernate.util.HibernateUtil;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

public class UserRoleDAOImpl implements UserRoleDAO {

    @Override
    public void addUserRole(UserRole userRole) throws SQLException {
        Session session = null;
        Set<UserRole> currentUserRoles = Factory.getInstance().getUserRoleDAO().getUserRoles(userRole.getUser().getUsername());
        if (currentUserRoles.contains(userRole)) {
            return;
        }
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(userRole);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.out.println(e.toString());
            StackTraceElement[] el = e.getStackTrace();
            for (StackTraceElement el1 : el) {
                System.out.println(el1);
            }
        } finally {
            if ((session != null) && (session.isOpen())) {
                session.close();
            }
        }
    }

    @Override
    public void updateUserRole(UserRole userRole) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(userRole);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.out.println(e.toString());
            StackTraceElement[] el = e.getStackTrace();
            for (StackTraceElement el1 : el) {
                System.out.println(el1);
            }
        } finally {
            if ((session != null) && (session.isOpen())) {
                session.close();
            }
        }
    }

    @Override
    public UserRole getUserRole(int userRoleId) throws SQLException {
        Session session = null;
        UserRole userRole = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query query = session.createSQLQuery(
                    "SELECT * "
                    + "FROM user_roles u "
                    + "WHERE u.user_role_id = :id "
            ).addEntity(UserRole.class).setInteger("id", userRoleId);
            if (!query.list().isEmpty()) {
                userRole = (UserRole) query.list().get(0);
            }
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.out.println(e.toString());
            StackTraceElement[] el = e.getStackTrace();
            for (StackTraceElement el1 : el) {
                System.out.println(el1);
            }
        } finally {
            if ((session != null) && (session.isOpen())) {
                session.close();
            }
        }
        return userRole;
    }

    @Override
    public Set<UserRole> getUserRoles(String user) throws SQLException {
        Session session = null;
        List<UserRole> userRoles = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query query = session.createSQLQuery(
                    "SELECT * "
                    + "FROM user_roles u "
                    + "WHERE u.username = :username "
            ).addEntity(UserRole.class).setString("username", user);
            if (!query.list().isEmpty()) {
                userRoles = (List<UserRole>) query.list();
            }
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.out.println(e.toString());
            StackTraceElement[] el = e.getStackTrace();
            for (StackTraceElement el1 : el) {
                System.out.println(el1);
            }
        } finally {
            if ((session != null) && (session.isOpen())) {
                session.close();
            }
        }
        return new HashSet<>(userRoles);
    }

    @Override
    public void deleteUserRole(int userRoleId) throws SQLException {
        Session session = null;
        UserRole userRole = getUserRole(userRoleId);
        if (userRole == null) {
            return;
        }
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(userRole);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.out.println(e.toString());
            StackTraceElement[] el = e.getStackTrace();
            for (StackTraceElement el1 : el) {
                System.out.println(el1);
            }
        } finally {
            if ((session != null) && (session.isOpen())) {
                session.close();
            }
        }
    }

    @Override
    public void deleteUserRoles(String user) throws SQLException {
        Session session = null;
        Set<UserRole> userRoles = getUserRoles(user);
        if (userRoles.isEmpty()) {
            return;
        }
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            for (UserRole userRole : userRoles) {
                session.delete(userRole);
            }
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.out.println(e.toString());
            StackTraceElement[] el = e.getStackTrace();
            for (StackTraceElement el1 : el) {
                System.out.println(el1);
            }
        } finally {
            if ((session != null) && (session.isOpen())) {
                session.close();
            }
        }
    }

}
