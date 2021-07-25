package ahmed.ucas.edu.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import ahmed.ucas.edu.finalproject.Classes.User;
import ahmed.ucas.edu.finalproject.databinding.ActivitySignupBinding;

public class signup extends AppCompatActivity {

    ActivitySignupBinding binding;

    FirebaseFirestore firestore;
    FirebaseAuth auth;

    String gender;
    User user;
    String location = "";

    String email;
    String password;
    String repassword;
    String firstname;
    String userUID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        user = new User();



        binding.imageBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.spinnerLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                switch (i) {

                    case 0:
                        location = "";
                        break;
                    case 1:
                        location = "Gaza";
                        break;
                    case 2:
                        location = "Rafah";
                        break;
                    case 3:
                        location = "Kanunes";
                        break;
                    case 4:
                        location = "Deer albalah";
                        break;
                    case 5:
                        location = "Nuseirat";
                        break;
                    case 6:
                        location = "bate hanoun";
                        break;
                    case 7:
                        location = "bate lahia";
                        break;
                    default:
                        location = "other";
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {


            }
        });
        binding.radioFemale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    gender = "female";
                }
            }
        });

        binding.radioMale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    gender = "male";
                }
            }
        });


        binding.btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!binding.etFullName.getText().toString().isEmpty()) {

                    firstname = binding.etFullName.getText().toString();

                    if (!binding.etEmail.getText().toString().isEmpty()) {

                        email = binding.etEmail.getText().toString();

                        if (!binding.etPassordSignup.getText().toString().isEmpty()) {

                            password = binding.etPassordSignup.getText().toString();

                            if (!binding.etRepasswordSignup.getText().toString().isEmpty()) {

                                repassword = binding.etRepasswordSignup.getText().toString();

                                if (password.equals(repassword)) {

                                    if (!location.isEmpty()) {


                                        if (!gender.isEmpty()) {

                                            binding.btnCreateAccount.startAnimation();

                                            auth.createUserWithEmailAndPassword(email, password)
                                                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                                        @Override
                                                        public void onSuccess(AuthResult authResult) {
                                                          userUID =   authResult.getUser().getUid();
                                                          user.setFull_name(firstname);
                                                          user.setGender(gender);
                                                          user.setLocation(location);
                                                          user.setEmail(email);
                                                          user.setUid(userUID);


                                                          firestore.collection("Users").document(userUID).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                              @Override
                                                              public void onSuccess(Void aVoid) {
                                                                  Toast.makeText(signup.this, "done", Toast.LENGTH_SHORT).show();
                                                                  finish();

                                                              }
                                                          }).addOnFailureListener(new OnFailureListener() {
                                                              @Override
                                                              public void onFailure(@NonNull Exception e) {
                                                                  Toast.makeText(signup.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                                  binding.btnCreateAccount.revertAnimation();
                                                                  binding.btnCreateAccount.setBackground(getResources().getDrawable(R.drawable.btn_signup_signup));
                                                              }
                                                          });

                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(signup.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                    binding.btnCreateAccount.revertAnimation();
                                                    binding.btnCreateAccount.setBackground(getResources().getDrawable(R.drawable.btn_signup_signup));

                                                }
                                            });

                                        } else {
                                            Toast.makeText(signup.this, "Invalid gender", Toast.LENGTH_SHORT).show();

                                        }


                                    } else {

                                        Toast.makeText(signup.this, "select location", Toast.LENGTH_SHORT).show();
                                    }


                                } else {

                                    Toast.makeText(signup.this, "password dose not matched", Toast.LENGTH_SHORT).show();
                                }


                            } else {

                                Toast.makeText(signup.this, "Invalid repassword", Toast.LENGTH_SHORT).show();
                            }


                        } else {
                            Toast.makeText(signup.this, "Invalid password", Toast.LENGTH_SHORT).show();

                        }


                    } else {

                        Toast.makeText(signup.this, "Invalid email", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(signup.this, "Invalid name", Toast.LENGTH_SHORT).show();
                }


            }

        });


    }
}