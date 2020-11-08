package dmanager;

import dtype.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import java.util.ArrayList;
import java.util.HashMap;

public class FlashData {

    HashMap<Object, ArrayList<Object>> dateIndex;
    HashMap<Object, ArrayList<Object>> satIndex;
    HashMap<Object, ArrayList<Object>> posIndex;
    HashMap<Object, ArrayList<Object>> compIndex;

    public FlashData(boolean load) throws IOException, ClassNotFoundException {

        dateIndex = new HashMap<Object, ArrayList<Object>>();
        satIndex = new HashMap<Object, ArrayList<Object>>();
        posIndex = new HashMap<Object, ArrayList<Object>>();
        compIndex = new HashMap<Object, ArrayList<Object>>();

        /** Completing the satellite list with those already existing in the database */
        if (load) {
            this.loadAll();
        }
    }

    public void loadAll() throws IOException, ClassNotFoundException {
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
            if (!hashMap.containsValue(value)) {
                hashMap.get(key).add(value);
            }
        } else {
            ArrayList<Object> list = new ArrayList<>();
            list.add(value);
            hashMap.put(key, list);
        }
    }

}
