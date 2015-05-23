package hibernate.DAO.Impl;

import hibernate.DAO.PhotoDescriptionDAO;
import hibernate.logic.PhotoDescription;
import hibernate.logic.User;
import hibernate.util.Factory;
import hibernate.util.HibernateUtil;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;


public class PhotoDescriptionDAOImpl implements PhotoDescriptionDAO{
    
    @Override
    public void addPhotoDescription(PhotoDescription photoDescription) throws SQLException{
        Session session = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(photoDescription);
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
    public void updatePhotoDescription(PhotoDescription photoDescription) throws SQLException{
        Session session = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(photoDescription);
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
    public void updatePhotoDescription(Long id, String description) throws SQLException{
        Session session = null;
        session = HibernateUtil.getSessionFactory().openSession();
        PhotoDescription desc = Factory.getInstance().getPhotoDescriptionDAO().getPhotoDescriptionById(id);
        if (desc == null) return;
        try{        
            session.beginTransaction();
            desc.setDescription(description);
            session.update(desc);
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
    public PhotoDescription getPhotoDescriptionById(Long id) throws SQLException{
        Session session = null;
        PhotoDescription photoDescription = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query query = session.createSQLQuery(
                "SELECT * "
              + "FROM photodescriptions d "
              + "WHERE d.id = :id"
                ).addEntity(PhotoDescription.class).setLong("id", id);
            if(!query.list().isEmpty()){ 
                photoDescription = (PhotoDescription)query.list().get(0);
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
        return photoDescription;
    }
    
    
    @Override
    public List<PhotoDescription> getAllPhotoDescriptions() throws SQLException{
        Session session = null;
        List<PhotoDescription> photoDescriptions = new ArrayList<>();
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query query = session.createSQLQuery(
                "select * " +
                "from photodescriptions  "
                ).addEntity(PhotoDescription.class);
             
            if(!query.list().isEmpty()){ 
                photoDescriptions = (List<PhotoDescription>)query.list();
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
        return photoDescriptions;
    }
   
    
    @Override
    public List<PhotoDescription> getAllPhotoDescriptions(User user, boolean withDeleted) throws SQLException{
        Session session = null;
        List<PhotoDescription> photoDescriptions = new ArrayList<>(); 
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query query = session.createSQLQuery(
                "select * "
              + "from photodescriptions d " 
              + "WHERE d.user = :login  and ((d.deleted = '0')or(d.deleted = :deleted )) "
                ).addEntity(PhotoDescription.class).setString("login", user.getUsername())
                .setBoolean("deleted", withDeleted);
             
            if(!query.list().isEmpty()){ 
                photoDescriptions = (List<PhotoDescription>)query.list();
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
        return photoDescriptions;
    }
      
    
    @Override
    public List<PhotoDescription> getPhotoDescriptionsBeforeId(User user, long id, long count, boolean withDeleted) throws SQLException{
        Session session = null;
        List<PhotoDescription> photoDescriptions = new ArrayList<>(); 
        if (user == null) return photoDescriptions;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query query;
            if (id != -1){
                query = session.createSQLQuery(
                    "select * " 
                  + "from photodescriptions d " 
                  + "WHERE d.user = :login and d.id < :id  "
                  + "and ((d.deleted = '0')or(d.deleted = :deleted )) "
                  + "ORDER BY d.date DESC LIMIT :count "
                    ).addEntity(PhotoDescription.class).setString("login", user.getUsername())
                    .setLong("id", id).setLong("count", count).setBoolean("deleted", withDeleted);
            }
            else{
                query = session.createSQLQuery(
                    "select * " 
                  + "from photodescriptions d " 
                  + "WHERE d.user = :login  "
                  + "and ((d.deleted = '0')or(d.deleted = :deleted )) "
                  + "ORDER BY d.date DESC LIMIT :count "
                    ).addEntity(PhotoDescription.class).setString("login", user.getUsername())
                    .setLong("count", count).setBoolean("deleted", withDeleted);
            }
            if(!query.list().isEmpty()){ 
                photoDescriptions = (List<PhotoDescription>)query.list();
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
        return photoDescriptions;
    }
  
    
    @Override
    public List<PhotoDescription> getPhotoDescriptionsBeforeId(String login, long id, long count, boolean withDeleted) throws SQLException{
        Session session = null;
        List<PhotoDescription> photoDescriptions = new ArrayList<>(); 
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query query;
            if (id != -1){
                query = session.createSQLQuery(
                    "select * " 
                  + "from photodescriptions d " 
                  + "WHERE d.user = :login and d.id < :id  "
                  + "and ((d.deleted = '0')or(d.deleted = :deleted )) "
                  + "ORDER BY d.date DESC LIMIT :count "
                    ).addEntity(PhotoDescription.class).setString("login", login)
                    .setLong("id", id).setLong("count", count).setBoolean("deleted", withDeleted);
            }
            else{
                query = session.createSQLQuery(
                    "select * " 
                  + "from photodescriptions d " 
                  + "WHERE d.user = :login  and ((d.deleted = '0')or(d.deleted = :deleted )) "
                  + "ORDER BY d.date DESC LIMIT :count "
                    ).addEntity(PhotoDescription.class).setString("login", login)
                    .setLong("count", count).setBoolean("deleted", withDeleted);
            }
            if(!query.list().isEmpty()){ 
                photoDescriptions = (List<PhotoDescription>)query.list();
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
        return photoDescriptions;
    }
    
      
    @Override
    public List<PhotoDescription> getAllPhotoDescriptions(String login, boolean withDeleted) throws SQLException{
        Session session = null;
        List<PhotoDescription> photoDescriptions = new ArrayList<>(); 
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query query = session.createSQLQuery(
                "select * " 
              + "from photodescriptions d "
              + "WHERE d.user = :login  "
              + "and ((d.deleted = '0')or(d.deleted = :deleted )) "
                ).addEntity(PhotoDescription.class).setString("login", login).setBoolean("deleted", withDeleted);
             
            if(!query.list().isEmpty()){ 
                photoDescriptions = (List<PhotoDescription>)query.list();
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
        return photoDescriptions;
    }
    
    
    @Override
    public void deletePhotoDescriptionById(Long id) throws SQLException{
        Session session = null;
        PhotoDescription photoDescription = getPhotoDescriptionById(id);
        if (photoDescription == null) return;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Factory.getInstance().getPhotoLikeDAO().deleteAllPhotoLikes(id);
            Factory.getInstance().getPhotoCommentDAO().deleteAllPhotoComments(id);
            session.delete(photoDescription);
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
    public void setDeletedPhotoDescriptionById(Long id) throws SQLException{
        Session session = null;
        session = HibernateUtil.getSessionFactory().openSession();
        PhotoDescription desc = Factory.getInstance().getPhotoDescriptionDAO().getPhotoDescriptionById(id);
        if (desc == null) return;
        try{        
            session.beginTransaction();
            desc.setDeleted(true);
            session.update(desc);
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
    public void deleteAllPhotoDescriptions(User user) throws SQLException{
        Session session = null;
        List<PhotoDescription> photoDescriptions = getAllPhotoDescriptions(user, true);
        if (photoDescriptions.isEmpty()) return;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
                for (PhotoDescription photoDescription : photoDescriptions) {
                    Factory.getInstance().getPhotoLikeDAO().deleteAllPhotoLikes(photoDescription.getId());
                    Factory.getInstance().getPhotoCommentDAO().deleteAllPhotoComments(photoDescription.getId());
                    session.delete(photoDescription);
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
    public void setDeletedAllPhotoDescriptions(User user) throws SQLException{
        Session session = null;
        List<PhotoDescription> photoDescriptions = getAllPhotoDescriptions(user, false);
        if (photoDescriptions.isEmpty()) return;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            for (PhotoDescription desc : photoDescriptions){
                Factory.getInstance().getPhotoLikeDAO().setDeletedAllPhotoLikes(desc.getId());
                Factory.getInstance().getPhotoCommentDAO().setDeletedAllPhotoComments(desc.getId());
                desc.setDeleted(true);
                session.update(desc);
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
    public void deleteAllPhotoDescriptions(String user) throws SQLException{
        Session session = null;
        List<PhotoDescription> photoDescriptions = getAllPhotoDescriptions(user, true);
        if (photoDescriptions.isEmpty()) return;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
                for (PhotoDescription photoDescription : photoDescriptions) {
                    Factory.getInstance().getPhotoLikeDAO().deleteAllPhotoLikes(photoDescription.getId());
                    Factory.getInstance().getPhotoCommentDAO().deleteAllPhotoComments(photoDescription.getId());
                    session.delete(photoDescription);
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
    public void setDeletedAllPhotoDescriptions(String user) throws SQLException{
        Session session = null;
        List<PhotoDescription> photoDescriptions = getAllPhotoDescriptions(user, false);
        if (photoDescriptions.isEmpty()) return;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            for (PhotoDescription desc : photoDescriptions){
                Factory.getInstance().getPhotoLikeDAO().setDeletedAllPhotoLikes(desc.getId());
                Factory.getInstance().getPhotoCommentDAO().setDeletedAllPhotoComments(desc.getId());
                desc.setDeleted(true);
                session.update(desc);
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
    public void deleteAllPhotoDescriptions() throws SQLException{
        Session session = null;
        List<PhotoDescription> photoDescriptions = getAllPhotoDescriptions();
        if (photoDescriptions.isEmpty()) return;
        try{
            Factory.getInstance().getPhotoLikeDAO().deleteAllPhotoLikes();
            Factory.getInstance().getPhotoCommentDAO().deleteAllPhotoComments();
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
                for (PhotoDescription photoDescription : photoDescriptions) {
                    session.delete(photoDescription);
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
    
}
