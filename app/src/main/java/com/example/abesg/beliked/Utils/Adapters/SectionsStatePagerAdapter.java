package com.example.abesg.beliked.Utils.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SectionsStatePagerAdapter extends FragmentStatePagerAdapter {

    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final HashMap<Fragment,Integer> mFragments = new HashMap<>();
    private final HashMap<String, Integer> mFragmentNumbers = new HashMap<>();
    private final HashMap<Integer,String> mFragmentNames = new HashMap<>();

    public SectionsStatePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFragment(Fragment fragment, String fragmentName)
    {
        mFragmentList.add(fragment);
        int fragmentNumber = mFragmentList.size()-1;
        mFragments.put(fragment,fragmentNumber);
        mFragmentNumbers.put(fragmentName,fragmentNumber);
        mFragmentNames.put(fragmentNumber,fragmentName);
    }

    public Integer getFragmentNumber(String fragmentName)
    {
        return mFragmentNumbers.get(fragmentName);
    }

    public String getFragmentName(Integer fragmentNumber)
    {
        return mFragmentNames.get(fragmentNumber);
    }

    public Integer getFragmentNumber(Fragment fragment)
    {
        return mFragments.get(fragment);
    }

}
