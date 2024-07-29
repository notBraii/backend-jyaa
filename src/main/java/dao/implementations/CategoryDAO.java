package dao.implementations;

import org.jvnet.hk2.annotations.Service;
import models.Category;

@Service
public class CategoryDAO extends BaseDAO<Category> {

	public CategoryDAO() {
	}

	@Override
	protected Class<Category> getEntityClass() {
		// TODO Auto-generated method stub
		return Category.class;
	}

}