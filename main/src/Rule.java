public class Rule {
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

    public int getFitness(){
        return this.fitness;
    }

    public boolean[] getMyRule(){
        return this.myRule;
    }
    public int getID(){return this.ID;}

}
