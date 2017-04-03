package pl.lodz.p.pathfinder.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import pl.lodz.p.pathfinder.R;
import pl.lodz.p.pathfinder.json.directions.Step;
import pl.lodz.p.pathfinder.json.directions.TransitDetails;
import pl.lodz.p.pathfinder.model.DetailDirections;
import pl.lodz.p.pathfinder.model.PointOfInterest;
import pl.lodz.p.pathfinder.model.SimpleDirections;

/**
 * Created by QDL on 2017-03-26.
 */

public class PoiCardRVAdapterDirectionDecorator extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements RVAdapterRemovable
{
    /*
        View types:
            POI - 0
            Directions - 1
     */

    private  PoiCardRVAdapter poiAdapter;

    private List<SimpleDirections> directionsOverviewList;
    private List<DetailDirections> directionsDetailList;


    //TODO remove
    {
//        for(PointOfInterest p : )
//
//        SimpleDirections asd = new SimpleDirections();
//        asd.setDistance("123 km");
//        asd.setDuration("1hr 23min");
//        directionsOverviewList = new ArrayList<>();
//        directionsOverviewList.add(asd);
//        directionsOverviewList.add(asd);
//        directionsOverviewList.add(asd);
//        directionsOverviewList.add(asd);
    }


    public PoiCardRVAdapterDirectionDecorator(List<PointOfInterest> dataset, RvItemClickListener<PointOfInterest> itemListener)
    {
        poiAdapter = new PoiCardRVAdapter(dataset,itemListener);

        //FIXME?
        directionsOverviewList = new ArrayList<>();
        directionsDetailList = new ArrayList<>();
        SimpleDirections asd = new SimpleDirections();
        asd.setDistance("- km");
        asd.setDuration("- min");
        for(int i=0; i<dataset.size();i++)
        {
            directionsOverviewList.add(asd);
            directionsDetailList.add(null);
        }
//        super(dataset);
    }


    class ViewHolderDirections extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public ImageView icon;
        TextView overviewTime;
        TextView overviewDistance;
        LinearLayout baseAnchor;
//        public TextView details;
//        private PoiCardRVAdapter.ViewHolder

        public ViewHolderDirections(View itemView)
        {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.cardview_directions_icon);
//            details = (TextView) itemView.findViewById(R.id.cardview_directions_content);
            overviewDistance = (TextView) itemView.findViewById(R.id.cardview_directions_overview_distance);
            overviewTime = (TextView) itemView.findViewById(R.id.cardview_directions_overview_time);
            baseAnchor = (LinearLayout) itemView.findViewById(R.id.cardview_attach);
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



    //TODO move to some sort of interface?
    void updateDirections(SimpleDirections dirs, int position)
    {
        directionsOverviewList.set(position,dirs);
        notifyDataSetChanged();
    }

    void updateDetailDirections(DetailDirections dirs, int position)
    {
        directionsDetailList.set(position,dirs);
        notifyDataSetChanged();
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
                poiAdapter.onBindViewHolder((PoiCardRVAdapter.ViewHolder)holder,position/2);
            break;
            case 1:
                ViewHolderDirections vhd = (ViewHolderDirections) holder;
                //TODO move to directions api callback
                if(directionsOverviewList != null )
                {
                    SimpleDirections sd = directionsOverviewList.get(position/2);
                    vhd.overviewTime.setText(sd.getDuration());
                    vhd.overviewDistance.setText(sd.getDistance());
                }

                if(directionsDetailList.get(position/2) != null)
                {
                    vhd.baseAnchor.removeAllViews();
                    DetailDirections dd = directionsDetailList.get(position/2);
                    for (Step s : dd.getDetails())
                    {
                        boolean isTransit = s.getTravelMode().equals("TRANSIT");
                        View child = isTransit ? createTransitView(s, vhd.baseAnchor.getContext()) : createWalkView(s,vhd.baseAnchor.getContext());
                        vhd.baseAnchor.addView(child);
                    }
                }

            break;
        }
    }

    @Override
    public int getItemCount()
    {
        return poiAdapter.getItemCount()*2-1 ;  //FIXME actually not broken (probably), come back to later
    }


    @Override
    public int getItemViewType(int position)
    {
//        return super.getItemViewType(position);
        return position%2;
    }



    @Override
    public void removeAt(int position)
    {
        poiAdapter.removeAt(position);
        notifyDataSetChanged();
    }



    private View createTransitView(Step step, Context context)
    {
        View child = LayoutInflater.from(context).inflate(R.layout.direction_detail_transit,null);

        ImageView travelMode = (ImageView) child.findViewById(R.id.direction_transit_travel_mode);
        TextView hourDepart  = (TextView) child.findViewById(R.id.direction_transit_hour_depart);
        TextView hourArrive  = (TextView) child.findViewById(R.id.direction_hour_dest);
        TextView nameDepart  = (TextView) child.findViewById(R.id.direction_transit_name_depart);
        TextView nameArrive  = (TextView) child.findViewById(R.id.direction_transit_name_dest);
        TextView transitInfo = (TextView) child.findViewById(R.id.direction_transit_info_line);
        TextView transitInfoDetails = (TextView) child.findViewById(R.id.direction_transit_info_details);

        TransitDetails details = step.getTransitDetails();

        //set icon for vehicle
        Picasso.with(context).load("http:" + details.getLine().getVehicle().getIcon()).resize(60,60).into(travelMode);
//        Picasso.with(context).load("http://maps.gstatic.com/mapfiles/transit/iw2/6/bus2.png").resize(60,60).into(travelMode);

        hourDepart.setText(details.getDepartureTime().getText());
        nameDepart.setText(details.getDepartureStop().getName());
        hourArrive.setText(details.getArrivalTime().getText());
        nameArrive.setText(details.getArrivalStop().getName());

        String line = details.getLine().getVehicle().getName() + " " +  details.getLine().getShortName() + " " + details.getHeadsign() ;
        transitInfo.setText(line);
        String deets = step.getDuration().getText() + "  " + details.getNumStops() + " stops";
        transitInfoDetails.setText(deets);

        return child;
    }


    private View createWalkView(Step step, Context context)
    {
        View child = LayoutInflater.from(context).inflate(R.layout.direction_detail_walk,null);
        TextView walkInfo = (TextView) child.findViewById(R.id.direction_walk_info);
        TextView walkDetail = (TextView) child.findViewById(R.id.direction_walk_detail);

        String details = step.getDistance().getText() + ", " + step.getDuration().getText();
        walkDetail.setText(details);
        walkInfo.setText(step.getHtmlInstructions());



        return child;
    }




}
