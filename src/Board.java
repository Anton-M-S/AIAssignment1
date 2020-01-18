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
}