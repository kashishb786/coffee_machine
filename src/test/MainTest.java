package test;

import main.CoffeeMachine;
import main.CoffeeMachineImpl;
import main.Recipe;
import main.RecipeObj;

import java.util.*;

/**
 * @author vishnu.bhaskar
 * @created_on 13/02/21
 */
//Hi, I did not know if we could use the Junit or not and hence i have written the code using java core
public class MainTest {
    private static boolean failure;
    private static void failure(){
        failure=true;
        System.out.println("\n+++++++++++++++++++++++++++++++++++++++++++++++++\n============================================================\n++++++++++++++++++++++++++++++++++++++++++++++++++\nFailure!!!");
    }
    public static void main(String[] args) {

            CoffeeMachine coffeeMachine = new CoffeeMachineImpl();
            //adding hot_tea
            Recipe recipe = new Recipe();
            List<String> hotTeaIngredients=Arrays.asList("hot_water","hot_milk","ginger_syrup","sugar_syrup","tea_leaves_syrup");
            List<Double> hotTeaIngredientsQuantity=Arrays.asList(200D,100D,10D,10D,30D);
            for (int i = 0; i < hotTeaIngredients.size(); i++) {
                recipe.add(new RecipeObj(hotTeaIngredients.get(i),hotTeaIngredientsQuantity.get(i)));
            }
            coffeeMachine.addNewBeverage("hot_tea", recipe);
            //==========> test add beverage
            if (coffeeMachine.showAllBeverages().size() != 1 && !coffeeMachine.showAllBeverages().contains("hot_tea"))
            {
                MainTest.failure();
                System.out.println("beverage was inserted or added");
            }
            Map<String,Double> inventoryTest=new HashMap<>();
            List <String> ingredientList=Arrays.asList("hot_milk","hot_water","ginger_syrup","sugar_syrup","tea_leaves_syrup");
            List<Double> quantityList=Arrays.asList(500D,500D,100D,100D,100D);
            for (int i=0;i<ingredientList.size();i++) {
                inventoryTest.put(ingredientList.get(i),quantityList.get(i));
                coffeeMachine.addNewIngredient(ingredientList.get(i));
                coffeeMachine.refill(ingredientList.get(i),quantityList.get(i));
            }

//=======================adding beverages=======================
            //adding hot_coffee
            Map<String,List<String>> drinkIngredientsMap=new HashMap<>();
            drinkIngredientsMap.put("hot_tea",hotTeaIngredients);
            drinkIngredientsMap.put("hot_coffee",Arrays.asList("hot_water","hot_milk","ginger_syrup","sugar_syrup","tea_leaves_syrup"));
            drinkIngredientsMap.put("black_tea",Arrays.asList("hot_water","ginger_syrup","sugar_syrup","tea_leaves_syrup"));
            drinkIngredientsMap.put("green_tea",Arrays.asList("hot_water","ginger_syrup","sugar_syrup","green_mixture"));
            Map<String,List<Double>> drinkQuantityMap=new HashMap<>();
            drinkQuantityMap.put("hot_tea",hotTeaIngredientsQuantity);
            drinkQuantityMap.put("hot_coffee",Arrays.asList(100D,400D,30D,50D,30D));
            drinkQuantityMap.put("black_tea",Arrays.asList(300D,30D,50D,30D));
            drinkQuantityMap.put("green_tea",Arrays.asList(100D,30D,50D,30D));
            recipe=new Recipe();
            for (int i = 0; i <drinkIngredientsMap.get("hot_coffee").size() ; i++) {
                recipe.add(new RecipeObj(drinkIngredientsMap.get("hot_coffee").get(i),drinkQuantityMap.get("hot_coffee").get(i)));
            }
            coffeeMachine.addNewBeverage("hot_coffee",recipe);
            recipe=new Recipe();
            //adding black_tea
            for (int i = 0; i <drinkIngredientsMap.get("black_tea").size() ; i++) {
                recipe.add(new RecipeObj(drinkIngredientsMap.get("black_tea").get(i),drinkQuantityMap.get("black_tea").get(i)));
            }
            coffeeMachine.addNewBeverage("black_tea",recipe);
            recipe=new Recipe();
            //adding green_tea

            for (int i = 0; i <drinkIngredientsMap.get("green_tea").size() ; i++) {
                recipe.add(new RecipeObj(drinkIngredientsMap.get("green_tea").get(i),drinkQuantityMap.get("green_tea").get(i)));
            }
            coffeeMachine.addNewBeverage("green_tea",recipe);

            //=======================adding beverages end =======================



            //=============>test alert
            coffeeMachine.customizeMiniAlert("hot_milk",1000D);
            if(!coffeeMachine.alert())
            {
                MainTest.failure();
                System.out.println("alert test 1 failed");
            }
            coffeeMachine.customizeMiniAlert("hot_milk",100D);
            if(coffeeMachine.alert())
            {
                MainTest.failure();
                System.out.println("alert test 2 failed");
            }
            //=============>test Make hot_tea
            String testBeverage="hot_tea";
            if (!coffeeMachine.makeBeverage(testBeverage).equals("hot_tea is prepared"))
            {
                MainTest.failure();
                System.out.println("make beverage test 1 failed");
            }
            Map<String,Double> systemInventory=coffeeMachine.showInventory();

            for (int i = 0; i <drinkIngredientsMap.get(testBeverage).size() ; i++) {
                if(!systemInventory.get(drinkIngredientsMap.get(testBeverage).get(i)).equals(inventoryTest.get(drinkIngredientsMap.get(testBeverage).get(i))-drinkQuantityMap.get(testBeverage).get(i)))
                {
                    MainTest.failure();
                    System.out.println(drinkIngredientsMap.get(testBeverage).get(i)+" failed");
                }
            }


            //testing non availability
        String result=coffeeMachine.makeBeverage("green_tea");
            if (!result.contains("not available")||!result.contains("[green_mixture]")){
                MainTest.failure();
                System.out.println("not available test failed");
            }
        //testing insufficient
        result=coffeeMachine.makeBeverage("hot_tea");
         result=coffeeMachine.makeBeverage("hot_tea");
        if (!result.contains("not sufficient")||!result.contains("[hot_water]")){
            MainTest.failure();
            System.out.println("not insufficient test failed");
        }

        if(!failure)
        System.out.println("\n+++++++++++++++++++++++++++++++++++++++++++++++++\n============================================================\n++++++++++++++++++++++++++++++++++++++++++++++++++\nAll test cases passed");
    }

}
