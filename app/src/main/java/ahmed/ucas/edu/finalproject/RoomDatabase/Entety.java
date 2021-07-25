package ahmed.ucas.edu.finalproject.RoomDatabase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Appointment_table")
public class Entety  {

    @ColumnInfo(name = "Appointment")
    private String ApppointmentObject;

    @PrimaryKey(autoGenerate = true)
    private int id;

    public Entety(){}

    public Entety(String apppointmentObject) {
        ApppointmentObject = apppointmentObject;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getApppointmentObject() {
        return ApppointmentObject;
    }

    public void setApppointmentObject(String apppointmentObject) {
        ApppointmentObject = apppointmentObject;
    }
}
