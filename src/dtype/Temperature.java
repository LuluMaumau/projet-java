package dtype;

public class Temperature extends Data {

    private static final long serialVersionUID = 1L;
    double temp;

    /**
     * Make a Temperature object from the name of the satellite and the component
     * which took the measure
     * 
     * @param sat       Name of the satellite
     * @param component Name of the component
     */
    public Temperature(String sat, String component) {
        super(sat, component, "TEMPERATURE");
    }

    /** make a GrayImage with names of satellite and component empty */
    public Temperature() {
        this("", "");
    }

    /**
     * Get the temp of the Temperature
     * 
     * @return The temp attribute
     */
    public double getSize() {
        return this.temp;
    }

    public String toString() {
        return super.toString() + " , Temperature : " + temp + " ] ";
    }

}
