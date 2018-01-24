package radfordsm2.androidrecipebook;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import radfordsm2.androidrecipebook.helper.DatabaseHelper;
import radfordsm2.androidrecipebook.model.Ingredient;
import radfordsm2.androidrecipebook.model.Recipe;
import radfordsm2.androidrecipebook.model.RecipeIngredient;
import radfordsm2.androidrecipebook.model.Step;

public class ViewRecipe extends AppCompatActivity {

    Recipe recipe;
    List<RecipeIngredient> recipeIngredientList;
    List<Step> directionList;
    String selected = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipe);
        Toolbar myToolbar = findViewById(R.id.view_recipe_toolbar);
        setSupportActionBar(myToolbar);
        if(selected.equals("")) {
            Intent intent = getIntent();
            selected = intent.getStringExtra("SELECTED_RECIPE");
        }
        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        recipe = db.getRecipeByName(selected);
        Log.e("id: ", recipe.getId() + "");
        recipeIngredientList = db.getAllIngredientsByRecipe(recipe.getId());
        directionList = db.getAllStepsByRecipe(recipe.getId());
        db.closeDatabase();
        displayRecipe();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.view_recipe_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_menu_edit_recipe:
                Log.i("onOptionsItemSelected", "Edit recipe button pressed");
                startEditRecipe();
                return true;

            case R.id.action_menu_delete_recipe:
                new AlertDialog.Builder(this)
                        .setMessage("Are you sure you want to delete this Recipe")
                        .setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                deleteRecipe();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void onBackPressed() {
        NavUtils.navigateUpFromSameTask(this);
    }


    public void fillActivity(String name){
        setContentView(R.layout.activity_view_recipe);
        Toolbar myToolbar = findViewById(R.id.view_recipe_toolbar);
        setSupportActionBar(myToolbar);
        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        recipe = db.getRecipeByName(name);
        Log.e("id: ", recipe.getId() + "");
        recipeIngredientList = db.getAllIngredientsByRecipe(recipe.getId());
        directionList = db.getAllStepsByRecipe(recipe.getId());
        db.closeDatabase();
        displayRecipe();
    }

    public void displayRecipe(){
        displayRecipeInformation();
        displayIngredients();
        displayDirections();
    }

    public void displayRecipeInformation(){
        TextView view = findViewById(R.id.header_name);
        view.setText(recipe.getName());

        view = findViewById(R.id.header_recipe_category);
        view.setText(recipe.getCategory());

        view = findViewById(R.id.label_prep_time);
        String text = "Prep Time : " + recipe.getPrepTime();
        view.setText(text);

        view = findViewById(R.id.label_cook_time);
        text = "Cook Time : " + recipe.getCookTime();
        view.setText(text);

        view = findViewById(R.id.label_total_time);
        text = "Total Time: " + recipe.getTotalTime();
        view.setText(text);
    }

    public void displayIngredients(){
        TableLayout ingredients_table = findViewById(R.id.ingredients_table);
        TableRow row;
        Ingredient current_ingredient;
        String text;
        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        for(RecipeIngredient ing: recipeIngredientList){
            row = new TableRow(this);
            TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
            /**
            Resources r = getResources();
            float pxBottomMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, r.getDisplayMetrics());

            params.setMargins(0,0,0,Math.round(pxBottomMargin));
            */
            row.setLayoutParams(params);

            TextView ing_amount = new TextView(this);
            //ing_amount.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
            current_ingredient = db.getIngredientByID(ing.getIngredientId());
            text = ing.getAmount() + " " + current_ingredient.getName();
            ing_amount.setText(text);
            ing_amount.setGravity(Gravity.CENTER);
            ing_amount.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            row.addView(ing_amount);

            ingredients_table.addView(row);
        }
        db.closeDatabase();
    }

    public void displayDirections(){
        TableLayout direction_table = findViewById(R.id.directions_table);
        TableRow row;
        String text;
        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        for(Step current_direction: directionList){
            row = new TableRow(this);
            TableRow.LayoutParams layout = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
            Resources r = getResources();
            float pxBottomMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, r.getDisplayMetrics());

            layout.setMargins(0,0,0,Math.round(pxBottomMargin));
            row.setLayoutParams(layout);

            TextView direction = new TextView(this);
            //direction.setSingleLine(false);
            //direction.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
            text = "- " + current_direction.getDirection();
            direction.setText(text);
            direction.setGravity(Gravity.LEFT);
            direction.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            row.addView(direction);

            direction_table.addView(row);

        }
        db.closeDatabase();
    }

    public void startEditRecipe(){
        Intent intent = new Intent(this, EditRecipe.class);
        intent.putExtra("SELECTED_RECIPE", selected);
        startActivityForResult(intent, 1);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                 String changed = data.getStringExtra("CHANGED_RECIPE");
                 fillActivity(changed);
            }
        }
    }

    public void deleteRecipe(){
        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        db.deleteStepsRecipe(recipe.getId());
        db.deleteRecipeIngredientByRecipe(recipe.getId());
        db.deleteRecipe(recipe.getId());
        db.closeDatabase();
        new AlertDialog.Builder(this)
                .setMessage(recipe.getName() + " was deleted.")
                .setCancelable(false)
                .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        onBackPressed();
                    }
                })
                .show();

    }

}
