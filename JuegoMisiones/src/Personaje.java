import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

/**
 * author: Pedro Martínez Herrero
 * @since 18/12/2025
 * @until 18/12/2025
 * Actividad: clase Personaje
 */

@Entity
public class Personaje {

    @Id
    @GeneratedValue
    private Long id;

    private String nombre;
    private String clasePersonaje;
    private int nivel;

    public Personaje() {
        // Constructor por defecto obligatorio
    }

    public Personaje(String nombre, String clasePersonaje, int nivel) {
        this.nombre = nombre;
        this.clasePersonaje = clasePersonaje;
        this.nivel = nivel;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getClasePersonaje(String clasePersonaje) { return this.clasePersonaje; }
    public void setClasePersonaje(String clasePersonaje) { this.clasePersonaje = clasePersonaje; }

    public int getNivel() { return nivel; }
    public void setNivel(int nivel) { this.nivel = nivel; }

    @Override
    public String toString() {
        return "Personaje [id = " + id
                + ", nombre = " + nombre
                + ", clase del personaje = " + clasePersonaje
                + ", nivel = " + nivel + "]";
    }
}