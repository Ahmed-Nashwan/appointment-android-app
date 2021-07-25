package ahmed.ucas.edu.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class mapActivity extends AppCompatActivity {

    double let;
    double longt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);


           let =       getIntent().getDoubleExtra("let",0);
        longt =    getIntent().getDoubleExtra("long",0);


        // TODO : this code put it when you want to use map activity

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        MapsFragment fragment = MapsFragment.newInstance(let,longt);
        fragmentTransaction.replace(R.id.container,fragment);
        fragmentTransaction.commit();





    }
}