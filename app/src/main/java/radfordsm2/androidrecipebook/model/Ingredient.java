package radfordsm2.androidrecipebook.model;

/**
 * Created by smr12 on 12/30/2017.
 */

public class Ingredient {

    private int id;
    private String name;
    private String category;
    private int in_pantry;

    public Ingredient(){
        this.id = -1;
    }

    public Ingredient(String name, String category, int in_pantry){
        this.name = name;
        this.category = category;
        this.in_pantry = in_pantry;

    }

    public Ingredient(int id, String name, String category, int in_pantry){
        this.id = id;
        this.name = name;
        this.category = category;
        this.in_pantry = in_pantry;
    }

    //getters
    public int getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public String getCategory(){
        return this.category;
    }

    public int getInPantry() { return this.in_pantry; }

    //setters
    public void setId(int id){
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setCategory(String category){
        this.category = category;
    }

    public void setInPantry(int in_pantry){ this.in_pantry = in_pantry; }

    //Representations
    public String testString(){
        return this.name + " " + this.category;
    }
}
