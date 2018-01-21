package radfordsm2.androidrecipebook;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import radfordsm2.androidrecipebook.helper.DatabaseHelper;
import radfordsm2.androidrecipebook.model.Ingredient;
import radfordsm2.androidrecipebook.model.Recipe;
import radfordsm2.androidrecipebook.model.RecipeIngredient;

public class AddRecipe extends AppCompatActivity {

    //Layout Views
    private Spinner category_spinner;
    private EditText name_input,prep_input,cook_input,total_input;
    private TableLayout ingredient_table, direction_table;
    TextView empty,empty_time,empty_ingredient,empty_direction;

    //View Helpers
    private ArrayAdapter<String> adapter;
    private String[] ingredients_autocomplete;

    //Recipe Information
    String new_name, category, prep_time, cook_time, total_time;
    String[] ingredient_names, ingredient_amounts, directions;

    //private int steps;

    private View.OnTouchListener Spinner_OnTouch = new View.OnTouchListener() {
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                closeKeyboard(v);
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        ingredients_autocomplete = allIngredients();
        empty = (TextView)findViewById(R.id.name_error);
        empty_time = (TextView)findViewById(R.id.empty_time_error);
        empty_ingredient = (TextView)findViewById(R.id.empty_ingredient_error);
        empty_direction = (TextView)findViewById(R.id.empty_direction_error);


        name_input = findViewById(R.id.new_recipe_name);

        //Fill the category spinner with the correct options
        category_spinner = (Spinner) findViewById(R.id.category_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.recipe_category_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        category_spinner.setAdapter(adapter);
        category_spinner.setOnTouchListener(Spinner_OnTouch);

        prep_input = findViewById(R.id.prep_time);
        cook_input = findViewById(R.id.cook_time);
        total_input = findViewById(R.id.total_time);

        ingredient_table = findViewById(R.id.ingredients_table);
        addIngredient(findViewById(R.id.add_ingredient_button));

        //steps = 1001;
        direction_table = findViewById(R.id.directions_table);
        addStep(findViewById(R.id.add_step_button));
    }

    @Override
    public void onBackPressed(){
        NavUtils.navigateUpFromSameTask(this);
    }

    public void addIngredient(View view){
        closeKeyboard(view);

        TableRow new_row = new TableRow(this);
        TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.WRAP_CONTENT,1.0f);
        new_row.setLayoutParams(params);

        //Fill the AutoCompleteTextView with all the ingredients in the database
        adapter = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, ingredients_autocomplete);
        AutoCompleteTextView actv = new AutoCompleteTextView(this);
        actv.setLayoutParams(params);
        actv.setHint(R.string.ingredient_hint);
        actv.setInputType(InputType.TYPE_CLASS_TEXT);
        actv.setThreshold(1);//will start working from first character
        actv.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
        //actv.setTextColor(Color.RED);
        new_row.addView(actv);

        EditText new_amount_input = new EditText(this);
        new_amount_input.setLayoutParams(params);
        new_amount_input.setInputType(InputType.TYPE_CLASS_TEXT);
        new_amount_input.setHint(R.string.unit_hint);
        new_row.addView(new_amount_input);

        final ImageButton delete_ingredient = new ImageButton(this);
        delete_ingredient.setImageResource(R.drawable.icon_delete);
        delete_ingredient.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                deleteIngredient(v);
            }
        });

        new_row.addView(delete_ingredient);


        ingredient_table.addView(new_row);
    }

    public void deleteIngredient(View view){
        closeKeyboard(view);
        TableRow selected = (TableRow)view.getParent();
        ingredient_table.removeView(selected);
    }

    public void addStep(View view){
        closeKeyboard(view);
        TableRow new_row = new TableRow(this);
        new_row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        //new_row.setId(steps);

        EditText new_step_input = new EditText(this);
        new_step_input.setInputType(InputType.TYPE_CLASS_TEXT);
        new_step_input.setHint(R.string.step_hint);
        new_row.addView(new_step_input);

        final ImageButton delete_step = new ImageButton(this);
        delete_step.setImageResource(R.drawable.icon_delete);
        delete_step.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                deleteStep(v);
            }
        });

        new_row.addView(delete_step);


        direction_table.addView(new_row);
        //steps += 1;
    }

    public void deleteStep(View view){
        closeKeyboard(view);
        TableRow selected = (TableRow)view.getParent();
        direction_table.removeView(selected);
    }

    public void saveRecipe(View view){
        closeKeyboard(view);
        clearMessages(view);
        getData(view);
        if(check_valid_data(view) == false){
            return;
        }

        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        Recipe new_recipe = new Recipe(new_name, prep_time, cook_time, total_time, "blah.jpg",category);
        db.createRecipe(new_recipe);
        new_recipe = db.getRecipeByName(new_name);
        if(ingredient_names.length > 0) {
            saveIngredients(db, 0, new_recipe);
        }
        else if(directions.length > 0){
            db.closeDatabase();
            saveDirections(new_recipe);
        }
        else{
            db.closeDatabase();
            finishSaveRecipe(new_recipe);
        }
    }

    public void saveIngredients(final DatabaseHelper db, int index, final Recipe new_recipe){
        Ingredient new_ingredient = db.getIngredientByName(ingredient_names[index]);
        //Ingredient already exists
        if(new_ingredient.getId() != -1){
            db.createRecipeIngredient(new_recipe.getId(), db.getIngredientByName(ingredient_names[index]).getId(), ingredient_amounts[index]);
            if(index == ingredient_names.length){
                db.closeDatabase();
                saveDirections(new_recipe);
            }
            else {
                index += 1;
                saveIngredients(db, index, new_recipe);
            }
        }


        //Need to create the ingredient
        else{
            LayoutInflater factory = LayoutInflater.from(this);
            final View ingredientDialogView = factory.inflate(R.layout.new_ingredient_dialog, null);
            final AlertDialog ingredientDialog = new AlertDialog.Builder(this).create();
            ingredientDialog.setView(ingredientDialogView);

            TextView name_display = ingredientDialogView.findViewById(R.id.name_header);
            String header = "New Ingredient: " + ingredient_names[index];
            name_display.setText(header);

            final Spinner in_category_spinner = ingredientDialogView.findViewById(R.id.category_spinner);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                    R.array.categories_array, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            in_category_spinner.setAdapter(adapter);

            final Spinner in_pantry_spinner = ingredientDialogView.findViewById(R.id.pantry_spinner);
            ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                    R.array.y_or_n, android.R.layout.simple_spinner_item);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            in_pantry_spinner.setAdapter(adapter2);

            final String current_name = ingredient_names[index];
            final String amount = ingredient_amounts[index];
            final boolean last = (index == ingredient_names.length-1) ? true : false;
            final int inc = index + 1;
            ingredientDialogView.findViewById(R.id.save_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String cat = in_category_spinner.getSelectedItem().toString();
                    String in_pantry_string = in_pantry_spinner.getSelectedItem().toString();
                    int in_pantry = (in_pantry_string.equals("Yes")) ? 1 : 0;
                    Ingredient new_ingredient = new Ingredient(current_name, cat, in_pantry);
                    db.createIngredient(new_ingredient);
                    db.createRecipeIngredient(new_recipe.getId(), db.getIngredientByName(current_name).getId(), amount);

                    if(last) {
                        db.closeDatabase();
                        ingredientDialog.dismiss();
                        saveDirections(new_recipe);
                    }
                    else{
                        saveIngredients(db,inc,new_recipe);
                    }
                }
            });
            ingredientDialog.show();
        }
    }

    public void saveDirections(Recipe new_recipe){
        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        int sN = 0;
        for(String s: directions){
            db.createStep(new_recipe.getId(),s,sN);
            sN += 1;
        }
        db.closeDatabase();
        finishSaveRecipe(new_recipe);
    }

    public void finishSaveRecipe(Recipe new_recipe){
        new AlertDialog.Builder(this)
                .setMessage(new_recipe.getName() + " was added!")
                .setCancelable(false)
                .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        onBackPressed();
                    }
                })
                .show();
    }

    public void clearMessages(View view){
        empty.setVisibility(view.GONE);
        empty_time.setVisibility(view.GONE);
        empty_ingredient.setVisibility(view.GONE);
        empty_direction.setVisibility(view.GONE);
    }

    public void getData(View view){
        new_name = name_input.getText().toString();
        category = category_spinner.getSelectedItem().toString();
        prep_time = prep_input.getText().toString();
        cook_time = cook_input.getText().toString();
        total_time = total_input.getText().toString();

        int ingredient_rows_count = ingredient_table.getChildCount();
        ingredient_names = new String[ingredient_rows_count];
        ingredient_amounts = new String[ingredient_rows_count];
        EditText current_ingredient;
        EditText current_amount;
        for(int i = 0; i < ingredient_rows_count; i++) {
            TableRow current_row = (TableRow) ingredient_table.getChildAt(i);
            current_ingredient = (EditText) current_row.getChildAt(0);
            current_amount = (EditText) current_row.getChildAt(1);
            ingredient_names[i] = current_ingredient.getText().toString();
            ingredient_amounts[i] = current_amount.getText().toString();
        }

        int direction_rows_count = direction_table.getChildCount();
        directions = new String[direction_rows_count];
        EditText current_direction;
        for(int i = 0; i < direction_rows_count; i++) {
            TableRow current_row = (TableRow) direction_table.getChildAt(i);
            current_direction = (EditText) current_row.getChildAt(0);
            directions[i] = current_direction.getText().toString();
        }
    }

    public boolean check_valid_data(View view){
        //Name Check
        if(check_valid_name(view, new_name) == false)
            return false;
        //Times Check
        if(check_valid_times(view,prep_time,cook_time,total_time) == false)
            return false;
        //Ingredients Check
        if(check_valid_ingredients(view, ingredient_names, ingredient_amounts) == false)
            return false;
        //Directions Check
        if(check_valid_directions(view, directions) == false)
            return false;
        return true;
    }

    public boolean check_valid_name(View view, String new_name){
        if(new_name.trim().length() == 0){
            empty.setText("Please Input A Name");
            empty.setVisibility(view.VISIBLE);
            return false;
        }
        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        Recipe test_recipe = db.getRecipeByName(new_name);
        if(test_recipe.getId() != -1){
            empty.setText("A Recipe of That Name Already Exists");
            empty.setVisibility(view.VISIBLE);
            db.closeDatabase();
            return false;
        }
        db.closeDatabase();
        return true;
    }

    public boolean check_valid_times(View view, String prep_time, String cook_time, String total_time){
        if(prep_time.trim().length() < 1
                || cook_time.trim().length() < 1
                || total_time.trim().length() < 1) {
            empty_time.setVisibility(view.VISIBLE);
            return false;
        }
        return true;
    }

    public boolean check_valid_ingredients(View view, String[] ingredients, String[] amounts){
        if(ingredients.length != amounts.length) {
            return false;
        }

        for(int i = 0; i < ingredients.length; i++){
            if(ingredients[i].trim().length() < 1 || amounts[i].trim().length() < 1) {
                empty_ingredient.setVisibility(view.VISIBLE);
                return false;
            }
        }
        return true;
    }

    public boolean check_valid_directions(View view, String[] directions){
        for(int i = 0; i < directions.length; i++){
            if(directions[i].trim().length() < 1){
                empty_direction.setVisibility(view.VISIBLE);
                return false;
            }
        }
        return true;
    }

    public void cancelRecipe(View view){
        closeKeyboard(view);
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to cancel?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AddRecipe.this.finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }



    public void closeKeyboard(View view){
        try{
            InputMethodManager imm = (InputMethodManager) view.getContext()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        catch (Exception e){
            Log.e("focusInSave", e.toString());
        }
    }

    public String[] allIngredients(){
        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        List<String> ingredients = db.getAllIngredientNames();
        db.close();
        return ingredients.toArray(new String[ingredients.size()]);
    }
}
