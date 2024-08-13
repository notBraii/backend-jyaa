package dao.implementations;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.RollbackException;
import javax.persistence.TypedQuery;

import org.hibernate.exception.ConstraintViolationException;
import org.jvnet.hk2.annotations.Service;

import exception.DuplicateEntityException;
import models.Delivery;
import models.Product;
import models.ProductGroup;
import models.SalesChannel;

@Service
public class DeliveryDAO extends BaseDAO<Delivery> {

	public DeliveryDAO() {
	}

	@Override
	protected Class<Delivery> getEntityClass() {
		// TODO Auto-generated method stub
		return Delivery.class;
	}

	@Override
	public void save(Delivery delivery) {
		EntityTransaction etx = em.getTransaction();
		try {
			etx.begin();
			
			// Validar que el producto existe
			Product product = em.find(Product.class, delivery.getProduct().getId());
			if (product == null) {
				throw new EntityNotFoundException("Product not found.");
			}
			
			// Validar que el grupo de productos existe
			ProductGroup productGroup = product.getProductGroup();
			if (productGroup == null) {
				throw new EntityNotFoundException("ProductGroup not found.");
			}
			
			// Validar que existe el canal de venta
			SalesChannel channel = em.find(SalesChannel.class, delivery.getChannel().getId());
			if (channel == null) {
				throw new EntityNotFoundException("Channel not found.");
			}
			
			// Validar que se puede actualizar el stock de ProductGroup
			if (productGroup.getStock() < delivery.getStock()) {
				throw new Exception("Not enough stock in the product group.");
			}
			
			// Validar que se puede actualizar el stock de Product
			if (product.getQuantity() < delivery.getStock()) {
				throw new Exception("Not enough stock in the product batch.");
			}
			
			// Actualizar stock de Product
			int newProductStock = product.getQuantity() - delivery.getStock();
			product.setQuantity(newProductStock);
			em.merge(product);
			
			// Actualizar stock de ProductGroup
			int newProductGroupStock = productGroup.getStock() - delivery.getStock();
			productGroup.setStock(newProductGroupStock);
			em.merge(productGroup);

			// Persistir el nuevo Delivery
			em.persist(delivery);
			
			etx.commit();
		} catch (RollbackException e) {
			Throwable cause = e.getCause();
			while (cause != null) {
				if (cause instanceof ConstraintViolationException) {
					throw new DuplicateEntityException("Duplicate entry error.");
				}
				cause = cause.getCause();
			}
			if (etx.isActive())
				etx.rollback();
			throw e;
		} catch (Exception e) {
			if (etx.isActive())
				etx.rollback();
			
			throw new RuntimeException("Error saving entity." + e);
		}
	}


	public List<Delivery> search(Integer stock, Date deliverDate, String productGroupName, String salesChannelName) {
	    try {
	        StringBuilder jpql = new StringBuilder("SELECT d FROM Delivery d WHERE 1=1");

	        // Agregar filtros a la consulta JPQL
	        if (stock != null) {
	            jpql.append(" AND d.stock = :stock");
	        }
	        if (deliverDate != null) {
	            jpql.append(" AND d.deliverDate = :deliverDate");
	        }
	        if (productGroupName != null && !productGroupName.isEmpty()) {
	            jpql.append(" AND d.product.productGroup.name LIKE :productGroupName");
	        }
	        if (salesChannelName != null && !salesChannelName.isEmpty()) {
	            jpql.append(" AND d.channel.name LIKE :salesChannelName");
	        }

	        System.out.println("JPQL Query: " + jpql.toString());
	        TypedQuery<Delivery> query = em.createQuery(jpql.toString(), Delivery.class);

	        // Establecer parámetros de la consulta
	        if (stock != null) {
	            query.setParameter("stock", stock);
	        }
	        if (deliverDate != null) {
	            query.setParameter("deliverDate", deliverDate);
	        }
	        if (productGroupName != null && !productGroupName.isEmpty()) {
	            query.setParameter("productGroupName", "%" + productGroupName + "%");
	        }
	        if (salesChannelName != null && !salesChannelName.isEmpty()) {
	            query.setParameter("salesChannelName", "%" + salesChannelName + "%");
	        }

	        List<Delivery> results = query.getResultList();
	        System.out.println("Results found: " + results.size());
	        return results;
	    } catch (IllegalArgumentException e) {
	        throw new IllegalArgumentException("Error en los parámetros de la consulta: " + e.getMessage());
	    } catch (PersistenceException e) {
	        throw new PersistenceException("Error al ejecutar la consulta: " + e.getMessage());
	    }
	}

}
