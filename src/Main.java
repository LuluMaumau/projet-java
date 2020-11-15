import satellite.subsystems.*;
import dmanager.*;
import exec.Control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {

        /**
         * Making a satellite LULU from family FAM (fullname FAMLULU) with one Imager
         */
        Database BDD = new Database();

        HashMap<String, Component> Components1 = new HashMap<String, Component>();
        HashMap<String, Component> Components2 = new HashMap<String, Component>();
        HashMap<String, Component> Components3 = new HashMap<String, Component>();

        ArrayList<String> commands = new ArrayList<String>();

        IMG Imager = new IMG("IMAGER", commands);
        Thermometer thermometer = new Thermometer("THERMOMETER", commands);
        Components1.put(Imager.getName(), Imager);
        Components2.put(thermometer.getName(), thermometer);
        Components3.put(Imager.getName(), Imager);
        Components3.put(thermometer.getName(), thermometer);

        BDD.makeFamily("FAM1", Components1);
        BDD.makeFamily("FAM2", Components2);
        BDD.makeFamily("FAM3", Components3);

        BDD.makeSatellite("LULU", BDD.getFamily("FAM1"));
        BDD.makeSatellite("PAULO", BDD.getFamily("FAM2"));
        BDD.makeSatellite("ESTOU", BDD.getFamily("FAM3"));

        Control.execute(BDD);

    }
}
