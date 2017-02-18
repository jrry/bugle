package pl.bugle.blog.dao;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Singleton
@Startup
public class Config {
    
    @PostConstruct
    public void createData() {
        /*
        UsersDao usersDao = new UsersDao();
        
        Users user = new Users();
        user.setUsername("admin");
        user.setEmail("jaroslaw.pawlowski");
        user.setPassword("admin");

        usersDao.createUser(user);
        */
    }
    
    @PreDestroy
    public void deleteData() {
        
    }
}
