import satellite.subsystems.*;
import dmanager.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {

        /**
         * Making a satellite LULU from family FAM (fullname FAMLULU) with one Imager
         */
        Database BDD = new Database();
        HashMap<String, Component> Components = new HashMap<String, Component>();
        ArrayList<String> commands = new ArrayList<String>();

        IMG Imager = new IMG("IMAGER", commands);
        Components.put(Imager.getName(), Imager);

        BDD.makeFamily("FAM", Components);
        BDD.makeSatellite("LULU", BDD.getFamily("FAM"));
        BDD.makeSatellite("PAULO", BDD.getFamily("FAM"));

        Control.execute(BDD);

    }
}
