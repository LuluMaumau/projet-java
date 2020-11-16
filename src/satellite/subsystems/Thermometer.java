package satellite.subsystems;

import dtype.*;

import java.util.ArrayList;

public class Thermometer extends Component {

    private static final long serialVersionUID = 1L;

    public Thermometer(String name, ArrayList<String> commands) {
        super(name, commands);
    }

    public Data recoverData() {
        return new GrayImage("", super.getName());
    }

    public boolean runSpecific(String command) {
        return true;
    }

}