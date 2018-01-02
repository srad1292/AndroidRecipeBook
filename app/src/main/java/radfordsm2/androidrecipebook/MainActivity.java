package radfordsm2.androidrecipebook;

import android.content.DialogInterface;
import android.content.Intent;
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

import radfordsm2.androidrecipebook.helper.DatabaseHelper;
import radfordsm2.androidrecipebook.model.Ingredient;
import radfordsm2.androidrecipebook.model.Recipe;
import radfordsm2.androidrecipebook.model.RecipeIngredient;


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

    public void initialFillDatabase(){
        Log.i("Main Toolbar", "Pressed Fill Database");
        DatabaseHelper db = new DatabaseHelper(getApplicationContext());;
        try{
            Recipe r = db.getRecipeByName("Bacon Bagel Sandwich");
            if (r.getId() == -1) {
                Log.i("initialFillDatabase", "No Recipe with that name");
                baconCheeseBagel(db);
            }
            else {
                r = db.getRecipeByName("Bacon Bagel Sandwich");
                if (r.getId() == -1)
                    Log.i("initialFillDatabase", "No Recipe with that name");
                else {
                    Log.i("initialFillDatabase", r.getName());
                    Log.i("initialFillDatabase", "Recipes: " + db.getRecipeCount());
                    Log.i("initialFillDatabase", "Ingredients: " + db.getIngredientCount());
                    Log.i("initialFillDatabase", "RecipeIngredients: " + db.getRecipeIngredientCount());
                    Log.i("initialFillDatabase", "Steps: " + db.getStepCount());
                }
            }
        }
        catch (Exception e){
            Log.e("intializeFillDatabase", e.toString());
        }
        finally {
            db.close();
        }
    }

    public void baconCheeseBagel(DatabaseHelper db){
        try {
            Recipe recipe1 = new Recipe("Bacon Bagel Sandwich", "1 Minute", "17 Minutes", "18 Minutes", "bbs.jpg", "Breakfast");
            long recipe1_id = db.createRecipe(recipe1);

            Ingredient ingredient1 = new Ingredient("Thick-Cut Bacon", "Meat, Poultry, and Seafood");
            long ing1_id = db.createIngredient(ingredient1);

            Ingredient ingredient2 = new Ingredient("Sesame Seed Bagel", "Bread");
            long ing2_id = db.createIngredient(ingredient2);

            Ingredient ingredient3 = new Ingredient("Sharp Cheddar Cheese", "Cheese");
            long ing3_id = db.createIngredient(ingredient3);

            long rI = db.createRecipeIngredient(recipe1_id, ing1_id, "2.5 Strips");
            long rI2 = db.createRecipeIngredient(recipe1_id, ing2_id, "1");
            long rI3 = db.createRecipeIngredient(recipe1_id, ing3_id, "2 Slices");

            long step1 = db.createStep(recipe1_id, "Preheat your oven to 350 degrees", 1);
            long step2 = db.createStep(recipe1_id, "Preheat pan to medium heat", 2);
            long step3 = db.createStep(recipe1_id, "Cut whole slices of bacon into halves", 3);
            long step4 = db.createStep(recipe1_id, "Fry bacon 8-12 minutes", 4);
            long step5 = db.createStep(recipe1_id, "Cut the bagel in half and add a slice of cheese on each half.  Stack the bacon on each half.", 5);
            long step6 = db.createStep(recipe1_id, "Bake 7 minutes or until warm and cheese is melted", 6);
        }catch (Exception e){
            Log.e("baconCheeseBagel", e.toString());
        }
    }
}
