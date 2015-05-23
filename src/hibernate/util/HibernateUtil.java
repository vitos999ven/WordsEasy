package hibernate.util;

 
import hibernate.logic.Message;
import hibernate.logic.PhotoComment;
import hibernate.logic.PhotoDescription;
import hibernate.logic.PhotoLike;
import hibernate.logic.User;
import hibernate.logic.UserRole;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
      

public class HibernateUtil {
    private static SessionFactory sessionFactory = null;
    static{
        try{
            AnnotationConfiguration aconf = new AnnotationConfiguration()
                    .addAnnotatedClass(User.class)
                    .addAnnotatedClass(UserRole.class)
                    .addAnnotatedClass(Message.class)
                    .addAnnotatedClass(PhotoDescription.class)
                    .addAnnotatedClass(PhotoLike.class)
                    .addAnnotatedClass(PhotoComment.class);
            Configuration conf = aconf.configure("hibernate.cfg.xml");
          
            sessionFactory = conf.buildSessionFactory();
            
        }catch(HibernateException ex){
            System.out.println("Initial SessionFactory failed. "+ex.getMessage());
            StackTraceElement[] stack = ex.getStackTrace();
            for (StackTraceElement stack1 : stack) {
                System.out.println(stack1);
            }
        }
    }
    
    public static SessionFactory getSessionFactory(){
        return sessionFactory;
    }
}
