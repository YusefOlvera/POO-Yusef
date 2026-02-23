// SISTEMA DE GESTION STREAMCODE

interface IReproducible {
    void reproducir();
    void pausar();
    String obtenerDetalles();
}

abstract class ContenidoMultimedia implements IReproducible {
    protected int id;
    protected String titulo;
    protected int duracionMinutos;

    // CONSTRUCTOR
    public ContenidoMultimedia(int id, String titulo, int duracionMinutos) {
        this.id = id;
        this.titulo = titulo;
        this.duracionMinutos = duracionMinutos;
    }

    // GETTERS Y SETTERS
    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public int getDuracionMinutos() {
        return duracionMinutos;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDuracionMinutos(int duracionMinutos) {
        this.duracionMinutos = duracionMinutos;
    }

    // METODO CONCRETO
    public void imprimirInfoBasica() {
        System.out.println("ID " + id + " TITULO " + titulo);
    }

    // IMPLEMENTACION POR DEFECTO DE PAUSAR
    @Override
    public void pausar() {
        System.out.println("PAUSADO " + titulo);
    }

    // IMPLEMENTACION DE obtenerDetalles
    @Override
    public String obtenerDetalles() {
        return "ID " + id + " TITULO " + titulo + " DURACION " + duracionMinutos + "MIN";
    }

    // METODO ABSTRACTO
    public abstract double calcularCalidad();
}

class Pelicula extends ContenidoMultimedia {
    private boolean ganoOscar;

    public Pelicula(int id, String titulo, int duracionMinutos, boolean ganoOscar) {
        super(id, titulo, duracionMinutos);
        this.ganoOscar = ganoOscar;
    }

    public boolean isGanoOscar() {
        return ganoOscar;
    }

    public void setGanoOscar(boolean ganoOscar) {
        this.ganoOscar = ganoOscar;
    }

    @Override
    public double calcularCalidad() {
        return ganoOscar ? 10.0 : 7.0;
    }

    @Override
    public void reproducir() {
        System.out.println("PROYECTANDO PELICULA " + getTitulo());
    }
}

class Documental extends ContenidoMultimedia {
    private String tema;

    public Documental(int id, String titulo, int duracionMinutos, String tema) {
        super(id, titulo, duracionMinutos);
        this.tema = tema;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    @Override
    public double calcularCalidad() {
        return getDuracionMinutos() > 60 ? 9.0 : 6.0;
    }

    @Override
    public void reproducir() {
        System.out.println("PRESENTANDO DOCUMENTAL EDUCATIVO SOBRE " + tema);
    }
}

public class Main {
    public static void main(String[] args) {
        // ARREGLO DE CONTENIDO MULTIMEDIA CAPACIDAD 5
        ContenidoMultimedia[] catalogo = new ContenidoMultimedia[5];

        // LLENAR ARREGLO CON PELICULAS Y DOCUMENTALES
        catalogo[0] = new Pelicula(1, "LA AVENTURA", 120, true);
        catalogo[1] = new Documental(2, "EL OCÉANO", 75, "Ciencia");
        catalogo[2] = new Pelicula(3, "COMEDIA LOCAL", 95, false);
        catalogo[3] = new Documental(4, "HISTORIA REGIONAL", 50, "Historia");
        catalogo[4] = new Pelicula(5, "DRAMA INDEPENDIENTE", 110, false);

        // RECORRER Y USAR POLIMORFISMO
        for (ContenidoMultimedia item : catalogo) {
            if (item == null) continue;
            item.reproducir();
            double calidad = item.calcularCalidad();
            System.out.println(item.obtenerDetalles() + " CALIDAD " + calidad);
            // USO DE instanceof PARA ACCEDER A ATRIBUTOS ESPECIFICOS
            if (item instanceof Pelicula) {
                Pelicula p = (Pelicula) item;
                System.out.println("ES PELICULA GANO OSCAR " + p.isGanoOscar());
            } else if (item instanceof Documental) {
                Documental d = (Documental) item;
                System.out.println("TEMA DOCUMENTAL " + d.getTema());
            }
            item.pausar();
            System.out.println("-----");
        }
    }
}
