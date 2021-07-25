package ahmed.ucas.edu.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import ahmed.ucas.edu.finalproject.databinding.ActivityAddnoticeBinding;

public class addnotice extends AppCompatActivity {

    ActivityAddnoticeBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddnoticeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar2);
        getSupportActionBar().setTitle("Add Suggestion");




        binding.sendNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(binding.etAddNotice.getText().toString().isEmpty()){

                    Toast.makeText(addnotice.this, "Enter a thing", Toast.LENGTH_SHORT).show();
                }else{

                    String sug = binding.etAddNotice.getText().toString();
                    Map<String,String> map = new HashMap<>();
                    map.put("suggestion",sug);
                    FirebaseFirestore.getInstance().collection("Suggestions").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            binding.etAddNotice.setText("");
                            Toast.makeText(addnotice.this, "Tanks for your suggestion", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(addnotice.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }


            }
        });
    }
}