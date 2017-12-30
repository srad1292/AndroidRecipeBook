package radfordsm2.androidrecipebook.model;


/**
 * Created by smr12 on 12/30/2017.
 */

public class RecipeIngredient {

    private int id;
    private int recipe_id;
    private int ingredient_id;
    private String amount;

    public RecipeIngredient(){

    }

    public RecipeIngredient(int recipe_id, int ingredient_id, String amount){
        this.recipe_id = recipe_id;
        this.ingredient_id = ingredient_id;
        this.amount = amount;
    }

    public RecipeIngredient(int id, int recipe_id, int ingredient_id, String amount){
        this.id = id;
        this.recipe_id = recipe_id;
        this.ingredient_id = ingredient_id;
        this.amount = amount;
    }

    //getters
    public int getId(){
        return this.id;
    }

    public int getRecipeId(){ return this.recipe_id; }

    public int getIngredientId(){
        return this.ingredient_id;
    }

    public String getAmount(){
        return this.amount;
    }

    //setters
    public void setId(int id){
        this.id = id;
    }

    public void setRecipeId(int recipe_id) {
        this.recipe_id = recipe_id;
    }

    public void setIngredientId(int ingredient_id){
        this.ingredient_id = ingredient_id;
    }

    public void setAmount(String amount){
        this.amount = amount;
    }
}