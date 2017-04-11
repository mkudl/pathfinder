package pl.lodz.p.pathfinder.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcel;
import android.view.View;

import pl.lodz.p.pathfinder.model.PointOfInterest;

/**
 * Created by QDL on 2017-04-11.
 */

public class PoiListOnClickReturn implements RvItemClickListener<PointOfInterest>
{

    private Activity activity;


    public PoiListOnClickReturn(Activity activity)
    {
        this.activity = activity;
    }

    @Override
    public void onItemClicked(PointOfInterest item, View view)
    {

        Intent returnIntent = new Intent();
        returnIntent.putExtra("selectedPoi",item);
        getActivity().setResult(Activity.RESULT_OK,returnIntent);
        getActivity().finish();
    }




    public Activity getActivity()
    {
        return activity;
    }

    public void setActivity(Activity activity)
    {
        this.activity = activity;
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {

    }
}
