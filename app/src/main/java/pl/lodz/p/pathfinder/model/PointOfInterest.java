package pl.lodz.p.pathfinder.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;




public class PointOfInterest implements Parcelable
{
    private String name;
    private String description;
    private LatLng position;


    //Classes implementing the Parcelable interface must also have a non-null static field called CREATOR (...)
    public static final Parcelable.Creator<PointOfInterest> CREATOR = new Creator<PointOfInterest>()
    {
        @Override
        public PointOfInterest createFromParcel(Parcel source)
        {
            PointOfInterest poi = new PointOfInterest();
            poi.setName(source.readString());
            poi.setDescription(source.readString());
            poi.setPosition( source.<LatLng>readParcelable(LatLng.class.getClassLoader()) );
            return poi;
        }

        @Override
        public PointOfInterest[] newArray(int size)
        {
            return new PointOfInterest[size];
        }
    };



    public PointOfInterest(String name, String description, LatLng position)
    {
        this.name = name;
        this.description = description;
        this.position = position;
    }

    private PointOfInterest(){}

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public LatLng getPosition()
    {
        return position;
    }

    public void setPosition(LatLng position)
    {
        this.position = position;
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(name);
        dest.writeString(description);
        dest.writeParcelable(position,0);
    }
}
