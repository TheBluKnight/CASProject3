/**
 * Genetic
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.prefs.BackingStoreException;

public class GeneticAlorithm {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        //TODO move some variables to be in args if you want
        int durationToBeTested = 100;
        int generationsToBeTested = 300;
        int radius = 1;
        boolean[][] defaultBoard;
        int ruleSize = (int)Math.pow(2,((Math.pow((1+2*radius),2)-1)));
        //boolean boardDom; // The dominant value in the board -- True for mostly Ones / False for mostly zeros
        Rule[] rules = new Rule[100];
        Rule[] leaderBoard = new Rule[100];
        //IDs is the rule ID counter, it should be updated after every rule generation
        int IDs = 0;


        rules = generateInitial(rules,ruleSize,IDs,leaderBoard);
        IDs += rules.length;

        defaultBoard = BoardHandler.getBoard("First100");

        for (int i = 0; i < rules.length; i++) {
            RunCA CA = new RunCA(radius,durationToBeTested,defaultBoard,rules[i].getMyRule(),rules[i].getID()+"-0");
            CA.start();
            CA = new RunCA(radius,durationToBeTested,BoardHandler.getBoard("100x100Ones"),rules[i].getMyRule(),rules[i].getID()+"-1");
            CA.start();
        }

        try {
            Thread.sleep(500);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < rules.length; i++) {

            //rules[i].setFitness(getFitness(i+""));
            rules[i].setFitness(getFitness(i+""));
        }

        leaderBoard(rules, leaderBoard);


        for (int j = 0; j < generationsToBeTested; j++) {

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
                RunCA newGen = new RunCA(radius, durationToBeTested, defaultBoard, rules[i].getMyRule(), rules[i].getID() + "-0");
                newGen.start();

                newGen = new RunCA(radius, durationToBeTested,BoardHandler.getBoard("100x100Ones"), rules[i].getMyRule(), rules[i].getID() + "-1");
                newGen.start();

                newGen = new RunCA(radius, durationToBeTested, defaultBoard, rules[rules.length - 1 - i].getMyRule(),
                        rules[rules.length - 1 - i].getID() + "-0");
                newGen.start();
                newGen = new RunCA(radius, durationToBeTested,BoardHandler.getBoard("100x100Ones"), rules[rules.length - 1 - i].getMyRule(),
                        rules[rules.length - 1 - i].getID() + "-1");
                newGen.start();

            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < rules.length; i++) {

                rules[i].setFitness(getFitness(i + ""));
            }
            for (Rule rule : leaderBoard) {
                rule.setFitness(getFitness("" + rule.getID()));
            }
            leaderBoard(rules, leaderBoard);
        }


//        for (Rule rule : leaderBoard) {
//            rule.setFitness(getFitness("" + rule.getID()));
//        }
//        for (Rule rule:leaderBoard) {
//            RunCA verifying = new RunCA(radius,durationToBeTested,BoardHandler.getBoard("100x100Ones"),rule.getMyRule(),rule.getID()+"");
//            verifying.start();
//        }
//        try {
//            Thread.sleep(50000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        for (Rule rule : leaderBoard) {
            rule.setFitness(getFitness("" + rule.getID()));
        }
        leaderBoard(rules, leaderBoard);

        for (int i = 0; i < leaderBoard.length; i++) {
            System.out.println("Leader at pos: " + i + " has ID " + leaderBoard[i].getID() + " and fitness " +
                    leaderBoard[i].getFitness());
        }



        try{
            FileWriter out = new FileWriter("Leaderboard.txt");
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


    /**
     * This method generates a new rule by taking one as an input and switching the values of a random index in the
     * inputs rule-set
     * @param parent The rule that will be used as a basis to mutate
     * @param ID The ID that the new rule will have
     * @return A new rule with a single value switched from the parent and a new ID number
     */
    public static Rule mutate(Rule parent,int ID){
        Random randomIndex = new Random();
        int index = randomIndex.nextInt(parent.getMyRule().length);
        boolean[] parentsRule = parent.getMyRule().clone();
        parentsRule[index] = !parentsRule[index];
        return new Rule(parentsRule,ID);
    }


    /**
     * This function fills and updates the leaderboard, it can be used whether the leaderBoard is empty or full
     * @param newGeneration The new set of rules to be examined for a place on the leaderboard
     * @param currentLeaders The current leaderboard that may be updated in this function
     * @return The new updated leaderboard
     */
    private static Rule[] leaderBoard(Rule[] newGeneration, Rule[] currentLeaders){
        Rule[] backupLeaderboard = currentLeaders.clone();
        for (Rule rule : newGeneration) {
            rule.setFitness(getFitness("" + rule.getID()));
        }
        if (currentLeaders[0] == null){
            System.arraycopy(newGeneration, 0, currentLeaders, 0, currentLeaders.length);
            return currentLeaders;
        }


        for (Rule rule:newGeneration) {
//            if(correctOutcome(boardDom,rule.getID() + "") == 1){
//                rule.setFitness((rule.getFitness()+100));
//                System.out.println("WASSUP");
//            }
            for (int i = 0; i < currentLeaders.length; i++) {
                if(rule.getFitness() > currentLeaders[i].getFitness()){
//                    System.out.println("Swapping Leader a pos: "+i+" that has fitness: "+currentLeaders[i].getFitness() + " with ID: "+currentLeaders[i].getID());
//                    System.out.println("With new Leader "+rule.getID()+" that has fitness: "+rule.getFitness());
                    currentLeaders[i] = rule;
                    for (int j = i; j < currentLeaders.length-1; j++) {
                        currentLeaders[j+1] = backupLeaderboard[j];
                    }
                    backupLeaderboard = currentLeaders.clone();
                    break;
                }
            }
        }
        return currentLeaders;
    }

    /**
     * Randomly generates rule-sets to be evolved as the program runs
     * @param rules The array of rules that will be run and analyzed
     * @param ruleSize The size of the rule-sets for this CA
     * @param IDStart The starting point of the IDS to be assigned to the newly generated rules
     * @param leaderBoard The leaderboard this is just for checking if a rule has previously been generated already
     * @return The array of newly generated rules.
     */
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
    public static int getFitness(String name){
        int fitOne =0;
        int fitTwo =0;
        try {
            Scanner scanner = new Scanner(new File("C:\\Users\\nicob\\IdeaProjects\\CASProject3\\CATables\\"
                    +name + "-0.txt"));
            String line;
            String[] row;
            while(scanner.hasNextLine()){
                line = scanner.nextLine();
                row = line.split(" ");
                if (row[0].equals("Fitness:")){
                    fitOne = Integer.parseInt(row[1]);
                }
            }

            scanner = new Scanner(new File("C:\\Users\\nicob\\IdeaProjects\\CASProject3\\CATables\\"
                    +name + "-1.txt"));
            while(scanner.hasNextLine()){
                line = scanner.nextLine();
                row = line.split(" ");
                if (row[0].equals("Fitness:")){
                    fitTwo = Integer.parseInt(row[1]);
                }
            }
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return (fitOne+fitTwo)/2;
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
}
