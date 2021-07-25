package ahmed.ucas.edu.finalproject.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.joda.time.DateTimeUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import ahmed.ucas.edu.finalproject.Classes.Company;
import ahmed.ucas.edu.finalproject.MyReceiver_wifi_status;
import ahmed.ucas.edu.finalproject.R;


public class CompaniesAdapter extends RecyclerView.Adapter<CompaniesAdapter.View_holder> {


    ArrayList<Company> arrayList;
    Context context;
    onClickRecycler clickRecycler;


    public CompaniesAdapter(ArrayList<Company> arrayList, Context context, onClickRecycler clickRecycler) {
        this.arrayList = arrayList;
        this.context = context;
        this.clickRecycler = clickRecycler;

    }


    @NonNull
    @Override
    public View_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new View_holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.company_item, null));
    }


    @Override
    public void onBindViewHolder(@NonNull View_holder holder, int position) {


        Company company = arrayList.get(position);
        holder.tv_name.setText(company.getCompany_name());

        holder.tv_type.setText(company.getCompany_type());

        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        //   int minut =   Calendar.getInstance().get(Calendar.MINUTE);

//        if(hour>12){
//            hour = hour-12;
//        }

        int hourStart = company.getStart_at();
        int hourEnd = company.getEnd_at();

//        if (hour >= (hourStart)) {
//
//            if (hour+12 < (hourEnd+12 )) {
//
//
//                holder.is_open.setAnimation(R.raw.open);
//
//            } else {
//
//                holder.is_open.setAnimation(R.raw.close);
//            }
//        } else {
//
//            holder.is_open.setAnimation(R.raw.close);
//        }

        Calendar cal = Calendar.getInstance(); //Create Calendar-Object
        cal.setTime(new Date());               //Set the Calendar to now
        //Get the hour from the calendar
        if (hour <= (hourEnd + 12) && hour >= hourStart)              // Check if hour is between 8 am and 11pm
        {
            // do whatever you want
            holder.is_open.setAnimation(R.raw.open);
        } else {
            holder.is_open.setAnimation(R.raw.close);
        }


        new Thread(() -> {
            if (MyReceiver_wifi_status.isNetworkAvailable(context)) {

                FirebaseFirestore.getInstance().collection("Appointment-" + FirebaseAuth.getInstance().getUid())
                        .whereEqualTo("company_name", company.getCompany_name()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {


                        holder.tv_size.setText(queryDocumentSnapshots.getDocuments().size() + "");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });


            } else {
                holder.tv_size.setText("0");
            }


        }).start();


        Glide
                .with(context)
                .load(company.getCompany_image())
                .into(holder.im_company);





    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    class View_holder extends RecyclerView.ViewHolder {

        TextView tv_name;
        TextView tv_type;
        TextView tv_size;
        LottieAnimationView is_open;
        ImageView im_company;
        ImageView icon_location;


        public View_holder(@NonNull View itemView) {
            super(itemView);

            tv_name = itemView.findViewById(R.id.tv_company_name);
            tv_type = itemView.findViewById(R.id.tv_company_type);
            tv_size = itemView.findViewById(R.id.tv_no_of_10);
            is_open = itemView.findViewById(R.id.lottie_company);
            im_company = itemView.findViewById(R.id.image_company);
            icon_location = itemView.findViewById(R.id.image_company_location);


            icon_location.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    clickRecycler.onRecyclerClickLisntener(arrayList.get(getAdapterPosition()));

                }
            });


        }
    }


    public interface onClickRecycler {

        void onRecyclerClickLisntener(Company company);
    }



}
