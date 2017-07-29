package pl.lodz.p.pathfinder.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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

    private RecyclerView recyclerView;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public TextView name;
        public TextView description;
        public ImageView photo;
        List<Trip> tripList;
        public LinearLayout details;
        ImageView expandIcon;

        TextView popupMenuButton;

        boolean isExpanded;

        public ViewHolder(View v, List<Trip> tl){
            super(v);
            name = (TextView) v.findViewById(R.id.tripcard_textview_title);
            description = (TextView)v.findViewById(R.id.tripcard_textview_details);
            photo = (ImageView) v.findViewById(R.id.tripcard_imageview);
            tripList = tl;
            details = (LinearLayout) v.findViewById(R.id.tripcard_details);
            expandIcon = (ImageView) v.findViewById(R.id.tripcard_expandIcon);

            //not actually a button, but it's more convenient like this
            popupMenuButton = (TextView) v.findViewById(R.id.tripcard_menu_actions);
            v.setOnClickListener(this);
        }


        @Override
        public void onClick(View view)
        {
            int itemPosition = getLayoutPosition();
            Trip trip = tripList.get(itemPosition);

            Intent intent = new Intent(view.getContext(), TripViewingActivity.class);
            intent.putExtra("TRIP_PARAM",trip);
            view.getContext().startActivity(intent);
        }
    }

    public TripCardRVAdapter(List<Trip> dataset)
    {
        this.dataset = dataset;
        expandStateList = new ArrayList<Boolean>(Arrays.asList(new Boolean[dataset.size()]));
        Collections.fill(expandStateList,false);
        this.photoBitmaps = Collections.nCopies(dataset.size(),null );

    }

    @Override
    public TripCardRVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {   // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_trip, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder( v, this.dataset);
        return vh;
    }


    @Override
    public void onBindViewHolder(TripCardRVAdapter.ViewHolder holder, int position)
    {
        holder.name.setText(dataset.get(position).getName());
        holder.description.setText(dataset.get(position).getDescription());
        if(photoBitmaps.get(position) != null){
            holder.photo.setImageBitmap(photoBitmaps.get(position));
        }
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

        holder.popupMenuButton.setVisibility(View.GONE);
        setupPopupMenu(holder.popupMenuButton,dataset.get(position));
    }

    protected void setupPopupMenu(TextView popupMenuButton, Trip trip)
    {
        //Intentionally left blank (most use cases don't need this menu)
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


    void updatePhotos(List<Bitmap> photos)
    {
        this.photoBitmaps = photos;
        notifyDataSetChanged();
    }

}