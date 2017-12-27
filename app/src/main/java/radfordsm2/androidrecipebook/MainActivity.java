package radfordsm2.androidrecipebook;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;


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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
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
}
