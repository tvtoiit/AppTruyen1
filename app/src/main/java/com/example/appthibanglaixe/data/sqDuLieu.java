package com.example.appthibanglaixe.data;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.example.appthibanglaixe.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class sqDuLieu extends SQLiteOpenHelper {
    private static final String TAG = sqDuLieu.class.getSimpleName();

    private Resources mResources;
    private static final String DB_Name = "banglaixea1.db";
    private static final int DB_VERSION = 1;
    SQLiteDatabase db;
    private static sqDuLieu instance = null;
    public synchronized static sqDuLieu getInstance(Context context){
        if(instance == null){
            instance = new sqDuLieu(context);
        }
        return instance;
    }
    public sqDuLieu(Context context) {
        super(context, DB_Name, null, DB_VERSION);
        mResources = context.getResources();

        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_LOGIN_TABLE = "CREATE TABLE " + DbContract.LoginEntry.TABLE_NAME_LOGIN + " (" +
                DbContract.LoginEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                DbContract.LoginEntry.COLUMN_USER + " TEXT NOT NULL, " +
                DbContract.LoginEntry.COLUMN_PASS + " TEXT NOT NULL);";

        final String SQL_CREATE_COMICS_TABLE = "CREATE TABLE " + DbContract.ComicsEntry.TABLE_NAME_COMICS + " (" +
                DbContract.ComicsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                DbContract.ComicsEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                DbContract.ComicsEntry.COLUMN_IMAGE + " TEXT NOT NULL, " +
                DbContract.ComicsEntry.COLUMN_DESCRIPTION + " TEXT NOT NULL);";


        final String SQL_CREATE_DES_TABLE = "CREATE TABLE " + DbContract.DESEntry.DESTABLE + " (" +
                DbContract.DESEntry.COLUMN_ID_BO + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DbContract.DESEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                DbContract.DESEntry.COLUMN_IMAGE + " TEXT NOT NULL, " +
                DbContract.DESEntry.COLUMN_DESCRIPTION + " TEXT, " +
                DbContract.DESEntry.COLUMN_AUTHOR + " TEXT NOT NULL, " +
                DbContract.DESEntry.COLUMN_PUBLICATION_DATE + " TEXT NOT NULL, " +
                DbContract.DESEntry.COLUMN_CONTENT + " TEXT" +
                ");";


        db.execSQL(SQL_CREATE_LOGIN_TABLE);
        db.execSQL(SQL_CREATE_COMICS_TABLE);
        db.execSQL(SQL_CREATE_DES_TABLE);
        Log.d(TAG, "Database Created Successfully" );

        try {
            readDataDes(db);
            readDataToDb_BoDe(db);
            readDataToComicsDb(db);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void readDataToComicsDb(SQLiteDatabase db) throws IOException, JSONException {
        final String COMIC_TITLE = "title";
        final String COMIC_IMAGE_URL = "image_url";
        final String COMIC_DESCRIPTION = "description";

        String jsonDataString = readJsonDataFromFile();
        JSONArray comicsJsonArray = new JSONArray(jsonDataString);

        for (int i = 0; i < comicsJsonArray.length(); ++i) {
            String title, imageUrl, description;
            JSONObject comicObject = comicsJsonArray.getJSONObject(i);

            title = comicObject.getString(COMIC_TITLE);
            imageUrl = comicObject.getString(COMIC_IMAGE_URL);
            description = comicObject.getString(COMIC_DESCRIPTION);

            ContentValues comicValues = new ContentValues();
            comicValues.put(DbContract.ComicsEntry.COLUMN_TITLE, title);
            comicValues.put(DbContract.ComicsEntry.COLUMN_IMAGE, imageUrl);
            comicValues.put(DbContract.ComicsEntry.COLUMN_DESCRIPTION, description);

            db.insert(DbContract.ComicsEntry.TABLE_NAME_COMICS, null, comicValues);
            Log.d(TAG, "Inserted Successfully " + comicValues);
        }
    }

    private void readDataDes(SQLiteDatabase db) throws IOException, JSONException {
        final String DES_IDBO = "idBo";
        final String DES_TITLE = "title";
        final String DES_IMAGE = "image";
        final String DES_DESCRIPTION = "description";
        final String DES_AUTHOR = "author";
        final String DES_PUBLICATION_DATE = "publicationDate";
        final String DES_CONTENT = "content";

        try {
            String jsonDataString = readJsonDataFromFile_Des();
            JSONArray desJsonArray = new JSONArray(jsonDataString);

            for (int i = 0; i < desJsonArray.length(); ++i) {
                int idBo;
                String title, image, description, author, publicationDate, content;
                JSONObject desObject = desJsonArray.getJSONObject(i);

                idBo = desObject.getInt(DES_IDBO); // Chuyển đổi sang int nếu cần
                title = desObject.getString(DES_TITLE);
                image = desObject.getString(DES_IMAGE);
                description = desObject.getString(DES_DESCRIPTION);
                author = desObject.getString(DES_AUTHOR);
                publicationDate = desObject.getString(DES_PUBLICATION_DATE);
                content = desObject.getString(DES_CONTENT);

                ContentValues desValues = new ContentValues();
                desValues.put(DbContract.DESEntry.COLUMN_ID_BO, idBo);
                desValues.put(DbContract.DESEntry.COLUMN_TITLE, title);
                desValues.put(DbContract.DESEntry.COLUMN_IMAGE, image);
                desValues.put(DbContract.DESEntry.COLUMN_DESCRIPTION, description);
                desValues.put(DbContract.DESEntry.COLUMN_AUTHOR, author);
                desValues.put(DbContract.DESEntry.COLUMN_PUBLICATION_DATE, publicationDate);
                desValues.put(DbContract.DESEntry.COLUMN_CONTENT, content);

                db.insert(DbContract.DESEntry.DESTABLE, null, desValues);
                Log.d(TAG, "Inserted Successfully " + desValues);
            }
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage(), e);
            e.printStackTrace();
        }
    }


    private String readJsonDataFromFile_Des() throws IOException {
        InputStream inputStream = null;
        StringBuilder builder = new StringBuilder();
        try {
            inputStream = mResources.openRawResource(R.raw.menu_iterm);
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(inputStream, "UTF-8"));
            String jsonDataString;
            while ((jsonDataString = bufferedReader.readLine()) != null) {
                builder.append(jsonDataString);
            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return new String(builder);
    }

    private String readJsonDataFromFile_BoDe() throws IOException {
        InputStream inputStream = null;
        StringBuilder builder = new StringBuilder();
        try {
            inputStream = mResources.openRawResource(R.raw.menu_bode);
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(inputStream, "UTF-8"));
            String jsonDataString;
            while ((jsonDataString = bufferedReader.readLine()) != null) {
                builder.append(jsonDataString);
            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return new String(builder);
    }

    private void readDataToDb_BoDe(SQLiteDatabase db) throws IOException, JSONException {
        try {
            String jsonDataString = readJsonDataFromFile_BoDe();
            JSONArray comicArray = new JSONArray(jsonDataString);

            for (int i = 0; i < comicArray.length(); i++) {
                JSONObject comicObject = comicArray.getJSONObject(i);

                String title = comicObject.getString("title");
                String image = comicObject.getString("image");
                String description = comicObject.getString("description");

                // Insert data into the database using the correct table and column names
                ContentValues comicValues = new ContentValues();
                comicValues.put(DbContract.ComicsEntry.COLUMN_TITLE, title);
                comicValues.put(DbContract.ComicsEntry.COLUMN_IMAGE, image);
                comicValues.put(DbContract.ComicsEntry.COLUMN_DESCRIPTION, description);

                db.insert(DbContract.ComicsEntry.TABLE_NAME_COMICS, null, comicValues);
                Log.d(TAG, "Inserted Successfully " + comicValues);
            }
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage(), e);
            e.printStackTrace();
        }
    }


    private String readJsonDataFromFile() throws IOException {

        InputStream inputStream = null;
        StringBuilder builder = new StringBuilder();

        try {
            String jsonDataString = null;
            inputStream = mResources.openRawResource(R.raw.menu_iterm);
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(inputStream, "UTF-8"));
            while ((jsonDataString = bufferedReader.readLine()) != null) {
                builder.append(jsonDataString);
            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }

        return new String(builder);
    }

    private String readJsonDataFromFile1() throws IOException {

        InputStream inputStream = null;
        StringBuilder builder = new StringBuilder();

        try {
            String jsonDataString = null;
            inputStream = mResources.openRawResource(R.raw.menu_lythuyet);
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(inputStream, "UTF-8"));
            while ((jsonDataString = bufferedReader.readLine()) != null) {
                builder.append(jsonDataString);
            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }

        return new String(builder);
    }

    private String readJsonDataFromFile_MeoThi() throws IOException {
        InputStream inputStream = null;
        StringBuilder builder = new StringBuilder();
        try {
            String jsonDataString = null;
            inputStream = mResources.openRawResource(R.raw.menu_meothi);
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(inputStream, "UTF-8"));
            while ((jsonDataString = bufferedReader.readLine()) != null) {
                builder.append(jsonDataString);
            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return new String(builder);
    }

    public long Update(String table, ContentValues values, String whereClause, String[] whereArgs){
        SQLiteDatabase db = getWritableDatabase();
        return db.update(table,values,whereClause,whereArgs);
    }
}
