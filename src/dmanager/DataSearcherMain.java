package dmanager;

import java.io.IOException;
import java.util.Scanner;

public class DataSearcherMain {

    public static void main(String[] args) throws ClassNotFoundException, IOException {

        DataSearcher DS = new DataSearcher();

        int day, hrs, min, sec;
        String sat, dtype;

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
                    System.out.println("Which parameter of search to add ?");
                    s = sc.nextLine();
                    switch (s.toLowerCase()) {
                        case "start":
                            System.out.println("Day ?");
                            day = sc.nextInt();
                            System.out.println("Hour ?");
                            hrs = sc.nextInt();
                            System.out.println("Minute ?");
                            min = sc.nextInt();
                            System.out.println("Second ?");
                            sec = sc.nextInt();
                            DS.addStart(2020, 11, day, hrs, min, sec);
                            break;
                        case "end":
                            System.out.println("Day ?");
                            day = sc.nextInt();
                            System.out.println("Hour ?");
                            hrs = sc.nextInt();
                            System.out.println("Minute ?");
                            min = sc.nextInt();
                            System.out.println("Second ?");
                            sec = sc.nextInt();
                            DS.addEnd(2020, 11, day, hrs, min, sec);
                            break;
                        case "sat":
                            System.out.println("Which satellite to add ?");
                            sat = sc.nextLine().toUpperCase();
                            DS.addSatellite(sat);
                            break;
                        case "stype":
                            System.out.println("Which satellite to add ?");
                            dtype = sc.nextLine().toUpperCase();
                            DS.addSatellite(dtype);
                            break;
                    }
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
