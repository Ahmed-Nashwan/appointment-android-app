package ahmed.ucas.edu.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.skyhope.eventcalenderlibrary.CalenderEvent;
import com.skyhope.eventcalenderlibrary.listener.CalenderDayClickListener;
import com.skyhope.eventcalenderlibrary.model.DayContainerModel;
import com.skyhope.eventcalenderlibrary.model.Event;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import ahmed.ucas.edu.finalproject.Adapters.AppointmentAdapter;
import ahmed.ucas.edu.finalproject.Classes.Appointment;
import ahmed.ucas.edu.finalproject.RoomDatabase.SQLdatabase.MyDatabase;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class MainFragment extends Fragment {

    CalenderEvent calenderEvent;
    RecyclerView recyclerView_main_appointment;
    FirebaseFirestore firebaseFirestore;
    ArrayList<Appointment> appointments;
    ArrayList<Appointment> appointmentsOffLine;
    ArrayList<Appointment> appointmentsOffLine2;
    Appointment appointment;
    FirebaseAuth auth;
    MyDatabase database;
    ProgressBar progress_main;
    AppointmentAdapter appointmentAdapter;
    Appointment objectAppointment;
    AppointmentAdapter appointmentAdapter22;
    ArrayList<Appointment> arrayList;
    int hour;
    int day;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

    }

    public MainFragment() {

        // Required empty public constructor

    }



    // TODO: Rename and change types and number of parameters

    public static MainFragment newInstance(String param1) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);

            // mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        firebaseFirestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        appointments = new ArrayList<>();
        appointmentsOffLine = new ArrayList<>();
        appointmentsOffLine2 = new ArrayList<>();
        arrayList = new ArrayList<>();
         database = new MyDatabase(getActivity(),"appointments",null,4);


        View v = inflater.inflate(R.layout.mainfragment, container, false);
        calenderEvent = v.findViewById(R.id.calendarView_main_fragment);
        recyclerView_main_appointment = v.findViewById(R.id.recycler_main_fragment);
        progress_main = v.findViewById(R.id.progress_main);
        progress_main.setVisibility(View.GONE);


        return v;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        drawAllEvent();
        appointments.clear();
        appointmentsOffLine.clear();
        appointmentsOffLine2.clear();
        calenderEvent.initCalderItemClickCallback(new CalenderDayClickListener() {
            @Override
            public void onGetDay(DayContainerModel dayContainerModel) {
                day = dayContainerModel.getDay();
                if (MyReceiver_wifi_status.isNetworkAvailable(getActivity())) {
                    appointments.clear();
                    arrayList.clear();
                    appointmentsOffLine.clear();
                    progress_main.setVisibility(View.VISIBLE);


                    if (isValidDate(dayContainerModel.getYear(), dayContainerModel.getMonthNumber(), dayContainerModel.getDay())) {


                        firebaseFirestore.collection("Appointment-" + auth.getUid()).whereEqualTo("year", dayContainerModel.getYear()).whereEqualTo("month", (dayContainerModel.getMonthNumber() + 1))
                                .whereEqualTo("day", dayContainerModel.getDay()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                                List<DocumentSnapshot> documentSnapshots = queryDocumentSnapshots.getDocuments();


                                if (documentSnapshots.size() != 0) {

                                    for (DocumentSnapshot documentSnapshot : documentSnapshots) {
                                        appointment = new Appointment();


                                        int y = documentSnapshot.getLong("year").intValue();
                                        int m = documentSnapshot.getLong("month").intValue();
                                        int d = documentSnapshot.getLong("day").intValue();
                                        int h = documentSnapshot.getLong("hour").intValue();
                                        int mi = documentSnapshot.getLong("minut").intValue();
                                        appointment.setCompany_name(documentSnapshot.getString("company_name"));
                                        appointment.setCompany_id(documentSnapshot.getString("company_id"));
                                        appointment.setDocId(documentSnapshot.getString("docId"));
                                        appointment.setNotice(documentSnapshot.getString("notice"));
                                        appointment.setTime(documentSnapshot.getLong("time").intValue());
                                        appointment.setYear(y);
                                        appointment.setMonth(m);
                                        appointment.setDay(d);
                                        appointment.setHour(h);
                                        appointment.setMinut(mi);

                                        if (isValidDatehour(y, m, d, h, mi)) {

                                            appointments.add(appointment);

                                        }

                                    }

                                    appointmentAdapter = new AppointmentAdapter(appointments, getActivity(), new AppointmentAdapter.onClickListener() {
                                        @Override
                                        public void onRecyclerClick(Appointment appointment) {

                                            Intent intent = new Intent(getActivity(), details.class);
                                            intent.putExtra("Appointment", appointment);
                                            getActivity().startActivity(intent);

                                        }
                                    }, new AppointmentAdapter.onClickListenerLocation() {
                                        @Override
                                        public void onImageClick(Appointment appointment) {

//                                        Intent intent = new Intent(getActivity(), mapActivity.class);
//                                        intent.putExtra("lat", appointment.get);
//                                        getActivity().startActivity(intent);

                                        }

                                    });
                                    recyclerView_main_appointment.setLayoutManager(new LinearLayoutManager(getActivity()));
                                    recyclerView_main_appointment.setAdapter(appointmentAdapter);

                                } else {

                                    Toast.makeText(getActivity(), "no appointment", Toast.LENGTH_SHORT).show();
                                }
                                progress_main.setVisibility(View.GONE);

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                progress_main.setVisibility(View.GONE);

                            }
                        });


                    } else {

                        Toast.makeText(getActivity(), "Date selected is in the past", Toast.LENGTH_SHORT).show();
                        progress_main.setVisibility(View.GONE);

                    }





                } else {

                    appointments.clear();
                    appointmentsOffLine.clear();
                    appointmentsOffLine2.clear();
                    arrayList.clear();

                    if (isValidDate(dayContainerModel.getYear(), dayContainerModel.getMonthNumber(), dayContainerModel.getDay())) {

                        if (getActivity().getSharedPreferences("switch", MODE_PRIVATE).getBoolean("caching", false)) {

                            // TODO : get the data from database when internet connected and save it in room database and get it here

                            arrayList = getAllAppointmentDatabase();

                            progress_main.setVisibility(View.VISIBLE);


                            if (arrayList.size() != 0) {

                                for (Appointment appointment : arrayList) {


                                    if (appointment.getYear() == dayContainerModel.getYear() && appointment.getMonth() ==
                                            (dayContainerModel.getMonthNumber() + 1) && appointment.getDay() == dayContainerModel.getDay()) {
                                        if (isValidDatehour(appointment.getYear(), appointment.getMonth(), appointment.getDay(), appointment.getHour(), appointment.getMinut())) {

                                            if (isValidDatehour(appointment.getYear(), appointment.getMonth(), appointment.getDay(), appointment.getHour(), appointment.getMinut())) {
                                                appointmentsOffLine.add(appointment);
                                            }

                                        }
                                    }
                                }


                                if (appointmentsOffLine.size() == 0) {
                                    Toast.makeText(getActivity(), "no Appointment", Toast.LENGTH_SHORT).show();
                                }

                                appointmentAdapter22 = new AppointmentAdapter(appointmentsOffLine, getActivity(), new AppointmentAdapter.onClickListener() {


                                    @Override
                                    public void onRecyclerClick(Appointment appointment) {

                                        Intent intent = new Intent(getActivity(), details.class);
                                        intent.putExtra("Appointment", appointment);
                                        getActivity().startActivity(intent);

                                    }
                                }, new AppointmentAdapter.onClickListenerLocation() {
                                    @Override
                                    public void onImageClick(Appointment appointment) {

                                    }
                                });

                                recyclerView_main_appointment.setLayoutManager(new LinearLayoutManager(getActivity()));
                                recyclerView_main_appointment.setAdapter(appointmentAdapter22);
                                // appointmentAdapter22.notifyDataSetChanged();
                            }
                            progress_main.setVisibility(View.GONE);


                        } else {
                            progress_main.setVisibility(View.GONE);
                            // TODO : show dialog to user that you can allow caching from settings to get data when internet is not avilible
                            Toast.makeText(getActivity(), "no Internet connection", Toast.LENGTH_SHORT).show();
                            Toast.makeText(getActivity(), "caching is not allowed", Toast.LENGTH_SHORT).show();
                        }


                    } else {
                        Toast.makeText(getActivity(), "this time in the past", Toast.LENGTH_SHORT).show();
                    }
                }

            }

        });


    }

    public boolean isValidDate(int year, int month, int day) {
        int y = Calendar.getInstance().get(Calendar.YEAR);
        int m = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int d = Calendar.getInstance().get(Calendar.DATE);


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

    public boolean isValidDatehour(int year, int month, int day, int hour3, int minute) {
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

                            if (minute > mi) {

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

    public void drawAllEvent() {


        if (MyReceiver_wifi_status.isNetworkAvailable(getActivity())) {
            firebaseFirestore.collection("Appointment-" + auth.getUid()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                    List<DocumentSnapshot> snapshots = queryDocumentSnapshots.getDocuments();

                    if (snapshots.size() != 0) {

                        for (DocumentSnapshot snapshot : snapshots) {

                            Calendar calendar = Calendar.getInstance();

                            calendar.set(Calendar.YEAR, snapshot.getLong("year").intValue());
                            calendar.set(Calendar.MONTH, (snapshot.getLong("month").intValue() - 1));
                            calendar.set(Calendar.DATE, snapshot.getLong("day").intValue());
                            hour = snapshot.getLong("hour").intValue();


                            Event event = new Event(calendar.getTimeInMillis(), "o", Color.RED);
                            calenderEvent.addEvent(event);


                        }


                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });

        } else {


            if (getActivity().getSharedPreferences("switch", MODE_PRIVATE).getBoolean("caching", false)) {


                ArrayList<Appointment> entetyArrayList = getAllAppointmentDatabase();


                for (Appointment appointment : entetyArrayList) {

                    appointmentsOffLine2.add(appointment);

                }

                AppointmentAdapter appointmentAdapter2 = new AppointmentAdapter(appointmentsOffLine2, getActivity(), new AppointmentAdapter.onClickListener() {
                    @Override
                    public void onRecyclerClick(Appointment appointment) {
                        Intent intent = new Intent(getActivity(), details.class);
                        intent.putExtra("Appointment", appointment);
                        getActivity().startActivity(intent);

                    }
                }, new AppointmentAdapter.onClickListenerLocation() {
                    @Override
                    public void onImageClick(Appointment appointment) {


                    }
                });


                recyclerView_main_appointment.setAdapter(appointmentAdapter2);

            } else {

                Toast.makeText(getActivity(), "check your internet", Toast.LENGTH_SHORT).show();
            }


        }

    }






    public  ArrayList<Appointment> getAllAppointmentDatabase() {
       // MyDatabase database =   MyDatabase.getInstance(context);
        ArrayList arrayList = new ArrayList();
        SQLiteDatabase read = database.getReadableDatabase();

        Cursor cursor = read.rawQuery("select * from Appointment", null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {

            Appointment current = (Appointment) CustomSerializable.deserialize(cursor.getString(cursor.getColumnIndex("object")));
            arrayList.add(current);
            cursor.moveToNext();

        }
        cursor.close();
        return arrayList;
    }






}