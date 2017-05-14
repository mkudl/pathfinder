package pl.lodz.p.pathfinder.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import pl.lodz.p.pathfinder.Configuration;
import pl.lodz.p.pathfinder.R;
import pl.lodz.p.pathfinder.model.PointOfInterest;
import pl.lodz.p.pathfinder.model.Trip;
import pl.lodz.p.pathfinder.presenter.TripEditPresenter;
import pl.lodz.p.pathfinder.rest.DatabaseTripRest;
import pl.lodz.p.pathfinder.service.TripUploadService;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class TripEditActivity extends AppCompatActivity
{

    private final static int CALL_POI_PICK_CODE = 42099;

    TripEditPresenter presenter;

    Button addTripButton;
    Button finishButton;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_edit);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);


        Trip trip = getIntent().getParcelableExtra("TRIP_PARAM");
        setInitialFieldContent(trip);
        createPresenter(trip);
        bindButtons();
        setAddButtonBehavior();
        setFinishButtonBehavior();
    }

    private void setInitialFieldContent(Trip trip)
    {
        ((EditText) findViewById(R.id.trip_edit_name_content)).setText(trip.getName());
        ((EditText) findViewById(R.id.trip_edit_desc_content)).setText(trip.getDescription());
        trip.getPointOfInterestList().forEach(this::addPoiPickRepresentation);  //display all pois in trip
//        for( PointOfInterest p : trip.getPointOfInterestList())
//        {
//            addPoiPickRepresentation(p);
//        }
    }

    private void bindButtons()
    {
        addTripButton = (Button) findViewById(R.id.trip_edit_button_poi_add);
        finishButton = (Button) findViewById(R.id.trip_edit_button_finish);
    }

    private void setAddButtonBehavior()
    {
        addTripButton.setOnClickListener( v -> {
            Intent intent = new Intent(this,PoiMenuActivity.class);
            startActivityForResult(intent,CALL_POI_PICK_CODE);
        });
    }

    private void setFinishButtonBehavior()
    {
        finishButton.setOnClickListener( v ->
                presenter.saveTrip(
                        ((EditText) findViewById(R.id.trip_edit_name_content)).getText().toString(),
                        ((EditText) findViewById(R.id.trip_edit_desc_content)).getText().toString()
                ));
    }

    private void createPresenter(Trip trip)
    {
        Retrofit rxRetrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Configuration.SERVER_ADDRESS)
                .build();
        DatabaseTripRest restClient = rxRetrofit.create(DatabaseTripRest.class);
        TripUploadService tup = new TripUploadService(restClient);
        presenter = new TripEditPresenter(this,tup,trip);
    }



    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == CALL_POI_PICK_CODE) {
            if (resultCode == Activity.RESULT_OK) {

                PointOfInterest selectedPoi =  data.getParcelableExtra("selectedPoi");

                presenter.addPoi(selectedPoi);
            }
            else {
                displayGenericError();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.trip_edit_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        if(item.getItemId() == R.id.action_delete)
        {
            presenter.deleteTrip();
            return true;
        }

        return false;

    }




    public void displayGenericError()
    {
        Toast.makeText(this,getResources().getString(R.string.error_generic), Toast.LENGTH_LONG)
                .show();
    }


    public void addPoiPickRepresentation(PointOfInterest poi)
    {
        //adds a simple representation of the added poi (no image, name only)
        LinearLayout ll = (LinearLayout) findViewById(R.id.trip_edit_points_anchor);
        View child = LayoutInflater.from(this).inflate(R.layout.cardview_poi_simple,null);
        ImageView closeIcon = (ImageView) child.findViewById(R.id.spoicard_remove);
        TextView poiNameView = (TextView) child.findViewById(R.id.spoicard_name);
        poiNameView.setText(poi.getName());
        closeIcon.setOnClickListener( v -> presenter.removePoi(poi,child));   //remove this poi from trip
        ll.addView(child);
    }


    public void removeSimplePoiChildView(View childToRemove)
    {
        LinearLayout ll = (LinearLayout) findViewById(R.id.trip_edit_points_anchor);
        ll.removeView(childToRemove);
        ll.invalidate();
    }


    public void displayCreationSuccessMessage()
    {
        Toast.makeText(this,getResources().getString(R.string.trip_edit_success_message), Toast.LENGTH_LONG)
                .show();
    }

    public void displayCreationErrorMessage(Throwable t)
    {
        Snackbar.make( finishButton,
                getResources().getString(R.string.trip_edit_error_message),
                Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    public void displayDeleteSuccessMessage()
    {
        Toast.makeText(this,getResources().getString(R.string.trip_delete_success_message), Toast.LENGTH_LONG)
                .show();
    }

    public void displayDeleteErrorMessage(Throwable t)
    {
        Snackbar.make( finishButton,
                getResources().getString(R.string.trip_delete_error_message),
                Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }


    public void finishActivity()
    {
        finish();
    }

}
