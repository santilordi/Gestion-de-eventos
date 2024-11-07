
import java.util.ArrayList;
import java.util.List;

public class gestorEventos {

    private List<evento> listaEventos;

    public gestorEventos() {
        this.listaEventos = new ArrayList<>();
    }

    public void agregarEvento(evento evento) {
        if (evento != null) {
            listaEventos.add(evento);
        }
    }

    public void editarEvento(evento eventoAntiguo, evento eventoNuevo) {
        int indice = listaEventos.indexOf(eventoAntiguo);
        if (indice != -1) {
            listaEventos.set(indice, eventoNuevo);
        }
    }

    public evento obtenerEvento(String nombre) {
        for (evento evento : listaEventos) {
            if (evento.getNombre() == nombre) {
                return evento;
            }
        }
        return null;
    }

    public List<evento> obtenerTodosEventos() {
        // Retornamos una copia de la lista para evitar modificaciones externas
        return new ArrayList<>(listaEventos);
    }

    public void agregarAsistenteAEvento(evento evento, asistente asistente) {
        int indice = listaEventos.indexOf(evento);
        if (indice != -1) {
            listaEventos.get(indice).agregarAsistente(asistente);
        }
    }

    public List<asistente> obtenerAsistentesEvento(evento evento) {
        int indice = listaEventos.indexOf(evento);
        if (indice != -1) {
            return listaEventos.get(indice).getAsistentes();
        }
        return new ArrayList<>();
    }


}
