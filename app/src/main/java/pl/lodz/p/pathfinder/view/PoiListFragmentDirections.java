package pl.lodz.p.pathfinder.view;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import pl.lodz.p.pathfinder.model.DetailDirections;
import pl.lodz.p.pathfinder.model.PointOfInterest;
import pl.lodz.p.pathfinder.model.SimpleDirections;
import pl.lodz.p.pathfinder.service.directions.DetailedDirectionsCallback;

/**
 * Created by QDL on 2017-03-29.
 */

public class PoiListFragmentDirections extends PoiListFragment
{
    private GoogleMapsMovable mapsMovable;

    private PoiCardRVAdapterDirectionDecorator RVAdapter;

    public static PoiListFragmentDirections newInstance(List<PointOfInterest> poiList)
    {
        PoiListFragmentDirections fragment = new PoiListFragmentDirections();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM1,new ArrayList<Parcelable>(poiList));
//        args.putString(ARG_PARAM2,listenerType);
        fragment.setArguments(args);
        return fragment;
    }



    //TODO? extract interface
    void updateDirections(SimpleDirections dirs, int position)
    {
        RVAdapter.updateDirections(dirs,position);
    }

    void updateDetailedDirections(DetailDirections dirs, int position)
    {
        RVAdapter.updateDetailDirections(dirs,position);
    }



    //Creates the decorator to allow for the display of directions in addition to POIs
    @Override
    RecyclerView.Adapter createRVAdapter(List<PointOfInterest> dataset)
    {
        RVAdapter = new PoiCardRVAdapterDirectionDecorator(dataset,this.createItemListener());
        return RVAdapter;
    }

    @Override
    RvItemClickListener<PointOfInterest> createItemListener()
    {
        return new PoiListOnClickUpdateMap(mapsMovable);
    }


    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);

        if (context instanceof GoogleMapsMovable)
        {
            mapsMovable = (GoogleMapsMovable) context;
        } else
        {
            throw new RuntimeException(context.toString()
                    + " must implement GoogleMapsMovable");
        }
    }
}
