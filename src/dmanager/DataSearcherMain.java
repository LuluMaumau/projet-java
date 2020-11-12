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
            s = sc.nextLine();
            switch (s.toLowerCase()) {
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
                    s = sc.nextLine();
                    DS.addSatellite(s.toUpperCase());
                    break;
                case "search":
                    DS.search();
                    DS.printResult(DS.result);
                    break;
                case "search start":
                    DS.searchStart();
                    DS.printResult(DS.resultStart);
                    break;
                case "search end":
                    DS.searchEnd();
                    DS.printResult(DS.resultEnd);
                    break;
                case "search sat":
                    DS.searchSat();
                    DS.printResult(DS.resultSat);
                    break;
                case "search dtype":
                    DS.searchDtype();
                    DS.printResult(DS.resultDtype);
                    break;
                case "load":
                    DS.load();
                    break;
                case "print result":
                    DS.printResult(DS.result);
                    break;
                default:
                    System.out.println("Command not recognized");
                    break;
            }
        }
        sc.close();
    }

}
