import java.util.ArrayList;

public class Board {
    private char[][] layout;
    public Board(char[][] newBoard){
        layout = new char[newBoard.length][newBoard[0].length];
        for (int i = 0; i < layout.length; i++) {
            for (int j = 0; j < layout[i].length; j++) {
                layout[i][j]=newBoard[i][j];
            }
        }
    }

    public String toString(){
        String toReturn = "";
        for (int i = 0; i < layout.length; i++) {
            for (int j = 0; j < layout[i].length; j++) {
                toReturn+=layout[i][j];
            }
            toReturn+='\n';
        }
        return toReturn;
    }

    public char getPosition(int row, int col){
        return layout[row][col];
    }

    public void updatePosition(char newChar, int row, int col){
        layout[row][col] = newChar;
    }

    public boolean isBoardValid(){

        return false;
    }

    public boolean areBulbsValid(ArrayList<Space> bulbSpaces){
        boolean valid = true;
        int counter = 0;
        Space currSpace;
        while (valid && counter<bulbSpaces.size()){
            currSpace = bulbSpaces.get(counter);

        }
        return false;
    }

    public boolean areWallsValid(Board board, ArrayList<Space> wallSpaces){

        return false;
    }

    public boolean isRowValid(int rowNum, int colNum){
        boolean valid = true;
        boolean noWall = true;
        int counter = colNum-1;
        while (valid && noWall && counter >= 0){
            if (this.getPosition(rowNum, counter) == 'b'){
                valid = false;
            }else {
                if (Character.isDigit(this.getPosition(rowNum, counter))){
                    noWall = false;
                }
            }
            counter -= 1;
        }
        counter = colNum+1;
        while (valid && noWall && counter < this.layout[rowNum].length){
            if (this.getPosition(rowNum,counter) == 'b'){
                valid = false;
            }else {
                if (Character.isDigit(this.getPosition(rowNum, counter))){
                    noWall = false;
                }
            }
            counter+=1;
        }
        return valid;
    }
}