import java.util.ArrayList;

public class BoardObj{
    ArrayList<Space> spacesLit;
    ArrayList<Space> spacesAva;
    String[][] board;

    BoardObj(ArrayList<Space> inS, ArrayList<Space> inA,String[][] inB){
        board = inB;
        spacesLit = inS;
        spacesAva = inA;
    }
}