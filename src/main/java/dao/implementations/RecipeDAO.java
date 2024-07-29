package dao.implementations;

import org.jvnet.hk2.annotations.Service;

import models.Recipe;

@Service
public class RecipeDAO extends BaseDAO<Recipe> {

	public RecipeDAO() {
	}
	
	@Override
	protected Class<Recipe> getEntityClass() {
		// TODO Auto-generated method stub
		return Recipe.class;
	}

}
