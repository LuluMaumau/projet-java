package dmanager;

import satellite.subsystems.*;
import satellite.*;
import dtype.*;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;

public class Database {

    HashMap<String, Satellite> satList;
    HashMap<String, Family> famList;

    /**
     * Initialization of the database, we will later implement an external Dataset
     * To stock the satellites' and the families I use an HashMap indexed by their
     * names
     * 
     * For the measured data I use a HashMap index by the date of the measure, seen
     * as a double for the time being and it will be stocked as such :
     * YYYYMMDD.HHMMSS
     * 
     * @throws IOException
     * @throws ClassNotFoundException
     */

    public Database() throws ClassNotFoundException, IOException {
        /** Constructing the HashMaps */
        satList = new HashMap<String, Satellite>();
        famList = new HashMap<String, Family>();

        /** Completing the satellite list with those already existing in the database */
        File[] fileList = (new File("data")).listFiles();

        for (File file : fileList) {
            if (file.isDirectory()) {
                String filePath = file.getCanonicalPath();
                FileInputStream inSatFile = new FileInputStream(filePath + "/sat.bin");
                ObjectInputStream inSat = new ObjectInputStream(inSatFile);
                Satellite sat = (Satellite) inSat.readObject();
                satList.put(sat.getName(), sat);
                inSat.close();
            }
        }
    }

    /**
     * Makes a Satellite and adds it to the HashMap listing the Satellites indexed
     * by their names
     * 
     * @param name Name of the new satellite
     * @param f    Family of the new Satellite
     * @throws FileNotFoundException
     */
    public void makeSatellite(String name, Family f) throws IOException {

        Satellite sat = new Satellite(name.toUpperCase(), f);
        File newFile = new File("data/" + sat.getName());

        if (!newFile.exists()) {
            boolean madeNewFile = newFile.mkdirs();

            if (!madeNewFile) {
                throw new Error(
                        "This satellite doesn't exist but the program couldn't make a new folder to store its informations");
            } else {
                /** Making the nextseqnum file */
                FileOutputStream newSeqFile = new FileOutputStream("data/" + sat.getName() + "/nextseqnum.bin");
                ObjectOutputStream outSeq = new ObjectOutputStream(newSeqFile);
                outSeq.writeLong(000000000);
                outSeq.flush();
                outSeq.close();

                /** Making a file with the satelitte's informations */
                FileOutputStream newSatFile = new FileOutputStream("data/" + sat.getName() + "/sat.bin");
                ObjectOutputStream outSat = new ObjectOutputStream(newSatFile);
                outSat.writeObject(sat);
                outSat.flush();
                outSat.close();

                /** Adding the satellite to the satellite HashMap */
                satList.put(sat.getName(), sat);
            }

        } else {
            System.out.println("Satellite with the same name already exists, please change the satellite's name");
        }

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
     * @throws IOException
     */
    public void addData(Data data) throws IOException {
        /** Checking the next sequence number */
        Satellite sat = satList.get(data.getSat());
        FileInputStream inSeqFile = new FileInputStream("data/" + sat.getName() + "/nextseqnum.bin");
        ObjectInputStream inSeq = new ObjectInputStream(inSeqFile);
        long n = inSeq.readLong();
        inSeq.close();

        if (n == 99999999) {
            System.out.println("Data folder for " + sat.getName() + " is full.");
        } else {
            /** Writing the data */
            FileOutputStream newDataFile = new FileOutputStream("data/" + sat.getName() + "/" + n + ".bin");
            ObjectOutputStream outData = new ObjectOutputStream(newDataFile);
            outData.writeObject(data);
            outData.close();
            /** Modifying the next sequence number */
            FileOutputStream nextSeqFile = new FileOutputStream("data/" + sat.getName() + "/nextseqnum.bin");
            ObjectOutputStream outSeq = new ObjectOutputStream(nextSeqFile);
            outSeq.writeLong(n + 1);
            outSeq.close();
        }
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

    public String toString() {
        return this.satList.toString() + "\n" + this.famList.toString() + "\n";
    }
}