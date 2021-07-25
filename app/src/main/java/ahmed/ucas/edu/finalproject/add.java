package ahmed.ucas.edu.finalproject;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ahmed.ucas.edu.finalproject.Classes.Appointment;
import ahmed.ucas.edu.finalproject.Classes.Company;
import ahmed.ucas.edu.finalproject.databinding.ActivityAddBinding;

public class add extends AppCompatActivity {
    ActivityAddBinding binding;
    Calendar newCalendar;
    SimpleDateFormat dateFormatter;
    Spinner_adapter adapter;
    ArrayList<Company> arrayList;
    Company company;
    FirebaseFirestore firestor;
    Company company_selected;
    int statusT;
    int statusD;
    MyReceiver_wifi_status receiver_wifi_status;
    int year;
    int month;
    int day;
    int hours;
    int menit;
    FirebaseAuth auth;
    AppCompatActivity appCompatActivity;
    boolean valid;
    Appointment appointment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddBinding.inflate(getLayoutInflater());
        newCalendar = Calendar.getInstance();
        setContentView(binding.getRoot());
        dateFormatter = new SimpleDateFormat("dd-mm-yyy");
        firestor = FirebaseFirestore.getInstance();
        arrayList = new ArrayList<>();
        appointment = new Appointment();
        auth = FirebaseAuth.getInstance();



        appCompatActivity =  this;




//
//            Toast.makeText(this, "Check your internet", Toast.LENGTH_SHORT).show();
//            FragmentManager fragmentManager = getSupportFragmentManager();
//            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//
//            fragmentTransaction.add(R.id.container_wifi,new noconnection());
//            fragmentTransaction.commit();
//            binding.etTimeAdd.setVisibility(View.GONE);
//            binding.etDateAdd.setVisibility(View.GONE);
//            binding.btnAddNewAppointment.setVisibility(View.GONE);
//            binding.spinnerCompanies.setVisibility(View.GONE);
//            binding.etSomeNotices.setVisibility(View.GONE);
//            binding.titleAdd.setVisibility(View.GONE);


            firestor.collection("Company").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                    List<DocumentSnapshot> documentSnapshots = queryDocumentSnapshots.getDocuments();

                    for (DocumentSnapshot documentSnapshot : documentSnapshots) {
                        company = new Company();
                        company.setCompany_name(documentSnapshot.getString("company_name"));
                        company.setCompany_image(documentSnapshot.getString("company_image"));
                        company.setCompany_type(documentSnapshot.getString("company_type"));
                        company.setDocId(documentSnapshot.getString("docId"));
                        company.setEnd_at(documentSnapshot.getLong("end_at").intValue());
                        company.setStart_at(documentSnapshot.getLong("start_at").intValue());
                        company.setLat(documentSnapshot.getDouble("lat"));
                        company.setLongt(documentSnapshot.getDouble("longt"));

                        arrayList.add(company);


                    }

                    adapter = new Spinner_adapter(getApplicationContext(), arrayList);
                    binding.spinnerCompanies.setAdapter(adapter);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    Toast.makeText(add.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });


            binding.spinnerCompanies.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    company_selected = arrayList.get(i);

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });


            binding.etTimeAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    statusT = 1;
                    show_time_dialog();
                }
            });


            binding.etDateAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    statusD = 1;
                    show_date_dialog();
                }
            });


            binding.btnAddNewAppointment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (company_selected != null) {

                        if (statusD == 1) {

                            if (statusT == 1) {

                                if (!binding.etDateAdd.getText().toString().isEmpty()) {


                                    if (!binding.etTimeAdd.getText().toString().isEmpty()) {

//                                    Date date = new Date(24141241);
                                        if (isValidDate(year, month, day)) {


                                            if (isValidTimeFirst(hours, company_selected.getStart_at(), company_selected.getEnd_at())) {

                                                if(isValidDatehour(year,month,day,hours,menit)) {


                                                    Toast.makeText(add.this, "every thing is good", Toast.LENGTH_SHORT).show();


                                                    Calendar calendar = Calendar.getInstance();

                                                    calendar.set(Calendar.YEAR, year);
                                                    calendar.set(Calendar.MONTH, month);
                                                    calendar.set(Calendar.DATE, day);
                                                    calendar.set(Calendar.HOUR, hours);
                                                    calendar.set(Calendar.MINUTE, menit);


                                                    String id = firestor.collection("Appointment").document().getId();
                                                    appointment.setMinut(menit);
                                                    appointment.setHour(hours);
                                                    appointment.setDay(day);
                                                    appointment.setMonth(month);
                                                    appointment.setYear(year);
                                                    appointment.setTime(calendar.getTimeInMillis());
                                                    appointment.setNotice(binding.etSomeNotices.getText().toString() + "");
                                                    appointment.setDocId(id);
                                                    appointment.setCompany_id(company_selected.getDocId());
                                                    appointment.setCompany_name(company_selected.getCompany_name());


                                                    firestor.collection("Appointment-" + auth.getUid()).document(id).set(appointment).addOnSuccessListener(
                                                            new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void aVoid) {
                                                                    Toast.makeText(add.this, "added successfully", Toast.LENGTH_SHORT).show();
                                                                    binding.etSomeNotices.setText("");
                                                                    binding.etDateAdd.setText("");
                                                                    binding.etTimeAdd.setText("");

                                                                }
                                                            }
                                                    ).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {

                                                        }
                                                    });


                                                }else{
                                                    Toast.makeText(add.this, "time in the past", Toast.LENGTH_SHORT).show();
                                                }

                                            } else {

                                            }

                                        } else {


                                        }


                                    } else {


                                    }

                                } else {

                                }

                            } else {

                            }

                        } else {

                        }

                    } else {
                        Toast.makeText(add.this, "choose a company", Toast.LENGTH_SHORT).show();
                    }
                }
            });


        }





    void show_date_dialog() {

        DatePickerDialog StartTime = new DatePickerDialog(add.this, new DatePickerDialog.OnDateSetListener() {


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
        mTimePicker = new TimePickerDialog(add.this, new TimePickerDialog.OnTimeSetListener() {
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

    public boolean isValidDate(int year, int month, int day) {
        int y = Calendar.getInstance().get(Calendar.YEAR);
        int m = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int d = Calendar.getInstance().get(Calendar.DATE);

        // Toast.makeText(getActivity(), m+"", Toast.LENGTH_SHORT).show();
        boolean ret = false;

        if (year == y) {

            if ((month + 1) >= m) {

                if (day >= d) {

                    ret = true;

                }


            }


        } else if (year > y) {

            ret = true;

        } else {

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

    public boolean isNotBeazyCompany(){

          valid = false;
        firestor.collection("Appointment-"+auth.getUid()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                List<DocumentSnapshot> snapshots = queryDocumentSnapshots.getDocuments();



                    for(DocumentSnapshot snapshot : snapshots){
                      int day =   snapshot.getLong("day").intValue();
                     int minut =    snapshot.getLong("minut").intValue();
                      int hour  =   snapshot.getLong("hour").intValue();

                        if(day!=add.this.day || minut!=add.this.menit || hour!=add.this.hours){

                            if((add.this.menit+30)>minut){

                                valid = true;

                            }else{
                                Toast.makeText(add.this, "لا يوجد متسع من الوقت لحجز موعد", Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            Toast.makeText(add.this, "no valid time", Toast.LENGTH_SHORT).show();
                            valid = false;
                        }

                    }


             }
        })
     .addOnFailureListener(new OnFailureListener() {
         @Override
         public void onFailure(@NonNull Exception e) {

         }
     });

       return  valid;
    }


    public boolean isValidTime2(){
        boolean valid = false;
       int currentHour =  Calendar.getInstance().get(Calendar.HOUR);
       int currentDay =  Calendar.getInstance().get(Calendar.DATE);

        Toast.makeText(appCompatActivity, hours+"", Toast.LENGTH_SHORT).show();
       int h = hours;
       if(day==currentDay) {

           if (h >12) {
               h -= 12;
           }

           if (currentHour >12) {
               currentHour -= 12;
           }

           if ((h-currentHour) < 0) {

               valid = false;

           } else {

               valid = true;

           }




       }else if(day>currentDay){

           valid = true;

       }

       return  valid;

    }

    public boolean isValidDatehour(int year, int month, int day, int hour3,int minut) {
       // Toast.makeText(ge(), hour3 + "", Toast.LENGTH_SHORT).show();

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

                        } else {
                            //   Toast.makeText(getActivity(), "invalid hour", Toast.LENGTH_SHORT).show();

                        }


                    } else {
                        //  Toast.makeText(getActivity(), "invalid day", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    //  Toast.makeText(getActivity(), "invalid month", Toast.LENGTH_SHORT).show();
                }

            } else {
                //  Toast.makeText(getActivity(), "invalid year", Toast.LENGTH_SHORT).show();
            }

        } else {
            ret = true;
        }
        return ret;
    }









    private Boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Network nw = connectivityManager.getActiveNetwork();
            if (nw == null) return false;
            NetworkCapabilities actNw = connectivityManager.getNetworkCapabilities(nw);
            return actNw != null && (actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) || actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH));
        } else {
            NetworkInfo nwInfo = connectivityManager.getActiveNetworkInfo();
            return nwInfo != null && nwInfo.isConnected();
        }
    }


    public void startReciver() {
         receiver_wifi_status = new MyReceiver_wifi_status(binding , appCompatActivity);

        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(receiver_wifi_status, filter);

    }

    public void StopReciver() {
        unregisterReceiver(receiver_wifi_status);

    }
    @Override
    protected void onPostResume() {
        super.onPostResume();
        startReciver();
    }

    @Override
    protected void onPause() {
        super.onPause();
StopReciver();
    }
}