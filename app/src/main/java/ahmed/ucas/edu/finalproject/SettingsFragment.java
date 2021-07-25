package ahmed.ucas.edu.finalproject;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import ahmed.ucas.edu.finalproject.databinding.ActivitySignupBinding;
import ahmed.ucas.edu.finalproject.databinding.SettingsfragmentBinding;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    EditText et_username;
    Switch allow_notification;
    Switch allow_caching;
    ImageView image_edit;
    Button btn_update;
    FirebaseFirestore firebaseFirestore;
    String username ="";
    String userid ="";
    int status = 0;
    ImageView imageView;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public SettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment settings_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.settingsfragment, container, false);
        firebaseFirestore = FirebaseFirestore.getInstance();
        et_username =  view.findViewById(R.id.et_username_settings_fragment);
        et_username.setEnabled(false);
        allow_notification =  view.findViewById(R.id.switch_allow_notification);
        allow_caching =  view.findViewById(R.id.switch_allow_caching);
        if(getActivity().getSharedPreferences("switch",MODE_PRIVATE).getBoolean("caching",false)){
         allow_caching.setChecked(true);
        }
        image_edit =  view.findViewById(R.id.image_edit_settings_fragment);
        btn_update =  view.findViewById(R.id.btn_update_Settings);
        imageView = view.findViewById(R.id.imageView2);

        allow_caching.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                SharedPreferences.Editor editor = getActivity().getSharedPreferences("switch", MODE_PRIVATE).edit();
                if(b){
                    editor.putBoolean("caching",true);
                }else{
                    editor.putBoolean("caching",false);
                }
                editor.apply();
            }
        });
        // Inflate the layout for this fragment

        return view;




    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        FirebaseAuth auth = FirebaseAuth.getInstance();

                 userid =    auth.getUid();

        firebaseFirestore.collection("Users").document(userid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                username = documentSnapshot.getString("full_name");

                et_username.setText(username);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });



        image_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MyReceiver_wifi_status.isNetworkAvailable(getActivity())) {
                    status = 1;
                    et_username.setEnabled(true);
                }else{
                    Toast.makeText(getActivity(), "check your internet", Toast.LENGTH_SHORT).show();
                }

            }
        });


        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(MyReceiver_wifi_status.isNetworkAvailable(getActivity())) {

                    if (status == 1) {
                        if (!et_username.getText().toString().equals(username)) {

                            String newname = et_username.getText().toString();


                            firebaseFirestore.collection("Users").document(userid).update("full_name", newname).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    et_username.setEnabled(false);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });


                        } else {
                            Toast.makeText(getActivity(), "change your name", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(getActivity(), "nothing to update", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(getActivity(), "check your internet", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
}