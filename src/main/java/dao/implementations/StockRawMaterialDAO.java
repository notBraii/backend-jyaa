package dao.implementations;

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

}
