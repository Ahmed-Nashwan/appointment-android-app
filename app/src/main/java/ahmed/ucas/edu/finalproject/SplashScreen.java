package ahmed.ucas.edu.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class SplashScreen extends AppCompatActivity {

    FirebaseAuth auth;
    Thread thread;
    SharedPreferences prefs;
    boolean status;
    boolean status2;
    boolean login;
    boolean status3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        auth = FirebaseAuth.getInstance();
         prefs = getSharedPreferences("Status", MODE_PRIVATE);
         status = prefs.getBoolean("isStarted", false);
         status2 = prefs.getBoolean("isStarted2", false);
         login = prefs.getBoolean("isLogin", false);
        status3 = prefs.getBoolean("isStarted3", false);




        thread = new Thread() {
            @Override
            public void run() {
                super.run();

                try {




                    if(status && status2 && status3)  {

                        if(auth.getUid()==null){
                            Thread.sleep(2500);
                            Intent i = new Intent(getApplicationContext(), login.class);
                            startActivity(i);
                            finish();
                        }else if(auth.getUid()!=null){

                            if(login) {
                                Thread.sleep(2000);
                                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(i);

                            }else{
                                Thread.sleep(2500);
                                Intent i = new Intent(getApplicationContext(), login.class);
                                startActivity(i);
                            }
                            finish();

                        }



                    }else {
                        Thread.sleep(3000);
                        Intent i = new Intent(getApplicationContext(), onBoarding.class);
                        startActivity(i);
                        finish();
                    }

                } catch (Exception e) {

                }

            }

        };

        thread.start();








    }
}