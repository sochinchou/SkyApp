package com.sky.modulefragment;

import android.app.Fragment;

/**
 * Created by Administrator on 2016/3/15.
 */
public class ActivityFragmetn1 extends ActivityFragmentBase {

    @Override
    public Fragment createFragment() {
        return new Fragment1();
    }
}
