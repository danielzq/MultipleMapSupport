package multiplemaps.core;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Daniel on 2018/11/13.
 */
public class LatLng implements Parcelable {

    public double latitude;
    public double longitude;

    public LatLng(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.latitude);
        dest.writeDouble(this.longitude);
    }

    protected LatLng(Parcel in) {
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
    }

    public static final Parcelable.Creator<LatLng> CREATOR = new Parcelable.Creator<LatLng>() {
        @Override
        public LatLng createFromParcel(Parcel source) {
            return new LatLng(source);
        }

        @Override
        public LatLng[] newArray(int size) {
            return new LatLng[size];
        }
    };
}
