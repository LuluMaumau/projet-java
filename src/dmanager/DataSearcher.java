package dmanager;

import dtype.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Date;

public class DataSearcher {

    HashMap<Object, ArrayList<Object>> dateIndex;
    HashMap<Object, ArrayList<Object>> satIndex;
    HashMap<Object, ArrayList<Object>> posIndex;
    HashMap<Object, ArrayList<Object>> compIndex;
    Date date;
    String sat;
    Position pos;
    String comp;

    public DataSearcher() throws IOException, ClassNotFoundException {

        dateIndex = new HashMap<Object, ArrayList<Object>>();
        satIndex = new HashMap<Object, ArrayList<Object>>();
        posIndex = new HashMap<Object, ArrayList<Object>>();
        compIndex = new HashMap<Object, ArrayList<Object>>();

        /** Completing the satellite list with those already existing in the database */
        File[] satList = (new File("data")).listFiles();
        for (File satFile : satList) {

            if (satFile.isDirectory()) {

                File[] dataList = satFile.listFiles();
                for (File dataFile : dataList) {

                    String dataPath = dataFile.getCanonicalPath();
                    FileInputStream inDataFile = new FileInputStream(dataPath);
                    ObjectInputStream inData = new ObjectInputStream(inDataFile);
                    Data data = (Data) inData.readObject();

                    addValue(dateIndex, data.getDate(), data);
                    addValue(satIndex, data.getSat(), data);
                    addValue(posIndex, data.getPosition(), data);
                    addValue(compIndex, data.getComponent(), data);

                    inData.close();

                }

            }

        }

    }

    private void addValue(HashMap<Object, ArrayList<Object>> hashMap, Object key, Object value) {
        if (hashMap.containsKey(key)) {
            hashMap.get(key).add(value);
        } else {
            ArrayList<Object> list = new ArrayList<>();
            list.add(value);
            hashMap.put(key, list);
        }
    }

    private ArrayList<Object> findDate() {
        return dateIndex.get(this.date);
    }

    private ArrayList<Object> findSatellite() {
        return satIndex.get(this.sat);
    }

    private ArrayList<Object> findPosition() {
        return posIndex.get(this.pos);
    }

    private ArrayList<Object> findSat() {
        return compIndex.get(this.comp);
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

}
