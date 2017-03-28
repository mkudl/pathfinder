package pl.lodz.p.pathfinder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import pl.lodz.p.pathfinder.model.PointOfInterest;

public class PoiDetailBaseActivity extends AppCompatActivity
{
    PointOfInterest displayedPoi;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poi_detail_base);
        displayedPoi = getIntent().getParcelableExtra("POI_PARAM");
    }
}
