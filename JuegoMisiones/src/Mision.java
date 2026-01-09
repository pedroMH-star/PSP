import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * author: Pedro Martínez Herrero
 * @since 18/12/2025
 * @until 18/12/2025
 * Actividad: clase Mision
 */

@Entity
public class Mision {

    @Id
    @GeneratedValue
    private Long id;

    private String descripcion;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Personaje> Participantes = new ArrayList<>();

    public Mision() {
        // Constructor por defecto obligatorio
    }

    public Mision(String descripcion, List<Personaje> Participantes) {
        this.descripcion = descripcion;
        this.Participantes = (Participantes != null) ? Participantes : new ArrayList<>();;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Personaje> getParticipantes() {
        return Participantes;
    }
    public void setParticipantes(List<Personaje> Participantes) {
        this.Participantes = Participantes;
    }

    @Override
    public String toString() {
        return "Personaje [id = " + id
                + ", descripción = " + descripcion
                + ", Participantes = " + Participantes.size() + "]";
    }
}