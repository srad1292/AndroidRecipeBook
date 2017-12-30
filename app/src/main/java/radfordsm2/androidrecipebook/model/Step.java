package radfordsm2.androidrecipebook.model;


/**
 * Created by smr12 on 12/30/2017.
 */

public class Step {

    private int id;
    private int recipe_id;
    private String direction;
    private int step_number;

    public Step(){

    }

    public Step(int recipe_id, String direction, int step_number){
        this.recipe_id = recipe_id;
        this.direction = direction;
        this.step_number = step_number;
    }

    public Step(int id, int recipe_id, String direction, int step_number){
        this.id = id;
        this.recipe_id = recipe_id;
        this.direction = direction;
        this.step_number = step_number;
    }

    //getters
    public int getId(){
        return this.id;
    }

    public int getRecipe_id(){
        return this.recipe_id;
    }

    public String getDirection(){
        return this.direction;
    }

    public int getStepNumber(){
        return this.step_number;
    }

    //setters
    public void setId(int id){
        this.id = id;
    }

    public void setRecipeId(int recipe_id){
        this.recipe_id = recipe_id;
    }

    public void setDirection(String direction){
        this.direction = direction;
    }

    public void setStepNumber(int step_number){
        this.step_number = step_number;
    }
}
