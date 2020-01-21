import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class ValidationTests {
    private char[][]testBoard;
    private Board singleLine;
    @Before
    public void setUp(){

    }
    @Test
    public void bulbToTheLeftOfCurrentInvalidates(){
        testBoard = new char[1][3];
        testBoard[0][0]='b';
        testBoard[0][1]='_';
        testBoard[0][2]='b';
        singleLine = new Board(testBoard);
        assertFalse(singleLine.isRowValid(0,2));
    }

    @Test
    public void bulbToTheRightOfCurrentInvalidates(){
        testBoard = new char[1][3];
        testBoard[0][0]='b';
        testBoard[0][1]='_';
        testBoard[0][2]='b';
        singleLine = new Board(testBoard);
        assertFalse(singleLine.isRowValid(0,0));
    }

    @Test
    public void wallInBetweenBulbsIsValid(){
        testBoard = new char[1][5];
        testBoard[0][0]='b';
        testBoard[0][1]='_';
        testBoard[0][2]='3';
        testBoard[0][3]='_';
        testBoard[0][4]='b';
        singleLine = new Board(testBoard);
        assertTrue(singleLine.isRowValid(0,4));
    }
}
