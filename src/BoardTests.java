import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class BoardTests {
    private Space[][] testBoard;
    private Board newBoard;
    private ArrayList<Space> spacesAva;
    private ArrayList<Space> spacesLit;

    @Test
    public void fourWallsAutomaticallyPlacesBulbs() {
        testBoard = new Space[][]{{new UnlitSpace(0, 0), new UnlitSpace(0, 1), new UnlitSpace(0, 2)},
                {new UnlitSpace(1, 0), new Wall(1, 1, 4), new UnlitSpace(1, 2)},
                {new UnlitSpace(2, 0), new UnlitSpace(2, 1), new UnlitSpace(2, 2)}};
        spacesAva = new ArrayList<>();
        spacesAva.add(new Space(0, 1));
        spacesAva.add(new Space(1, 0));
        spacesAva.add(new Space(1, 2));
        spacesAva.add(new Space(2, 1));
        newBoard = new Board(testBoard, new ArrayList<>(), new ArrayList<>());
        newBoard.solveGuaranteedBulbs();
        assertTrue(newBoard.getPosition(0, 1) instanceof Bulb);
        assertTrue(newBoard.getPosition(1, 0) instanceof Bulb);
        assertTrue(newBoard.getPosition(1, 2) instanceof Bulb);
        assertTrue(newBoard.getPosition(2, 1) instanceof Bulb);
    }

    @Test
    public void threeWallsDoesNotPlaceBulbs() {
        testBoard = new Space[][]{{new UnlitSpace(0, 0), new UnlitSpace(0, 1), new UnlitSpace(0, 2)},
                {new UnlitSpace(1, 0), new Wall(1, 1, 3), new UnlitSpace(1, 2)},
                {new UnlitSpace(2, 0), new UnlitSpace(2, 1), new UnlitSpace(2, 2)}};
        spacesAva = new ArrayList<>();
        spacesAva.add(new Space(0, 1));
        spacesAva.add(new Space(1, 0));
        spacesAva.add(new Space(1, 2));
        spacesAva.add(new Space(2, 1));
        newBoard = new Board(testBoard, new ArrayList<>(), new ArrayList<>());
        newBoard.solveGuaranteedBulbs();
        assertFalse(newBoard.getPosition(0, 1) instanceof Bulb);
        assertFalse(newBoard.getPosition(1, 0) instanceof Bulb);
        assertFalse(newBoard.getPosition(1, 2) instanceof Bulb);
        assertFalse(newBoard.getPosition(2, 1) instanceof Bulb);
    }

    @Test
    public void alreadyPresentBulbDoesntCauseIssues(){
        testBoard = new Space[][]{{new UnlitSpace(0, 0), new UnlitSpace(0, 1), new UnlitSpace(0, 2)},
                {new UnlitSpace(1, 0), new Wall(1, 1, 4), new Bulb(1, 2)},
                {new UnlitSpace(2, 0), new UnlitSpace(2, 1), new UnlitSpace(2, 2)}};
        spacesAva = new ArrayList<>();
        spacesAva.add(new Space(0, 1));
        spacesAva.add(new Space(1, 0));
        spacesAva.add(new Space(1, 2));
        spacesAva.add(new Space(2, 1));
        newBoard = new Board(testBoard, new ArrayList<>(), new ArrayList<>());
        newBoard.solveGuaranteedBulbs();
        assertTrue(newBoard.getPosition(0, 1) instanceof Bulb);
        assertTrue(newBoard.getPosition(1, 0) instanceof Bulb);
        assertTrue(newBoard.getPosition(1, 2) instanceof Bulb);
        assertTrue(newBoard.getPosition(2, 1) instanceof Bulb);
    }
    @Test
    public void threeWallOnEdgePlaces3Bulbs(){
        testBoard = new Space[][]{{new UnlitSpace(0, 0), new UnlitSpace(0, 1), new UnlitSpace(0, 2)},
                {new Wall(1, 0,3), new UnlitSpace(1, 1), new UnlitSpace(1, 2)},
                {new UnlitSpace(2, 0), new UnlitSpace(2, 1), new UnlitSpace(2, 2)}};
        spacesAva = new ArrayList<>();
        spacesAva.add(new Space(0,0));
        spacesAva.add(new Space(1,1));
        spacesAva.add(new Space(2,0));
        newBoard = new Board(testBoard, new ArrayList<>(),new ArrayList<>());
        newBoard.solveGuaranteedBulbs();
        assertTrue(newBoard.getPosition(0, 0) instanceof Bulb);
        assertTrue(newBoard.getPosition(1, 1) instanceof Bulb);
        assertTrue(newBoard.getPosition(2, 0) instanceof Bulb);
    }

    @Test
    public void threeWallnextToAnotherWallplaces3Bulbs(){
        testBoard = new Space[][]{{new UnlitSpace(0, 0), new UnlitSpace(0, 1), new UnlitSpace(0, 2)},
                {new Wall(1, 0,0), new Wall(1, 1, 3), new UnlitSpace(1, 2)},
                {new UnlitSpace(2, 0), new UnlitSpace(2, 1), new UnlitSpace(2, 2)}};
        spacesAva = new ArrayList<>();
        spacesAva.add(new Space(0,1));
        spacesAva.add(new Space(1,2));
        spacesAva.add(new Space(2,1));
        newBoard = new Board(testBoard, new ArrayList<>(),new ArrayList<>());
        newBoard.solveGuaranteedBulbs();
        assertTrue(newBoard.getPosition(0, 1) instanceof Bulb);
        assertTrue(newBoard.getPosition(1, 2) instanceof Bulb);
        assertTrue(newBoard.getPosition(2, 1) instanceof Bulb);
    }
}
