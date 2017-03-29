package pl.lodz.p.pathfinder;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pl.lodz.p.pathfinder.model.PointOfInterest;
import pl.lodz.p.pathfinder.model.SimpleDirections;

/**
 * Created by QDL on 2017-03-26.
 */

public class PoiCardRVAdapterDirectionDecorator extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    /*
        View types:
            POI - 0
            Directions - 1
     */

    private  PoiCardRVAdapter poiAdapter;

    private List<SimpleDirections> directionsOverviewList;


    //TODO remove
    {
        SimpleDirections asd = new SimpleDirections();
        asd.setDistance("123 km");
        asd.setDuration("1hr 23min");
        directionsOverviewList = new ArrayList<>();
        directionsOverviewList.add(asd);
        directionsOverviewList.add(asd);
        directionsOverviewList.add(asd);
        directionsOverviewList.add(asd);
    }


    public PoiCardRVAdapterDirectionDecorator(List<PointOfInterest> dataset, RvItemClickListener<PointOfInterest> itemListener)
    {
        poiAdapter = new PoiCardRVAdapter(dataset,itemListener);
//        super(dataset);
    }


    class ViewHolderDirections extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public ImageView icon;
        TextView overviewTime;
        TextView overviewDistance;
//        public TextView details;
//        private PoiCardRVAdapter.ViewHolder

        public ViewHolderDirections(View itemView)
        {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.cardview_directions_icon);
//            details = (TextView) itemView.findViewById(R.id.cardview_directions_content);
            overviewDistance = (TextView) itemView.findViewById(R.id.cardview_directions_overview_distance);
            overviewTime = (TextView) itemView.findViewById(R.id.cardview_directions_overview_time);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            int itemPosition = getLayoutPosition();
            //TODO? perhaps remove check entirely
            if(getItemViewType() == 1)  //FIXME change to enum
            {
                Toast.makeText(v.getContext(),String.valueOf(itemPosition) + " direction",Toast.LENGTH_SHORT).show();
                //TODO expand details
            }

        }
    }





    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        //returns appropriate viewholder type
        return (viewType%2==0) ? poiAdapter.onCreateViewHolder(parent,viewType) : onCreateViewHolderDirections(parent,viewType);
    }

    public RecyclerView.ViewHolder onCreateViewHolderDirections(ViewGroup parent, int viewType)
    {
          View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.cardview_directions,parent,false);
          return new ViewHolderDirections(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        switch (holder.getItemViewType()){
            case 0:
                poiAdapter.onBindViewHolder((PoiCardRVAdapter.ViewHolder)holder,position%2);
            break;
            case 1:
                ViewHolderDirections vhd = (ViewHolderDirections) holder;
                //TODO move to directions api callback
                if(directionsOverviewList != null )
                {
                    SimpleDirections sd = directionsOverviewList.get(position%2);
                    vhd.overviewTime.setText(sd.getDuration());
                    vhd.overviewDistance.setText(sd.getDistance());
                }

            break;
        }
    }

    @Override
    public int getItemCount()
    {
        return poiAdapter.getItemCount()*2 ;  //FIXME actually not broken (probably), come back to later
    }


    @Override
    public int getItemViewType(int position)
    {
//        return super.getItemViewType(position);
        return position%2;
    }
}
