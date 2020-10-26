package data;

public class Position {

    double altitude;
    double latitude;
    double longitude;

    public Position(double altitude, double latitude, double longitude) {
        this.altitude = altitude;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Position(String sat, String component) {
        this(Math.random() * 200000, Math.random() * 360 - 180, Math.random() * 360 - 180);
    }

    public Position() {
        this(Math.random(), Math.random(), Math.random());
    }

    public double getAltitude() {
        return this.altitude;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public String toString() {
        return this.longitude + "E " + this.latitude + "N , Altitude : " + this.altitude / 1000 + "km";
    }
}
