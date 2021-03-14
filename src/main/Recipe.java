package main;

import java.util.LinkedList;
import java.util.List;

/**
 * @author vishnu.bhaskar
 * @created_on 13/02/21
 */
public class Recipe {
    private List<RecipeObj> recipeObjList=new LinkedList<>();
    public List<RecipeObj> getRecipeObjList() {
        return recipeObjList;
    }
    public void add(RecipeObj recipeObj) {
        recipeObjList.add(recipeObj);
    }
}
