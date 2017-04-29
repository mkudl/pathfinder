package pl.lodz.p.pathfinder.view;

import android.os.Parcelable;
import android.view.View;

/**
 * Created by QDL on 2017-03-28.
 */


public interface RvItemClickListener<T>  extends Parcelable
{
    void onItemClicked(T item, View view);
}
