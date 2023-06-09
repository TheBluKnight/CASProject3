/**
 * This is just a tester class for trying out different parts of the program independently, probably shouldn't make
 * it onto github
 */

import java.util.Arrays;

/**Dominic Larranaga RunCA.java
 * This class is for actually running the cellular automata and contains the main function
 */
public class Tester {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
//        /*
//          args[0] should be either true or false denoting
//          whether you have a board to use or if one needs to be generated
//
//          args[1] is the name of the file containing the board you have
//          ready to use if args[0] is true
//
//          args[2] is the size of the board to be made if args[0] is false
//
//          args[3] is another true / false choice similar to args[0], this one
//          describing whether a rule-set is to be provided or generated
//
//          args[4] is the rule-set provided if args[3] is set to true
//         */
////        boolean[][] board;
////        int size;
////        //Generating or retrieving board
////        if (!Boolean.parseBoolean(args[0])){
////            board = BoardHandler.makeBoard(Integer.parseInt(args[2]));
////            size = board.length;
////        }else{
////            board = BoardHandler.getBoard(args[1]);
////            BoardHandler.printBoard(board);
////        }
//
//        //Generating or retrieving rule-set
////        if(!Boolean.parseBoolean(args[3])){
////            //TODO: Generate a ruleset
////            System.out.println("Generate a ruleset");
////        }else{
////            //TODO: Get a ruleset from file
////            System.out.println("Get a ruleset");
////        }
//        int neighborHoodSize = 1;
////        BoardHandler.exportBoard(BoardHandler.makeBoard(10),10,"TestGenBoard");
////        BoardHandler.printBoard(BoardHandler.getBoard("TestGenBoard"));
//        boolean[][] testBoard = {
//                {false,false,true,false,false},
//                {false,false,false,false,false},
//                {false,false,true,false,false},
//                {false,false,false,false,false},
//                {false,false,true,false,false}
//
//        };
//
//
//        boolean[] testRules = new boolean[(int)Math.pow(2,((Math.pow((1+2*neighborHoodSize),2)-1)))];
//        testRules[66] = true;
//        testRules[16] = true;
//        testRules[8] = true;
//        System.out.println(testRules.length + " rules length");
//        boolean[] testNeighbors;
//        boolean testNeighborEval;
//
//        RunCA tester = new RunCA(1,100,BoardHandler.getBoard("10x10Good"),testRules,"tester");
////        RunCA tester1 = new RunCA(1,1,testBoard,testRules,"tester1");
////        RunCA tester2 = new RunCA(1,1,testBoard,testRules,"tester2");
////        RunCA tester3 = new RunCA(1,1,testBoard,testRules,"tester3");
//
//        tester.start();
//        BoardHandler.printBoard(BoardHandler.getBoard("tester"));
//
//
////        System.out.println(tester.findUpdate(tester.examineNeighbors(2,2,5)));
////        testNeighbors = tester.examineNeighbors(4,4,5);
////        for (boolean i: testNeighbors) {
////            System.out.print(i + ", ");
////        }
////        for (int i = 0; i < testResult.length; i++) {
////            for (int j = 0; j < testResult.length; j++) {
////                System.out.print(testResult[i][j] + ", ");
////            }
////            System.out.println();
////        }
        boolean[] ruleSet = {false, true, false, true, false, false, false, false, false, false, false, true, true, false,
                false, false, false, true, true, false, false, false, false, false, true, false, false, false, false,
                true, false, true, false, false, false, false, false, true, true, false, false, true, true, true, false,
                true, false, true, false, false, true, false, true, false, true, false, true, false, false, false,
                false, true, false, false, false, false, true, false, true, true, true, true, true, true, false, false,
                false, false, false, false, false, true, true, false, true, false, true, false, true, false, true,
                false, true, true, false, true, false, false, false, false, true, false, true, false, true, true, true,
                true, false, true, true, true, false, true, true, false, true, true, true, false, true, false, true,
                false, false, true, true, true, false, false, true, false, true, false, false, false, false, true,
                false, false, false, false, false, true, true, true, false, false, true, false, false, true, false,
                true, false, false, false, true, true, true, false, false, false, true, false, true, true, false, true,
                true, true, true, true, true, true, false, true, true, true, true, true, true, true, true, true, false,
                false, false, true, true, false, true, false, false, true, false, false, false, true, true, false, true,
                false, false, false, false, false, false, false, false, false, true, false, true, true, true, true,
                true, false, false, false, true, false, true, false, false, false, true, false, false, false, true,
                true, true, true, true, false, true, false, true, true, false, false, true, false, false, false, false,
                false, false, false, false, false, true, false, false
};

//        boolean[] testRuleset = new boolean[ruleSet.length];
//        for (int i = 0; i < ruleSet.length; i++) {
//            if(ruleSet[i] == 1){
//                testRuleset[i] = true;
//            }else {
//                testRuleset[i] = false;
//            }
//        }
//        System.out.println(Arrays.toString(BoardHandler.getBoard("100x100Ones")[1]));
        RunCA testCA = new RunCA(1,3000,BoardHandler.getBoard("100x100Ones"),ruleSet,1+"-0");
        testCA.start();


        long stop = System.currentTimeMillis();
        System.out.println("Elapsed time: " + (stop - start));
    }

    //Generating or retrieving rule-set

}
