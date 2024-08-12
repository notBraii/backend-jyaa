package dao.implementations;

import java.util.Date;
import java.util.List;

import javax.persistence.TypedQuery;

import org.jvnet.hk2.annotations.Service;

import models.StockRawMaterial;

@Service
public class StockRawMaterialDAO extends BaseDAO<StockRawMaterial>{

	public StockRawMaterialDAO() {
	}
	
	@Override
	protected Class<StockRawMaterial> getEntityClass() {
		// TODO Auto-generated method stub
		return StockRawMaterial.class;
	}

	public List<StockRawMaterial> search(String resourceTagName, Date expiredAt) {
		StringBuilder jpql = new StringBuilder("SELECT s FROM StockRawMaterial s WHERE 1=1");

// Agregar filtros a la consulta JPQL
		if (resourceTagName != null && !resourceTagName.isEmpty()) {
			jpql.append(" AND s.group.name LIKE :resourceTagName");
		}
		if (expiredAt != null) {
			jpql.append(" AND s.expiredAt LIKE :expiredAt");
		}
		
		TypedQuery<StockRawMaterial> query = em.createQuery(jpql.toString(), StockRawMaterial.class);

// Establecer parámetros de la consulta
		if (resourceTagName != null && !resourceTagName.isEmpty()) {
			query.setParameter("resourceTagName", "%" + resourceTagName+ "%");
		}
		if (expiredAt != null ) {
			query.setParameter("expiredAt", "%" + expiredAt + "%");
		}

// Aplicar la paginación
		// int firstResult = (page - 1) * size;
		// query.setFirstResult(firstResult);
		// query.setMaxResults(size);

		List<StockRawMaterial> results = query.getResultList();
		return results;
	}
	
}
