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
            switch (s.trim().toLowerCase()) {
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
                case "add":
                    System.out.println("Which satellite to add ?");
                    s = sc.next();
                    System.out.println(s);
                    DS.addSatellite(s);
                    break;
                case "search":
                    DS.search();
                    DS.printResult();
                    break;
                case "load":
                    DS.load();
                    break;
                case "print result":
                    DS.printResult();
                    break;
                default:
                    System.out.println("Command not recognized");
                    break;
            }
        }
        sc.close();
    }

}
