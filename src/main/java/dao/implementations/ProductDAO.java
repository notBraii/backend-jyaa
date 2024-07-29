package dao.implementations;

import org.jvnet.hk2.annotations.Service;

import models.Product;

@Service
public class ProductDAO extends BaseDAO<Product>{

	public ProductDAO() {
	}
	
	@Override
	protected Class<Product> getEntityClass() {
		// TODO Auto-generated method stub
		return Product.class;
	}

}
