package hibernate.DAO.Impl;

import hibernate.DAO.MessageDAO;
import hibernate.logic.Message;
import hibernate.logic.User;
import hibernate.util.Factory;
import hibernate.util.HibernateUtil;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

public class MessageDAOImpl implements MessageDAO {

    @Override
    public void addMessage(Message message) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            message.setUser(message.getFrom_id());
            session.save(message);
            session.save(new Message(null, message.getDate(), message.getFrom_id(), message.getTo_id(), message.getValue(), message.getTo_id()));
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
    public void updateMessage(Message message) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(message);
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
    public Message getMessageById(Long id) throws SQLException {
        Session session = null;
        Message message = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query query = session.createSQLQuery(
                    "SELECT * "
                    + "FROM messages m "
                    + "WHERE m.id = :id"
            ).addEntity(Message.class).setLong("id", id);
            if (!query.list().isEmpty()) {
                message = (Message) query.list().get(0);
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
        return message;
    }

    @Override
    public void changeMessageUnread(Long id) throws SQLException {
        Session session = null;
        session = HibernateUtil.getSessionFactory().openSession();
        Message message = Factory.getInstance().getMessageDAO().getMessageById(id);
        if (message == null) {
            return;
        }
        try {
            session.beginTransaction();
            message.setUnread(false);
            session.update(message);
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
    public void changeAllUserToUserMessagesUnread(User user, User other) throws SQLException {
        Session session = null;
        List<Message> messages = new ArrayList<>();
        point:
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            String firstLogin = user.getUsername();
            String secondLogin = other.getUsername();
            Query query = session.createSQLQuery(
                    "SELECT * "
                    + "FROM Messages m "
                    + "WHERE ((m.from_id = :firstL AND m.to_id = :secondL) OR (m.from_id = :secondL AND m.to_id = :firstL)) "
                    + "AND (m.unread = '1')and(m.deleted = '0') "
            ).addEntity(Message.class).setString("firstL", firstLogin).setString("secondL", secondLogin);
            if (query.list().isEmpty()) {
                break point;
            }
            messages = (List<Message>) query.list();
            for (Message message : messages) {
                message.setUnread(false);
                session.update(message);
            }
            session.getTransaction().commit();
        } catch (NullPointerException e) {
            System.out.println("!!!!!USER EXISTED!!!!!");
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
    public void changeAllUserToUserMessagesUnread(String user, String other) throws SQLException {
        Session session = null;
        List<Message> messages = new ArrayList<>();
        point:
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query query = session.createSQLQuery(
                    "SELECT * "
                    + "FROM Messages m "
                    + "WHERE ((m.from_id = :firstL AND m.to_id = :secondL) OR (m.from_id = :secondL AND m.to_id = :firstL)) "
                    + "AND (m.unread = '1')and(m.deleted = '0') "
            ).addEntity(Message.class).setString("firstL", user).setString("secondL", other);
            if (query.list().isEmpty()) {
                break point;
            }
            messages = (List<Message>) query.list();
            for (Message message : messages) {
                message.setUnread(false);
                session.update(message);
            }
            session.getTransaction().commit();
        } catch (NullPointerException e) {
            System.out.println("!!!!!USER EXISTED!!!!!");
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
    public List<Message> getAllMessages() throws SQLException {
        Session session = null;
        List<Message> messages = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            messages = session.createCriteria(Message.class).list();
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
        return messages;
    }

    @Override
    public List<Message> getAllMessages(User user, boolean onlyUsers, boolean withDeleted) throws SQLException {
        Session session = null;
        List<Message> messages = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query query = session.createSQLQuery(
                    "select * "
                    + "from messages m "
                    + "WHERE ((m.from_id = :login) or (m.to_id = :login)) "
                    + ((onlyUsers) ? "AND (m.user = :login) " : " ")
                    + "AND ((m.deleted = '0') or (m.deleted = :deleted )) "
                    + "ORDER BY m.date DESC"
            ).addEntity(Message.class).setString("login", user.getUsername())
                    .setBoolean("deleted", withDeleted);

            if ((query != null) && (!query.list().isEmpty())) {
                messages = (List<Message>) query.list();
            }
            session.getTransaction().commit();
        } catch (NullPointerException e) {
            System.out.println("!!!!!NullPointerException!!!!!");
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
        return messages;
    }

    @Override
    public List<Message> getAllMessages(String user, boolean onlyUsers, boolean withDeleted) throws SQLException {
        Session session = null;
        List<Message> messages = new ArrayList<>();
        if (user == null) {
            return messages;
        }
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query query = session.createSQLQuery(
                    "SELECT * "
                    + "FROM messages m "
                    + "WHERE ((m.from_id = :login) OR (m.to_id = :login)) "
                    + ((onlyUsers) ? "AND (m.user = :login) " : " ")
                    + "AND ((m.deleted = '0') OR (m.deleted = :deleted )) "
                    + "ORDER BY m.date DESC"
            ).addEntity(Message.class).setString("login", user).setBoolean("deleted", withDeleted);

            if ((query != null) && (!query.list().isEmpty())) {
                messages = (List<Message>) query.list();
            }
            session.getTransaction().commit();
        } catch (NullPointerException e) {
            System.out.println("!!!!!NullPointerException!!!!!");
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
        return messages;
    }

    @Override
    public List<Message> getAllUserToUserMessages(User firstUser, User secondUser, boolean withDeleted) throws SQLException {
        Session session = null;
        List<Message> messages = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            String firstLogin = firstUser.getUsername();
            String secondLogin = secondUser.getUsername();
            Query query = session.createSQLQuery(
                    "SELECT * "
                    + "FROM Messages m "
                    + "WHERE ((m.from_id = :firstL AND m.to_id = :secondL) OR (m.from_id = :secondL AND m.to_id = :firstL)) "
                    + "AND (m.user = :firstL) AND ((m.deleted = '0') OR (m.deleted = :deleted )) "
                    + "ORDER BY m.date DESC"
            ).addEntity(Message.class).setString("firstL", firstLogin)
                    .setString("secondL", secondLogin).setBoolean("deleted", withDeleted);

            if ((query != null) && (!query.list().isEmpty())) {
                messages = (List<Message>) query.list();
            }
            session.getTransaction().commit();
        } catch (NullPointerException e) {
            System.out.println("!!!!!USER EXISTED!!!!!");
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
        return messages;
    }

    @Override
    public List<Message> getAllUserToUserMessages(String firstUser, String secondUser, boolean withDeleted) throws SQLException {
        Session session = null;
        List<Message> messages = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query query = session.createSQLQuery(
                    "SELECT * "
                    + "FROM Messages m "
                    + "WHERE ((m.from_id = :firstL AND m.to_id = :secondL) OR (m.from_id = :secondL AND m.to_id = :firstL)) "
                    + "AND (m.user = :firstL) AND ((m.deleted = '0') OR (m.deleted = :deleted )) "
                    + "ORDER BY m.date DESC"
            ).addEntity(Message.class).setString("firstL", firstUser)
                    .setString("secondL", secondUser).setBoolean("deleted", withDeleted);

            if ((query != null) && (!query.list().isEmpty())) {
                messages = (List<Message>) query.list();
            }
            session.getTransaction().commit();
        } catch (NullPointerException e) {
            System.out.println("!!!!!USER EXISTED!!!!!");
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
        return messages;
    }

    @Override
    public List<Message> getUserToUserMessagesBeforeId(User firstUser, User secondUser, long id, long count, boolean withDeleted) throws SQLException {
        Session session = null;
        List<Message> messages = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            String firstLogin = firstUser.getUsername();
            String secondLogin = secondUser.getUsername();
            if (id == -1) {
                Query query = session.createSQLQuery(
                        "SELECT * "
                        + "FROM Messages m "
                        + "WHERE ((m.from_id = :firstL AND m.to_id = :secondL) OR (m.from_id = :secondL AND m.to_id = :firstL)) "
                        + "AND (m.user = :firstL) AND ((m.deleted = '0') OR (m.deleted = :deleted )) "
                        + "ORDER BY m.date DESC LIMIT :count "
                ).addEntity(Message.class).setString("firstL", firstLogin)
                        .setString("secondL", secondLogin).setLong("count", count).setBoolean("deleted", withDeleted);

                if ((query != null) && (!query.list().isEmpty())) {
                    messages = (List<Message>) query.list();
                }
            } else {
                Query query = session.createSQLQuery(
                        "SELECT * "
                        + "FROM Messages m "
                        + "WHERE ((m.from_id = :firstL AND m.to_id = :secondL) OR (m.from_id = :secondL AND m.to_id = :firstL)) "
                        + "AND (m.user = :firstL) AND (m.id < :id ) AND ((m.deleted = '0') OR (m.deleted = :deleted )) "
                        + "ORDER BY m.date DESC  LIMIT :count "
                ).addEntity(Message.class).setString("firstL", firstLogin).setString("secondL", secondLogin)
                        .setLong("id", id).setLong("count", count).setBoolean("deleted", withDeleted);

                if ((query != null) && (!query.list().isEmpty())) {
                    messages = (List<Message>) query.list();
                }
            }
            session.getTransaction().commit();
        } catch (NullPointerException e) {
            System.out.println("!!!!!USER EXISTED!!!!!");
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
        return messages;
    }

    @Override
    public List<Message> getUserToUserMessagesBeforeId(String firstUser, String secondUser, long id, long count, boolean withDeleted) throws SQLException {
        Session session = null;
        List<Message> messages = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            if (id == -1) {
                Query query = session.createSQLQuery(
                        "SELECT * "
                        + "FROM Messages m "
                        + "WHERE ((m.from_id = :firstL AND m.to_id = :secondL) OR (m.from_id = :secondL AND m.to_id = :firstL)) "
                        + "AND (m.user = :firstL) AND ((m.deleted = '0') OR (m.deleted = :deleted )) "
                        + "ORDER BY m.date DESC  LIMIT :count "
                ).addEntity(Message.class).setString("firstL", firstUser).setString("secondL", secondUser)
                        .setLong("count", count).setBoolean("deleted", withDeleted);

                if ((query != null) && (!query.list().isEmpty())) {
                    messages = (List<Message>) query.list();
                }
            } else {
                Query query = session.createSQLQuery(
                        "SELECT * "
                        + "FROM Messages m "
                        + "WHERE ((m.from_id = :firstL AND m.to_id = :secondL) OR (m.from_id = :secondL AND m.to_id = :firstL)) "
                        + "AND (m.user = :firstL) AND (m.id < :id ) AND ((m.deleted = '0') OR (m.deleted = :deleted )) "
                        + "ORDER BY m.date DESC  LIMIT :count "
                ).addEntity(Message.class).setString("firstL", firstUser).setString("secondL", secondUser)
                        .setLong("id", id).setLong("count", count).setBoolean("deleted", withDeleted);

                if ((query != null) && (!query.list().isEmpty())) {
                    messages = (List<Message>) query.list();
                }
            }
            session.getTransaction().commit();
        } catch (NullPointerException e) {
            System.out.println("!!!!!USER EXISTED!!!!!");
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
        return messages;
    }

    @Override
    public List<Message> getAllUserToUserMessagesFromId(User firstUser, User secondUser, long lastId, boolean withDeleted) throws SQLException {
        Session session = null;
        List<Message> messages = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            String firstLogin = firstUser.getUsername();
            String secondLogin = secondUser.getUsername();
            Query query = session.createSQLQuery(
                    "SELECT * "
                    + "FROM Messages m "
                    + "WHERE ((m.from_id = :firstL AND m.to_id = :secondL) OR (m.from_id = :secondL AND m.to_id = :firstL)) "
                    + "AND (m.user = :firstL) AND (m.id > :lastId) AND ((m.deleted = '0') OR (m.deleted = :deleted )) "
                    + "ORDER BY m.date DESC "
            ).addEntity(Message.class).setString("firstL", firstLogin).setString("secondL", secondLogin)
                    .setLong("lastId", lastId).setBoolean("deleted", withDeleted);

            if ((query != null) && (!query.list().isEmpty())) {
                messages = (List<Message>) query.list();
            }
            session.getTransaction().commit();
        } catch (NullPointerException e) {
            System.out.println("!!!!!USER EXISTED!!!!!");
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
        return messages;
    }

    @Override
    public List<Message> getAllUserToUserMessagesFromId(String firstUser, String secondUser, long lastId, boolean withDeleted) throws SQLException {
        Session session = null;
        List<Message> messages = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query query = session.createSQLQuery(
                    "SELECT * "
                    + "FROM Messages m "
                    + "WHERE ((m.from_id = :firstL AND m.to_id = :secondL) OR (m.from_id = :secondL AND m.to_id = :firstL)) "
                    + "AND (m.user = :firstL) AND (m.id > :lastId) AND ((m.deleted = '0') OR (m.deleted = :deleted )) "
                    + "ORDER BY m.date DESC "
            ).addEntity(Message.class).setString("firstL", firstUser).setString("secondL", secondUser)
                    .setLong("lastId", lastId).setBoolean("deleted", withDeleted);

            if ((query != null) && (!query.list().isEmpty())) {
                messages = (List<Message>) query.list();
            }
            session.getTransaction().commit();
        } catch (NullPointerException e) {
            System.out.println("!!!!!USER EXISTED!!!!!");
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
        return messages;
    }

    @Override
    public Long getLastUserToUserMessageId(User firstUser, User secondUser) throws SQLException {
        Session session = null;
        Long id = (long) -1;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            String firstLogin = firstUser.getUsername();
            String secondLogin = secondUser.getUsername();
            Query query = session.createSQLQuery(
                    "SELECT Max(m.id) "
                    + "FROM Messages m "
                    + "WHERE ((m.from_id = :firstL AND m.to_id = :secondL) OR (m.from_id = :secondL AND m.to_id = :firstL)) "
                    + "AND (m.user = :firstL) AND (m.deleted = '0') "
            ).setString("firstL", firstLogin).setString("secondL", secondLogin);

            if ((query != null) && (!query.list().isEmpty())) {
                BigInteger num = (BigInteger) query.list().get(0);
                id = num.longValue();
            }
            session.getTransaction().commit();
        } catch (NullPointerException e) {
            System.out.println("!!!!!USER EXISTED!!!!!");
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
        return id;
    }

    @Override
    public Long getLastUserToUserMessageId(String firstUser, String secondUser) throws SQLException {
        Session session = null;
        Long id = (long) -1;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query query = session.createSQLQuery(
                    "SELECT Max(m.id) "
                    + "FROM Messages m "
                    + "WHERE ((m.from_id = :firstL AND m.to_id = :secondL) OR (m.from_id = :secondL AND m.to_id = :firstL)) "
                    + "AND (m.user = :firstL) AND (m.deleted = '0') "
            ).setString("firstL", firstUser).setString("secondL", secondUser);

            if ((query != null) && (!query.list().isEmpty())) {
                BigInteger num = (BigInteger) query.list().get(0);
                id = num.longValue();
            }
            session.getTransaction().commit();
        } catch (NullPointerException e) {
            System.out.println("!!!!!USER EXISTED!!!!!");
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
        return id;
    }

    @Override
    public List<Message> getDialogsOfAllUsersWhoTalkedWith(User user, boolean withDeleted) throws SQLException {
        Session session = null;
        List<Message> messages = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            String login = user.getUsername();
            Query query = session.createSQLQuery(
                    "select r.id, r.date, "
                    + " case "
                    + "  when r.sender = 0 then "
                    + "  (select :login) "
                    + "  when r.sender = 1 then "
                    + "  (select r.other) "
                    + " end from_id, "
                    + " case "
                    + "  when r.sender = 0 then "
                    + "   (select r.other) "
                    + "  when r.sender = 1 then "
                    + "   (select :login) "
                    + " end to_id, r.value, r.user , r.unread, r.deleted "
                    + " from (select m.id, m.date, "
                    + "  case "
                    + "   when m.from_id = :login then "
                    + "    (select m.to_id ) "
                    + "   when m.to_id = :login then "
                    + "    (select m.from_id ) "
                    + "  end other, "
                    + "  case "
                    + "   when m.from_id = :login then "
                    + "    (select 0 ) "
                    + "   when m.to_id = :login then "
                    + "    (select 1 ) "
                    + "   end sender,  m.value, m.user , m.unread , m.deleted "
                    + "   from (select * from messages order by date desc) m "
                    + "    where ((m.from_id = :login) or (m.to_id = :login)) and (m.user = :login) "
                    + "     and ((m.deleted = '0') or (m.deleted = :deleted )) "
                    + "    group by other "
                    + "    order by date desc) r;"
            ).addEntity(Message.class).setString("login", login).setBoolean("deleted", withDeleted);

            if ((query != null) && (!query.list().isEmpty())) {
                messages = (List<Message>) query.list();
            }
            session.getTransaction().commit();
        } catch (NullPointerException e) {
            System.out.println("\n!!!!!!!!!!!!!!!!!!!!\n!!!!!USER EXIST!!!!!\n!!!!!!!!!!!!!!!!!!!!\n");
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
        return messages;
    }

    @Override
    public List<Message> getDialogsOfAllUsersWhoTalkedWith(String user, boolean withDeleted) throws SQLException {
        Session session = null;
        List<Message> messages = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query query = session.createSQLQuery(
                    "select r.id, r.date, "
                    + " case "
                    + "  when r.sender = 0 then "
                    + "  (select :login) "
                    + "  when r.sender = 1 then "
                    + "  (select r.other) "
                    + " end from_id, "
                    + " case "
                    + "  when r.sender = 0 then "
                    + "   (select r.other) "
                    + "  when r.sender = 1 then "
                    + "   (select :login) "
                    + " end to_id, r.value, r.user , r.unread, r.deleted "
                    + " from (select m.id, m.date, "
                    + "  case "
                    + "   when m.from_id = :login then "
                    + "    (select m.to_id ) "
                    + "   when m.to_id = :login then "
                    + "    (select m.from_id ) "
                    + "  end other, "
                    + "  case "
                    + "   when m.from_id = :login then "
                    + "    (select 0 ) "
                    + "   when m.to_id = :login then "
                    + "    (select 1 ) "
                    + "   end sender,  m.value, m.user , m.unread , m.deleted "
                    + "   from (select * from messages order by date desc) m "
                    + "    where ((m.from_id = :login) or (m.to_id = :login)) and (m.user = :login) "
                    + "     and ((m.deleted = '0') or (m.deleted = :deleted )) "
                    + "    group by other "
                    + "    order by date desc) r;"
            ).addEntity(Message.class).setString("login", user).setBoolean("deleted", withDeleted);

            if ((query != null) && (!query.list().isEmpty())) {
                messages = (List<Message>) query.list();
            }
            session.getTransaction().commit();
        } catch (NullPointerException e) {
            System.out.println("\n!!!!!!!!!!!!!!!!!!!!\n!!!!!USER EXIST!!!!!\n!!!!!!!!!!!!!!!!!!!!\n");
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
        return messages;
    }

    @Override
    public List<Message> getDialogsOfAllUsersWhoTalkedWithBeforeId(User user, long id, long count, boolean withDeleted) throws SQLException {
        Session session = null;
        List<Message> messages = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            String login = user.getUsername();
            Query query;
            if (id != -1) {
                query = session.createSQLQuery(
                        "select r.id, r.date, "
                        + " case "
                        + "  when r.sender = 0 then "
                        + "   (select :login) "
                        + "  when r.sender = 1 then "
                        + "   (select r.other) "
                        + "  end from_id, "
                        + " case "
                        + "  when r.sender = 0 then "
                        + "   (select r.other) "
                        + "  when r.sender = 1 then "
                        + "   (select :login) "
                        + " end to_id, r.value, r.user , r.unread, r.deleted "
                        + " from (select m.id, m.date, "
                        + "  case "
                        + "   when m.from_id = :login then "
                        + "    (select m.to_id ) "
                        + "   when m.to_id = :login then "
                        + "    (select m.from_id ) "
                        + "  end other, "
                        + "  case "
                        + "   when m.from_id = :login then "
                        + "    (select 0 ) "
                        + "   when m.to_id = :login then "
                        + "    (select 1 ) "
                        + "  end sender,  m.value, m.user , m.unread, m.deleted "
                        + "  from (select * from messages order by date desc) m "
                        + "  where ((m.from_id = :login) or (m.to_id = :login)) and (m.user = :login) "
                        + "   and ((m.deleted = '0') or (m.deleted = :deleted )) "
                        + "  group by other "
                        + "  order by date desc) r "
                        + " WHERE r.id < :id LIMIT :count "
                ).addEntity(Message.class).setString("login", login).setLong("id", id)
                        .setLong("count", count).setBoolean("deleted", withDeleted);
            } else {
                query = session.createSQLQuery(
                        "select r.id, r.date, "
                        + " case "
                        + "  when r.sender = 0 then "
                        + "   (select :login) "
                        + "  when r.sender = 1 then "
                        + "   (select r.other) "
                        + "  end from_id, "
                        + " case "
                        + "  when r.sender = 0 then "
                        + "   (select r.other) "
                        + "  when r.sender = 1 then "
                        + "   (select :login) "
                        + " end to_id, r.value, r.user , r.unread, r.deleted "
                        + " from (select m.id, m.date, "
                        + "  case "
                        + "   when m.from_id = :login then "
                        + "    (select m.to_id ) "
                        + "   when m.to_id = :login then "
                        + "    (select m.from_id ) "
                        + "  end other, "
                        + "  case "
                        + "   when m.from_id = :login then "
                        + "    (select 0 ) "
                        + "   when m.to_id = :login then "
                        + "    (select 1 ) "
                        + "  end sender,  m.value, m.user , m.unread, m.deleted "
                        + "  from (select * from messages order by date desc) m "
                        + "  where ((m.from_id = :login) or (m.to_id = :login)) and (m.user = :login) "
                        + "   and ((m.deleted = '0') or (m.deleted = :deleted )) "
                        + "  group by other "
                        + "  order by date desc) r "
                        + " LIMIT :count "
                ).addEntity(Message.class).setString("login", login).setLong("count", count)
                        .setBoolean("deleted", withDeleted);
            }

            if ((query != null) && (!query.list().isEmpty())) {
                messages = (List<Message>) query.list();
            }
            session.getTransaction().commit();
        } catch (NullPointerException e) {
            System.out.println("\n!!!!!!!!!!!!!!!!!!!!\n!!!!!USER EXIST!!!!!\n!!!!!!!!!!!!!!!!!!!!\n");
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
        return messages;
    }

    @Override
    public List<Message> getDialogsOfAllUsersWhoTalkedWithBeforeId(String user, long id, long count, boolean withDeleted) throws SQLException {
        Session session = null;
        List<Message> messages = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query query;
            if (id != -1) {
                query = session.createSQLQuery(
                        "select r.id, r.date, "
                        + " case "
                        + "  when r.sender = 0 then "
                        + "   (select :login) "
                        + "  when r.sender = 1 then "
                        + "   (select r.other) "
                        + "  end from_id, "
                        + " case "
                        + "  when r.sender = 0 then "
                        + "   (select r.other) "
                        + "  when r.sender = 1 then "
                        + "   (select :login) "
                        + " end to_id, r.value, r.user , r.unread, r.deleted "
                        + " from (select m.id, m.date, "
                        + "  case "
                        + "   when m.from_id = :login then "
                        + "    (select m.to_id ) "
                        + "   when m.to_id = :login then "
                        + "    (select m.from_id ) "
                        + "  end other, "
                        + "  case "
                        + "   when m.from_id = :login then "
                        + "    (select 0 ) "
                        + "   when m.to_id = :login then "
                        + "    (select 1 ) "
                        + "  end sender,  m.value, m.user , m.unread, m.deleted "
                        + "  from (select * from messages order by date desc) m "
                        + "  where ((m.from_id = :login) or (m.to_id = :login)) and (m.user = :login) "
                        + "   and ((m.deleted = '0') or (m.deleted = :deleted )) "
                        + "  group by other "
                        + "  order by date desc) r "
                        + " WHERE r.id < :id LIMIT :count "
                ).addEntity(Message.class).setString("login", user).setLong("id", id)
                        .setLong("count", count).setBoolean("deleted", withDeleted);
            } else {
                query = session.createSQLQuery(
                        "select r.id, r.date, "
                        + " case "
                        + "  when r.sender = 0 then "
                        + "   (select :login) "
                        + "  when r.sender = 1 then "
                        + "   (select r.other) "
                        + "  end from_id, "
                        + " case "
                        + "  when r.sender = 0 then "
                        + "   (select r.other) "
                        + "  when r.sender = 1 then "
                        + "   (select :login) "
                        + " end to_id, r.value, r.user , r.unread, r.deleted "
                        + " from (select m.id, m.date, "
                        + "  case "
                        + "   when m.from_id = :login then "
                        + "    (select m.to_id ) "
                        + "   when m.to_id = :login then "
                        + "    (select m.from_id ) "
                        + "  end other, "
                        + "  case "
                        + "   when m.from_id = :login then "
                        + "    (select 0 ) "
                        + "   when m.to_id = :login then "
                        + "    (select 1 ) "
                        + "  end sender,  m.value, m.user , m.unread, m.deleted "
                        + "  from (select * from messages order by date desc) m "
                        + "  where ((m.from_id = :login) or (m.to_id = :login)) and (m.user = :login) "
                        + "   and ((m.deleted = '0') or (m.deleted = :deleted )) "
                        + "  group by other "
                        + "  order by date desc) r "
                        + " limit :count "
                ).addEntity(Message.class).setString("login", user).setLong("count", count)
                        .setBoolean("deleted", withDeleted);
            }

            if ((query != null) && (!query.list().isEmpty())) {
                messages = (List<Message>) query.list();
            }
            session.getTransaction().commit();
        } catch (NullPointerException e) {
            System.out.println("\n!!!!!!!!!!!!!!!!!!!!\n!!!!!USER EXIST!!!!!\n!!!!!!!!!!!!!!!!!!!!\n");
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
        return messages;
    }

    @Override
    public Long getLastDialogsMessageId(User user) throws SQLException {
        Session session = null;
        Long id = (long) -1;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            String Login = user.getUsername();
            Query query = session.createSQLQuery(
                    "SELECT Max(m.id) "
                    + "FROM Messages m "
                    + "WHERE ((m.from_id = :login) OR (m.to_id = :login)) "
                    + "AND (m.user = :login)and(m.deleted = '0') "
            ).setString("login", Login);

            if ((query != null) && (!query.list().isEmpty())) {
                BigInteger num = (BigInteger) query.list().get(0);
                id = num.longValue();
            }
            session.getTransaction().commit();
        } catch (NullPointerException e) {
            System.out.println("!!!!!USER EXISTED!!!!!");
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
        return id;
    }

    @Override
    public Long getLastDialogsMessageId(String user) throws SQLException {
        Session session = null;
        Long id = (long) -1;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query query = session.createSQLQuery(
                    "SELECT Max(m.id) "
                    + "FROM Messages m "
                    + "WHERE ((m.from_id = :login) OR (m.to_id = :login)) "
                    + "AND (m.user = :login) AND (m.deleted = '0') "
            ).setString("login", user);

            if ((query != null) && (!query.list().isEmpty())) {
                BigInteger num = (BigInteger) query.list().get(0);
                id = num.longValue();
            }
            session.getTransaction().commit();
        } catch (NullPointerException e) {
            System.out.println("!!!!!USER EXISTED!!!!!");
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
        return id;
    }

    @Override
    public int getNumberOfUnreadDialogs(User user) throws SQLException {
        Session session = null;
        List<Message> messages = new ArrayList<>();
        int number = 0;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            String login = user.getUsername();
            Query query = session.createSQLQuery(
                    "select Count(*) "
                    + "from (select m.id, m.date, "
                    + " case "
                    + "  when m.from_id = :login then "
                    + "   (select m.to_id ) "
                    + "  when m.to_id = :login then "
                    + "   (select m.from_id ) "
                    + " end other, "
                    + " case "
                    + "  when m.from_id = :login then "
                    + "   (select 0 ) "
                    + "  when m.to_id = :login then "
                    + "   (select 1 ) "
                    + " end sender,  m.value, m.user , m.unread "
                    + " from (select * from messages order by date desc) m "
                    + " where (m.to_id = :login) and (m.user = :login) "
                    + "  and(m.unread = '1') and (m.deleted = '0') "
                    + " group by other "
                    + " order by date desc) r;").setString("login", login);

            if ((query != null) && (!query.list().isEmpty())) {
                BigInteger num = (BigInteger) query.list().get(0);
                number = num.intValue();
            }
            session.getTransaction().commit();
        } catch (NullPointerException e) {
            System.out.println("\n!!!!!!!!!!!!!!!!!!!!\n!!!!!USER EXIST!!!!!\n!!!!!!!!!!!!!!!!!!!!\n");
            System.out.println(e.toString());
            StackTraceElement[] el = e.getStackTrace();
            for (StackTraceElement el1 : el) {
                System.out.println(el1);
            }
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
        return number;
    }

    @Override
    public int getNumberOfUnreadDialogs(String user) throws SQLException {
        Session session = null;
        List<Message> messages = new ArrayList<>();
        int number = 0;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query query = session.createSQLQuery(
                    "select Count(*) "
                    + "from (select m.id, m.date, "
                    + " case "
                    + "  when m.from_id = :login then "
                    + "   (select m.to_id ) "
                    + "  when m.to_id = :login then "
                    + "   (select m.from_id ) "
                    + " end other, "
                    + " case "
                    + "  when m.from_id = :login then "
                    + "   (select 0 ) "
                    + "  when m.to_id = :login then "
                    + "   (select 1 ) "
                    + " end sender,  m.value, m.user , m.unread "
                    + " from (select * from messages order by date desc) m "
                    + " where (m.to_id = :login) and (m.user = :login) "
                    + "  and (m.unread = '1') and (m.deleted = '0') "
                    + " group by other "
                    + " order by date desc) r;").setString("login", user);

            if ((query != null) && (!query.list().isEmpty())) {
                BigInteger num = (BigInteger) query.list().get(0);
                number = num.intValue();
            }
            session.getTransaction().commit();
        } catch (NullPointerException e) {
            System.out.println("\n!!!!!!!!!!!!!!!!!!!!\n!!!!!USER EXIST!!!!!\n!!!!!!!!!!!!!!!!!!!!\n");
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
        return number;
    }

    @Override
    public void deleteMessageById(Long id) throws SQLException {
        Session session = null;
        Message message = getMessageById(id);
        if (message == null) {
            return;
        }
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(message);
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
    public void deleteAllMessages(User user) throws SQLException {
        Session session = null;
        List<Message> messages = getAllMessages(user, false, true);
        if (messages.isEmpty()) {
            return;
        }
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            for (Message message : messages) {
                session.delete(message);
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
    public void deleteAllMessages(String user) throws SQLException {
        Session session = null;
        List<Message> messages = getAllMessages(user, false, true);
        if (messages.isEmpty()) {
            return;
        }
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            for (Message message : messages) {
                session.delete(message);
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
    public void deleteDialog(User user, User other) throws SQLException {
        Session session = null;
        List<Message> messages = getAllUserToUserMessages(user, other, true);
        if (messages.isEmpty()) {
            return;
        }
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            for (Message message : messages) {
                session.delete(message);
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
    public void deleteDialog(String user, String other) throws SQLException {
        Session session = null;
        List<Message> messages = getAllUserToUserMessages(user, other, true);
        if (messages.isEmpty()) {
            return;
        }
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            for (Message message : messages) {
                session.delete(message);
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
    public void setDeletedMessageById(Long id) throws SQLException {
        Session session = null;
        Message message = getMessageById(id);
        if (message == null) {
            return;
        }
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            message.setDeleted(true);
            session.update(message);
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
    public void setDeletedAllMessages(User user) throws SQLException {
        Session session = null;
        List<Message> messages = getAllMessages(user, true, true);
        if (messages.isEmpty()) {
            return;
        }
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            for (Message m : messages) {
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
    public void setDeletedAllMessages(String user) throws SQLException {
        Session session = null;
        List<Message> messages = getAllMessages(user, true, true);
        if (messages.isEmpty()) {
            return;
        }
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            for (Message m : messages) {
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
    public void setDeletedDialog(User user, User other) throws SQLException {
        Session session = null;
        List<Message> messages = getAllUserToUserMessages(user, other, true);
        if (messages.isEmpty()) {
            return;
        }
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            for (Message m : messages) {
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
    public void setDeletedDialog(String user, String other) throws SQLException {
        Session session = null;
        List<Message> messages = getAllUserToUserMessages(user, other, true);
        if (messages.isEmpty()) {
            return;
        }
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            for (Message m : messages) {
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

}
