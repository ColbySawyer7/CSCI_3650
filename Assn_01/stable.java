import java.util.*;
import java.io.*;
//===================================================
//      Assignment 01 - Gale-Shapley
//===================================================
// by: Colby Sawyer- sawyerc17@students.ecu.edu
// B01204512
//===================================================
public class stable {

//LinkedLists to store member information
    private static LinkedList<String> boyList = new LinkedList<String>();
    private static String[] girlList = new String[100];

//Find Number of Members
    private static int numBoys;
    private static int numGirls;

    private static LinkedList<String> unmatchedMem;
    private static String[] marriage;

//===================================================
//      Perform Gale-Shapley
//===================================================
    public static void wedding(){
        
    }
//===================================================
//      Perform Gale-Shapley
//===================================================
    public static void galeShapley(String man, String[] manPreference){
        System.out.println("Man: " + man);
    }
//===================================================
//      Create and Print to File
//===================================================


//===================================================
//      Main -
//===================================================
// Handles reading from files and calls the gale-
// shapley and output functions
//===================================================
    public static void main(String args[]) throws FileNotFoundException{

        int girlCount = 0;
        int boyCount = 0;

        System.out.println("Limit for Members: 100");
        Scanner boyScanner = new Scanner(new File(args[0]));
        while(boyScanner.hasNextLine()){ //Read while File has next line
            //Read Boys file
            String boy = boyScanner.nextLine();
            //Debug System.out.println(boy);
            boyList.add(boy);
            boyCount++;
            if(boyCount > 100){
                System.out.println("ERROR: MORE THAN MAX MEMBERS (100)");
            }
        }

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
        numBoys = Integer.parseInt(boyList.getFirst());
        numGirls = Integer.parseInt(girlList[0]);


        //Debug System.out.println("Boys: " + numBoys + "\t" + "Girls: " + numGirls);
        //Start Algorithm with First Man

        String[] mansPreference = boyList.getFirst().split("\\s+");
        LinkedList<String> stack = new LinkedList<String>();
        
        String man = stack.pop();

        while(stack.length > 0){
            //Perform Algorithm
            galeShapley(man,mansPreference);
        }
    }
}