import java.util.Arrays;
import java.util.Random;
public class GeneticAlorithm {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        //TODO move some variables to be in args if you want
        int durationToBeTested = 300;
        int radius = 1;
        boolean[][] defaultBoard;
        int ruleSize = (int)Math.pow(2,((Math.pow((1+2*radius),2)-1)));
        Rule[] rules = new Rule[100];
        rules = generateInitial(rules,ruleSize);
        defaultBoard = BoardHandler.getBoard("10x10Good");

        for (int i = 0; i < rules.length; i++) {
            RunCA CA = new RunCA(radius,durationToBeTested,defaultBoard,rules[i].getMyRule(),(i+" "));
            System.out.println(Arrays.toString(rules[i].getMyRule()));
            CA.start();
        }
        long end = System.currentTimeMillis();
        System.out.println("Time Elapsed: " + (end - start));
    }




    private static Rule[] generateInitial(Rule[] rules,int ruleSize){
        Random coinFlip = new Random();
        Rule[] newRules = new Rule[rules.length];
        for (int i = 0; i < rules.length; i++) {
            Rule newRule = new Rule(new boolean[ruleSize]);
            for (int j = 0; j < ruleSize; j++) {
                newRule.getMyRule()[j] = coinFlip.nextBoolean();
            }

            boolean notprovenUnique = true;
            while (notprovenUnique) {
                for (Rule rule : rules) {
                    if(rule != null) {
                        if (Arrays.equals(rule.getMyRule(), newRule.getMyRule())) {
                            for (int k = 0; k < ruleSize; k++) {
                                newRule.getMyRule()[k] = coinFlip.nextBoolean();
                            }
                            break;
                        }
                    }
                }
                notprovenUnique = false;
            }
            newRules[i] = newRule;
        }
        return newRules;
    }
//Pretty sure this wasn't needed but leaving it just in case
//    /**
//     * Checks if the two boolean arrays in a pair of rules is the same used for rule generation
//     * @param ruleOne The rule already in the rule-set
//     * @param ruleTwo The rule being check to be added --- Note: the order of these two parameters does not matter
//     * @return True: The Rules are different / False: The pair of rules is identical
//     */
//    private boolean checkIfAlreadyUsed(Rule ruleOne, Rule ruleTwo){
//        boolean[] ruleSetOne = ruleOne.getMyRule();
//        boolean[] ruleSetTwo = ruleTwo.getMyRule();
//        for (int i = 0; i < ruleSetOne.length; i++) {
//            if (ruleSetOne[i] != ruleSetTwo[i]){
//                return true;
//            }
//        }
//        return false;
//    }
}
