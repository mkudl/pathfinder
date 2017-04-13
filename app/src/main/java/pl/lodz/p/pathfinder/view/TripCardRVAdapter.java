package pl.lodz.p.pathfinder.view;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import pl.lodz.p.pathfinder.R;
import pl.lodz.p.pathfinder.model.Trip;

/**
 * Created by QDL on 2017-01-17.
 */

public class TripCardRVAdapter extends RecyclerView.Adapter<TripCardRVAdapter.ViewHolder> //implements View.OnClickListener
{

    private List<Trip> dataset;
    private List<Boolean> expandStateList;

    RecyclerView recyclerView;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public TextView name;
        public TextView description;
        public ImageView photo;
        List<Trip> tripList;
        public TextView details;
        ImageView expandIcon;

        boolean isExpanded;

        public ViewHolder(View v, List<Trip> tl){
            super(v);
            name = (TextView) v.findViewById(R.id.poicard_textview_title);
            description = (TextView)v.findViewById(R.id.poicard_textview_details);
            photo = (ImageView) v.findViewById(R.id.poicard_imageview);
            tripList = tl;
            details = (TextView) v.findViewById(R.id.textView3);
            expandIcon = (ImageView) v.findViewById(R.id.tripcard_expandIcon);

            v.setOnClickListener(this);


//            tv.setOnClickListener(new View.OnClickListener()
//            {
//                @Override
//                public void onClick(View view)
//                {
//                    Toast.makeText(view.getContext(),"textview pressed",Toast.LENGTH_SHORT).show();
//                }
//            });
        }


        //TODO utilize my generic interface
        @Override
        public void onClick(View view)
        {
//            int itemPosition = recyclerView.indexOfChild(v);
            int itemPosition = getLayoutPosition();



//            Trip trip = TripCardRVAdapter.this.dataset.get(itemPosition);
            Trip trip = tripList.get(itemPosition);


            Intent intent = new Intent(view.getContext(), TripViewingActivity.class);
            intent.putExtra("TRIP_PARAM",trip);
//            TemporarySingleton.INSTANCE.setTrip(trip);
            view.getContext().startActivity(intent);

//            Toast.makeText(view.getContext(),"position" + itemPosition, Toast.LENGTH_SHORT).show();

        }
    }

    public TripCardRVAdapter(List<Trip> dataset)
    {
        this.dataset = dataset;
        expandStateList = new ArrayList<Boolean>(Arrays.asList(new Boolean[dataset.size()]));
        Collections.fill(expandStateList,false);
    }

    @Override
    public TripCardRVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_trip, parent, false);
        //v.setOnClickListener(this);
//        v.setOnClickListener(new MyOnClickListener());


        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder( v, this.dataset);
        return vh;
    }

    @Override
    public void onBindViewHolder(TripCardRVAdapter.ViewHolder holder, int position)
    {
//        holder.tv.setOnClickListener(new MyOnClickListener());
        holder.name.setText(dataset.get(position).getName());
        holder.description.setText(dataset.get(position).getDescription());

        holder.details.setVisibility( expandStateList.get(position) ? View.VISIBLE : View.GONE);

        holder.expandIcon.setOnClickListener((View.OnClickListener) view ->
        {
            if(expandStateList.get(position))
            {
                holder.details.setVisibility(View.GONE);
                expandStateList.set(position,false);
                holder.expandIcon.setRotation(0);
            }
            else
            {
                holder.details.setVisibility(View.VISIBLE);
                expandStateList.set(position,true);
                holder.expandIcon.setRotation(180);
            }
        });

    }



    @Override
    public int getItemCount()
    {
        return dataset.size();
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView)
    {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

//    @Override
//    public void onClick(final View view)
//    {
//        TripCardRVAdapter.this.index
//        Intent intent = new Intent(recyclerView.getContext(), TripViewingActivity.class);
//        intent.putExtra("TRIP_PARAM",)
//        recyclerView.getContext().startActivity(intent);
//
//        //int asd = recyclerView.getChildLayoutPosition(view);
//        //String st = dataset.get(asd);
//        //Toast.makeText(view.getContext(),st,Toast.LENGTH_SHORT).show();
//    }
//
//    class MyOnClickListener implements View.OnClickListener {
//        @Override
//        public void onClick(View v){
//            int itemPosition = recyclerView.indexOfChild(v);
//
//            Log.e("Clicked and Position is",String.valueOf(itemPosition));
//
//
//            Trip trip = TripCardRVAdapter.this.dataset.get(itemPosition);
//
//            Intent intent = new Intent(recyclerView.getContext(), TripViewingActivity.class);
////            intent.putExtra("TRIP_PARAM",;
//            TemporarySingleton.INSTANCE.setTrip(trip);
//            recyclerView.getContext().startActivity(intent);
//        }
//    }



}