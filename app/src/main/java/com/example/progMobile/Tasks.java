package com.example.progMobile;

import java.io.Serializable;

public class Tasks implements Serializable {
    private int idTask;
    private String titleTask;
    private String descTask;
    private String date;
    private String time;
    private String local;

    public Tasks() {
    }

    public Tasks(String title, String desc, String date, String time) {
        this.titleTask = title;
        this.descTask = desc;
        this.date = date;
        this.time = time;
    }

    public Tasks(String title, String date, String time) {
        this.titleTask = title;
        this.date = date;
        this.time = time;
    }

    //get sets
    public int getTaskID() { return idTask; }
    public void setTaskID(int idTarefa) { this.idTask = idTarefa; }

    public String getTaskTitle() { return this.titleTask; }
    public void setTaskTitle(String tituloTarefa) { this.titleTask = tituloTarefa; }

    public String getTaskDesc() { return this.descTask; }
    public void setTaskDesc(String descricaoTarefa) { this.descTask = descricaoTarefa; }

    public String getDate() { return this.date; }
    public void setDate(String data) { this.date = data; }

    public String getTime() { return this.time; }
    public void setTime(String hora) { this.time = hora; }

    @Override
    public String toString(){
        return "Atividade: " + this.titleTask + "\n" + "Descrição: " + this.descTask;
    }

}
