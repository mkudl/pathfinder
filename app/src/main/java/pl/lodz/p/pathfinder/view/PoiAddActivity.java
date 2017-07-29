package pl.lodz.p.pathfinder.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;

import pl.lodz.p.pathfinder.Configuration;
import pl.lodz.p.pathfinder.R;
import pl.lodz.p.pathfinder.presenter.PoiAddPresenter;
import pl.lodz.p.pathfinder.rest.DatabasePoiRest;
import pl.lodz.p.pathfinder.service.PoiRepository;
import pl.lodz.p.pathfinder.service.PointOfInterestClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class PoiAddActivity extends AppCompatActivity
{
    final int PLACE_PICKER_REQUEST = 9420;

    private PoiAddPresenter presenter;

    private TextView coordinates;
    private TextView address;
    private Button finishButton;
    private EditText name;

    GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poi_add);

        setupUI();
        setupPresenter();
    }

    private void setupUI()
    {
        Button getLocationButton = (Button) findViewById(R.id.poi_add_button_pickagain);
        coordinates = (TextView) findViewById(R.id.poi_add_location_coords);
        address = (TextView) findViewById(R.id.poi_add_location_address);
        finishButton = (Button)  findViewById(R.id.poi_add_finish_button);
        name = (EditText) findViewById(R.id.poi_add_name_edittext);

        getLocationButton.setOnClickListener( v -> {
            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
            try
            {
                startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
            } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e)
            {
                e.printStackTrace();
            }
        });

        finishButton.setOnClickListener( v -> presenter.createPoi(name.getText().toString()));
        hideFinishButton();
    }

    private void setupPresenter()
    {
        Retrofit rxRetrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Configuration.SERVER_ADDRESS)
                .build();
        DatabasePoiRest restClient = rxRetrofit.create(DatabasePoiRest.class);
        PointOfInterestClient poiClient = new PointOfInterestClient(this);
        PoiRepository pr = new PoiRepository(restClient,poiClient);

        googleApiClient = new GoogleApiClient.Builder(this)
               .addApi(Places.GEO_DATA_API)
               .build();
        googleApiClient.connect();

        this.presenter = new PoiAddPresenter(this,pr,googleApiClient);
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == Activity.RESULT_OK)
            {
                Place place = PlacePicker.getPlace(this,data);
                presenter.updateLocation(place.getLatLng(),place.getAddress().toString());
            }
        }
    }

    public void updateLocationDisplay(LatLng coordinates, CharSequence address)
    {
        this.coordinates.setText(coordinates.toString());
        this.address.setText(address);
    }



    public void hideFinishButton()
    {
        finishButton.setVisibility(View.GONE);
    }

    public void showFinishButton()
    {
        finishButton.setVisibility(View.VISIBLE);
    }

    public void displayCreationSuccessMessage()
    {
        Toast.makeText(this,getResources().getString(R.string.poi_add_success_message), Toast.LENGTH_LONG)
                .show();
    }

    public void displayCreationErrorMessage(Throwable t)
    {
        Snackbar.make( finishButton,
                getResources().getString(R.string.poi_add_error_message),
                Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    public void finishActivity()
    {
        googleApiClient.disconnect();
        finish();
    }


}