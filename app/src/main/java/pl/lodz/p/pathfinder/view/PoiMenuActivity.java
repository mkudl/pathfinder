package pl.lodz.p.pathfinder.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import java.util.List;

import pl.lodz.p.pathfinder.R;
import pl.lodz.p.pathfinder.model.PointOfInterest;
import pl.lodz.p.pathfinder.presenter.PoiMenuPresenter;
import pl.lodz.p.pathfinder.service.PointOfInterestClient;

public class PoiMenuActivity extends AppCompatActivity
{

    private ProgressBar spinner;
    private TabLayout tabs;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.poi_menu_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        spinner = (ProgressBar) findViewById(R.id.poi_menu_spinner);
        tabs = (TabLayout) findViewById(R.id.sliding_tabs);
        viewPager = (ViewPager) findViewById(R.id.poi_viewpager);



        tabs.setVisibility(View.GONE);
        viewPager.setVisibility(View.GONE);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(PoiMenuActivity.this, PoiAddActivity.class);
                startActivity(intent);

            }
        });



//        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
//        tabLayout.post(new Runnable() {
//            @Override
//            public void run() {
//                tabLayout.setupWithViewPager(viewPager);
//            }
//        });

        PointOfInterestClient poiClient = new PointOfInterestClient(this);

        PoiMenuPresenter presenter = new PoiMenuPresenter(this,poiClient);
        presenter.startActivity();

    }


    public void showSpinner()
    {
        spinner.setVisibility(View.VISIBLE);
    }
    public void hideSpinner()
    {
        spinner.setVisibility(View.GONE);
    }
    public void showUI()
    {
        tabs.setVisibility(View.VISIBLE);
        viewPager.setVisibility(View.VISIBLE);
    }

    public void onDataRetrieved(List<PointOfInterest> created, List<PointOfInterest> favorites)
    {
        //components responsible for the horizontally scrolling tab view
        viewPager.setAdapter(new PoiFragmentPagerAdapter(getSupportFragmentManager(),PoiMenuActivity.this,created, favorites));
        tabs.setupWithViewPager(viewPager);
    }



}
