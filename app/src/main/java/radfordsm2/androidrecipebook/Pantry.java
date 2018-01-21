package radfordsm2.androidrecipebook;


import android.content.DialogInterface;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import radfordsm2.androidrecipebook.helper.DatabaseHelper;
import radfordsm2.androidrecipebook.model.Ingredient;

public class Pantry extends AppCompatActivity {

    private ExpandableListAdapter listAdapter;
    private ExpandableListView expListView;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listDataChild;
    private List<String> meats, fruitveggie, beverages, alcohol, bread;
    private List<String> dips, cheese, canned, cereal, snacks, sweets, mixes;
    private List<String> sauces, seasoning, dressing, oil, other;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantry);
        Toolbar myToolbar = findViewById(R.id.pantry_toolbar);
        setSupportActionBar(myToolbar);


        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.pantry_accordion);

        // preparing list data
        prepareListData();
        prepareListChildren();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        // Listview Group click listener
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                // Toast.makeText(getApplicationContext(),
                // "Group Clicked " + listDataHeader.get(groupPosition),
                // Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Expanded",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Collapsed",
                        Toast.LENGTH_SHORT).show();

            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                startEditIngredient(listDataChild.get(
                        listDataHeader.get(groupPosition)).get(
                        childPosition));

                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.pantry_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_menu_add_ingredient:
                Log.i("onOptionsItemSelected", "Add recipe button pressed");
                startAddIngredient();
                return true;
            case R.id.action_menu_edit_ingredient:
                Log.i("onOptionsItemSelected", "Add recipe button pressed");
                editIngredientPopup();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void onBackPressed(){
        NavUtils.navigateUpFromSameTask(this);
    }


    private void prepareListData() {

        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String,List<String>>();

        // Adding child data
        listDataHeader.add("Meats, Poultry, and Seafood");
        listDataHeader.add("Fruits and Vegetables");
        listDataHeader.add("Beverages");
        listDataHeader.add("Alcohol and Liquor");
        listDataHeader.add("Bread");
        listDataHeader.add("Dips and Spreads");
        listDataHeader.add("Cheese");
        listDataHeader.add("Canned Goods");
        listDataHeader.add("Cereal, Oatmeal, and Grains");
        listDataHeader.add("Snacks");
        listDataHeader.add("Sweets");
        listDataHeader.add("Mixes");
        listDataHeader.add("Sauces");
        listDataHeader.add("Seasonings, Spices, and Basics");
        listDataHeader.add("Dressings");
        listDataHeader.add("Oils and Vinegars");
        listDataHeader.add("Other");

        meats = new ArrayList<String>();
        fruitveggie = new ArrayList<String>();
        beverages = new ArrayList<String>();
        alcohol = new ArrayList<String>();
        bread = new ArrayList<String>();
        dips = new ArrayList<String>();
        cheese = new ArrayList<String>();
        canned = new ArrayList<String>();
        cereal = new ArrayList<String>();
        snacks = new ArrayList<String>();
        sweets = new ArrayList<String>();
        mixes = new ArrayList<String>();
        sauces = new ArrayList<String>();
        seasoning = new ArrayList<String>();
        dressing = new ArrayList<String>();
        oil = new ArrayList<String>();
        other = new ArrayList<String>();

    }


    private void prepareListChildren() {


        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        meats = db.getAllIngredientsByCategoryInPantry("Meats, Poultry, and Seafood");
        fruitveggie = db.getAllIngredientsByCategoryInPantry("Fruits and Vegetables");
        beverages = db.getAllIngredientsByCategoryInPantry("Beverages");
        alcohol = db.getAllIngredientsByCategoryInPantry("Alcohol and Liquor");
        bread = db.getAllIngredientsByCategoryInPantry("Bread");
        dips = db.getAllIngredientsByCategoryInPantry("Dips and Spreads");
        cheese = db.getAllIngredientsByCategoryInPantry("Cheese");
        canned = db.getAllIngredientsByCategoryInPantry("Canned Goods");
        cereal = db.getAllIngredientsByCategoryInPantry("Cereal, Oatmeal, and Grains");
        snacks = db.getAllIngredientsByCategoryInPantry("Snacks");
        sweets = db.getAllIngredientsByCategoryInPantry("Sweets");
        mixes = db.getAllIngredientsByCategoryInPantry("Mixes");
        sauces = db.getAllIngredientsByCategoryInPantry("Sauces");
        seasoning = db.getAllIngredientsByCategoryInPantry("Seasonings, Spices, and Basics");
        dressing = db.getAllIngredientsByCategoryInPantry("Dressings");
        oil = db.getAllIngredientsByCategoryInPantry("Oils and Vinegars");
        other = db.getAllIngredientsByCategoryInPantry("Other");
        db.closeDatabase();

        listDataChild.put(listDataHeader.get(0), meats);
        listDataChild.put(listDataHeader.get(1), fruitveggie);
        listDataChild.put(listDataHeader.get(2), beverages);
        listDataChild.put(listDataHeader.get(3), alcohol);
        listDataChild.put(listDataHeader.get(4), bread);
        listDataChild.put(listDataHeader.get(5), dips);
        listDataChild.put(listDataHeader.get(6), cheese);
        listDataChild.put(listDataHeader.get(7), canned);
        listDataChild.put(listDataHeader.get(8), cereal);
        listDataChild.put(listDataHeader.get(9), snacks);
        listDataChild.put(listDataHeader.get(10), sweets);
        listDataChild.put(listDataHeader.get(11), mixes);
        listDataChild.put(listDataHeader.get(12), sauces);
        listDataChild.put(listDataHeader.get(13), seasoning);
        listDataChild.put(listDataHeader.get(14), dressing);
        listDataChild.put(listDataHeader.get(15), oil);
        listDataChild.put(listDataHeader.get(16), other);
    }


    public void editIngredientPopup(){
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("Select Ingredient");
        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        List<String> ingredients = db.getAllIngredientNames();
        final String[] ingredient_names = ingredients.toArray(new String[ingredients.size()]);
        b.setItems(ingredient_names, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
                startEditIngredient(ingredient_names[which]);
            }


        });

        b.show();
    }


    public void startAddIngredient(){
        Intent intent = new Intent(this, AddIngredient.class);
        startActivity(intent);
    }

    public void startEditIngredient(String name){
        Intent intent = new Intent(this, EditIngredient.class);
        Log.e("startEdit", name);
        intent.putExtra("SELECTED_INGREDIENT", name);
        startActivity(intent);
    }
}
