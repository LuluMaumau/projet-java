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

                    break;
                case "print start":

                    break;
                case "print end":

                    break;
                case "print pos":

                    break;
                case "print sat":

                    break;
                case "print comp":

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
