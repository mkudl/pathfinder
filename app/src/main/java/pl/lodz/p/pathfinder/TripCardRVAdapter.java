package pl.lodz.p.pathfinder;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import pl.lodz.p.pathfinder.model.Trip;

/**
 * Created by QDL on 2017-01-17.
 */

public class TripCardRVAdapter extends RecyclerView.Adapter<TripCardRVAdapter.ViewHolder> //implements View.OnClickListener
{

    private List<Trip> dataset;
    RecyclerView recyclerView;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public TextView tv;
        List<Trip> tripList;
        public TextView details;
        ImageView expandIcon;

        public ViewHolder(View v, List<Trip> tl){
            super(v);
            tv = (TextView)v.findViewById(R.id.poicard_textview_details);
            tripList = tl;
            details = (TextView) v.findViewById(R.id.textView3);
            expandIcon = (ImageView) v.findViewById(R.id.tripcard_expandIcon);

            v.setOnClickListener(this);

            details.setVisibility(View.GONE);

            expandIcon.setOnClickListener(new View.OnClickListener()
            {
                boolean isExpanded = false;
                @Override
                public void onClick(View view)
                {

                    Toast.makeText(view.getContext(),"details pressed",Toast.LENGTH_SHORT).show();

                    if(isExpanded)
                    {
                        details.setVisibility(View.GONE);
                        isExpanded = false;
                    }
                    else
                    {
                        details.setVisibility(View.VISIBLE);
                        isExpanded = true;
                    }
                }
            });

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
        holder.tv.setText(dataset.get(position).getName());

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