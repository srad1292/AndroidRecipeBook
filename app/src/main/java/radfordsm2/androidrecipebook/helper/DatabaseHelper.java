package radfordsm2.androidrecipebook.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
            + KEY_CATEGORY + " TEXT," + ")";

    // Ingredient table create statement
    private static final String CREATE_TABLE_INGREDIENT = "CREATE TABLE " + TABLE_INGREDIENT
            + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
            + KEY_CATEGORY + " TEXT," + ")";

    // RecipeIngredient table create statement
    private static final String CREATE_TABLE_RECIPE_INGREDIENT = "CREATE TABLE "
            + TABLE_RECIPE_INGREDIENT + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_RECIPE_ID + " INTEGER," + KEY_INGREDIENT_ID + " INTEGER,"
            + KEY_AMOUNT + " Text" + ")";

    // Step table create statement
    private static final String CREATE_TABLE_STEP = "CREATE TABLE "
            + TABLE_STEP + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_RECIPE_ID + " INTEGER," + KEY_DIRECTION
            + " Text," + KEY_STEP_NUMBER + " INTEGER" + ")";

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
}
