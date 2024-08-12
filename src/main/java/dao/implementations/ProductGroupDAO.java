package dao.implementations;

import java.util.List;

import javax.persistence.TypedQuery;

import org.jvnet.hk2.annotations.Service;

import models.ProductGroup;

@Service
public class ProductGroupDAO extends BaseDAO<ProductGroup> {

	public ProductGroupDAO() {
	}

	@Override
	protected Class<ProductGroup> getEntityClass() {
		// TODO Auto-generated method stub
		return ProductGroup.class;
	}

	public List<ProductGroup> search(String name, String categoryName) {
		StringBuilder jpql = new StringBuilder("SELECT p FROM ProductGroup p WHERE 1=1");

// Agregar filtros a la consulta JPQL
		if (name != null && !name.isEmpty()) {
			jpql.append(" AND p.name LIKE :name");
		}
		if (categoryName != null && !categoryName.isEmpty()) {
			jpql.append(" AND p.category.name LIKE :categoryName");
		}
		
		TypedQuery<ProductGroup> query = em.createQuery(jpql.toString(), ProductGroup.class);

// Establecer parámetros de la consulta
		if (name != null && !name.isEmpty()) {
			query.setParameter("name", "%" + name + "%");
		}
		if (categoryName != null && !categoryName.isEmpty()) {
			query.setParameter("categoryName", "%" + categoryName + "%");
		}

// Aplicar la paginación
		// int firstResult = (page - 1) * size;
		// query.setFirstResult(firstResult);
		// query.setMaxResults(size);

		List<ProductGroup> results = query.getResultList();
		return results;
	}

}
