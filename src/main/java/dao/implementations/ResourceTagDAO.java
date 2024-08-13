package dao.implementations;

import java.util.List;

import javax.persistence.TypedQuery;

import org.jvnet.hk2.annotations.Service;

import models.ResourceTag;

@Service
public class ResourceTagDAO extends BaseDAO<ResourceTag> {

	public ResourceTagDAO() {
	}

	@Override
	protected Class<ResourceTag> getEntityClass() {
		// TODO Auto-generated method stub
		return ResourceTag.class;
	}
	
	public List<ResourceTag> search(String name) {
		StringBuilder jpql = new StringBuilder("SELECT r FROM ResourceTag r WHERE 1=1");

// Agregar filtros a la consulta JPQL
		if (name != null && !name.isEmpty()) {
			jpql.append(" AND r.name LIKE :name");
		}
		
		TypedQuery<ResourceTag> query = em.createQuery(jpql.toString(), ResourceTag.class);

// Establecer parámetros de la consulta
		if (name != null && !name.isEmpty()) {
			query.setParameter("name", "%" + name + "%");
		}

// Aplicar la paginación
		// int firstResult = (page - 1) * size;
		// query.setFirstResult(firstResult);
		// query.setMaxResults(size);

		List<ResourceTag> results = query.getResultList();
		return results;
	}

}
