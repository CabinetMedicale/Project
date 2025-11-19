package ma.WhiteLab.repository.modules.consultation.fileBase_implementation;

import ma.WhiteLab.entities.consultation.Consultation;
import ma.WhiteLab.entities.enums.StatusConsultation;
import ma.WhiteLab.repository.modules.consultation.api.ConsultationRepository;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Implémentation FILE-BASED via NIO
 * Lecture initiale depuis src/main/resources/fileBase/consultations.psv
 * Sauvegarde modifiable dans ~/.dentaltech/fileBase/consultations.psv
 * Format :
 * ID|Date|Status|Notes|ObservationsMedecin|DateCreation|DateMiseAJour|CreePar|ModifierPar
 */
public class ConsultationRepositoryImpl implements ConsultationRepository {

    private static final String CLASSPATH_FILE = "fileBase/consultations.psv";
    private static final String HEADER = "ID|Date|Status|Notes|ObservationsMedecin|DateCreation|DateMiseAJour|CreePar|ModifierPar";

    private final Path localFilePath =
            Paths.get(System.getProperty("user.home"), ".dentaltech", "fileBase", "consultations.psv");

    public ConsultationRepositoryImpl() {
        initializeLocalCopy();
    }

    /** Copie initiale de /resources/fileBase → ~/.dentaltech/fileBase */
    private void initializeLocalCopy() {
        try {
            if (!Files.exists(localFilePath)) {
                Files.createDirectories(localFilePath.getParent());
                URL resource = getClass().getClassLoader().getResource(CLASSPATH_FILE);
                if (resource != null) {
                    Path src = Paths.get(resource.toURI());
                    Files.copy(src, localFilePath, StandardCopyOption.REPLACE_EXISTING);
                } else {
                    Files.write(localFilePath, List.of(HEADER), StandardCharsets.UTF_8);
                }
            }
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException("Erreur d'initialisation du fichier consultations.psv", e);
        }
    }

    private List<String> readAllLines() {
        try {
            return Files.readAllLines(localFilePath, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Erreur de lecture du fichier consultations.psv", e);
        }
    }

    private void writeAllLines(List<String> lines) {
        try {
            Files.write(localFilePath, lines, StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Erreur d'écriture dans consultations.psv", e);
        }
    }

    // ===================== CRUD =====================

    @Override
    public List<Consultation> findAll() {
        return readAllLines().stream()
                .skip(1)
                .filter(line -> !line.isBlank())
                .map(this::toConsultation)
                .collect(Collectors.toList());
    }

    @Override
    public Consultation findById(Long id) {
        return findAll().stream()
                .filter(c -> Objects.equals(c.getId(), id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void create(Consultation consultation) {
        List<Consultation> consultations = findAll();
        long newId = consultations.stream()
                             .mapToLong(c -> c.getId() == null ? 0 : c.getId())
                             .max().orElse(0) + 1;
        consultation.setId(newId);
        if (consultation.getDateCreation() == null)
            consultation.setDateCreation(LocalDateTime.now());
        consultations.add(consultation);
        saveAll(consultations);
    }

    @Override
    public void update(Consultation consultation) {
        List<Consultation> consultations = findAll();
        for (int i = 0; i < consultations.size(); i++) {
            if (Objects.equals(consultations.get(i).getId(), consultation.getId())) {
                consultation.setDateMiseAJour(LocalDateTime.now());
                consultations.set(i, consultation);
                saveAll(consultations);
                return;
            }
        }
        throw new RuntimeException("Consultation avec ID " + consultation.getId() + " introuvable.");
    }

    @Override
    public void delete(Consultation consultation) {
        if (consultation != null && consultation.getId() != null)
            deleteById(consultation.getId());
    }

    @Override
    public void deleteById(Long id) {
        List<Consultation> consultations = findAll().stream()
                .filter(c -> !Objects.equals(c.getId(), id))
                .collect(Collectors.toList());
        saveAll(consultations);
    }

    // ===================== UTILITAIRES =====================

    private void saveAll(List<Consultation> consultations) {
        List<String> lines = new ArrayList<>();
        lines.add(HEADER);
        for (Consultation c : consultations) {
            lines.add(String.join("|",
                    stringOrNull(c.getId()),
                    stringOrNull(c.getDate()),
                    stringOrNull(c.getStatus()),
                    stringOrNull(c.getNotes()),
                    stringOrNull(c.getObservationsMedecin()),
                    stringOrNull(c.getDateCreation()),
                    stringOrNull(c.getDateMiseAJour()),
                    stringOrNull(c.getCreePar()),
                    stringOrNull(c.getModifierPar())
            ));
        }
        writeAllLines(lines);
    }

    private Consultation toConsultation(String line) {
        String[] t = line.split("\\|", -1);
        Consultation c = new Consultation();
        c.setId(parseLong(t[0]));
        c.setDate(parseNullableLocalDateTime(t[1]));
        c.setStatus(parseStatusConsultation(t[2]));
        c.setNotes(parseNullableString(t[3]));
        c.setObservationsMedecin(parseNullableString(t[4]));
        c.setDateCreation(parseNullableLocalDateTime(t[5]));
        c.setDateMiseAJour(parseNullableLocalDateTime(t[6]));
        c.setCreePar(parseNullableString(t[7]));
        c.setModifierPar(parseNullableString(t[8]));
        return c;
    }

    // ===================== Parsing helpers =====================

    private String parseNullableString(String s) {
        return (s == null || s.isBlank() || s.equalsIgnoreCase("null")) ? null : s.trim();
    }

    private LocalDateTime parseNullableLocalDateTime(String s) {
        if (s == null || s.isBlank() || s.equalsIgnoreCase("null")) return null;
        return LocalDateTime.parse(s.trim());
    }

    private StatusConsultation parseStatusConsultation(String s) {
        if (s == null || s.isBlank() || s.equalsIgnoreCase("null")) return null;
        try {
            return StatusConsultation.valueOf(s.trim());
        } catch (Exception e) {
            return null;
        }
    }

    private String stringOrNull(Object o) {
        if (o == null) return "null";
        if (o instanceof Enum<?>) return ((Enum<?>) o).name();
        return o.toString();
    }

    private Long parseLong(String s) {
        try { return (s == null || s.isBlank() || s.equalsIgnoreCase("null")) ? null : Long.parseLong(s.trim()); }
        catch (NumberFormatException e) { return null; }
    }
}

