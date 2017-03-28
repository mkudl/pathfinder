package pl.lodz.p.pathfinder;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import pl.lodz.p.pathfinder.model.PointOfInterest;

/**
 * Created by QDL on 2017-03-21.
 */

public class PoiCardRVAdapter extends RecyclerView.Adapter<PoiCardRVAdapter.ViewHolder>
{



    private List<PointOfInterest> poiList;
    private RvItemClickListener<PointOfInterest> itemClick;

    private String listenerType;


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public TextView title;
        public TextView details;

        public ViewHolder(View itemView)
        {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.poicard_textview_title);
            details = (TextView) itemView.findViewById(R.id.poicard_textview_details);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v)
        {
            int itemPosition = getLayoutPosition();
            Toast.makeText(v.getContext(),String.valueOf(itemPosition),Toast.LENGTH_SHORT).show();
            if(itemClick != null)    itemClick.onItemClicked(poiList.get(itemPosition), v);
        }
    }


    public PoiCardRVAdapter(List<PointOfInterest> dataset, RvItemClickListener<PointOfInterest> itemListener)
    {
        this.poiList = dataset;
        this.itemClick = itemListener;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext())
                 .inflate(R.layout.cardview_poi,parent,false);
        ViewHolder  vh = new ViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        holder.title.setText(poiList.get(position).getName());
    }

    @Override
    public int getItemCount()
    {
        return poiList.size();
    }





    public List<PointOfInterest> getPoiList()
    {
        return poiList;
    }

}
