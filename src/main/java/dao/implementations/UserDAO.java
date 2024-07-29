package dao.implementations;

import javax.persistence.TypedQuery;

import org.jvnet.hk2.annotations.Service;

import models.User;

@Service
public class UserDAO extends BaseDAO<User> {

	public UserDAO() {
	}

	@Override
	protected Class<User> getEntityClass() {
		// TODO Auto-generated method stub
		return User.class;
	}

	public User findByUsername(String username) {
		try {
			TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class);
			query.setParameter("username", username);
			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

}
