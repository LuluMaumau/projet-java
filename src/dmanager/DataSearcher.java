package dmanager;

import dtype.Data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

public class DataSearcher {

    private FlashData FD;

    private Date start;
    private Date end;
    private ArrayList<String> satellite;
    private ArrayList<String> dtype;

    private ArrayList<Data> resultStart;
    private ArrayList<Data> resultEnd;
    private ArrayList<Data> resultSat;
    private ArrayList<Data> resultDtype;
    private ArrayList<Data> result;

    /**
     * Constructor of the DataSearcher with a FlashData, parameters of search and
     * the searchList initialized (but not yet with the searched data)
     * 
     * @param FD        FlashData to use
     * @param start     Parameter of search : starting date
     * @param end       Parameter of search : ending date
     * @param satellite Parameter of search : array of the satellites to search
     * @param dtype     Parameter of search : array of the dtypes to search
     */
    public DataSearcher(FlashData FD, Date start, Date end, ArrayList<String> satellite, ArrayList<String> dtype) {
        this.FD = FD;
        this.start = start;
        this.end = end;
        this.satellite = satellite;
        this.dtype = dtype;

        this.resultStart = new ArrayList<>();
        this.resultEnd = new ArrayList<>();
        this.resultSat = new ArrayList<>();
        this.resultDtype = new ArrayList<>();
        this.result = new ArrayList<>();
    }

    /**
     * Constructor of the DataSearcher without the parameters of search being
     * initialized to a certain value
     * 
     * @param load Boolean choosing if you want to automatically load Data stored in
     *             memory onto the used FlashData
     */
    public DataSearcher(boolean load) {
        this(new FlashData(load), null, null, new ArrayList<String>(), new ArrayList<String>());
    }

    /**
     * Constructor of the DataSearcher without the parameters of search being
     * initialized to a certain value, FlashData is automatically loaded
     */
    public DataSearcher() {
        this(new FlashData(true), null, null, new ArrayList<String>(), new ArrayList<String>());
    }

    /**
     * Loads the stored data in the FlashData being used
     */
    public void load() {
        FD.loadAll();
    }

    /**
     * Search the data according to the start attribute and stores the result in the
     * resultStart attribute
     */
    public void searchStart() {
        resultStart.clear();
        if (start != null) {
            Collection<ArrayList<Object>> c = FD.getDateIndex().tailMap(start, true).values();
            for (ArrayList<Object> arrayList : c) {
                for (Object data : arrayList) {
                    resultStart.add((Data) data);
                }
            }
        }
    }

    /**
     * Search the data according to the end attribute and stores the result in the
     * resultEnd attribute
     */
    public void searchEnd() {
        resultEnd.clear();
        if (end != null) {
            Collection<ArrayList<Object>> c = FD.getDateIndex().headMap(end, true).values();
            for (ArrayList<Object> arrayList : c) {
                for (Object data : arrayList) {
                    resultEnd.add((Data) data);
                }
            }
        }
    }

    /**
     * Search the data according to the satellite attribute and stores the result in
     * the resultSat attribute
     */
    public void searchSat() {
        resultSat.clear();
        if (!satellite.isEmpty()) {
            for (String sat : satellite) {
                for (Object data : FD.getSatelliteIndex().get(sat)) {
                    resultSat.add((Data) data);
                }
            }
        }
    }

    /**
     * Search the data according to the dtype attribute and stores the result in the
     * resultDtype attribute
     */
    public void searchDtype() {
        resultDtype.clear();
        if (!dtype.isEmpty()) {
            for (String dt : dtype) {
                for (Object data : FD.getDtypeIndex().get(dt)) {
                    resultDtype.add((Data) data);
                }
            }
        }
    }

    /**
     * Search the data, intersects the results according to all the active
     * attributes (fot date if not null, for the other if the array is not empty)
     * and stores them in the result attribute
     */
    public void search() {
        searchStart();
        searchEnd();
        searchSat();
        searchDtype();
        ArrayList<Data> dateSearch = intersectData(resultStart, start != null, resultEnd, end != null);
        ArrayList<Data> satDtypeSearch = intersectData(resultSat, !satellite.isEmpty(), resultDtype, !dtype.isEmpty());
        result = intersectData(dateSearch, (start != null || end != null), satDtypeSearch,
                (!satellite.isEmpty() || !dtype.isEmpty()));
    }

    /**
     * A method to intersect the result arrays of differents parameters of search
     * 
     * @param list1     The first list to intersect
     * @param searchIn1 If false, return list2, otherwise search in the list1 for
     *                  intersected elements
     * @param list2     The second list to intersect
     * @param searchIn2 If false, return list1, otherwise search in the list2 for
     *                  intersected elements
     * @return A list which is the intersection of the 2 lists in entry
     */
    private static ArrayList<Data> intersectData(ArrayList<Data> list1, boolean searchIn1, ArrayList<Data> list2,
            boolean searchIn2) {
        if (!searchIn1) {
            return list2;
        } else {
            if (!searchIn2) {
                return list1;
            } else {
                Set<Data> set = new HashSet<>();
                if (list1.size() < list2.size()) {
                    for (Data data : list1) {
                        if (list2.contains(data)) {
                            set.add(data);
                        }
                    }
                } else {
                    for (Data data : list2) {
                        if (list1.contains(data)) {
                            set.add(data);
                        }
                    }
                }
                return new ArrayList<>(set);
            }
        }

    }

    /**
     * Change the date value of start to a new one according to the parameters
     * 
     * @param year  Year
     * @param month Month
     * @param day   Day
     * @param hrs   Hours
     * @param min   Minutes
     * @param sec   Seconds
     */
    public void addStart(int year, int month, int day, int hrs, int min, int sec) {
        GregorianCalendar GC = new GregorianCalendar(year, month, day, hrs, min, sec);
        Date d = GC.getTime();
        if (end != null && d.after(end)) {
            System.out.println("Careful, starting time of search is set after the ending time of search");
        }
        if (d.after(new Date())) {
            System.out.println("Specified starting point not valid, start is unchanged");
        } else {
            start = d;
        }
    }

    /**
     * Put the start attribute to null so it won't be discriminated for search
     */
    public void removeStart() {
        start = null;
    }

    /**
     * Change the date value of end to a new one according to the parameters, if end
     * is after the current instant it is set to null for faster search
     * 
     * @param year  Year
     * @param month Month
     * @param day   Day
     * @param hrs   Hours
     * @param min   Minutes
     * @param sec   Seconds
     */
    public void addEnd(int year, int month, int day, int hrs, int min, int sec) {
        GregorianCalendar GC = new GregorianCalendar(year, month, day, hrs, min, sec);
        Date d = GC.getTime();
        if (start != null && d.before(start)) {
            System.out.println("Careful, ending time of search is set before the starting time of search");
        }
        if (d.after(new Date())) {
            end = null;
        } else {
            end = d;
        }
    }

    /**
     * Put the end attribute to null so it won't be discriminated for search
     */
    public void removeEnd() {
        end = null;
    }

    /**
     * Adds a satellite as a parameter of search
     * 
     * @param sat
     */
    public void addSatellite(String sat) {
        if (FD.getSatelliteIndex().containsKey(sat)) {
            satellite.add(sat);
        } else {
            System.out.println("The loaded data does not contain such satellite, try load.");
        }
    }

    /**
     * Removes a satellite as a parameter of search
     * 
     * @param sat
     */
    public void removeSatellite(String sat) {
        if (satellite.contains(sat)) {
            satellite.remove(sat);
        } else {
            System.out.println("This satellite is not a parameter of the search.");
        }
    }

    /**
     * Make the satellite array list of search empty
     */
    public void clearSatellite() {
        satellite.clear();
    }

    /**
     * Adds a dtype as a parameter of search
     * 
     * @param dt
     */
    public void addDtype(String dt) {
        if (FD.getDtypeIndex().containsKey(dt)) {
            dtype.add(dt);
        } else {
            System.out.println("The loaded data does not contain such data type, if you are sure it exists try load");
        }
    }

    /**
     * Removes a dtype as a parameter of search
     * 
     * @param dt
     */
    public void removeDtype(String dt) {
        if (dtype.contains(dt)) {
            dtype.remove(dt);
        } else {
            System.out.println("This data type is not a parameter of the search.");
        }
    }

    /**
     * Make the dtype array list of search empty
     */
    public void clearDtype() {
        dtype.clear();
    }

    /**
     * Displays the start parameter of search
     */
    public void displayStart() {
        if (start != null) {
            System.out.println("The selected starting date for search is " + start);
        } else {
            System.out.println("No starting date selected");
        }
    }

    /**
     * Displays the end parameter of search
     */
    public void displayEnd() {
        if (end != null) {
            System.out.println("The selected ending date for search is " + end);
        } else {
            System.out.println("No ending date selected");
        }
    }

    /**
     * Displays the satellites parameters of search
     */
    public void displaySatellite() {
        if (!satellite.isEmpty()) {
            System.out.println("The selected satellites for the search are :");
            System.out.println(satellite);
        } else {
            System.out.println("No satellite selected");
        }
    }

    /**
     * Displays the dtypes parameters of search
     */
    public void displayDtype() {
        if (!dtype.isEmpty()) {
            System.out.println("The selected data types are :");
            System.out.println(dtype);
        } else {
            System.out.println("No data type selected");
        }
    }

    /**
     * Displays all the parameters of search
     */
    public void displayAll() {
        displayStart();
        displayEnd();
        displaySatellite();
        displayDtype();
    }

    /**
     * Prints an array of Data in the ne
     * 
     * @param r The array of data
     */
    public void printResult(ArrayList<Data> r) {
        if (r.size() > 10) {
            System.out.println(r.size() + " valid results : try to have more detailed search parameters");
        } else {
            System.out.println("There are " + r.size() + " results");
            for (Data data : r) {
                System.out.println(data);
            }
        }
    }

    /**
     * Get the FlashData attribute
     * 
     * @return the FD attribute
     */
    public FlashData getFD() {
        return this.FD;
    }

    /**
     * Get the start attribute
     * 
     * @return the start attribute
     */
    public Date getStart() {
        return this.start;
    }

    /**
     * Get the end attribute
     * 
     * @return the end attribute
     */
    public Date getEnd() {
        return this.end;
    }

    /**
     * Get the satellite attribute which is an array of searched satellites
     * 
     * @return the satellite attribute
     */
    public ArrayList<String> getSatellite() {
        return this.satellite;
    }

    /**
     * Get the dtype attribute which is an array of searched data types
     * 
     * @return the dtype attribute
     */
    public ArrayList<String> getDtype() {
        return this.dtype;
    }

    /**
     * Get the result for a search according only to the start attribute
     * 
     * @return the resultStart attribute
     */
    public ArrayList<Data> getResultStart() {
        return this.resultStart;
    }

    /**
     * Get the result for a search according only to the end attribute
     * 
     * @return the resultEnd attribute
     */
    public ArrayList<Data> getResultEnd() {
        return this.resultEnd;
    }

    /**
     * Get the result for a search according only to the satellite attribute
     * 
     * @return the resultSat attribute
     */
    public ArrayList<Data> getResultSat() {
        return this.resultSat;
    }

    /**
     * Get the result for a search according only to the dtype attribute
     * 
     * @return the resultDtype attribute
     */
    public ArrayList<Data> getResultDtype() {
        return this.resultDtype;
    }

    /**
     * Get the result for a search according to all the attributes
     * 
     * @return the result attribute
     */
    public ArrayList<Data> getResult() {
        return this.result;
    }

}
