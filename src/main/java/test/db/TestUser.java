package test.db;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
//import javax.persistence.EntityTransaction;

//import models.Role;
import models.User;
import utils.EMFSingleton;
import dao.implementations.UserDAO;

public class TestUser {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EntityManagerFactory emf = EMFSingleton.INSTANCE.getEMF();
		EntityManager em = emf.createEntityManager();
		//EntityTransaction etx = em.getTransaction();
		
		//Creo un usuario y lo persisto
		/*
		User u = new User("joaquin", "Asdasd123", "Joaquin Matto");
		try {
			etx.begin();
			
			em.persist(u);
			
			etx.commit();
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e);
		}
		*/
		User u;
		UserDAO uDAO = new UserDAO();
		// Consultas
		// Crear
		/*
		u = uDAO.get(1);
		*/
		
		// Actualizar
		//u = uDAO.get((long) 1);
		
		u = new User("joaquin", "123", "Joa");
		
		//uDAO.update(u, null);
		uDAO.save(u);
		/*
		System.out.println("Nombre: " + u.getFullname());
		System.out.println("Pass: " + u.getPass());
		System.out.println("Role: " + u.getRole());
		System.out.println("Username: " + u.getUsername());
*/
		em.close();
	}

}
