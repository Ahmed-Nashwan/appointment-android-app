package ahmed.ucas.edu.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import ahmed.ucas.edu.finalproject.Classes.Company;
import ahmed.ucas.edu.finalproject.databinding.ActivityLoginBinding;

public class login extends AppCompatActivity {
    ActivityLoginBinding binding;

    FirebaseFirestore firestore;
    FirebaseAuth auth;
    String username;
    String password;
    String   documentId;
    Company company;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();




        binding.btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(getApplicationContext(),signup.class);
                startActivity(intent);


            }
        });


        binding.btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                if(!binding.etUsernameLogin.getText().toString().isEmpty()){
                    username = binding.etUsernameLogin.getText().toString();

                    if(!binding.etPasswordLogin.getText().toString().isEmpty()){
                        password = binding.etPasswordLogin.getText().toString();


                        binding.btnLogin.startAnimation();

                        auth.signInWithEmailAndPassword(username,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {

                                SharedPreferences.Editor editor = getSharedPreferences("Status", MODE_PRIVATE).edit();
                                editor.putBoolean("isLogin",true);
                                editor.apply();



                                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);




                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                               // binding.progressBarLogin.setVisibility(View.INVISIBLE);
                                Toast.makeText(login.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                                binding.btnLogin.revertAnimation();
                                binding.btnLogin.setBackground(getResources().getDrawable(R.drawable.btn_login));
                            }
                        });

                    }else{
                        Toast.makeText(login.this, "Empty password", Toast.LENGTH_SHORT).show();
                        Toast.makeText(login.this, "", Toast.LENGTH_SHORT).show();


                    }

                }else{
                    Toast.makeText(login.this, "Empty Email", Toast.LENGTH_SHORT).show();
                    Toast.makeText(login.this, "Empty Email", Toast.LENGTH_SHORT).show();


                }

            }
        });


    }


}