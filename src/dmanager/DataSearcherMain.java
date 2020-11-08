package dmanager;

import java.io.IOException;
import java.util.Scanner;

public class DataSearcherMain {

    public static void main(String[] args) throws ClassNotFoundException, IOException {

        DataSearcher DS = new DataSearcher();

        /** Entry command line */
        Scanner sc = new Scanner(System.in);
        String s = "";
        while (!s.equals("quit")) {
            s = sc.next();
            switch (s) {
                case "print":
                    DS.displayAll();
                    break;
                case "print start":
                    DS.displayStart();
                    break;
                case "print end":
                    DS.displayEnd();
                    break;
                case "print sat":
                    DS.displaySatellite();
                    break;
                case "print dtype":
                    DS.displayDtype();
                    break;
                case "search":
                    DS.search();
                    DS.printResults();
                    break;
                case "load":
                    DS.load();
                    break;
                default:
                    System.out.println("Command not recognized");
                    break;
            }
        }
        sc.close();
    }

}
