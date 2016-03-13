package com.sky.modulefragment;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends Activity {

    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private Fragment1 mFragment1;
    private Fragment2 mFragment2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mFragmentManager = getFragmentManager();
        mFragment1 = new Fragment1();
        mFragment2 = new Fragment2();
    }

    public void onBtn1(View view){
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.fragment_container, mFragment1);
        mFragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        mFragmentTransaction.addToBackStack(null);
        mFragmentTransaction.commit();
    }

    public void onBtn2(View view){
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.fragment_container, mFragment2);
        mFragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        mFragmentTransaction.addToBackStack(null);
        mFragmentTransaction.commit();
    }

    public void onBtn3(View view){
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.remove(mFragment1);
        mFragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        mFragmentTransaction.addToBackStack(null);
        mFragmentTransaction.commit();
    }

    public void onBtn4(View view){
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.remove(mFragment2);
        mFragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        mFragmentTransaction.addToBackStack(null);
        mFragmentTransaction.commit();
    }
}
