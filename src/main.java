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

            // Iniciar la ventana principal
            new ventanaPrincipal(gestor);
        });
    }
}