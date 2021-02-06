import java.util.*;
import java.io.*;

class SocialRegister {
    public static SocialRegister defaultRegister; 
    // singleton pattern
    LinkedList eligibleMen, women;
    HashMap engagements; 
    // maps Women to Men
    SocialRegister(LinkedList eligibleMen, LinkedList eligibleWomen) {
        defaultRegister = this;
        this.eligibleMen = eligibleMen;
        women = eligibleWomen;
        engagements = new HashMap();
    }
    
    boolean eligibleMenExist() {
        return !eligibleMen.isEmpty();
    }
    
    Man getFirstEligible() {
        return (Man) eligibleMen.get(0);
    }
    
    void createEngagement(Woman w, Man m) {
        if (engagements.containsKey(w))eligibleMen.add(engagements.get(w));
        engagements.put(w,m);eligibleMen.remove(m);
    }
    
    public String toString() {
        String result = "";
        if (eligibleMenExist()) {result += "\n ELIGIBLE MEN: \n" + showList(eligibleMen);
        result += " WOMEN: \n" + showList(women);result += " ENGAGEMENTS: \n";
    } else {
        result += " MARRIAGES: \n";
    }
    result += showEngagements();
    return result;
    }
    String showList(LinkedList eligible) {
        String result = "";
        Iterator it = eligible.listIterator();
        while (it.hasNext()) {
            Person p = (Person) it.next();
            result += "    " + p + ":" + p.getRankings() + "\n";
        }
        return result;
    }
    
    String showEngagements() {
        String result = "";
        Set couples = engagements.entrySet();
         // get the set of couples
         Iterator it = couples.iterator();
         while (it.hasNext()) {
             Map.Entry couple = (Map.Entry)it.next();
             result += "   (" + couple.getKey() + "," + couple.getValue() + ")\n";
            }
            return result;
        }
    }
    
    class Rankings extends LinkedList {public void trim(Object x) {// removes x and all elements after it
        Iterator it = listIterator();
        boolean found = false;
        while (!found)
        if (it.next() == x) {
            found = true;
            it.remove();
        }
        while (it.hasNext()) {
            it.next();it.remove();
        }
    }
    
    public String toString() {
        String result = "";
        Iterator it = listIterator();
        while (it.hasNext())result += " " + it.next();
        return result;
    }
}

class Person {
    String name;
    Rankings preferences;
    Person(String name) {
        this.name = name;
        preferences = new Rankings();
    }
    Rankings getRankings() {
        return preferences;
    }
    
    public String toString() {
        return name;
    }
}

class Man extends Person {
    Man(String name) {
        super(name);
    }
    
    void makeProposal() {
        ((Woman) preferences.removeFirst()).considerProposal(this);
    }
}
class Woman extends Person {
    Woman(String name) {super(name);
    }
    
    void considerProposal(Man m) {
        if (preferences.contains(m)) {
            SocialRegister.defaultRegister.createEngagement(this,m);
            preferences.trim(m);
        }
    }
}

public class stableSol {

    private int numCouples = 0;
    private static LinkedList eligibleMen;
    private static LinkedList eligibleWomen;

    static LinkedList createEligibleMen(int number, Scanner input) {
        LinkedList men = new LinkedList();
        for (int i = 1; i <= number; i++)
        men.add(new Man(input.next()));
        return men;
    }

    static LinkedList createEligibleWomen(int number, Scanner input) {
        LinkedList women = new LinkedList();
        for (int i = 1; i <= number; i++)women.add(new Woman(input.next()));
        return women;
    }

    static void createPreferences(LinkedList a, LinkedList b, Scanner input) {
        Iterator it = a.listIterator();
        while (it.hasNext()) {
            Person p = (Person) it.next();
            Rankings r = p.getRankings();
            r.addAll(b);
            System.out.println("Whatare the preferences of " + p.name + "? (Input numericposition of persons)");
            int n = r.size();
            for (int i = 0; i < n; i++) {
                int loc = input.nextInt() -1;
                r.add(r.get(loc)); 
                // put it at the back
            }
            for (int i = 0; i < n; i++)r.removeFirst();
        }
    }  

    public static void main(String[] args) throws FileNotFoundException{
        System.out.print("Welcome to the Gale-Shapley Machine\n");
        Scanner inputMen = new Scanner (new File(args[0]));
        Scanner inputWomen = new Scanner (new File(args[1]));

        int numCouples = inputMen.nextInt();                

        System.out.println("# Couples: " + numCouples);

        eligibleMen = createEligibleMen(numCouples, inputMen);                
        eligibleWomen = createEligibleWomen(numCouples, inputWomen);        

        createPreferences(eligibleMen, eligibleWomen, inputMen);
        createPreferences(eligibleWomen, eligibleMen, inputWomen);

        SocialRegister sr = new SocialRegister(eligibleMen,eligibleWomen);
        System.out.println(sr);
        while (sr.eligibleMenExist()) {
            sr.getFirstEligible().makeProposal();
            System.out.println(sr);
        }
    }
}
