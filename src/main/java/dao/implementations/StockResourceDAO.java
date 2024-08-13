package dao.implementations;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.jvnet.hk2.annotations.Service;

import models.StockResource;

@Service
public class StockResourceDAO extends BaseDAO<StockResource> {

	public StockResourceDAO() {
	}
	
	@Override
	protected Class<StockResource> getEntityClass() {
		// TODO Auto-generated method stub
		return StockResource.class;
	}
	
    @SuppressWarnings("unchecked")
    @Override
    public List<StockResource> getAll() {
        try {
               String sql = "SELECT * FROM StockResource WHERE DTYPE = 'StockResource'";
                Query query = em.createNativeQuery(sql, StockResource.class);
                return query.getResultList();
        } catch (PersistenceException e) {
            // Manejo específico para excepciones relacionadas con la persistencia
            throw new RuntimeException("Error retrieving all entities: " + e.getMessage(), e);
        } catch (Exception e) {
            // Manejo general para cualquier otro tipo de excepción
            throw new RuntimeException("Unexpected error occurred while retrieving all entities.", e);
        }
    }

}
