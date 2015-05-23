package hibernate.DAO.Impl;

import hibernate.DAO.UserDAO;
import hibernate.logic.SexEnum;
import hibernate.logic.User;
import hibernate.util.Factory;
import hibernate.util.HibernateUtil;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

public class UserDAOImpl implements UserDAO {

    @Override
    public void addUser(User user) throws SQLException {
        Session session = null;
        User currentUser = Factory.getInstance().getUserDAO().getUser(user.getUsername());
        if (currentUser != null) {
            return;
        }
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(user);
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
    public void updateUser(User user) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(user);
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
    public User getUser(String username) throws SQLException {
        Session session = null;
        User user = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query query = session.createSQLQuery(
                    "SELECT * "
                    + "FROM users u "
                    + "WHERE u.username = :username "
            ).addEntity(User.class).setString("username", username);
            if (!query.list().isEmpty()) {
                user = (User) query.list().get(0);
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
        return user;
    }

    @Override
    public void setUserAvatarByPhotoDescriptionId(User user, long photoId) throws SQLException {
        Session session = null;
        String userLogin = user.getUsername();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            User userUpdate = (User) session.get(User.class, user.getUsername());
            if (userUpdate != null) {
                userUpdate.setAvatar(photoId);
                session.update(userUpdate);
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

    @Override
    public void setUserAvatarByPhotoDescriptionId(String user, long photoId) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            User userUpdate = (User) session.get(User.class, user);
            if (userUpdate != null) {
                userUpdate.setAvatar(photoId);
                session.update(userUpdate);
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

    @Override
    public void setUserLastOnlineTime(User user, long time) throws SQLException {
        Session session = null;
        String userLogin = user.getUsername();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            User userUpdate = (User) session.get(User.class, user.getUsername());
            if (userUpdate != null) {
                userUpdate.setLastOnlineTime(time);
                session.update(userUpdate);
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

    @Override
    public void setUserLastOnlineTime(String user, long time) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            User userUpdate = (User) session.get(User.class, user);
            if (userUpdate != null) {
                userUpdate.setLastOnlineTime(time);
                session.update(userUpdate);
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

    @Override
    public List<User> getAllUsers() throws SQLException {
        Session session = null;
        List<User> users = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            users = session.createCriteria(User.class).list();
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
        return users;
    }

    @Override
    public List<User> getUsersBySearchAfterLogin(String search, SexEnum sex, int minAge, int maxAge, String city, String login, long count) throws SQLException {
        Session session = null;
        if (maxAge <= 0) {
            maxAge = 150;
        }
        if (minAge < 0) {
            minAge = 0;
        }
        List<User> users = new ArrayList<>();
        GregorianCalendar minDate = new GregorianCalendar();
        GregorianCalendar maxDate = new GregorianCalendar();
        minDate.roll(Calendar.YEAR, -(int) (maxAge + 1));
        maxDate.roll(Calendar.YEAR, -(int) minAge);
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query query;
            if (sex.getValue() == 0) {
                query = session.createSQLQuery(
                        "select * "
                        + "from users u "
                        + "WHERE u.username like :search and u.username <> 'Loader' "
                        + "and u.birthday >= :minDate and u.birthday <= :maxDate "
                        + "and u.city like :city and u.username > :username "
                        + "LIMIT :count "
                ).addEntity(User.class).setString("search", search + "%").setCalendar("minDate", minDate)
                        .setCalendar("maxDate", maxDate).setString("city", city + "%").setString("username", login)
                        .setLong("count", count);
            } else {
                query = session.createSQLQuery(
                        "select * "
                        + "from users u "
                        + "WHERE u.username like :search and u.username <> 'Loader' "
                        + "and u.sex = :sex and u.birthday >= :minDate and u.birthday <= :maxDate "
                        + "and u.city like :city and u.username > :login "
                        + "LIMIT :count "
                ).addEntity(User.class).setString("search", search + "%").setByte("sex", sex.getValue())
                        .setCalendar("minDate", minDate).setCalendar("maxDate", maxDate)
                        .setString("city", city + "%").setString("login", login).setLong("count", count);
            }
            if (!query.list().isEmpty()) {
                users = (List<User>) query.list();
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
        return users;
    }

    @Override
    public List<User> getUsersBySearchAfterLogin(String search, String login, long count) throws SQLException {
        Session session = null;
        List<User> users = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query query = session.createSQLQuery(
                    "select * "
                    + "from users u "
                    + "WHERE u.username like :search and u.username <> 'Loader' "
                    + "and u.username > :login "
                    + "LIMIT :count "
            ).addEntity(User.class).setString("search", search + "%").setString("login", login)
                    .setLong("count", count);

            if (!query.list().isEmpty()) {
                users = (List<User>) query.list();
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
        return users;
    }

    @Override
    public List<User> getUsersBySearchBeforeLogin(String search, String login, long count) throws SQLException {
        Session session = null;
        List<User> users = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query query = session.createSQLQuery(
                    "SELECT username, password, sex, birthday, city, about, avatar, last_online_time, status "
                    + "FROM (select * "
                    + "   FROM users u "
                    + "   WHERE u.username like :search and u.username <> 'Loader' and u.username < :login "
                    + "   ORDER BY u.username DESC LIMIT :count ) r "
                    + "ORDER BY username  "
            ).addEntity(User.class).setString("search", search + "%").setString("login", login)
                    .setLong("count", count);

            if (!query.list().isEmpty()) {
                users = (List<User>) query.list();
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
        return users;
    }

    @Override
    public List<User> getUsersAfterLogin(String login, long count) throws SQLException {
        Session session = null;
        List<User> users = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query query = session.createSQLQuery(
                    "select * "
                    + "from users u "
                    + "WHERE u.username > :login and u.username <> 'Loader' "
                    + "LIMIT :count "
            ).addEntity(User.class).setString("login", login).setLong("count", count);

            if (!query.list().isEmpty()) {
                users = (List<User>) query.list();
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
        return users;
    }

    @Override
    public void deleteUserByLogin(String login) throws SQLException {
        Session session = null;
        User user = getUser(login);
        if (user == null) {
            return;
        }
        Factory factory = Factory.getInstance();
        factory.getUserRoleDAO().deleteUserRoles(login);
        factory.getMessageDAO().deleteAllMessages(user);
        factory.getPhotoDescriptionDAO().deleteAllPhotoDescriptions(user);
        factory.getPhotoLikeDAO().deleteAllPhotoLikes(user);
        factory.getPhotoCommentDAO().deleteAllPhotoComments(user);
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(user);
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
