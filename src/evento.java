
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class evento {

    private String nombre;
    private String ubicacion;
    private LocalDate fecha;
    private String descripcion;

    private int equipoAudiovisual;
    private int catering;
    private int salones;

    private List<asistente> asistentes;
    private boolean realizado;

    // Evento

    public evento(String nombre, String ubicacion, LocalDate fecha, String descripcion, int equipoAudiovisual, int catering, int salones) {
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.fecha = fecha;
        this.descripcion = descripcion;

        this.equipoAudiovisual = equipoAudiovisual;
        this.catering = catering;
        this.salones = salones;

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

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getEquipoAudiovisual() {
        return equipoAudiovisual;
    }

    public void setEquipoAudiovisual(int equipoAudiovisual) {
        this.equipoAudiovisual = equipoAudiovisual;
    }

    public int getCatering() {
        return catering;
    }

    public void setCatering(int catering) {
        this.catering = catering;
    }

    public int getSalones() {
        return salones;
    }

    public void setSalones(int salones) {
        this.salones = salones;
    }

    // Asistente

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

    // Recursos

    public class Recursos {
        public static final int[] equipoAudiovisualDisponibles = {0, 1, 2, 3, 4, 5};  // 5 opciones de equipos
        public static final int[] cateringDisponibles = {0, 1, 2, 3, 4};  // 4 opciones de catering
        public static final int[] salonesDisponibles = {0, 1, 2, 3};  // 3 opciones de salones
    }
    

}
