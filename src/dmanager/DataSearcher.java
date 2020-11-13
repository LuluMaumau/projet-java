package dmanager;

import dtype.Data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

public class DataSearcher {

    FlashData FD;

    Date start;
    Date end;
    ArrayList<String> satellite;
    ArrayList<String> dtype;

    ArrayList<Data> resultStart;
    ArrayList<Data> resultEnd;
    ArrayList<Data> resultSat;
    ArrayList<Data> resultDtype;
    ArrayList<Data> result;

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

    public DataSearcher(boolean load) throws ClassNotFoundException, IOException {
        this(new FlashData(load), null, null, new ArrayList<String>(), new ArrayList<String>());
    }

    public DataSearcher() throws ClassNotFoundException, IOException {
        this(new FlashData(true), null, null, new ArrayList<String>(), new ArrayList<String>());
    }

    public void load() throws ClassNotFoundException, IOException {
        FD.loadAll();
    }

    public void searchStart() {
        resultStart.clear();
        if (start != null) {
            Collection<ArrayList<Object>> c = FD.dateIndex.tailMap(start, true).values();
            for (ArrayList<Object> arrayList : c) {
                for (Object data : arrayList) {
                    resultStart.add((Data) data);
                }
            }
        }
    }

    public void searchEnd() {
        resultEnd.clear();
        if (end != null) {
            Collection<ArrayList<Object>> c = FD.dateIndex.headMap(start, true).values();
            for (ArrayList<Object> arrayList : c) {
                for (Object data : arrayList) {
                    resultEnd.add((Data) data);
                }
            }
        }
    }

    public void searchSat() {
        resultSat.clear();
        if (!satellite.isEmpty()) {
            for (String sat : satellite) {
                for (Object data : FD.satelliteIndex.get(sat)) {
                    resultSat.add((Data) data);
                }
            }
        }
    }

    public void searchDtype() {
        resultDtype.clear();
        if (!dtype.isEmpty()) {
            for (String dt : dtype) {
                for (Object data : FD.satelliteIndex.get(dt)) {
                    resultDtype.add((Data) data);
                }
            }
        }
    }

    public void search() {
        searchStart();
        searchEnd();
        searchSat();
        searchDtype();
        ArrayList<Data> dateSearch = intersectData(resultStart, start != null, resultEnd, end != null);
        ArrayList<Data> satDtypeSearch = intersectData(resultSat, !satellite.isEmpty(), resultDtype, !dtype.isEmpty());
        result = intersectData(dateSearch, !dateSearch.isEmpty(), satDtypeSearch, !satDtypeSearch.isEmpty());
    }

    private ArrayList<Data> intersectData(ArrayList<Data> list1, boolean searchIn1, ArrayList<Data> list2,
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

    public void addStart(int year, int month, int day, int hrs, int min, int sec) {
        GregorianCalendar GC = new GregorianCalendar(year, month, day, hrs, min, sec);
        start = GC.getTime();
    }

    public void clearStart() {
        start = null;
    }

    public void addEnd(int year, int month, int day, int hrs, int min, int sec) {
        GregorianCalendar GC = new GregorianCalendar(year, month, day, hrs, min, sec);
        end = GC.getTime();
    }

    public void clearEnd() {
        end = null;
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
            System.out.println("The loaded data does not contain such data type, if you are sure it exists try load");
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

    public void printResult(ArrayList<Data> r) {
        if (r.size() > 10) {
            System.out.println(result.size());
        } else {
            for (Data data : r) {
                System.out.println(data);
            }
        }
    }
}
