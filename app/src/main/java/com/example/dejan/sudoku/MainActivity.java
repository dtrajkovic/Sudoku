package com.example.dejan.sudoku;



import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;



public class MainActivity extends ActionBarActivity {


    Button start_game;
    int level;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        start_game= (Button)findViewById(R.id.start_game);


    }


    public void onButtonClickEvent(View sender)
    {
        registerForContextMenu(sender);
        openContextMenu(sender);
        unregisterForContextMenu(sender);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_levelchoser,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        Intent intent = new Intent("android.intent.action.level_1");
       if (item.getItemId()==R.id.easy)
        {   level =1;
            intent.putExtra("level",level);
        }
        if (item.getItemId()==R.id.medium)
        {
            level =2;
            intent.putExtra("level",level);
        }
        if (item.getItemId()==R.id.hard)
        {
            level =3;
            intent.putExtra("level",level);
        }
        startActivity(intent);
        return super.onContextItemSelected(item);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




}
