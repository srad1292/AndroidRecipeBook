package radfordsm2.androidrecipebook;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import radfordsm2.androidrecipebook.helper.DatabaseHelper;
import radfordsm2.androidrecipebook.model.Ingredient;

public class AddIngredient extends AppCompatActivity {

    EditText ingredient_name;
    Spinner category_spinner;
    Spinner in_spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredient);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        ingredient_name = (EditText)findViewById(R.id.name_input);


        category_spinner = (Spinner) findViewById(R.id.category_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categories_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        category_spinner.setAdapter(adapter);

        in_spinner = (Spinner) findViewById(R.id.pantry_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.y_or_n, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        in_spinner.setAdapter(adapter2);
    }

    @Override
    public void onBackPressed(){
        NavUtils.navigateUpFromSameTask(this);
    }



    public void saveIngredient(View view){
        try{
            InputMethodManager imm = (InputMethodManager) view.getContext()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        catch (Exception e){
            Log.e("focusInSave", e.toString());
        }
        TextView empty = (TextView)findViewById(R.id.empty_name_error);
        TextView dup = (TextView)findViewById(R.id.duplicate_name_error);
        empty.setVisibility(view.GONE);
        dup.setVisibility(view.GONE);
        String new_name = ingredient_name.getText().toString();
        String new_category = category_spinner.getSelectedItem().toString();
        int new_in_pantry = in_spinner.getSelectedItem().toString().equals("Yes") ? 1 : 0;
        //Empty name input
        if(new_name.length() == 0){
            empty.setVisibility(view.VISIBLE);
        }
        //Some text in the name input
        else {
            DatabaseHelper db = new DatabaseHelper(getApplicationContext());
            Ingredient test = db.getIngredientByName(new_name);
            //There is no ingredient already with that name
            if (test.getId() == -1) {
                try {
                    Ingredient new_ingredient = new Ingredient(new_name, new_category, new_in_pantry);
                    db.createIngredient(new_ingredient);
                } catch (Exception e) {
                    Log.e("saveIngredient ", "Error creating and inserting ingredient");
                } finally {
                    db.close();
                    NavUtils.navigateUpFromSameTask(this);
                }
            }
            //A duplicate ingredient
            else {
                dup.setVisibility(view.VISIBLE);
            }
        }

    }

    public void cancelIngredient(View view){
        try{
            InputMethodManager imm = (InputMethodManager) view.getContext()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        catch (Exception e){
            Log.e("focusInSave", e.toString());
        }
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to cancel?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AddIngredient.this.finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }






}
