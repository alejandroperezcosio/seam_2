package ch.swissbytes.persistencia.action;

import java.util.List;

import javax.persistence.EntityManager;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.log.Log;
import org.jboss.seam.security.Credentials;
import org.jboss.seam.security.Identity;

import ch.swissbytes.persistencia.model.User;

@Name("authenticator")
public class Authenticator
{
    @Logger private Log log;

    @In Identity identity;
    @In Credentials credentials;
    @In EntityManager entityManager;
    @In(required=false)
    @Out(required=false, scope=ScopeType.SESSION)
    User user;

    public boolean authenticate()
    {
        log.info("authenticating {0}", credentials.getUsername());
        // User implementation code... 
        List<?> results = entityManager.createQuery("select u from User u where u.username=#{credentials.username} and u.password=#{credentials.password}").getResultList();
        if (results.size() == 0) {
        	return false;
        } else {
        	user = (User) results.get(0);
        	return true;
        }
        //
        /* if ("admin".equals(credentials.getUsername()))
        {
            identity.addRole("admin");
            return true;
        }
        return false;*/
    }

}
