import java.lang.Math;
public class RunCA extends Thread{
    int neighborhoodSize; //Radius around a cell that will be looked at for updating
    int duration; //max cycle count
    boolean[][] board; //The board the CA runs on
    boolean[] ruleSet; //The ruleset being applied
    final String ID;

    public void run(){
        BoardHandler.exportBoard(this.Run(),this.board.length,this.ID,this.ruleSet);
    }

    public RunCA(int neighborhoodSize,int duration,
                 boolean[][] board, boolean[] ruleSet,String id){
        this.neighborhoodSize = neighborhoodSize;
        this.duration = duration;
        this.board = board;
        this.ruleSet = ruleSet;
        this.ID = id;

    }

    private boolean[][] Run(){
        int cycleNum = 0;
        int size = this.board.length;
        boolean[][] editingBoard = new boolean[size][size];


        while(cycleNum < this.duration){
//            System.out.println(cycleNum + " cycleNume " + duration + " duration");
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {

                    editingBoard[i][j] = findUpdate(examineNeighbors(i,j,size));

                }

            }
            this.board = editingBoard;
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

    //TODO: Change back to private after testing
    private boolean[] examineNeighbors(int x, int y,int size){
//        System.out.println("Printing Board in RunCA");
//        for (int i = 0; i < size; i++) {
//            for (int j = 0; j < size; j++) {
//                System.out.print(this.board[i][j]+", ");
//            }
//            System.out.println();
//        }
//        System.out.println("Done Printing Board \n");
        int neighborX;
        int neighborY;
        int neighborCount = 0;
        int neighborDim = 1+2*this.neighborhoodSize;
        boolean[] neighborStatuses = new boolean[(neighborDim*neighborDim)-1];
        for (int i = x-this.neighborhoodSize; i <= x+this.neighborhoodSize; i++) {
//            System.out.println("Ho " + i);
            //correcting x-cord for wrap around
            if(0<=i && i<size){
                neighborX = i;
            }else if(i<0){
                neighborX = i+size;
            }else {
//                System.out.print(i +" i " + size + " size " + (i - size) + " i - size");
                neighborX = i - size;
            }
            for (int j = y-this.neighborhoodSize; j <= y+this.neighborhoodSize; j++) {
//                System.out.println("HI " + j);
                //correcting y-cord for wrap around
                if(0<= j && j<size){
                    neighborY = j;
                }else if (j<0){
                    neighborY = j + size;
                }else {
//                    System.out.println(j +" j " + size + " size " + (j - size) + " j - size");
                    neighborY = j - size;
                }

                //Ignore current center cord and check
                if (neighborX != x || neighborY != y){
//                    System.out.println(neighborX + " neighborX "+ neighborY + " neighborY ");
//                    System.out.println("Neighbor at " + neighborX  + " " + neighborY + " is " + this.board[neighborX][neighborY]);
                    neighborStatuses[neighborCount] = this.board[neighborX][neighborY];
                    neighborCount++;
                }
            }
            
        }
//        System.out.println(neighborCount);
        return neighborStatuses;
    }



    private boolean findUpdate(boolean[] neighbors){
        int neighborIntVal = 0;
        for (int i = 0; i < neighbors.length; i++) {
            if (neighbors[i]){
//                System.out.println(i + " made it");
                neighborIntVal += Math.pow(2,i);
            }
        }
//        System.out.println(neighborIntVal + " neighborIntVal");
        return this.ruleSet[neighborIntVal];
    }

}
