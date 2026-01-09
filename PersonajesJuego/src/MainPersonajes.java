import jakarta.persistence.*;
import java.util.List;

/**
 * author: Pedro Martínez Herrero
 * @since 18/12/2025
 * @until 18/12/2025
 * Actividad: clase MainPersonajes
 */

public class MainPersonajes {
    public static void main(String[] args) {

        // Crear la conexión con la base de datos
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("personajesJuego.odb");
        EntityManager em = emf.createEntityManager();

        // Inicia la transacción
        em.getTransaction().begin();

        // Crear personajes
        Personaje p1 = new Personaje("Arthas", "Gerrero", 10);
        Personaje p2 = new Personaje("Jaina", "Mago", 12);
        Personaje p3 = new Personaje("Sylvanas", "Arquero", 8);
        Personaje p4 = new Personaje("Thrall", "Chamán", 14);
        Personaje p5 = new Personaje("Anduin", "Sacerdote", 9);

        // Guardar objetos
        em.persist(p1);
        em.persist(p2);
        em.persist(p3);
        em.persist(p4);
        em.persist(p5);

        // Confirmar cambios
        em.getTransaction().commit();

        // Cerrar conexiones
        em.close();
        emf.close();

        System.out.println("Personajes guardados correctamente con Jakarta Persistence.");
    }
}
