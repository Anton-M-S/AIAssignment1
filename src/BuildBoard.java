//import java.io.File;
//import java.io.FileReader;
//import java.util.Scanner;
//
//public class BuildBoard {
//    public static Space[][] build(String[] args){
//        Space[][] newBoard;
//        int x = 0;
//        int y = 0;
//        try {
//        Scanner fileScan = new Scanner(new FileReader(new File(args[0])));
//        boolean isStart = true;
//        String currline;
//        String[] dimensions;
//        currline = fileScan.nextLine();
//        Space newSpace = null;
//        char currChar;
//        while (fileScan.hasNext()) {
//            if (currline.charAt(0) == '#') {
//                if (isStart) {
//                    while (currline.charAt(0) == '#') {
//                        currline = fileScan.nextLine();
//                    }
//                    isStart = false;
//                    dimensions = currline.split(" ");
//                    x = Integer.parseInt(dimensions[0]);
//                    y = Integer.parseInt(dimensions[1]);
//                    newBoard = new Space[x][y];
//
//                } else {
//                    isStart = true;
//                    currline = fileScan.nextLine();
//                }
//            } else {
//                for (int i = 0; i < x; i++) {
//                    currline = fileScan.nextLine();
//                    for (int j = 0; j < y; j++) {
//                        currChar = currline.charAt(j);
//                        if (currChar == '_') {
//                            newSpace = new UnlitSpace(i, j);
//                        } else {
//                            if (Character.isDigit(currChar)) {
//                                newSpace = new Wall(i, j, currChar);
//                            }
//                        }
//                        newBoard[i][j] = newSpace;
//                    }
//                }
//            }
//        return newBoard;
//    }
//}
