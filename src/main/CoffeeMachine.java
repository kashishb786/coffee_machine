package main;

import java.util.Map;
import java.util.Set;

/**
 * @author vishnu.bhaskar
 * @created_on 13/02/21
 */
public interface CoffeeMachine {

    String makeBeverage(String beverage);
    void refill(String ingredient,Double amount);
    void addNewBeverage(String beverage,Recipe recipe );
    void removeBeverage(String beverage);
    void addNewIngredient(String ingredient);
    void removeIngredient(String ingredient);
    Map<String,Double> showInventory();
    void customizeMiniAlert(String ingredient,Double amount);
    Set<String> showAllBeverages();
    Boolean alert();
}
