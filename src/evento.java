
import java.util.ArrayList;
import java.util.List;

public class evento {

    private String nombre;
    private String ubicacion;
    private String fecha;
    private String descripcion;
    private List<asistente> asistentes;
    private boolean realizado;

    public evento(String nombre, String ubicacion, String fecha, String descripcion) {
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.asistentes = new ArrayList<>();
        this.realizado = false;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void agregarAsistente(asistente asistente) {
        if (asistentes == null) {
            asistentes = new ArrayList<>();
        }
        asistentes.add(asistente);
    }

    public List<asistente> getAsistentes() {
        return new ArrayList<>(asistentes);
    }

    public int getNumeroAsistentes() {
        return asistentes.size();
    }

    public boolean isRealizado() {
        return realizado;
    }

    public void setRealizado(boolean realizado) {
        this.realizado = realizado;
    }
}
