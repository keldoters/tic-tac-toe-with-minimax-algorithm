package tictactoe;

import java.util.Random;

public class AI extends Player {

    private Random random;
    private String difficulty;
    private char opponent;

    public AI(char symbol, String difficulty) {
        super(symbol);
        this.difficulty = difficulty;
        this.random = new Random();
        if (this.getSymbol() == 'X') {
            this.opponent = 'O';
        } else {
            this.opponent = 'X';
        }
    }
    @Override
    public void makeMove(Board board) {
        switch (difficulty) {
            case "easy":
                System.out.println("Making move level \"easy\"");
                makeMoveEasy(board);
                break;
            case "medium":
                System.out.println("Making move level \"medium\"");
                makeMoveMedium(board);
                break;
            case "hard":
                System.out.println("Making move level \"hard\"");
                makeMoveHard(board);
                break;
        }
    }
    public void makeMoveEasy(Board board) {
        int x;
        int y;
        while (true) {
            x = random.nextInt(3);
            y = random.nextInt(3);
            if (!board.checkIfOccupied(x, y)) {
                break;
            }
        }
        board.placeValue(x, y, this.getSymbol());
    }

    public void makeMoveMedium(Board board) {
        int x;
        int y;

        int[] setMove = checkIfGonnaWin(board, this.getSymbol());
        if (setMove == null) {
            setMove = checkIfGonnaWin(board, opponent);
        }
        if (setMove != null) {
            y = setMove[0];
            x = setMove[1];
            board.placeValue(x, y, this.getSymbol());
            return;
        }
        while (true) {
            x = random.nextInt(3);
            y = random.nextInt(3);
            if (!board.checkIfOccupied(x, y)) {
                break;
            }
        }
        board.placeValue(x, y, this.getSymbol());
    }
    public int[] checkIfGonnaWin(Board board, char symbol) {
        int y = 0;
        int x = 0;

        for (int i = 0; i < 3; i++) {
            int horizontal = 0;
            for (int j = 0; j < 3; j++) {
                char value = board.getBoard()[i][j];
                if (symbol == value || value == ' ') {
                    horizontal += value;
                }
                if (value == ' ') {
                    x = j;
                    y = i;
                }
            }
            if (horizontal == 2 * symbol + ' ') {
                return new int[]{y, x};
            }

        }
        for (int i = 0; i < 3; i++) {
            int vertical = 0;
            for (int j = 0; j < 3; j++) {
                char value = board.getBoard()[j][i];
                if (symbol == value || value == ' ') {
                    vertical += value;
                }
                if (value == ' ') {
                    x = i;
                    y = j;
                }
            }
            if (vertical == 2 * symbol + ' ') {
                return new int[]{y, x};
            }
        }
        for (int i = 0; i < 3; i++) {
            int diagDown = 0;
            for (int j = 0; j < 3; j++) {
                char value = board.getBoard()[j][j];
                if (symbol == value || value == ' ') {
                    diagDown += value;
                }
                if (value == ' ') {
                    x = j;
                    y = j;
                }
            }
            if (diagDown == 2 * symbol + ' ') {
                System.out.println("orayt");
                return new int[]{y, x};
            }
        }
        for (int i = 0; i < 3; i++) {
            int diagUp = 0;
            for (int j = 0; j < 3; j++) {
                char value = board.getBoard()[j][2 - j];
                if (symbol == value || value == ' ') {
                    diagUp += value;
                }
                if (value == ' ') {
                    x = 2 - j;
                    y = j;
                }
            }
            if (diagUp == 2 * symbol + ' ') {
                return new int[]{y, x};
            }
        }
        return null;
    }
    public void makeMoveHard(Board board){
        int[] move = bestMove(board);
        board.placeValue(move[1],move[0],this.symbol);
    }
    public int minimax(Board board,int depth, boolean isMax){
        if (evaluate(board) == 1) {
            return 10;
        }
        if (evaluate(board) == -1) {
            return -10;
        }
        if (board.isMoveLeft() == false){
            return 0;
        }
        if (isMax){
            int bestScore = -1000;
            for (int i = 0; i < 3; i++){
                for (int j = 0; j < 3; j++){
                    if (board.getBoard()[i][j] == ' '){
                        board.getBoard()[i][j] = this.symbol;
                        int score = minimax(board,depth +1, false);
                        board.getBoard()[i][j] = ' ';
                        bestScore = Math.max(score, bestScore);
                    }
                }
            }
            return bestScore;
        }
        else {
            int bestScore = 1000;
            for (int i = 0; i < 3; i++){
                for (int j = 0; j < 3; j++){
                    if (board.getBoard()[i][j] == ' '){
                        board.getBoard()[i][j] = opponent;
                        int score = minimax(board,depth +1, true);
                        board.getBoard()[i][j] = ' ';
                        bestScore = Math.min(score, bestScore);
                    }
                }
            }
            return bestScore;
        }
    }

    public int[] bestMove(Board board){
        int bestScore = -1000;
        int x = -1;
        int y = -1;
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                if (board.getBoard()[i][j] == ' ') {
                    board.getBoard()[i][j] = this.symbol;
                    int score = minimax(board,0, false);
                    board.getBoard()[i][j] = ' ';
                    if (score > bestScore) {
                        x = j;
                        y = i;
                        bestScore = score;
                    }
                }
            }
        }
        System.out.println(bestScore);
        return new int[]{y, x};
    }
    public int evaluate(Board board){
        int diag1 = 0;
        int horizontal = 0;
        int vertical = 0;
        int diag2 = 0;
        for(int i = 0; i<3; i++){
            for(int j = 0; j<3; j++){
                horizontal = horizontal + board.getBoard()[i][j];
                vertical = vertical + board.getBoard()[j][i];
                diag1 += board.getBoard()[j][j];
                diag2 = diag2 + board.getBoard()[j][2-j];
            }
            if (horizontal == 3*this.opponent || vertical == 3*this.opponent
                 || diag1 == 3*this.opponent  || diag2 == 3*this.opponent){
                return -1;
            }
            if (horizontal == 3*this.symbol || vertical == 3*this.symbol
                 || diag1 == 3*this.symbol  || diag2 == 3*this.symbol){
                return 1;
            }
            horizontal = 0;
            vertical = 0;
            diag1 = 0;
            diag2 = 0;
        }
        return 0;
    }


}






