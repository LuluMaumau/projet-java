package dmanager;

import dtype.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

public class FlashData {

    TreeMap<Object, ArrayList<Object>> dateIndex;
    HashMap<Object, ArrayList<Object>> satelliteIndex;
    HashMap<Object, ArrayList<Object>> dtypeIndex;

    public FlashData(boolean load) throws IOException, ClassNotFoundException {

        dateIndex = new TreeMap<Object, ArrayList<Object>>();
        satelliteIndex = new HashMap<Object, ArrayList<Object>>();
        dtypeIndex = new HashMap<Object, ArrayList<Object>>();

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

                    if (!dataFile.getName().equals("sat.bin") && !dataFile.getName().equals("nextseqnum.bin")) {

                        String dataPath = dataFile.getCanonicalPath();
                        System.out.println(dataPath);
                        FileInputStream inDataFile = new FileInputStream(dataPath);
                        ObjectInputStream inData = new ObjectInputStream(inDataFile);
                        Data data = (Data) inData.readObject();

                        addValue(dateIndex, data.getDate(), data);
                        addValue(satelliteIndex, data.getSat(), data);
                        addValue(dtypeIndex, data.getComponent(), data);

                        inData.close();
                    }

                }
            }

        }
    }

    private void addValue(AbstractMap<Object, ArrayList<Object>> map, Object key, Object value) {
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

}
