package pl.lodz.p.pathfinder.model;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;


public class Trip implements Parcelable
{
    private int id;
    private String name;
    private String description;
    private List<PointOfInterest> pointOfInterestList;

    // part of the Parcelable interface
    //Classes implementing the Parcelable interface must also have a non-null static field called CREATOR (...)
    public static final Parcelable.Creator<Trip> CREATOR = new Creator<Trip>()
    {
        @Override
        public Trip createFromParcel(Parcel source)
        {
            Trip returnTrip = new Trip();
            List<PointOfInterest> poi = new ArrayList<>();
            returnTrip.setName(source.readString());
            returnTrip.setDescription(source.readString());
            source.readList(poi,PointOfInterest.class.getClassLoader());
            returnTrip.setPointOfInterestList(poi);
            returnTrip.setId(source.readInt());
            return returnTrip;
        }

        @Override
        public Trip[] newArray(int size)
        {
            return new Trip[size];
        }
    };

    public Trip(int id, String name, String description, List<PointOfInterest> pointOfInterestList)
    {
        this.id = id;
        this.name = name;
        this.description = description;
        this.pointOfInterestList = pointOfInterestList;
    }

    //TODO? include id
    @Deprecated
    public Trip(String name, String description, List<PointOfInterest> pointOfInterestList)
    {
        this.name = name;
        this.description = description;
        this.pointOfInterestList = pointOfInterestList;
    }




    private Trip(){}

    public List<PointOfInterest> getPointOfInterestList()
    {
        return pointOfInterestList;
    }

    public void setPointOfInterestList(List<PointOfInterest> pointOfInterestList)
    {
        this.pointOfInterestList = pointOfInterestList;
    }

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

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(getName());
        dest.writeString(getDescription());
        dest.writeList(getPointOfInterestList());
        dest.writeInt(id);
    }
}
