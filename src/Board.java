import java.util.ArrayList;

public class Board {
    private Space[][] layout;
    public ArrayList<Space> spacesAva;
    public ArrayList<Space> spacesLit;
    private ArrayList<Wall> wallLocations;
    private int wallTotal;

    public Board(Space[][] newBoard, ArrayList<Space> ava, ArrayList<Space> lit) {
        this.wallTotal = 0;
        this.spacesAva = ava;
        this.spacesLit = lit;
        this.wallLocations = new ArrayList<>();
        int tempNum;
        layout = new Space[newBoard.length][newBoard[0].length];
        for (int i = 0; i < layout.length; i++) {
            for (int j = 0; j < layout[i].length; j++) {
                layout[i][j] = newBoard[i][j];
                if (layout[i][j] instanceof Wall) {
                    tempNum = ((Wall) layout[i][j]).getWallNum();
                    wallLocations.add((Wall) layout[i][j]);
                    wallTotal += tempNum;
                }
            }
        }
    }

    public Board(Board b) {
        this.spacesAva = new ArrayList<>();
        this.spacesAva.addAll(b.spacesAva);
        this.spacesLit = new ArrayList<>();
        this.spacesLit.addAll(b.spacesLit);
        this.layout = new Space[b.layout.length][b.layout[0].length];
        this.wallLocations = new ArrayList<>();
        int tempNum;
        for (int i = 0; i < layout.length; i++) {
            for (int j = 0; j < layout[i].length; j++) {
                layout[i][j] = b.layout[i][j];
                if (layout[i][j] instanceof Wall) {
                    tempNum = ((Wall) layout[i][j]).getWallNum();
                    wallLocations.add((Wall) layout[i][j]);
                    wallTotal += tempNum;
                }
            }
        }

    }

    public int getWallTotal() {
        return wallTotal;
    }

    public String toString() {
        String toReturn = "";
        for (int i = 0; i < layout.length; i++) {
            for (int j = 0; j < layout[i].length; j++) {
                toReturn += layout[i][j].getSpaceType();
            }
            toReturn += '\n';
        }
        return toReturn;
    }

    public Space getPosition(int row, int col) {
        return layout[row][col];
    }

    private void updatePosition(Space newSpace) {
        layout[newSpace.getX()][newSpace.getY()] = newSpace;
    }

    public boolean isBoardValid(ArrayList<Space> bulbLocations) {
        boolean bulbsValid = this.areBulbsValid(bulbLocations);
        boolean wallsValid = this.areWallsValid();
        boolean boardValid = this.checkForUnlitSpaces();

        return bulbsValid && wallsValid && boardValid;
    }

    public boolean areBulbsValid(ArrayList<Space> bulbSpaces) {
        boolean valid = true;
        int counter = 0;
        Space currSpace;
        while (valid && counter < bulbSpaces.size()) {
            currSpace = bulbSpaces.get(counter);
            if (!(this.getPosition(currSpace.getX(), currSpace.getY()) instanceof LitSpace)) {
                this.updatePosition(new Bulb(currSpace.getX(), currSpace.getY()));
                this.isRowValid(currSpace.getX(), currSpace.getY());
                this.isColValid(currSpace.getX(), currSpace.getY());
            } else {
                valid = false;
            }
            counter += 1;
        }
        return valid;
    }

    public boolean areWallsValid() {
        int counter = 0;
        int numBulbs;
        int currX;
        int currY;
        int currWallNum;
        boolean valid = true;
        Wall currSpace;
        //Space currChar;

        while (valid && counter < wallLocations.size()) {
            currSpace = wallLocations.get(counter);
            currX = currSpace.getX();
            currY = currSpace.getY();
            //currChar = this.getPosition(currX,currY);
            // if (currSpace instanceof Wall){
            currWallNum = currSpace.getWallNum();

            numBulbs = this.getNumBulbsAroundWall(currSpace);
            if (numBulbs != currWallNum) {
                valid = false;
            }
            // }
            counter++;
        }
        return valid;
    }

    public boolean areWallsOverloaded() {
        int counter = 0;
        int numBulbs;
        int currWallNum;
        boolean overloaded = true;
        Wall currSpace;

        while (overloaded && counter < wallLocations.size()) {
            currSpace = wallLocations.get(counter);
            //currChar = this.getPosition(currX,currY);
            // if (currSpace instanceof Wall){
            currWallNum = currSpace.getWallNum();
            numBulbs = this.getNumBulbsAroundWall(currSpace);

            if (numBulbs <= currWallNum) {
                overloaded = false;
            }
            //  }
            counter++;
        }
        return overloaded;
    }

    private int getNumBulbsAroundWall(Space wall) {
        int currX = wall.getX();
        int currY = wall.getY();
        int numBulbs = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if ((i == 0 || j == 0) && ((currX + i) >= 0) && ((currY + j) >= 0)
                        && ((currX + i) < this.layout.length) && ((currY + j) < this.layout[0].length)) {
                    if (this.getPosition(currX + i, currY + j) instanceof Bulb) {
                        numBulbs += 1;
                    }
                }
            }
        }
        return numBulbs;
    }

    public void isRowValid(int rowNum, int colNum) {
        boolean noWall = true;
        int counter = colNum - 1;
        while (noWall && counter >= 0) {
            noWall = lightUpFromBulb(rowNum, counter, noWall);

            counter -= 1;
        }
        noWall = true;
        counter = colNum + 1;
        while (noWall && counter < this.layout[rowNum].length) {

            noWall = lightUpFromBulb(rowNum, counter, noWall);

            counter += 1;
        }
    }

    public void isColValid(int rowNum, int colNum) {
        boolean noWall = true;
        int counter = rowNum - 1;
        while (noWall && counter >= 0) {
            noWall = lightUpFromBulb(counter, colNum, noWall);
            counter -= 1;
        }
        noWall = true;
        counter = rowNum + 1;
        while (noWall && counter < this.layout.length) {

            noWall = lightUpFromBulb(counter, colNum, noWall);

            counter += 1;
        }
    }

    private boolean lightUpFromBulb(int x, int y, boolean noWall) {
        if (this.getPosition(x, y) instanceof Wall) {
            noWall = false;
        } else {
            this.updatePosition(new LitSpace(x, y));
        }
        return noWall;
    }

    public boolean validatePartialSolution(ArrayList<Space> bulbLocations) {
        return this.areBulbsValid(bulbLocations) && this.areWallsValid();
    }

    private boolean checkForUnlitSpaces() {
        boolean valid = true;
        for (int i = 0; i < this.layout.length; i++) {
            for (int j = 0; j < this.layout[i].length; j++) {
                if (valid && layout[i][j] instanceof UnlitSpace) {
                    valid = false;
                }
            }
        }
        return valid;
    }

    //changed to return a list of white spaces instead of updating spacesAva
    private ArrayList<Space> findWhiteSpaces() {
        ArrayList<Space> tempList = new ArrayList<>();
        for (int i = 0; i < layout.length; i++) {
            for (int j = 0; j < layout[i].length; j++) {
                if (layout[i][j] instanceof UnlitSpace) {
                    tempList.add(new Space(i, j));
                }
            }
        }
        return tempList;
    }

    //now the method to update spacesava
    public void setAvailableSpacesToAllBlanks() {
        this.spacesAva = this.findWhiteSpaces();
    }

    //moves a lit space from spaces ava to spacesLit
    //MAY NEED TO WRITE A NEW VERSION FOR FORWARD CHECKING THAT CKECLS IF THE SPACE IS LIT
    public void lightSpace(Space toLight) {
        Space temp;
        boolean found = false;
        int counter = 0;
        //if (layout[toLight.getX()][toLight.getY()]!='L') {
        while (counter < this.spacesAva.size() && !found) {
            temp = spacesAva.get(counter);
            if (temp.equals(toLight)) {
                spacesLit.add(temp);
                spacesAva.remove(counter);
                found = true;
            }
            counter++;
        }
        //}
    }

    //removes a space from spaces available
    private void removeAvailableSpace(Space s) {
        boolean found = false;
        int counter = 0;
        while (!found && counter < spacesAva.size()) {
            if (spacesAva.get(counter).equals(s)) {
                spacesAva.remove(counter);
                found = true;
            }
            counter++;
        }
    }

    //checks for available spaces that are no longer valid and removes them
    //USE FOR FORWARD CHECKING, WILL NEED TO BE ADVANCED
    public void updateAvailableSpaces() {
        //remove a space if it has a bulb lighting it
        Space currSpace;
        for (int i = 0; i < spacesAva.size(); i++) {
            currSpace = spacesAva.get(i);
            if (layout[currSpace.getX()][currSpace.getY()] instanceof LitSpace) {
                spacesAva.remove(i);
                i--;
            }
        }//end remove space if bulb is lighting it

        //remove spaces adjacent to walls that are already full
        int wallVal;
        int currX, currY;
        for (int i = 0; i < wallLocations.size(); i++) {
            currSpace = wallLocations.get(i);
            currX = currSpace.getX();
            currY = currSpace.getY();
            wallVal = ((Wall) layout[currX][currY]).getWallNum();
            if (wallVal <= this.getNumBulbsAroundWall(currSpace)) {
                if (currX - 1 > 0 && layout[currX - 1][currY] instanceof UnlitSpace) {
                    removeAvailableSpace(new Space(currX - 1, currY));
                }
                if (currX + 1 < layout.length && layout[currX + 1][currY] instanceof UnlitSpace) {
                    removeAvailableSpace(new Space(currX + 1, currY));
                }
                if (currY - 1 > 0 && layout[currX][currY - 1] instanceof UnlitSpace) {
                    removeAvailableSpace(new Space(currX, currY - 1));
                }
                if (currY + 1 < layout[currX].length && layout[currX][currY + 1] instanceof UnlitSpace) {
                    removeAvailableSpace(new Space(currX + 1, currY));
                }
            }
        }//end wall space removal
    }

    //puts a b on a space to represent a bulb(necessary for removal of available spaces next to filled walls)
    //USE FOR FORWARD CHECKING
    public void placeBulb(Space nowBulb) {
        if (layout[nowBulb.getX()][nowBulb.getY()] instanceof UnlitSpace) {
            layout[nowBulb.getX()][nowBulb.getY()] = new Bulb(nowBulb.getX(), nowBulb.getY());
        }
    }

    public void solveGuaranteedBulbs() {
        int counter = 0;
        Wall currWall;
        int currX, currY;
        int currChar;
        int bulbsNeeded;
        while (counter < this.wallLocations.size()) {
            currWall = this.wallLocations.get(counter);
            currX = currWall.getX();
            currY = currWall.getY();
            currChar = currWall.getWallNum();
            if (currChar != 0) {
                bulbsNeeded = countNumBulbsNeeded(currChar, currX, currY);
                if (bulbsNeeded == 4) {
                    checkForWallsNeedingFourBulbs(currX, currY);
                } else {
                    if (bulbsNeeded == 3) {
                        checkForWallsNeedingThreeBulbs(currX, currY);
                    } else {
                        if (bulbsNeeded == 2) {
                            checkForWallsNeedingTwoBulbs(currX, currY);
                        }
                    }
                }
            }

            counter++;
        }
    }

    private void checkForWallsNeedingTwoBulbs(int currX, int currY) {

    }

    private void checkForWallsNeedingThreeBulbs(int currX, int currY) {
        if ((currX + 1 >= layout.length || currX - 1 < 0 || currY + 1 >= layout[currX].length || currY - 1 < 0
                || isWall(currX + 1, currY) || isWall(currX - 1, currY)
                || isWall(currX, currY - 1) || isWall(currX, currY + 1))) {
            placeBulbsAroundWall(currX, currY);
        }
    }

    private void checkForWallsNeedingFourBulbs(int currX, int currY) {
        Space tempSpace;

        if (!(layout[currX + 1][currY] instanceof Bulb)) {
            tempSpace = new Space(currX + 1, currY);
            this.placeBulb(tempSpace);
            this.lightSpace(tempSpace);
        }
        if (!(layout[currX - 1][currY] instanceof Bulb)) {
            tempSpace = new Space(currX - 1, currY);
            this.placeBulb(tempSpace);
            this.lightSpace(tempSpace);
        }
        if (!(layout[currX][currY + 1] instanceof Bulb)) {
            tempSpace = new Space(currX, currY + 1);
            this.placeBulb(tempSpace);
            this.lightSpace(tempSpace);
        }
        if (!(layout[currX][currY - 1] instanceof Bulb)) {
            tempSpace = new Space(currX, currY - 1);
            this.placeBulb(tempSpace);
            this.lightSpace(tempSpace);
        }
    }

    private int countNumBulbsNeeded(int currChar, int currX, int currY) {
        int numUnavailable = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <=1; j++) {
                if((i==0 || j==0) && (i!=0 || j!=0)){
                    if (currX+i>=0 && currX+i<layout.length && currY+j>=0 && currY+j<layout[0].length){
                        if (!(layout[currX+i][currY+j] instanceof UnlitSpace)){
                            numUnavailable++;
                        }
                    }else {
                        numUnavailable++;
                    }
                }
            }
        }
        return 4-numUnavailable;
    }

    private void placeBulbsAroundWall(int currX, int currY) {
        Space tempSpace;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 || j == 0) {
                    if ((currX + i < layout.length && currX + i >= 0
                            && currY + j < layout[0].length && currY + j >= 0) && !isWall(currX + i, currY + j)) {
                        if (!(layout[currX + i][currY + j] instanceof Bulb)) {
                            tempSpace = new Space(currX + i, currY + j);
                            this.placeBulb(tempSpace);
                            this.lightSpace(tempSpace);
                            this.isRowValid(tempSpace.getX(), tempSpace.getY());
                            this.isColValid(tempSpace.getX(), tempSpace.getY());
                        }
                    }
                }
            }
        }
    }

    public boolean isWall(int x, int y) {
        boolean isWall = false;
        if (x >= 0 && x < layout.length && y >= 0 && y < layout[0].length) {
            isWall = layout[x][y] instanceof Wall;
        }
        return isWall;
    }

    public boolean checkIfSpaceisWIthinBoardBounds(int x, int y) {
        return x >= 0 && x < layout.length && y >= 0 && y < layout[x].length;
    }
}