package com.example.mycardview;

class Anime
{
    private final int imagen;
    private final String nombre;
    private final int visitas;
    public Anime(int imagen, String nombre, int visitas)
    {
        this.imagen = imagen;
        this.nombre = nombre;
        this.visitas = visitas;
    }
    public String getNombre()
    {
        return nombre;
    }
    public int getVisitas()
    {
        return visitas;
    }
    public int getImagen()
    {
        return imagen;
    }
}
