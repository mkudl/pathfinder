package pl.lodz.p.pathfinder.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import pl.lodz.p.pathfinder.R;
import pl.lodz.p.pathfinder.model.PointOfInterest;
import pl.lodz.p.pathfinder.model.Trip;

/**
 * Created by QDL on 2017-01-17.
 */

public class TripCardRVAdapter extends RecyclerView.Adapter<TripCardRVAdapter.ViewHolder> //implements View.OnClickListener
{

    private List<Trip> dataset;
    private List<Boolean> expandStateList;
    private List<Bitmap> photoBitmaps;

    RecyclerView recyclerView;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public TextView name;
        public TextView description;
        public ImageView photo;
        List<Trip> tripList;
        public LinearLayout details;
        ImageView expandIcon;

        boolean isExpanded;

        public ViewHolder(View v, List<Trip> tl){
            super(v);
            name = (TextView) v.findViewById(R.id.poicard_textview_title);
            description = (TextView)v.findViewById(R.id.poicard_textview_details);
            photo = (ImageView) v.findViewById(R.id.poicard_imageview);
            tripList = tl;
            details = (LinearLayout) v.findViewById(R.id.tripcard_details);
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

    public TripCardRVAdapter(List<Trip> dataset, Context context)
    {
        this.dataset = dataset;
        expandStateList = new ArrayList<Boolean>(Arrays.asList(new Boolean[dataset.size()]));
        Collections.fill(expandStateList,false);
        Bitmap placeholder = BitmapFactory.decodeResource(context.getResources(),R.drawable.placeholder);
        this.photoBitmaps = Collections.nCopies(dataset.size(),placeholder);
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


    //FIXME everything in here
    @Override
    public void onBindViewHolder(TripCardRVAdapter.ViewHolder holder, int position)
    {
//        holder.tv.setOnClickListener(new MyOnClickListener());
        holder.name.setText(dataset.get(position).getName());
        holder.description.setText(dataset.get(position).getDescription());
        holder.photo.setImageBitmap(photoBitmaps.get(position));
        holder.details.setVisibility( expandStateList.get(position) ? View.VISIBLE : View.GONE);

        holder.expandIcon.setOnClickListener( view ->
        {
            if(expandStateList.get(position))
            {
                holder.details.setVisibility(View.GONE);
                expandStateList.set(position,false);
                holder.expandIcon.setRotation(0);
            }
            else
            {
                holder.details.removeAllViews();
                for(PointOfInterest poi : dataset.get(position).getPointOfInterestList())
                {
                    TextView poiTextView = new TextView(holder.details.getContext());
                    poiTextView.setText(poi.getName());
                    poiTextView.setLayoutParams(new LinearLayoutCompat.LayoutParams(
                            LinearLayoutCompat.LayoutParams.MATCH_PARENT,
                            LinearLayoutCompat.LayoutParams.WRAP_CONTENT));
                    poiTextView.setGravity(Gravity.CENTER_HORIZONTAL);
                    holder.details.addView(poiTextView);
                }
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


    //TODO use some kind of interface for this
    public void updatePhotos(List<Bitmap> photos)
    {
        this.photoBitmaps = photos;
        notifyDataSetChanged();
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