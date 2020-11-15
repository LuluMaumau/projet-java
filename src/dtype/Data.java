package dtype;

import java.util.Date;

import java.io.Serializable;

public class Data implements Serializable {

    private static final long serialVersionUID = 1L;
    Date date;
    Position pos;
    String sat;
    String component;
    String dtype;

    /**
     * Makes a Data object from the name of a satellite and the component which took
     * the measure
     * 
     * @param sat       Name of the satellite
     * @param component Name of the component
     */
    public Data(String sat, String component, String dtype) {
        this.date = new Date();
        this.pos = new Position();
        this.sat = sat;
        this.component = component;
        this.dtype = dtype;
    }

    /**
     * Change the sat attribute
     * 
     * @param sat The new sat attribute to apply
     */
    public void changeSat(String sat) {
        this.sat = sat;
    }

    /**
     * Change the component attribute
     * 
     * @param component The new component attribute to apply
     */
    public void changeComponent(String component) {
        this.component = component;
    }

    /**
     * Get the date of the measure
     * 
     * @return The date attribute
     */
    public Date getDate() {
        return this.date;
    }

    /**
     * Get the position of the measure
     * 
     * @return The position attribute
     */
    public Position getPosition() {
        return this.pos;
    }

    /**
     * Get the satellite which did the measure
     * 
     * @return The sat attribute
     */
    public String getSat() {
        return this.sat;
    }

    /**
     * Get the component type which did the measure
     * 
     * @return The component attribute
     */
    public String getComponent() {
        return this.component;
    }

    /**
     * Get the data type
     * 
     * @return The dtype attribute
     */
    public String getDtype() {
        return this.dtype;
    }

    public String toString() {
        return " [ Date : " + this.date.toString() + " , Position : " + this.pos.toString() + " , Satellite : "
                + this.sat + " , Component : " + this.component + " , Data type : " + this.dtype;
    }

}
