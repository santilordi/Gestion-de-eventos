public class asistente {

    private String nombre;
    private String email;

    public asistente(String nombre, String email) {
        this.nombre = nombre;
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // @Override
    // public String toString() {
    //     return nombre + " (" + email + ")";
    // }

    @Override
    public String toString() {
        return nombre;  // Esto hará que la JList muestre solo el nombre
    }
}
