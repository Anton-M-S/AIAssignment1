
import java.util.ArrayList;
//import java.util.LinkedList;
import java.util.Stack;

public class Lights {

    public static void main(String[] args) {
       
        String[][] board = {
            {"_","_"},
            {"_","_"}
        };
        System.out.println(board[0][0]);
    }

    public static String[][] BT(ArrayList<Space> part,String[][]board){
        Stack<BoardObj> stackBT = new Stack<BoardObj>();
        ArrayList<Space> buildLit = new ArrayList<Space>();
        ArrayList<Space> buildAva = part;
        BoardObj currObj = new BoardObj(buildLit, buildAva, board);
        BoardObj buildObj;
        
        stackBT.push(currObj);

        while(!stackBT.empty()){
            currObj = stackBT.pop();
            if(pVal(currObj)){
                if(cVal(currObj)){
                    return currObj.board;
                }
                BT(currObj.spacesLit, board);
            }

            for(Space inner : currObj.spacesAva){

                buildLit = new ArrayList<Space>();
                for(Space s : currObj.spacesLit) {
                    buildLit.add(s.deepClone());
                }

                buildAva = new ArrayList<Space>();
                for(Space a : currObj.spacesAva) {
                    buildAva.add(a.deepClone());
                }

                buildLit.add(inner);
                buildAva.remove(inner);

                buildObj = new BoardObj(buildLit,buildAva, board);
                stackBT.add(buildObj);
            }


        }

        return board;
    }

    public static boolean pVal(BoardObj inObj){
        return false;
    }
    public static boolean cVal(BoardObj inObj){
        return false;
    }





    public static boolean bulbCheck(int x, int y, String[][] inBrd){
        
        int cnt = 1;
        //up
        while(y-cnt>=0){

        }
        //down
        while(y+cnt<=inBrd.length){

        }
        //left
        while(x-cnt>=0){

        }
        //right
        while(x+cnt<=inBrd.length){

        }
        return false;
    }

}


