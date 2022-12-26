package com.pequla.playerfinder.adapter;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.pequla.playerfinder.R;
import com.pequla.playerfinder.fragment.PlayerFragment;
import com.pequla.playerfinder.fragment.SavedFragment;
import com.pequla.playerfinder.fragment.StatusFragment;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{
            R.string.tab_text_1,
            R.string.tab_text_2,
            R.string.tab_text_3
    };
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // Server status fragment
        if (position == 1) {
            String serverAddress = "atm8.g.akliz.net";
            String serverName = "All The Mods 8";
            return StatusFragment.newInstance(serverAddress, serverName);
        }

        // Saved players fragment
        if (position == 2) {
            return SavedFragment.newInstance("x", "o");
        }

        // Player fragment
        return PlayerFragment.newInstance();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Total count of tabs to display
        return 3;
    }
}