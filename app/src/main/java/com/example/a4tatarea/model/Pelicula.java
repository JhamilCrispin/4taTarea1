package com.example.a4tatarea.model;

public class Pelicula {
    String año, director, duracion, genero, resolucion, sinopsis, titulo;

    public Pelicula(){}

    public Pelicula(String año, String director, String duracion, String genero, String resolucion, String sinopsis, String titulo) {
        this.año = año;
        this.director = director;
        this.duracion = duracion;
        this.genero = genero;
        this.resolucion = resolucion;
        this.sinopsis = sinopsis;
        this.titulo = titulo;
    }
    public String getAño() {
        return año;
    }

    public void setAño(String año) {
        this.año = año;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getResolucion() {
        return resolucion;
    }

    public void setResolucion(String resolucion) {
        this.resolucion = resolucion;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

}

