package com.example.carrie.carrie_test1;

/**
 * Created by jonathan on 2017/8/6.
 */
import android.content.ClipData;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PillPlot extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab3_pillschedule, container, false);

        return rootView;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_swipe_plot, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_settings){
            return true;
        }
        if(id == R.id.action_myinfo){

            return true;
        }
        if(id == R.id.action_myschedule){
            gotoschedule();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void gotoschedule(){
        Intent it = new Intent(this.getActivity(),Choice.class);
        this.startActivity(it);
        Toast.makeText(getActivity(), "have done this method!", Toast.LENGTH_SHORT).show();

    }
    public void goback(View v)
    {
        getActivity().onBackPressed();
    }
}
