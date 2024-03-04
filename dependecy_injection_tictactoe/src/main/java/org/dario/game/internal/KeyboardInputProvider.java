 
package org.dario.game.internal;

import java.util.Scanner;

class KeyboardInputProvider implements InputProvider {
    private final Scanner scanner = new Scanner(System.in);
    private final BoardDimensions boardDimensions;

    public KeyboardInputProvider(BoardDimensions boardDimensions) {
        this.boardDimensions = boardDimensions;
    }

    @Override
    public BoardLocation provideNextMove(Board board) {
        int row;
        int column;
        do {
            System.out.print("Please choose row: ");
            row = scanner.nextInt()-1;
            System.out.print("Please choose column: ");
            column = scanner.nextInt()-1;
        } while (row < 0
                || row >= boardDimensions.getNumberOfRows()
                || column < 0
                || column >= boardDimensions.getNumberOfColumns()
                || !board.isCellEmpty(row, column));
        return new BoardLocation(row, column);
    }
}
