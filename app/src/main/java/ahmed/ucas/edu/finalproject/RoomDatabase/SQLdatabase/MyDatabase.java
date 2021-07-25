package ahmed.ucas.edu.finalproject.RoomDatabase.SQLdatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.airbnb.lottie.animation.content.Content;

import java.util.ArrayList;

import ahmed.ucas.edu.finalproject.Classes.Appointment;
import ahmed.ucas.edu.finalproject.CustomSerializable;
import ahmed.ucas.edu.finalproject.RoomDatabase.RoomDb;

public  class MyDatabase extends SQLiteOpenHelper {


   // public static MyDatabase instance;
//    public static  MyDatabase getInstance(Context context){
//        if(instance==null){
//
//            instance = new MyDatabase(context,"appointments",null,3);
//        }else{
//
//        }
//
//        return instance;
//    }

    public MyDatabase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("create table IF NOT EXISTS Appointment (id INTEGER  primary key autoincrement  , object TEXT )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("delete  from Appointment");
        onCreate(sqLiteDatabase);

    }



}
