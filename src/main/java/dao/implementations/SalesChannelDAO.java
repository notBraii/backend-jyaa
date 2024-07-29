package dao.implementations;

import org.jvnet.hk2.annotations.Service;

import models.SalesChannel;

@Service
public class SalesChannelDAO extends BaseDAO<SalesChannel>{

	public SalesChannelDAO() {
	}
	
	@Override
	protected Class<SalesChannel> getEntityClass() {
		// TODO Auto-generated method stub
		return SalesChannel.class;
	}

}
