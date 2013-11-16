package ch.swissbytes.persistencia.action;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.Query;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.international.StatusMessages;
import org.jboss.seam.log.Log;

import ch.swissbytes.persistencia.model.User;

@Name("register")
@Scope(ScopeType.EVENT)
public class RegistrarAction {
	
	@In	
	User user;
	
	@In 
	EntityManager entityManager;
	
	@In
	StatusMessages statusMessages;
		
	private boolean registered; 
	
	@Logger Log log;
	
	private String verify;
	
	public void register() {
		log.info("El usuario {0} quiere registrarse", user.getName());
		List<?> existing = entityManager.createQuery("select u.username from User u where u.username=#{user.username}").getResultList();
		if (existing.size() == 0){
			entityManager.persist(user);
			statusMessages.add("Usuario registrado exitosamente #{user.name}");
			registered = true;	
		} else {
			statusMessages.addToControl("usrId", "Username #{user.username} ya registrado..");
		}
	}

	public String getVerify() {
		return verify;
	}

	public void setVerify(String verify) {
		this.verify = verify;
	}

	public boolean isRegistered() {
		return registered;
	}

	public void setRegistered(boolean registered) {
		this.registered = registered;
	}
}
