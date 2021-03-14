package main;

/**
 * @author vishnu.bhaskar
 * @created_on 13/02/21
 */
public class RecipeObj {
    private String ingredient;
    private Double quantity;

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public RecipeObj(String ingredient, Double quantity) {
        this.ingredient = ingredient;
        this.quantity = quantity;
    }
}
