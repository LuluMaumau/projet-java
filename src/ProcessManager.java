
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.Instant;
import java.time.format.DateTimeParseException;

import data.ReturnedData;

public class ProcessManager extends Control {

    /**
     * execute a process : read the given line, check if the satellite existe, make
     * the satellite execut the process and retrun the success (or not) of the last
     * line
     * 
     * @param BDD Database to use and potentially update
     * @param s   Entry line
     */
    public static void process(Database BDD, String s) {
        String satellite = getSatellite(s).trim().toUpperCase();
        String file = getCommand(s).trim().toUpperCase();
        boolean lastSuccess = false;
        if (BDD.checkSatelliteExists(satellite)) {
            lastSuccess = readFile(BDD, file, satellite);
            Control.printResult(lastSuccess);
        } else {
            System.out.println("Satellite does not exists");
        }
    }

    /**
     * check if the given file exist and if it does, read it line by line. for each
     * line test if it is a repeat command, a conditional command or a satellite
     * command, and execute the right action. return the success (or failure) of the
     * last line
     * 
     * @param BDD       Database to use and potentially update
     * @param file      the file trajectory
     * @param satellite the given satellite
     * @return the success (or failure) of the last lin
     */
    public static boolean readFile(Database BDD, String file, String satellite) {
        BufferedReader lecteurAvecBuffer = null;
        boolean lastSuccess = false;
        String ligne;
        int repeat = 1;
        try {
            lecteurAvecBuffer = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException exc) {
            System.out.println("Process file does not exist");
        }
        try {
            while ((ligne = lecteurAvecBuffer.readLine()) != null) {
                if (testempty(ligne)) {
                } else if (testWait(ligne)) {
                    doWait(ligne);
                } else if (testRepeat(ligne)) {
                    repeat = Integer.parseInt(ligne.substring(ligne.indexOf(" ") + 1, ligne.length()));
                } else if (testAt(ligne)) {
                    doAt(ligne);
                } else if (ligne.equals("ANDTHEN")) {
                    if (!lastSuccess) {
                        repeat = 0;
                    }

                } else if (ligne.equals("ORELSE")) {
                    if (lastSuccess) {
                        repeat = 0;
                    }
                } else {
                    lastSuccess = doLine(BDD, ligne, satellite, repeat);
                    repeat = 1;
                }
            }
        } catch (IOException e) {
            System.out.println("wrong type of file");
        }
        try {
            lecteurAvecBuffer.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return lastSuccess;
    }

    /**
     * test if the given line is a wait command
     * 
     * @param s the given line
     * @return true if the line is a wait command
     */
    public static boolean testWait(String s) {
        if (s.length() > 6) {
            if (s.substring(0, 4).equals("WAIT")) {
                return true;
            }
        }
        return false;
    }

    /**
     * wait the time needed
     * 
     * @param s the wait command line
     */
    public static void doWait(String s) {
        int time = 0;
        try {
            time = Integer.parseInt(s.substring(s.indexOf(" ") + 1, s.length()));
        } catch (DateTimeParseException e) {
            System.out.println("wrong 'wait' input");
        }
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            System.out.println("Exception in doAt");
        }
    }

    /**
     * test if the given line is a AT command
     * 
     * @param s the given line
     * @return true if the line is a AT command
     */
    public static boolean testAt(String s) {
        if (s.length() > 4) {
            if (s.substring(0, 2).equals("AT")) {
                return true;
            }
        }
        return false;
    }

    /**
     * wait the correct time to be over the given instant
     * 
     * @param s the AT command line
     */
    public static void doAt(String s) {
        long time = 0;
        try {
            time = Instant.parse(s.substring(s.indexOf(" ") + 1, s.length())).toEpochMilli();
        } catch (DateTimeParseException e) {
            System.out.println("wrong 'at' input");
        }
        long currentTime = System.currentTimeMillis();
        if (time - currentTime > 0) {
            try {
                Thread.sleep(time - currentTime);
            } catch (InterruptedException e) {
                System.out.println("Exception in doAt");
            }
        }
    }

    /**
     * test if a ligne is a comment or an empty ligne
     * 
     * @param s the given line
     * @return true if the line is empty, false if not
     */
    public static boolean testempty(String s) {
        if (s.isEmpty()) {
            return true;
        } else if (s.substring(0, 1).equals(";")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * test if a line is a repeat command
     * 
     * @param ligne the given line
     * @return true if the line is a repeat command, false if not
     */
    public static boolean testRepeat(String ligne) {
        if (!ligne.contains(" ")) {
            return false;
        } else if (ligne.substring(0, ligne.indexOf(' ')).equals("REPEAT")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * execute a T/c,a T/M or a process the right number of time and return the
     * success of hte last action donne
     * 
     * @param BDD       Database to use and potentially update
     * @param ligne     the given line
     * @param satellite the given satellite
     * @param repeat    the number of time the line have to be repeat
     * @return the sucess of the las action
     */
    public static boolean doLine(Database BDD, String ligne, String satellite, int repeat) {
        boolean success = false;
        if (!ligne.contains(":")) {
            while (repeat > 0) {
                success = readFile(BDD, ligne, satellite);
                repeat -= 1;
            }
        } else if (!(ligne.substring(ligne.indexOf(':') + 1, ligne.length())).contains(":")) {
            while (repeat > 0) {
                success = processOperation(BDD, ligne, satellite);
                repeat -= 1;
            }
        } else {
            System.out.println("Wrong format of ligne");
            return false;
        }
        return success;
    }

    /**
     * check if the command is a T/M or a T/C and send the request to the correct
     * component. return the sucess (or not) of the execution
     * 
     * @param BDD       Database to use and potentially update
     * @param s         the command line
     * @param satellite the given satellite
     * @return the success of the T/M or T/C
     */
    public static boolean processOperation(Database BDD, String s, String satellite) {
        // la lecture dans un fichier est décalé par rapport à celle dans une tC TM
        String command = getCommand(s).trim().toUpperCase();
        String component = getSatellite(s).trim().toUpperCase();
        boolean success = true;
        if (command.compareTo("DATA") == 0) {
            success = processSendTM(BDD, satellite, component);
        } else {
            success = processSendTC(BDD, satellite, component, command);
        }
        return success;
    }

    /**
     * Send a telemeasure request to a satellite and return the results. If the data
     * was well acquired it adds it to the data listing in the Database instance.
     * 
     * @param BDD       Database to use
     * @param satellite Satellite to send the requested telemesure to
     * @param component Components to send the requested telemesure to
     * @param command   The said command to send
     * @return the success of the TC
     */
    public static boolean processSendTC(Database BDD, String satellite, String component, String command) {
        ReturnedData answer = BDD.getSatellite(satellite).executeTC(component, command);
        ;
        return answer.isSuccess();
    }

    /**
     * 
     * @param BDD       Database to use and potentially update
     * @param satellite Satellite to send the requested telemesure to
     * @param component Components to send the requested telemesure to
     * @return the success of the tm
     */
    public static boolean processSendTM(Database BDD, String satellite, String component) {
        ReturnedData answer = BDD.getSatellite(satellite).executeTM(component);
        if (answer.isSuccess()) {
            Control.archive(BDD, answer.getRecoveredData());
        }
        return answer.isSuccess();
    }
}
