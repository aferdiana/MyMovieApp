package me.aldy.mylastsubmission.ui.favorite;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import me.aldy.mylastsubmission.R;
import me.aldy.mylastsubmission.ui.favorite.tab.FavMovieFragment;
import me.aldy.mylastsubmission.ui.favorite.tab.FavTvFragment;

public class SectionsPagerAdapter extends FragmentPagerAdapter {
    private static final int[] TAB_TITLE = new int[]{R.string.nav_movie, R.string.nav_tv};
    private Fragment[] childFragments;
    private Context c;

    public SectionsPagerAdapter(FragmentManager fm, Context context){
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        c = context;
        childFragments = new Fragment[]{
                new FavMovieFragment(),
                new FavTvFragment()
        };
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return childFragments[position];
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return c.getResources().getString(TAB_TITLE[position]);
    }

    @Override
    public int getCount() {
        return 2;
    }
}
