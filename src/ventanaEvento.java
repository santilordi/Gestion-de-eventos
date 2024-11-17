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
    
    private JSpinner spnEquipoAudiovisual;
    private JSpinner spnCatering;
    private JSpinner spnSalones;


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

        // Recursos (JSpinners)
        spnEquipoAudiovisual = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1)); // Mínimo 1, máximo 10
        spnCatering = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1)); // Mínimo 1, máximo 10
        spnSalones = new JSpinner(new SpinnerNumberModel(1, 1, 5, 1)); // Mínimo 1, máximo 5

        // Equipo Audiovisual
        JLabel lblEquipoAudiovisual = new JLabel("Equipo Audiovisual:");
        lblEquipoAudiovisual.setFont(fuenteGrande);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0.0;
        formPanel.add(lblEquipoAudiovisual, gbc);

        spnEquipoAudiovisual = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
        spnEquipoAudiovisual.setFont(fuenteGrande);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        formPanel.add(spnEquipoAudiovisual, gbc);

        // Catering
        JLabel lblCatering = new JLabel("Catering:");
        lblCatering.setFont(fuenteGrande);
        gbc.gridx = 0;
        gbc.gridy = 5;
        formPanel.add(lblCatering, gbc);

        spnCatering = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
        spnCatering.setFont(fuenteGrande);
        gbc.gridx = 1;
        formPanel.add(spnCatering, gbc);

        // Salones
        JLabel lblSalones = new JLabel("Salones:");
        lblSalones.setFont(fuenteGrande);
        gbc.gridx = 0;
        gbc.gridy = 6;
        formPanel.add(lblSalones, gbc);

        spnSalones = new JSpinner(new SpinnerNumberModel(1, 1, 5, 1));
        spnSalones.setFont(fuenteGrande);
        gbc.gridx = 1;
        formPanel.add(spnSalones, gbc);

        // Checkbox para estado realizado
        chkRealizado = new JCheckBox("Evento realizado");
        chkRealizado.setFont(fuenteGrande);
        chkRealizado.setSelected(eventoActual != null && eventoActual.isRealizado());
        gbc.gridx = 1;
        gbc.gridy = 7; 
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

            spnEquipoAudiovisual.setValue(eventoActual.getEquipoAudiovisual());
            spnCatering.setValue(eventoActual.getCatering());
            spnSalones.setValue(eventoActual.getSalones());
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
    
        // Extraer valores de los JSpinners
        int equipoAudiovisual = Math.max(1, Math.min(10, (int) spnEquipoAudiovisual.getValue()));
        int catering = Math.max(1, Math.min(10, (int) spnCatering.getValue()));
        int salones = Math.max(1, Math.min(5, (int) spnSalones.getValue()));
            
        if (eventoActual == null) {
            // Nuevo evento
            evento nuevoEvento = new evento(nombre, ubicacion, fecha,descripcion, equipoAudiovisual, catering, salones);
            nuevoEvento.setRealizado(realizado);
            gestor.agregarEvento(nuevoEvento);
        } else {
            // Actualizar evento existente
            evento eventoActualizado = new evento(nombre, ubicacion, fecha, descripcion, equipoAudiovisual, catering, salones);
            eventoActualizado.setRealizado(realizado);
    
            // Mantener la lista de asistentes del evento original
            for (asistente asistente : eventoActual.getAsistentes()) {
                eventoActualizado.agregarAsistente(asistente);
            }
    
            gestor.editarEvento(eventoActual, eventoActualizado);
        }
    
        // Cerrar ventana
        dispose();
    }
    
    private boolean validarCampos() {
        StringBuilder errores = new StringBuilder();

        int equipoAudiovisual = (int) spnEquipoAudiovisual.getValue();
        int catering = (int) spnCatering.getValue();
        int salones = (int) spnSalones.getValue();

        int contador = 0;
    
        // Validación de campos
        if (txtNombre.getText().trim().isEmpty()) {
            errores.append("- El nombre es obligatorio\n");
            contador ++;
        }

        if (dateChooser.getDate() == null) {
            errores.append("- La fecha es obligatoria\n");
            contador ++;
        }
    
        if (txtUbicacion.getText().trim().isEmpty()) {
            errores.append("- La ubicación es obligatoria\n");
            contador ++;
        }

        if (equipoAudiovisual < 1 || equipoAudiovisual > 10) {
            equipoAudiovisual = Math.max(1, Math.min(10, equipoAudiovisual)); 
            JOptionPane.showMessageDialog(this, "El valor para Equipo Audiovisual debe estar entre 1 y 10.");
            contador ++;
        }
        
        if (catering < 1 || catering > 10) {
            catering = Math.max(1, Math.min(10, catering));
            JOptionPane.showMessageDialog(this, "El valor para Catering debe estar entre 1 y 10.");
            contador ++;
        }
        
        if (salones < 1 || salones > 5) {
            salones = Math.max(1, Math.min(5, salones));
            JOptionPane.showMessageDialog(this, "El valor para Salones debe estar entre 1 y 5.");
            contador ++;
        }
    
        if (txtDescripcion.getText().trim().isEmpty()) {
            errores.append("- La descripción es obligatoria\n");
            contador ++;
        }

    
        if (contador > 0) {
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