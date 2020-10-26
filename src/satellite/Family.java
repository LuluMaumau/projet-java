package satellite;

import satellite.subsystems.*;

import java.util.HashMap;

public class Family {

    private String name;
    private HashMap<String, Component> component;

    public Family(String name, HashMap<String, Component> component) {
        this.name = name.toUpperCase();
        this.component = component;
    }

    /**
     * Get the family name
     * 
     * @return the name attribute
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get the HashMap listing usual components of this family indexed by their name
     * 
     * @return The components attribute
     */
    public HashMap<String, Component> getComponents() {
        return this.component;
    }

    @Override
    public String toString() {
        return " [ Family Name : " + this.name + ", Components : " + this.component.toString() + " ]";
    }
}
