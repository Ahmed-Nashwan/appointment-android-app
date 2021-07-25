package ahmed.ucas.edu.finalproject;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.ferfalk.simplesearchview.SimpleSearchView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import ahmed.ucas.edu.finalproject.Classes.Appointment;
import ahmed.ucas.edu.finalproject.RoomDatabase.Entety;
import ahmed.ucas.edu.finalproject.RoomDatabase.RoomDb;
import ahmed.ucas.edu.finalproject.RoomDatabase.SQLdatabase.MyDatabase;
import ahmed.ucas.edu.finalproject.ui.main.SectionsPagerAdapter;
import io.reactivex.CompletableObserver;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {


    FirebaseAuth auth;
    Toolbar toolbar;
    SimpleSearchView simpleSearchView;
    boolean searchViewShown;
    List<Entety> enteties;
    int i;
    public static String database_name = "appointments";
  //  public static MyDatabase database;
    FloatingActionButton button;
    static  MyDatabase   database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        button = findViewById(R.id.btn_floating2);
        simpleSearchView = findViewById(R.id.searchView);
        ViewPager viewPager = findViewById(R.id.view_pager);
        TabLayout tabs = findViewById(R.id.tabs);
        simpleSearchView.setTabLayout(tabs);
        tabs.setupWithViewPager(viewPager);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Home");
        auth = FirebaseAuth.getInstance();
        JobInfo info1;
        enteties = new ArrayList<>();

        database = new MyDatabase(getApplicationContext(),"appointments",null,4);



        Appointment appointment = new Appointment();
        appointment.setCompany_id("company id");
        appointment.setCompany_name("company name");
        appointment.setDay(16);
        appointment.setDocId("doc id");
        appointment.setHour(33);
        appointment.setMinut(59);
        appointment.setYear(2021);
        appointment.setMonth(12);
        appointment.setNotice("notice");


        if (getSharedPreferences("switch", MODE_PRIVATE).getBoolean("caching", false)) {
            // deleteAllAppointment();
            ComponentName info = new ComponentName(getBaseContext(), MyService.class);
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.N) {

                info1 = new JobInfo.Builder(1, info)
                        .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                        .setPeriodic(216000000)   //216000000
                        .build();

            } else {

                info1 = new JobInfo.Builder(1, info)
                        .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                        .setMinimumLatency(216000000)   //21600000
                        .build();

            }

            JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
            scheduler.schedule(info1);

        }



        button.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), add.class);
            startActivity(intent);
        });

//
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if (tab.getPosition() == 1) {

                    searchViewShown = true;

                } else {

                    searchViewShown = false;

                }

                invalidateOptionsMenu();
              //  viewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        simpleSearchView.setOnQueryTextListener(new SimpleSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                CompaniesFragment fragment = CompaniesFragment.newInstance(query);
                fragmentTransaction.replace(R.id.main_fragment_container2, fragment);

                fragmentTransaction.commit();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {


                return false;
            }

            @Override
            public boolean onQueryTextCleared() {


                return false;
            }
        });


        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());


        viewPager.setAdapter(sectionsPagerAdapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mymenu, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        simpleSearchView.setMenuItem(item);


        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.logout:

                auth.signOut();
                Intent i1 = new Intent(getApplicationContext(), login.class);
                i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i1);

                Toast.makeText(MainActivity.this, "logout successfully", Toast.LENGTH_SHORT).show();
                getSharedPreferences("Status", MODE_PRIVATE).edit().clear().commit();
                getSharedPreferences("switch", MODE_PRIVATE).edit().clear().commit();
                return true;

            case R.id.addNotice:
                Intent i = new Intent(getApplicationContext(), addnotice.class);
                startActivity(i);
                return true;

            case R.id.delete_account:
                getSharedPreferences("Status", MODE_PRIVATE).edit().clear().commit();
                getSharedPreferences("switch", MODE_PRIVATE).edit().clear().commit();

                auth.getCurrentUser().delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Intent i1 = new Intent(getApplicationContext(), login.class);
                        i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i1);
                        Toast.makeText(MainActivity.this, "deleted successfully", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

                return true;

        }

        return super.onOptionsItemSelected(item);


    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem itm = menu.findItem(R.id.action_search);
        if (searchViewShown) {
            itm.setVisible(true);
        } else {
            itm.setVisible(false);
        }
        return true;
    }



    @Override
    public void onBackPressed() {
        if (simpleSearchView.onBackPressed()) {
            return;
        }

        super.onBackPressed();
    }


    public static   boolean insertDatabase(String object) {

        SQLiteDatabase write = database.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("object", object);

        long i = write.insert("Appointment", null, contentValues);

        if (i > 0) {
            return true;
        } else {
            return false;
        }

    }

    public static boolean deleteAllDatabase() {
        SQLiteDatabase writableDatabase = database.getWritableDatabase();

        long id = writableDatabase.delete("Appointment", null, null);

        if (id > 0) {
            return true;
        } else {
            return false;
        }
    }

}