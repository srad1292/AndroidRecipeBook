package radfordsm2.androidrecipebook;

import android.content.DialogInterface;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import java.util.List;

import radfordsm2.androidrecipebook.helper.DatabaseHelper;

public class AddRecipe extends AppCompatActivity {

    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        adapter = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, allIngredients());
        AutoCompleteTextView actv = (AutoCompleteTextView) findViewById(R.id.ingredient_1);
        actv.setThreshold(1);//will start working from first character
        actv.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
        //actv.setTextColor(Color.RED);

    }

    @Override
    public void onBackPressed(){
        NavUtils.navigateUpFromSameTask(this);
    }

    public void addIngredient(View view){

    }

    public void deleteIngredient(View view){

    }

    public void addStep(View view){

    }

    public void deleteStep(View view){

    }

    public void saveRecipe(View view){

    }

    public void cancelRecipe(View view){
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

    public String[] allIngredients(){
        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        List<String> ingredients = db.getAllIngredientNames();
        return ingredients.toArray(new String[ingredients.size()]);
    }
}
