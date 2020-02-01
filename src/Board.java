import java.util.ArrayList;

public class Board {
    public char[][] layout;
    public ArrayList<Space> spacesAva;
    public ArrayList<Space> spacesLit;
    public ArrayList<Space> wallLocations;
    private int wallTotal;

    public Board(char[][] newBoard, ArrayList<Space> ava, ArrayList<Space> lit){
        this.wallTotal = 0;
        this.spacesAva = ava;
        this.spacesLit = lit;
        this.wallLocations = new ArrayList<>();
        layout = new char[newBoard.length][newBoard[0].length];
        for (int i = 0; i < layout.length; i++) {
            for (int j = 0; j < layout[i].length; j++) {
                layout[i][j]=newBoard[i][j];
                if (Character.isDigit(layout[i][j])){
                    wallLocations.add(new Space(i,j));
                    wallTotal += layout[i][j]-48;
                }
            }
        }
    }

    public Board(Board b){
        this.spacesAva = new ArrayList<>();
        this.spacesAva.addAll(b.spacesAva);
        this.spacesLit = new ArrayList<>();
        this.spacesLit.addAll(b.spacesLit);
        this.layout = new char[b.layout.length][b.layout[0].length];
        this.wallLocations = new ArrayList<>();
        for (int i = 0; i < layout.length; i++) {
            for (int j = 0; j < layout[i].length; j++) {
                layout[i][j]=b.layout[i][j];
                if (Character.isDigit(layout[i][j])){
                    wallLocations.add(new Space(i,j));
                }
            }
        }

    }

    public int getWallTotal(){
        return wallTotal;
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

    public boolean isBoardValid(ArrayList<Space> bulbLocations){
        boolean bulbsValid = this.areBulbsValid(bulbLocations);
        boolean wallsValid = this.areWallsValid();
        boolean boardValid = this.checkForUnlitSpaces();

        return bulbsValid && wallsValid && boardValid;
    }

    public boolean areBulbsValid(ArrayList<Space> bulbSpaces){
        boolean valid = true;
        int counter = 0;
        Space currSpace;
        while (valid && counter<bulbSpaces.size()){
            currSpace = bulbSpaces.get(counter);
            if (this.getPosition(currSpace.x, currSpace.y)!='L') {
                this.updatePosition('b', currSpace.x, currSpace.y);
                this.isRowValid(currSpace.x, currSpace.y);
                this.isColValid(currSpace.x, currSpace.y);
            }else {
                valid = false;
            }
            counter +=1;
        }
        return valid;
    }

    public boolean areWallsValid(){
        int counter = 0;
        int numBulbs;
        int currX;
        int currY;
        int currWallNum;
        boolean valid = true;
        Space currSpace;
        char currChar;

        while (valid && counter < wallLocations.size()){
            currSpace = wallLocations.get(counter);
            currX = currSpace.x;
            currY = currSpace.y;
            currChar = this.getPosition(currX,currY);
            if (Character.isDigit(currChar)){
                currWallNum = currChar-48;

                numBulbs = this.getNumBulbsAroundWall(currSpace);
                if (numBulbs!= currWallNum){
                    valid = false;
                }
            }
            counter++;
        }
        return valid;
    }

    public boolean areWallsOverloaded(){
        int counter = 0;
        int numBulbs;
        int currX;
        int currY;
        int currWallNum;
        boolean overloaded = true;
        Space currSpace;
        char currChar;

        while (overloaded && counter < wallLocations.size()){
            currSpace = wallLocations.get(counter);
            currX = currSpace.x;
            currY = currSpace.y;
            currChar = this.getPosition(currX,currY);
            if (Character.isDigit(currChar)){
                currWallNum = currChar-48;
                numBulbs = this.getNumBulbsAroundWall(currSpace);

                if (numBulbs <= currWallNum){
                    overloaded = false;
                }
            }
            counter++;
        }
        return overloaded;
    }

    public int getNumBulbsAroundWall(Space wall){
        int currX = wall.getX();
        int currY = wall.getY();
        int numBulbs = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if ((i==0 || j==0) && ((currX+i) >= 0) && ((currY+j) >= 0)
                        && ((currX+i) < this.layout.length) && ((currY+j) < this.layout[0].length)){
                    if (this.getPosition(currX+i,currY+j)=='b'){
                        numBulbs += 1;
                    }
                }
            }
        }
        return numBulbs;
    }
    public boolean isRowValid(int rowNum, int colNum){
        boolean valid = true;
        boolean noWall = true;
        int counter = colNum-1;
        while (valid && noWall && counter >= 0){
//            if (this.getPosition(rowNum, counter) == 'b'){
//                valid = false;
//            }else {
            if (Character.isDigit(this.getPosition(rowNum, counter))){
                noWall = false;
            }else {
                this.updatePosition('L',rowNum,counter);
            }
            //     }
            counter -= 1;
        }
        noWall = true;
        counter = colNum+1;
        while (valid && noWall && counter < this.layout[rowNum].length){
//            if (this.getPosition(rowNum,counter) == 'b'){
//                valid = false;
//            }else {
            if (Character.isDigit(this.getPosition(rowNum, counter))){
                noWall = false;
            }else {
                this.updatePosition('L', rowNum, counter);
            }
            // }
            counter+=1;
        }
        return valid;
    }

    public boolean isColValid(int rowNum, int colNum){
        boolean valid = true;
        boolean noWall = true;
        int counter = rowNum-1;
        while (valid && noWall && counter >= 0){
//            if (this.getPosition(counter, rowNum) == 'b'){
//                valid = false;
//            }else {
            if (Character.isDigit(this.getPosition(counter, colNum))){
                noWall = false;
            }else {
                this.updatePosition('L', counter, colNum);
            }
            //}
            counter -= 1;
        }
        noWall = true;
        counter = rowNum+1;
        while (valid && noWall && counter < this.layout.length){
//            if (this.getPosition(rowNum,counter) == 'b'){
//                valid = false;
//            }else {
            if (Character.isDigit(this.getPosition(counter, colNum))){
                noWall = false;
            }else {
                this.updatePosition('L', counter, colNum);
            }
            //  }
            counter+=1;
        }
        return valid;
    }

    public boolean validatePartialSolution(ArrayList<Space> bulbLocations){
        return this.areBulbsValid(bulbLocations) && this.areWallsValid();
    }

    public boolean checkForUnlitSpaces(){
        boolean valid = true;
        for (int i = 0; i < this.layout.length; i++) {
            for (int j = 0; j < this.layout[i].length; j++) {
                if (valid && layout[i][j]=='_'){
                    valid = false;
                }
            }
        }
        return valid;
    }

    public ArrayList<Space> findWhiteSpaces(){
        ArrayList<Space> tempList = new ArrayList<>();
        for (int i = 0; i < layout.length; i++) {
            for (int j = 0; j < layout[i].length; j++) {
                if (layout[i][j]=='_'){
                    tempList.add(new Space(i,j));
                }
            }
        }
        return tempList;
    }

    public void setAvailableSpacesToAllBlanks(){
        this.spacesAva = this.findWhiteSpaces();
    }

    public void lightSpace(Space toLight){
        Space temp;
        boolean found = false;
        int counter = 0;
        while (counter<this.spacesAva.size() && !found){
            temp = spacesAva.get(counter);
            if (temp.equals(toLight)){
                spacesLit.add(temp);
                spacesAva.remove(counter);
                found = true;
            }
            counter++;
        }
    }

    private void removeAvailableSpace(Space s){
        boolean found = false;
        int counter =0;
        while (!found && counter<spacesAva.size()){
            if (spacesAva.get(counter).equals(s)){
                spacesAva.remove(counter);
                found = true;
            }
            counter++;
        }
    }

    public void updateAvailableSpaces(){
        Space currSpace;
        for (int i = 0; i < spacesAva.size(); i++) {
            currSpace = spacesAva.get(i);
            if (layout[currSpace.getX()][currSpace.getY()]=='L'){
                spacesAva.remove(i);
                i--;
            }
        }

        int wallVal;
        int currX, currY;
        for (int i = 0; i < wallLocations.size(); i++) {
            currSpace = wallLocations.get(i);
            currX = currSpace.getX();
            currY = currSpace.getY();
            wallVal = layout[currX][currY]-48;
            if (wallVal<=this.getNumBulbsAroundWall(currSpace)){
                if (currX-1>0 && layout[currX-1][currY]=='_'){
                    removeAvailableSpace(new Space(currX-1, currY));
                }
                if (currX+1<layout.length && layout[currX+1][currY]=='_'){
                    removeAvailableSpace(new Space(currX+1, currY));
                }
                if (currY-1>0 && layout[currX][currY-1]=='_'){
                    removeAvailableSpace(new Space(currX, currY-1));
                }
                if (currY+1<layout[currX].length && layout[currX][currY+1]=='_'){
                    removeAvailableSpace(new Space(currX+1, currY));
                }
            }
        }
    }

    public void placeBulb(Space nowBulb){
        if (layout[nowBulb.getX()][nowBulb.getY()]=='_'){
            layout[nowBulb.getX()][nowBulb.getY()] = 'b';
        }
    }
}