import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class GeneticAlorithm {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        //TODO move some variables to be in args if you want
        int durationToBeTested = 300;
        int generationsToBeTested = 3;
        int radius = 1;
        boolean[][] defaultBoard;
        int ruleSize = (int)Math.pow(2,((Math.pow((1+2*radius),2)-1)));
        boolean boardDom; // The dominant value in the board -- True for mostly Ones / False for mostly zeros
        Rule[] rules = new Rule[100];
       /// int[] fitnessScores = new int[rules.length];
        Rule[] leaderBoard = new Rule[50];
        //IDs is the rule ID counterit should be updated after every rule generation
        int IDs = 0;


        rules = generateInitial(rules,ruleSize,IDs,leaderBoard);
        IDs += rules.length;

        defaultBoard = BoardHandler.getBoard("10x10Good");
        boardDom = BoardHandler.getDominant(defaultBoard);
        //System.out.println(boardDom + " boardDom");

        for (int i = 0; i < rules.length; i++) {
            RunCA CA = new RunCA(radius,durationToBeTested,defaultBoard,rules[i].getMyRule(),rules[i].getID()+"");
////            System.out.println(Arrays.toString(rules[i].getMyRule()));
            CA.start();
        }

        try {
            Thread.sleep(500);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < rules.length; i++) {

            rules[i].setFitness(getFitness(i+""));
//            System.out.println(rules[i].getFitness() + " fitness of " + i);
        }

        leaderBoard = leaderBoard(rules,leaderBoard,boardDom);
//        for (int i = 0; i < leaderBoard.length; i++) {
//            System.out.println("Ho");
//            System.out.println("Leader at pos: "+ i + " has ID " + leaderBoard[i].getID() + " and fitness " +
//                    leaderBoard[i].getFitness());
//        }


        for (int j = 0; j < generationsToBeTested; j++) {
            System.out.println("Heyyooo");
            System.out.println(j);
            System.out.println(generationsToBeTested);

            for (int i = 0; i < leaderBoard.length; i++) {
                rules[i] = mutate(leaderBoard[i], IDs);

                IDs++;
                boolean notProvenDiff = true;
                Rule secMut = mutate(leaderBoard[i], IDs);

                while (notProvenDiff) {
                    if (Arrays.equals(rules[i].getMyRule(), secMut.getMyRule())) {
                        secMut = mutate(leaderBoard[i], IDs);
                    } else {
                        notProvenDiff = false;
                    }
                }
                rules[rules.length - 1 - i] = secMut;
                IDs++;
                RunCA newGen = new RunCA(radius, durationToBeTested, defaultBoard, rules[i].getMyRule(), rules[i].getID() + "");
                newGen.start();
                newGen = new RunCA(radius, durationToBeTested, defaultBoard, rules[rules.length - 1 - i].getMyRule(),
                        rules[rules.length - 1 - i].getID() + "");
                newGen.start();

            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < rules.length; i++) {

                rules[i].setFitness(getFitness(i + ""));
//            System.out.println(rules[i].getFitness() + " fitness of " + i);
            }

            leaderBoard = leaderBoard(rules, leaderBoard, boardDom);
        }
        for (int i = 0; i < leaderBoard.length; i++) {
            System.out.println("ho");
            System.out.println("Leader at pos: " + i + " has ID " + leaderBoard[i].getID() + " and fitness " +
                    leaderBoard[i].getFitness());
        }



        try{
            FileWriter out = new FileWriter(new File("Leaderboard.txt"));
            for (int i = 0; i < leaderBoard.length; i++) {
                out.write("Leaderboard Position: " + (i+1) + " is rule: "+ leaderBoard[i].getID() + " with fitness "
                        + leaderBoard[i].getFitness() + "\n") ;
                out.write("Rule-set is " + Arrays.toString(leaderBoard[i].getMyRule()) + "\n\n");

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("Time Elapsed: " + (end - start));
    }


    public static Rule mutate(Rule parent,int ID){
        Random randomIndex = new Random();
        int index = randomIndex.nextInt(parent.getMyRule().length);
        boolean[] parentsRule = parent.getMyRule().clone();
        parentsRule[index] = !parentsRule[index];
        return new Rule(parentsRule,ID);
    }


    /**
     * This function fills and updates the leaderboard, it can be used bot if the leaderBoard is empty or full
     * @param newGeneration The new set of rules to be examined for a place on the leaderboard
     * @param currentLeaders The current leaderboard that may be updated in this function
     * @return The new updated leaderboard
     */
    private static Rule[] leaderBoard(Rule[] newGeneration, Rule[] currentLeaders,boolean boardDom){
        if (currentLeaders[0] == null){
            for (int i = 0; i < currentLeaders.length; i++) {
                currentLeaders[i] = newGeneration[i];
            }
        }


        for (Rule rule:newGeneration) {
            if(correctOutcome(boardDom,rule.getID() + "") == 1){
                rule.setFitness((rule.getFitness()+100));
            }
            for (int i = 0; i < currentLeaders.length; i++) {
                if(rule.getFitness() > currentLeaders[i].getFitness()){
//                    System.out.println(rule.getFitness() +" "+ rule.getID() + " newgen and currentGen "+
//                            currentLeaders[i].getFitness()+ " " + currentLeaders[i].getID());

                    //
                    System.arraycopy(currentLeaders,i,currentLeaders,i+1,currentLeaders.length-i-1);
                    currentLeaders[i] = rule;

                    break;

                }
            }
        }

//        for (int i = 0; i < currentLeaders.length; i++) {
//            System.out.println("LeaderBoard position " + i + " fitness: " + currentLeaders[i].getFitness() +
//                    " RuleID: " + currentLeaders[i].getID());
//        }
        return currentLeaders;
    }
    private static Rule[] generateInitial(Rule[] rules,int ruleSize,int IDStart,Rule[] leaderBoard){
        Random coinFlip = new Random();
        Rule[] newRules = new Rule[rules.length];

        for (int i = 0; i < rules.length; i++) {
            Rule newRule = new Rule(new boolean[ruleSize],IDStart);
            for (int j = 0; j < ruleSize; j++) {
                newRule.getMyRule()[j] = coinFlip.nextBoolean();
            }

            boolean notprovenUnique = true;
            while (notprovenUnique) {
                for (Rule rule : rules) {
                    if (rule != null) {
                        if (Arrays.equals(rule.getMyRule(), newRule.getMyRule())) {
                            for (int k = 0; k < ruleSize; k++) {
                                newRule.getMyRule()[k] = coinFlip.nextBoolean();
                            }
                            break;
                        }
                    }
                }
                if(leaderBoard[0] != null) {
                    for (Rule rule : leaderBoard) {
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
            IDStart++;
        }
        return newRules;
    }

    /**
     * This function reads a fitness value from a CA output as standardized in BoardHandler.exportBoard
     * @param name The name of the CA output file to be examined
     * @return the int value of the fitness score
     */
    private static int getFitness(String name){
        try {
            Scanner scanner = new Scanner(new File("C:\\Users\\nicob\\IdeaProjects\\CASProject3\\CATables\\"
                    +name + ".txt"));
            String line;
            String[] row;
            while(scanner.hasNextLine()){
                line = scanner.nextLine();
                row = line.split(" ");
                if (row[0].equals("Fitness:")){
                    return Integer.parseInt(row[1]);
                }
            }
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * This function reads a CA output file and standardized in BoardHandler.exportBoard and reads the outcome of the board
     * @param boardDom The dominant value in the boards initial state
     * @param fileName The name of the CA output file to examine
     * @return outputs are:
     * 1= The CA filled the board with the initial dominant value
     * 2= The CA filled the board but not with the initial dominant value
     * -1= The CA did not fill the board or an error occurred
     */
    private static int correctOutcome(boolean boardDom, String fileName){
        try {
            Scanner scanner = new Scanner(new File("C:\\Users\\nicob\\IdeaProjects\\CASProject3\\CATables\\"
                    + fileName + ".txt"));
            String line;
            String[] row;
            while (scanner.hasNextLine()){
                line = scanner.nextLine();
                row = line.split(" ");
                if(row[0].equals("Did")){
                    if(row[3].equals("Yes")){
                        if((row[4].equals("Ones") && boardDom) || (row[4].equals("Zeros") && !boardDom)){
                            return 1;
                        }else {
                            return 0;
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
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
