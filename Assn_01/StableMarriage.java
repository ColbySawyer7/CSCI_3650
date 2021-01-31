import java.util.*;
public class StableMarriage {
    public static void main(String[] args) {
        System.out.print("How many pairs would you like to match up? ");
        Scanner input = new Scanner (System.in);
        int NUMBER_OF_COUPLES = input.nextInt();                
        System.out.println();
        LinkedList eligibleMen = createEligibleMen(NUMBER_OF_COUPLES);                
        System.out.println();LinkedList eligibleWomen =createEligibleWomen(NUMBER_OF_COUPLES);        
        System.out.println();createPreferences(eligibleMen, eligibleWomen);
        createPreferences(eligibleWomen, eligibleMen);
        SocialRegister sr = new SocialRegister(eligibleMen,eligibleWomen);
        System.out.println(sr);
        while (sr.eligibleMenExist()) {
            sr.getFirstEligible().makeProposal();
            System.out.println(sr);
        }
    }
    
    static LinkedList createEligibleMen(int number) {
        System.out.println("What are the names of the men? (Press Enter to submit a name)");
        LinkedList men = new LinkedList();
        Scanner input = new Scanner (System.in);
        for (int i = 1; i <= number; i++)men.add(new Man(input.next()));
        return men;
    }
        static LinkedList createEligibleWomen(int number) {
            System.out.println("What are the names of the women? (Press Enter to submit aname)");
            LinkedList women = new LinkedList();
            Scanner input = new Scanner (System.in);
            for (int i = 1; i <= number; i++)women.add(new Woman(input.next()));
            return women;
        }
        static void createPreferences(LinkedList a, LinkedList b) {Iterator it = a.listIterator();

    while (it.hasNext()) {
        Person p = (Person) it.next();
        Rankings r = p.getRankings();
        r.addAll(b);
        System.out.println("Whatare the preferences of " + p.name + "? (Input numericposition of persons)");
        Scanner input = new Scanner (System.in);
        int n = r.size();
        for (int i = 0; i < n; i++) {
            int loc = input.nextInt() -1;
            r.add(r.get(loc)); 
            // put it at the back
        }
        for (int i = 0; i < n; i++)r.removeFirst();
    }
}
}

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