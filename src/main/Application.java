package main;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;

/**
 * @author vishnu.bhaskar
 * @created_on 13/02/21
 */

// Hi all to run this code you need to provide the necessary inputs in this format: hot_coffee green_tea black_tea hot_tea
    // and to exit you can enter the command exit anywhere
public class Application {

    public static void main(String[] args) {
        // New coffee machine
        CoffeeMachine coffeeMachine=new CoffeeMachineImpl();
        //adding hot_tea
        Recipe recipe= new Recipe();
        recipe.add(new RecipeObj("hot_water",200D));
        recipe.add(new RecipeObj("hot_milk",100D));
        recipe.add(new RecipeObj("ginger_syrup",10D));
        recipe.add(new RecipeObj("sugar_syrup",10D));
        recipe.add(new RecipeObj("tea_leaves_syrup",30D));
        coffeeMachine.addNewBeverage("hot_tea",recipe);
        //adding hot_coffee
        recipe=new Recipe();
        recipe.add(new RecipeObj("hot_water",100D));
        recipe.add(new RecipeObj("hot_milk",400D));
        recipe.add(new RecipeObj("ginger_syrup",30D));
        recipe.add(new RecipeObj("sugar_syrup",50D));
        recipe.add(new RecipeObj("tea_leaves_syrup",30D));
        coffeeMachine.addNewBeverage("hot_coffee",recipe);
        recipe=new Recipe();
        //adding black_tea
        recipe.add(new RecipeObj("hot_water",300D));
//        recipe.add(new RecipeObj("hot_milk",400D));
        recipe.add(new RecipeObj("ginger_syrup",30D));
        recipe.add(new RecipeObj("sugar_syrup",50D));
        recipe.add(new RecipeObj("tea_leaves_syrup",30D));
        coffeeMachine.addNewBeverage("black_tea",recipe);
        recipe=new Recipe();
        //adding green_tea
        recipe.add(new RecipeObj("hot_water",100D));
//        recipe.add(new RecipeObj("hot_milk",400D));
        recipe.add(new RecipeObj("ginger_syrup",30D));
        recipe.add(new RecipeObj("sugar_syrup",50D));
        recipe.add(new RecipeObj("green_mixture",30D));
        coffeeMachine.addNewBeverage("green_tea",recipe);
        //Inventory
        coffeeMachine.addNewIngredient("hot_milk");
        coffeeMachine.addNewIngredient("hot_water");
        coffeeMachine.addNewIngredient("ginger_syrup");
        coffeeMachine.addNewIngredient("sugar_syrup");
        coffeeMachine.addNewIngredient("tea_leaves_syrup");
        coffeeMachine.refill("hot_milk",500D);
        coffeeMachine.refill("hot_water",500D);
        coffeeMachine.refill("ginger_syrup",100D);
        coffeeMachine.refill("sugar_syrup",100D);
        coffeeMachine.refill("tea_leaves_syrup",100D);
        //Set alerting values
        coffeeMachine.customizeMiniAlert("hot_water",100D);
        List<String>a;



        Scanner scanner=new Scanner(System.in);
        System.out.println("Enter the tread pool count:");
        Integer nThreads= scanner.nextInt();
        //thread pool
        ExecutorService pool=Executors.newFixedThreadPool(nThreads);
        // parallel thread for alerting
        ExecutorService executorService= Executors.newSingleThreadExecutor();

        executorService.execute(new Runnable() {
            public void run() {
                try {
               while(1==1)
               {
                   coffeeMachine.alert();
                   Thread.sleep(60000L);

               }
                } catch (InterruptedException e) {
                }
            }
        });

        //infinite loop
        while(1==1)
        {
            System.out.println("Menu: "+coffeeMachine.showAllBeverages());
            String commands=scanner.next();
            pool.submit(()->Arrays.asList(commands.split(" ")).parallelStream().forEach(s-> {
                if(s.equalsIgnoreCase("Exit"))
                {
                    System.exit(0); //system close/exit
                }
                System.out.println("thread id"+Thread.currentThread().getId());
                System.out.println(coffeeMachine.makeBeverage(s));

            }));

        }
    }
}
