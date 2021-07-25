package ahmed.ucas.edu.finalproject.RoomDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;


@androidx.room.Dao
public interface Dao {

        @Insert
        Completable Insert(Entety entety);

    @Insert
    void Insert2(Entety entety);

    @Query("SELECT * FROM Appointment_table")
    Observable<List<Entety>> getAll();

    @Query("SELECT * FROM Appointment_table")
    List<Entety> getAll2();


     @Query("delete from Appointment_table ")
     Completable deleteAllAppointment();


}
