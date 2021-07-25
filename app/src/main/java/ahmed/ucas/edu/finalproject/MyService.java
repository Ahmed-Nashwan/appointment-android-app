package ahmed.ucas.edu.finalproject;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.Observable;

import ahmed.ucas.edu.finalproject.Classes.Appointment;
import ahmed.ucas.edu.finalproject.RoomDatabase.Entety;
import ahmed.ucas.edu.finalproject.RoomDatabase.RoomDb;

import ahmed.ucas.edu.finalproject.RoomDatabase.SQLdatabase.MyDatabase;
import io.reactivex.CompletableObserver;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class MyService extends JobService {

    Appointment appointment;


//Context context;


   // MyDatabase database = new MyDatabase(context,"appointments",null,4);


    public MyService() {
    }

    @Override
    public boolean onStartJob(JobParameters jobParameters) {

        new Thread(() -> {

            MainActivity.deleteAllDatabase();

            Log.d("aaa","started");
            FirebaseFirestore.getInstance().collection("Appointment-" + FirebaseAuth.getInstance().getUid())
                    .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                    List<DocumentSnapshot> snapshots = queryDocumentSnapshots.getDocuments();

                    if (snapshots.size() != 0) {

                        for (DocumentSnapshot documentSnapshot : snapshots) {

                            appointment = new Appointment();


                            int y = documentSnapshot.getLong("year").intValue();
                            int m = documentSnapshot.getLong("month").intValue();
                            int d = documentSnapshot.getLong("day").intValue();
                            int h = documentSnapshot.getLong("hour").intValue();
                            appointment.setCompany_name(documentSnapshot.getString("company_name"));
                            appointment.setCompany_id(documentSnapshot.getString("company_id"));
                            appointment.setDocId(documentSnapshot.getString("docId"));
                            appointment.setNotice(documentSnapshot.getString("notice"));
                            appointment.setTime(documentSnapshot.getLong("time").intValue());
                            appointment.setYear(y);
                            appointment.setMonth(m);
                            appointment.setDay(d);
                            appointment.setHour(h);
                            appointment.setMinut(documentSnapshot.getLong("minut").intValue());


                            String objectAppointment = CustomSerializable.serialize(appointment);


                            MainActivity.insertDatabase(objectAppointment);


                        }

                                jobFinished(jobParameters,false);
                        Log.d("aaa","finished");
                    }


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    e.printStackTrace();
                }
            });

        }).start();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {

        return true;
    }

}
