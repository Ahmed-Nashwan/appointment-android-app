package ahmed.ucas.edu.finalproject.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import ahmed.ucas.edu.finalproject.Classes.Appointment;
import ahmed.ucas.edu.finalproject.R;

public class AppointmentAdapter  extends RecyclerView.Adapter<AppointmentAdapter.View_holder>{


    ArrayList<Appointment> arrayList;
    Context context;
    onClickListener listener;
    onClickListenerLocation listenerLocation;

    public AppointmentAdapter(ArrayList<Appointment> arrayList, Context context, onClickListener listener, onClickListenerLocation listenerLocation) {
        this.arrayList = arrayList;
        this.context = context;
        this.listener = listener;
this.listenerLocation = listenerLocation;
    }


    @NonNull
    @Override
    public AppointmentAdapter.View_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AppointmentAdapter.View_holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item, null));
    }

    @Override
    public void onBindViewHolder(@NonNull View_holder holder, int position) {

     Appointment current =    arrayList.get(position);
     holder.tv_name.setText(current.getCompany_name());
     holder.tv_possition.setText(String.valueOf(position+1));




        String currentdate =    Calendar.getInstance().get(Calendar.DATE)+
                    "/"+(Calendar.getInstance().get(Calendar.MONTH)+1)+"/"+Calendar.getInstance().get(Calendar.YEAR)
                +" "+Calendar.getInstance().get(Calendar.HOUR_OF_DAY)+":"+Calendar.getInstance().get(Calendar.MINUTE);


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/M/yyyy hh:mm");

        try {
            Date CurrentDate = simpleDateFormat.parse(currentdate);
            Date date1Appointment = simpleDateFormat.parse(current.getDay()+"/"+current.getMonth()+"/"+current.getYear()+" "+current.getHour()+":"+current.getMinut());


        String dateDiff =     printDifference(CurrentDate,date1Appointment);
        holder.tv_time.setText(dateDiff);

        } catch (ParseException e) {
            e.printStackTrace();
        }

//1 minute = 60 seconds
//1 hour = 60 x 60 = 3600
//1 day = 3600 x 24 = 86400





//        int hour =    Calendar.getInstance().get(Calendar.HOUR);
//        //   int minut =   Calendar.getInstance().get(Calendar.MINUTE);
//
//        int   hourStart =   current.();
//        int   hourEnd =   company.getEnd_at();
//
//        if (hour >= hourStart) {
//            if (hour < (hourEnd + 12)) {
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


    }





    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    class View_holder extends RecyclerView.ViewHolder {

        TextView tv_name;
        TextView tv_time;
        TextView tv_possition;
        ImageView im_company;
        Button btn_more;


        public View_holder(@NonNull View itemView) {
            super(itemView);

            tv_name = itemView.findViewById(R.id.tv_company_name_main);
            tv_time = itemView.findViewById(R.id.tv_details_time_main);
            tv_possition = itemView.findViewById(R.id.tv_mian_item_possition);
            im_company = itemView.findViewById(R.id.image_location_main);
            btn_more = itemView.findViewById(R.id.btn_more_details_main);

            im_company.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    listenerLocation.onImageClick(arrayList.get(getAdapterPosition()));

                }
            });

            btn_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onRecyclerClick(arrayList.get(getAdapterPosition()));


                }
            });

        }
    }


    public interface onClickListener{

       void onRecyclerClick(Appointment appointment);
    }

    public interface onClickListenerLocation{

        void onImageClick(Appointment appointment);
    }



    public String printDifference(Date startDate, Date endDate) {
        //milliseconds
        String date = "";
        long different = endDate.getTime() - startDate.getTime();


//        int numOfDays = (int) (different / (1000 * 60 * 60 * 24));
//        int hours = (int) (different / (1000 * 60 * 60));
      //  int minutes = (int) (different / (1000 * 60));
       // int seconds = (int) (different / (1000));

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

       long elapsedMinutes = different / minutesInMilli;

        different = different % minutesInMilli;

        //long elapsedSeconds = different / secondsInMilli;

//        System.out.printf(
//                "%d days, %d hours, %d minutes, %d seconds%n",
//                elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds);

        date = elapsedDays+"d "+elapsedHours+"h "+elapsedMinutes+"m";

        return date;
    }
}
