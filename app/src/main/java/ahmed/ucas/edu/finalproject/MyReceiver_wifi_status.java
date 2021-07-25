package ahmed.ucas.edu.finalproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Keep;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import  ahmed.ucas.edu.finalproject.databinding.ActivityAddBinding;
import static android.content.Context.CONNECTIVITY_SERVICE;
import static android.content.Context.WIFI_SERVICE;

@Keep

public class MyReceiver_wifi_status extends BroadcastReceiver {

    ActivityAddBinding  binding;
    AppCompatActivity appCompatActivity;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    noconnection noconnection;

    MyReceiver_wifi_status(){

    }
    MyReceiver_wifi_status(ahmed.ucas.edu.finalproject.databinding.ActivityAddBinding binding, AppCompatActivity appCompatActivity) {
        this.binding = binding;
        this.appCompatActivity = appCompatActivity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {


        if (intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")) {


            if (!isNetworkAvailable(context)) {


                Toast.makeText(context, "Check your internet", Toast.LENGTH_SHORT).show();
                fragmentManager = appCompatActivity.getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                 noconnection = new noconnection();
                fragmentTransaction.add(R.id.container_wifi, noconnection);
                    fragmentTransaction.commit();

                binding.etTimeAdd.setVisibility(View.GONE);
                binding.etDateAdd.setVisibility(View.GONE);
                binding.btnAddNewAppointment.setVisibility(View.GONE);
                binding.spinnerCompanies.setVisibility(View.GONE);
                binding.etSomeNotices.setVisibility(View.GONE);
                binding.titleAdd.setVisibility(View.GONE);
            } else {
                    if(noconnection!=null) {
                        appCompatActivity.getSupportFragmentManager().beginTransaction().remove(noconnection).commit();
                    }

                Toast.makeText(context, "Connected", Toast.LENGTH_SHORT).show();
                binding.etTimeAdd.setVisibility(View.VISIBLE);
                binding.etDateAdd.setVisibility(View.VISIBLE);
                binding.btnAddNewAppointment.setVisibility(View.VISIBLE);
                binding.spinnerCompanies.setVisibility(View.VISIBLE);
                binding.etSomeNotices.setVisibility(View.VISIBLE);
                binding.titleAdd.setVisibility(View.VISIBLE);
            }





        }


    }

    public static Boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Network nw = connectivityManager.getActiveNetwork();
            if (nw == null) return false;
            NetworkCapabilities actNw = connectivityManager.getNetworkCapabilities(nw);
            return actNw != null && (actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) || actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH));
        } else {
            NetworkInfo nwInfo = connectivityManager.getActiveNetworkInfo();
            return nwInfo != null && nwInfo.isConnected();
        }
    }

}
