package satellite;

import satellite.subsystems.*;
import dtype.*;

import java.util.HashMap;

public class Satellite {

    String name;
    String familyName;
    HashMap<String, Component> components;

    public Satellite(String name, Family f) {
        this.familyName = f.getName().toUpperCase();
        this.name = this.familyName + name;
        this.components = f.getComponents();
    }

    /**
     * This checks whether the component exists
     * 
     * @param comp Name of the searched component
     * @return
     */
    private boolean checkComponentExists(String comp) {
        return this.components.containsKey(comp);
    }

    /**
     * This checks whether the command of a component exists
     * 
     * @param comp    Name of the searched component
     * @param command Name of the searched command
     * @return
     */
    private boolean checkCommandExists(String comp, String command) {
        return this.components.get(comp).checkCommandExists(command);
    }

    /**
     * Get a component according to its name
     * 
     * @param comp Name of the component to get
     * @return The Component object with such a name
     */
    private Component getComponent(String comp) {
        return this.components.get(comp);
    }

    /**
     * Execute a telecommand
     * 
     * @param comp    Name of the component to excute the command on
     * @param command Name of the command to execute
     * @return Indicates if everything happened correctly
     */
    public ReturnedData executeTC(String comp, String command) {
        if (this.checkComponentExists(comp) && this.checkCommandExists(comp, command)) {
            return new ReturnedData(this.getComponent(comp).run(command), null);
        }
        return new ReturnedData(false, null);
    }

    /**
     * Execute a telemeasure
     * 
     * @param comp Name of the component which needs to execute the measure
     * @return An object consisting of a boolean which indicates if everything
     *         happened correctly and a Data which is the recovered data from the
     *         component
     */
    public ReturnedData executeTM(String comp) {
        if (this.checkComponentExists(comp) && this.getComponent(comp).getActivity()) {
            Data recoveredData = this.getComponent(comp).recoverData();
            recoveredData.changeSat(this.getName());
            return new ReturnedData(true, recoveredData);
        }
        return new ReturnedData(false, null);
    }

    /**
     * Get the short name of the satellite
     * 
     * @return The name attribute
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the HashMap listing the components indexed by their name
     * 
     * @return The components attribute
     */
    public HashMap<String, Component> getComponents() {
        return this.components;
    }

    /**
     * Get the family name of the satellite
     * 
     * @return The familyName attribute
     */
    public String getFamilyName() {
        return this.familyName;
    }

    @Override
    public String toString() {
        return " [ Satellite Name : " + this.name + ", Family name : " + this.familyName + ", Components : "
                + this.components.toString() + " ]";
    }
}
