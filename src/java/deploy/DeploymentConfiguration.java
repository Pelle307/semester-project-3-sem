package deploy;

import entity.Role;
import entity.Url;
import entity.User;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import security.PasswordHash;


@WebListener
public class DeploymentConfiguration implements ServletContextListener {

    public static String PU_NAME = "PU_Local";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Map<String, String> env = System.getenv();
        //If we are running in the OPENSHIFT environment change the pu-name 
        if (env.keySet().contains("OPENSHIFT_MYSQL_DB_HOST")) {
            PU_NAME = "PU_OPENSHIFT";
        }
        System.out.println(PU_NAME);
        try {
            ServletContext context = sce.getServletContext();
            EntityManagerFactory emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);
            EntityManager em = emf.createEntityManager();

            //This flag is set in Web.xml -- Make sure to disable for a REAL system
            boolean makeTestUsers = context.getInitParameter("makeTestUsers").toLowerCase().equals("true");
            if (!makeTestUsers
                    || (em.find(User.class, "user") != null && em.find(User.class, "admin") != null && em.find(User.class, "user_admin") != null)) {
                return;
            }
            Role userRole = new Role("User");
            Role adminRole = new Role("Admin");

            User user = new User("user", PasswordHash.createHash("test"));
            User admin = new User("admin", PasswordHash.createHash("test"));
            User both = new User("user_admin", PasswordHash.createHash("test"));
            user.AddRole(userRole);
            admin.AddRole(adminRole);
            both.AddRole(userRole);
            both.AddRole(adminRole);

            try {
                em.getTransaction().begin();
                em.persist(userRole);
                em.persist(adminRole);

                em.persist(user);
                em.persist(admin);
                em.persist(both);
                em.persist(new Url("http://angularairline-plaul.rhcloud.com/"));
                em.persist(new Url("http://thebighornairline-ebski.rhcloud.com/GiantHornAirlineServer/"));
                em.persist(new Url("http://jstairline-hardboilr.rhcloud.com/"));
                em.persist(new Url("http://airline-nvbcphbusinesss.rhcloud.com/Travelr"));
                em.persist(new Url("http://justfly.azurewebsites.net/MomondoProjekt/"));
                em.persist(new Url("http://airline-nharbo.rhcloud.com/airline/"));
                em.persist(new Url("http://bizz-favl.rhcloud.com/bizzairline/"));
                em.persist(new Url("http://wildfly-x.cloudapp.net/airline/"));
                em.persist(new Url("http://frenchyairlines-zerosource.rhcloud.com/"));
                em.persist(new Url("http://infamouslines-baniproductions.rhcloud.com"));
                em.getTransaction().commit();

            } finally {
                em.close();
            }
      
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            Logger.getLogger(DeploymentConfiguration.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
