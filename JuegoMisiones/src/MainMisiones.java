import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.List;

/**
 * author: Pedro Martínez Herrero
 * @since 18/12/2025
 * @until 18/12/2025
 * Actividad: clase MainMisiones
 */

public class MainMisiones {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("misionesJuego.odb");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        // Crear misiones
        Mision mision1 = new Mision("Rescatar al rey", null);
        Mision mision2 = new Mision("Derrotar al dragon", null);

        // Guardar objetos
        em.persist(mision1);
        em.persist(mision2);

        // Crear lista de personajes
        Personaje p1 = new Personaje("Waluigi", "Caballero", 90);
        Personaje p2 = new Personaje("Gandalf", "Mago", 81);

        List<Personaje> Participantes = new ArrayList<>();

        Participantes.add(p1);
        Participantes.add(p2);

        // Asignar los personajes a la misión1
        mision1.getParticipantes().addAll(Arrays.asList(p1, p2));

        // Guardar objetos
        em.persist(mision1);

        // Confirmar cambios
        em.getTransaction().commit();

        // Consulta
        System.out.println("### Personajes que participan en misiones ###");
        TypedQuery<Personaje> consulta = em.createQuery(
                "SELECT p FROM Mision m JOIN m.Participantes p WHERE m.descripcion = :desc", Personaje.class);

        consulta.setParameter("desc", "Rescatar al rey");

        List<Personaje> personajesParticipantes = consulta.getResultList();

        for (Personaje p : personajesParticipantes) {
            System.out.println(p);
        }

        // Cerrar conexiones
        em.close();
        emf.close();
        sc.close();

        System.out.println("Misiones guardadas correctamente en ObjectDB: ");
        System.out.println("\nInformación de Misión 1: ");
        System.out.println(" - Descripción: " + mision1.getDescripcion());
        System.out.println(" - Nº Participantes: " + mision1.getParticipantes().size());
    }
}