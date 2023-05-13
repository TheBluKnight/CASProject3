/** Dominic Larranaga  BoardHandler.java
 * This class is for generally dealing with boards used in the CA such as making them, retrieving one from a file, or
 * writing the outcome board from a rule-set to a file.
 */

import java.util.Arrays;
import java.util.Random;
import java.io.*;
import java.util.Scanner;

public class BoardHandler {
    /**
     * Randomly generates a new board of true or false values
     * @param size The size of the board you want to make
     * @return The board that has been made
     */
    public static boolean[][] makeBoard(int size) {


        Random coinFlip = new Random();

        //Populate board with values
        boolean[][] board = new boolean[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = coinFlip.nextBoolean();
            }
        }


        return board;
    }

    /**
     * Exports a board to a text file
     * @param board The board you want to export
     * @param size The size of the board
     * @param name The name of the file containing the board the .txt is added by this method
     */
    public static void exportBoard(boolean[][] board, int size,String name,boolean[] rule,boolean[][] defaultBoard){
        FileWriter out;
        int ones = 0;
        int zeros = 0;
        int[] binaryRule = new int[rule.length];
        try {

            out = new FileWriter(new File("CATables",name+".txt"));
            for (int i = 0; i < rule.length; i++) {
                if (rule[i]){
                    binaryRule[i] = 1;
                }else {
                    binaryRule[i] = 0;
                }
            }
            out.write(Arrays.toString(binaryRule)+"\n");
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if(board[i][j]){
                        ones++;
                    }else {
                        zeros++;
                    }
                    out.write(board[i][j]+" ");
                }
                out.write('\n');
            }
            out.write("Ones: " + ones + "\n");
            out.write("Zeros " + zeros + "\n");
            out.write("Did fill board?: ");
            if (ones == (size*size) || zeros == 0){
                out.write(" Yes Ones did\n");
            }else if (zeros == (size*size) || ones == 0){
                out.write(" Yes Zeros did\n");
            }else {
                out.write("No\n");
            }
            out.write("Fitness: ");
            if (ones > zeros && getDominant(defaultBoard)){
                out.write(ones+"");
            }else if (ones < zeros && !getDominant(defaultBoard)){
                out.write(zeros+"");
            } else {
                out.write("0");
            }
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    /**
     * This method is for retrieving a board from a .txt file
     * @param fileName the name of the file where the board is, this does not need .txt
     * @return A 2D boolean array that is the board
     */
    public static boolean[][] getBoard(String fileName){
        boolean[][] board = {};
        String line;
        String[] row;
        int size;
        try {
            Scanner scanner = new Scanner(new File(fileName + ".txt"));
            line = scanner.nextLine();
            row = line.split(" ",0);
            size = row.length;
            board = new boolean[size][size];
            for (int i = 0; i < size; i++) {
                board[0][i] = Boolean.parseBoolean(row[i]);
            }
            for (int i = 1; i < size; i++) {
                line = scanner.nextLine();
                row = line.split(" ",0);
                for (int j = 0; j < size; j++) {
                    board[i][j] = Boolean.parseBoolean(row[j]);
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return board;
    }

    /**
     * Just for printing any boolean board to console
     * @param board The board you want to print
     */
    //WARNING: Don't use in large tests this has a lot of print statements just use for looking at a single CA
    public static void printBoard(boolean[][] board){
        int numZeros = 0;
        int numOnes = 0;
        int size = board.length;
        for (boolean[] booleans : board) {
            for (int j = 0; j < size; j++) {
                if (!booleans[j]) {
                    System.out.print("0 ");
                    numZeros++;
                } else {
                    System.out.print("1 ");
                    numOnes++;
                }
            }
            System.out.println();
        }
        System.out.println("Number of Zeros: " + numZeros);
        System.out.println("Number of Ones: " + numOnes);


    }

    /**
     * Iterates through a board and figures out if the majority of values are zeros or ones
     * @param board The binary board you would like to analyze
     * @return boolean: True meaning the majority are ones / False meaning the majority are zeros
     */
    public static boolean getDominant(boolean[][] board){
        int Ones = 0;
        int Zeros = 0;
        for (boolean[] booleans : board) {
            for (int j = 0; j < board.length; j++) {
                if (booleans[j]) {
                    Ones++;
                } else {
                    Zeros++;
                }
            }
        }
        return Ones > Zeros;
    }

}

