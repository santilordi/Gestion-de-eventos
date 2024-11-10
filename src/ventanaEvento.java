import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import com.toedter.calendar.JDateChooser;


public class ventanaEvento extends JFrame {
    private evento eventoActual;
    private gestorEventos gestor;

    // Componentes del formulario
    private JTextField txtNombre;
    private JFormattedTextField txtFecha;
    private JTextField txtUbicacion;
    private JTextArea txtDescripcion;
    private JButton btnGuardar;
    private JButton btnCancelar;
    private JCheckBox chkRealizado;
    private JDateChooser dateChooser;

    public ventanaEvento(evento evento, gestorEventos gestor) {
        this.eventoActual = evento;
        this.gestor = gestor;
        boolean esEdicion = (evento != null);

        // Configurar ventana
        setTitle(esEdicion ? "Editar Evento" : "Nuevo Evento");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700, 800);
        setLocationRelativeTo(null);
        setResizable(false);

        inicializarComponentes();
        if (esEdicion) {
            cargarDatosEvento();
        }

        setVisible(true);
    }

    private void inicializarComponentes() {
        // Panel principal con padding
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(20, 20));
        Font fuenteGrande = new Font("Arial", Font.PLAIN, 18);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Panel del formulario
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setFont(fuenteGrande);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Nombre
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setFont(fuenteGrande);
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(lblNombre, gbc);

        txtNombre = new JTextField(20);
        txtNombre.setFont(fuenteGrande);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        formPanel.add(txtNombre, gbc);

        // Fecha
        JLabel lblFecha = new JLabel("Fecha:");
        lblFecha.setFont(fuenteGrande);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        formPanel.add(lblFecha, gbc);

        // Correctamente inicializando JDateChooser
        dateChooser = new JDateChooser();
        dateChooser.setFont(fuenteGrande); // Aquí usamos dateChooser
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        formPanel.add(dateChooser, gbc);

        // Ubicación
        JLabel lblUbicacion = new JLabel("Ubicación:");
        lblUbicacion.setFont(fuenteGrande);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.0;
        formPanel.add(lblUbicacion, gbc);

        txtUbicacion = new JTextField(20);
        txtUbicacion.setFont(fuenteGrande);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        formPanel.add(txtUbicacion, gbc);

        // Descripción
        JLabel lblDescripcion = new JLabel("Descripción:");
        lblDescripcion.setFont(fuenteGrande);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.0;
        formPanel.add(lblDescripcion, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtDescripcion = new JTextArea(5, 20);
        txtDescripcion.setFont(fuenteGrande);
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);
        JScrollPane scrollDescripcion = new JScrollPane(txtDescripcion);
        formPanel.add(scrollDescripcion, gbc);

        // Checkbox para estado realizado
        chkRealizado = new JCheckBox("Evento realizado");
        chkRealizado.setFont(fuenteGrande);
        chkRealizado.setSelected(eventoActual != null && eventoActual.isRealizado());
        gbc.gridx = 1;
        gbc.gridy = 4;
        formPanel.add(chkRealizado, gbc);

        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnGuardar = new JButton("Guardar");
        btnCancelar = new JButton("Cancelar");

        btnGuardar.setFont(fuenteGrande);
        btnCancelar.setFont(fuenteGrande);

        buttonPanel.add(btnGuardar);
        buttonPanel.add(btnCancelar);

        // Agregar los paneles al panel principal
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Agregar el panel principal a la ventana
        add(mainPanel);

        // Configurar acciones de los botones
        btnGuardar.addActionListener(e -> guardarEvento());
        btnCancelar.addActionListener(e -> dispose());
    }

    private void cargarDatosEvento() {
        if (eventoActual != null) {
            txtNombre.setText(eventoActual.getNombre());
            txtUbicacion.setText(eventoActual.getUbicacion());
            txtDescripcion.setText(eventoActual.getDescripcion());

            Date fecha = Date.from(eventoActual.getFecha().atStartOfDay(ZoneId.systemDefault()).toInstant());
            dateChooser.setDate(fecha);
        }
    }

    private void guardarEvento() {
        // Validar campos
        if (!validarCampos()) {
            return;
        }

        // Crear nuevo evento o actualizar existente
        String nombre = txtNombre.getText().trim();
        LocalDate fecha = dateChooser.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        String ubicacion = txtUbicacion.getText().trim();
        String descripcion = txtDescripcion.getText().trim();

        boolean realizado = chkRealizado.isSelected();
        if (eventoActual == null) {
            // Nuevo evento
            evento nuevoEvento = new evento( nombre, fecha, ubicacion, descripcion);
            gestor.agregarEvento(nuevoEvento);
            nuevoEvento.setRealizado(realizado);
        } else {
            // Actualizar evento existente
            evento eventoActualizado = new evento(
                    nombre,
                    fecha,
                    ubicacion,
                    descripcion
            );
            // Mantener la lista de asistentes del evento original
            for (asistente asistente : eventoActual.getAsistentes()) {
                eventoActualizado.agregarAsistente(asistente);
            }
            eventoActualizado.setRealizado(realizado);
            gestor.editarEvento(eventoActual, eventoActualizado);
        }

        dispose();
    }

    private boolean validarCampos() {
        String errores = new String();

        if (txtNombre.getText().trim().isEmpty()) {
            errores = "- El nombre es obligatorio\n";
        }

        if (dateChooser.getDate() == null) {
            errores = "- La fecha es obligatoria\n";
        }

        if (txtUbicacion.getText().trim().isEmpty()) {
            errores = "- La ubicación es obligatoria\n";
        }

        if (txtDescripcion.getText().trim().isEmpty()) {
            errores = "- La descripción es obligatoria\n";
        }

        if (errores.length() > 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Por favor corrija los siguientes errores:\n" + errores.toString(),
                    "Error de validación",
                    JOptionPane.ERROR_MESSAGE
            );
            return false;
        }

        return true;
    }
}