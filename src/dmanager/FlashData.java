package dmanager;

import dtype.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.TreeMap;

public class FlashData {

    private TreeMap<Object, ArrayList<Object>> dateIndex;
    private TreeMap<Object, ArrayList<Object>> satelliteIndex;
    private TreeMap<Object, ArrayList<Object>> dtypeIndex;

    /**
     * Constructor of the FlashData
     * 
     * @param load True if you want to load the data in the data rep
     */
    public FlashData(boolean load) {

        dateIndex = new TreeMap<Object, ArrayList<Object>>();
        satelliteIndex = new TreeMap<Object, ArrayList<Object>>();
        dtypeIndex = new TreeMap<Object, ArrayList<Object>>();

        /** Completing the satellite list with those already existing in the database */
        if (load) {
            this.loadAll();
        }
    }

    /**
     * Load the data in the HardDisk (data folder in same directory as src) onto the
     * FlashData Puts it in 3 maps : 1 indexed by date, 1 by satellite and 1 by data
     * type
     */
    public void loadAll() {
        removeAll();
        File[] satList = (new File("data")).listFiles();
        // Going through all the files and adding them if necessary
        for (File satFile : satList) {

            if (satFile.isDirectory()) {
                File[] dataList = satFile.listFiles();
                for (File dataFile : dataList) {

                    if (!dataFile.getName().equals("sat.bin") && !dataFile.getName().equals("nextseqnum.bin")) {

                        try {
                            String dataPath = dataFile.getCanonicalPath();
                            System.out.println(dataPath);
                            FileInputStream inDataFile = new FileInputStream(dataPath);
                            ObjectInputStream inData = new ObjectInputStream(inDataFile);
                            Data data;
                            try {
                                data = (Data) inData.readObject();
                                addValue(dateIndex, data.getDate(), data);
                                addValue(satelliteIndex, data.getSat(), data);
                                addValue(dtypeIndex, data.getDtype(), data);
                            } catch (ClassNotFoundException e) {
                                System.out.println("Couldn't add " + dataPath
                                        + " to the FlashData, it won't be looked when searching");
                            }
                            inData.close();
                        } catch (IOException ioe) {
                            System.out.print(ioe.getMessage());
                        }
                    }

                }
            }

        }
    }

    /**
     * Add a value to a TreeMap
     * 
     * @param map   The TreeMap to add the value to
     * @param key   The key
     * @param value The value
     */
    private void addValue(TreeMap<Object, ArrayList<Object>> map, Object key, Object value) {
        if (map.containsKey(key)) {
            if (!map.containsValue(value)) {
                map.get(key).add(value);
            }
        } else {
            ArrayList<Object> list = new ArrayList<>();
            list.add(value);
            map.put(key, list);
        }
    }

    /**
     * Clears all the attributes
     */
    private void removeAll() {
        dateIndex.clear();
        satelliteIndex.clear();
        dtypeIndex.clear();
    }

    /**
     * Get the dateIntex attribute
     * 
     * @return dateIndex
     */
    public TreeMap<Object, ArrayList<Object>> getDateIndex() {
        return this.dateIndex;
    }

    /**
     * Get the satelliteIntex attribute
     * 
     * @return satelliteIndex
     */
    public TreeMap<Object, ArrayList<Object>> getSatelliteIndex() {
        return this.satelliteIndex;
    }

    /**
     * Get the dtypeIntex attribute
     * 
     * @return dtypeIndex
     */
    public TreeMap<Object, ArrayList<Object>> getDtypeIndex() {
        return this.dtypeIndex;
    }

}
