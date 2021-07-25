package ahmed.ucas.edu.finalproject.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

import ahmed.ucas.edu.finalproject.CompaniesFragment;
import ahmed.ucas.edu.finalproject.MainFragment;
import ahmed.ucas.edu.finalproject.MapsFragment;
import ahmed.ucas.edu.finalproject.R;
import ahmed.ucas.edu.finalproject.SettingsFragment;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {


    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }


    ArrayList<String> titles = new ArrayList<>();
    ArrayList<Fragment> fragments = new ArrayList<>();

    @Override
    public Fragment getItem(int position) {

        fragments.add(new MainFragment());
        fragments.add(new CompaniesFragment());
        fragments.add(new SettingsFragment());


        //fragment;
        return fragments.get(position);

    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        titles.add("Main");
        titles.add("Company");
        titles.add("Settings");

        return titles.get(position);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 3;
    }


}