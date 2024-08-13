package dao.implementations;

import java.util.Calendar;
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

	public List<StockRawMaterial> search(String resourceTagName, Date startDate, Date endDate) {
	    StringBuilder jpql = new StringBuilder("SELECT s FROM StockRawMaterial s WHERE 1=1");

	    // Agregar filtros a la consulta JPQL
	    if (resourceTagName != null && !resourceTagName.isEmpty()) {
	        jpql.append(" AND s.group.name LIKE :resourceTagName");
	    }
	    if (startDate != null && endDate != null) {
	        jpql.append(" AND s.expiredAt BETWEEN :startDate AND :endDate");
	    } else if (startDate != null) {
	        jpql.append(" AND s.expiredAt >= :startDate");
	    } else if (endDate != null) {
	        jpql.append(" AND s.expiredAt <= :endDate");
	    }

	    TypedQuery<StockRawMaterial> query = em.createQuery(jpql.toString(), StockRawMaterial.class);

	    // Establecer parámetros de la consulta
	    if (resourceTagName != null && !resourceTagName.isEmpty()) {
	        query.setParameter("resourceTagName", "%" + resourceTagName + "%");
	    }
	    if (startDate != null) {
	        query.setParameter("startDate", normalizeDate(startDate));
	    }
	    if (endDate != null) {
	        query.setParameter("endDate", normalizeDate(endDate));
	    }

	    // Obtener los resultados
	    List<StockRawMaterial> results = query.getResultList();
	    return results;
	}

	// Método para normalizar la fecha y eliminar la hora
	private Date normalizeDate(Date date) {
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    cal.set(Calendar.HOUR_OF_DAY, 0);
	    cal.set(Calendar.MINUTE, 0);
	    cal.set(Calendar.SECOND, 0);
	    cal.set(Calendar.MILLISECOND, 0);
	    return cal.getTime();
	}
	
}
