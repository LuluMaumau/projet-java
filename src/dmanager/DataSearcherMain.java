package dmanager;

import java.util.Scanner;

public class DataSearcherMain {

    public static void main(String[] args) {

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
                case "print result":
                    DS.printResult(DS.result);
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
                            DS.addStart(2020, 10, day, hrs, min, sec);
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
                            DS.addEnd(2020, 10, day, hrs, min, sec);
                            break;
                        case "sat":
                            System.out.println("Which satellite to add ?");
                            sat = sc.nextLine().toUpperCase();
                            DS.addSatellite(sat);
                            break;
                        case "dtype":
                            System.out.println("Which dtype to add ?");
                            dtype = sc.nextLine().toUpperCase();
                            DS.addDtype(dtype);
                            break;
                        default:
                            System.out.println("Command not recognized");
                    }
                    break;
                case "remove":
                    System.out.println("Which parameter of search to remove ?");
                    s = sc.nextLine();
                    switch (s.toLowerCase()) {
                        case "start":
                            DS.start = null;
                            break;
                        case "end":
                            DS.end = null;
                            break;
                        case "sat":
                            System.out.println("Which satellite ?");
                            s = sc.nextLine();
                            switch (s.toLowerCase()) {
                                case "all":
                                    DS.satellite.clear();
                                    break;
                                default:
                                    DS.removeSatellite(s.toUpperCase());
                                    break;
                            }
                            break;
                        case "dtype":
                            System.out.println("Which data type ?");
                            s = sc.nextLine();
                            switch (s.toLowerCase()) {
                                case "all":
                                    DS.dtype.clear();
                                    break;
                                default:
                                    DS.removeDtype(s);
                                    break;
                            }
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
                default:
                    System.out.println("Command not recognized");
                    break;
            }
        }
        sc.close();
    }

}
