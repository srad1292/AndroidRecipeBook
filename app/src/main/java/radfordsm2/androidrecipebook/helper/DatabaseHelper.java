package radfordsm2.androidrecipebook.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import radfordsm2.androidrecipebook.model.Ingredient;
import radfordsm2.androidrecipebook.model.Recipe;
import radfordsm2.androidrecipebook.model.RecipeIngredient;
import radfordsm2.androidrecipebook.model.Step;

/**
 * Created by smr12 on 12/30/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    // Logcat tag
    private static final String LOG = "DatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "recipeBook";

    // Table Names
    private static final String TABLE_RECIPE = "recipe";
    private static final String TABLE_INGREDIENT = "ingredient";
    private static final String TABLE_RECIPE_INGREDIENT= "recipe_ingredient";
    private static final String TABLE_STEP = "step";

    // Common column names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_CATEGORY = "category";
    private static final String KEY_RECIPE_ID = "recipe_id";

    // Recipe Table - column names
    private static final String KEY_PREP_TIME = "prep_time";
    private static final String KEY_COOK_TIME = "cook_time";
    private static final String KEY_TOTAL_TIME = "total_time";
    private static final String KEY_IMAGE = "image";

    // INGREDIENT Table - column names
    private static final String KEY_IN_PANTRY = "in_pantry";

    // RECIPE_INGREDIENT Table - column names
    private static final String KEY_INGREDIENT_ID = "ingredient_id";
    private static final String KEY_AMOUNT = "amount";

    // STEP Table - column names
    private static final String KEY_DIRECTION = "direction";
    private static final String KEY_STEP_NUMBER = "step_number";

    // Table Create Statements
    // Todo table create statement
    // Recipe table create statement
    private static final String CREATE_TABLE_RECIPE = "CREATE TABLE "
            + TABLE_RECIPE + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME
            + " TEXT," + KEY_PREP_TIME + " TEXT," + KEY_COOK_TIME
            + " TEXT," + KEY_TOTAL_TIME + " TEXT," + KEY_IMAGE + " TEXT,"
            + KEY_CATEGORY + " TEXT" + ")";

    // Ingredient table create statement
    private static final String CREATE_TABLE_INGREDIENT = "CREATE TABLE " + TABLE_INGREDIENT
            + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
            + KEY_CATEGORY + " TEXT," + KEY_IN_PANTRY + " INTEGER" + ")";

    // RecipeIngredient table create statement
    private static final String CREATE_TABLE_RECIPE_INGREDIENT = "CREATE TABLE "
            + TABLE_RECIPE_INGREDIENT + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_RECIPE_ID + " INTEGER," + KEY_INGREDIENT_ID + " INTEGER,"
            + KEY_AMOUNT + " TEXT" + ")";

    // Step table create statement
    private static final String CREATE_TABLE_STEP = "CREATE TABLE "
            + TABLE_STEP + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_RECIPE_ID + " INTEGER," + KEY_DIRECTION
            + " TEXT," + KEY_STEP_NUMBER + " INTEGER" + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(CREATE_TABLE_RECIPE);
        db.execSQL(CREATE_TABLE_INGREDIENT);
        db.execSQL(CREATE_TABLE_RECIPE_INGREDIENT);
        db.execSQL(CREATE_TABLE_STEP);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INGREDIENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPE_INGREDIENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STEP);

        // create new tables
        onCreate(db);
    }

    //Close the database when the connection is no longer needed
    public void closeDatabase() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }


    public void clearDatabase(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_STEP, null, null);
        db.delete(TABLE_RECIPE_INGREDIENT, null, null);
        db.delete(TABLE_INGREDIENT, null, null);
        db.delete(TABLE_RECIPE, null, null);
    }
    //CRUD methods for Recipe table
    //Create a recipe
    public long createRecipe(Recipe recipe) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, recipe.getName());
        values.put(KEY_PREP_TIME, recipe.getPrepTime());
        values.put(KEY_COOK_TIME, recipe.getCookTime());
        values.put(KEY_TOTAL_TIME, recipe.getTotalTime());
        values.put(KEY_IMAGE, recipe.getImage());
        values.put(KEY_CATEGORY, recipe.getCategory());

        return db.insert(TABLE_RECIPE, null, values);

    }

    //Retrieve all recipes
    public List<Recipe> getAllRecipes(){
        List<Recipe> recipes = new ArrayList<Recipe>();
        String selectQuery = "SELECT * FROM " + TABLE_RECIPE;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null && c.moveToFirst()){
            do {
                int id = c.getInt(c.getColumnIndex(KEY_ID));
                String name = c.getString(c.getColumnIndex(KEY_NAME));
                String prep = c.getString(c.getColumnIndex(KEY_PREP_TIME));
                String cook = c.getString(c.getColumnIndex(KEY_COOK_TIME));
                String total = c.getString(c.getColumnIndex(KEY_TOTAL_TIME));
                String image = c.getString(c.getColumnIndex(KEY_IMAGE));
                String category = c.getString(c.getColumnIndex(KEY_CATEGORY));
                Recipe recipe = new Recipe(id, name, prep, cook, total, image, category);

                recipes.add(recipe);
            } while(c.moveToNext());
            c.close();
        }
        return recipes;
    }

    //Retrieve recipe by ID
    public Recipe getRecipeByID(long recipe_id){
        String selectQuery = "SELECT * FROM " + TABLE_RECIPE
                + " WHERE " + KEY_ID + " = " + recipe_id;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null && c.moveToFirst()){
            int id = c.getInt(c.getColumnIndex(KEY_ID));
            String name = c.getString(c.getColumnIndex(KEY_NAME));
            String prep = c.getString(c.getColumnIndex(KEY_PREP_TIME));
            String cook = c.getString(c.getColumnIndex(KEY_COOK_TIME));
            String total = c.getString(c.getColumnIndex(KEY_TOTAL_TIME));
            String image = c.getString(c.getColumnIndex(KEY_IMAGE));
            String category = c.getString(c.getColumnIndex(KEY_CATEGORY));
            Recipe recipe = new Recipe(id, name, prep, cook, total, image, category);
            c.close();
            return recipe;

        }
        return new Recipe();
    }

    //Retrieve recipe by Name
    public Recipe getRecipeByName(String recipe_name){
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_RECIPE
                + " WHERE " + KEY_NAME + " = '" + recipe_name + "'";

        Log.e("GetRecipeByName", selectQuery);


        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null && c.moveToFirst()){

            int id = c.getInt(c.getColumnIndex(KEY_ID));
            String name = c.getString(c.getColumnIndex(KEY_NAME));
            String prep = c.getString(c.getColumnIndex(KEY_PREP_TIME));
            String cook = c.getString(c.getColumnIndex(KEY_COOK_TIME));
            String total = c.getString(c.getColumnIndex(KEY_TOTAL_TIME));
            String image = c.getString(c.getColumnIndex(KEY_IMAGE));
            String category = c.getString(c.getColumnIndex(KEY_CATEGORY));
            Recipe recipe = new Recipe(id, name, prep, cook, total, image, category);
            c.close();
            return recipe;

        }
        return new Recipe();
    }

    //Retrieve all recipes by Category
    public List<Recipe> getAllRecipesByCategory(String recipe_category){
        List<Recipe> recipes = new ArrayList<Recipe>();
        String selectQuery = "SELECT * FROM " + TABLE_RECIPE
                + " WHERE " + KEY_CATEGORY + " = '" + recipe_category + "'";

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null && c.moveToFirst()){
            do {
                int id = c.getInt(c.getColumnIndex(KEY_ID));
                String name = c.getString(c.getColumnIndex(KEY_NAME));
                String prep = c.getString(c.getColumnIndex(KEY_PREP_TIME));
                String cook = c.getString(c.getColumnIndex(KEY_COOK_TIME));
                String total = c.getString(c.getColumnIndex(KEY_TOTAL_TIME));
                String image = c.getString(c.getColumnIndex(KEY_IMAGE));
                String category = c.getString(c.getColumnIndex(KEY_CATEGORY));
                Recipe recipe = new Recipe(id, name, prep, cook, total, image, category);

                recipes.add(recipe);
            } while(c.moveToNext());
            c.close();
        }
        return recipes;
    }

    //Retrieve all recipe names by Category
    public List<String> getAllRecipeNamesByCategory(String recipe_category){
        List<String> recipes = new ArrayList<String>();
        String selectQuery = "SELECT * FROM " + TABLE_RECIPE
                + " WHERE " + KEY_CATEGORY + " = '" + recipe_category + "'"
                + " ORDER BY " + KEY_NAME;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null && c.moveToFirst()){
            do {
                String name = c.getString(c.getColumnIndex(KEY_NAME));
                recipes.add(name);
            } while(c.moveToNext());
            c.close();
        }
        return recipes;
    }

    //Update a recipe
    public int updateRecipe(Recipe old_recipe, Recipe recipe) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, recipe.getName());
        values.put(KEY_PREP_TIME, recipe.getPrepTime());
        values.put(KEY_COOK_TIME, recipe.getCookTime());
        values.put(KEY_TOTAL_TIME, recipe.getTotalTime());
        values.put(KEY_IMAGE, recipe.getImage());
        values.put(KEY_CATEGORY, recipe.getCategory());

        return db.update(TABLE_RECIPE, values, KEY_ID + " = ?", new String[] {String.valueOf(old_recipe.getId())});
    }

    //Delete a recipe
    public void deleteRecipe(long recipe_id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_RECIPE, KEY_ID + " = ?", new String[] { String.valueOf(recipe_id)});
    }

    //Get a count of recipes
    public int getRecipeCount() {
        String countQuery = "SELECT  * FROM " + TABLE_RECIPE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = 0;
        if(cursor != null) {
            count = cursor.getCount();
            cursor.close();
        }
        // return count
        return count;
    }

    //CRUD methods for Ingredient table
    //Create an ingredient
    public long createIngredient(Ingredient ingredient) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, ingredient.getName());
        values.put(KEY_CATEGORY, ingredient.getCategory());
        values.put(KEY_IN_PANTRY, ingredient.getInPantry());
        return db.insert(TABLE_INGREDIENT, null, values);

    }

    //Retrieve all ingredients
    public List<Ingredient> getAllIngredients(){
        List<Ingredient> ingredients = new ArrayList<Ingredient>();
        String selectQuery = "SELECT * FROM " + TABLE_INGREDIENT;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null && c.moveToFirst()){
            do {
                int id = c.getInt(c.getColumnIndex(KEY_ID));
                String name = c.getString(c.getColumnIndex(KEY_NAME));
                String category = c.getString(c.getColumnIndex(KEY_CATEGORY));
                int in_pantry = c.getInt(c.getColumnIndex(KEY_IN_PANTRY));
                Ingredient ingredient = new Ingredient(id, name, category, in_pantry);

                ingredients.add(ingredient);
            } while(c.moveToNext());
            c.close();
        }
        return ingredients;
    }

    public List<String> getAllIngredientNames(){
        List<String> ingredients = new ArrayList<String>();
        String selectQuery = "SELECT " + KEY_NAME + " FROM " + TABLE_INGREDIENT
                + " ORDER BY " + KEY_NAME;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null && c.moveToFirst()){
            do {
                String name = c.getString(c.getColumnIndex(KEY_NAME));

                ingredients.add(name);
            } while(c.moveToNext());
            c.close();
        }
        return ingredients;
    }

    //Retrieve ingredient by ID
    public Ingredient getIngredientByID(long ingredient_id){
        String selectQuery = "SELECT * FROM " + TABLE_INGREDIENT
                + " WHERE " + KEY_ID + " = " + ingredient_id;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null && c.moveToFirst()){

            int id = c.getInt(c.getColumnIndex(KEY_ID));
            String name = c.getString(c.getColumnIndex(KEY_NAME));
            String category = c.getString(c.getColumnIndex(KEY_CATEGORY));
            int in_pantry = c.getInt(c.getColumnIndex(KEY_IN_PANTRY));
            Ingredient ingredient = new Ingredient(id, name, category, in_pantry);
            c.close();
            return ingredient;

        }
        return new Ingredient();
    }

    //Retrieve ingredient by name
    public Ingredient getIngredientByName(String ingredient_name){
        String selectQuery = "SELECT * FROM " + TABLE_INGREDIENT
                + " WHERE " + KEY_NAME + " = '" + ingredient_name + "'";

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null && c.moveToFirst()){
            int id = c.getInt(c.getColumnIndex(KEY_ID));
            String name = c.getString(c.getColumnIndex(KEY_NAME));
            String category = c.getString(c.getColumnIndex(KEY_CATEGORY));
            int in_pantry = c.getInt(c.getColumnIndex(KEY_IN_PANTRY));
            Ingredient ingredient = new Ingredient(id, name, category, in_pantry);
            c.close();
            return ingredient;

        }
        return new Ingredient();
    }

    //Retrieve all ingredients by category
    public List<Ingredient> getAllIngredientsByCategory(String ingredient_category){
        List<Ingredient> ingredients = new ArrayList<Ingredient>();
        String selectQuery = "SELECT * FROM " + TABLE_INGREDIENT
                + " WHERE " + KEY_CATEGORY + " = '" + ingredient_category + "'"
                + " ORDER BY " + KEY_NAME;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null && c.moveToFirst()){
            do {
                int id = c.getInt(c.getColumnIndex(KEY_ID));
                String name = c.getString(c.getColumnIndex(KEY_NAME));
                String category = c.getString(c.getColumnIndex(KEY_CATEGORY));
                int in_pantry = c.getInt(c.getColumnIndex(KEY_IN_PANTRY));
                Ingredient ingredient = new Ingredient(id, name, category, in_pantry);

                ingredients.add(ingredient);
            } while(c.moveToNext());
            c.close();
        }
        return ingredients;
    }

    //Retrieve all ingredients by category in the pantry
    public List<String> getAllIngredientsByCategoryInPantry(String ingredient_category){
        List<String> ingredients = new ArrayList<String>();
        String selectQuery = "SELECT * FROM " + TABLE_INGREDIENT
                + " WHERE " + KEY_CATEGORY + " = '" + ingredient_category + "'"
                + " AND " + KEY_IN_PANTRY + " = " + 1
                + " ORDER BY " + KEY_NAME;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null && c.moveToFirst()){
            do {
                String name = c.getString(c.getColumnIndex(KEY_NAME));
                ingredients.add(name);
            } while(c.moveToNext());
            c.close();
        }
        return ingredients;
    }

    //Update an ingredient
    public int updateIngredient(Ingredient old_ingredient, Ingredient ingredient) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, ingredient.getName());
        values.put(KEY_CATEGORY, ingredient.getCategory());
        values.put(KEY_IN_PANTRY, ingredient.getInPantry());
        return db.update(TABLE_INGREDIENT, values, KEY_ID + " = ?", new String[] {String.valueOf(old_ingredient.getId())});
    }

    //Delete an ingredient
    public void deleteIngredient(long ingredient_id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_INGREDIENT, KEY_ID + " = ?", new String[] { String.valueOf(ingredient_id)});
    }

    //Get a count of ingredients
    public int getIngredientCount() {
        String countQuery = "SELECT  * FROM " + TABLE_INGREDIENT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = 0;
        if(cursor != null) {
            count = cursor.getCount();
            cursor.close();
        }
        // return count
        return count;
    }


    //CRUD methods for RecipeIngredient table
    //Create a RecipeIngredient
    public long createRecipeIngredient(long recipe_id, long ingredient_id, String amount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_RECIPE_ID, recipe_id);
        values.put(KEY_INGREDIENT_ID, ingredient_id);
        values.put(KEY_AMOUNT, amount);
        return db.insert(TABLE_RECIPE_INGREDIENT, null, values);

    }


    //Retrieve all RecipeIngredients
    public List<RecipeIngredient> getAllRecipeIngredient(){
        List<RecipeIngredient> ingredients = new ArrayList<RecipeIngredient>();
        String selectQuery = "SELECT * FROM " + TABLE_RECIPE_INGREDIENT;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null && c.moveToFirst()){
            do {
                int id = c.getInt(c.getColumnIndex(KEY_ID));
                int r_id = c.getInt(c.getColumnIndex(KEY_RECIPE_ID));
                int ingredient_id = c.getInt(c.getColumnIndex(KEY_INGREDIENT_ID));
                String amount = c.getString(c.getColumnIndex(KEY_AMOUNT));
                RecipeIngredient ri = new RecipeIngredient(id, r_id, ingredient_id, amount);

                ingredients.add(ri);
            } while(c.moveToNext());
            c.close();
        }
        return ingredients;
    }
    //Retrieve all RecipeIngredients by recipe
    public List<RecipeIngredient> getAllIngredientsByRecipe(long recipe_id){
        List<RecipeIngredient> ingredients = new ArrayList<RecipeIngredient>();
        String selectQuery = "SELECT * FROM " + TABLE_RECIPE_INGREDIENT
                + " WHERE " + KEY_RECIPE_ID + " = " + recipe_id;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null && c.moveToFirst()){
            do {
                int id = c.getInt(c.getColumnIndex(KEY_ID));
                int r_id = c.getInt(c.getColumnIndex(KEY_RECIPE_ID));
                int ingredient_id = c.getInt(c.getColumnIndex(KEY_INGREDIENT_ID));
                String amount = c.getString(c.getColumnIndex(KEY_AMOUNT));
                RecipeIngredient ri = new RecipeIngredient(id, r_id, ingredient_id, amount);

                ingredients.add(ri);
            } while(c.moveToNext());
            c.close();
        }
        return ingredients;
    }

    //Update a RecipeIngredient
    public int updateRecipeIngredient(RecipeIngredient recipeIngredient) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_RECIPE_ID, recipeIngredient.getRecipeId());
        values.put(KEY_INGREDIENT_ID, recipeIngredient.getIngredientId());
        values.put(KEY_AMOUNT, recipeIngredient.getAmount());
        return db.update(TABLE_RECIPE_INGREDIENT, values, KEY_ID + " = ?", new String[] {String.valueOf(recipeIngredient.getId())});
    }

    //Delete an RecipeIngredient
    public void deleteRecipeIngredient(long recipe_ingredient_id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_RECIPE_INGREDIENT, KEY_ID + " = ?", new String[] { String.valueOf(recipe_ingredient_id)});
    }

    //Delete a RecipeIngredients Based On Recipe
    public void deleteRecipeIngredientByRecipe(long recipe_id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_RECIPE_INGREDIENT, KEY_RECIPE_ID + " = ?", new String[] { String.valueOf(recipe_id)});
    }

    //Get a count of RecipeIngredients
    public int getRecipeIngredientCount() {
        String countQuery = "SELECT  * FROM " + TABLE_RECIPE_INGREDIENT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = 0;
        if(cursor != null) {
            count = cursor.getCount();
            cursor.close();
        }
        // return count
        return count;
    }


    //CRUD methods for Step table
    //Create a Step
    public long createStep(long recipe_id, String direction, int stepNumber) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_RECIPE_ID, recipe_id);
        values.put(KEY_DIRECTION, direction);
        values.put(KEY_STEP_NUMBER, stepNumber);
        return db.insert(TABLE_STEP, null, values);

    }

    //Retrieve all Steps
    public List<Step> getAllSteps(){
        List<Step> steps = new ArrayList<Step>();
        String selectQuery = "SELECT * FROM " + TABLE_STEP;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null && c.moveToFirst()){
            do {
                int id = c.getInt(c.getColumnIndex(KEY_ID));
                int r_id = c.getInt(c.getColumnIndex(KEY_RECIPE_ID));
                String direction = c.getString(c.getColumnIndex(KEY_DIRECTION));
                int stepNumber = c.getInt(c.getColumnIndex(KEY_STEP_NUMBER));
                Step step = new Step(id, r_id, direction, stepNumber);

                steps.add(step);
            } while(c.moveToNext());
            c.close();
        }
        return steps;
    }

    //Retrieve all Steps by recipe
    public List<Step> getAllStepsByRecipe(long recipe_id){
        List<Step> steps = new ArrayList<Step>();
        String selectQuery = "SELECT * FROM " + TABLE_STEP
                + " WHERE " + KEY_RECIPE_ID + " = " + recipe_id;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null && c.moveToFirst()){
            do {
                int id = c.getInt(c.getColumnIndex(KEY_ID));
                int r_id = c.getInt(c.getColumnIndex(KEY_RECIPE_ID));
                String direction = c.getString(c.getColumnIndex(KEY_DIRECTION));
                int stepNumber = c.getInt(c.getColumnIndex(KEY_STEP_NUMBER));
                Step step = new Step(id, r_id, direction, stepNumber);

                steps.add(step);
            } while(c.moveToNext());
            c.close();
        }
        return steps;
    }

    //Update a Step
    public int updateStep(Step step) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_RECIPE_ID, step.getRecipeId());
        values.put(KEY_DIRECTION, step.getDirection());
        values.put(KEY_STEP_NUMBER, step.getStepNumber());
        return db.update(TABLE_STEP, values, KEY_ID + " = ?", new String[] {String.valueOf(step.getId())});
    }

    //Delete a Step
    public void deleteStep(long step_id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_STEP, KEY_ID + " = ?", new String[] { String.valueOf(step_id)});
    }

    //Delete all steps from a recipe
    public void deleteStepsRecipe(long recipe_id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_STEP, KEY_RECIPE_ID + " = ?", new String[] { String.valueOf(recipe_id)});
    }

    //Get a count of steps
    public int getStepCount() {
        String countQuery = "SELECT  * FROM " + TABLE_STEP;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = 0;
        if(cursor != null) {
            count = cursor.getCount();
            cursor.close();
        }
        // return count
        return count;
    }
}
