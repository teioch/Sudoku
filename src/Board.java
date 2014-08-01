/**
 * Created by Teioch on 01.08.2014.
 */
public class Board {
    private final int width = 9;
    private final int height = 9;
    private int[][] board;

    public Board(){
        board = new int[width][height];
        for(int i = 0; i < 9; i++){
            for(int k = 0; k < 9; k++){
                board[i][k] = k+1;
            }
        }
    }

    public void printBoard() {
        for (int i = 0; i < height; i++) {
            System.out.print("| ");
            for (int k = 0; k < width; k++) {
                System.out.print(board[i][k] + " | ");
            }
            System.out.println();
            System.out.println("-------------------------------------");

        }
    }
}
