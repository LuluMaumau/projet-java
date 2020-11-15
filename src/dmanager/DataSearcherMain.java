package dmanager;

import java.util.Scanner;

public class DataSearcherMain {

    public static void main(String[] args) {

        System.out.println("Initializing...");
        DataSearcher DS = new DataSearcher();

        int day, hrs, min, sec;
        String sat, dtype;

        /** Entry command line */
        Scanner sc = new Scanner(System.in);
        String s = "";

        System.out.println("Ready !");

        while (!s.equals("quit")) {
            s = sc.nextLine();
            switch (s.toLowerCase()) {
                case "print":
                    DS.displayAll();
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
                            System.out.println("Wrong input. Must be start, end, sat or dtype");
                            break;
                    }
                    break;
                case "remove":
                    System.out.println("Which parameter of search to remove ?");
                    s = sc.nextLine();
                    switch (s.toLowerCase()) {
                        case "start":
                            DS.removeStart();
                            ;
                            break;
                        case "end":
                            DS.removeEnd();
                            ;
                            break;
                        case "sat":
                            System.out.println("Which satellite ?");
                            s = sc.nextLine();
                            switch (s.toLowerCase()) {
                                case "all":
                                    DS.clearSatellite();
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
                                    DS.clearDtype();
                                    break;
                                default:
                                    DS.removeDtype(s.toUpperCase());
                                    break;
                            }
                            break;
                        default:
                            System.out.println("Wrong input. Must be start, end, sat or dtype");
                            break;
                    }
                    break;
                case "search":
                    DS.search();
                    DS.displayAll();
                    DS.printResult(DS.getResult());
                    break;
                case "search start":
                    DS.searchStart();
                    DS.displayStart();
                    DS.printResult(DS.getResultStart());
                    break;
                case "search end":
                    DS.searchEnd();
                    DS.displayEnd();
                    DS.printResult(DS.getResultEnd());
                    break;
                case "search sat":
                    DS.searchSat();
                    DS.displaySatellite();
                    DS.printResult(DS.getResultSat());
                    break;
                case "search dtype":
                    DS.searchDtype();
                    DS.displayDtype();
                    DS.printResult(DS.getResultDtype());
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
