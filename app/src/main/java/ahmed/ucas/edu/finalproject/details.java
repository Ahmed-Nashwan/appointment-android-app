package ahmed.ucas.edu.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import ahmed.ucas.edu.finalproject.Classes.Appointment;
import ahmed.ucas.edu.finalproject.databinding.ActivityDetailsBinding;

public class details extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseFirestore firebaseFirestore;

    ActivityDetailsBinding binding;

    int year,day,month,menit,hours,Company_start,Company_end,startusD,statusT;
    Calendar newCalendar;

    Appointment appointment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        newCalendar = Calendar.getInstance();
        auth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        if(auth.getUid()==null){
            finish();
        }

       appointment =   (Appointment)getIntent().getSerializableExtra("Appointment");
        binding.etSomeNotices.setText(appointment.getNotice());
        binding.etDateAdd.setText(appointment.getYear()+"/"+appointment.getMonth()+"/"+appointment.getDay());
        binding.etTimeAdd.setText(appointment.getHour()+":"+appointment.getMinut());
        binding.tvCompanyNameMain.setText(appointment.getCompany_name());
        binding.tvDetailsTimeMain.setText(appointment.getYear()+"/"+appointment.getMonth()+"/"+appointment.getDay()+"-"+appointment.getHour()+":"+appointment.getMinut());


binding.etSomeNotices.setEnabled(false);
binding.etDateAdd.setEnabled(false);
binding.etTimeAdd.setEnabled(false);


binding.imageEdit.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        if(MyReceiver_wifi_status.isNetworkAvailable(getApplicationContext())) {
            binding.etSomeNotices.setEnabled(true);
            binding.etDateAdd.setEnabled(true);
            binding.etTimeAdd.setEnabled(true);
        }else{
            Toast.makeText(details.this, "check your internet", Toast.LENGTH_SHORT).show();
        }


    }
});



binding.etDateAdd.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        startusD = 1;
        show_date_dialog();

    }
});

        binding.etTimeAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show_time_dialog();
                statusT = 1;

            }
        });



        binding.imageDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(MyReceiver_wifi_status.isNetworkAvailable(getApplicationContext())) {
                    firebaseFirestore.collection("Appointment-" + auth.getUid()).document(appointment.getDocId())
                            .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            Toast.makeText(details.this, "Deleted successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(details.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }else{
                    Toast.makeText(details.this, "check your internet", Toast.LENGTH_SHORT).show();
                }
            }
        });



        binding.imageUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(MyReceiver_wifi_status.isNetworkAvailable(getApplicationContext())) {


                    firebaseFirestore.collection("Company").document(appointment.getCompany_id()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {

                            Company_start = documentSnapshot.getLong("start_at").intValue();
                            Company_end = documentSnapshot.getLong("end_at").intValue();


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(details.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });


                    if (startusD != 0) {

                        if (startusD != 0) {


                            if (isValidDate(year, month, day)) {


                                if (isValidDatehour(year,month,day,hours,menit)) {

                                    if (isValidTimeFirst(Calendar.getInstance().get(Calendar.HOUR), Company_start, Company_end)) {
                                        Calendar calendar = Calendar.getInstance();
                                        calendar.set(Calendar.YEAR, year);
                                        calendar.set(Calendar.MONTH, month);
                                        calendar.set(Calendar.DATE, day);
                                        calendar.set(Calendar.HOUR, hours);
                                        calendar.set(Calendar.MINUTE, menit);

                                        Map<String, Object> map = new HashMap<>();

                                        map.put("month", month);
                                        map.put("day", day);
                                        map.put("year", year);
                                        map.put("hour", hours);
                                        map.put("minut", menit);
                                        map.put("notice", binding.etSomeNotices.getText().toString());
                                        map.put("time", calendar.getTimeInMillis());


                                        firebaseFirestore.collection("Appointment-" + auth.getUid()).document(appointment.getDocId())
                                                .set(map, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {

                                                Toast.makeText(details.this, "Updated successfully", Toast.LENGTH_SHORT).show();

                                                binding.etSomeNotices.setEnabled(false);
                                                binding.etDateAdd.setEnabled(false);
                                                binding.etTimeAdd.setEnabled(false);

                                                binding.etSomeNotices.setText(binding.etSomeNotices.getText().toString());
                                                binding.etDateAdd.setText(year + "/" + month + "/" + day);
                                                binding.etTimeAdd.setText(hours + " : " + menit);

                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {

                                                Toast.makeText(details.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                                            }
                                        });

                                    } else {
                                        Toast.makeText(details.this, "time in the past", Toast.LENGTH_SHORT).show();
                                    }

                                }

                            }

                        }


                    }

                }else{
                    Toast.makeText(details.this, "check your internet", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


    void show_date_dialog() {

        DatePickerDialog StartTime = new DatePickerDialog(details.this, new DatePickerDialog.OnDateSetListener() {


            public void onDateSet(DatePicker view, int years, int monthOfYears, int dayOfMonths) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYears, dayOfMonths);
                year = years;
                month = monthOfYears+1;
                day = dayOfMonths;
                binding.etDateAdd.setText(years + "/" + month + "/" + day);


            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        //   StartTime.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

        StartTime.show();

    }


    void show_time_dialog() {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(details.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                hours = i;
                menit = i1;
                binding.etTimeAdd.setText(i + "-" + i1);
            }
        }, hour, minute, true);

        mTimePicker.setTitle("Select Time");
        mTimePicker.show();


    }

    public boolean isValidDate(int year, int month, int day  ) {
        int y = Calendar.getInstance().get(Calendar.YEAR);
        int m = Calendar.getInstance().get(Calendar.MONTH)+1;
        int d = Calendar.getInstance().get(Calendar.DATE);

        // Toast.makeText(getActivity(), m+"", Toast.LENGTH_SHORT).show();
        boolean ret = false;

        if (year == y )  {

            if((month+1) >= m){

                if(day >= d){

                    ret = true;

                }


            }




        } else if(year>y) {

            ret = true;

        }else{

            ret = false;

        }



        return ret;
    }


    public boolean isValidTimeFirst(int hour, int startCompany, int endCompany) {

        boolean valid;
        if (hour >= startCompany) {
            if (hour < (endCompany + 12)) {
                Toast.makeText(this, "every thing is good", Toast.LENGTH_LONG).show();
                valid = true;
            } else {
                Toast.makeText(this, "in this time company closed", Toast.LENGTH_LONG).show();
                valid = false;
            }
        } else {
            Toast.makeText(this, "in this time company closed", Toast.LENGTH_LONG).show();
            valid = false;
        }
        return valid;
    }

    public boolean isValidTime2(){
        boolean valid = false;
        int currentHour =  Calendar.getInstance().get(Calendar.HOUR);
        int h = hours;
        if(h>12){
            h -=12;
        }

        if((currentHour-h)>0){

            valid = true;
        }else{
            valid = false;
        }

        return  valid;

    }

    public boolean isValidDatehour(int year, int month, int day, int hour3, int minut) {
        Toast.makeText(getApplicationContext(), hour3 + "", Toast.LENGTH_SHORT).show();
        int y = Calendar.getInstance().get(Calendar.YEAR);
        int m = Calendar.getInstance().get(Calendar.MONTH);
        int d = Calendar.getInstance().get(Calendar.DATE);
        int h = Calendar.getInstance().get(Calendar.HOUR);
        int mi = Calendar.getInstance().get(Calendar.MINUTE);
        boolean ret;

        if (day == d) {
            if (hour3 > 12) {
                hour3 -= 12;
            }
            ret = false;

            if (year >= y) {

                if (month >= m) {

                    if (day >= d) {

                        if (hour3 > h) {

                            if(minut>mi) {

                                ret = true;

                            }

                        }

                    }

                }

            }

        } else {
            ret = true;
        }
        return ret;
    }


}