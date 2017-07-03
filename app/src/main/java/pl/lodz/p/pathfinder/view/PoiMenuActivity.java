package pl.lodz.p.pathfinder.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
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
    private FloatingActionButton fab;

    private boolean isForResult = false;

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
        fab = (FloatingActionButton) findViewById(R.id.fab);

        //hide ui
        //doesn't need method; only place it'd be called from anyway
        tabs.setVisibility(View.GONE);
        viewPager.setVisibility(View.GONE);

        PointOfInterestClient poiClient = new PointOfInterestClient(this);

        PoiMenuPresenter presenter = new PoiMenuPresenter(this,poiClient);
        presenter.startActivity();

        isForResult =  getCallingActivity() != null;

    }


    //methods called from presenter
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
        //don't let user add new pois until after data was retrieved
        fab.setOnClickListener( view -> startActivity(new Intent(PoiMenuActivity.this, PoiAddActivity.class)) );

        //components responsible for the horizontally scrolling tab view
        PoiFragmentPagerAdapter pagerAdapter = isForResult ?
                new PoiFragmentPagerAdapterResult(getSupportFragmentManager(),PoiMenuActivity.this,created, favorites) :
                new PoiFragmentPagerAdapterDetails(getSupportFragmentManager(),PoiMenuActivity.this,created, favorites);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(4);
        tabs.setupWithViewPager(viewPager);
    }


    public void displayCreationErrorMessage(Throwable t)
    {
        Snackbar.make( fab,
                getResources().getString(R.string.error_connection_generic),
                Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

}
