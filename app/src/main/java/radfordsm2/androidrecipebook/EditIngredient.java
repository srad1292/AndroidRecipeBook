package radfordsm2.androidrecipebook;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import radfordsm2.androidrecipebook.helper.DatabaseHelper;
import radfordsm2.androidrecipebook.model.Ingredient;

public class EditIngredient extends AppCompatActivity {

    EditText ingredient_name;
    Spinner category_spinner;
    Spinner in_spinner;
    String old_name, new_name;
    String old_category, new_category;
    int old_in_pantry, new_in_pantry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_ingredient);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        Intent intent = getIntent();
        String selected = intent.getStringExtra("SELECTED_INGREDIENT");

        Log.e("EditIngredient", selected);
        old_name = selected;
        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        Ingredient ing = db.getIngredientByName(selected);
        db.closeDatabase();
        old_category = ing.getCategory();
        old_in_pantry = ing.getInPantry();

        ingredient_name = (EditText)findViewById(R.id.name_input);
        ingredient_name.setText(old_name);

        category_spinner = (Spinner) findViewById(R.id.category_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categories_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        category_spinner.setAdapter(adapter);
        // Set the selected value to match the record
        category_spinner.setSelection(adapter.getPosition(old_category));


        in_spinner = (Spinner) findViewById(R.id.pantry_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.y_or_n, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        in_spinner.setAdapter(adapter2);
        String in_string = old_in_pantry == 1 ? "Yes": "No";
        in_spinner.setSelection(adapter2.getPosition(in_string));

        category_spinner.setOnTouchListener(Spinner_OnTouch);
        in_spinner.setOnTouchListener(Spinner_OnTouch);
    }

    @Override
    public void onBackPressed(){
        NavUtils.navigateUpFromSameTask(this);
    }



    public void saveIngredient(View view){
        closeKeyboard(view);
        TextView empty = (TextView)findViewById(R.id.empty_name_error);
        TextView dup = (TextView)findViewById(R.id.duplicate_name_error);
        empty.setVisibility(view.GONE);
        dup.setVisibility(view.GONE);
        new_name = ingredient_name.getText().toString();
        new_category = category_spinner.getSelectedItem().toString();
        new_in_pantry = in_spinner.getSelectedItem().toString().equals("Yes") ? 1 : 0;
        //Empty name input
        if(new_name.length() == 0){
            empty.setVisibility(view.VISIBLE);
        }
        //Some text in the name input
        else {
            DatabaseHelper db = new DatabaseHelper(getApplicationContext());
            Ingredient test = db.getIngredientByName(new_name);
            //If the name wasn't changed or if the name was changed and doesn't already exist
            //Within the database, then update the database
            if(new_name.equals(old_name) || (!new_name.equals(old_name) && test.getId()== -1)){
                try {
                    Ingredient new_ingredient = new Ingredient(new_name, new_category, new_in_pantry);
                    Ingredient old = db.getIngredientByName(old_name);
                    db.updateIngredient(old, new_ingredient);
                } catch (Exception e) {
                    Log.e("saveIngredient ", "Error Updating ingredient");
                } finally {
                    db.closeDatabase();
                    NavUtils.navigateUpFromSameTask(this);
                }
            }
            else {
                dup.setVisibility(view.VISIBLE);
                db.closeDatabase();
            }

        }
    }

    public void cancelIngredient(View view){
        closeKeyboard(view);
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to cancel?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        EditIngredient.this.finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    public void deleteIngredient(View view){
        closeKeyboard(view);
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to delete this ingredient?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        removeRecord();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    public void removeRecord(){
        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        try{
            Ingredient ingredient = db.getIngredientByName(old_name);
            db.deleteIngredient(ingredient.getId());
        }
        catch (Exception e){
            Log.e("removeRecord", e.toString());
        }
        finally{
            db.closeDatabase();
            NavUtils.navigateUpFromSameTask(this);
        }
    }

    private View.OnTouchListener Spinner_OnTouch = new View.OnTouchListener() {
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                closeKeyboard(v);
            }
            return false;
        }
    };

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

}
