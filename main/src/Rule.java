public class Rule implements Cloneable{
    private boolean[] myRule; // The rule-set that will be applied to the CA
    private int fitness; //The fitness score that this rule-set earned
    private final int ID;// The ID number for this rule-set

    public Rule(boolean[] rule, int ID){
        this.myRule = rule;
        this.ID = ID;
    }
    public void setFitness(int fitness){
        this.fitness = fitness;
    }
    public void setMyRule(boolean[] newRule){
        this.myRule = newRule;
    }

    public int getFitness(){
        return this.fitness;
    }

    public boolean[] getMyRule(){
        return this.myRule;
    }
    public int getID(){return this.ID;}

    @Override
    public Rule clone() {
        try {
            Rule clone = (Rule) super.clone();
            clone.setMyRule(this.myRule);
            clone.setFitness(this.getFitness());
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
