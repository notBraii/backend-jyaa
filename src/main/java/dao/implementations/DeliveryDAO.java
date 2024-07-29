package dao.implementations;

import org.jvnet.hk2.annotations.Service;

import models.Delivery;

@Service
public class DeliveryDAO extends BaseDAO<Delivery>{

	public DeliveryDAO() {
	}
	
	@Override
	protected Class<Delivery> getEntityClass() {
		// TODO Auto-generated method stub
		return Delivery.class;
	}
	
}
