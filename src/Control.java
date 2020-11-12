import dtype.*;
import dmanager.*;

import java.io.IOException;
import java.util.Scanner;

public class Control {

    /**
     * Get the satellite name from the user interface prompted input
     * 
     * @param s The prompted command
     * @return The satellite name
     */
    private static String getSatellite(String s) {
        return s.substring(0, s.indexOf(':'));
    }

    /**
     * Get the Component name from the user interface prompted input
     * 
     * @param s The prompted command
     * @return The component name
     */
    private static String getComponent(String s) {
        return s.substring(s.indexOf(':') + 1, s.lastIndexOf(':'));
    }

    /**
     * Get the command name from the user interface prompted input
     * 
     * @param s The prompted input
     * @return The command name
     */
    private static String getCommand(String s) {
        return s.substring(s.lastIndexOf(':') + 1, s.length());
    }

    /**
     * Enter the command line, type "quit" to exit
     * 
     * @param BDD
     * @throws IOException
     */
    public static void execute(Database BDD) throws IOException {

        /** Printing the Database elements */
        System.out.println(BDD);

        /** Entry command line */
        Scanner sc = new Scanner(System.in);
        String s = "";
        while (!s.equals("quit")) {
            s = sc.nextLine();
            Control.run(BDD, s);
        }
        sc.close();

        /** Printing the final databse */
        System.out.println(BDD);
    }

    /**
     * Checking if the entry is acceptable, ie if it is well formatted (exactly 2
     * times ) without sending back errors, running it if so.
     * 
     * @param BDD Database to use and potentially update
     * @param s   Entry line
     * @throws IOException
     */
    public static void run(Database BDD, String s) throws IOException {
        if (!s.contains(":")) {
            System.out.println("Wrong format of input");
        } else if (!(s.substring(s.indexOf(':') + 1, s.length())).contains(":")) {
            System.out.println("Wrong format of input");
        } else if ((s.substring(s.indexOf(':') + 1, s.lastIndexOf(':')).contains(":"))) {
            System.out.println("Wrong format of input");
        } else {
            operation(BDD, s);
        }
    }

    /**
     * Check if the prompted request is a command or a request for data and execute
     * it
     * 
     * @param s The prompted input
     * @throws IOException
     */
    public static void operation(Database BDD, String s) throws IOException {
        String command = getCommand(s).trim().toUpperCase();
        String component = getComponent(s).trim().toUpperCase();
        String satellite = getSatellite(s).trim().toUpperCase();
        if (BDD.checkSatelliteExists(satellite)) {
            if (command.compareTo("DATA") == 0) {
                Control.sendTM(BDD, satellite, component);
            } else {
                Control.sendTC(BDD, satellite, component, command);
            }
        } else {
            System.out.println("Satellite does not exists");
        }
    }

    /**
     * Send a telecommand request to a satellite
     * 
     * @param satellite Satellite to send the requested telecommand to
     * @param component Component to send the requested telecommand to
     * @param command   The said command to send
     */
    public static void sendTC(Database BDD, String satellite, String component, String command) {
        ReturnedData answer = BDD.getSatellite(satellite).executeTC(component, command);
        ;
        Control.printResult(answer.isSuccess());
    }

    /**
     * Send a telemeasure request to a satellite and prints the results. If the data
     * was well acquired it adds it to the data listing in the Database instance.
     * 
     * @param satellite Satellite to send the requested telemesure to
     * @param component Components to send the requested telemesure to
     * @throws IOException
     */
    public static void sendTM(Database BDD, String satellite, String component) throws IOException {
        ReturnedData answer = BDD.getSatellite(satellite).executeTM(component);
        if (answer.isSuccess()) {
            Control.archive(BDD, answer.getRecoveredData());
        }
        Control.printResult(answer.isSuccess());
    }

    /**
     * Prints OK or KO in the console
     * 
     * @param result Printing according to this value
     */
    public static void printResult(boolean result) {
        if (result) {
            System.out.println("-> OK");
        } else {
            System.out.println("-> KO");
        }
    }

    /**
     * Requests to archive a data to the database
     * 
     * @param BDD  The database
     * @param data The data to add
     * @throws IOException
     */
    public static void archive(Database BDD, Data data) throws IOException {
        BDD.addData(data);
    }

}