package pl.lodz.p.pathfinder.view;

import android.graphics.Bitmap;

/**
 * Created by QDL on 2017-04-03.
 */

public interface PhotoDownloadCallback
{
    public void photoDownloaded(Bitmap bitmap, int position);
}