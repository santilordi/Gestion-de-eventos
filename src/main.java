import javax.swing.*;

public class main {
    public static void main(String[] args) {
        // Asegurarse de que la interfaz se ejecute en el Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            try {
                // Intentar establecer el look and feel del sistema operativo
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Crear el gestor de eventos
            gestorEventos gestor = new gestorEventos();

            // Crear algunos eventos de ejemplo (opcional)
            evento evento1 = new evento("Conferencia Java", "2024-05-15", "Auditorio Principal", "Conferencia sobre Java y Sus Frameworks");
            evento evento2 = new evento("Workshop Spring", "2024-06-01", "Sala 101", "Taller práctico de Spring Boot");

            // Agregar los eventos al gestor
            gestor.agregarEvento(evento1);
            gestor.agregarEvento(evento2);

            // Iniciar la ventana principal
            new ventanaPrincipal(gestor);
        });
    }
}