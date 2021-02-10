import java.util.*;

import javax.xml.stream.events.EndElement;

import java.io.*;
//===================================================
//      Assignment 01 - Gale-Shapley
//===================================================
// by: Colby Sawyer- sawyerc17@students.ecu.edu
// B01204512
//===================================================

class Person {
    String name;
    LinkedList<String> preferences;
    Person(String name, LinkedList<String> pref) {
        this.name = name;
        this.preferences = pref;
    }
    LinkedList<String> getPref() {
        return preferences;
    }
}

class Man extends Person {
    Woman fiance;
    Boolean engaged;
    Man(String name, LinkedList<String> pref) {
        super(name,pref);
        this.fiance = null;
        this.engaged= false;
    }
    void getEngaged(Woman w){
        this.fiance = w;
        this.engaged = true;
    }

}
class Woman extends Person {
    Man fiance;
    Boolean engaged;
    Woman(String name, LinkedList<String> pref) {
        super(name, pref);
        this.fiance = null;
        this.engaged = false;
    }

    Man considerProposal(Man m) {
        System.out.println("Engaged:" + engaged);
        System.out.println("Man Asking:" + m.name);
        if(fiance != null){
            System.out.println("Fiance Currently: "+ fiance.name);
        }
        Man fianceStart = this.fiance;

        if (engaged && preferences.contains(m.name) && preferences.indexOf(m.name) < preferences.indexOf(fianceStart.name)) { //Should replace old engagement
            this.fiance = m;
            this.engaged = true;
            m.getEngaged(this);
            System.out.println("Chosen Over: " + fianceStart.name);
            return fianceStart;
        }
        else if(!engaged || fiance == null){ //Single
            this.fiance = m;
            this.engaged = true;
            m.getEngaged(this);
            System.out.println("She was single");
            return m;
        }
        else{
            System.out.println("Better Luck next time");
            return m;
        }
    }
}

public class stable {

    public static LinkedList<String> getMarried(String mName, String wName){
        LinkedList<String> marriage = new LinkedList<String>();
        marriage.add(mName);
        marriage.add(wName);

        return marriage;
    }
    public static Woman findWoman(Woman[] women, String name){
        Woman woman = null;
        for(int x = 0; x < women.length; x++){
            //DEBUGSystem.out.println("WomenArray @: " + women[x].name + " WomanSearch: " + name);
            if(women[x].name.equals(name)){
                //DEBUGSystem.out.println("Its TRUE");
                woman = women[x];
                return woman;
            }
        }
        return woman;
    }

    // ===================================================
    // Perform Gale-Shapley
    // ===================================================
    public static LinkedList<LinkedList<String>> galeShapley(LinkedList<Man> singleMen, Woman[] singleWomen) {
        //While there exists a single man who still has a women to propose to
            //first woman on mans list who he hasnt proposed to
            //if she is single then they become engaged (add to return var)
            //else
                //if she prefers m to another man2 (who she is engaged to)
                    //she becomes engaged to m and man2 goes back to being single
                //else 
                    //she and m2 stay engaged
        LinkedList<LinkedList<String>> marriages = new LinkedList<LinkedList<String>>();

        LinkedList<Man> unmatchedMen = singleMen;
        int index = 0;

        while(!unmatchedMen.isEmpty()){
            Man singleMan = unmatchedMen.pop();
            System.out.println("Man: " + singleMan.name);
            LinkedList<String> pref = singleMan.preferences;
            if(index < pref.size()){
                String w = pref.get(index);
                Woman woman = findWoman(singleWomen, w);
                if(woman != null){
                    Man result = woman.considerProposal(singleMan);

                    if(result != singleMan){ // She said yes
                        //DEBUG 
                        System.out.println("She said yes");
                        unmatchedMen.push(result);
                        //singleMan.getEngaged(woman);
                        unmatchedMen.remove(singleMan);
                        LinkedList<String> marriageLicense = getMarried(result.name, woman.name);
                        System.out.println("Marriage License: " + Arrays.toString(marriageLicense.toArray()));
                        if(marriages.contains(marriageLicense)){ // Marriage Already Exists
                            System.out.println("Found it");
                            marriages.remove(marriageLicense);
                        }else{
                            System.out.println("No previous marriage");
                            marriages.add(marriageLicense);
                        }
                    }else if(result == singleMan && result.engaged){// She was single and said yes
                        System.out.println("She was single and said yes");
                        unmatchedMen.remove(singleMan);
                        LinkedList<String> marriageLicense = getMarried(result.name, woman.name);
                        System.out.println("Marriage License: " + Arrays.toString(marriageLicense.toArray()));
                        marriages.add(marriageLicense);
                    }else{ // She said no
                        //DEBUG 
                        System.out.println("she said no");
                        index++;
                        singleMen.push(singleMan);
                    }
                }else{
                    //DEBUG 
                    System.out.println("Women is null");
                }
         }
        }
        return marriages;

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

        LinkedList<Man> singleMen = new LinkedList<Man>();
        Woman[] singleWomen = new Woman[girlCount];

        //Read Each Man and his preferences
        while(boyScanner.hasNext()){
            String manInfo = boyScanner.nextLine();
                String[] splitInfo = manInfo.split("\\s+");
                LinkedList<String> pref = new LinkedList<String>();
                for(int x = 1; x < splitInfo.length; x++){
                    pref.add(splitInfo[x]);
                }
                Man man = new Man(splitInfo[0], pref);
                singleMen.add(man);
            
        }

        //Read Each Woman and her preferences
        int currentMember = 0;
        while(girlScanner.hasNextLine()){
            String womanInfo = girlScanner.nextLine();
                String[] splitInfo = womanInfo.split("\\s+");
                if(splitInfo.length > 1){
                    LinkedList<String> pref = new LinkedList<String>();
                    for(int x = 1; x < splitInfo.length; x++){
                        pref.add(splitInfo[x]);
                    }
                    //DEBUG System.out.println("Girl Added: " + splitInfo[0]);
                    Woman woman = new Woman(splitInfo[0], pref);
                    if(currentMember < girlCount){
                        singleWomen[currentMember] = woman;
                        //DEBUG System.out.println("Women Added: " + singleWomen[currentMember].name);
                        currentMember++;
                    }
                } 
        }


        //Pass List of Single Men and Women to Gale-Shapley
        LinkedList<LinkedList<String>> marriages = galeShapley(singleMen,singleWomen);

        //File Output Handling
        //File Creation
        try{
            File output = new File(args[2] + ".txt");
            if (output.createNewFile()) {
                //DEBUG System.out.println("File created: " + output.getName());
                try {
                    FileWriter outpuWriter = new FileWriter(args[2] + ".txt");
                    outpuWriter.write(Arrays.toString(marriages.toArray()) + "END OF FILE");
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
