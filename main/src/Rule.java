public class Rule implements Cloneable{
    private boolean[] myRule;
    private int fitness;
    private final int ID;

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
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            clone.setMyRule(this.myRule);
            clone.setFitness(this.getFitness());
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
