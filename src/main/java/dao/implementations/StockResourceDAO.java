package dao.implementations;

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

}
