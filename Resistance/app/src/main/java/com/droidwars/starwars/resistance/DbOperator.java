package com.droidwars.starwars.resistance;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.droidwars.starwars.resistance.ListAdapter.ListModel;

import java.util.ArrayList;

public class DbOperator extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Resistance.db";
    protected static final String TABLE_NAME = "warrior_details";

    public static final String CREATE_TABLE = "create table if not exists "
            + TABLE_NAME
            + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT NOT NULL, AFF TEXT, SPECIES TEXT, GENDER TEXT, LSO TEXT, PLANET TEXT,IMAGE TEXT);";



    public DbOperator(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);

        //db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXITS "+ TABLE_NAME);
        onCreate(db);
    }
    public boolean insertWarrior  (String name, String aff, String species, String gender,String lso,String planet,String image)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("NAME", name);
        contentValues.put("AFF", aff);
        contentValues.put("SPECIES", species);
        contentValues.put("GENDER", gender);
        contentValues.put("LSO", lso);
        contentValues.put("PLANET", planet);
        contentValues.put("IMAGE",image);
        if(db.insert(TABLE_NAME, null, contentValues) != -1)
            return true;
        else
            return false;
    }

    public ContentValues getData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("select * from " + TABLE_NAME + " where id=" + id + "", null);

        res.moveToFirst();
        ContentValues contentValues = new ContentValues();
        contentValues.put("NAME", res.getString(res.getColumnIndex("NAME")));
        contentValues.put("AFF", res.getString(res.getColumnIndex("AFF")));
        contentValues.put("SPECIES", res.getString(res.getColumnIndex("SPECIES")));
        contentValues.put("GENDER", res.getString(res.getColumnIndex("GENDER")));
        contentValues.put("LSO", res.getString(res.getColumnIndex("LSO")));
        contentValues.put("PLANET", res.getString(res.getColumnIndex("PLANET")));
        contentValues.put("IMAGE", res.getString(res.getColumnIndex("IMAGE")));
        return contentValues;
    }


    public boolean updateWarrior (int id,String name, String aff, String species, String gender,String lso,String planet,String image)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("NAME", name);
        contentValues.put("AFF", aff);
        contentValues.put("SPECIES", species);
        contentValues.put("GENDER", gender);
        contentValues.put("LSO", lso);
        contentValues.put("PLANET", planet);
        contentValues.put("IMAGE",image);
        if(db.update(TABLE_NAME, contentValues, "id = ? ", new String[] { Integer.toString(id) } ) >= 1)
            return true;
        else
            return false;
    }

    public Integer deleteWarrior (Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "id = ? ", new String[] { Integer.toString(id) });
    }

    public ArrayList<ListModel> getAllWarriorDetails()
    {
        ArrayList<ListModel> array_list = new ArrayList<ListModel>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from " + TABLE_NAME, null );
        res.moveToFirst();
        while (res.isAfterLast() == false) {

            final ListModel sched = new ListModel();

            /******* Firstly take data in model object ******/
            sched.setId(res.getInt(res.getColumnIndex("ID")));
            sched.setWarriorName(res.getString(res.getColumnIndex("NAME")));
            sched.setGender(res.getString(res.getColumnIndex("GENDER")));
            sched.setSide(res.getString(res.getColumnIndex("AFF")));
            sched.setWarriorImage(res.getString(res.getColumnIndex("IMAGE")));


            /******** Take Model Object in ArrayList **********/
            array_list.add(sched);
            res.moveToNext();
        }

        return array_list;
    }
}
