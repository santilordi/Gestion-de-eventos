import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class ventanaPrincipal extends JFrame {
    private JList<evento> listaEventos;
    private DefaultListModel<evento> modeloLista;
    private JButton btnAgregar;
    private JButton btnEditar;
    private JButton btnGestionarAsistentes;
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
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(20, 20));
        Font fuenteGrande = new Font("Arial", Font.PLAIN, 18);

        // Panel principal que contiene la lista y los botones
        JPanel panelPrincipal = new JPanel(new BorderLayout(15, 15));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Inicializar la lista de eventos
        modeloLista = new DefaultListModel<>();
        listaEventos = new JList<>(modeloLista);
        listaEventos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaEventos.setCellRenderer(new eventoListCellRenderer());
        listaEventos.setFont(fuenteGrande);

        // Agregar la lista a un ScrollPane
        JScrollPane scrollPane = new JScrollPane(listaEventos);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);

        // Panel para los botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));

        // Inicializar botones
        btnAgregar = new JButton("Agregar Evento");
        btnAgregar.setFont(fuenteGrande);
        btnAgregar.setPreferredSize(new Dimension(180, 50));
        btnEditar = new JButton("Editar Evento");
        btnEditar.setFont(fuenteGrande);
        btnEditar.setPreferredSize(new Dimension(180, 50));
        btnGestionarAsistentes = new JButton("Gestionar Asistentes");
        btnGestionarAsistentes.setFont(fuenteGrande);
        btnGestionarAsistentes.setPreferredSize(new Dimension(180, 50));

        // Configurar estado inicial de los botones
        btnEditar.setEnabled(false);
        btnGestionarAsistentes.setEnabled(false);

        // Agregar listeners a los botones
        btnAgregar.addActionListener(e -> mostrarVentanaAgregar());
        btnEditar.addActionListener(e -> mostrarVentanaEditar());
        btnGestionarAsistentes.addActionListener(e -> mostrarVentanaAsistentes());

        // Agregar listener a la selección de la lista
        listaEventos.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    boolean haySeleccion = listaEventos.getSelectedIndex() != -1;
                    btnEditar.setEnabled(haySeleccion);
                    btnGestionarAsistentes.setEnabled(haySeleccion);
                }
            }
        });

        // Agregar botones al panel
        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnGestionarAsistentes);

        // Agregar el panel de botones al panel principal
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        // Agregar el panel principal al frame
        add(panelPrincipal);
    }

    private class eventoListCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(
                JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            Font fuenteGrande = new Font("Arial", Font.PLAIN, 18); // Fuente para elementos de lista
            setFont(fuenteGrande);

            if (value instanceof evento) {
                evento evento = (evento) value;
                // Personalizar el texto que se muestra para cada evento
                setText(String.format("%s - %s - %d asistentes",
                        evento.getNombre(),
                        evento.getFecha(),
                        evento.getNumeroAsistentes()));
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
}
