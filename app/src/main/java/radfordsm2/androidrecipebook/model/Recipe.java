package radfordsm2.androidrecipebook.model;


/**
 * Created by smr12 on 12/28/2017.
 */

public class Recipe {

    private int id;
    private String name;
    private String prep_time;
    private String cook_time;
    private String total_time;
    private String image;
    private String category;

    public Recipe() {
        this.id = -1;
    }

    public Recipe(String name, String prep_time, String cook_time, String total_time, String image, String category) {
        this.name = name;
        this.prep_time = prep_time;
        this.cook_time = cook_time;
        this.total_time = total_time;
        this.image = image;
        this.category = category;
    }

    public Recipe(int id, String name, String prep_time, String cook_time, String total_time, String image, String category) {
        this.id = id;
        this.name = name;
        this.prep_time = prep_time;
        this.cook_time = cook_time;
        this.total_time = total_time;
        this.image = image;
        this.category = category;
    }

    //getters
    public int getId(){
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getPrepTime(){
        return this.prep_time;
    }

    public String getCookTime(){
        return this.cook_time;
    }

    public String getTotalTime(){
        return this.total_time;
    }

    public String getImage() {
        return this.image;
    }

    public String getCategory() {
        return this.category;
    }

    //Setters
    public void setId(int id){
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setPrepTime(String prep_time){
        this.prep_time = prep_time;
    }

    public void setCookTime(String cook_time){
        this.cook_time = cook_time;
    }

    public void setTotalTime(String total_time){
        this.total_time = total_time;
    }

    public void setImage(String image){
        this.image = image;
    }

    public void setCategory(String category){
        this.category = category;
    }
}