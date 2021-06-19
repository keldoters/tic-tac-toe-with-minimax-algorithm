package tictactoe;

import java.util.Scanner;

public class UI {
    private Scanner scanner;
    private Board board;

    public UI() {
        this.scanner = new Scanner(System.in);
        this.board = new Board();
    }

    public void start() {
        while(true){
            System.out.print("Input command: ");
            String input = scanner.nextLine();
            if (input.equals("exit")){
                break;
            }
            if (!input.matches("start (user|easy|medium|hard) (user|easy|medium|hard)")){
                System.out.println("Bad parameters!");
                continue;
            }
            Player player1;
            Player player2;
            String[] part = input.split(" ");
            if (part[1].equals("user") && part[2].equals("user")){
                player1 = new Player('X');
                player2 = new Player('O');
            } else if (part[1].equals("user") ){
                player1 = new Player('X');
                player2 = new AI('O',part[2]);
            } else if (part[2].equals("user")){
                player1 = new AI('X',part[1]);
                player2 = new Player('O');
            } else {
                player1 = new AI('X',part[1]);
                player2 = new AI('O',part[2]);
            }
            startGame(player1,player2);
        }
    }
    public void startGame(Player player1, Player player2){
        System.out.println(board);
        while (gameStillOngoing()) {
            try {
                if (board.checkIfPlayerTurn(player1.getSymbol())) {
                    player1.makeMove(board);
                } else {
                    player2.makeMove(board);
                }
                System.out.println(board);

            } catch (Exception e) {
                System.out.println("You should enter numbers!"+e.getMessage());
            }
        }
        board.emptyBoard();
    }
    public boolean gameStillOngoing(){
        switch (board.checkGameState()) {
            case -1:
                System.out.println("O wins");
                return false;
            case 1:
                System.out.println("X wins");
                return false;
        }
        if (!board.isMoveLeft()){
            System.out.println("draw");
            return false;
        }
        return true;
    }


}
