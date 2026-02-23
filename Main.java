package Ada3Poo;

// CLIENTE AGREGACION EXISTE INDEPENDIENTE
class Cliente {
    private Long id;
    private String nombre;
    private String email;

    public Cliente(Long id, String nombre, String email) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public String getEmail() { return email; }
}

// LINEA DE DETALLE COMPOSICION CREADA POR PEDIDO
class LineaDeDetalle {
    private String producto;
    private int cantidad;
    private double precioUnitario;

    LineaDeDetalle(String producto, int cantidad, double precioUnitario) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
    }

    double subtotal() {
        return cantidad * precioUnitario;
    }

    String info() {
        return producto + " x" + cantidad + " SUBTOTAL " + subtotal();
    }
}

// PEDIDO USA WRAPPERS LONG Y DOUBLE Y MANEJA LINEAS EN UN ARRAY FIJO
class Pedido {
    private Long id;
    private Double total;
    private Cliente cliente;
    private LineaDeDetalle[] lineas;
    private int contadorLineas = 0;

    public Pedido(Long id, Cliente cliente, int capacidadLineas) {
        this.id = id;
        this.cliente = cliente;
        this.lineas = new LineaDeDetalle[capacidadLineas];
        this.total = 0.0;
    }

    public boolean agregarLinea(String producto, int cantidad, double precioUnitario) {
        if (contadorLineas >= lineas.length) return false;
        lineas[contadorLineas++] = new LineaDeDetalle(producto, cantidad, precioUnitario);
        recalcularTotal();
        return true;
    }

    private void recalcularTotal() {
        double suma = 0.0;
        for (int i = 0; i < contadorLineas; i++) {
            suma += lineas[i].subtotal();
        }
        this.total = suma;
    }

    public void imprimirResumen() {
        System.out.println("PEDIDO ID " + id);
        System.out.println("CLIENTE " + (cliente != null ? cliente.getNombre() : "SIN CLIENTE"));
        for (int i = 0; i < contadorLineas; i++) {
            System.out.println(lineas[i].info());
        }
        System.out.println("TOTAL " + total);
    }

    // ELIMINAR PEDIDO ROMPE LA COMPOSICION DE LINEAS
    public void eliminarPedido() {
        for (int i = 0; i < lineas.length; i++) lineas[i] = null;
        contadorLineas = 0;
        total = null;
        id = null;
    }

    public Long getId() { return id; }
    public Double getTotal() { return total; }
    public Cliente getCliente() { return cliente; }
}

// CLASE PRINCIPAL CON MAIN PARA EJECUTAR EL EJEMPLO
public class Main {
    public static void main(String[] args) {
        Cliente c = new Cliente(1001L, "Ana Perez", "ana@example.com");
        Pedido p = new Pedido(5001L, c, 3);
        p.agregarLinea("Zapatos", 2, 499.99);
        p.agregarLinea("Calcetines", 5, 29.50);
        p.imprimirResumen();

        p.eliminarPedido();
        System.out.println("DESPUES DE ELIMINAR PEDIDO TOTAL ES " + p.getTotal());
        System.out.println("EL CLIENTE SIGUE EXISTIENDO " + c.getNombre());
    }
}
