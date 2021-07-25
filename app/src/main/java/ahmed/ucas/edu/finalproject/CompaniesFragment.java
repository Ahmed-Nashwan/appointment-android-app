package ahmed.ucas.edu.finalproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import ahmed.ucas.edu.finalproject.Adapters.CompaniesAdapter;
import ahmed.ucas.edu.finalproject.Classes.Company;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CompaniesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CompaniesFragment extends Fragment {



    RecyclerView recyclerView_companies;
    FirebaseFirestore firebaseFirestore;
    ArrayList<Company> companies;
    Company company;
    ProgressBar progressBar;
    CompaniesAdapter adapter2;
    ImageView image_back_update;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String query = "";
    private String mParam2;

    public CompaniesFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static CompaniesFragment newInstance(String param1) {
        CompaniesFragment fragment = new CompaniesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            query = getArguments().getString(ARG_PARAM1);


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        firebaseFirestore = FirebaseFirestore.getInstance();
        View view = inflater.inflate(R.layout.companiesfragment, container, false);
        recyclerView_companies = view.findViewById(R.id.recycler_companies);
        companies = new ArrayList<>();
        image_back_update = view.findViewById(R.id.image_back_update);
        progressBar = view.findViewById(R.id.progress_recycler_company);

        image_back_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(MyReceiver_wifi_status.isNetworkAvailable(getActivity())) {
                    allCompanies();
                }else{
                    Toast.makeText(getActivity(), "check your internet", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



                if(!query.isEmpty()){
                    image_back_update.setVisibility(View.VISIBLE);
                    searchCompany(query);
                }else{
                    image_back_update.setVisibility(View.GONE);
                    allCompanies();
                }

//



    }


    void searchCompany(String query){


        if(MyReceiver_wifi_status.isNetworkAvailable(getActivity())){

            String  [] paths= new String[2];
            paths[0] = "Company/company_search Arrays";
            paths[1] = "Company/company_name Ascending";
            companies.clear();
            firebaseFirestore.collection("Company").whereArrayContains("company_search",query).get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {

                        List<DocumentSnapshot>  documentSnapshots =    queryDocumentSnapshots.getDocuments();

                        if(documentSnapshots.size()!=0){

                            for(DocumentSnapshot documentSnapshot : documentSnapshots){

                                company = new Company();
                                company.setLongt(documentSnapshot.getDouble("longt"));
                                company.setLat(documentSnapshot.getDouble("lat"));
                                company.setCompany_image(documentSnapshot.getString("company_image"));
                                company.setEnd_at(documentSnapshot.getLong("end_at").intValue());
                                company.setStart_at(documentSnapshot.getLong("start_at").intValue());
                                company.setDocId(documentSnapshot.getString("docId"));
                                company.setCompany_type(documentSnapshot.getString("company_type"));
                                company.setCompany_name( documentSnapshot.getString("company_name"));

                                companies.add(company);


                            }
                            adapter2 = new CompaniesAdapter(companies, getActivity(), new CompaniesAdapter.onClickRecycler() {
                                @Override
                                public void onRecyclerClickLisntener(Company company) {

//                      Intent  intent = new Intent(getActivity(),details.class);
//                      intent.putExtra("company",company);
//                      getActivity().startActivity(intent);

                                    if(MyReceiver_wifi_status.isNetworkAvailable(getActivity())) {
                                        Double longt = company.getLongt();
                                        Double lat = company.getLat();

                                        Intent intent = new Intent(getActivity(), mapActivity.class);
                                        intent.putExtra("long", longt);
                                        intent.putExtra("let", lat);
                                        getActivity().startActivity(intent);

                                    }else{

                                        Toast.makeText(getActivity(), "check your internet", Toast.LENGTH_SHORT).show();
                                    }


                                }
                            });

                        }
                        recyclerView_companies.setLayoutManager(new LinearLayoutManager(getActivity()));
                        recyclerView_companies.setAdapter(adapter2);
                        progressBar.setVisibility(View.GONE);
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });



        }else{
            Toast.makeText(getActivity(), "check your internet", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        }

    }


    public void allCompanies(){
        companies.clear();
        image_back_update.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
                firebaseFirestore.collection("Company").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

              List<DocumentSnapshot> documentSnapshots =  queryDocumentSnapshots.getDocuments();




              for(DocumentSnapshot documentSnapshot : documentSnapshots){
                  company = new Company();
                  company.setLongt(documentSnapshot.getDouble("longt"));
                  company.setLat(documentSnapshot.getDouble("lat"));
                  company.setCompany_image(documentSnapshot.getString("company_image"));
                  company.setEnd_at(documentSnapshot.getLong("end_at").intValue());
                  company.setStart_at(documentSnapshot.getLong("start_at").intValue());
                  company.setDocId(documentSnapshot.getString("docId"));
                  company.setCompany_type(documentSnapshot.getString("company_type"));
                  company.setCompany_name( documentSnapshot.getString("company_name"));

                  companies.add(company);
              }

              CompaniesAdapter adapter = new CompaniesAdapter(companies, getActivity(), new CompaniesAdapter.onClickRecycler() {
                  @Override
                  public void onRecyclerClickLisntener(Company company) {

//                      Intent  intent = new Intent(getActivity(),details.class);
//                      intent.putExtra("company",company);
//                      getActivity().startActivity(intent);

                      if(MyReceiver_wifi_status.isNetworkAvailable(getActivity())) {
                          Double longt = company.getLongt();
                          Double lat = company.getLat();

                          Intent intent = new Intent(getActivity(), mapActivity.class);
                          intent.putExtra("long", longt);
                          intent.putExtra("let", lat);
                          getActivity().startActivity(intent);

                      }else{

                          Toast.makeText(getActivity(), "check your internet", Toast.LENGTH_SHORT).show();
                      }


                  }
              });

                recyclerView_companies.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView_companies.setAdapter(adapter);
                progressBar.setVisibility(View.GONE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }
}