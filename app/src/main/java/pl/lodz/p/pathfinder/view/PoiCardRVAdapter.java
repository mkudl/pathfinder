package pl.lodz.p.pathfinder.view;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import pl.lodz.p.pathfinder.R;
import pl.lodz.p.pathfinder.model.PointOfInterest;

/**
 * Created by QDL on 2017-03-21.
 */

public class PoiCardRVAdapter extends RecyclerView.Adapter<PoiCardRVAdapter.ViewHolder> implements RVAdapterRemovable, RVAdapterPhotoUpdateable
{



    private List<PointOfInterest> poiList;
    private List<Bitmap> poiPhotoList;
    private RvItemClickListener<PointOfInterest> itemClick;
    private String listenerType;



    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView title;
        public ImageView image;

        public View itemView;

        public ViewHolder(View itemView)
        {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.tripcard_textview_title);
            image = (ImageView) itemView.findViewById(R.id.tripcard_imageview);
            this.itemView = itemView;
        }
    }


    public PoiCardRVAdapter(List<PointOfInterest> dataset, RvItemClickListener<PointOfInterest> itemListener, List<Bitmap> poiPhotoList)
    {
        this.poiList = dataset;
        this.itemClick = itemListener;
        this.poiPhotoList = poiPhotoList;

    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext())
                 .inflate(R.layout.cardview_poi,parent,false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position)
    {
        holder.title.setText(poiList.get(position).getName());
        holder.image.setImageBitmap(poiPhotoList.get(position));

        holder.itemView.setOnClickListener(v ->
        {
            if(itemClick != null)    itemClick.onItemClicked(poiList.get(position), v);
        });
    }

    @Override
    public int getItemCount()
    {
        return poiList.size();
    }


    @Override
    public void removeAt(int position)
    {
        poiList.remove(position);
        poiPhotoList.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public void updatePhoto(Bitmap bitmap, int position)
    {
        poiPhotoList.set(position,bitmap);
        notifyDataSetChanged();
    }


    public List<PointOfInterest> getPoiList()
    {
        return poiList;
    }

}
