package dao.implementations;

import org.jvnet.hk2.annotations.Service;

import models.Ingredient;

@Service
public class IngredientDAO extends BaseDAO<Ingredient>{

	public IngredientDAO() {
	}
	
	@Override
	protected Class<Ingredient> getEntityClass() {
		// TODO Auto-generated method stub
		return Ingredient.class;
	}

}
