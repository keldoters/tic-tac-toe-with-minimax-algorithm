package tictactoe;

public class Board {

    private char[][] board;
    private int counter;

    public Board(){
        this.board = new char[3][3];
        emptyBoard();
    }
    public void emptyBoard(){
        for (int i=0; i<9; i++){
            board[i/3][i%3] = ' ';
        }
        counter = 0;
    }
    public char[][] getBoard(){
        return board;
    }
    public boolean checkIfPlayerTurn(char symbol){
        return counter%2 == 0 && symbol == 'X' || counter%2 != 0 && symbol == 'O';
    }
    public void placeValue(int x, int y, char symbol){
        board[y][x] = symbol;
        counter ++;
    }
    public boolean checkIfOccupied(int x, int y){
        return board[y][x] != ' ';
    }
    public boolean checkIfNotInRange(int x, int y){
        return x<0 || x>2 || y<0 || y>2;
    }

    public boolean isMoveLeft(){
        for (int i = 0; i<3; i++){
            for (int j = 0; j<3; j++){
                if (board[i][j]==' '){
                    return true;
                }
            }
        }
        return false;
    }

    public int checkGameState(){
        int diag1 = 0;
        int horizontal = 0;
        int vertical = 0;
        int diag2 = 0;
        for(int i = 0; i<board.length; i++){
            for(int j = 0; j<board.length; j++){
                horizontal = horizontal + board[i][j];
                vertical = vertical + board[j][i];
                diag1 += board[j][j];
                diag2 = diag2 + board[j][2-j];
            }
            if (horizontal == 237 || vertical == 237 || diag1 == 237  || diag2 == 237){
                return -1;
            }
            if (horizontal == 264 || vertical == 264 || diag1 == 264  || diag2 == 264){
                return 1;
            }
            horizontal = 0;
            vertical = 0;
            diag1 = 0;
            diag2 = 0;
        }
        return 0;
    }
    @Override
    public String toString(){
        StringBuilder string = new StringBuilder();
        string.append("---------\n");
        for (int i=0; i<board.length; i++){
            string.append("| ");
            for (int j=0; j<board.length; j++){
                string.append(board[i][j]).append(" ");
            }
            string.append("|\n");
        }
        string.append("---------");
        return string.toString();
    }




}
