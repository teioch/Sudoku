import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Board class, handles GUI of board and control of cells and units within the board
 */
public class Board {
    private final int width = 9;
    private final int height = 9;
    private int[][] board;
    private boolean gameover;

    /* GUI objects */
    private JFrame frame;
    private JPanel mainPanel;
    private JPanel gamePanelOutter;
    private JPanel rightMenuPanel;
    private JPanel topLeft;
    private JPanel topMiddle;
    private JPanel topRight;
    private JPanel centerLeft;
    private JPanel centerMiddle;
    private JPanel centerRight;
    private JPanel bottomLeft;
    private JPanel bottomMiddle;
    private JPanel bottomRight;
    private JButton[][] buttonBoard;
    private JButton exitButton;
    private JButton resetButton;
    private JButton openButton;
    private JButton saveButton;

    public Board(){
        board = new int[width][height];
        buttonBoard = new JButton[width][height];
        gameover = false;
        frame = new JFrame();
        frame.setSize(700,450);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        createAndSetupGridPanels();

        for(int i = 0; i < 9; i++){
            for(int k = 0; k < 9; k++){
                board[i][k] = 0;
                buttonBoard[i][k] = new JButton("-");
                findQuadrant(i, k, buttonBoard[i][k]);
            }
        }
        buildGamePanel();

        setOpenButton();
        setSaveButton();
        setResetButton();
        setExitButton();

        mainPanel.add(gamePanelOutter);
        mainPanel.add(rightMenuPanel);
        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private void createAndSetupGridPanels() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1,2));
        gamePanelOutter = new JPanel();
        gamePanelOutter.setLayout(new GridLayout(3,3));
        rightMenuPanel = new JPanel();
        rightMenuPanel.setLayout(new GridLayout(4,1));

        topLeft = new JPanel();
        topLeft.setLayout(new GridLayout(3,3));
        topMiddle = new JPanel();
        topMiddle.setLayout(new GridLayout(3,3));
        topRight = new JPanel();
        topRight.setLayout(new GridLayout(3,3));

        centerLeft = new JPanel();
        centerLeft.setLayout(new GridLayout(3,3));
        centerMiddle = new JPanel();
        centerMiddle.setLayout(new GridLayout(3,3));
        centerRight = new JPanel();
        centerRight.setLayout(new GridLayout(3,3));

        bottomLeft = new JPanel();
        bottomLeft.setLayout(new GridLayout(3,3));
        bottomMiddle = new JPanel();
        bottomMiddle.setLayout(new GridLayout(3,3));
        bottomRight = new JPanel();
        bottomRight.setLayout(new GridLayout(3,3));
    }

    private void buildGamePanel() {
        gamePanelOutter.add(topLeft);
        gamePanelOutter.add(topMiddle);
        gamePanelOutter.add(topRight);

        gamePanelOutter.add(centerLeft);
        gamePanelOutter.add(centerMiddle);
        gamePanelOutter.add(centerRight);

        gamePanelOutter.add(bottomLeft);
        gamePanelOutter.add(bottomMiddle);
        gamePanelOutter.add(bottomRight);
    }

    private void setExitButton() {
        exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        rightMenuPanel.add(exitButton);
    }

    private void setResetButton() {
        resetButton = new JButton("Reset");
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        rightMenuPanel.add(resetButton);
    }

    private void setOpenButton() {
        openButton = new JButton("Open ... ");
        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        rightMenuPanel.add(openButton);
    }

    private void setSaveButton() {
        saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        rightMenuPanel.add(saveButton);
    }

    private void findQuadrant(int x, int y, JButton button){
        quadrantRow(x,y,button);
    }

    private void quadrantRow(int x, int y, JButton button){
        if(x < 3){
            quadrantCol(0, y, button);
        }
        else if(x >= 3 && x < 6){
            quadrantCol(1, y, button);
        }
        else{
            quadrantCol(2, y, button);
        }
    }

    private void quadrantCol(int quadrantRow, int y, JButton button){
        if(y < 3){
            addButtonToQuadrant(quadrantRow, 0, button);
        }
        else if(y >= 3 && y < 6){
            addButtonToQuadrant(quadrantRow, 1, button);
        }
        else{
            addButtonToQuadrant(quadrantRow, 2, button);
        }
    }

    private void addButtonToQuadrant(int quadrantRow, int quadrantCol, JButton button){
        JPanel[][] panels = new JPanel[][]{
                {topLeft,topMiddle,topRight},
                {centerLeft,centerMiddle,centerRight},
                {bottomLeft,bottomMiddle,bottomRight}
        };
        panels[quadrantRow][quadrantCol].add(button);
    }

    public void printBoard() {
        for (int i = 0; i < height; i++) {
            System.out.print("| ");
            for (int k = 0; k < width; k++) {
                if(board[i][k] == 0){
                    System.out.println("  | ");
                }
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

    public int getBoardAt(int x, int y){
        return board[x][y];
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public boolean isGameover(){
        return gameover;
    }
}
