package hibernate.DAO.Impl;

import hibernate.DAO.PhotoLikeDAO;
import hibernate.logic.PhotoLike;
import hibernate.logic.User;
import hibernate.util.HibernateUtil;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

public class PhotoLikeDAOImpl implements PhotoLikeDAO {

    @Override
    public void addPhotoLike(PhotoLike photoLike) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query query = session.createSQLQuery(
                    "SELECT * "
                    + "FROM photolikes r "
                    + "WHERE  r.photo_id = :photo_id and r.user_from = :user_from "
            ).addEntity(PhotoLike.class).setLong("photo_id", photoLike.getPhotoId()).setString("user_from", photoLike.getUserFrom());
            boolean empty = (query.list().isEmpty());
            session.getTransaction().commit();
            if (empty) {
                session.beginTransaction();
                session.save(photoLike);
                session.getTransaction().commit();
            } else {
                PhotoLike like = (PhotoLike) query.list().get(0);
                if (like.getDeleted()) {
                    session.beginTransaction();
                    like.setDeleted(false);
                    like.setDate(photoLike.getDate());
                    session.update(like);
                    session.getTransaction().commit();
                }
            }
        } catch (ConstraintViolationException e) {
            System.out.println("\n!!!!!!!!!!!!!!!!!!!!!!!!!!!!\nERROR! FOREIGN KEY IS WRONG!\n!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
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
    public void updatePhotoLike(PhotoLike photoLike) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(photoLike);
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
    public PhotoLike getPhotoLikeById(Long id) throws SQLException {
        Session session = null;
        PhotoLike photoLike = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query query = session.createSQLQuery(
                    "SELECT * "
                    + "FROM photolikes l "
                    + "WHERE l.id = :id "
            ).addEntity(PhotoLike.class).setLong("id", id);

            if (!query.list().isEmpty()) {
                photoLike = (PhotoLike) query.list().get(0);
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
        return photoLike;
    }

    @Override
    public PhotoLike getPhotoLike(Long photo_id, User user_from) throws SQLException {
        Session session = null;
        PhotoLike photoLike = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query query = session.createSQLQuery(
                    "SELECT * "
                    + "FROM photolikes r "
                    + "WHERE r.photo_id = :photo_id and r.user_from = :user_from"
            ).addEntity(PhotoLike.class).setLong("photo_id", photo_id)
                    .setString("user_from", user_from.getUsername());
            if (!query.list().isEmpty()) {
                photoLike = (PhotoLike) query.list().get(0);
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
        return photoLike;
    }

    @Override
    public PhotoLike getPhotoLike(Long photo_id, String userLogin_from) throws SQLException {
        Session session = null;
        PhotoLike photoLike = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query query = session.createSQLQuery(
                    "SELECT * "
                    + "FROM photolikes r "
                    + "WHERE r.photo_id = :photo_id and r.user_from = :user_from"
            ).addEntity(PhotoLike.class).setLong("photo_id", photo_id).setString("user_from", userLogin_from);
            if (!query.list().isEmpty()) {
                photoLike = (PhotoLike) query.list().get(0);
            }
            session.getTransaction().commit();
        } catch (NullPointerException e) {
            System.out.println("\n!!!!!!!!!!!!!!!!!!!!!!!!!!!!\nERROR! FOREIGN KEY IS WRONG!\n!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
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
        return photoLike;
    }

    @Override
    public List<PhotoLike> getAllPhotoLikes() throws SQLException {
        Session session = null;
        List<PhotoLike> photoLikes = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            photoLikes = session.createCriteria(PhotoLike.class).list();
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
        return photoLikes;
    }

    @Override
    public List<PhotoLike> getAllPhotoLikes(User user, boolean withDeleted) throws SQLException {
        Session session = null;
        List<PhotoLike> photoLikes = new ArrayList<>();
        if (user == null) {
            return photoLikes;
        }
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query query = session.createSQLQuery(
                    "SELECT * "
                    + "FROM photolikes l "
                    + "INNER JOIN photodescriptions d "
                    + "ON l.photo_id = d.id "
                    + "WHERE l.user_from = :login and ((l.deleted = '0')or(l.deleted = :deleted )) "
                    + "and ((d.deleted = '0')or(d.deleted = :deleted )) "
            ).addEntity(PhotoLike.class).setString("login", user.getUsername()).setBoolean("deleted", withDeleted);

            if (!query.list().isEmpty()) {
                photoLikes = (List<PhotoLike>) query.list();
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
        return photoLikes;
    }

    @Override
    public List<PhotoLike> getAllPhotoLikes(String user, boolean withDeleted) throws SQLException {
        Session session = null;
        List<PhotoLike> photoLikes = new ArrayList<>();
        if (user == null) {
            return photoLikes;
        }
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query query = session.createSQLQuery(
                    "SELECT * "
                    + "FROM photolikes l "
                    + "INNER JOIN photodescriptions d "
                    + "ON l.photo_id = d.id "
                    + "WHERE l.user_from = :login "
                    + "and ((l.deleted = '0')or(l.deleted = :deleted )) and ((d.deleted = '0')or(d.deleted = :deleted )) "
            ).addEntity(PhotoLike.class).setString("login", user).setBoolean("deleted", withDeleted);

            if (!query.list().isEmpty()) {
                photoLikes = (List<PhotoLike>) query.list();
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
        return photoLikes;
    }

    @Override
    public List<PhotoLike> getAllPhotoLikes(Long photo_id, boolean withDeleted) throws SQLException {
        Session session = null;
        List<PhotoLike> photoLikes = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query query = session.createSQLQuery(
                    "SELECT * "
                    + "FROM photolikes l "
                    + "INNER JOIN photodescriptions d "
                    + "ON l.photo_id = d.id "
                    + "WHERE l.photo_id = :photo_id "
                    + "and ((l.deleted = '0')or(l.deleted = :deleted )) and ((d.deleted = '0')or(d.deleted = :deleted ))")
                    .addEntity(PhotoLike.class).setLong("photo_id", photo_id).setBoolean("deleted", withDeleted);

            if (!query.list().isEmpty()) {
                photoLikes = (List<PhotoLike>) query.list();
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
        return photoLikes;
    }

    @Override
    public List<PhotoLike> getAllPhotoLikesAfterLogin(Long photo_id, String login, long count, boolean withDeleted) throws SQLException {
        Session session = null;
        List<PhotoLike> photoLikes = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query query = session.createSQLQuery(
                    "SELECT * "
                    + "FROM photolikes l "
                    + "INNER JOIN photodescriptions d "
                    + "ON l.photo_id = d.id "
                    + "WHERE l.photo_id = :photo_id and l.user_from > :login "
                    + "and ((l.deleted = '0')or(l.deleted = :deleted )) and ((d.deleted = '0')or(d.deleted = :deleted ))"
                    + "ORDER BY l.user_from LIMIT :count ")
                    .addEntity(PhotoLike.class).setLong("photo_id", photo_id).setString("login", login).setLong("count", count).setBoolean("deleted", withDeleted);

            if (!query.list().isEmpty()) {
                photoLikes = (List<PhotoLike>) query.list();
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
        return photoLikes;
    }

    @Override
    public List<PhotoLike> getAllPhotoLikesBeforeLogin(Long photo_id, String login, long count, boolean withDeleted) throws SQLException {
        Session session = null;
        List<PhotoLike> photoLikes = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query query = session.createSQLQuery(
                    "SELECT r.id, r.photo_id, r.user_from, r.date, r.deleted "
                    + "FROM (SELECT l.id, l.photo_id, l.user_from, l.date, l.deleted "
                    + " FROM photolikes l "
                    + " INNER JOIN photodescriptions d "
                    + " ON l.photo_id = d.id "
                    + " WHERE l.photo_id = :photo_id and l.user_from < :login "
                    + " and ((l.deleted = '0')or(l.deleted = :deleted )) and ((d.deleted = '0')or(d.deleted = :deleted ))"
                    + " ORDER BY l.user_from DESC "
                    + " LIMIT :count) r "
                    + "ORDER BY r.user_from "
            ).addEntity(PhotoLike.class).setLong("photo_id", photo_id).setString("login", login)
                    .setLong("count", count).setBoolean("deleted", withDeleted);

            if (!query.list().isEmpty()) {
                photoLikes = (List<PhotoLike>) query.list();
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
        return photoLikes;
    }

    @Override
    public void deletePhotoLikeById(Long id) throws SQLException {
        Session session = null;
        PhotoLike photoLike = getPhotoLikeById(id);
        if (photoLike == null) {
            return;
        }
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(photoLike);
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
    public void setDeletedPhotoLikeById(Long id) throws SQLException {
        Session session = null;
        PhotoLike photoLike = getPhotoLikeById(id);
        if (photoLike == null) {
            return;
        }
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            photoLike.setDeleted(true);
            session.update(photoLike);
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
    public void deletePhotoLike(Long photo_id, User user_from) throws SQLException {
        Session session = null;
        PhotoLike photoLike = getPhotoLike(photo_id, user_from);
        if (photoLike == null) {
            return;
        }
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(photoLike);
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
    public void deletePhotoLike(Long photo_id, String user_from) throws SQLException {
        Session session = null;
        PhotoLike photoLike = getPhotoLike(photo_id, user_from);
        if (photoLike == null) {
            return;
        }
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(photoLike);
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
    public void setDeletedPhotoLike(Long photo_id, String user_from) throws SQLException {
        Session session = null;
        PhotoLike photoLike = getPhotoLike(photo_id, user_from);
        if (photoLike == null) {
            return;
        }
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            photoLike.setDeleted(true);
            session.update(photoLike);
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
    public void deleteAllPhotoLikes(User user) throws SQLException {
        Session session = null;
        List<PhotoLike> photoLikes = getAllPhotoLikes(user, true);
        if (photoLikes.isEmpty()) {
            return;
        }
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            for (PhotoLike photoLike : photoLikes) {
                session.delete(photoLike);
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
    public void deleteAllPhotoLikes(String user) throws SQLException {
        Session session = null;
        List<PhotoLike> photoLikes = getAllPhotoLikes(user, true);
        if (photoLikes.isEmpty()) {
            return;
        }
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            for (PhotoLike photoLike : photoLikes) {
                session.delete(photoLike);
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
    public void deleteAllPhotoLikes(Long photo_id) throws SQLException {
        Session session = null;
        List<PhotoLike> photoLikes = getAllPhotoLikes(photo_id, true);
        if (photoLikes.isEmpty()) {
            return;
        }
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            for (PhotoLike photoLike : photoLikes) {
                session.delete(photoLike);
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
    public void setDeletedAllPhotoLikes(User user) throws SQLException {
        Session session = null;
        List<PhotoLike> photoLikes = getAllPhotoLikes(user, false);
        if (photoLikes.isEmpty()) {
            return;
        }
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            for (PhotoLike m : photoLikes) {
                m.setDeleted(true);
                session.update(m);
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
    public void setDeletedAllPhotoLikes(String user) throws SQLException {
        Session session = null;
        List<PhotoLike> photoLikes = getAllPhotoLikes(user, false);
        if (photoLikes.isEmpty()) {
            return;
        }
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            for (PhotoLike m : photoLikes) {
                m.setDeleted(true);
                session.update(m);
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
    public void setDeletedAllPhotoLikes(Long photo_id) throws SQLException {
        Session session = null;
        List<PhotoLike> photoLikes = getAllPhotoLikes(photo_id, false);
        if (photoLikes.isEmpty()) {
            return;
        }
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            for (PhotoLike m : photoLikes) {
                m.setDeleted(true);
                session.update(m);
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
    public void deleteAllPhotoLikes() throws SQLException {
        Session session = null;
        try {
            List<PhotoLike> photoLikes = this.getAllPhotoLikes();
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            for (PhotoLike photoLike : photoLikes) {
                session.delete(photoLike);
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
