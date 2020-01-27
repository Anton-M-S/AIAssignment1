import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
//import java.util.LinkedList;
import java.util.Stack;
import java.util.Scanner;

public class Main {

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
                if (currline.charAt(0)=='#') {
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
                }else {
                    for (int i = 0; i < x; i++) {
                        currline = fileScan.nextLine();
                        for (int j = 0; j < y; j++) {
                            newBoard[i][j] = currline.charAt(j);
                        }
                    }


                    board = new Board(newBoard, setWallSpaces(newBoard), new ArrayList<Space>());
                    
                    System.out.println(board);//call Search functions from this line
                    BT(board.spacesAva, board.layout);
                    System.out.println("BT passed");
                    //setWallSpaces(newBoard);
                    currline = fileScan.nextLine();
                }
            }
        fileScan.close();
        } catch (Exception e) {
            System.out.println("error:" + e);
        }
        
    }

    







    public static Board BT(ArrayList<Space> ava,char[][]board){
        Stack<Board> stackBT = new Stack<Board>();
        ArrayList<Space> buildLit = new ArrayList<Space>();
        ArrayList<Space> buildAva = ava;
        Board currObj = new Board(board, ava, buildLit);
        Board buildObj;

        
        
        stackBT.push(currObj);

        System.out.println("BT Start");

        while(!stackBT.empty()){
            currObj = new Board(stackBT.pop());
             if(currObj.validatePartialSolution(currObj.spacesLit)){
                 System.out.println(currObj);
                 if(currObj.isBoardValid(currObj.spacesLit)){
                     System.out.println(currObj);
                     return currObj;
                 }
                 BT(currObj.spacesLit, board);
             }
            
            //System.out.println("BT 2");
            if(currObj.spacesAva.isEmpty()){break;}
            for(Space inner : currObj.spacesAva){
                System.out.println(stackBT.size());

                buildLit = new ArrayList<Space>();
                for(Space s : currObj.spacesLit) {
                    buildLit.add(s.deepClone());
                }

                buildAva = new ArrayList<Space>();
                for(Space a : currObj.spacesAva) {
                    if(!a.equals(inner)){
                        buildAva.add(a.deepClone());}
                }

                buildLit.add(inner.deepClone());

                //buildObj = new Board( board,buildAva,buildLit);
                buildObj = new Board( board,buildAva,buildLit);
                //System.out.println("Added Board with lights");
                stackBT.add(buildObj);
            }


        }

        return null;
    }

    public static boolean pVal(Board inObj){
        return false;
    }
    public static boolean cVal(Board inObj){
        return false;
    }












    public static ArrayList<Space> setWallSpaces(char[][] layout){
        ArrayList<Space> returnList = new ArrayList<Space>();
        Space builderSpace;
        for(int i = 0; i<layout.length; i++){
            for(int j = 0; j<layout[0].length; j++){

                if(layout[i][j]=='_'){

                    if (i+1<layout.length && (layout[i+1][j]=='1'||layout[i+1][j]=='2'||layout[i+1][j]=='3'||layout[i+1][j]=='4')){
                        //System.out.println("Space at " + i + j);
                        builderSpace = new Space(j, i);
                        returnList.add(builderSpace);
                    } 
                    
                    else if (i>0 && (layout[i-1][j]=='1'||layout[i-1][j]=='2'||layout[i-1][j]=='3'||layout[i-1][j]=='4')){
                        //System.out.println("Space at " + i + j);
                        builderSpace = new Space(j, i);
                        returnList.add(builderSpace);
                    } 

                    else if (j+1<layout[0].length && (layout[i][j+1]=='1'||layout[i][j+1]=='2'||layout[i][j+1]=='3'||layout[i][j+1]=='4')){
                        //System.out.println("Space at " + i + j);
                        builderSpace = new Space(j, i);
                        returnList.add(builderSpace);
                    } 

                    else if (j>0 && (layout[i][j-1]=='1'||layout[i][j-1]=='2'||layout[i][j-1]=='3'||layout[i][j-1]=='4')){
                        //System.out.println("Space at " + i + j);
                        builderSpace = new Space(j, i);
                        returnList.add(builderSpace);
                    } 
                }  
            }
        
        }
    return returnList;
    }
}
