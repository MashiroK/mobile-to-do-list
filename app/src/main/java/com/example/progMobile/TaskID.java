package com.example.progMobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class TaskID extends AppCompatActivity {

    private ListView listTask;
    private Button buttonNewTask;
    Tasks task;
    DBToDoHelper db;
    ArrayList<Tasks> listTasks;
    ArrayAdapter<Tasks> arrayAdapterTask;
    private int id1, id2;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_id);
        listTask = findViewById(R.id.listTasks);
        buttonNewTask = findViewById(R.id.bttnAddTask);
        registerForContextMenu(listTask);
        fill();
        buttonNewTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TaskID.this, TaskRegistering.class);
                startActivity(intent);
            }

        });
        listTask.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Tasks a1=(Tasks) arrayAdapterTask.getItem(position);
                Intent it=new Intent(TaskID.this, TaskRegistering.class);
                it.putExtra("ch_tarefa",a1);
                startActivity(it);
            }
        });

        listTask.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                task = arrayAdapterTask.getItem(position);
                return false;
            }
        });
    }

    public void fill() {
        db = new DBToDoHelper(TaskID.this);
        listTasks = db.selectAllTasks();
        db.close();
        if (listTasks != null) {
            arrayAdapterTask = new ArrayAdapter<Tasks>(TaskID.this,
                    android.R.layout.simple_expandable_list_item_1, listTasks);
            listTask.setAdapter(arrayAdapterTask);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        fill();
    }

    @Override //not working for delete bttn remove???
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem deleteMenu = menu.add(Menu.NONE, id1, 1, "Deletar Atividade");
        //MenuItem leaveMenu = menu.add(Menu.NONE, id2, 2, "Cancelar");
        deleteMenu.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            long ret;
            db = new DBToDoHelper(TaskID.this);
            ret = db.deleteTask(task);
            db.close();
            if (ret == -1) {
                alert("Erro");
            } else {
                alert("Atividade deletada!");
            }
            fill();
            return false;
        }
    });
        super.onCreateContextMenu(menu, v, menuInfo);

    }

    private void alert(String s) {
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }
}
