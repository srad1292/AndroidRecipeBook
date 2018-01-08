package radfordsm2.androidrecipebook;

import android.content.Intent;
import android.support.v4.app.NavUtils;
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
import radfordsm2.androidrecipebook.model.Recipe;

public class Recipes extends AppCompatActivity {

    private ExpandableListAdapter listAdapter;
    private ExpandableListView expListView;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listDataChild;
    private List<String> breakfast, appetizers, soups, salads, sides;
    private List<String> main, desserts, drinks, breads, dips, snacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);
        Toolbar myToolbar = findViewById(R.id.recipes_toolbar);
        setSupportActionBar(myToolbar);

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.recipes_accordion);

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
                // TODO Auto-generated method stub
                Toast.makeText(
                        getApplicationContext(),
                        listDataHeader.get(groupPosition)
                                + " : "
                                + listDataChild.get(
                                listDataHeader.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT)
                        .show();
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.recipes_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_menu_add_recipe:
                Log.i("onOptionsItemSelected", "Add recipe button pressed");
                startAddRecipe();
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

    private void prepareListData() {

        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Breakfast");
        listDataHeader.add("Appetizers");
        listDataHeader.add("Soups");
        listDataHeader.add("Salads");
        listDataHeader.add("Sides");
        listDataHeader.add("Main Dishes");
        listDataHeader.add("Desserts");
        listDataHeader.add("Drinks");
        listDataHeader.add("Breads and Pastries");
        listDataHeader.add("Dips and Spreads");
        listDataHeader.add("Snacks");

        breakfast = new ArrayList<String>();
        appetizers = new ArrayList<String>();
        soups = new ArrayList<String>();
        salads = new ArrayList<String>();
        sides = new ArrayList<String>();
        main = new ArrayList<String>();
        desserts = new ArrayList<String>();
        drinks = new ArrayList<String>();
        breads = new ArrayList<String>();
        dips = new ArrayList<String>();
        snacks = new ArrayList<String>();
    }

    /**
     * Fill the categories with correct data
     * i.e breakfast = db.getAllRecipeNamesByCategory("Breakfast")
     * For each recipe in the Recipe table
     */
    private void prepareListChildren() {


        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        breakfast = db.getAllRecipeNamesByCategory("Breakfast");
        appetizers = db.getAllRecipeNamesByCategory("Appetizers");
        soups = db.getAllRecipeNamesByCategory("Soups");
        salads = db.getAllRecipeNamesByCategory("Salads");
        sides = db.getAllRecipeNamesByCategory("Sides");
        main = db.getAllRecipeNamesByCategory("Main Dishes");
        desserts = db.getAllRecipeNamesByCategory("Desserts");
        drinks = db.getAllRecipeNamesByCategory("Drinks");
        breads = db.getAllRecipeNamesByCategory("Breads and Pastries");
        dips = db.getAllRecipeNamesByCategory("Dips and Spreads");
        snacks = db.getAllRecipeNamesByCategory("Snacks");
        db.closeDatabase();

        listDataChild.put(listDataHeader.get(0), breakfast);
        listDataChild.put(listDataHeader.get(1), appetizers);
        listDataChild.put(listDataHeader.get(2), soups);
        listDataChild.put(listDataHeader.get(3), salads);
        listDataChild.put(listDataHeader.get(4), sides);
        listDataChild.put(listDataHeader.get(5), main);
        listDataChild.put(listDataHeader.get(6), desserts);
        listDataChild.put(listDataHeader.get(7), drinks);
        listDataChild.put(listDataHeader.get(8), breads);
        listDataChild.put(listDataHeader.get(9), dips);
        listDataChild.put(listDataHeader.get(10), snacks);
    }

    public void startAddRecipe() {
        Intent intent = new Intent(this, AddRecipe.class);
        startActivity(intent);
    }

}