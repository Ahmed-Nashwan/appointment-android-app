package ahmed.ucas.edu.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

public class onBoarding extends AppCompatActivity {

    ViewPager pager ;
    PageviewAdapter adapter;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);
        pager = findViewById(R.id.viewPager);
        auth = FirebaseAuth.getInstance();


        adapter = new PageviewAdapter(this);
        pager.setAdapter(adapter);


    }
}