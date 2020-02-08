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
                if (layout[i][j] instanceof Wall) {//builds teh list of wall locations
                    tempNum = ((Wall) layout[i][j]).getWallNum();
                    wallLocations.add((Wall) layout[i][j]);
                    wallTotal += tempNum;
                }
            }
        }
    }

    //copy constructor
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
        Space currSpace;
        for (int i = 0; i < layout.length; i++) {
            for (int j = 0; j < layout[i].length; j++) {
                currSpace = layout[i][j];
                if (currSpace instanceof LitSpace) {
                    toReturn += "_";//don't print L for lit spaces (Clean up output)
                } else {
                    toReturn += currSpace.getSpaceType();
                }
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

    //checks that all bulbs are not lighting other bulbs, that all walls have correct number of
    //bulbs, and that there are no unlit spaces
    public boolean isBoardValid() {
        return this.areBulbsValid() && this.areWallsValid() && this.checkForUnlitSpaces();
    }

    //checks to make sure no bulbs are lighting other bulbs
    public boolean areBulbsValid() {
        boolean valid = true;
        int counter = 0;
        Space currSpace;
        Bulb newBulb;
        //while no validation errors have been found, and we still have bulbs to check
        while (valid && counter < this.spacesLit.size()) {
            currSpace = spacesLit.get(counter);
            //if the space is not already lit
            if (!(this.getPosition(currSpace.getX(), currSpace.getY()) instanceof LitSpace)) {
                newBulb = new Bulb(currSpace.getX(), currSpace.getY());
                this.updatePosition(new Bulb(currSpace.getX(), currSpace.getY()));//change the space to a bulb
                this.LightUpRow(newBulb);//light up the rows and and columns
                this.LightUpColumn(newBulb);//associated with the bulb
            } else {//otherwise the board is not valid
                valid = false;
            }
            counter += 1;
        }
        return valid;
    }


    //checks to see if the walls have the correct number of bulbs
    public boolean areWallsValid() {
        int counter = 0;
        int numBulbs;
        int currWallNum;
        boolean valid = true;
        Wall currSpace;
        //while all walls are valid and we have walls left to check
        while (valid && counter < wallLocations.size()) {
            currSpace = wallLocations.get(counter);

            currWallNum = currSpace.getWallNum();//get the number on teh wall

            numBulbs = this.getNumBulbsAroundWall(currSpace);//counts bulbs aroun teh wall
            if (numBulbs != currWallNum) {//if they d not match
                valid = false;
            }
            counter++;
        }
        return valid;
    }

    //specifically checks if any walls have too many bulbs (i.e. cannot be made valid)
    public boolean wallsNotOverloaded() {
        int counter = 0;
        int numBulbs;
        int currWallNum;
        boolean overloaded = true;
        Wall currSpace;
        while (overloaded && counter < wallLocations.size()) {
            currSpace = wallLocations.get(counter);
            currWallNum = currSpace.getWallNum();
            numBulbs = this.getNumBulbsAroundWall(currSpace);//count bulbs around teh wall

            if (numBulbs > currWallNum) {//if the wall has too many bulbs
                overloaded = false;
            }
            counter++;
        }
        return overloaded;
    }

    //counts the number of bulbs around a wall
    private int getNumBulbsAroundWall(Wall wall) {
        int currX = wall.getX();
        int currY = wall.getY();
        int numBulbs = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                //i==0 || j==0 means it does not check diagonals
                if ((i == 0 || j == 0) && ((currX + i) >= 0) && ((currY + j) >= 0)
                        && ((currX + i) < this.layout.length) && ((currY + j) < this.layout[0].length)) {
                    if (this.getPosition(currX + i, currY + j) instanceof Bulb) {//if the space being examined is a bulb
                        numBulbs += 1;
                    }
                }
            }
        }
        return numBulbs;
    }

    //after a bulb is placed, lights up the board row that the bulb is on
    public void LightUpRow(Bulb b) {
        boolean noWall = true;
        int rowNum = b.getX();
        int colNum = b.getY();
        int counter = colNum - 1;
        //move backwards from teh bulb, towards zero
        while (noWall && counter >= 0) {//as long as there is not a wall in teh way, and we aren't at the edge of the board
            noWall = lightUpFromBulb(rowNum, counter);//lights the current spaceeee

            counter -= 1;
        }
        noWall = true;//reset noWall
        counter = colNum + 1;
        //move forwards from teh bulb to the edge of the board
        while (noWall && counter < this.layout[rowNum].length) {

            noWall = lightUpFromBulb(rowNum, counter);

            counter += 1;
        }
    }

    //after a bulb is placed, lights up the board row that the bulb is on
    public void LightUpColumn(Bulb b) {
        boolean noWall = true;
        int rowNum = b.getX();
        int colNum = b.getY();
        int counter = rowNum - 1;
        //move backwards from teh bulb, towards zero
        while (noWall && counter >= 0) {
            noWall = lightUpFromBulb(counter, colNum);
            counter -= 1;
        }
        noWall = true;
        counter = rowNum + 1;
        //move forwards from teh bulb to the edge of the board
        while (noWall && counter < this.layout.length) {

            noWall = lightUpFromBulb(counter, colNum);

            counter += 1;
        }
    }

    private boolean lightUpFromBulb(int x, int y) {
        boolean noWall = true;
        //if it is a wall, notify the caller
        if (this.getPosition(x, y) instanceof Wall) {
            noWall = false;
        } else {
            this.updatePosition(new LitSpace(x, y));//otherwise, light the space
        }
        return noWall;
    }

    //checks if a solution is partially valid
    //this means that all walls have the correct number of bulbs, and no bulbs light other bulbs
    //there may still be white spaces
    public boolean validatePartialSolution() {
        return this.areBulbsValid() && this.areWallsValid();
    }

    //loops through the entire board, retuens false if it finds any unlit spcaes
    private boolean checkForUnlitSpaces() {
        boolean valid = true;
        int counter = 0;
        int counter2 = 0;
        while (valid && counter < this.layout.length) {
            while (valid && counter2 < this.layout[counter].length) {
                if (valid && layout[counter][counter2] instanceof UnlitSpace) {
                    valid = false;
                }
                counter2++;
            }
            counter++;
        }
//        for (int i = 0; i < layout.length; i++) {
//            for (int j = 0; j < layout[i].length; j++) {
//                if (valid && layout[i][j] instanceof UnlitSpace) {
//                    valid = false;
//                }
//            }
//        }
        return valid;
    }

    //loops through the board and creates a list of unlit spaces
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

    //the method to update spacesava to be all white spaces
    public void setAvailableSpacesToAllBlanks() {
        this.spacesAva = this.findWhiteSpaces();
    }

    //moves a lit space from spaces ava to spacesLit
    public void lightSpace(Space toLight) {
        Space temp;
        boolean found = false;
        int counter = 0;
        //if (layout[toLight.getX()][toLight.getY()]!='L') {
        while (counter < this.spacesAva.size() && !found) {
            temp = spacesAva.get(counter);
            if (temp.equals(toLight)) {//if the space is found
                spacesLit.add(temp);//add it to lit spaces
                spacesAva.remove(counter);//remove it from unlit
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
        Wall currWall;
        //removes unlit spaces next to walls that are already full
        for (int i = 0; i < wallLocations.size(); i++) {
            currWall = wallLocations.get(i);
            currX = currWall.getX();
            currY = currWall.getY();
            wallVal = ((Wall) layout[currX][currY]).getWallNum();
            if (wallVal <= this.getNumBulbsAroundWall(currWall)) {//if the wall is overloaded, or has the right number of bulbs
                //checks each space around the wall, removes it if unlit
                if (currX - 1 >= 0 && layout[currX - 1][currY] instanceof UnlitSpace) {
                    removeAvailableSpace(new Space(currX - 1, currY));
                }
                if (currX + 1 < layout.length && layout[currX + 1][currY] instanceof UnlitSpace) {
                    removeAvailableSpace(new Space(currX + 1, currY));
                }
                if (currY - 1 >= 0 && layout[currX][currY - 1] instanceof UnlitSpace) {
                    removeAvailableSpace(new Space(currX, currY - 1));
                }
                if (currY + 1 < layout[currX].length && layout[currX][currY + 1] instanceof UnlitSpace) {
                    removeAvailableSpace(new Space(currX + 1, currY));
                }
            }
        }//end wall space removal
    }

    //puts a b on a space to represent a bulb(necessary for removal of available spaces next to filled walls)
    public void placeBulb(Space nowBulb) {
        if (layout[nowBulb.getX()][nowBulb.getY()] instanceof UnlitSpace) {
            layout[nowBulb.getX()][nowBulb.getY()] = new Bulb(nowBulb.getX(), nowBulb.getY());
        }
    }

    //solves cases where bulbs must be placed
    public boolean solveGuaranteedBulbs() {
        boolean madeChange = false;//becomes true if a change has been made, used in loop for forward checking
        int counter = 0;
        Wall currWall;
        int currX, currY;
        int currChar;
        int bulbsNeeded;
        //check all walls
        while (counter < this.wallLocations.size()) {
            currWall = this.wallLocations.get(counter);
            currChar = currWall.getWallNum();

            if (currChar != 0) {//not a zero wall
                currX = currWall.getX();
                currY = currWall.getY();
                bulbsNeeded = countNumBulbsNeeded(currX, currY);//count the number of bulbs that the wall still needs beside it
                if (bulbsNeeded == currChar && getNumBulbsAroundWall(currWall) != currChar) {
                    placeBulbsAroundWall(currX, currY);//places bulbs on all available spaces around the wall
                    madeChange = true;
                    //checkForWallsNeedingFourBulbs(currX, currY);
                }
            }

            counter++;
        }
        return madeChange;
    }

    //counts the number of bulbs that a wall still needs
    private int countNumBulbsNeeded(int currX, int currY) {
        int numUnavailable = 0;//counts the number of spaces that are not unlit or contain a bulb
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if ((i == 0 || j == 0) && (i != 0 || j != 0)) {
                    //if the space is within the bounds, and its not unlit or a bulb
                    if (currX + i >= 0 && currX + i < layout.length && currY + j >= 0 && currY + j < layout[0].length) {
                        if (!(layout[currX + i][currY + j] instanceof UnlitSpace) && !(layout[currX + i][currY + j] instanceof Bulb)) {
                            numUnavailable++;
                        }
                    } else {
                        numUnavailable++;
                    }
                }
            }
        }
        return 4 - numUnavailable;
    }


    //places bulbs in all empty spaces around a wall
    private void placeBulbsAroundWall(int currX, int currY) {
        Bulb tempSpace;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 || j == 0) {
                    if ((currX + i < layout.length && currX + i >= 0
                            && currY + j < layout[0].length && currY + j >= 0) && !isWall(currX + i, currY + j)) {
                        if (!(layout[currX + i][currY + j] instanceof Bulb) && !(layout[currX + i][currY + j] instanceof LitSpace)) {
                            tempSpace = new Bulb(currX + i, currY + j);
                            this.placeBulb(tempSpace);//place the bulb
                            this.lightSpace(tempSpace); //move the bulb from available to lit
                            this.LightUpRow(tempSpace);//place lit spaces
                            this.LightUpColumn(tempSpace);//
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

    public int calculateH2(Space s){
        int constraining = 0;
        constraining+=this.countColumToWall(s);
        constraining+=this.countRowToWall(s);
        return constraining;
    }

    public int countColumToWall(Space b){
        boolean noWall = true;
        int rowNum = b.getX();
        int colNum = b.getY();
        int numspaces = 0;
        int counter = rowNum - 1;
        //move backwards from teh bulb, towards zero
        while (noWall && counter >= 0) {
            if (this.isWall(counter,colNum)){
                noWall = false;
            }else {
                numspaces++;
            }
            counter -= 1;
        }
        noWall = true;
        counter = rowNum + 1;
        //move forwards from teh bulb to the edge of the board
        while (noWall && counter < this.layout[rowNum].length) {

            if (this.isWall(counter,colNum)){
                noWall = false;
            }else {
                numspaces++;
            }
            counter += 1;
        }
        return numspaces;
    }

    public int countRowToWall(Space b){
        boolean noWall = true;
        int rowNum = b.getX();
        int colNum = b.getY();
        int numspaces = 0;
        int counter = colNum - 1;
        //move backwards from teh bulb, towards zero
        while (noWall && counter >= 0) {//as long as there is not a wall in teh way, and we aren't at the edge of the board
            if (this.isWall(rowNum,counter)){
                noWall = false;
            }else {
                numspaces++;
            }
            counter -= 1;
        }
        noWall = true;//reset noWall
        counter = colNum + 1;
        //move forwards from teh bulb to the edge of the board
        while (noWall && counter < this.layout.length) {

            if (this.isWall(rowNum,counter)){
                noWall = false;
            }else {
                numspaces++;
            }
            counter += 1;
        }
        return numspaces;
    }
}