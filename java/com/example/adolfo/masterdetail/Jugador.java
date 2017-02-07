package com.example.adolfo.masterdetail;

public class Jugador {
    String nombreEquipo;
    String nombreJugador;
    int dorsal;
    String link;


    public Jugador(){
        nombreEquipo="";
        nombreJugador="";
        dorsal = 0;
        link = "";
    };

    public int getDorsal() {
        return dorsal;
    }

    public void setDorsal(int dorsal) {
        this.dorsal = dorsal;
    }

    public String getNombreEquipo() {
        return nombreEquipo;
    }

    public void setNombreEquipo(String nombreEquipo) {
        this.nombreEquipo = nombreEquipo;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getNombreJugador() {
        return nombreJugador;
    }

    public void setNombreJugador(String nombreJugador) {
        this.nombreJugador = nombreJugador;
    }

    public Jugador(String nombreEquipo, String nombreJugador, String link) {
        this.nombreEquipo = nombreEquipo;
        this.nombreJugador = nombreJugador;
        this.link = link;
    }
}