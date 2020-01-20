import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        char[][] newBoard = new char[0][0];
        int x = 0;
        int y = 0;
        Board board;
        try {
            Scanner fileScan = new Scanner(new FileReader(new File(args[0])));
            boolean isStart = true;
            String currline;
            String fullBoard = "";
            String[] dimensions;
            currline = fileScan.nextLine();
            while (fileScan.hasNext()) {
                if (currline.charAt(0)=='#') {
                    if (isStart) {
                        currline = fileScan.nextLine();
                        isStart = false;
                        dimensions = currline.split(" ");
                        x = Integer.parseInt(dimensions[0]);
                        y = Integer.parseInt(dimensions[1]);
                        newBoard = new char[x][y];

                    } else {
                        isStart = true;
                        currline = fileScan.nextLine();
                    }
                }else {
                    for (int i = 0; i < x; i++) {
                        currline = fileScan.nextLine();
                        for (int j = 0; j < y; j++) {
                            newBoard[i][j] = currline.charAt(j);
                        }
                    }
                    board = new Board(newBoard);
                    System.out.println(board);//call Search functions from this line
                    currline = fileScan.nextLine();
                }
            }

        } catch (Exception e) {
            System.out.println("error I guess");
        }
    }
}
