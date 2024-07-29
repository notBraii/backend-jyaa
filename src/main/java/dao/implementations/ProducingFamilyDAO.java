package dao.implementations;

import org.jvnet.hk2.annotations.Service;

import models.ProducingFamily;

@Service
public class ProducingFamilyDAO extends BaseDAO<ProducingFamily>{

	public ProducingFamilyDAO() {
	}
	
	@Override
	protected Class<ProducingFamily> getEntityClass() {
		// TODO Auto-generated method stub
		return ProducingFamily.class;
	}
	
}
