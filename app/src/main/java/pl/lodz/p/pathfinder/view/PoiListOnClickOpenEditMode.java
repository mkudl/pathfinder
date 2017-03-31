package pl.lodz.p.pathfinder.view;

import android.content.Intent;
import android.os.Parcel;
import android.view.View;

import pl.lodz.p.pathfinder.model.PointOfInterest;

/**
 * Created by QDL on 2017-03-28.
 */

public class PoiListOnClickOpenEditMode implements RvItemClickListener<PointOfInterest>
{



    @Override
    public void onItemClicked(PointOfInterest item, View view)
    {
        Intent intent = new Intent(view.getContext(), PoiDetailBaseActivity.class); //TODO change to implementation
        intent.putExtra("POI_PARAM",item);
        view.getContext().startActivity(intent);
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
