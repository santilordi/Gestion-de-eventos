import javax.swing.*;
import java.awt.*;

public class ventanaAsistentes extends JFrame {
    private evento evento;
    private gestorEventos gestor;
    private JList<asistente> listaAsistentes;
    private DefaultListModel<asistente> modeloLista;
    private JTextField txtNombre;
    private JTextField txtEmail;
    private JTextField txtTelefono;

    public ventanaAsistentes(evento evento, gestorEventos gestor) {
        this.evento = evento;
        this.gestor = gestor;
        inicializarComponentes();
        cargarAsistentes();
        setVisible(true);
    }

    private void inicializarComponentes() {
        setTitle("Gestión de Asistentes - " + evento.getNombre());
        setLayout(new BorderLayout());
        setSize(400, 500);
        Font fuenteGrande = new Font("Arial", Font.PLAIN, 18);
        setLocationRelativeTo(null);

        // Panel superior con información del evento
        JPanel panelEvento = new JPanel();
        panelEvento.add(new JLabel("Evento: " + evento.getNombre()));
        panelEvento.setFont(fuenteGrande);

        // Panel central con lista de asistentes
        modeloLista = new DefaultListModel<>();
        listaAsistentes = new JList<>(modeloLista);
        JScrollPane scrollLista = new JScrollPane(listaAsistentes);

        // Panel de botones para la lista
        JPanel panelBotones = new JPanel();


        // Panel de ingreso de nuevo asistente
        JPanel panelNuevo = new JPanel(new GridLayout(4, 2));
        txtNombre = new JTextField();
        txtEmail = new JTextField();
        txtTelefono = new JTextField();

        panelNuevo.add(new JLabel("Nombre:"));
        panelNuevo.add(txtNombre);
        panelNuevo.add(new JLabel("Email:"));
        panelNuevo.add(txtEmail);
        panelNuevo.add(new JLabel("Teléfono:"));
        panelNuevo.add(txtTelefono);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(e -> guardarAsistente());
        panelNuevo.add(btnGuardar);

        // Agregar todos los componentes
        add(panelEvento, BorderLayout.NORTH);
        add(scrollLista, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.EAST);
        add(panelNuevo, BorderLayout.SOUTH);
    }

    private void cargarAsistentes() {
        modeloLista.clear();
        for (asistente asistente : evento.getAsistentes()) {
            modeloLista.addElement(asistente);
        }
    }

    private void guardarAsistente() {
        String nombre = txtNombre.getText().trim();
        String email = txtEmail.getText().trim();
        String telefono = txtTelefono.getText().trim();

        if (nombre.isEmpty() || email.isEmpty() || telefono.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Por favor, complete todos los campos",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        asistente nuevoAsistente = new asistente(nombre, email);
        gestor.agregarAsistenteAEvento(evento, nuevoAsistente);
        cargarAsistentes();
        limpiarCampos();
    }

    private void limpiarCampos() {
        txtNombre.setText("");
        txtEmail.setText("");
        txtTelefono.setText("");
    }
}