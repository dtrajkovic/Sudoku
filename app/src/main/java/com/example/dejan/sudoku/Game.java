package com.example.dejan.sudoku;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Toast;


public class Game extends ActionBarActivity {


 Sudoku sudoku = new Sudoku();

    public static final int[][] sud = {
            {4, 3, 5, 8, 7, 6, 1, 2, 9},
            {8, 7, 6, 2, 1, 9, 3, 4, 5},
            {2, 1, 9, 4, 3, 5, 7, 8, 6},
            {5, 2, 3, 6, 4, 7, 8, 9, 1},
            {9, 8, 1, 5, 2, 3, 4, 6, 7},
            {6, 4, 7, 9, 8, 1, 2, 5, 3},
            {7, 5, 4, 1, 6, 8, 9, 3, 2},
            {3, 9, 2, 7, 5, 4, 6, 1, 8},
            {1, 6, 8, 3, 9, 2, 5, 7, 4}};

    public  int l ;//koliko ce praznih polja biti
    private int puzzle[];
    private SudokuBord board;
    public int level;
    private int num;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LevelChooser();
        sudoku.createPuzzle(sudoku.createBoard(sud),l);
        puzzle =trans();
        board = new SudokuBord(this);
        setContentView(board);
        board.requestFocus();

    }




    //odabir nivoa igre
    public void LevelChooser(){
        Intent intent = getIntent();
        level = intent.getIntExtra("level", 1);
        if (level==1)
        {
            l=1;
        }
        if (level==2)
        {
            l=50;
        }
        if (level==3)
        {
            l=55;
        }
    }
    //transformise iz 2d u 1d
    public int[] trans(){

        int c=0;
        int temp = 0;
        int one[]=new int[81];
        for (int i = 0; i < 9; i++)
        {
            for (int j = 0; j < 9; j++)
            {

                temp =sudoku.puzzle[i][j];
                one[c]=temp;
                c++;

            }
        }

        return one;
    }






// poziva keypad ili izbacuje greske
    protected void showKeypadOrError(int x, int y) {
        int a=sudoku.copy[x][y];
        //ako je broj upisan prilikom startovanja onemogucava njegovu promenu
        if(a==0)
        {

            Dialog v = new Keypad( this, board);
            v.show();


        } else
                {
                    Toast toast = Toast.makeText(this,"no available for change",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
    }


       //postavlja vrednost u polja
    protected boolean setTileIfValid(int x, int y, int value) {

        sudoku.puzzle[x][y]=value;
        puzzle =trans();
        isFinish();
        return true;
    }

    //ispisuje brojeve u polja
    protected String getTileString(int x, int y) {
        int v = getTile(x, y);
        if (v == 0)
            return "";
        else
            return String.valueOf(v);
    }

   //vraca vrednost sa zadatog polja
    private int getTile(int x, int y) {
        return puzzle[x * 9 + y];
    }

//proverava da li su sva polja popunjena
    public void isFinish(){
        int check=0;
        for (int i = 0; i < 9; i++)
        {
            for (int j = 0; j <9; j++)
            {      int temp=sudoku.puzzle[i][j];
                if (temp!=0)
                {
                    check++;


                }
                if(check==81)
                {
                   if(iscCorect()==81)
                   {
                       Intent intent = new Intent("android.intent.GameFinish");
                       startActivity(intent);
                       finish();
                   }
                }
            }

        }

    }
    //proverava da li su brojevi poredani po sudoka pravilima
    public int iscCorect(){
        num=0;
        for (int i = 0; i < 9; i++)
        {
            for (int j = 0; j <9; j++)
            {
               if( !checkIfNumberRepet(i,j))
               {

                   num++;

               }else
               {
                   Toast toast = Toast.makeText(this,"YOU HAVE ERROR IN YOUR SOLUTION",Toast.LENGTH_SHORT);
                   toast.setGravity(Gravity.CENTER, 0, 0);
                   toast.show();
                   i=9;
                   j=9;

               }


            }
        }


        return num;
    }


    //proverava da li se broj ponavlja vertikalnom,horizontalnom i u 3x3  sa poslatim kordinatama
    private boolean checkIfNumberRepet(int x, int y) {
        int temp= sudoku.puzzle[x][y];
        // horizontalno
        for (int i = 0; i < 9; i++) {
            if (i == x)
                continue;
            int t = getTile(i, y);
            if (t ==temp) {
                return true;
            }

        }
        // verticalno

        for (int i = 0; i < 9; i++) {
            if (i == y)
                continue;
            int t = getTile(x, i);
            if (t ==temp) {
                return true;
            }
        }

        // u 3x3
        int startx = (x / 3) * 3;
        int starty = (y / 3) * 3;
        for (int i = startx; i < startx + 3; i++) {
            for (int j = starty; j < starty + 3; j++) {
                if (i == x && j == y)
                    continue;
                int t = getTile(i, j);
                if (t ==temp) {
                    return true;
                }
            }
        }
        return false;
    }







}
