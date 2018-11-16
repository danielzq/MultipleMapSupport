package multiplemaps.core;

import java.util.ArrayList;

/**
 * Created by Daniel on 2018/11/14.
 */
public class LatLngBounds {

    private ArrayList<LatLng> points;

    public LatLng southwest;
    public LatLng northeast;

    public LatLngBounds(LatLng southwest, LatLng northeast) {
        this.southwest = southwest;
        this.northeast = northeast;
    }

    public LatLngBounds(ArrayList<LatLng> points) {
        this.points = points;
    }

    public ArrayList<LatLng> getPoints() {
        return points;
    }

    public final LatLng getCenter() {
        double var1 = (this.southwest.latitude + this.northeast.latitude) / 2.0D;
        double var3 = this.northeast.longitude;
        double var5 = this.southwest.longitude;
        double var7;
        if (this.southwest.longitude <= var3) {
            var7 = (var3 + var5) / 2.0D;
        } else {
            var7 = (var3 + 360.0D + var5) / 2.0D;
        }

        return new LatLng(var1, var7);
    }

    private static double zza(double var0, double var2) {
        return (var0 - var2 + 360.0D) % 360.0D;
    }

    private static double zzb(double var0, double var2) {
        return (var2 - var0 + 360.0D) % 360.0D;
    }

    public static final class Builder {

        private double zzdg = 1.0D / 0.0;
        private double zzdh = -1.0D / 0.0;
        private double zzdi = 0.0D / 0.0;
        private double zzdj = 0.0D / 0.0;

        private ArrayList<LatLng> points = new ArrayList<>();

        public Builder() {
        }

        public final LatLngBounds.Builder include(LatLng var1) {
            points.add(var1);

            this.zzdg = Math.min(this.zzdg, var1.latitude);
            this.zzdh = Math.max(this.zzdh, var1.latitude);
            double var2 = var1.longitude;
            if (Double.isNaN(this.zzdi)) {
                this.zzdi = var2;
            } else {
                if (this.zzdi <= this.zzdj ? this.zzdi <= var2 && var2 <= this.zzdj : this.zzdi <= var2 || var2 <= this.zzdj) {
                    return this;
                }

                if (LatLngBounds.zza(this.zzdi, var2) < LatLngBounds.zzb(this.zzdj, var2)) {
                    this.zzdi = var2;
                    return this;
                }
            }

            this.zzdj = var2;
            return this;
        }

        public final LatLngBounds build() {
            LatLngBounds latLngBounds = new LatLngBounds(points);
            latLngBounds.southwest = new LatLng(this.zzdg, this.zzdi);
            latLngBounds.northeast = new LatLng(this.zzdh, this.zzdj);
            return latLngBounds;
        }
    }
}
