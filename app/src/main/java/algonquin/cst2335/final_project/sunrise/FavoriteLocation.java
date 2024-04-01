package algonquin.cst2335.final_project.sunrise;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Entity class representing a favorite location
 *
 * @author Tony Chen
 * @version 1.0
 */
@Entity
public class FavoriteLocation {

    /**
     * Primary key for identifying the favorite location
     */
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public long id;

    /**
     * Latitude of the favorite location
     */
    @ColumnInfo(name = "latitude")
    public String latitude;

    /**
     * Longitude of the favorite location
     */
    @ColumnInfo(name = "longitude")
    public String longitude;

    /**
     * Timezone of the favorite location
     */
    @ColumnInfo(name = "timezone")
    public String timezone;

    /**
     * Sunrise time at the favorite location
     */
    @ColumnInfo(name = "sunrise")
    public String sunrise;

    /**
     * Sunset time at the favorite location
     */
    @ColumnInfo(name = "sunset")
    public String sunset;

    /**
     * Default constructor for creating an empty FavoriteLocation object
     */
    public FavoriteLocation() { }

    /**
     * Constructor for creating a FavoriteLocation object with specified attributes
     *
     * @param latitude is the latitude of the favorite location
     * @param longitude is the longitude of the favorite location
     * @param timezone is the timezone of the favorite location
     * @param sunrise is the sunrise time at the favorite location
     * @param sunset is the sunset time at the favorite location
     */
    public FavoriteLocation(String latitude, String longitude, String timezone, String sunrise, String sunset) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.timezone = timezone;
        this.sunrise = sunrise;
        this.sunset = sunset;
    }

    /**
     * Gets the latitude of the favorite location
     *
     * @return is the latitude of the favorite location
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * Sets the latitude of the favorite location
     *
     * @param latitude is a string representing the latitude value to be set
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    /**
     * Gets the longitude of the favorite location
     *
     * @return is the longitude of the favorite location
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * Sets the longitude of the favorite location
     *
     * @param longitude is a string representing the longitude value to be set
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    /**
     * Gets the timezone of the favorite location
     *
     * @return is the timezone of the favorite location
     */
    public String getTimezone() {
        return timezone;
    }

    /**
     * Gets the sunrise time at the favorite location
     *
     * @return is the sunrise time at the favorite location
     */
    public String getSunrise() {
        return sunrise;
    }

    /**
     * Gets the sunset time at the favorite location
     *
     * @return is the sunset time at the favorite location
     */
    public String getSunset() {
        return sunset;
    }
}