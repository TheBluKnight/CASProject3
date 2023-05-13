/**
 * Dominic Larranaga RunCa, this class is meant to be instantiated and ran on parallel threads to allow for fast
 * program running. What it does specifically is use a given rule-set to run a cellular automata on a given board
 */

import java.lang.Math;
public class RunCA extends Thread{
    int neighborhoodSize; //Radius around a cell that will be looked at for updating
    int duration; //max cycle count
    boolean[][] board; //The board the CA runs on
    final boolean[][] defaultBoard;
    boolean[] ruleSet; //The ruleset being applied
    final String ID;

    /**
     * run method for multi-threaded running, also gets this Cellular Automata started, then sends the
     * results to get exported
     */
    public void run(){
        BoardHandler.exportBoard(this.Run(),this.board.length,this.ID,this.ruleSet,this.defaultBoard);
    }

    /**
     * Class constructor
     * @param neighborhoodSize The radius around a cell that will be looked at for running the CA
     * @param duration The number of cycles the CA will run for
     * @param board The 2D boolean array that the CA runs on
     * @param ruleSet a boolean array of the rule-set applied to the CA
     * @param id The ID of this rule-set
     */
    public RunCA(int neighborhoodSize,int duration,
                 boolean[][] board, boolean[] ruleSet,String id){
        this.neighborhoodSize = neighborhoodSize;
        this.duration = duration;
        this.board = board;
        this.defaultBoard = board;
        this.ruleSet = ruleSet;
        this.ID = id;

    }


    /**
     * This is where the cellular automata is actually run at
     * @return a 2D boolean array of the final board after running the cellular automata, usually returned to get
     * copied to a file with the rule-set info
     */
    private boolean[][] Run(){
        int cycleNum = 0;
        int size = this.board.length;
        boolean[][] editingBoard = new boolean[size][size];
        while(cycleNum < this.duration){
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    editingBoard[i][j] = findUpdate(examineNeighbors(i,j,size));
                }
            }
            this.board = editingBoard;
//             BoardHandler.exportBoard(this.board,100,""+this.ID+"-"+cycleNum,this.ruleSet,this.defaultBoard);
//            BoardHandler.printBoard(this.board);
// Only uncomment for single board running
            //   or else be flooded  with print statements
            cycleNum++;
        }
        return this.board;
    }


    /**
     * This function returns an array of the values around a point representing the status of the neighbors around
     * a cell within the neighborhood being searched
     * @param x the x-coordinate of the center cell
     * @param y the y-coordinate of the center cell
     * @param size the size of the board just so we don't need to keep calculating it
     * @return an array of the values each of the neighbors in the neighborhood has
     */
    private boolean[] examineNeighbors(int x, int y,int size){
        int neighborX;
        int neighborY;
        int neighborCount = 0;
        int neighborDim = 1+2*this.neighborhoodSize;
        boolean[] neighborStatuses = new boolean[(neighborDim*neighborDim)-1];
        for (int i = x-this.neighborhoodSize; i <= x+this.neighborhoodSize; i++) {
            //correcting x-cord for wrap around
            if(0<=i && i<size){
                neighborX = i;
            }else if(i<0){
                neighborX = i+size;
            }else {
                neighborX = i - size;
            }
            for (int j = y-this.neighborhoodSize; j <= y+this.neighborhoodSize; j++) {
                //correcting y-cord for wrap around
                if(0<= j && j<size){
                    neighborY = j;
                }else if (j<0){
                    neighborY = j + size;
                }else {
                    neighborY = j - size;
                }

                //Ignore current center cord and check
                if (neighborX != x || neighborY != y){
                    neighborStatuses[neighborCount] = this.board[neighborX][neighborY];
                    neighborCount++;
                }
            }
            
        }
        return neighborStatuses;
    }


    /**
     * Converts a binary string representing the states of a cells neighbors to a base 10 integers and uses
     * that to retrieve the next state from this instance's rule-set
     * @param neighbors A boolean array representing the states of all the neihbors surrounding a cell
     * @return The state of the center cell in the next cycle
     */
    private boolean findUpdate(boolean[] neighbors){
        int neighborIntVal = 0;
        for (int i = 0; i < neighbors.length; i++) {
            if (neighbors[i]){
                neighborIntVal += Math.pow(2,i);
            }
        }
        return this.ruleSet[neighborIntVal];
    }

}
