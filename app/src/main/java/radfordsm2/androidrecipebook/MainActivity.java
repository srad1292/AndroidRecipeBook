package radfordsm2.androidrecipebook;

import android.content.DialogInterface;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import radfordsm2.androidrecipebook.helper.DatabaseHelper;
import radfordsm2.androidrecipebook.model.Ingredient;
import radfordsm2.androidrecipebook.model.Recipe;
import radfordsm2.androidrecipebook.model.RecipeIngredient;
import radfordsm2.androidrecipebook.model.Step;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //This is in activityMain.xml
        Toolbar myToolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(myToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_settings was selected
            case R.id.action_initializeDB:
                initialFillDatabase();
                break;
            case R.id.action_clearDB:
                clearDatabase();
                break;
            case R.id.action_testCrud:
                testCrud();
                break;
            default:
                break;
        }
        return true;
    }

    //Ensure user wants to exit then close it if so
    @Override
    public void onBackPressed(){
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MainActivity.this.finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }


    /** Called when the Recipes button is clicked */
    public void startRecipes(View view){
        Intent intent = new Intent(this, Recipes.class);
        startActivity(intent);
    }

    /** Called when the Shopping button is clicked */
    public void startShoppingList(View view){
        Intent intent = new Intent(this, ShoppingList.class);
        startActivity(intent);
    }

    /** Called when the Pantry button is clicked */
    public void startPantry(View view){
        Intent intent = new Intent(this, Pantry.class);
        startActivity(intent);
    }

    /** Called when the Meal Builder button is clicked */
    public void startMealBuilder(View view){
        Intent intent = new Intent(this, MealBuilder.class);
        startActivity(intent);
    }


    /**
     * Called when initializeDB in the main action menu is clicked
     * Fills the DB with recipes, their ingredients, etc to
     * so it doesn't have to be done manually every time the app
     * has to be reloaded during development
     */
    public void initialFillDatabase(){
        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        Recipe r1 = db.getRecipeByName("Bacon Bagel Sandwich");
        Recipe r2 = db.getRecipeByName("Marinated Hot Dogs");
        if(r1.getId() == -1 && r2.getId() == -1) {
            baconCheeseBagel(db);
            marinatedHotDogs(db);
            caesarSalad(db);
            cornflakePotatoCasserole(db);
            ovenFrenchFries(db);
            loadedNachos(db);
            brownies(db);
            Log.i("fillDatabase: ", "Recipes: " + db.getRecipeCount());
            Log.i("fillDatabase: ", "Ingredients: " + db.getIngredientCount());
            Log.i("fillDatabase: ", "RecipeIngredients: " + db.getRecipeIngredientCount());
            Log.i("fillDatabase: ", "Steps: " + db.getStepCount());
        }
        db.closeDatabase();
    }

    public void clearDatabase(){
        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        db.clearDatabase();
        db.closeDatabase();
    }

    /**
     * The following methods are used to test
     * creating, retrieving, updating, and deleting
     * for all four tables in the database
     */

    public void baconCheeseBagel(DatabaseHelper db){
        try {
            Recipe recipe1 = new Recipe("Bacon Bagel Sandwich", "10 Minutes", "17 Minutes", "27 Minutes", "bbs.jpg", "Breakfast");
            long recipe1_id = db.createRecipe(recipe1);

            Ingredient ingredient1 = new Ingredient("Thick-Cut Bacon", "Meat, Poultry, and Seafood", 0);
            long ing1_id = db.createIngredient(ingredient1);

            Ingredient ingredient2 = new Ingredient("Sesame Seed Bagel", "Bread", 0);
            long ing2_id = db.createIngredient(ingredient2);

            Ingredient ingredient3 = new Ingredient("Sharp Cheddar Cheese", "Cheese", 1);
            long ing3_id = db.createIngredient(ingredient3);

            long rI = db.createRecipeIngredient(recipe1_id, ing1_id, "2.5 Strips");
            long rI2 = db.createRecipeIngredient(recipe1_id, ing2_id, "1");
            long rI3 = db.createRecipeIngredient(recipe1_id, ing3_id, "2 Slices");

            long step1 = db.createStep(recipe1_id, "Preheat your oven to 350 degrees.", 1);
            long step2 = db.createStep(recipe1_id, "Preheat pan to medium heat.", 2);
            long step3 = db.createStep(recipe1_id, "Cut whole slices of bacon into halves.", 3);
            long step4 = db.createStep(recipe1_id, "Fry bacon 8-12 minutes.", 4);
            long step5 = db.createStep(recipe1_id, "Cut the bagel in half and add a slice of cheese on each half.  Stack the bacon on each half.", 5);
            long step6 = db.createStep(recipe1_id, "Bake 7 minutes or until warm and cheese is melted.", 6);
        }catch (Exception e){
            Log.e("baconCheeseBagel", e.toString());
        }
    }

    private void marinatedHotDogs(DatabaseHelper db) {
        try {
            Recipe recipe1 = new Recipe("Marinated Hot Dogs", "10 minutes", "20 Minutes", "30 Minutes", "bbs.jpg", "Appetizers");
            long recipe1_id = db.createRecipe(recipe1);

            Ingredient ingredient1 = new Ingredient("Hot Dogs", "Meat, Poultry, and Seafood", 0);
            long ing1_id = db.createIngredient(ingredient1);

            Ingredient ingredient2 = new Ingredient("Ketchup", "Sauces", 1);
            long ing2_id = db.createIngredient(ingredient2);

            Ingredient ingredient3 = new Ingredient("BBQ Sauce", "Sauces", 1);
            long ing3_id = db.createIngredient(ingredient3);

            Ingredient ingredient4 = new Ingredient("Grape Jelly", "Dips and Spreads", 1);
            long ing4_id = db.createIngredient(ingredient4);

            long rI = db.createRecipeIngredient(recipe1_id, ing1_id, "10 Hot Dogs");
            long rI2 = db.createRecipeIngredient(recipe1_id, ing2_id, "1 Cups");
            long rI3 = db.createRecipeIngredient(recipe1_id, ing3_id, "2 Cups");
            long rI4 = db.createRecipeIngredient(recipe1_id, ing4_id, "1 Cup");

            long step1 = db.createStep(recipe1_id, "Combine Ketchup, BBQ Sauce, and Grape Jelly in a sauce pan.", 1);
            long step2 = db.createStep(recipe1_id, "Bring large pot filled with water to boil.", 2);
            long step3 = db.createStep(recipe1_id, "Heat sauce mixture, stirring, until everything is combined.", 3);
            long step4 = db.createStep(recipe1_id, "Slice hot dogs into half inch pieces, about 5-6 per hot dog.", 4);
            long step5 = db.createStep(recipe1_id, "Boil hot dogs until cooked, about 5-8 minutes.", 5);
            long step6 = db.createStep(recipe1_id, "Drain water then pour the sauce over the hot dogs.", 6);
            long step7 = db.createStep(recipe1_id, "Bring to a strong simmer, cover, then turn heat to medium and let everything cook 10-12 minutes.", 7);
        }catch (Exception e){
            Log.e("marinatedHotDogs", e.toString());
        }
    }

    private void caesarSalad(DatabaseHelper db) {
    }

    private void cornflakePotatoCasserole(DatabaseHelper db) {
        try {
            Recipe recipe1 = new Recipe("Cornflake Potato Casserole", "10 Minutes", "1 Hour and 10 Minutes", "1 Hour and 20 Minutes", "bbs.jpg", "Sides");
            long recipe1_id = db.createRecipe(recipe1);

            Ingredient ingredient1 = new Ingredient("Diced Hash Browns", "Fruits and Vegetables", 0);
            long ing1_id = db.createIngredient(ingredient1);

            long ing2_id = db.getIngredientByName("Sharp Cheddar Cheese").getId();

            Ingredient ingredient3 = new Ingredient("Sour Cream", "Dips and Spreads", 0);
            long ing3_id = db.createIngredient(ingredient3);

            Ingredient ingredient4 = new Ingredient("Cream of Potato Soup", "Canned Goods", 0);
            long ing4_id = db.createIngredient(ingredient4);

            Ingredient ingredient5 = new Ingredient("Butter", "Dips and Spreads", 1);
            long ing5_id = db.createIngredient(ingredient5);

            Ingredient ingredient6 = new Ingredient("Corn Flakes", "Cereal, Oatmeal, and Grains", 1);
            long ing6_id = db.createIngredient(ingredient6);

            long rI = db.createRecipeIngredient(recipe1_id, ing1_id, "32 oz");
            long rI2 = db.createRecipeIngredient(recipe1_id, ing2_id, "2 Cups Shredded");
            long rI3 = db.createRecipeIngredient(recipe1_id, ing3_id, "16 oz");
            long rI4 = db.createRecipeIngredient(recipe1_id, ing4_id, "10 3/4 oz");
            long rI5 = db.createRecipeIngredient(recipe1_id, ing5_id, "3/4 Cup");
            long rI6 = db.createRecipeIngredient(recipe1_id, ing6_id, "1 1/2 Cups");

            long step1 = db.createStep(recipe1_id, "Preheat oven to 350 degrees.", 1);
            long step2 = db.createStep(recipe1_id, "Melt 1/2 cup of butter in a large bowl.", 2);
            long step3 = db.createStep(recipe1_id, "Spray a 9x13 glass pan with cooking oil.  Pour the hash browns into the pan and spread into one layer.", 3);
            long step4 = db.createStep(recipe1_id, "Mix cheese, sour cream, and soup in with the melted butter.  Spread the mixture over the hash browns.", 4);
            long step5 = db.createStep(recipe1_id, "Bake for 55 minutes", 5);
            long step6 = db.createStep(recipe1_id, "Melt 1/4 cup of butter.  Mix butter and corn flakes, crunching the corn flakes up a little in the process.", 6);
            long step7 = db.createStep(recipe1_id, "Spread the corn flake mixture over the hash browns.", 7);
            long step8 = db.createStep(recipe1_id, "Bake 10-15 minutes.", 8);

        }catch (Exception e){
            Log.e("cornflakePotatoCasserole", e.toString());
        }
    }

    private void ovenFrenchFries(DatabaseHelper db) {
        try {
            Recipe recipe1 = new Recipe("Oven French Fries", "12 Minutes", "35 Minutes", "47 Minutes", "bbs.jpg", "Sides");
            long recipe1_id = db.createRecipe(recipe1);

            Ingredient ingredient1 = new Ingredient("Baking Potatoes", "Fruits and Vegetables", 0);
            long ing1_id = db.createIngredient(ingredient1);

            Ingredient ingredient2 = new Ingredient("Vegetable Oil", "Oils and Vinegars", 1);
            long ing2_id = db.createIngredient(ingredient2);

            Ingredient ingredient3 = new Ingredient("Salt", "Seasonings, Spices, and Basics", 1);
            long ing3_id = db.createIngredient(ingredient3);

            Ingredient ingredient4 = new Ingredient("Black Pepper", "Seasonings, Spices, and Basics", 1);
            long ing4_id = db.createIngredient(ingredient4);

            long rI = db.createRecipeIngredient(recipe1_id, ing1_id, "2");
            long rI2 = db.createRecipeIngredient(recipe1_id, ing2_id, "2 Tbls");
            long rI3 = db.createRecipeIngredient(recipe1_id, ing3_id, "");
            long rI4 = db.createRecipeIngredient(recipe1_id, ing4_id, "");

            long step1 = db.createStep(recipe1_id, "Preheat your oven to 400 degrees.", 1);
            long step2 = db.createStep(recipe1_id, "Cut potatoes into wedges.  Each potato should produce about 12 wedges.", 2);
            long step3 = db.createStep(recipe1_id, "Soak potato wedges in cold water to wash the starch off of them.", 3);
            long step4 = db.createStep(recipe1_id, "Line a baking sheet with aluminum foil and spray with cooking oil.", 4);
            long step5 = db.createStep(recipe1_id, "Drain the potatoes and then dry them.  Pour vegetable oil over them and stir to coat evenly.", 5);
            long step6 = db.createStep(recipe1_id, "Create a single layer of potatoes on your baking pan.", 6);
            long step7 = db.createStep(recipe1_id, "Bake for 15 minutes, flip them, then bake for 20 more minutes or until golden.", 7);
            long step8 = db.createStep(recipe1_id, "Immediately add salt and pepper to taste.", 8);
        }catch (Exception e){
            Log.e("ovenFrenchFries", e.toString());
        }
    }

    private void loadedNachos(DatabaseHelper db) {
    }


    private void brownies(DatabaseHelper db) {

    }


    public void testCrud(){
        Log.i("Main Toolbar", "Pressed Fill Database");
        DatabaseHelper db = new DatabaseHelper(getApplicationContext());;
        try{
            Recipe r = db.getRecipeByName("Bacon Bagel Sandwich");
            if (r.getId() == -1) {
                baconCheeseBagel(db);
            }
            r = db.getRecipeByName("Bacon Bagel Sandwich");
            Log.i("first insert", "Recipes: " + db.getRecipeCount() + " " + r.testString());
            printTests(db, r);
            updateBaconCheeseBagel(db);
            r = db.getRecipeByName("Bacon-Bagel Sandwich");
            Log.i("update", "Recipes: " + db.getRecipeCount() + " " + r.testString());
            printTests(db, r);
            deleteBaconCheeseBagel(db);
            r = db.getRecipeByName("Bacon-Bagel Sandwich");
            Log.i("deleted", "Recipes: " + db.getRecipeCount() + " " + r.getId() + "");
            printTests(db, r);
            baconCheeseBagel(db);
            r = db.getRecipeByName("Bacon Bagel Sandwich");
            Log.i("second insert", "Recipes: " + db.getRecipeCount() + " " + r.testString());
            printTests(db, r);
        }
        catch (Exception e){
            Log.e("initializeFillDatabase", e.toString());
        }
        finally {
            db.clearDatabase();
            db.closeDatabase();
        }
    }



    public void updateBaconCheeseBagel(DatabaseHelper db){
        try {
            Recipe old_recipe = db.getRecipeByName("Bacon Bagel Sandwich");
            Recipe recipe1 = new Recipe("Bacon-Bagel Sandwich", "2 Minutes", "17 Minutes", "19 Minutes", "bbs.jpg", "Breakfast");
            long recipe1_id = db.updateRecipe(old_recipe, recipe1);

            Ingredient old_ingredient = db.getIngredientByName("Sesame Seed Bagel");
            Ingredient ingredient2 = new Ingredient("Plain Bagel", "Bread", 0);
            long ing2_id = db.updateIngredient(old_ingredient, ingredient2);

            List<RecipeIngredient> ri = db.getAllIngredientsByRecipe(recipe1_id);
            RecipeIngredient toUpdate = ri.get(1);
            toUpdate.setAmount("Four");
            db.updateRecipeIngredient(toUpdate);


            List<Step> steps = db.getAllStepsByRecipe(recipe1_id);
            for(Step s: steps){
                s.setDirection("- " + s.getDirection());
                db.updateStep(s);
            }
        }catch (Exception e){
            Log.e("updateBaconCheeseBagel", e.toString());
        }
    }

    public void deleteBaconCheeseBagel(DatabaseHelper db) {
        try {
            Recipe recipe1 = db.getRecipeByName("Bacon-Bagel Sandwich");
            db.deleteStepsRecipe(recipe1.getId());
            db.deleteRecipeIngredientByRecipe(recipe1.getId());
            db.deleteRecipe(recipe1.getId());

        }catch(Exception e){
            Log.e("deleteBaconCheeseBagel",e.toString());
        }
    }

    public void printTests(DatabaseHelper db, Recipe r1){
        List<Ingredient> ings = new ArrayList<Ingredient>();
        List<RecipeIngredient> ris = db.getAllIngredientsByRecipe(r1.getId());
        for (RecipeIngredient r: ris){
            ings.add(db.getIngredientByID(r.getIngredientId()));
        }
        for(Ingredient i: ings){
            Log.i("ingredients: ", i.testString());
        }

        for(RecipeIngredient ri: ris){
            Log.i("recipeIngredients: ", ri.testString());
        }
        List<Step> steps = db.getAllStepsByRecipe(r1.getId());
        for(Step s: steps){
            Log.i("steps: ", s.testString());
        }
    }
}
