package ma.WhiteLab.repository.modules.cabinet.fileBase_implementation;

import ma.WhiteLab.entities.cabinet.CabinetMedicale;
import ma.WhiteLab.repository.modules.cabinet.api.CabinetRepository;

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
 * Lecture initiale depuis src/main/resources/fileBase/cabinets.psv
 * Sauvegarde modifiable dans ~/.dentaltech/fileBase/cabinets.psv
 * Format :
 * ID|Nom|Email|Logo|Categorie|Tel1|Tel2|SiteWeb|Instagram|Facebook|Description|DateCreation|DateMiseAJour|CreePar|ModifierPar
 */
public class CabinetRepositoryImpl implements CabinetRepository {

    private static final String CLASSPATH_FILE = "fileBase/cabinets.psv";
    private static final String HEADER = "ID|Nom|Email|Logo|Categorie|Tel1|Tel2|SiteWeb|Instagram|Facebook|Description|DateCreation|DateMiseAJour|CreePar|ModifierPar";

    private final Path localFilePath =
            Paths.get(System.getProperty("user.home"), ".dentaltech", "fileBase", "cabinets.psv");

    public CabinetRepositoryImpl() {
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
            throw new RuntimeException("Erreur d'initialisation du fichier cabinets.psv", e);
        }
    }

    private List<String> readAllLines() {
        try {
            return Files.readAllLines(localFilePath, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Erreur de lecture du fichier cabinets.psv", e);
        }
    }

    private void writeAllLines(List<String> lines) {
        try {
            Files.write(localFilePath, lines, StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Erreur d'écriture dans cabinets.psv", e);
        }
    }

    // ===================== CRUD =====================

    @Override
    public List<CabinetMedicale> findAll() {
        return readAllLines().stream()
                .skip(1)
                .filter(line -> !line.isBlank())
                .map(this::toCabinet)
                .collect(Collectors.toList());
    }

    @Override
    public CabinetMedicale findById(Long id) {
        return findAll().stream()
                .filter(c -> Objects.equals(c.getId(), id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void create(CabinetMedicale cabinet) {
        List<CabinetMedicale> cabinets = findAll();
        long newId = cabinets.stream()
                             .mapToLong(c -> c.getId() == null ? 0 : c.getId())
                             .max().orElse(0) + 1;
        cabinet.setId(newId);
        if (cabinet.getDateCreation() == null)
            cabinet.setDateCreation(LocalDateTime.now());
        cabinets.add(cabinet);
        saveAll(cabinets);
    }

    @Override
    public void update(CabinetMedicale cabinet) {
        List<CabinetMedicale> cabinets = findAll();
        for (int i = 0; i < cabinets.size(); i++) {
            if (Objects.equals(cabinets.get(i).getId(), cabinet.getId())) {
                cabinet.setDateMiseAJour(LocalDateTime.now());
                cabinets.set(i, cabinet);
                saveAll(cabinets);
                return;
            }
        }
        throw new RuntimeException("Cabinet avec ID " + cabinet.getId() + " introuvable.");
    }

    @Override
    public void delete(CabinetMedicale cabinet) {
        if (cabinet != null && cabinet.getId() != null)
            deleteById(cabinet.getId());
    }

    @Override
    public void deleteById(Long id) {
        List<CabinetMedicale> cabinets = findAll().stream()
                .filter(c -> !Objects.equals(c.getId(), id))
                .collect(Collectors.toList());
        saveAll(cabinets);
    }

    // ===================== UTILITAIRES =====================

    private void saveAll(List<CabinetMedicale> cabinets) {
        List<String> lines = new ArrayList<>();
        lines.add(HEADER);
        for (CabinetMedicale c : cabinets) {
            lines.add(String.join("|",
                    stringOrNull(c.getId()),
                    stringOrNull(c.getNom()),
                    stringOrNull(c.getEmail()),
                    stringOrNull(c.getLogo()),
                    stringOrNull(c.getCategorie()),
                    stringOrNull(c.getTel1()),
                    stringOrNull(c.getTel2()),
                    stringOrNull(c.getSiteWeb()),
                    stringOrNull(c.getInstagram()),
                    stringOrNull(c.getFacebook()),
                    stringOrNull(c.getDescription()),
                    stringOrNull(c.getDateCreation()),
                    stringOrNull(c.getDateMiseAJour()),
                    stringOrNull(c.getCreePar()),
                    stringOrNull(c.getModifierPar())
            ));
        }
        writeAllLines(lines);
    }

    private CabinetMedicale toCabinet(String line) {
        String[] t = line.split("\\|", -1);
        CabinetMedicale c = new CabinetMedicale();
        c.setId(parseLong(t[0]));
        c.setNom(parseNullableString(t[1]));
        c.setEmail(parseNullableString(t[2]));
        c.setLogo(parseNullableString(t[3]));
        c.setCategorie(parseNullableString(t[4]));
        c.setTel1(parseNullableString(t[5]));
        c.setTel2(parseNullableString(t[6]));
        c.setSiteWeb(parseNullableString(t[7]));
        c.setInstagram(parseNullableString(t[8]));
        c.setFacebook(parseNullableString(t[9]));
        c.setDescription(parseNullableString(t[10]));
        c.setDateCreation(parseNullableLocalDateTime(t[11]));
        c.setDateMiseAJour(parseNullableLocalDateTime(t[12]));
        c.setCreePar(parseNullableString(t[13]));
        c.setModifierPar(parseNullableString(t[14]));
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

    private String stringOrNull(Object o) {
        if (o == null) return "null";
        return o.toString();
    }

    private Long parseLong(String s) {
        try { return (s == null || s.isBlank() || s.equalsIgnoreCase("null")) ? null : Long.parseLong(s.trim()); }
        catch (NumberFormatException e) { return null; }
    }
}

