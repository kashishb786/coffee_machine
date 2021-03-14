package main;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author vishnu.bhaskar
 * @created_on 13/02/21
 */
public class CoffeeMachineImpl implements CoffeeMachine{

    //stores all the beverages
    Map<String, Recipe> beverageStore=new HashMap<>();
    //inventory of the coffee machine
    Map<String,Double> inventory=new ConcurrentHashMap<>();
    // Store for alert thresholds
    Map<String,Double> minimumQuantity= new HashMap<>();

    @Override
    public Set<String> showAllBeverages() {
        return beverageStore.keySet();
    }

    @Override
    public String makeBeverage(String beverage) {
        if(beverageStore.containsKey(beverage))
        {
            Recipe recipe=beverageStore.get(beverage);

            List<String> missingIngredients= new LinkedList<>();
            List<String> insufficientIngredients= new LinkedList<>();
            // using a sync block so that only one thread executes the critical block at any given amount of time
            synchronized (CoffeeMachineImpl.class){
                // adding elements to the insufficient or missing ingredients list
                for(RecipeObj recipeObj : recipe.getRecipeObjList()){
                    if(inventory!=null && inventory.containsKey(recipeObj.getIngredient())){
                        Double availableQuantity = inventory.get(recipeObj.getIngredient());
                        if(availableQuantity<recipeObj.getQuantity())
                        {
                            insufficientIngredients.add(recipeObj.getIngredient());
                        }
                    }
                    else{
                        missingIngredients.add(recipeObj.getIngredient());
                    }
                }
                if(missingIngredients.size()>0)  // there are some missing ingredient
                {
                    return beverage+" cannot be prepared because "+missingIngredients.toString()+" is not available";
                }
                if(insufficientIngredients.size()>0)// there are some insufficient ingredients in the inventory
                {
                    return  beverage+" cannot be prepared because "+insufficientIngredients.toString()+" is not sufficient";
                }
                // updating the inventory
                for(RecipeObj recipeObj : recipe.getRecipeObjList()){
                    inventory.put(recipeObj.getIngredient(),inventory.get(recipeObj.getIngredient())-recipeObj.getQuantity());
                }
            }
            return beverage+ " is prepared";
        }
        return "Invalid Input";
    }

    @Override
    public void refill(String ingredient,Double amount) {
        if(inventory.containsKey(ingredient))
            inventory.put(ingredient,inventory.get(ingredient)+amount);
        else
            System.out.println("Error no ingredient Missing");
    }

    @Override
    public void addNewBeverage(String beverage,Recipe recipe) {
        beverageStore.put(beverage,recipe);
    }

    @Override
    public void removeBeverage(String beverage) {
        beverageStore.remove(beverage);
    }

    @Override
    public void addNewIngredient(String ingredient) {
        if(inventory.containsKey(ingredient))
            return;
        inventory.put(ingredient,0D);
    }

    @Override
    public void removeIngredient(String ingredient) {
        inventory.remove(ingredient);
    }
    @Override
    public void customizeMiniAlert(String ingredient,Double amount) {
        minimumQuantity.put(ingredient,amount);
    }

    @Override
    public Map<String, Double> showInventory() {
        return inventory;
    }

    @Override
    public Boolean alert(){
        System.out.println("System scan");
        Boolean alert=false;
        for (String ingredient:inventory.keySet()) {
            if(minimumQuantity.containsKey(ingredient)&& minimumQuantity.get(ingredient)>=inventory.get(ingredient)) {
                System.out.println("System ALERT!!!!");
                System.out.println(ingredient + " is low");
                alert=true;
            }
        }
        return alert;
    }
}
