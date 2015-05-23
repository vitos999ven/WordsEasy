package hibernate.DAO.Impl;


import hibernate.DAO.PhotoCommentDAO;
import hibernate.logic.PhotoComment;
import hibernate.logic.User;
import hibernate.util.HibernateUtil;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;


public class PhotoCommentDAOImpl implements PhotoCommentDAO{
    
    @Override
    public void addPhotoComment(PhotoComment photoComment) throws SQLException{
        Session session = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(photoComment);
            session.getTransaction().commit();   
        }catch(ConstraintViolationException e){
            System.out.println("\n!!!!!!!!!!!!!!!!!!!!!!!!!!!!\nERROR! FOREIGN KEY IS WRONG!\n!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        }catch(HibernateException e){
            System.out.println(e.toString());
            StackTraceElement[] el = e.getStackTrace();
                for (StackTraceElement el1 : el) {
                    System.out.println(el1);
                }
        }finally{
            if((session != null)&&(session.isOpen())) session.close();
        }
   }
   
   
    @Override
    public void updatePhotoComment(PhotoComment photoComment) throws SQLException{
        Session session = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(photoComment);
            session.getTransaction().commit();
        }catch(HibernateException e){
            System.out.println(e.toString());
            StackTraceElement[] el = e.getStackTrace();
                for (StackTraceElement el1 : el) {
                    System.out.println(el1);
                }  
        }finally{
            if((session != null)&&(session.isOpen())) session.close();
        }
    }
  
   
    @Override
    public PhotoComment getPhotoCommentById(Long id) throws SQLException{
        Session session = null;
        PhotoComment photoComment = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query query = session.createSQLQuery(
                "SELECT * "
              + "FROM photocomments c "
              + "WHERE c.id = :id "
                ).addEntity(PhotoComment.class).setLong("id", id);
            if(!query.list().isEmpty())
                photoComment = (PhotoComment)query.list().get(0);
            session.getTransaction().commit();
        }catch(HibernateException e){
            System.out.println(e.toString());
            StackTraceElement[] el = e.getStackTrace();
                for (StackTraceElement el1 : el) {
                    System.out.println(el1);
                }
        }finally{
            if((session != null)&&(session.isOpen())) session.close();
        }
        return photoComment;
    }
    
    
    @Override
    public PhotoComment getLastPhotoComment(String user_from) throws SQLException{
        Session session = null;
        PhotoComment photoComment = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query query = session.createSQLQuery(
                "SELECT * "
              + "FROM photocomments c "
              + "INNER JOIN photodescriptions d "
              + "ON c.photo_id = d.id "          
              + "WHERE c.user_from = :user and c.deleted = '0' and d.deleted = '0' "
              + "ORDER BY c.date DESC LIMIT 1 "
                ).addEntity(PhotoComment.class).setString("user", user_from);
            if(!query.list().isEmpty()) 
                photoComment = (PhotoComment)query.list().get(0);
            session.getTransaction().commit();
        }catch(HibernateException e){
            System.out.println(e.toString());
            StackTraceElement[] el = e.getStackTrace();
                for (StackTraceElement el1 : el) {
                    System.out.println(el1);
                }
        }finally{
            if((session != null)&&(session.isOpen())) session.close();
        }
        return photoComment;
    }
    
     
    @Override
    public List<PhotoComment> getAllPhotoComments() throws SQLException{
        Session session = null;
        List<PhotoComment> photoComments = new ArrayList<>();
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            photoComments = session.createCriteria(PhotoComment.class).list();
        }catch(HibernateException e){
            System.out.println(e.toString());
            StackTraceElement[] el = e.getStackTrace();
                for (StackTraceElement el1 : el) {
                    System.out.println(el1);
                }  
        }finally{
            if((session != null)&&(session.isOpen())) session.close();
        }
        return photoComments;
    }
   
    
    @Override
    public List<PhotoComment> getAllPhotoComments(User user_from, boolean withDeleted) throws SQLException{
        Session session = null;
        List<PhotoComment> photoComments = new ArrayList<>(); 
        if (user_from == null) return photoComments;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query query = session.createSQLQuery(
                "select * " 
              + "from photocomments c "
              + "INNER JOIN photodescriptions d "
              + "ON c.photo_id = d.id "
              + "WHERE c.user_from = :login "
              + "and ((c.deleted = '0')or(c.deleted = :deleted )) and ((d.deleted = '0')or(d.deleted = :deleted )) "
              + "order by c.date desc")
                .addEntity(PhotoComment.class).setString("login", user_from.getUsername())
                .setBoolean("deleted", withDeleted);
             
            if(!query.list().isEmpty()) 
                photoComments = (List<PhotoComment>)query.list();
            session.getTransaction().commit();
        }catch(HibernateException e){
            System.out.println(e.toString());
            StackTraceElement[] el = e.getStackTrace();
                for (StackTraceElement el1 : el) {
                    System.out.println(el1);
                }
        }finally{
            if((session != null)&&(session.isOpen())) session.close();
        }
        return photoComments;
    }
      
   
    @Override
    public List<PhotoComment> getAllPhotoComments(String user_from, boolean withDeleted) throws SQLException{
        Session session = null;
        List<PhotoComment> photoComments = new ArrayList<>(); 
        if (user_from == null) return photoComments;
        try{
             session = HibernateUtil.getSessionFactory().openSession();
             session.beginTransaction();
             Query query = session.createSQLQuery(
                "select * " 
              + "from photocomments c " 
              + "INNER JOIN photodescriptions d "
              + "ON c.photo_id = d.id "
              + "WHERE c.user_from = :login "
              + "and ((c.deleted = '0')or(c.deleted = :deleted )) and ((d.deleted = '0')or(d.deleted = :deleted )) "
              + "order by c.date desc"
                ).addEntity(PhotoComment.class).setString("login", user_from).setBoolean("deleted", withDeleted);
             
            if(!query.list().isEmpty()) 
                photoComments = (List<PhotoComment>)query.list();
             session.getTransaction().commit();
        }catch(HibernateException e){
            System.out.println(e.toString());
            StackTraceElement[] el = e.getStackTrace();
                for (StackTraceElement el1 : el) {
                    System.out.println(el1);
                }
        }finally{
            if((session!=null)&&(session.isOpen())) session.close();
        }
        return photoComments;
    }
    
    
    @Override
    public List<PhotoComment> getAllPhotoComments(Long photo_id, boolean withDeleted) throws SQLException{
        Session session = null;
        List<PhotoComment> photoComments = new ArrayList<>(); 
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query query = session.createSQLQuery(
                "select * " 
              + "from photocomments c " 
              + "INNER JOIN photodescriptions d "
              + "ON c.photo_id = d.id "
              + "WHERE c.photo_id = :photo_id "
              + "and ((c.deleted = '0')or(c.deleted = :deleted )) and ((d.deleted = '0')or(d.deleted = :deleted )) "
              + "order by c.date DESC"
                ).addEntity(PhotoComment.class).setLong("photo_id", photo_id).setBoolean("deleted", withDeleted);
             
            if(!query.list().isEmpty()) 
                photoComments = (List<PhotoComment>)query.list();
            session.getTransaction().commit();
        }catch(HibernateException e){
            System.out.println(e.toString());
            StackTraceElement[] el = e.getStackTrace();
                for (StackTraceElement el1 : el) {
                    System.out.println(el1);
                }
        }finally{
            if((session != null)&&(session.isOpen())) session.close();
        }
        return photoComments;
    }
      
      
    @Override
    public List<PhotoComment> getPhotoCommentsBeforeId(long photo_id, long comment_id, long count, boolean withDeleted) throws SQLException{
        Session session = null;
        List<PhotoComment> photoComments = new ArrayList<>(); 
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query query;
            if (comment_id != -1)
                query = session.createSQLQuery(
                    "select * " 
                  + "from photocomments c " 
                  + "INNER JOIN photodescriptions d "
                  + "ON c.photo_id = d.id "
                  + "WHERE c.photo_id = :photo_id and c.id < :id "
                  + "and ((c.deleted = '0')or(c.deleted = :deleted )) and ((d.deleted = '0')or(d.deleted = :deleted )) "
                  + "order by c.date desc LIMIT :count "
                    ).addEntity(PhotoComment.class).setLong("photo_id", photo_id).setLong("id", comment_id)
                    .setLong("count", count).setBoolean("deleted", withDeleted);
            else 
                query = session.createSQLQuery(
                    "select * " 
                  + "from photocomments c " 
                  + "INNER JOIN photodescriptions d "
                  + "ON c.photo_id = d.id "
                  + "WHERE c.photo_id = :photo_id "
                  + "and ((c.deleted = '0')or(c.deleted = :deleted )) and ((d.deleted = '0')or(d.deleted = :deleted )) "
                  + "ORDER by c.date desc LIMIT :count "
                    ).addEntity(PhotoComment.class).setLong("photo_id", photo_id)
                    .setLong("count", count).setBoolean("deleted", withDeleted);
           
            if(!query.list().isEmpty()) 
                photoComments = (List<PhotoComment>)query.list();
            session.getTransaction().commit();
        }catch(HibernateException e){
            System.out.println(e.toString());
            StackTraceElement[] el = e.getStackTrace();
                for (StackTraceElement el1 : el) {
                    System.out.println(el1);
                }
        }finally{
            if((session != null)&&(session.isOpen())) session.close();
        }
      
        return photoComments;
    }
    
      
    @Override
    public void deletePhotoCommentById(Long id) throws SQLException{
        Session session = null;
        PhotoComment photoComment = getPhotoCommentById(id);
        if (photoComment == null) return;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(photoComment);
            session.getTransaction().commit();
        }catch(HibernateException e){
            System.out.println(e.toString());
            StackTraceElement[] el = e.getStackTrace();
                for (StackTraceElement el1 : el) {
                    System.out.println(el1);
                }
        }finally{
            if((session != null)&&(session.isOpen())) session.close();
        }
    } 
     
     
    @Override
    public void setDeletedPhotoCommentById(Long id) throws SQLException{
        Session session = null;
        PhotoComment photoComment = getPhotoCommentById(id);
        if (photoComment == null) return;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            photoComment.setDeleted(true);
            session.update(photoComment);
            session.getTransaction().commit();
        }catch(HibernateException e){
            System.out.println(e.toString());
            StackTraceElement[] el = e.getStackTrace();
                for (StackTraceElement el1 : el) {
                    System.out.println(el1);
                }
        }finally{
            if((session != null)&&(session.isOpen())) session.close();
        }
    } 
     
   
    @Override
    public void deleteAllPhotoComments(User user) throws SQLException{
        Session session = null;
        List<PhotoComment> photoComments = getAllPhotoComments(user, true);
        if (photoComments.isEmpty()) return;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
                for (PhotoComment photoComment : photoComments) {
                    session.delete(photoComment);
                }
            session.getTransaction().commit();
        }catch(HibernateException e){
            System.out.println(e.toString());
            StackTraceElement[] el = e.getStackTrace();
                for (StackTraceElement el1 : el) {
                    System.out.println(el1);
                }
        }finally{
            if((session != null)&&(session.isOpen())) session.close();
        }
    } 
    
    
    @Override
    public void deleteAllPhotoComments(String user) throws SQLException{
        Session session = null;
        List<PhotoComment> photoComments = getAllPhotoComments(user, true);
        if (photoComments.isEmpty()) return;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
                for (PhotoComment photoComment : photoComments) {
                    session.delete(photoComment);
                }
            session.getTransaction().commit();
        }catch(HibernateException e){
            System.out.println(e.toString());
            StackTraceElement[] el = e.getStackTrace();
                for (StackTraceElement el1 : el) {
                    System.out.println(el1);
                }
        }finally{
            if((session != null)&&(session.isOpen())) session.close();
        }
    }
    
    
    @Override
    public void deleteAllPhotoComments(Long photo_id) throws SQLException{
        Session session = null;
        List<PhotoComment> photoComments = getAllPhotoComments(photo_id, true);
        if (photoComments.isEmpty()) return;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
                for (PhotoComment photoComment : photoComments) {
                    session.delete(photoComment);
                }
            session.getTransaction().commit();
        }catch(HibernateException e){
            System.out.println(e.toString());
            StackTraceElement[] el = e.getStackTrace();
                for (StackTraceElement el1 : el) {
                    System.out.println(el1);
                }
        }finally{
            if((session != null)&&(session.isOpen())) session.close();
        }
    } 
    
     
    @Override
    public void setDeletedAllPhotoComments(User user) throws SQLException{
        Session session = null;
        List<PhotoComment> photoComments = getAllPhotoComments(user, false);
        if (photoComments.isEmpty()) return;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            for (PhotoComment m : photoComments){
                m.setDeleted(true);
                session.update(m);
            }
            session.getTransaction().commit();
        }catch(HibernateException e){
            System.out.println(e.toString());
            StackTraceElement[] el = e.getStackTrace();
                for (StackTraceElement el1 : el) {
                    System.out.println(el1);
                }
        }finally{
            if((session != null)&&(session.isOpen())) session.close();
        }
    } 
    
    
    @Override
    public void setDeletedAllPhotoComments(String user) throws SQLException{
        Session session = null;
        List<PhotoComment> photoComments = getAllPhotoComments(user, false);
        if (photoComments.isEmpty()) return;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            for (PhotoComment m : photoComments){
                m.setDeleted(true);
                session.update(m);
            }
            session.getTransaction().commit();
        }catch(HibernateException e){
            System.out.println(e.toString());
            StackTraceElement[] el = e.getStackTrace();
                for (StackTraceElement el1 : el) {
                    System.out.println(el1);
                }
        }finally{
            if((session != null)&&(session.isOpen())) session.close();
        }
    }
    
    
    @Override
    public void setDeletedAllPhotoComments(Long photo_id) throws SQLException{
        Session session = null;
        List<PhotoComment> photoComments = getAllPhotoComments(photo_id, false);
        if (photoComments.isEmpty()) return;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            for (PhotoComment m : photoComments){
                m.setDeleted(true);
                session.update(m);
            }
            session.getTransaction().commit();
        }catch(HibernateException e){
            System.out.println(e.toString());
            StackTraceElement[] el = e.getStackTrace();
                for (StackTraceElement el1 : el) {
                    System.out.println(el1);
                }
        }finally{
            if((session != null)&&(session.isOpen())) session.close();
        }
    } 
   
    
    @Override
    public void deleteAllPhotoComments() throws SQLException{
        Session session = null;
        try{
            List<PhotoComment> photoComments = this.getAllPhotoComments();
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            for (PhotoComment photoComment : photoComments) {
                session.delete(photoComment);
            }
            session.getTransaction().commit();
        }catch(SQLException | HibernateException e){
            System.out.println(e.toString());
            StackTraceElement[] el = e.getStackTrace();
            for (StackTraceElement el1 : el) {
                System.out.println(el1);
            }
        }finally{
            if((session != null)&&(session.isOpen())) session.close();
        }
    }    
    
}
