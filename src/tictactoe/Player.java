package tictactoe;

import java.util.Scanner;

public class Player {
    private Scanner scanner;
    protected char symbol;

    public Player(char symbol) {
        this.symbol = symbol;
        this.scanner = new Scanner(System.in);
    }

    public void makeMove(Board board) {
        while (board.checkIfPlayerTurn(symbol)) {
            System.out.print("Enter the coordinates: ");
            String[] coordinate = scanner.nextLine().split(" ");
            int x = Integer.parseInt(coordinate[1]) - 1;
            int y = Integer.parseInt(coordinate[0]) - 1;
            if (board.checkIfNotInRange(x, y)) {
                System.out.println("Coordinates should be from 1 to 3!");
                continue;
            }
            if (board.checkIfOccupied(x, y)) {
                System.out.println("This cell is occupied! Choose another one!");
                continue;
            }
            board.placeValue(x, y, symbol);
        }
    }

    public char getSymbol() {
        return this.symbol;

    }


}
