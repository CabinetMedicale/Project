package ma.WhiteLab.repository.modules.prescription.impl.mySQL;

import ma.WhiteLab.entities.prescription.Medicament;
import ma.WhiteLab.entities.enums.Forme;
import ma.WhiteLab.conf.SessionFactory;
import ma.WhiteLab.repository.common.RowMappers;
import ma.WhiteLab.repository.modules.prescription.api.MedicamentRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MedicamentRepositoryImpl implements MedicamentRepository {

    // -------- CRUD --------
    @Override
    public List<Medicament> findAll() {
        String sql = "SELECT * FROM Medicament ORDER BY nom, labo";
        List<Medicament> out = new ArrayList<>();

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                out.add(RowMappers.mapMedicament(rs));
            }

        } catch (SQLException e) { 
            throw new RuntimeException(e); 
        }

        return out;
    }

    @Override
    public Medicament findById(Long id) {
        String sql = "SELECT * FROM Medicament WHERE id = ?";

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return RowMappers.mapMedicament(rs);
                return null;
            }

        } catch (SQLException e) { 
            throw new RuntimeException(e); 
        }
    }

    @Override
    public void create(Medicament medicament) {
        String sql = """
            INSERT INTO Medicament(nom, labo, type, forme, remboursable, prixUnitaire, description,
                                  dateCreation, dateMiseAJour, creePar, modifierPar)
            VALUES(?,?,?,?,?,?,?,NOW(),NOW(),?,?)
        """;

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, medicament.getNom());
            ps.setString(2, medicament.getLabo());
            ps.setString(3, medicament.getType());
            ps.setString(4, medicament.getForme() != null ? medicament.getForme().name() : null);
            ps.setBoolean(5, medicament.isRemboursable());
            ps.setDouble(6, medicament.getPrixUnitaire());
            ps.setString(7, medicament.getDescription());
            ps.setString(8, medicament.getCreePar());
            ps.setString(9, medicament.getModifierPar());

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) medicament.setId(keys.getLong(1));
            }

        } catch (SQLException e) { 
            throw new RuntimeException(e); 
        }
    }

    @Override
    public void update(Medicament medicament) {
        String sql = """
            UPDATE Medicament 
            SET nom=?, labo=?, type=?, forme=?, remboursable=?, prixUnitaire=?, description=?,
                dateMiseAJour=NOW(), modifierPar=? 
            WHERE id=?
        """;

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, medicament.getNom());
            ps.setString(2, medicament.getLabo());
            ps.setString(3, medicament.getType());
            ps.setString(4, medicament.getForme() != null ? medicament.getForme().name() : null);
            ps.setBoolean(5, medicament.isRemboursable());
            ps.setDouble(6, medicament.getPrixUnitaire());
            ps.setString(7, medicament.getDescription());
            ps.setString(8, medicament.getModifierPar());
            ps.setLong(9, medicament.getId());

            ps.executeUpdate();

        } catch (SQLException e) { 
            throw new RuntimeException(e); 
        }
    }

    @Override
    public void delete(Medicament medicament) {
        if (medicament != null) deleteById(medicament.getId());
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM Medicament WHERE id = ?";

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, id);
            ps.executeUpdate();

        } catch (SQLException e) { 
            throw new RuntimeException(e); 
        }
    }

    // -------- Advanced Queries --------

    @Override
    public Optional<Medicament> findByNom(String nom) {
        String sql = "SELECT * FROM Medicament WHERE nom = ?";

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, nom);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(RowMappers.mapMedicament(rs));
                return Optional.empty();
            }

        } catch (SQLException e) { 
            throw new RuntimeException(e); 
        }
    }

    @Override
    public List<Medicament> findByForme(Forme forme) {
        String sql = "SELECT * FROM Medicament WHERE forme = ? ORDER BY nom";
        List<Medicament> out = new ArrayList<>();

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, forme.name());

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) out.add(RowMappers.mapMedicament(rs));
            }

        } catch (SQLException e) { 
            throw new RuntimeException(e); 
        }

        return out;
    }

    @Override
    public List<Medicament> findByRemboursable(boolean remboursable) {
        String sql = "SELECT * FROM Medicament WHERE remboursable = ? ORDER BY nom";
        List<Medicament> out = new ArrayList<>();

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setBoolean(1, remboursable);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) out.add(RowMappers.mapMedicament(rs));
            }

        } catch (SQLException e) { 
            throw new RuntimeException(e); 
        }

        return out;
    }

    @Override
    public List<Medicament> findByLabo(String labo) {
        String sql = "SELECT * FROM Medicament WHERE labo = ? ORDER BY nom";
        List<Medicament> out = new ArrayList<>();

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, labo);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) out.add(RowMappers.mapMedicament(rs));
            }

        } catch (SQLException e) { 
            throw new RuntimeException(e); 
        }

        return out;
    }

    @Override
    public List<Medicament> findByType(String type) {
        String sql = "SELECT * FROM Medicament WHERE type = ? ORDER BY nom";
        List<Medicament> out = new ArrayList<>();

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, type);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) out.add(RowMappers.mapMedicament(rs));
            }

        } catch (SQLException e) { 
            throw new RuntimeException(e); 
        }

        return out;
    }

    @Override
    public List<Medicament> searchByNom(String nomPattern) {
        String sql = "SELECT * FROM Medicament WHERE nom LIKE ? ORDER BY nom";
        List<Medicament> out = new ArrayList<>();

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, "%" + nomPattern + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) out.add(RowMappers.mapMedicament(rs));
            }

        } catch (SQLException e) { 
            throw new RuntimeException(e); 
        }

        return out;
    }

    @Override
    public boolean existsByNom(String nom) {
        String sql = "SELECT 1 FROM Medicament WHERE nom = ?";

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, nom);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) { 
            throw new RuntimeException(e); 
        }
    }
}
