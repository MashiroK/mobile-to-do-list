package com.example.progMobile;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.scottyab.aescrypt.AESCrypt;

import java.security.GeneralSecurityException;
import java.util.ArrayList;

public class DBToDoHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Atividade";
    public static final String key = "32984kndf903nf92jf2-93487n";

    //*******
    private static final String TABLE_NAME_TASK = "Tasks";

    private static final String TASK_COLUM_ID = "ID";
    private static final String TASK_COLUM_TITLE = "Title";
    private static final String TASK_COLUM_DESC = "Description";
    private static final String TASK_COLUM_DATE = "Date";
    private static final String TASK_COLUM_TIME = "Time";
    //*******

    //*******
    private static final String TABLE_NAME_USER = "Users";

    private static final String USER_COLUM_ID = "ID";
    private static final String USER_COLUM_NAME = "Name";
    private static final String USER_COLUM_EMAIL = "Email";
    private static final String USER_COLUM_PHONE = "Phone";
    private static final String USER_COLUM_PASSWORD = "Password";
    //*******

    SQLiteDatabase db;

    public DBToDoHelper (Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    private static final String TABLE_CREATE_TASK =
            "create table " + TABLE_NAME_TASK
                    + " ("
                    + TASK_COLUM_ID + " integer primary key autoincrement, "
                    + TASK_COLUM_TITLE + " text, "
                    + TASK_COLUM_DESC + " text, "
                    + TASK_COLUM_DATE + " text, "
                    + TASK_COLUM_TIME + " text" +
                    ");";
    
    private static final String TABLE_CREATE_USER =
            "create table " + TABLE_NAME_USER
            + " ("
            + USER_COLUM_ID + " integer primary key autoincrement, "
            + USER_COLUM_NAME + " text, "
            + USER_COLUM_EMAIL + " text, "
            + USER_COLUM_PHONE + " text, "
            + USER_COLUM_PASSWORD + " text"
            + " );";


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE_TASK);
        db.execSQL(TABLE_CREATE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS " + TABLE_NAME_TASK;
        db.execSQL(query);
        query = "DROP TABLE IF EXISTS " + TABLE_NAME_USER;
        db.execSQL(query);
        this.onCreate(db);
    }

    public long insertTask(Tasks t) {
        long returnDB;
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TASK_COLUM_TITLE, t.getTaskTitle());
        values.put(TASK_COLUM_DESC, t.getTaskDesc());
        values.put(TASK_COLUM_DATE, t.getDate());
        values.put(TASK_COLUM_TIME, t.getTime());
        returnDB = db.insert(TABLE_NAME_TASK, null, values);
        String res = Long.toString(returnDB);
        Log.i("DBToDoHelper", res);
        db.close();
        return returnDB;
    }

    public long insertUser(User u) throws GeneralSecurityException {
        long returnDB;
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_COLUM_NAME, u.getName());
        values.put(USER_COLUM_EMAIL, u.getEmail());
        values.put(USER_COLUM_PHONE, u.getPhone());
        String passwd = u.getPassword();

        if(!passwd.equals("")) {
            String updtPassword = crypto(passwd);
            values.put(USER_COLUM_PASSWORD, updtPassword);
        }

        returnDB = db.insert(TABLE_NAME_USER, null, values);
        String res = Long.toString(returnDB);
        Log.i("DBToDoHelper", res);
        db.close();
        return returnDB;
    }

    public long updateTask(Tasks t) {
        long returnDB;
        this.db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TASK_COLUM_TITLE, t.getTaskTitle());
        values.put(TASK_COLUM_DESC, t.getTaskDesc());
        values.put(TASK_COLUM_DATE, t.getDate());
        values.put(TASK_COLUM_TIME, t.getTime());
        String[] args = {String.valueOf(t.getTaskID())};
        returnDB = db.update(TABLE_NAME_TASK, values, "ID=?", args);
        db.close();
        return returnDB;
    }

    public long updateUser(User u) throws GeneralSecurityException {
        long returnDB;
        this.db = getWritableDatabase();
        ContentValues values = new ContentValues();
        if(!u.getName().equals(""))
            values.put(USER_COLUM_NAME, u.getName());
        if(!u.getEmail().equals(""))
            values.put(USER_COLUM_EMAIL, u.getEmail());
        if(!u.getPhone().equals(""))
            values.put(USER_COLUM_PHONE, u.getPhone());

        String password = u.getPassword();
        if(password.equals("")) {
            String updtPassword = crypto(password);
            values.put(USER_COLUM_PASSWORD, updtPassword);
        }

        String[] args = {String.valueOf(u.getIdUser())};
        returnDB = db.update(TABLE_NAME_USER, values, "ID=?", args);
        db.close();
        return returnDB;
    }

    public ArrayList<Tasks> selectAllTasks() {
        String[] columns = {TASK_COLUM_ID,
                TASK_COLUM_TITLE,
                TASK_COLUM_DESC,
                TASK_COLUM_DATE,
                TASK_COLUM_TIME};
        Cursor cursor = getWritableDatabase().query(TABLE_NAME_TASK, columns, null, null, null, null, "ID", null);
        ArrayList<Tasks> listTasks = new ArrayList<Tasks>();
        while(cursor.moveToNext()) {
            Tasks t = new Tasks();
            t.setTaskID(cursor.getInt(0));
            t.setTaskTitle(cursor.getString(1));
            t.setTaskDesc(cursor.getString(2));
            t.setDate(cursor.getString(3));
            t.setTime(cursor.getString(4));
            listTasks.add(t);
        }
        cursor.close();

        return listTasks;

    }

    public ArrayList<User> selectAllUsers() {
        String[] columns = {USER_COLUM_ID,
                USER_COLUM_NAME,
                USER_COLUM_EMAIL,
                USER_COLUM_PHONE};
        Cursor cursor = getWritableDatabase().query(TABLE_NAME_USER, columns, null, null, null, null, "ID", null);
        ArrayList<User> listUsers = new ArrayList<User>();
        while(cursor.moveToNext()) {
            User u = new User();
            u.setId(cursor.getInt(0));
            u.setName(cursor.getString(1));
            u.setEmail(cursor.getString(2));
            u.setPhone(cursor.getString(3));

            listUsers.add(u);
        }
        cursor.close();

        return listUsers;

    }

    public String findPasswordCrypto(String user) throws GeneralSecurityException {
        String ret = "Not found";
            db = this.getReadableDatabase();
            String[] columns = {
                    USER_COLUM_ID,
                    USER_COLUM_NAME,
                    USER_COLUM_EMAIL,
                    USER_COLUM_PHONE,
                    USER_COLUM_PASSWORD
            };
            Cursor cursor = db.query(TABLE_NAME_USER, columns, null, null, null, null, "ID", null);
            String aux;
            if (cursor.moveToFirst()) {
                do {
                    aux = cursor.getString(1);
                    if (aux.equals(user)) {
                        ret = cursor.getString(4);
                        break;
                    }
                } while (cursor.moveToNext());
            }

        ret = noCrypto(ret);

        return ret;

    }


    public long update(Tasks task) {
        long ret;
        db=getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TASK_COLUM_TITLE, task.getTaskTitle());
        values.put(TASK_COLUM_DESC,task.getTaskDesc());
        values.put(TASK_COLUM_DATE,task.getDate());
        String[] args = {String.valueOf(task.getTaskID())};
        ret=db.update(TABLE_NAME_TASK,values,"id=?",args);
        db.close();
        return ret;
    }

    public String crypto(String senha) throws GeneralSecurityException {

        return AESCrypt.encrypt(key, senha);

    }

    public String noCrypto(String senha) throws GeneralSecurityException {

        return AESCrypt.decrypt(key, senha);

    }
}
