import com.toedter.calendar.JCalendar;
import javax.swing.*;
import java.awt.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Calendar;


public class calendario extends JDialog {
    private JCalendar jCalendar;
    private gestorEventos gestor;
    private Map<Date, String> eventosConDescripcion;

    public calendario(Frame parentFrame, gestorEventos gestor) {
        super(parentFrame, "Calendario de Eventos", true); // Ventana modal (true)
        this.gestor = gestor;
        eventosConDescripcion = new HashMap<>();
        inicializarCalendario();
        setSize(400, 400);
        setLocationRelativeTo(parentFrame); // Centrar la ventana sobre la ventana principal
    }

    private void inicializarCalendario() {
        // Crear el JCalendar
        jCalendar = new JCalendar();

        // Obtener las fechas con eventos y sus descripciones
        obtenerEventosConDescripcion();

        // Agregar un PropertyChangeListener para detectar el cambio de mes
        jCalendar.addPropertyChangeListener("calendar", e -> mostrarEventosDelMes());

        // Mostrar el calendario
        JPanel calendarioPanel = new JPanel(new BorderLayout());
        calendarioPanel.add(jCalendar, BorderLayout.CENTER);

        // Mostrar la ventana
        setLayout(new BorderLayout());
        add(calendarioPanel, BorderLayout.CENTER);

        // Mostrar los eventos del mes actual al iniciar
        mostrarEventosDelMes();
    }

    // Método para obtener los eventos y sus descripciones
    private void obtenerEventosConDescripcion() {
        for (evento evento : gestor.obtenerTodosEventos()) {
            Date fecha = java.sql.Date.valueOf(evento.getFecha()); // Convierte LocalDate a Date
            eventosConDescripcion.put(fecha, evento.getDescripcion()); // Guardamos la fecha y su descripción
        }
    }

    // Método para mostrar los eventos futuros cuando se cambia de mes
    private void mostrarEventosDelMes() {
        // Obtener el mes seleccionado del JCalendar
        Calendar calendarSeleccionado = Calendar.getInstance();
        calendarSeleccionado.setTime(jCalendar.getDate());
        int mesSeleccionado = calendarSeleccionado.get(Calendar.MONTH);

        // Buscar las fechas que tienen eventos en ese mes y que son futuros
        StringBuilder eventosMes = new StringBuilder("Eventos en el mes seleccionado:\n");

        // Obtener la fecha de hoy
        Calendar hoy = Calendar.getInstance();

        for (Map.Entry<Date, String> entry : eventosConDescripcion.entrySet()) {
            Calendar calendarEvento = Calendar.getInstance();
            calendarEvento.setTime(entry.getKey());

            // Comprobar si el evento pertenece al mes seleccionado y si es futuro
            if (calendarEvento.get(Calendar.MONTH) == mesSeleccionado && calendarEvento.after(hoy)) {
                eventosMes.append(String.format("Fecha: %1$tF, Evento: %2$s\n", entry.getKey(), entry.getValue()));
            }
        }

        // Mostrar los eventos en un pop-up (JOptionPane)
        if (eventosMes.length() > 0) {
            JOptionPane.showMessageDialog(this, eventosMes.toString(), "Eventos en el Mes", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "No hay eventos en este mes.", "Sin Eventos", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}