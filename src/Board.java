import com.sun.org.apache.xpath.internal.SourceTree;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

/**
 * Board class, handles GUI of board and control of cells and units within the board
 */
public class Board{
    private final int width = 9;
    private final int height = 9;
    private int[][] board;
    private boolean gameover;

    private final String SAVE_FILE = "src/resources/savefiles/01.txt";
    private final String MEDIA_LOCATION = "src/resources/media/";

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
        frame = new JFrame();
        board = new int[width][height];
        buttonBoard = new JButton[width][height];
        gameover = false;

        frame.setSize(700, 400);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        createAndSetupGridPanels();
        buildButtonBoard();
        buildGamePanel();
        setMenuButtons();

        loadGame();

        mainPanel.add(gamePanelOutter);
        mainPanel.add(rightMenuPanel);

        //Add panel to JFrame and set visible
        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private void loadGame() {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(SAVE_FILE));
        } catch(FileNotFoundException e){
            System.out.println("ERROR: File could not be found: " + e.getMessage());
        }
        String line = null;
        int row = 0;
        int col = 0;

        try {
            line = reader.readLine();
            for(int i = 0; i < line.length(); i++){
                int nr = Integer.parseInt(line.substring(i, i+1));
                board[row][col] = nr;

                if(nr != 0) {
                    buttonBoard[row][col].setText(Integer.toString(nr));
                }
                else{
                    buttonBoard[row][col].setText(" ");
                }

                col++;
                if(col >= 9){
                    row++;
                    col = 0;
                }
            }
        } catch (IOException e){
            System.out.println("ERROR: IO error upon trying to read contents of save file: " + e.getMessage());
        }
    }

    private void createAndSetupGridPanels() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1,2));
        gamePanelOutter = new JPanel();
        gamePanelOutter.setLayout(new GridLayout(3, 3));
        rightMenuPanel = new JPanel();
        rightMenuPanel.setLayout(new GridLayout(4, 1));

        topLeft = new JPanel();
        topLeft.setLayout(new GridLayout(3, 3));
        topLeft.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        topMiddle = new JPanel();
        topMiddle.setLayout(new GridLayout(3, 3));
        topMiddle.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        topRight = new JPanel();
        topRight.setLayout(new GridLayout(3,3));
        topRight.setBorder(BorderFactory.createLineBorder(Color.BLUE));

        centerLeft = new JPanel();
        centerLeft.setLayout(new GridLayout(3,3));
        centerLeft.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        centerMiddle = new JPanel();
        centerMiddle.setLayout(new GridLayout(3,3));
        centerMiddle.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        centerRight = new JPanel();
        centerRight.setLayout(new GridLayout(3,3));
        centerRight.setBorder(BorderFactory.createLineBorder(Color.BLUE));

        bottomLeft = new JPanel();
        bottomLeft.setLayout(new GridLayout(3,3));
        bottomLeft.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        bottomMiddle = new JPanel();
        bottomMiddle.setLayout(new GridLayout(3,3));
        bottomMiddle.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        bottomRight = new JPanel();
        bottomRight.setLayout(new GridLayout(3, 3));
        bottomRight.setBorder(BorderFactory.createLineBorder(Color.BLUE));
    }

    private void buildButtonBoard() {
        for(int i = 0; i < 9; i++){
            for(int k = 0; k < 9; k++){
                final int iindex = i;
                final int kindex = k;

                board[i][k] = 0;
                buttonBoard[i][k] = new JButton(" ");
                buttonBoard[i][k].setMargin(new Insets(0,0,0,0));
                buttonBoard[i][k].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        final JFrame numbersFrame = new JFrame();
                        numbersFrame.setSize(275,275);
                        numbersFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                        numbersFrame.setLocationRelativeTo(null);

                        JPanel selectDigit = new JPanel(new GridLayout(3,3));
                        JButton[] digitButtons = new JButton[9];

                        for(int j = 0; j < 9; j++){
                            digitButtons[j] = new JButton(Integer.toString(j + 1));
                            final JButton digitButton = digitButtons[j];
                            digitButtons[j].addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    int buttonContent = Integer.parseInt(digitButton.getText());
                                    if(buttonContent > 0 && buttonContent < 10){
                                        if(intExistsHorizontally(buttonContent, iindex)){
                                            JOptionPane.showMessageDialog(null, "Number already exists horizontally in row " + (iindex+1), "Invalid input", JOptionPane.ERROR_MESSAGE);
                                        }
                                        else if(intExistsVertically(buttonContent, kindex)){
                                            JOptionPane.showMessageDialog(null, "Number already exists vertically in column " + (kindex + 1), "Invalid input", JOptionPane.ERROR_MESSAGE);
                                        }
                                        else if(intExistsInBox(buttonContent, iindex, kindex)){
                                            JOptionPane.showMessageDialog(null, "Number already exists in this quadrant", "Invalid input", JOptionPane.ERROR_MESSAGE);
                                        }
                                        else{
                                            board[iindex][kindex] = buttonContent;
                                            buttonBoard[iindex][kindex].setText("" + buttonContent);
                                            numbersFrame.dispose();
                                        }
                                    }
                                    else{
                                        JOptionPane.showMessageDialog(null, "Number must be in the range of 1-9", "Invalid input", JOptionPane.ERROR_MESSAGE);
                                    }
                                }
                            });
                            selectDigit.add(digitButtons[j]);
                        }
                        numbersFrame.add(selectDigit);
                        numbersFrame.setVisible(true);
                    }
                });

                findQuadrant(i, k, buttonBoard[i][k]);
            }
        }
    }

    public boolean isGameOver(){
        for(int i = 0; i < height; i++){
            for(int k = 0; k < width; k++){
                if(board[i][k] == 0){
                    return false;
                }
            }
        }
        return true;
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

    private void setMenuButtons() {
        setOpenButton();
        setSaveButton();
        setResetButton();
        setExitButton();
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

    public boolean intExistsHorizontally(int nr, int row){
        for(int i = 0; i < height; i++){
            if(board[row][i] == nr){
                return true;
            }
        }
        return false;
    }

    public boolean intExistsVertically(int nr, int col){
        for(int i = 0; i < width; i++){
            if(board[i][col] == nr){
                return true;
            }
        }
        return false;
    }

    public boolean intExistsInBox(int nr, int row, int col){
        int rowBox = (row / 3) * 3;
        int colBox = (col / 3) * 3;

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
