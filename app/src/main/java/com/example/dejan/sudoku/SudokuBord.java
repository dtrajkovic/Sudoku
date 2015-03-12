package com.example.dejan.sudoku;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

public class SudokuBord  extends View{


    private Game game= new Game();
    Sudoku sudoku = new Sudoku();
    private float width; // sirina jednog polja
    private float height; // visina jednog polja
    private int selX; // X indeks selektovanog polja
    private int selY; // Y indeks selektovanog polja
    private final Rect selRect = new Rect();
    Paint background = new Paint();
    Paint dark = new Paint();
    Paint hilite = new Paint();
    Paint light = new Paint();
    Paint foreground = new Paint(Paint.ANTI_ALIAS_FLAG);




    public SudokuBord(Context context) {
        super(context);
        this.game= (Game)context;
        setFocusable(true);
        setFocusableInTouchMode(true);
        //odabir boja
        background.setColor(getResources().getColor(R.color.puzzle_background));
        dark.setColor(getResources().getColor(R.color.puzzle_dark));
        hilite.setColor(getResources().getColor(R.color.puzzle_hilite));
        light.setColor(getResources().getColor(R.color.puzzle_light));
    }

    //odredjuje velicinu polja
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        width = w / 9;
        height = h / 9;
        getRect(selX, selY, selRect);
        super.onSizeChanged(w, h, oldw, oldh);
    }

        //pravi pravugaonik po odredjenim merama
    private void getRect(int x, int y, Rect rect) {
        rect.set((int) (x * width), (int) (y * height),
                (int) (x * width + width), (int) (y * height + height));
    }


    @Override
    protected void onDraw(Canvas canvas) {
        // iscrtava pozadinu


        canvas.drawRect(0, 0, getWidth(), getHeight(), background);

        // iscrtava tablu
        // iscrtava glavne linije 3x3
        for (int i = 0; i < 9; i++)
        {
            canvas.drawLine(0, i * height + 1, getWidth(), i * height + 1,hilite);
            canvas.drawLine(i * width + 1, 0, i * width + 1, getHeight(), hilite);
        }

        // iscrtava ostale linije
       for (int i = 0; i < 9; i++)
       {
            if (i % 3 != 0)
                continue;
            canvas.drawLine(0, i * height, getWidth(), i * height, dark);
            canvas.drawLine(i * width, 0, i * width, getHeight(), dark);

       }


        // ispisuje brojeve i izabira boju za njih


        foreground.setStyle(Paint.Style.FILL);
        foreground.setTextSize(height * 0.75f);
        foreground.setTextScaleX(width / height);
        foreground.setTextAlign(Paint.Align.CENTER);

        // upisuje broj u centar polja
        Paint.FontMetrics fm = foreground.getFontMetrics();
        float x = width / 2;
        float y = height / 2 - (fm.ascent + fm.descent) / 2;


        // ispisuje broj
        for (int i = 0; i < 9; i++)
        {
            for (int j = 0; j < 9; j++)
            {

                            canvas.drawText(this.game.getTileString(i, j), i * width + x, j* height + y, foreground);
            }
        }


        // iscrtava selektovano polje

        Paint selected = new Paint();
        selected.setColor(getResources().getColor(R.color.puzzle_selected));
        canvas.drawRect(selRect, selected);
    }





    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() != MotionEvent.ACTION_DOWN)
            return super.onTouchEvent(event);
        select((int) (event.getX() / width), (int) (event.getY() / height));
        game.showKeypadOrError(selX, selY);
        return true;
    }

//salje kordinate i vrednost
    public void setSelectedTile(int tile) {

        if (game.setTileIfValid(selX, selY, tile))
        {
            invalidate();


        }

    }


    private void select(int x, int y) {
        invalidate(selRect);
        selX = Math.min(Math.max(x, 0), 8);
        selY = Math.min(Math.max(y, 0), 8);
        getRect(selX, selY, selRect);
        invalidate(selRect);
    }




}