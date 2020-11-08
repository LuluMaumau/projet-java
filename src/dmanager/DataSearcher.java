package dmanager;

import dtype.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Date;
import java.util.Scanner;

public class DataSearcher {

    HashMap<Date, Data> dateIndex;
    HashMap<String, Data> satIndex;

    public DataSearcher() throws IOException, ClassNotFoundException {
        dateIndex = new HashMap<Date, Data>();
        satIndex = new HashMap<String, Data>();

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

                    dateIndex.put(data.getDate(), data);
                    satIndex.put(data.getSat(), data);

                    inData.close();

                }
            }
        }
    }

    public static void main(String[] args) {

        /** Entry command line */
        Scanner sc = new Scanner(System.in);
        String s = "";
        while (!s.equals("quit")) {
            s = sc.next();
        }
        sc.close();
    }
}
