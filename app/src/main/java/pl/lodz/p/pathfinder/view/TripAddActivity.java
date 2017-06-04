package pl.lodz.p.pathfinder.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
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
import pl.lodz.p.pathfinder.presenter.TripAddPresenter;
import pl.lodz.p.pathfinder.rest.DatabaseTripRest;
import pl.lodz.p.pathfinder.service.TripUploadService;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class TripAddActivity extends AppCompatActivity
{
    TripAddPresenter presenter;
    Button addPoiButton;
    Button finishButton;
    private final static int CALL_POI_PICK_CODE = 42099;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_add);
        createPresenter();
        bindButtons();
        setAddButtonBehavior();
        setFinishButtonBehavior();
    }

    private void setFinishButtonBehavior()
    {
        finishButton.setOnClickListener( v ->
                presenter.saveTrip(
                    ((EditText) findViewById(R.id.trip_add_name_content)).getText().toString(),
                    ((EditText) findViewById(R.id.trip_add_desc_content)).getText().toString()
                ));
    }

    private void setAddButtonBehavior()
    {
        addPoiButton.setOnClickListener(v -> {
            Intent intent = new Intent(this,PoiMenuActivity.class);
            startActivityForResult(intent,CALL_POI_PICK_CODE);
        });
    }

    private void bindButtons()
    {
        addPoiButton = (Button) findViewById(R.id.trip_add_button_poi_add);
        finishButton = (Button) findViewById(R.id.trip_add_button_finish);
    }

    private void createPresenter()
    {
        Retrofit rxRetrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Configuration.SERVER_ADDRESS)
                .build();
        DatabaseTripRest restClient = rxRetrofit.create(DatabaseTripRest.class);
        TripUploadService tup = new TripUploadService(restClient);
        presenter = new TripAddPresenter(this,tup);
    }



    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == CALL_POI_PICK_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                PointOfInterest selectedPoi =  data.getParcelableExtra("selectedPoi");
                presenter.addPoi(selectedPoi);
            }
            else {
                //TODO error handling
            }
        }
    }





    public void poiPickCallback(PointOfInterest poi)
    {
        //adds a simple representation of the added poi (no image, name only)
        LinearLayout ll = (LinearLayout) findViewById(R.id.trip_add_points_anchor);
        View child = LayoutInflater.from(this).inflate(R.layout.cardview_poi_simple,null);
        ImageView closeIcon = (ImageView) child.findViewById(R.id.spoicard_remove);
        TextView poiNameView = (TextView) child.findViewById(R.id.spoicard_name);
        poiNameView.setText(poi.getName());
        closeIcon.setOnClickListener( v -> presenter.removePoi(poi,child));   //remove this poi from trip
        ll.addView(child);
    }

    /**
     * remove specified child view (simple poi cardview attached to the anchor view)
     * @param childToRemove a child view to be removed from the anchor
     */
    public void removeSimplePoiChildView(View childToRemove)
    {
        LinearLayout ll = (LinearLayout) findViewById(R.id.trip_add_points_anchor);
        ll.removeView(childToRemove);
        ll.invalidate();
    }


    public void displayCreationSuccessMessage()
    {
        Toast.makeText(this,getResources().getString(R.string.trip_add_success_message), Toast.LENGTH_LONG)
                .show();
    }

    public void displayCreationErrorMessage(Throwable t)
    {
        Snackbar.make( finishButton,
                       getResources().getString(R.string.trip_add_error_message),
                       Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    public void finishActivity()
    {
        finish();
    }


}
