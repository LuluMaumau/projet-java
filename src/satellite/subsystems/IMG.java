package satellite.subsystems;

import dtype.*;

import java.util.ArrayList;

public class IMG extends Component {

    public IMG(String name, String satName, ArrayList<String> commands) {
        super(name, satName, commands);
    }

    public Data recoverData() {
        return new GrayImage("", super.getName());
    }

    public boolean runSpecific(String command) {
        return true;
    }

}
