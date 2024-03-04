 
package org.dario.game.internal;

class Board {
    private org.dario.game.internal.Cell[][] cells;
    private org.dario.game.internal.BoardDimensions dimensions;

    public Board(org.dario.game.internal.BoardDimensions boardDimensions) {
        this.dimensions = boardDimensions;
        this.cells = new org.dario.game.internal.Cell[boardDimensions.getNumberOfColumns()][boardDimensions.getNumberOfRows()];
        initAllCells();
    }

    private void initAllCells() {
        for (int r = 0; r < dimensions.getNumberOfRows(); r++) {
            for (int c = 0; c < dimensions.getNumberOfColumns(); c++) {
                this.cells[c][r] = new org.dario.game.internal.Cell();
            }
        }
    }

    public void updateCell(int row, int column, org.dario.game.internal.Sign sign) {
        this.cells[column][row].setSign(sign);
    }

    public org.dario.game.internal.Sign checkWinner() {
        // Check rows
        for (int r = 0; r < dimensions.getNumberOfRows(); r++) {
            org.dario.game.internal.Sign sign = getRowWinner(r);
            if (sign != org.dario.game.internal.Sign.EMPTY) {
                return sign;
            }
        }

        // Check columns
        for (int c = 0; c < dimensions.getNumberOfColumns(); c++) {
            org.dario.game.internal.Sign sign = getColumnWinner(c);
            if (sign != org.dario.game.internal.Sign.EMPTY) {
                return sign;
            }
        }

        // Check diagonal
        org.dario.game.internal.Sign sign = getDiagonalWinner(0, 0, 1, 1);
        if (sign != org.dario.game.internal.Sign.EMPTY) {
            return sign;
        }

        // Check diagonal
        return getDiagonalWinner(0, dimensions.getNumberOfColumns() - 1, -1, 1);
    }

    public boolean isCellEmpty(int row, int column) {
        return this.cells[column][row].isEmpty();
    }

    public char getPrintableCellSign(int row, int column) {
        return this.cells[column][row].getSign().getValue();
    }

    public boolean isBoardFull() {
        for (int r = 0; r < dimensions.getNumberOfRows(); r++) {
            for (int c = 0; c < dimensions.getNumberOfColumns(); c++) {
                if (this.cells[c][r].isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    private org.dario.game.internal.Sign getColumnWinner(int currentColumn) {
        org.dario.game.internal.Sign initialSign = this.cells[currentColumn][0].getSign();

        if (initialSign == org.dario.game.internal.Sign.EMPTY) {
            return initialSign;
        }

        for (int r = 1; r < dimensions.getNumberOfRows(); r++) {
            if (this.cells[currentColumn][r].getSign() != initialSign) {
                return org.dario.game.internal.Sign.EMPTY;
            }
        }
        return initialSign;
    }

    private org.dario.game.internal.Sign getRowWinner(int currentRow) {
        org.dario.game.internal.Sign initialSign = this.cells[0][currentRow].getSign();

        if (initialSign == org.dario.game.internal.Sign.EMPTY) {
            return initialSign;
        }

        for (int c = 1; c < dimensions.getNumberOfColumns(); c++) {
            if (this.cells[c][currentRow].getSign() != initialSign) {
                return org.dario.game.internal.Sign.EMPTY;
            }
        }
        return initialSign;
    }


    private org.dario.game.internal.Sign getDiagonalWinner(int startRow, int startColumn, int horizontalStep, int verticalStep) {
        org.dario.game.internal.Sign initialSign = this.cells[startColumn][startRow].getSign();
        if (initialSign == org.dario.game.internal.Sign.EMPTY) {
            return org.dario.game.internal.Sign.EMPTY;
        }

        int r = startRow + verticalStep;
        int c = startColumn + horizontalStep;

        while (r < dimensions.getNumberOfRows() && c < dimensions.getNumberOfColumns()) {
            if (this.cells[c][r].getSign() != initialSign) {
                return org.dario.game.internal.Sign.EMPTY;
            }
            r += verticalStep;
            c += horizontalStep;
        }

        return initialSign;
    }
}
