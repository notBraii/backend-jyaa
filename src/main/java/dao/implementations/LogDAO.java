package dao.implementations;

import org.jvnet.hk2.annotations.Service;

import models.Log;

@Service
public class LogDAO extends BaseDAO<Log>{

	public LogDAO() {
	}
	
	@Override
	protected Class<Log> getEntityClass() {
		// TODO Auto-generated method stub
		return Log.class;
	}

}
