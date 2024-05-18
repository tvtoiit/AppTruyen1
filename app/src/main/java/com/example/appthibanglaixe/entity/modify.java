package com.example.appthibanglaixe.entity;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.appthibanglaixe.data.DbContract;
import com.example.appthibanglaixe.data.sqDuLieu;
import com.example.appthibanglaixe.model.Comic;
import com.example.appthibanglaixe.model.CusDes;

import java.util.ArrayList;

public class modify {
    public Context context;
    public static ArrayList<Comic> getAllLoaiTruyen(Context context) {
        ArrayList<Comic> comicList = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = sqDuLieu.getInstance(context).getReadableDatabase();
            cursor = db.rawQuery("SELECT * FROM comics", null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    // Lấy dữ liệu từ cột mỗi hàng trong Cursor và thêm vào danh sách
                    String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
                    String imageUrl = cursor.getString(cursor.getColumnIndexOrThrow("image"));
                    String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
                    Comic comic = new Comic(title, imageUrl, description);
                    comicList.add(comic);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null && db.isOpen()) {
                db.close();
            }
        }

        return comicList;
    }



    public static void insertTestUser(Context context) {
        SQLiteDatabase db = sqDuLieu.getInstance(context).getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", "testuser");
        values.put("password", "testpassword");
        db.insert("login", null, values);
    }

    /**
     * // Hàm thêm người dùng mới vào cơ sở dữ liệu
     *
     * @param context Trạng thái
     * @param username Tên user name của người dùng
     * @param password Mật khẩu của người dùng
     */
    public static void registerUser(Context context, String username, String password) {
        SQLiteDatabase db = sqDuLieu.getInstance(context).getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password", password);
        db.insert("login", null, values);
    }

    /**
     * Hàm kiểm tra xem username có tồn tại trong cơ sở dữ liệu không
     *
     * @param context  Trạng thái
     * @param username Tên username của người dùng
     * @return Kết quả có tồn tại username
     */
    public static boolean isUsernameExist(Context context, String username) {
        SQLiteDatabase db = sqDuLieu.getInstance(context).getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM login WHERE username = ?", new String[]{username});

        if (cursor != null && cursor.getCount() > 0) {
            cursor.close();
            return true;
        } else {
            if (cursor != null) {
                cursor.close();
            }
            return false;
        }
    }

    /**
     * get bộ dề theo id
     *
     * @param context trạng thái
     * @param idbo input bộ truyên vào
     * @return Data của bộ đề đó
     */
    public static CusDes getDesId(Context context, int idbo) {
        SQLiteDatabase db = sqDuLieu.getInstance(context).getReadableDatabase();
        CusDes data = null;
        Cursor cursor = db.rawQuery("SELECT * FROM " + DbContract.DESEntry.DESTABLE + " WHERE " + DbContract.DESEntry.COLUMN_ID_BO + " = ?", new String[]{String.valueOf(idbo)});
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int idBo = cursor.getInt(cursor.getColumnIndexOrThrow(DbContract.DESEntry.COLUMN_ID_BO));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(DbContract.DESEntry.COLUMN_TITLE));
                String image = cursor.getString(cursor.getColumnIndexOrThrow(DbContract.DESEntry.COLUMN_IMAGE));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(DbContract.DESEntry.COLUMN_DESCRIPTION));
                String author = cursor.getString(cursor.getColumnIndexOrThrow(DbContract.DESEntry.COLUMN_AUTHOR));
                String publicationDate = cursor.getString(cursor.getColumnIndexOrThrow(DbContract.DESEntry.COLUMN_PUBLICATION_DATE));
                String content = cursor.getString(cursor.getColumnIndexOrThrow(DbContract.DESEntry.COLUMN_CONTENT));

                data = new CusDes(idBo, title, image, description, author, publicationDate, content);
                Log.d(TAG, "Data retrieved: " + data);
            }
            cursor.close();
        }
        return data;
    }




    /**
     * Phương thức đổi mật dkhẩu của người dùng trong cơ sở dữ liệu
     *
     * @param context Trạng thái của nguoi dùng
     * @param username Tên đăng nhập của người dùng
     * @param newPassword Mật khẩu mới của người dùng
     * @return Giá trị thành công hay thất bại
     */
    public static boolean changePassword(Context context, String username, String newPassword) {
        SQLiteDatabase db = sqDuLieu.getInstance(context).getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("password", newPassword);

        int rowsAffected = db.update("login", values, "username = ?", new String[]{username});
        if (rowsAffected > 0) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isValidLogin(Context context, String username, String password) {
        // Kết nối tới cơ sở dữ liệu để kiểm tra thông tin đăng nhập
        SQLiteDatabase db = sqDuLieu.getInstance(context).getReadableDatabase();

        // Thực hiện truy vấn để lấy thông tin người dùng từ cơ sở dữ liệu
        Cursor cursor = db.rawQuery("SELECT * FROM login WHERE username = ? AND password = ?", new String[]{username, password});

        if (cursor != null && cursor.getCount() > 0) {
            // Đăng nhập thành công
            cursor.close();
            return true;
        } else {
            // Đăng nhập thất bại
            if (cursor != null) {
                cursor.close();
            }
            return false;
        }
    }
}
