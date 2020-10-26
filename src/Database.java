import satellite.subsystems.*;
import satellite.*;
import data.*;

import java.util.HashMap;
import java.util.Date;

public class Database {

    HashMap<String, Satellite> satList;
    HashMap<String, Family> famList;
    HashMap<Date, Data> dataHashMap;

    /**
     * Initialization of the database, we will later implement an external Dataset
     * To stock the satellites' and the families I use an HashMap indexed by their
     * names
     * 
     * For the measured data I use a HashMap index by the date of the measure, seen
     * as a double for the time being and it will be stocked as such :
     * YYYYMMDD.HHMMSS
     */

    public Database() {
        this.satList = new HashMap<String, Satellite>();
        this.famList = new HashMap<String, Family>();
        this.dataHashMap = new HashMap<Date, Data>();
    }

    /**
     * Makes a Satellite and adds it to the HashMap listing the Satellites indexed
     * by their names
     * 
     * @param name Name of the new satellite
     * @param f    Family of the new Satellite
     */
    public void makeSatellite(String name, Family f) {
        Satellite sat = new Satellite(name.toUpperCase(), f);
        satList.put(sat.getFullname(), sat);
    }

    /**
     * Makes a family and adds it to the Hashmap listing the families indexed by
     * their names
     * 
     * @param name       Name of the new family (The extension of all satellites of
     *                   this family)
     * @param components HashMap of the components of the satellites of this family
     *                   (indexed by their names)
     */
    public void makeFamily(String name, HashMap<String, Component> components) {
        famList.put(name, new Family(name, components));
    }

    /**
     * Adds Data to the HashMap listing the data indexed by their time aquisition
     * 
     * @param data The data to archive
     */
    public void addData(Data data) {
        dataHashMap.put(data.getDate(), data);
    }

    /**
     * Check if a satellite exists by searching its name in the HashMap of the
     * satellites
     * 
     * @param fullname Full name of the satellite to search
     * @return Indicates if this is true or false
     */
    public boolean checkSatelliteExists(String fullname) {
        return satList.containsKey(fullname);
    }

    /**
     * Get the Satellite object representing the satellite with such a full name
     * 
     * @param fullname Full name of the satellite
     * @return The satellite object with this full name
     */
    public Satellite getSatellite(String fullname) {
        return satList.get(fullname);
    }

    /**
     * Check if a family exists by checking if its name is in the HashMap listing
     * the Family
     * 
     * @param family The family to check if it exists
     * @return Indicates is it is true or false
     */
    public boolean checkFamilyExists(String family) {
        return famList.containsKey(family);
    }

    /**
     * Get the Family object representing the family with such a name
     * 
     * @param name Name of the satellite
     * @return The family object with this name
     */
    public Family getFamily(String fam) {
        return famList.get(fam);
    }

    /**
     * Gets the satList attribute
     * 
     * @return The satList attribute
     */
    public HashMap<String, Satellite> getSatList() {
        return this.satList;
    }

    /**
     * Gets the famList attribute
     * 
     * @return The fam list attribute
     */
    public HashMap<String, Family> getFamList() {
        return this.famList;
    }

    /**
     * Gets the dataHashMap attribute
     * 
     * @return The dataHashMap attribute
     */
    public HashMap<Date, Data> getDataMap() {
        return this.dataHashMap;
    }

    public String toString() {
        return this.satList.toString() + "\n" + this.famList.toString() + "\n" + this.dataHashMap.toString() + "\n";
    }
}