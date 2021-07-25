package ahmed.ucas.edu.finalproject.RoomDatabase;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.List;

import ahmed.ucas.edu.finalproject.MainActivity;
import io.reactivex.CompletableObserver;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
//import androidx.room.Room;
//import androidx.room.RoomDatabase;



@Database(entities = { Entety.class }, version = 1, exportSchema = false)
 public abstract  class RoomDb extends RoomDatabase {

    public static RoomDb instance;
    public abstract Dao dao();

    public static synchronized RoomDb getInstance(Context context){
        if(instance==null){
            instance = Room.databaseBuilder(context.getApplicationContext(), RoomDb.class,"Database" )
                    .fallbackToDestructiveMigration()
                    .build();
        }else{

        }

        return instance;
    }

//    void getAllAppointment() {
//
//
//        //  get all appointments from database
//
//        RoomDb.getInstance(MainActivity.this).dao().getAll().subscribeOn(Schedulers.computation())
//                .observeOn(Schedulers.computation())
//                .subscribe(new Observer<List<Entety>>() {
//                    @Override
//                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(@io.reactivex.annotations.NonNull List<Entety> enteties) {
//
//
//                        for (Entety e : enteties) {
//
//                            //    Appointment appointment =     (Appointment)  CustomSerializable.deserialize(e.getApppointmentObject());
//
//                            Log.d("ddd", e.getApppointmentObject());
//
//                        }
//
//                    }
//
//                    @Override
//                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Log.d("ddd", "complete");
//                    }
//                });
//
//
//    }
//
//    void deleteAllAppointment() {
//
//
//        // delete all appointments from room
//        RoomDb.getInstance(MainActivity.this).dao().deleteAllAppointment().subscribeOn(Schedulers.computation()).subscribe(new CompletableObserver() {
//            @Override
//            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
//
//
//            }
//
//            @Override
//            public void onComplete() {
//
//                Log.d("ttt", "deleted successfully");
//            }
//
//            @Override
//            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
//
//                Log.d("ddd", e.getMessage());
//            }
//        });
//    }
//
//    void insertAppointment(Entety entety) {
//
//        // insert object appointment in room
//        RoomDb.getInstance(MainActivity.this).dao().Insert(entety).subscribeOn(Schedulers.computation()).subscribe(new CompletableObserver() {
//            @Override
//            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
//
//
//            }
//
//            @Override
//            public void onComplete() {
//
//                Log.d("ttt", "deleted successfully");
//            }
//
//            @Override
//            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
//
//                Log.d("ddd", e.getMessage());
//            }
//        });
//
//    }

}
