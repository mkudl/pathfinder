package pl.lodz.p.pathfinder.view;

import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import pl.lodz.p.pathfinder.R;
import pl.lodz.p.pathfinder.model.Trip;

/**
 * Created by QDL on 2017-04-26.
 */

public class TripCardRVAdapterCreated extends TripCardRVAdapter
{
    public TripCardRVAdapterCreated(List<Trip> dataset)
    {
        super(dataset);
    }

    @Override
    protected void setupPopupMenu(TextView popupMenuButton, Trip trip)
    {
        popupMenuButton.setVisibility(View.VISIBLE);
        popupMenuButton.setOnClickListener( v -> {
            PopupMenu popupMenu = new PopupMenu(popupMenuButton.getContext(),popupMenuButton);
            popupMenu.inflate(R.menu.trip_popup_menu);
            popupMenu.setOnMenuItemClickListener( item -> {
                if(item.getItemId() == R.id.action_edit){
                    Intent intent = new Intent(popupMenuButton.getContext(), TripEditActivity.class);
                    intent.putExtra("TRIP_PARAM",trip);
                    popupMenuButton.getContext().startActivity(intent);
                }
                return true;
            });
            popupMenu.show();
        });
    }
}
