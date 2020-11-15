package satellite.subsystems;

import data.*;

import java.util.ArrayList;

public abstract class Component {

    String name;
    boolean active;
    ArrayList<String> commands;

    public Component(String name, ArrayList<String> commands) {
        this.name = name.toUpperCase();
        this.active = true;
        for (String string : commands) {
            string.toUpperCase();
        }
        commands.add(0, "ACTIVATE");
        commands.add(1, "DEACTIVATE");
        this.commands = commands;
    }

    /**
     * Activate the component
     * 
     * @return Indicates if it was well activated
     */
    public boolean activate() {
        this.active = true;
        return (this.active == true);
    }

    /**
     * Deactivate the component
     * 
     * @return Indicates if it was well deactivated
     */
    public boolean deactivate() {
        this.active = false;
        return (this.active == false);
    }

    /**
     * Check if a specified command exists in this component
     * 
     * @param command The specified command
     * @return Boolean giving thr answer to the question
     */
    public boolean checkCommandExists(String command) {
        return this.commands.contains(command);
    }

    /**
     * Run the specified command
     * 
     * @param command The command to run
     * @return Indicates if all happens correctly
     */
    public boolean run(String command) {

        switch (command) {
            case ("ACTIVATE"): {
                return this.activate();
            }
            case ("DESACTIVATE"): {
                return this.deactivate();
            }
            default: {
                return runSpecific(command);
            }
        }

    }

    /**
     * @param getName(
     * @return Data
     */
    /**
     * A method to recover data from this component. Type will vary to match the
     * type of recoveredData
     * 
     * @return Data gotten from a measure of this component
     */
    public abstract Data recoverData();

    /**
     * @param getName(
     * @return boolean
     */
    /**
     * Run the command which are component exclusive (other than activate and
     * deactivate) that may be implemented further
     * 
     * @param command The command to run
     * @return Indicates if everything happened as it was meant to
     */
    public abstract boolean runSpecific(String command);

    /**
     * Get the name of the Component
     * 
     * @return The name attribute
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get the Activity of the Component (ON/OFF)
     * 
     * @return The active attribute
     */
    public boolean getActivity() {
        return this.active;
    }

    /**
     * Get the array of existing commands
     * 
     * @return The commands attribute
     */
    public ArrayList<String> getCommands() {
        return this.commands;
    }

    @Override
    public final String toString() {
        return "Component name : " + this.name + ", isActive : " + this.active + ", Commands : "
                + this.commands.toString();
    }

}
