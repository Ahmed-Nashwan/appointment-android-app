package ahmed.ucas.edu.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ahmed.ucas.edu.finalproject.Classes.Company;

public class Spinner_adapter extends BaseAdapter {


        Context context;
        ArrayList<Company> arrayList;
        LayoutInflater inflter;
    ViewHolder holder = new ViewHolder();
        public Spinner_adapter(Context applicationContext, ArrayList<Company> arrayList) {
            this.context = applicationContext;
                this.arrayList = arrayList;
            inflter = (LayoutInflater.from(applicationContext));
        }

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }



    @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = inflter.inflate(R.layout.spinner_item, null);
        holder.textView =  view.findViewById(R.id.tv_company_name_item);
            holder.textView.setText(arrayList.get(i).getCompany_name());
            return view;

    }


   public class ViewHolder{
            TextView textView;
    }
}
