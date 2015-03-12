package com.example.dejan.sudoku;


import java.util.Arrays;
import java.util.Random;

public class Sudoku {

    int[][] puzzle;
    private Random random = new Random();
    int[][] copy;


    // pravi sudoku tablu
    public int[][] createBoard(int[][] board) {
        for (int i = 0; i < 10; i++)
        {
            swapRowsAndCols(board);
            swapGrids(board);

        }
        return board;
    }
      //menja redove i kolone
    private int[][] swapRowsAndCols(int[][] board) {

        int range = 7;
        int rowsInGrid = 3;
        int colsInGrid = 3;

        for (int a = 0; a < range; a += rowsInGrid)
        {
            int row[] = getTwoRanNum(a, rowsInGrid);
            swapRows(board, row[0], row[1]);
        }

        for (int a = 0; a < range; a += colsInGrid)
        {
            int[] col = getTwoRanNum(a, colsInGrid);
            swapCols(board, col[0], col[1]);
        }
        return board;
    }

    // zamenjauje redove
    private int[][] swapRows(int[][] board, int row1, int row2) {
        for (int j = 0; j < board.length; j++)
        {
            int temp = board[row1][j];
            board[row1][j] = board[row2][j];
            board[row2][j] = temp;
        }
        return board;
    }

    // zamenjuje kolone
    private int[][] swapCols(int[][] board, int col1, int col2) {
        for (int i = 0; i < board.length; i++)
        {
            int temp = board[i][col1];
            board[i][col1] = board[i][col2];
            board[i][col2] = temp;
        }
        return board;
    }
    // menja 3x3 polja samo u horizontali
    private int[][] swapGrids(int[][] board) {
        int firstgrid = 1 + random.nextInt(3);
        int secondgrid = 1 + random.nextInt(3);
        int numRowsInGrid = 3;

        if ((firstgrid == 1 && secondgrid == 2) || (firstgrid == 2 && secondgrid == 1))
        {
            for (int i = 0; i < numRowsInGrid; i++)
            {
                swapRows(board, i, i + numRowsInGrid);
            }
        } else if ((firstgrid == 2 && secondgrid == 3) || (firstgrid == 3 && secondgrid == 2))
        {
            for (int i = numRowsInGrid; i < numRowsInGrid * 2; i++)
            {
                swapRows(board, i, i + numRowsInGrid);
            }
        } else if ((firstgrid == 1 && secondgrid == 3) || (firstgrid == 3 && secondgrid == 1))
        {
            for (int i = 0; i < numRowsInGrid; i++)
            {
                swapRows(board, i, i + (numRowsInGrid * 2));
            }
        }
        return board;
    }


    // izbacuje dva nasumicna broja
    private int[] getTwoRanNum(int min, int tolerance) {
        int a[] = new int[2];
        a[0] = min + random.nextInt(tolerance);
        a[1] = min + random.nextInt(tolerance);
        return a;
    }


    // kopira 2d niz
    private int[][] copyOf(int[][] original) {
         copy = new int[original.length][];
        for (int i = 0; i < original.length; i++)
        {
            copy[i] = Arrays.copyOf(original[i], original[i].length);
        }
        return copy;
    }

    // menja brojeve u nulu u zavisnosti od nivoa igre
    public int[][] createPuzzle(int[][] board, int mode) {
        this.puzzle = copyOf(board);
        int numOfEmptyBlock =mode;
        for (int i = 0; i < numOfEmptyBlock; i++)
        {
            int[] rowcol = getTwoRanNum(0, board.length);
            if(this.puzzle[rowcol[0]][rowcol[1]]==0)
            {
                i--;
            }
            else
            {
                this.puzzle[rowcol[0]][rowcol[1]] = 0;
            }
        }
        return copyOf(this.puzzle);
    }



}
