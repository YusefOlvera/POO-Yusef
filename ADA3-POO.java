package ada3;

// INTERFAZ PARA ELEMENTOS REPRODUCIBLES
interface IReproducible {
    void reproducir();
    void pausar();
    String obtenerDetalles();
}

// CLASE ABSTRACTA BASE PARA CONTENIDO MULTIMEDIA
abstract class ContenidoMultimedia implements IReproducible {
    protected int id;
    protected String titulo;
    protected int duracionMinutos;

    // CONSTRUCTOR BASICO
    public ContenidoMultimedia(int id, String titulo, int duracionMinutos) {
        this.id = id;
        this.titulo = titulo;
        this.duracionMinutos = duracionMinutos;
    }

    // GETTERS MINIMOS
    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public int getDuracionMinutos() {
        return duracionMinutos;
    }

    // METODO CONCRETO PARA IMPRIMIR INFO BASICA
    public void imprimirInfoBasica() {
        System.out.println("ID " + id + " TITULO " + titulo);
    }

    // IMPLEMENTACION POR DEFECTO DE PAUSAR
    @Override
    public void pausar() {
        System.out.println("PAUSADO " + titulo);
    }

    // IMPLEMENTACION POR DEFECTO DE obtenerDetalles
    @Override
    public String obtenerDetalles() {
        return "ID " + id + " TITULO " + titulo + " DURACION " + duracionMinutos + "MIN";
    }

    // METODO ABSTRACTO QUE CALCULA CALIDAD
    public abstract double calcularCalidad();
}

// CLASE PELICULA CON ATRIBUTO EXTRA
class Pelicula extends ContenidoMultimedia {
    private boolean ganoOscar;

    public Pelicula(int id, String titulo, int duracionMinutos, boolean ganoOscar) {
        super(id, titulo, duracionMinutos);
        this.ganoOscar = ganoOscar;
    }

    public boolean isGanoOscar() {
        return ganoOscar;
    }

    @Override
    public double calcularCalidad() {
        return ganoOscar ? 10.0 : 7.0;
    }

    @Override
    public void reproducir() {
        System.out.println("PROYECTANDO PELICULA " + titulo);
    }
}

// CLASE DOCUMENTAL CON ATRIBUTO TEMA
class Documental extends ContenidoMultimedia {
    private String tema;

    public Documental(int id, String titulo, int duracionMinutos, String tema) {
        super(id, titulo, duracionMinutos);
        this.tema = tema;
    }

    public String getTema() {
        return tema;
    }

    @Override
    public double calcularCalidad() {
        return duracionMinutos > 60 ? 9.0 : 6.0;
    }

    @Override
    public void reproducir() {
        System.out.println("PRESENTANDO DOCUMENTAL EDUCATIVO SOBRE " + tema);
    }
}

// CLASE PRINCIPAL SEGUN PLANTILLA PROPORCIONADA
public class Ada3 {

    // METODO MAIN CON LA LOGICA SOLICITADA
    public static void main(String[] args) {
        // ARREGLO FIJO DE CONTENIDO MULTIMEDIA CAPACIDAD 5
        ContenidoMultimedia[] catalogo = new ContenidoMultimedia[5];

        // RELLENAR EL ARREGLO CON INSTANCIAS MIXTAS
        catalogo[0] = new Pelicula(1, "LA AVENTURA", 120, true);
        catalogo[1] = new Documental(2, "EL OCEANO", 75, "Ciencia");
        catalogo[2] = new Pelicula(3, "COMEDIA LOCAL", 95, false);
        catalogo[3] = new Documental(4, "HISTORIA REGIONAL", 50, "Historia");
        catalogo[4] = new Pelicula(5, "DRAMA INDEPENDIENTE", 110, false);

        // RECORRER EL ARREGLO Y USAR POLIMORFISMO
        for (ContenidoMultimedia item : catalogo) {
            if (item == null) continue;

            // REPRODUCIR SEGUN TIPO
            item.reproducir();

            // CALCULAR Y MOSTRAR CALIDAD
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

            // PAUSAR Y SEPARADOR
            item.pausar();
            System.out.println("-----");
        }
    }
}
