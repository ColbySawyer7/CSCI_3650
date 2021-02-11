import java.util.*;
import java.io.*;
//====================================================================================
//      Assignment 01 - Gale-Shapley
//====================================================================================
// by: Colby Sawyer- sawyerc17@students.ecu.edu
// B01204512
//====================================================================================
// ====================================================================================
// Person Class
// ====================================================================================
// Person Class is the parent class used by the Man
//      and Woman class (includes constructor)
// ====================================================================================
class Person {
    String name;
    LinkedList<String> preferences;
    Person(String name, LinkedList<String> pref) {
        this.name = name;
        this.preferences = pref;
    }
    // ====================================================================================
    // getPref
    // ====================================================================================
    // getter function for Preferences LinkedList<String>
    // ====================================================================================
    LinkedList<String> getPref() {
        return preferences;
    }
}

// ====================================================================================
// Man Class
// ====================================================================================
// Man class that holds a man's fiance (Woman),
//      engaged (boolean True:Engaged); Person super 
//      class
// ====================================================================================
class Man extends Person {
    Woman fiance;
    Boolean engaged;
    Man(String name, LinkedList<String> pref) {
        super(name,pref);
        this.fiance = null;
        this.engaged= false;
    }
    // ====================================================================================
    // getEngaged
    // ====================================================================================
    // getter for engaged status
    // ====================================================================================
    void getEngaged(Woman w){
        this.fiance = w;
        this.engaged = true;
    }
    // ====================================================================================
    // breakIfOff sets the correct values for when a Man
    //      has his previous engagement ended 
    // ====================================================================================
    void breakItOff(){
        this.fiance = null;
        this.engaged = false;
    }

}

// ====================================================================================
// Woman Class
// ====================================================================================
// Man class that holds a woman's fiance (Man),
//      engaged (boolean True:Engaged); Person super 
//      class
// ====================================================================================
class Woman extends Person {
    Man fiance;
    Boolean engaged;
    Woman(String name, LinkedList<String> pref) {
        super(name, pref);
        this.fiance = null;
        this.engaged = false;
    }

    // ====================================================================================
    // considerProposal
    // ====================================================================================
    // considerProposal takes a input m that represents
    //      a man who is proposing to the woman(this). This func
    //      handles all of the operations for determining
    //      if man m is a better suitor than the current
    //      fiance (if there is one)
    //  Returns:
    //      Man (with appropriate engagement status)
    //          Fiance is returned as the Man when he is    
    //          replaced (allows for him to be resuited).
    // ====================================================================================
    Man considerProposal(Man m) {
        //debug System.out.println("Engaged:" + engaged);
        //debug System.out.println("Man Asking:" + m.name);
        if(fiance != null){
            //debug System.out.println("Fiance Currently: "+ fiance.name);
        }
        Man fianceStart = this.fiance;

        if (engaged && preferences.contains(m.name) && preferences.indexOf(m.name) < preferences.indexOf(fianceStart.name)) { //Should replace old engagement
            this.fiance = m;
            this.engaged = true;
            m.getEngaged(this);
            //debug System.out.println("Chosen Over: " + fianceStart.name);
            fianceStart.breakItOff();
            return fianceStart;
        }
        else if(!engaged || fiance == null){ //Single
            this.fiance = m;
            this.engaged = true;
            m.getEngaged(this);
            //debug System.out.println("She was single");
            return m;
        }
        else{
            //debug System.out.println("Better Luck next time");
            return m;
        }
    
    }

}
// ====================================================================================
//  Stable Class
// ====================================================================================
//  contains the Main method and much of the work 
// ====================================================================================
    public class stable {

    // ====================================================================================
    // getMarried
    // ====================================================================================
    // getMarried creates a marraige record (LinkedList<String>) for a couple based on their
    //      names (mName == Man; wname == Woman)
    // Returns:
    //      LinkedList<String> that represents the marraigeRecord "[mname,wname]""
    // ====================================================================================
    public static LinkedList<String> getMarried(String mName, String wName){
        LinkedList<String> marriage = new LinkedList<String>();
        marriage.add(mName);
        marriage.add(wName);

        return marriage;
    }
    
    // ====================================================================================
    // findWoman
    // ====================================================================================
    //  searchs an array of Woman objects base on the name attribute and returns 
    //       the object when found
    // Returns:
    //      Woman object whos name corresponds with the name field
    //      NULL is returned if no woman is found.
    // ====================================================================================
    public static Woman findWoman(Woman[] women, String name){
        Woman woman = null;
        for(int x = 0; x < women.length; x++){
            //DEBUG System.out.println("WomenArray @: " + women[x].name + " WomanSearch: " + name);
            if(women[x].name.equals(name)){
                //DEBUG System.out.println("Its TRUE");
                woman = women[x];
                return woman;
            }
        }
        return woman;
    }

    // =====================================================================================================================
    // Gale-Shapley
    // =====================================================================================================================
    //  performs the Gale-Shapley algorithm on the two 
    //      strucs singleMen and singleWomen respectivley.
    //      Utilizes helper function considerProposal to 
    //      check on a singleWomen's availability. 
    //  Returns:
    //      LinkedList<LinkedList<String>> marriages marriages contains a record of each marriage
    //      represented by a LinkedList<String>. These marriageLicense (referred to as such) are 
    //      created using the helper function getMarried. This makes sure every record is kept in the 
    //      same formatting
    // =====================================================================================================================

    public static LinkedList<LinkedList<String>> galeShapley(LinkedList<Man> singleMen, Woman[] singleWomen) {
        LinkedList<LinkedList<String>> marriages = new LinkedList<LinkedList<String>>();

        LinkedList<Man> unmatchedMen = singleMen;
        int index = 0;

        while(!unmatchedMen.isEmpty()){
            Man singleMan = unmatchedMen.pop();
            //debug System.out.println("Man: " + singleMan.name);
            LinkedList<String> pref = singleMan.preferences;
            if(index < pref.size()){
                String w = pref.get(index);
                Woman woman = findWoman(singleWomen, w);
                if(woman != null){
                    Man result = woman.considerProposal(singleMan);

                    if(result != singleMan){ // She said yes
                        //DEBUG System.out.println("She said yes");
                        unmatchedMen.push(result);
                        //singleMan.getEngaged(woman);
                        unmatchedMen.remove(singleMan);
                        LinkedList<String> marriageLicense = getMarried(result.name, woman.name);
                        //debug System.out.println("Old Marriage License: " + Arrays.toString(marriageLicense.toArray()));
                        if(marriages.contains(marriageLicense)){ // Marriage Already Exists
                            //debug System.out.println("Found it");
                            marriages.remove(marriageLicense);
                            marriages.add(getMarried(singleMan.name, woman.name));
                        }else{
                            //debug System.out.println("No previous marriage");
                            marriages.add(getMarried(singleMan.name,woman.name));
                        }
                    }else if(result == singleMan && result.engaged){// She was single and said yes
                        //DEBUG System.out.println("Test" + result.name);
                        //DEBUG System.out.println("She was single and said yes");
                        unmatchedMen.remove(singleMan);
                        LinkedList<String> marriageLicense = getMarried(result.name, woman.name);
                        //debug System.out.println(" New Marriage License: " + Arrays.toString(marriageLicense.toArray()));
                        marriages.add(marriageLicense);
                    }else{ // She said no
                        //DEBUG System.out.println("she said no");
                        index++;
                        unmatchedMen.push(singleMan);
                    }
                }else{
                    //DEBUG System.out.println("Women is null");
                }
         }
        }
        return marriages;

    }

    // ====================================================================================
    // Main 
    // ====================================================================================
    // Handles reading from files and calls the gale-
    // shapley function before outputing to a file
    // ====================================================================================
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
                    marriages.forEach((temp) -> {
                        try{
                            outpuWriter.write(temp.toString() + "\n");
                        }catch(IOException e){
                            e.printStackTrace();
                        }
                    });
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
