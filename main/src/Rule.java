public class Rule {
    private boolean[] myRule;
    private int fitness;

    public Rule(boolean[] rule){
        this.myRule = rule;
    }
    public void setFitness(int fitness){
        if (this.fitness == 0){
            this.fitness = fitness;
        }
    }

    public int getFitness(){
        return this.fitness;
    }

    public boolean[] getMyRule(){
        return this.myRule;
    }

}
