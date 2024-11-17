
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import com.toedter.calendar.JCalendar;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.*;
import java.time.LocalDate;

public class ventanaPrincipal extends JFrame {
    private JList<evento> listaEventos;
    private DefaultListModel<evento> modeloLista;
    private JButton btnAgregar;
    private JButton btnEditar;
    private JButton btnGestionarAsistentes;
    private JButton btnMarcarRealizado;
    private JButton btnMostrarCalendario;  // Botón para abrir el calendario
    private JCalendar jCalendar;
    private gestorEventos gestor;
    private List<evento> eventos;

    public ventanaPrincipal(gestorEventos gestor) {
        this.gestor = gestor;
        inicializarComponentes();
        actualizarListaEventos();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void inicializarComponentes() {
        setTitle("Gestión de Eventos");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(20, 20));
        Font fuenteGrande = new Font("Arial", Font.PLAIN, 18);

        JPanel panelPrincipal = new JPanel(new BorderLayout(15, 15));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        modeloLista = new DefaultListModel<>();
        listaEventos = new JList<>(modeloLista);
        listaEventos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaEventos.setCellRenderer(new eventoListCellRenderer());
        listaEventos.setFont(fuenteGrande);

        JScrollPane scrollPane = new JScrollPane(listaEventos);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));

        btnAgregar = new JButton("Agregar Evento");
        btnEditar = new JButton("Editar Evento");
        btnGestionarAsistentes = new JButton("Gestionar Asistentes");
        btnMarcarRealizado = new JButton("Marcar como Realizado");
        btnMostrarCalendario = new JButton("Mostrar Calendario");

        btnMarcarRealizado.setFont(fuenteGrande);
        btnMarcarRealizado.setEnabled(false);

        btnMostrarCalendario.addActionListener(e -> mostrarCalendario());

        btnAgregar.addActionListener(e -> mostrarVentanaAgregar());
        btnEditar.addActionListener(e -> mostrarVentanaEditar());
        btnGestionarAsistentes.addActionListener(e -> mostrarVentanaAsistentes());
        btnMarcarRealizado.addActionListener(e -> marcarEventoComoRealizado());

        listaEventos.addListSelectionListener((ListSelectionEvent e) -> {
            boolean haySeleccion = listaEventos.getSelectedIndex() != -1;
            btnEditar.setEnabled(haySeleccion);
            btnGestionarAsistentes.setEnabled(haySeleccion);
            btnMarcarRealizado.setEnabled(haySeleccion);
        });

        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnGestionarAsistentes);
        panelBotones.add(btnMarcarRealizado);
        panelBotones.add(btnMostrarCalendario);

        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        add(panelPrincipal);
    }

    private class eventoListCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            Font fuenteGrande = new Font("Arial", Font.PLAIN, 18);
            setFont(fuenteGrande);

            if (value instanceof evento) {
                evento evento = (evento) value;
                String textoEvento = String.format("%s - %s - %d asistentes", evento.getNombre(), evento.getFecha().toString(), evento.getNumeroAsistentes());

                if (evento.getFecha().isBefore(LocalDate.now())) {
                    setForeground(Color.GRAY);
                    setFont(fuenteGrande.deriveFont(Font.ITALIC));
                } else {
                    setForeground(Color.BLACK);
                }

                if (evento.isRealizado()) {
                    setFont(fuenteGrande.deriveFont(Font.ITALIC));
                }

                setText(textoEvento);
            }
            return this;
        }
    }

    private void actualizarListaEventos() {
        modeloLista.clear();
        eventos = gestor.obtenerTodosEventos();
        for (evento evento : eventos) {
            modeloLista.addElement(evento);
        }
    }

    private void mostrarVentanaAgregar() {
        ventanaEvento ventana = new ventanaEvento(null, gestor);
        ventana.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                actualizarListaEventos();
            }
        });
        ventana.setVisible(true);
    }

    private void mostrarCalendario() {
        JDialog ventanaCalendario = new JDialog(this, "Calendario de Eventos", true);
        jCalendar = new JCalendar();

        jCalendar.addPropertyChangeListener("calendar", e -> mostrarEventosDelMes());

        ventanaCalendario.setLayout(new BorderLayout());
        ventanaCalendario.add(jCalendar, BorderLayout.CENTER);
        ventanaCalendario.setSize(400, 400);
        ventanaCalendario.setLocationRelativeTo(this);
        ventanaCalendario.setVisible(true);
    }

    private void mostrarEventosDelMes() {
        Calendar calendarSeleccionado = Calendar.getInstance();
        calendarSeleccionado.setTime(jCalendar.getDate());
        int mesSeleccionado = calendarSeleccionado.get(Calendar.MONTH);
        StringBuilder eventosMes = new StringBuilder("Eventos en el mes seleccionado:\n");
        Calendar hoy = Calendar.getInstance();

        for (evento evento : eventos) {
            Calendar calendarEvento = Calendar.getInstance();
            calendarEvento.setTime(java.sql.Date.valueOf(evento.getFecha()));

            if (calendarEvento.get(Calendar.MONTH) == mesSeleccionado && calendarEvento.after(hoy)) {
                eventosMes.append(String.format("Fecha: %1$tF, Evento: %2$s\n", java.sql.Date.valueOf(evento.getFecha()), evento.getDescripcion()));
            }
        }

        if (eventosMes.length() > 0) {
            JOptionPane.showMessageDialog(this, eventosMes.toString(), "Eventos en el Mes", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "No hay eventos en este mes.", "Sin Eventos", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void mostrarVentanaEditar() {
        evento eventoSeleccionado = listaEventos.getSelectedValue();
        if (eventoSeleccionado != null) {
            ventanaEvento ventana = new ventanaEvento(eventoSeleccionado, gestor);
            ventana.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    actualizarListaEventos();
                }
            });
        }
    }

    private void mostrarVentanaAsistentes() {
        evento eventoSeleccionado = listaEventos.getSelectedValue();
        if (eventoSeleccionado != null) {
            ventanaAsistentes ventana = new ventanaAsistentes(eventoSeleccionado, gestor);
            ventana.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    actualizarListaEventos();
                }
            });
        }
    }

    private void marcarEventoComoRealizado() {
        evento eventoSeleccionado = listaEventos.getSelectedValue();
        if (eventoSeleccionado != null) {
            eventoSeleccionado.setRealizado(true);
            actualizarListaEventos();
        }
    }
}
