package dao.implementations;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.RollbackException;
import javax.persistence.TypedQuery;

import org.hibernate.exception.ConstraintViolationException;
import org.jvnet.hk2.annotations.Service;

import exception.DuplicateEntityException;
import models.Product;
import models.ProductGroup;
import models.ProductResource;
import models.StockRawMaterial;
import models.StockResource;

@Service
public class ProductDAO extends BaseDAO<Product> {

	public ProductDAO() {
	}

	@Override
	protected Class<Product> getEntityClass() {
		// TODO Auto-generated method stub
		return Product.class;
	}

	/**
	 * Saves a new entity to the database.
	 *
	 * @param t The entity to save.
	 * @throws Exception If an error occurs during the save operation.
	 */
	@Override
	public void save(Product product) {

		EntityTransaction etx = em.getTransaction();
		try {
			etx.begin();
			// Validar stock del ProductGroup
			ProductGroup productGroup = em.find(ProductGroup.class, product.getProductGroup().getId());
			if (productGroup == null) {
				throw new EntityNotFoundException("ProductGroup not found.");
			}

			if (productGroup.getStock() < product.getQuantity()) {
				throw new Exception("Not enough stock in the product group.");
			}

			Set<Long> stockResourceIds = new HashSet<>();

			boolean isRawMaterial = false;

			// Validar stock en los ProductResource
			for (ProductResource productResource : product.getProductResources()) {
				StockResource stockResource = em.find(productResource.getStockResource().getClass(),
						productResource.getStockResource().getId());

				isRawMaterial = stockResource instanceof StockRawMaterial;

				if (stockResource == null) {
					throw new EntityNotFoundException("StockResource not found.");
				}
				// Validar que el stockResource dentro del ProductResource no sea repetido
				if (stockResourceIds.contains(stockResource.getId())) {
					throw new IllegalArgumentException("Duplicate StockResource in the same Product.");
				}
				stockResourceIds.add(stockResource.getId());

				// Validar que exista stock dentro de StockResource
				if (stockResource.getQuantity().getValue() < productResource.getUsedQuantity().getValue()) {
					throw new Exception("Not enough stock in the stock resource.");
				}
			}

			// Actualizar stock de ProductGroup
			Integer newStock = productGroup.getStock() + product.getQuantity();
			productGroup.setStock(newStock);

			em.merge(productGroup);
			Double totalPrice = 0.0;
			// Actualizar la cantidad de StockResource
			for (ProductResource productResource : product.getProductResources()) {
				// StockResource stockResource = productResource.getStockResource();
				StockResource stockResource = em.find(productResource.getStockResource().getClass(),
						productResource.getStockResource().getId());

				Double newQuantityValue = stockResource.getQuantity().getValue()
						- productResource.getUsedQuantity().getValue();

				// Agregar Log de decremento de StockResources

				stockResource.getQuantity().setValue(newQuantityValue);
				if (stockResource instanceof StockRawMaterial) {
					try {
						StockRawMaterial stockRawMaterial = em.find(StockRawMaterial.class, stockResource.getId());
						stockRawMaterial.setQuantity(stockResource.getQuantity());
						em.merge(stockRawMaterial);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else
					em.merge(stockResource);

				totalPrice += (stockResource.getPrice() * productResource.getUsedQuantity().getValue());

			}

			totalPrice /= product.getQuantity();
			product.setPrice(totalPrice);

			// Persistir el nuevo Product
			em.persist(product);
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
			throw new RuntimeException("Error saving entity. " + e);
		}
	}

	public List<Product> search(List<String> names, String batch, Integer quantity, String productGroupName,
			String categoryName) {

		try {
			StringBuilder jpql = new StringBuilder("SELECT p FROM Product p WHERE 1=1");

// Agregar filtros a la consulta JPQL
			if (names != null && !names.isEmpty()) {
				jpql.append(" AND (");
				for (int i = 0; i < names.size(); i++) {
					jpql.append("p.name LIKE :name").append(i);
					if (i < names.size() - 1) {
						jpql.append(" OR ");
					}
				}
				jpql.append(")");
			}
			if (batch != null) {
				jpql.append(" AND p.batch = :batch");
			}
//		if (productionDate != null) {
//			jpql.append(" AND p.productionDate = :productionDate");
//		}
			if (quantity != null) {
				jpql.append(" AND p.quantity = :quantity");
			}

			if (productGroupName != null && !productGroupName.isEmpty()) {
				jpql.append(" AND p.productGroup.name LIKE :productGroupName");
			}
			if (categoryName != null && !categoryName.isEmpty()) {
				jpql.append(" AND p.productGroup.category.name LIKE :categoryName");
			}
			System.out.println("JPQL Query: " + jpql.toString());
			TypedQuery<Product> query = em.createQuery(jpql.toString(), Product.class);

// Establecer parámetros de la consulta
			if (names != null && !names.isEmpty()) {
				for (int i = 0; i < names.size(); i++) {
					query.setParameter("name" + i, "%" + names.get(i) + "%");
				}
			}
			if (batch != null) {
				query.setParameter("batch", batch);
			}
//		if (productionDate != null) {
//			query.setParameter("productionDate", LocalDate.parse(productionDate));
//		}
			if (quantity != null) {
				query.setParameter("quantity", quantity);
			}
//		if (productGroupId != null) {
//			query.setParameter("productGroupId", productGroupId);
//		}
			if (productGroupName != null && !productGroupName.isEmpty()) {
				query.setParameter("productGroupName", "%" + productGroupName + "%");
			}
			if (categoryName != null && !categoryName.isEmpty()) {
				query.setParameter("categoryName", "%" + categoryName + "%");
			}

// Aplicar la paginación
			// int firstResult = (page - 1) * size;
			// query.setFirstResult(firstResult);
			// query.setMaxResults(size);

			List<Product> results = query.getResultList();
			System.out.println("Results found: " + results.size());
			return results;
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Error en los parámetros de la consulta: " + e.getMessage());
		} catch (PersistenceException e) {
			throw new PersistenceException("Error al ejecutar la consulta: " + e.getMessage());
		}
	}

	public long count(String name, String batch, Integer quantity, String productGroupName, String categoryName) {
		StringBuilder jpql = new StringBuilder("SELECT COUNT(p) FROM Product p WHERE 1=1");

// Agregar filtros a la consulta JPQL
		if (name != null && !name.isEmpty()) {
			jpql.append(" AND p.name LIKE :name");
		}
		if (batch != null) {
			jpql.append(" AND p.batch = :batch");
		}
//		if (productionDate != null) {
//			jpql.append(" AND p.productionDate = :productionDate");
//		}
		if (quantity != null) {
			jpql.append(" AND p.quantity = :quantity");
		}
//		if (productGroupId != null) {
//			jpql.append(" AND p.productGroup.id = :productGroupId");
//		}
		if (productGroupName != null && !productGroupName.isEmpty()) {
			jpql.append(" AND p.productGroup.name LIKE :productGroupName");
		}
		if (categoryName != null && !categoryName.isEmpty()) {
			jpql.append(" AND p.productGroup.category.name LIKE :categoryName");
		}

		TypedQuery<Long> query = em.createQuery(jpql.toString(), Long.class);

// Establecer parámetros de la consulta
		if (name != null && !name.isEmpty()) {
			query.setParameter("name", "%" + name + "%");
		}
		if (batch != null) {
			query.setParameter("batch", batch);
		}
//		if (productionDate != null) {
//			query.setParameter("productionDate", LocalDate.parse(productionDate));
//		}
		if (quantity != null) {
			query.setParameter("quantity", quantity);
		}
//		if (productGroupId != null) {
//			query.setParameter("productGroupId", productGroupId);
//		}
		if (productGroupName != null && !productGroupName.isEmpty()) {
			query.setParameter("productGroupName", "%" + productGroupName + "%");
		}
		if (categoryName != null && !categoryName.isEmpty()) {
			query.setParameter("categoryName", "%" + categoryName + "%");
		}
		System.out.println(query.getResultList());

		return query.getSingleResult();
	}
}
