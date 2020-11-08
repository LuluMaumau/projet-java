package dmanager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class DataSearcher {

    FlashData FD;

    Date start;
    Date end;
    ArrayList<String> satellite;
    ArrayList<String> dtype;

    public DataSearcher(FlashData FD, Date start, Date end, ArrayList<String> satellite, ArrayList<String> dtype) {
        this.FD = FD;
        this.start = start;
        this.end = end;
        this.satellite = satellite;
        this.dtype = dtype;
    }

    public DataSearcher(boolean load) throws ClassNotFoundException, IOException {
        this(new FlashData(load), null, null, new ArrayList<String>(), new ArrayList<String>());
    }

    public DataSearcher() throws ClassNotFoundException, IOException {
        this(new FlashData(false), null, null, new ArrayList<String>(), new ArrayList<String>());
    }

    public void load() throws ClassNotFoundException, IOException {
        FD.loadAll();
    }

    public void search() {

    }

    public void display(ArrayList<Object> toPrint) {
        if (toPrint.size() > 10) {
            System.out.println(toPrint.size());
        } else {
            for (Object object : toPrint) {
                System.out.println(object);
            }
        }
    }

    public void addSatellite(String sat) {
        if (FD.satelliteIndex.containsKey(sat)) {
            satellite.add(sat);
        } else {
            System.out.println("The loaded data does not contain such satellite, try load.");
        }
    }

    public void removeSatellite(String sat) {
        if (satellite.contains(sat)) {
            satellite.remove(sat);
        } else {
            System.out.println("This satellite is not a parameter of the search.");
        }
    }

    public void clearSatellite() {
        satellite.clear();
    }

    public void addDtype(String dt) {
        if (FD.dtypeIndex.containsKey(dt)) {
            dtype.add(dt);
        } else {
            System.out.println("The loaded data does not contain such data type, try load");
        }
    }

    public void removeDtype(String dt) {
        if (satellite.contains(dt)) {
            satellite.remove(dt);
        } else {
            System.out.println("This data type is not a parameter of the search.");
        }
    }

    public void clearDtype() {
        dtype.clear();
    }

    public void displayStart() {
        if (start != null) {
            System.out.println("The selected starting date for search is " + start);
        } else {
            System.out.println("No starting date selected, the search won't discriminate this parameter");
        }
    }

    public void displayEnd() {
        if (end != null) {
            System.out.println("The selected ending date for search is " + end);
        } else {
            System.out.println("No ending date selected, the search won't discriminate this parameter");
        }
    }

    public void displaySatellite() {
        if (!satellite.isEmpty()) {
            System.out.println("The selected satellites for the search are :");
            System.out.println(satellite);
        } else {
            System.out.println("No satellite selected, the search won't discriminate this parameter");
        }
    }

    public void displayDtype() {
        if (!dtype.isEmpty()) {
            System.out.println("The selected data types are :");
            System.out.println(dtype);
        } else {
            System.out.println("No data type selected, the search won't discriminate this parameter");
        }
    }

    public void displayAll() {
        displayStart();
        displayEnd();
        displaySatellite();
        displayDtype();
    }

    public void printResults() {

    }
}
