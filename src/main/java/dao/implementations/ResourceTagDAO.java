package dao.implementations;

import org.jvnet.hk2.annotations.Service;

import models.ResourceTag;

@Service
public class ResourceTagDAO extends BaseDAO<ResourceTag> {

	public ResourceTagDAO() {
	}

	@Override
	protected Class<ResourceTag> getEntityClass() {
		// TODO Auto-generated method stub
		return ResourceTag.class;
	}

}
