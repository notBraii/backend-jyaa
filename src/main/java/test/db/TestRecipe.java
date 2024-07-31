package test.db;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import models.Category;
import models.Ingredient;
import models.Recipe;
import models.ResourceTag;
import utils.EMFSingleton;
import utils.Magnitude;

public class TestRecipe {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EntityManagerFactory emf = EMFSingleton.INSTANCE.getEMF();
		EntityManager em = emf.createEntityManager();
		EntityTransaction etx = em.getTransaction();
		
		try {
			etx.begin();
			
			// Create resource
			ResourceTag flour = new ResourceTag("Flour");
			ResourceTag sugar = new ResourceTag("Sugar");
			
			// Persist resources
			//em.persist(flour);
			//em.persist(sugar);
			
			// Create ingredients
			Ingredient flourIngredient = new Ingredient(flour, new Magnitude(500.0, "gr"));
			Ingredient sugarIngredient = new Ingredient(sugar, new Magnitude(200.0, "gr"));
			
			Category breadedCategory = new Category("Breaded");
			// Persist ingredients
			em.persist(flourIngredient);
			em.persist(sugarIngredient);
			em.persist(breadedCategory);
			
			// Create recipe
            List<Ingredient> ingredients = Arrays.asList(flourIngredient, sugarIngredient);
            Recipe cakeRecipe = new Recipe("Cake", "Recipe for cake.", breadedCategory, ingredients, "Paso 1, paso 2, paso 3.");
                        
            // Persist recipe
            em.persist(cakeRecipe);
            
            etx.commit();
            
            // Fetch and print the persisted Recipe
            Recipe persistedRecipe = em.find(Recipe.class, cakeRecipe.getId());
            System.out.println("Recipe: " + persistedRecipe.getName());
            persistedRecipe.getIngredients().forEach(ingredient -> {
                System.out.println("Ingredient: " + ingredient.getResourceTag().getName() + ", Quantity: " + ingredient.getMagnitude().getValue() + " " + ingredient.getMagnitude().getUnit());
            });
            
		} catch (Exception e) {
			// TODO: handle exception
            if (etx.isActive()) {
                etx.rollback();
            }
            e.printStackTrace();
		} finally {
			em.close();
		}
	}

}
