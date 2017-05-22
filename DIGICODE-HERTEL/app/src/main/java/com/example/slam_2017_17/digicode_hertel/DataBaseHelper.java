package com.example.slam_2017_17.digicode_hertel;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by slam-2017-17 on 21/04/2017.
 */

public class DataBaseHelper extends SQLiteOpenHelper {
    //The Android's default system path of your application database.
    private static String DB_PATH = "/data/data/com.example.slam_2017_17.digicode_hertel/databases/";

    private static String DB_NAME = "bdfredi.db";

    private SQLiteDatabase myDataBase;

    private final Context myContext;

    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     * @param context
     */
    public DataBaseHelper(Context context) {

        super(context, DB_NAME, null, 1);
        this.myContext = context;
    }

    /**
     * Creates a empty database on the system and rewrites it with your own database.
     * */
    public void createDataBase() throws IOException {

        boolean dbExist = checkDataBase();

        if(dbExist){
//do nothing - database already exist
        }else{

//By calling this method and empty database will be created into the default system path
//of your application so we are gonna be able to overwrite that database with our database.
            this.getReadableDatabase();
// ajout car erreur sur lecture


            try {

                copyDataBase();

            } catch (IOException e) {

                throw new Error("Error copying database");

            }
        }
//ajout car erreur sur lecture
        this.close();
    }

    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase(){

        SQLiteDatabase checkDB = null;

        try{
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

        }catch(SQLiteException e){

//database n'existe pas deja.

        }

        if(checkDB != null){

            checkDB.close();

        }

        return checkDB != null ? true : false;
    }

    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
    private void copyDataBase() throws IOException{

//Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DB_NAME);

// Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;

//Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

//transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }

//Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    public void openDataBase() throws SQLException {

//Open the database
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

    }

    @Override
    public synchronized void close() {

        if(myDataBase != null)
            myDataBase.close();

        super.close();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

// Add your public helper methods to access and get content from the database.
// You could return cursors by doing "return myDataBase.query(....)" so it'd be easy
// to you to create adapters for your views.


    public boolean login(String login, String password) {

        try {
            createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor mCursor = db.rawQuery("SELECT * FROM users WHERE login=? AND password=?", new String[]{login,password});

        if (mCursor != null) {

            if(mCursor.getCount() > 0)
            {
                return true;
            }
        }

        return false;
    }

    public String recupDigicode(Integer jour, Integer salle){
        String res ="";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor mCursor = db.rawQuery("SELECT digi FROM digicode WHERE jour =? AND salle=?",new String[]{String.valueOf(jour),String.valueOf(salle)});
        if(mCursor.moveToFirst())
            res = mCursor.getString(0);
        else
            res= "Aucun code ne correspond à votre séléction";
        return res;
    }

    public String[] recupSalles()
    {
        String chaine = "";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor mCursor = db.rawQuery("SELECT * FROM salles", null);

        while(mCursor.moveToNext())
        {
            chaine += mCursor.getString(1) + "-";
        }
        return  chaine.split("-");
    }


}
