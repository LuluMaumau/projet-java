package satellite.subsystems;

import data.*;

import java.util.ArrayList;

public class IMG extends Component {

    public IMG(String name, ArrayList<String> commands) {
        super(name, commands);
    }

    public Data recoverData() {
        return new GrayImage("", super.getName());
    }

    public boolean runSpecific(String command) {
        return true;
    }

}
