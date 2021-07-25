package ahmed.ucas.edu.finalproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class PageviewAdapter extends PagerAdapter {


    Context context;

    ArrayList<DataViewPager> arrayList = new ArrayList<>();

    DataViewPager page1 = new DataViewPager("Welcome","This application gonna Arrange appointments",R.drawable.on_boarding_example1);
    DataViewPager page2 = new DataViewPager("Easy to use","Very easy to use and avalibale on every hours",R.drawable.on_borading_example2);
    DataViewPager page3 = new DataViewPager("Secure accounts","You are in safe site every thing is secure",R.drawable.on_borading_example3);






    public PageviewAdapter(Context context) {
        this.context = context;
        arrayList.add(page1);
        arrayList.add(page2);
        arrayList.add(page3);

    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {

        return  view == object;
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull final ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.sliderscreen,container,false);




        TextView tv_main = view.findViewById(R.id.tv_main);
        TextView tv_sub = view.findViewById(R.id.tv_sub);
        TextView onde1 = view.findViewById(R.id.indecator1);
        TextView onde2 = view.findViewById(R.id.indecator2);
        TextView onde3 = view.findViewById(R.id.indecator3);
        Button btn = view.findViewById(R.id.btn_getting_started);
        ImageView imageView = view.findViewById(R.id.image_onboarding1);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences.Editor editor = context.getSharedPreferences("Status", MODE_PRIVATE).edit();
                editor.putBoolean("isStarted",true);
                editor.putBoolean("isStarted2",true);
                editor.putBoolean("isStarted3",true);
                editor.apply();

                Intent intent = new Intent(context.getApplicationContext(),login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(intent);





            }
        });


        tv_main.setText(arrayList.get(position).getNaimTitlel());
        tv_sub.setText(arrayList.get(position).getSubTitle());
        imageView.setImageResource(arrayList.get(position).getImage());


        switch (position){
            case 0:
                onde1.setBackground(container.getResources().getDrawable(R.drawable.indecator2));
                onde2.setBackground(container.getResources().getDrawable(R.drawable.indecator));
                onde3.setBackground(container.getResources().getDrawable(R.drawable.indecator));
                break;

            case 1:
                onde1.setBackground(container.getResources().getDrawable(R.drawable.indecator));
                onde2.setBackground(container.getResources().getDrawable(R.drawable.indecator2));
                onde3.setBackground(container.getResources().getDrawable(R.drawable.indecator));
                break;

            case 2:
                onde1.setBackground(container.getResources().getDrawable(R.drawable.indecator));
                onde2.setBackground(container.getResources().getDrawable(R.drawable.indecator));
                onde3.setBackground(container.getResources().getDrawable(R.drawable.indecator2));
                break;
        }




        container.addView(view);
        return  view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((View) object);

    }
}
