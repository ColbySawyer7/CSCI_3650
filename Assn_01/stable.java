import java.util.*;
import java.io.*;
//===================================================
//      Assignment 01 - Gale-Shapley
//===================================================
// by: Colby Sawyer- sawyerc17@students.ecu.edu
// B01204512
//===================================================
public class stable {

    class Person {
        String name;
        String[] preferences;
        Person(String name, String[] preferences) {
            this.name = name;
            this.preferences = preferences;
        }
        String[] getPref() {
            return preferences;
        }
        public String toString() {
            return name;
        }
    }
    
    class Man extends Person {
        Man(String name, String[] preferences) {
            super(name, preferences);
        }

        Man newMan(String name, String[] pref){
            return new Man(name,pref);
        }
    }
    class Woman extends Person {
        Woman(String name, String[] preferences) {
            super(name, preferences);
        }
    }

//LinkedLists to store member information
    private static LinkedList<Man> boyList = new LinkedList<Man>();
    private Woman[] girlList = new Woman[100];

    // Find Number of Members
    private int boyCount;
    private int girlCount;

    // private static LinkedList<String> unmatchedMem;
    // private static String[] marriage;

    // ===================================================
    // Wedding (Add to Seperate Marriage Array)
    // ===================================================
    private static Man addMan(String name, String[] pref){
        return new Man(name, pref);
    }

    // ===================================================
    // Wedding (Add to Seperate Marriage Array)
    // ===================================================
    public static void wedding(String man, String woman) {
        // marriage[marriage.length] = "Man: " + man + " Woman: " + woman;
    }

    // ===================================================
    // Perform Gale-Shapley
    // ===================================================
    public static void galeShapley(LinkedList<String> men, String[] women) {

    }
    // ===================================================
    // Create and Print to File
    // ===================================================

    // ===================================================
    // Main -
    // ===================================================
    // Handles reading from files and calls the gale-
    // shapley and output functions
    // ===================================================
    public static void main(String args[]) throws FileNotFoundException {

        int girlCount = 0;
        int boyCount = 0;

        System.out.println("Limit for Members: 100");
        Scanner boyScanner = new Scanner(new File(args[0]));
        while (boyScanner.hasNextLine()) { // Read while File has next line
            // Read Boys file
            // Debug System.out.println(boy);
            String boy = boyScanner.nextLine();
            if (boyCount == 0) {// First Item
                boyCount = Integer.parseInt(boy);
            } else {
                // fullLine Holds entire Line broken up by space (Man + Preferences)
                String[] fullLine = boy.split("\\s+");
                String[] preferences = new String[100];
                int y = 0;
                for (int x = 1; x < fullLine.length; x++) {
                    // Create a new woman and append to preferences based on list from boy
                    preferences[y] = fullLine[x];
                }
                // Debug
                System.out.println("Man Created: " + fullLine[0]);
                boyList.add(addMan(fullLine[0], preferences));
            }
            if(boyCount > 100){
                System.out.println("ERROR: MORE THAN MAX MEMBERS (100)");
            }
        }
/*
        Scanner girlScanner = new Scanner(new File(args[1]));
        while(girlScanner.hasNextLine()){ //Read while File has next line
            //Read Girls File
            String girl = girlScanner.nextLine();
            //Debug System.out.println(girl);
            girlList[girlCount] = girl; 
            girlCount++;
            if(girlCount > 100){
                System.out.println("ERROR: MORE THAN MAX MEMBERS (100)");
            }
        }
        //Debug  System.out.println("Loading Complete");
        //numBoys = Integer.parseInt(boyList.getFirst());
        //numGirls = Integer.parseInt(girlList[0]);


        //Debug System.out.println("Boys: " + numBoys + "\t" + "Girls: " + numGirls);
        //Start Algorithm with First Man

*/

    }
}