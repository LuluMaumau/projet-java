package dmanager;

import java.util.Scanner;

public class DataSearcherMain {

    public static void main(String[] args) {

        /** Entry command line */
        Scanner sc = new Scanner(System.in);
        String s = "";
        while (!s.equals("quit")) {
            s = sc.next();
            switch (s) {
                case "":

                    break;
                case "search":

                    break;
                case "load":

                    break;
                default:
                    break;
            }
        }
        sc.close();
    }

}
