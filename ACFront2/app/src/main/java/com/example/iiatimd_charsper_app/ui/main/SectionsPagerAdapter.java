<<<<<<< HEAD:ACFront2/app/src/main/java/com/example/iiatimd_charsper_app/ui/main/SectionsPagerAdapter.java
package com.example.iiatimd_charsper_app.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.iiatimd_charsper_app.FragmentBugs;
import com.example.iiatimd_charsper_app.FragmentFish;
import com.example.iiatimd_charsper_app.FragmentSeaCreatures;
import com.example.iiatimd_charsper_app.R;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2, R.string.tab_text_3};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = new FragmentBugs();
                break;
            case 1:
                fragment = new FragmentFish();
                break;
            case 2:
                fragment = new FragmentSeaCreatures();
                break;
        }
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }
=======
package com.example.animalcrossingfront.ui.main;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
//

import com.example.animalcrossingfront.FragmentBugs;
import com.example.animalcrossingfront.FragmentDonated;
import com.example.animalcrossingfront.FragmentFish;
import com.example.animalcrossingfront.FragmentSeaCreatures;
import com.example.animalcrossingfront.R;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {
    //    @Casper For Code Info
    // 19-08-21 @Charlaine added donatedfragment.

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2, R.string.tab_text_3, R.string.tab_text_4};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = new FragmentBugs();
                break;
            case 1:
                fragment = new FragmentFish();
                break;
            case 2:
                fragment = new FragmentSeaCreatures();
                break;
            case 3:
                fragment = new FragmentDonated();
                break;


        }
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 4 total pages.
        return 4;
    }
>>>>>>> Char_backend:ACFront2/app/src/main/java/com/example/animalcrossingfront/ui/main/SectionsPagerAdapter.java
}