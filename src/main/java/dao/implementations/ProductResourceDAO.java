package dao.implementations;

import org.jvnet.hk2.annotations.Service;

import models.ProductResource;

@Service
public class ProductResourceDAO extends BaseDAO<ProductResource>{

	public ProductResourceDAO() {
	}
	
	@Override
	protected Class<ProductResource> getEntityClass() {
		// TODO Auto-generated method stub
		return ProductResource.class;
	}

}
