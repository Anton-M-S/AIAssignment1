import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class backtrack {
    private static int stateCounter = 0;
    public static void main(String[] args) {
        Space[][] newBoard = new Space[0][0];
        int x = 0;
        int y = 0;
        Board board;
        try {
            Scanner fileScan = new Scanner(new FileReader(new File(args[0])));
            boolean isStart = true;
            String currline;
            String[] dimensions;
            currline = fileScan.nextLine();
            Space newSpace = null;
            char currChar;
            while (fileScan.hasNext()) {
                if (currline.charAt(0) == '#') {
                    if (isStart) {
                        while (currline.charAt(0) == '#') {
                            currline = fileScan.nextLine();
                        }
                        isStart = false;
                        dimensions = currline.split(" ");
                        x = Integer.parseInt(dimensions[0]);
                        y = Integer.parseInt(dimensions[1]);
                        newBoard = new Space[x][y];

                    } else {
                        isStart = true;
                        currline = fileScan.nextLine();
                    }
                } else {
                    for (int i = 0; i < x; i++) {
                        currline = fileScan.nextLine();
                        for (int j = 0; j < y; j++) {
                            currChar = currline.charAt(j);
                            if (currChar=='_') {
                                newSpace = new UnlitSpace(i,j);
                            }else {
                                if (Character.isDigit(currChar)){
                                    newSpace = new Wall(i,j,currChar);
                                }
                            }
                            newBoard[i][j] = newSpace;
                        }
                    }


                    board = new Board(newBoard, setWallSpaces(newBoard), new ArrayList<>());
                    if (board.spacesAva.size()==0){
                        board.setAvailableSpacesToAllBlanks();
                    }
                    System.out.println(board);//call Search functions from this line
                    board.solveGuaranteedBulbs();
                    System.out.println("BT Start");
                    Board result = BTRecursive(board, null, false);
                    if (result == null) {
                        System.out.println("Backtrack failed");
                    } else {
                        System.out.println("BT passed");
                    }
                    System.out.println("States Examined: "+stateCounter);
                    stateCounter = 0;
                    //setWallSpaces(newBoard);
                    currline = fileScan.nextLine();
                }
            }
            fileScan.close();
        } catch (Exception e) {
            System.out.println("error:" + e);
        }

    }


    private static Board BTRecursive(Board board, Space nextBulb, boolean isPartial) {
        //  System.out.println(i++);
        stateCounter++;
        Board newBoard = new Board(board);

        if (nextBulb != null) {//if not the start, or the first iteration after a partial solution was found
            //newBoard.placeBulb(nextBulb);//place the next bulb on teh board
            newBoard.lightSpace(nextBulb);//
        }
        // newBoard.updateAvailableSpaces();//remove invalid spaces from available


        Board tempBoard = null;

        if (newBoard.isBoardValid()) {//if it is a fully valid board
            System.out.println(newBoard);
            return newBoard;//return it
        } else {
            if (!isPartial && newBoard.validatePartialSolution()) {//if a partial solution
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
                if (newBoard.areBulbsValid() && newBoard.wallsNotOverloaded()) {
                    ArrayList<Space> availSpaces = newBoard.spacesAva;
                    int counter = 0;
                    //tempboard will always be null, unless it is returned a fully valid solution
                    while (tempBoard == null && counter < newBoard.spacesAva.size() && newBoard.spacesAva.size() > 0) {
                        tempBoard = BTRecursive(newBoard, availSpaces.get(counter), isPartial);
                        if (tempBoard == null){
                            newBoard.spacesAva.remove(counter);
                            counter--;
                        }
                        counter++;
                    }
                }
            }
        }
        return tempBoard;
    }



    private static ArrayList<Space> setWallSpaces(Space[][] layout) {
        ArrayList<Space> returnList = new ArrayList<>();
        Space builderSpace;
        for (int i = 0; i < layout.length; i++) {
            for (int j = 0; j < layout[0].length; j++) {

                if (layout[i][j] instanceof UnlitSpace) {

                    if (i + 1 < layout.length && layout[i+1][j]instanceof Wall &&((Wall)layout[i+1][j]).getWallNum()!=0){//(layout[i + 1][j] == '1' || layout[i + 1][j] == '2' || layout[i + 1][j] == '3' || layout[i + 1][j] == '4')) {
                        //System.out.println("Space at " + i + j);
                        builderSpace = new Space(i, j);
                        returnList.add(builderSpace);
                    } else if (i > 0 && layout[i-1][j]instanceof Wall &&((Wall)layout[i-1][j]).getWallNum()!=0){//(layout[i - 1][j] == '1' || layout[i - 1][j] == '2' || layout[i - 1][j] == '3' || layout[i - 1][j] == '4')) {
                        //System.out.println("Space at " + i + j);
                        builderSpace = new Space(i, j);
                        returnList.add(builderSpace);
                    } else if (j + 1 < layout[0].length && layout[i][j+1]instanceof Wall &&((Wall)layout[i][j+1]).getWallNum()!=0){//&& (layout[i][j + 1] == '1' || layout[i][j + 1] == '2' || layout[i][j + 1] == '3' || layout[i][j + 1] == '4')) {
                        //System.out.println("Space at " + i + j);
                        builderSpace = new Space(i, j);
                        returnList.add(builderSpace);
                    } else if (j > 0 && layout[i][j-1]instanceof Wall &&((Wall)layout[i][j-1]).getWallNum()!=0){//(layout[i][j - 1] == '1' || layout[i][j - 1] == '2' || layout[i][j - 1] == '3' || layout[i][j - 1] == '4')) {
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
