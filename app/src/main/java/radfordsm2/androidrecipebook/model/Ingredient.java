package radfordsm2.androidrecipebook.model;

/**
 * Created by smr12 on 12/30/2017.
 */

public class Ingredient {

    private int id;
    private String name;
    private String category;

    public Ingredient(){

    }

    public Ingredient(String name, String category){
        this.name = name;
        this.category = category;
    }

    public Ingredient(int id, String name, String category){
        this.id = id;
        this.name = name;
        this.category = category;
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
}
