package es.studium.frasesfamosasapp.modelos;

public class FraseFamosa
{
    private String texto;
    private String autor;
    private long id; // El ID de la BD
    public FraseFamosa(String texto, String autor)
    {
        this.texto = texto;
        this.autor = autor;
    }
    // Constructor para cuando instanciamos desde la BD
    public FraseFamosa(String texto, String autor, long id)
    {
        this.texto = texto;
        this.autor = autor;
        this.id = id;
    }
    public long getId()
    {
        return id;
    }
    public void setId(long id)
    {
        this.id = id;
    }
    public String getTexto()
    {
        return texto;
    }
    public void setTexto(String texto)
    {
        this.texto = texto;
    }
    public String getAutor()
    {
        return autor;
    }
    public void setAutor(String autor)
    {
        this.autor = autor;
    }
    @Override
    public String toString()
    {
        return "Frase{" +
                "texto='" + texto + '\'' +
                ", autor=" + autor +
                '}';
    }
}
