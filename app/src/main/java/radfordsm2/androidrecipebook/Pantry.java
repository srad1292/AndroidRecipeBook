package radfordsm2.androidrecipebook;


import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Pantry extends AppCompatActivity {

    private ExpandableListAdapter listAdapter;
    private ExpandableListView expListView;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listDataChild;
    private List<String> meats, fruitveggie, beverages, alcohol, bread;
    private List<String> dips, canned, cereal, snacks, sweets, mixes;
    private List<String> sauces, seasoning, dressing, oil, other;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantry);
        /**
         Toolbar myToolbar = (Toolbar) findViewById(R.id.pantry_toolbar);
         myToolbar.setTitle("Pantry");
         setSupportActionBar(myToolbar);
         */

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

        meats.add("Salmon");
        meats.add("Sirloin Steak");
        fruitveggie.add("Apples");
        fruitveggie.add("Oranges");
        fruitveggie.add("Blueberries");

        listDataChild.put(listDataHeader.get(0), meats);
        listDataChild.put(listDataHeader.get(1), fruitveggie);
        listDataChild.put(listDataHeader.get(2), beverages);
        listDataChild.put(listDataHeader.get(3), alcohol);
        listDataChild.put(listDataHeader.get(4), bread);
        listDataChild.put(listDataHeader.get(5), dips);
        listDataChild.put(listDataHeader.get(6), canned);
        listDataChild.put(listDataHeader.get(7), cereal);
        listDataChild.put(listDataHeader.get(8), snacks);
        listDataChild.put(listDataHeader.get(9), sweets);
        listDataChild.put(listDataHeader.get(10), mixes);
        listDataChild.put(listDataHeader.get(11), sauces);
        listDataChild.put(listDataHeader.get(12), seasoning);
        listDataChild.put(listDataHeader.get(13), dressing);
        listDataChild.put(listDataHeader.get(14), oil);
        listDataChild.put(listDataHeader.get(15), other);
    }
}
