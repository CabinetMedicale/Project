package ma.WhiteLab.repository.modules.prescription.impl.mySQL;

import ma.WhiteLab.entities.prescription.Prescription;
import ma.WhiteLab.conf.SessionFactory;
import ma.WhiteLab.repository.common.RowMappers;
import ma.WhiteLab.repository.modules.prescription.api.PrescriptionRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PrescriptionRepositoryImpl implements PrescriptionRepository {

    // -------- CRUD --------
    @Override
    public List<Prescription> findAll() {
        String sql = "SELECT * FROM Prescription ORDER BY id";
        List<Prescription> out = new ArrayList<>();

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                out.add(RowMappers.mapPrescription(rs));
            }

        } catch (SQLException e) { 
            throw new RuntimeException(e); 
        }

        return out;
    }

    @Override
    public Prescription findById(Long id) {
        String sql = "SELECT * FROM Prescription WHERE id = ?";

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return RowMappers.mapPrescription(rs);
                return null;
            }

        } catch (SQLException e) { 
            throw new RuntimeException(e); 
        }
    }

    @Override
    public void create(Prescription prescription) {
        String sql = """
            INSERT INTO Prescription(qte, frequence, duree, ordonnance_id, medicament_id,
                                    dateCreation, dateMiseAJour, creePar, modifierPar)
            VALUES(?,?,?,?,?,NOW(),NOW(),?,?)
        """;

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, prescription.getQte());
            ps.setString(2, prescription.getFrequence());
            ps.setInt(3, prescription.getDuree());
            ps.setLong(4, prescription.getOrdonnance() != null ? prescription.getOrdonnance().getId() : null);
            ps.setLong(5, prescription.getMedicament() != null ? prescription.getMedicament().getId() : null);
            ps.setString(6, prescription.getCreePar());
            ps.setString(7, prescription.getModifierPar());

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) prescription.setId(keys.getLong(1));
            }

        } catch (SQLException e) { 
            throw new RuntimeException(e); 
        }
    }

    @Override
    public void update(Prescription prescription) {
        String sql = """
            UPDATE Prescription 
            SET qte=?, frequence=?, duree=?, ordonnance_id=?, medicament_id=?,
                dateMiseAJour=NOW(), modifierPar=? 
            WHERE id=?
        """;

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, prescription.getQte());
            ps.setString(2, prescription.getFrequence());
            ps.setInt(3, prescription.getDuree());
            ps.setLong(4, prescription.getOrdonnance() != null ? prescription.getOrdonnance().getId() : null);
            ps.setLong(5, prescription.getMedicament() != null ? prescription.getMedicament().getId() : null);
            ps.setString(6, prescription.getModifierPar());
            ps.setLong(7, prescription.getId());

            ps.executeUpdate();

        } catch (SQLException e) { 
            throw new RuntimeException(e); 
        }
    }

    @Override
    public void delete(Prescription prescription) {
        if (prescription != null) deleteById(prescription.getId());
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM Prescription WHERE id = ?";

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
    public List<Prescription> findByOrdonnance(Long ordonnanceId) {
        String sql = "SELECT * FROM Prescription WHERE ordonnance_id = ? ORDER BY id";
        List<Prescription> out = new ArrayList<>();

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, ordonnanceId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) out.add(RowMappers.mapPrescription(rs));
            }

        } catch (SQLException e) { 
            throw new RuntimeException(e); 
        }

        return out;
    }

    @Override
    public List<Prescription> findByMedicament(Long medicamentId) {
        String sql = "SELECT * FROM Prescription WHERE medicament_id = ? ORDER BY id";
        List<Prescription> out = new ArrayList<>();

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, medicamentId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) out.add(RowMappers.mapPrescription(rs));
            }

        } catch (SQLException e) { 
            throw new RuntimeException(e); 
        }

        return out;
    }

    @Override
    public List<Prescription> findByOrdonnanceAndMedicament(Long ordonnanceId, Long medicamentId) {
        String sql = "SELECT * FROM Prescription WHERE ordonnance_id = ? AND medicament_id = ? ORDER BY id";
        List<Prescription> out = new ArrayList<>();

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, ordonnanceId);
            ps.setLong(2, medicamentId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) out.add(RowMappers.mapPrescription(rs));
            }

        } catch (SQLException e) { 
            throw new RuntimeException(e); 
        }

        return out;
    }

    @Override
    public long countByOrdonnance(Long ordonnanceId) {
        String sql = "SELECT COUNT(*) FROM Prescription WHERE ordonnance_id = ?";

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, ordonnanceId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getLong(1);
            }

        } catch (SQLException e) { 
            throw new RuntimeException(e); 
        }

        return 0;
    }

    @Override
    public long countByMedicament(Long medicamentId) {
        String sql = "SELECT COUNT(*) FROM Prescription WHERE medicament_id = ?";

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, medicamentId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getLong(1);
            }

        } catch (SQLException e) { 
            throw new RuntimeException(e); 
        }

        return 0;
    }
}
