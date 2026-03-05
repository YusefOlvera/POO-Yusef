import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// INTERFAZ PARA DEFINIR COMPORTAMIENTO DE PRESTABLE
interface Prestable {
    void prestar();
    void devolver();
}

// CLASE ABSTRACTA BASE (MATERIAL DE BIBLIOTECA)
abstract class MaterialBiblioteca {
    // ATRIBUTOS PRIVADOS PARA ENCAPSULAMIENTO
    private String titulo;
    private String autor;
    private int anio;
    private boolean disponible;

    public MaterialBiblioteca(String titulo, String autor, int anio) {
        this.titulo = titulo;
        this.autor = autor;
        this.anio = anio;
        this.disponible = true;
    }

    // GETTERS Y SETTERS
    public String getTitulo() { return titulo; }
    public boolean isDisponible() { return disponible; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }

    // MÉTODO ABSTRACTO QUE CADA HIJO IMPLEMENTA
    public abstract void mostrarInfo();
}

// CLASE CONCRETA LIBRO (HERENCIA Y POLIMORFISMO)
class Libro extends MaterialBiblioteca implements Prestable {
    public Libro(String titulo, String autor, int anio) {
        super(titulo, autor, anio);
    }

    @Override
    public void mostrarInfo() {
        System.out.println("[LIBRO] Título: " + getTitulo() + " (Disponible: " + isDisponible() + ")");
    }

    @Override
    public void prestar() { setDisponible(false); }

    @Override
    public void devolver() { setDisponible(true); }
}

// CLASE USUARIO (AGREGACIÓN: EL MATERIAL EXISTE SIN EL USUARIO)
class Usuario {
    private String nombre;
    private List<MaterialBiblioteca> materialesPrestados;

    public Usuario(String nombre) {
        this.nombre = nombre;
        this.materialesPrestados = new ArrayList<>();
    }

    public String getNombre() { return nombre; }
    public void agregarMaterial(MaterialBiblioteca m) { materialesPrestados.add(m); }
    public void quitarMaterial(MaterialBiblioteca m) { materialesPrestados.remove(m); }
}

// CLASE TRANSACCION (COMPOSICIÓN CON DETALLEACCION)
class Transaccion {
    private String ID;
    private DetalleAccion detalle; // COMPOSICIÓN: SI MUERE TRANSACCION, MUERE DETALLE

    public Transaccion(String ID, String tipo, String fecha) {
        this.ID = ID;
        this.detalle = new DetalleAccion(tipo, fecha);
    }

    public void imprimirTransaccion() {
        System.out.println("ID: " + ID + " | " + detalle.getInfo());
    }

    // CLASE INTERNA PARA LA COMPOSICIÓN
    private class DetalleAccion {
        private String tipo;
        private String fecha;

        public DetalleAccion(String tipo, String fecha) {
            this.tipo = tipo;
            this.fecha = fecha;
        }
        public String getInfo() { return "Acción: " + tipo + " el " + fecha; }
    }
}

// CLASE MAIN CON EL MENÚ
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<MaterialBiblioteca> inventario = new ArrayList<>();
        List<Usuario> usuarios = new ArrayList<>();
        List<Transaccion> historial = new ArrayList<>();

        int opcion = 0;
        while (opcion != 7) {
            System.out.println("\n--- MENU BIBLIOTECA ---");
            System.out.println("1. Registrar Material\n2. Registrar Usuario\n3. Prestar\n4. Devolver\n5. Ver Disponibles\n6. Historial\n7. Salir");
            System.out.print("Elige: ");
            opcion = sc.nextInt();
            sc.nextLine(); // LIMPIAR BUFFER

            switch (opcion) {
                case 1: // REGISTRO DE MATERIAL
                    System.out.print("Título del Libro: ");
                    String t = sc.nextLine();
                    inventario.add(new Libro(t, "Autor Desconocido", 2024));
                    System.out.println("Material guardado.");
                    break;

                case 2: // REGISTRO DE USUARIO
                    System.out.print("Nombre de usuario: ");
                    String n = sc.nextLine();
                    usuarios.add(new Usuario(n));
                    System.out.println("Usuario registrado.");
                    break;

                case 3: // PROCESO DE PRÉSTAMO
                    if (inventario.isEmpty() || usuarios.isEmpty()) {
                        System.out.println("Faltan datos.");
                        break;
                    }
                    MaterialBiblioteca mat = inventario.get(0); // EJEMPLO CON EL PRIMERO
                    if (mat.isDisponible() && mat instanceof Prestable) {
                        ((Prestable) mat).prestar();
                        usuarios.get(0).agregarMaterial(mat);
                        historial.add(new Transaccion("T-" + (historial.size()+1), "PRESTAMO", "05/03/2026"));
                        System.out.println("Prestado con éxito.");
                    }
                    break;

                case 5: // CONSULTA DISPONIBLES (POLIMORFISMO EN ACCIÓN)
                    for (MaterialBiblioteca m : inventario) {
                        if (m.isDisponible()) m.mostrarInfo();
                    }
                    break;

                case 6: // MOSTRAR HISTORIAL
                    for (Transaccion tr : historial) tr.imprimirTransaccion();
                    break;
            }
        }
        System.out.println("Cerrando sistema...");
    }
}