
package pl.lodz.p.pathfinder.json.directions;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Line {

    @SerializedName("agencies")
    @Expose
    private List<Agency> agencies = null;
    @SerializedName("color")
    @Expose
    private String color;
    @SerializedName("short_name")
    @Expose
    private String shortName;
    @SerializedName("text_color")
    @Expose
    private String textColor;
    @SerializedName("vehicle")
    @Expose
    private Vehicle vehicle;

    public List<Agency> getAgencies() {
        return agencies;
    }

    public void setAgencies(List<Agency> agencies) {
        this.agencies = agencies;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

}
