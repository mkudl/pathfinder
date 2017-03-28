package pl.lodz.p.pathfinder;

import android.os.Parcelable;
import android.view.View;

/**
 * Created by QDL on 2017-03-28.
 */


/**
 *
 * TODO
 * @param <T>
 */
//TODO delete Parcelable once DI in fragments is implemented
public interface RvItemClickListener<T>  extends Parcelable
{
    void onItemClicked(T item, View view);
}
