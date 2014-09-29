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
                board[i][k] = 0;
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

    public boolean intExistsHorizontally(int nr, int col){
        for(int i = 0; i < height; i++){
            if(board[i][col] == nr){
                return true;
            }
        }
        return false;
    }

    public boolean intExistsVertically(int nr, int row){
        for(int i = 0; i < width; i++){
            if(board[row][i] == nr){
                return true;
            }
        }
        return false;
    }

    public boolean intExistsInBox(int nr, int row, int col){
        int rowBox = (row-1 / 3) * 3;
        int colBox = (col-1 / 3) * 3;

        for(int i = rowBox; i < rowBox + 3; i++){
            for(int k = colBox; k < colBox + 3; k++){
                if(board[i][k] == nr){
                    return true;
                }
            }
        }
        return false;
    }
}
