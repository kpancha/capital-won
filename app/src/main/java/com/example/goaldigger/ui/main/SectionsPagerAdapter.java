package com.example.goaldigger.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.goaldigger.R;
import com.example.goaldigger.models.FragmentUiModel;

import java.util.List;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2};
    private final Context mContext;
    private List<FragmentUiModel> listOfFragments;

    public SectionsPagerAdapter(Context context, FragmentManager fm, List<FragmentUiModel> fragments) {
        super(fm);
        mContext = context;
        this.listOfFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return listOfFragments.get(position).getFragment();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return listOfFragments.get(position).getTitle();
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return listOfFragments.size();
    }
}