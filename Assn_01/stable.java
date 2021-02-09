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
        Boolean engaged;
        Person(String name, String[] preferences, Boolean engaged) {
            this.name = name;
            this.preferences = preferences;
            this.engaged = false;
        }
        String[] getPref() {
            return preferences;
        }
        public String toString() {
            return name;
        }
        Boolean isEngaged(){
            return engaged;
        }
        void proposal(){
            this.engaged = true;
        }
        void endEngagement(){
            this.engaged = false;
        }
    }
    
    class Man extends Person {
        Woman engagedTo;
        Man(String name, String[] preferences) {
            super(name, preferences, false);
            this.engagedTo = null;
        }
        void getEngaged(Woman woman){
            this.engagedTo = woman;
            this.engaged = true;
        }
    }
    class Woman extends Person {
        Man engagedTo;
        Woman(String name, String[] preferences) {
            super(name, preferences, false);
            this.engagedTo = null;
        }
        void getEngaged(Man man){
            this.engagedTo = man;
            this.engaged = true;
        }
    }

  public static LinkedList<Man> addMen(Scanner boyScanner, int girlCount){
        LinkedList<Man> men = new LinkedList<Man>();
        String manName;
        String[] manPref;
        while(boyScanner.hasNextLine()){//Retrieve Each Man and Preferences
            manName = boyScanner.next();
            manPref = new String[girlCount];
            System.out.println("Man Added: " + manName);
            for(int x = 0; x < girlCount; x++){
                manPref[x] = boyScanner.next();
                //DEBUG System.out.println("Woman Added:" + manPref[x]);
            }
            Man man = new Man(manName,manPref);
            men.add(man);
        }
        return men;
    }

    public static Woman[] addWoman(Scanner girlScanner, int boyCount, int girlCount){
        Woman[] women = new Woman[girlCount];
        String womanName;
        String[] womanPref;
        int womenCount = 0;
        while(girlScanner.hasNextLine()){//Retrieve Each Woman and Preferences
            womanName = girlScanner.next();
            womanPref = new String[boyCount];
            //DEBUG System.out.println("Woman Added: " + womanName);
            for(int x = 0; x < girlCount; x++){
                womanPref[x] = girlScanner.next();
                //DEBUG System.out.println("Man Added:" + womanPref[x]);
            }
            women[womenCount] = new Woman(womanName, womanPref);
            womenCount++;
        }
        return women;
    }
    // ===================================================
    // Perform Gale-Shapley
    // ===================================================
    public static LinkedList<String> galeShapley(LinkedList<Man> singleMen, Woman[] singleWomen) {
        //While there exists a single man who still has a women to propose to
            //first woman on mans list who he hasnt proposed to
            //if she is single then they become engaged (add to return var)
            //else
                //if she prefers m to another man2 (who she is engaged to)
                    //she becomes engaged to m and man2 goes back to being single
                //else 
                    //she and m2 stay engaged
        LinkedList<String> pairs = new LinkedList<String>();;

        while(!singleMen.isEmpty()){
            Man manSearching = singleMen.getFirst();
            String[] manPref = manSearching.getPref();
            for(int x = 0; x < manPref.length; x++){
                String womanName = manPref[x];
                Woman woman = singleWomen[Arrays.asList(singleWomen).indexOf(womanName.toString())];
                if(!woman.isEngaged()){
                    pairs.add(manSearching.name + " " + womanName);
                }
                else{
                    String[] womanPref = woman.getPref();
                    final int newMan = Arrays.asList(womanPref).indexOf(manSearching.name);
                    Man currentMan = woman.engagedTo;
                    final int oldMan = Arrays.asList(womanPref).indexOf(currentMan.name);
                    if(newMan < oldMan){
                        pairs.add(manSearching.name + " " + womanName);
                        manSearching.getEngaged(woman);
                        woman.getEngaged(manSearching);

                        singleMen.remove(manSearching);
                        singleMen.push(currentMan);
                    }
                }
            }
        }
        return pairs;
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

        Scanner boyScanner = new Scanner(new File(args[0]));
        Scanner girlScanner = new Scanner(new File(args[1]));

        //Retrieve Size of Lists
        boyCount = boyScanner.nextInt();
        girlCount = girlScanner.nextInt();

        LinkedList<Man> unMatchedMen = new LinkedList<Man>();
        Woman[] unMatchedWomen = new Woman[girlCount];

        //DEBUG System.out.println("Boys:" + boyCount + "\tGirls: " + girlCount);
        
        //unMatchedMen = addMen(boyScanner, girlCount);
        //unMatchedWomen = addWoman(girlScanner, boyCount, girlCount);
        //DEBUG System.out.println("Stable Pairs");

        LinkedList<String> marriages = galeShapley(unMatchedMen, unMatchedWomen);

        marriages.forEach(System.out::println);
        System.out.println();

        //File Output Handling
        //File Creation
        try{
            File output = new File(args[2] + ".txt");
            if (output.createNewFile()) {
                //DEBUG System.out.println("File created: " + output.getName());
                try {
                    FileWriter outpuWriter = new FileWriter(args[2] + ".txt");
                    outpuWriter.write(marriages.toString() + "END OF FILE");
                    outpuWriter.close();
                    //DEBUG System.out.println("Successfully wrote to the file.");
                  } catch (IOException e) {
                    System.out.println("ERROR: An error occurred.");
                    e.printStackTrace();
                  }
            } else {
                System.out.println("ERROR: File already exists.");
            }
        } catch (IOException e) {
            System.out.println("ERROR: An error occurred.");
            e.printStackTrace();
        }
    }
}
