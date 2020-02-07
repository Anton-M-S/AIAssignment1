 import org.junit.Before;
 import org.junit.Test;

 import java.util.ArrayList;

 import static junit.framework.TestCase.assertEquals;
 import static junit.framework.TestCase.assertFalse;
 import static junit.framework.TestCase.assertTrue;

 public class ValidationTests {
     private Space[][]testBoard;
     private ArrayList<Space> testList;
     private Board singleLine;
     @Before
     public void setUp(){

     }
     @Test
     public void bulbToTheLeftOfCurrentInvalidates(){
         testBoard = new Space[1][3];
         testBoard[0][0]=new UnlitSpace(0,0);
         testBoard[0][1]=new UnlitSpace(0,1);
         testBoard[0][2]=new UnlitSpace(0,2);
         testList = new ArrayList<>();
         testList.add(new Space(0,0));
         testList.add(new Space(0,2));
         singleLine = new Board(testBoard, testList, new ArrayList<>());
         singleLine.LightUpRow(0,0);
         assertTrue(singleLine.getPosition(0,1) instanceof LitSpace);
     }

     @Test
     public void wallWithTooFewBulbsNotOverloaded(){
         testBoard = new Space[3][3];
         testBoard[0][0] = new UnlitSpace(0,0);
         testBoard[0][1] = new Bulb(0,1);
         testBoard[0][2] = new UnlitSpace(0,2);
         testBoard[1][0] = new Bulb(1,0);
         testBoard[1][1] = new Wall(1,1,'4');
         testBoard[1][2] = new UnlitSpace(1,2);
         testBoard[2][0] = new UnlitSpace(2,0);
         testBoard[2][1] = new Bulb(2,1);
         testBoard[2][2] = new UnlitSpace(2,2);
         testList = new ArrayList<>();
         testList.add(new Wall(1,1, '4'));
         singleLine = new Board(testBoard, new ArrayList<Space>(), testList);
         assertFalse(singleLine.wallsNotOverloaded());
     }

//     @Test
//     public void bulbToTheRightOfCurrentInvalidates(){
//         testBoard = new Space[1][3];
//         testBoard[0][0]=new Bulb(0,0);
//         testBoard[0][1]=new UnlitSpace(0,1);
//         testBoard[0][2]=new Bulb(0,2);
//         singleLine = new Board(testBoard);
//         assertFalse(singleLine.isRowValid(0,0));
//     }

//     @Test
//     public void wallInBetweenBulbsIsValid(){
//         testBoard = new char[1][5];
//         testBoard[0][0]='b';
//         testBoard[0][1]='_';
//         testBoard[0][2]='3';
//         testBoard[0][3]='_';
//         testBoard[0][4]='b';
//         singleLine = new Board(testBoard);
//         assertTrue(singleLine.isRowValid(0,0));
//     }

     @Test
     public void checkWholeBoardIsInvalidNoWalls(){
         testBoard = new Space[1][3];
         testBoard[0][0]=new UnlitSpace(0,0);
         testBoard[0][1]=new UnlitSpace(0,1);
         testBoard[0][2]=new UnlitSpace(0,2);
         testList = new ArrayList<>();
         testList.add(new Space(0,0));
         testList.add(new Space(0,2));
         singleLine = new Board(testBoard, new ArrayList<Space>(), testList);
         assertFalse(singleLine.areBulbsValid());
     }

     @Test
     public void checkValidBoardCorrectly(){
         testBoard = new Space[1][5];
         testBoard[0][0]=new Bulb(0,0);
         testBoard[0][1]=new UnlitSpace(0,1);
         testBoard[0][2]=new Wall(0,2,3);
         testBoard[0][3]=new UnlitSpace(0,3);
         testBoard[0][4]=new Bulb(0,4);
         testList = new ArrayList<>();
         testList.add(new Space(0,0));
         testList.add(new Space(0,4));
         singleLine = new Board(testBoard, new ArrayList<Space>(), testList);
         assertTrue(singleLine.areBulbsValid());
       }
//
     @Test
     public void checkWallFull(){
         testBoard = new Space[3][3];
         testBoard[0][0] = new UnlitSpace(0,0);
         testBoard[0][1] = new Bulb(0,1);
         testBoard[0][2] = new UnlitSpace(0,2);
         testBoard[1][0] = new Bulb(1,0);
         testBoard[1][1] = new Wall(1,1,'4');
         testBoard[1][2] = new Bulb(1,2);
         testBoard[2][0] = new UnlitSpace(2,0);
         testBoard[2][1] = new Bulb(2,1);
         testBoard[2][2] = new UnlitSpace(2,2);
         testList = new ArrayList<>();
         testList.add(new Space(1,1));
         singleLine = new Board(testBoard, new ArrayList<Space>(), testList);
         assertTrue(singleLine.areWallsValid());
     }
//
     @Test
     public void checkTomakeSureEdgesDontBreakThis(){
         testBoard = new Space[1][3];
         testBoard[0][0]=new Bulb(0,0);
         testBoard[0][1]=new Wall(0,1,2);
         testBoard[0][2]=new Bulb(0,2);
         testList = new ArrayList<>();
         testList.add(new Space(0,0));
         testList.add(new Space(0,2));
         singleLine = new Board(testBoard, new ArrayList<Space>(), testList);
         testList.add(new Space(0,1));
         assertTrue(singleLine.areWallsValid());
     }
//
     @Test
     public void partialSolutionValidates(){
         testBoard = new Space[3][3];
         testBoard[0][0]= new UnlitSpace(0,0);
         testBoard[0][1]= new UnlitSpace(0,1);
         testBoard[0][2]= new UnlitSpace(0,2);
         testBoard[1][0]= new UnlitSpace(1,0);
         testBoard[1][1]= new Wall(1,1,3);
         testBoard[1][2]= new UnlitSpace(1,2);
         testBoard[2][0]= new UnlitSpace(2,0);
         testBoard[2][1]= new UnlitSpace(2,1);
         testBoard[2][2]= new UnlitSpace(2,2);
         testList = new ArrayList<>();
         testList.add(new Space(0,1));
         testList.add(new Space(1,0));
         testList.add(new Space(1,2));
         singleLine = new Board(testBoard, new ArrayList<>(), testList);
         assertTrue(singleLine.validatePartialSolution());
     }
//
     @Test
     public void partialSolutionInvalidates(){
         testBoard = new Space[3][3];
         testBoard[0][0]= new UnlitSpace(0,0);
         testBoard[0][1]= new UnlitSpace(0,1);
         testBoard[0][2]= new UnlitSpace(0,2);
         testBoard[1][0]= new UnlitSpace(1,0);
         testBoard[1][1]= new Wall(1,1,3);
         testBoard[1][2]= new UnlitSpace(1,2);
         testBoard[2][0]= new UnlitSpace(2,0);
         testBoard[2][1]= new UnlitSpace(2,1);
         testBoard[2][2]= new UnlitSpace(2,2);
         testList = new ArrayList<>();
         testList.add(new Space(0,1));
         testList.add(new Space(1,0));
         testList.add(new Space(1,2));
         testList.add(new Space(2,0));
         singleLine = new Board(testBoard, new ArrayList<>(), testList);
         assertFalse(singleLine.validatePartialSolution());
     }
//
//     @Test
//     public void validateFullBoard(){
//         testBoard = new char[][]{{'_','_','_','_','_','1','_'},
//                 {'2','_','_','_','_','_','_'},
//                 {'_','_','_','_','_','0','_'},
//                 {'_','_','_','_','_','_','_'},
//                 {'_','1','_','_','_','_','_'},
//                 {'_','_','_','_','_','_','2'},
//                 {'_','_','_','_','_','_','_'}};
//         testList = new ArrayList<>();
//         testList.add(new Space(1,5));
//         testList.add(new Space(2,0));
//         testList.add(new Space(4,2));
//         testList.add(new Space(3,3));
//         testList.add(new Space(5,5));
//         testList.add(new Space(6,6));
//         singleLine = new Board(testBoard, new ArrayList<>(), testList);
//         assertFalse(singleLine.isBoardValid(testList));
//     }
 }
