package dao.implementations;

import javax.persistence.TypedQuery;

import org.jvnet.hk2.annotations.Service;

import models.Role;
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
	
//	public List<User> search(Role role) {
//		StringBuilder jpql = new StringBuilder("SELECT p FROM ProductGroup p WHERE 1=1");
//
//// Agregar filtros a la consulta JPQL
//		if (name != null && !name.isEmpty()) {
//			jpql.append(" AND p.name LIKE :name");
//		}
//		if (categoryName != null && !categoryName.isEmpty()) {
//			jpql.append(" AND p.category.name LIKE :categoryName");
//		}
//		
//		TypedQuery<ProductGroup> query = em.createQuery(jpql.toString(), ProductGroup.class);
//
//// Establecer parámetros de la consulta
//		if (name != null && !name.isEmpty()) {
//			query.setParameter("name", "%" + name + "%");
//		}
//		if (categoryName != null && !categoryName.isEmpty()) {
//			query.setParameter("categoryName", "%" + categoryName + "%");
//		}
//
//// Aplicar la paginación
//		// int firstResult = (page - 1) * size;
//		// query.setFirstResult(firstResult);
//		// query.setMaxResults(size);
//
//		List<ProductGroup> results = query.getResultList();
//		return results;
//	}

}
