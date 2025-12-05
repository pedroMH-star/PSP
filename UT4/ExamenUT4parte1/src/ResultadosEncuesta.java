import java.util.*;

/**
 * author: Pedro Martínez Herrero
 * @since 29/11/2025
 * @until 01/12/2025
 * Examen UT4 - Resultados de la encuesta
 */

public class ResultadosEncuesta {
    private final Map<String, List<String>> datos = new HashMap<>();

    // Añade una respuesta a una zona (método sincronizado)
    public synchronized void agregarRespuesta(String zona, String respuesta) {
        datos.computeIfAbsent(zona, k -> new ArrayList<>()).add(respuesta);
    }

    // Obtiene resumen de una zona (método sincronizado)
    public synchronized String getResumenZona(String zona) {
        List<String> r = datos.get(zona);
        if (r == null || r.isEmpty()) {
            return "Zona '" + zona + "' sin respuestas registradas";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Resumen zona '").append(zona).append("':\n");
        int i = 1;

        for (String resp : r) {
            sb.append(i++).append(". ").append(resp).append("\n");
        }

        return sb.toString();
    }


    // Obtiene resumen global (método sincronizado)
    public synchronized String getResumenGlobal() {
        if (datos.isEmpty()) return "No hay datos de encuestas registrados";
        StringBuilder sb = new StringBuilder();
        sb.append("Resumen global de encuestas:\n");

        for (String zona : datos.keySet()) {
            sb.append("-- ").append(zona).append(" (").append(datos.get(zona).size()).append(" respuestas):\n");
            int i = 1;

            for (String r : datos.get(zona)) {
                sb.append(" ").append(i++).append(". ").append(r).append("\n");
            }
        }

        return sb.toString();
    }
}