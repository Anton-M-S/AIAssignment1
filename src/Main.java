import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
//import java.util.LinkedList;
import java.util.Stack;
import java.util.Scanner;

public class Main {
    public static int i = 0;

    public static void main(String[] args) {
        char[][] newBoard = new char[0][0];
        int x = 0;
        int y = 0;
        Board board;
        try {
            Scanner fileScan = new Scanner(new FileReader(new File(args[0])));
            boolean isStart = true;
            String currline;
            String[] dimensions;
            currline = fileScan.nextLine();

            while (fileScan.hasNext()) {
                if (currline.charAt(0) == '#') {
                    if (isStart) {
                        currline = fileScan.nextLine();
                        isStart = false;
                        dimensions = currline.split(" ");
                        x = Integer.parseInt(dimensions[0]);
                        y = Integer.parseInt(dimensions[1]);
                        newBoard = new char[x][y];

                    } else {
                        isStart = true;
                        currline = fileScan.nextLine();
                    }
                } else {
                    for (int i = 0; i < x; i++) {
                        currline = fileScan.nextLine();
                        for (int j = 0; j < y; j++) {
                            newBoard[i][j] = currline.charAt(j);
                        }
                    }


                    board = new Board(newBoard, new ArrayList<Space>(), new ArrayList<Space>());
                    board.spacesAva = setWallSpaces(newBoard);
                    System.out.println(board);//call Search functions from this line
     //               BT(board.spacesAva, board.layout, false);
                    Board result = BTRecursive(board, null, false);
                    if (result == null) {
                        System.out.println("Backtrack failed");
                    } else {
                        System.out.println("BT passed");
                    }
                    setWallSpaces(newBoard);
                    currline = fileScan.nextLine();
                }
            }
            fileScan.close();
        } catch (Exception e) {
            System.out.println("error:" + e);
        }

    }


    public static Board BTRecursive(Board board, Space nextBulb, boolean isPartial) {
        //  System.out.println(i++);
        boolean partial = isPartial;//remember the state of whether or not teh backtrack began as a partial solution
        Board newBoard = new Board(board);
        if (nextBulb != null) {//if not the start, or the first iteration after a partial solution was found
            newBoard.placeBulb(nextBulb);//place the next bulb on teh board
            newBoard.lightSpace(nextBulb);//
        }
        newBoard.updateAvailableSpaces();//remove invalid spaces from available


        Board tempBoard = null;

        if (newBoard.isBoardValid(newBoard.spacesLit)) {//if it is a fully valid board
            //System.out.println(newBoard);
            return newBoard;//return it
        } else {
            if (!isPartial && newBoard.validatePartialSolution(newBoard.spacesLit)) {//if a partial solution
               // System.out.println(newBoard);
                newBoard.setAvailableSpacesToAllBlanks();//switch spacesAva to a list of all '_' spaces
                Board partialSol = BTRecursive(newBoard, null, true);
                if (partialSol != null) {//if the solution found on the previous line was valid, return it
                    //this works because the only place that returns anything other than null is if there is
                    //a complete solution
                    return partialSol;
                }
            } else {
                //if the board as it stands has no bulbs that light bulbs, or walls with too many bulbs
                if (newBoard.areBulbsValid(newBoard.spacesLit) && !newBoard.areWallsOverloaded()) {
                    ArrayList<Space> availSpaces = newBoard.spacesAva;
                    int counter = 0;
                    //tempboard will always be null, unless it is returned a fully valid solution
                    while (tempBoard == null && counter < newBoard.spacesAva.size() && newBoard.spacesAva.size() > 0) {
                        tempBoard = BTRecursive(newBoard, availSpaces.get(counter), partial);
                        counter++;
                    }
                }
            }
        }
        return tempBoard;
    }


    public static Board BT(ArrayList<Space> ava, char[][] board, boolean isPartial) {
        Stack<Board> stackBT = new Stack<Board>();
        ArrayList<Space> buildLit = new ArrayList<Space>();
        ArrayList<Space> buildAva = ava;
        Board currObj = new Board(board, ava, buildLit);
        Board buildObj;


        stackBT.push(currObj);

        System.out.println("BT Start");

        while (!stackBT.empty()) {
            currObj = new Board(stackBT.pop());
            //System.out.println(currObj);
            if (currObj.spacesLit.size() >= currObj.getWallTotal()) {
                if (currObj.validatePartialSolution(currObj.spacesLit)) {
                    //System.out.println(currObj);
                    if (currObj.isBoardValid(currObj.spacesLit)) {
                        System.out.println(currObj);
                        return currObj;
                    }
                    currObj.setAvailableSpacesToAllBlanks();
                    if (!isPartial) {
                        BT(currObj.spacesAva, currObj.layout, true);
                    }
                }
            }

            //System.out.println("BT 2");
            //if(currObj.spacesAva.isEmpty()){break;}
            for (Space inner : currObj.spacesAva) {
                // System.out.println(stackBT.size());

                buildLit = new ArrayList<Space>();
                for (Space s : currObj.spacesLit) {
                    buildLit.add(s.deepClone());
                }

                buildAva = new ArrayList<Space>();
                for (Space a : currObj.spacesAva) {
                    if (!a.equals(inner)) {
                        buildAva.add(a.deepClone());
                    }
                }

                buildLit.add(inner.deepClone());

                //buildObj = new Board( board,buildAva,buildLit);
                buildObj = new Board(board, buildAva, buildLit);
                //System.out.println("Added Board with lights");
                stackBT.add(buildObj);
            }


        }

        return null;
    }

    public static boolean pVal(Board inObj) {
        return false;
    }

    public static boolean cVal(Board inObj) {
        return false;
    }


    public static ArrayList<Space> setWallSpaces(char[][] layout) {
        ArrayList<Space> returnList = new ArrayList<Space>();
        Space builderSpace;
        for (int i = 0; i < layout.length; i++) {
            for (int j = 0; j < layout[0].length; j++) {

                if (layout[i][j] == '_') {

                    if (i + 1 < layout.length && (layout[i + 1][j] == '1' || layout[i + 1][j] == '2' || layout[i + 1][j] == '3' || layout[i + 1][j] == '4')) {
                        //System.out.println("Space at " + i + j);
                        builderSpace = new Space(i, j);
                        returnList.add(builderSpace);
                    } else if (i > 0 && (layout[i - 1][j] == '1' || layout[i - 1][j] == '2' || layout[i - 1][j] == '3' || layout[i - 1][j] == '4')) {
                        //System.out.println("Space at " + i + j);
                        builderSpace = new Space(i, j);
                        returnList.add(builderSpace);
                    } else if (j + 1 < layout[0].length && (layout[i][j + 1] == '1' || layout[i][j + 1] == '2' || layout[i][j + 1] == '3' || layout[i][j + 1] == '4')) {
                        //System.out.println("Space at " + i + j);
                        builderSpace = new Space(i, j);
                        returnList.add(builderSpace);
                    } else if (j > 0 && (layout[i][j - 1] == '1' || layout[i][j - 1] == '2' || layout[i][j - 1] == '3' || layout[i][j - 1] == '4')) {
                        //System.out.println("Space at " + i + j);
                        builderSpace = new Space(i, j);
                        returnList.add(builderSpace);
                    }
                }
            }

        }
        return returnList;
    }
}