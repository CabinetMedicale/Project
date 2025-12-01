package ma.WhiteLab.repository.modules.prescription.impl.mySQL;

import ma.WhiteLab.entities.prescription.Ordonnance;
import ma.WhiteLab.conf.SessionFactory;
import ma.WhiteLab.repository.common.RowMappers;
import ma.WhiteLab.repository.modules.prescription.api.OrdonnanceRepository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrdonnanceRepositoryImpl implements OrdonnanceRepository {

    // -------- CRUD --------
    @Override
    public List<Ordonnance> findAll() {
        String sql = "SELECT * FROM Ordonnance ORDER BY dateOrdonnance DESC, id DESC";
        List<Ordonnance> out = new ArrayList<>();

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                out.add(RowMappers.mapOrdonnance(rs));
            }

        } catch (SQLException e) { 
            throw new RuntimeException(e); 
        }

        return out;
    }

    @Override
    public Ordonnance findById(Long id) {
        String sql = "SELECT * FROM Ordonnance WHERE id = ?";

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return RowMappers.mapOrdonnance(rs);
                return null;
            }

        } catch (SQLException e) { 
            throw new RuntimeException(e); 
        }
    }

    @Override
    public void create(Ordonnance ordonnance) {
        String sql = """
            INSERT INTO Ordonnance(dateOrdonnance, consultation_id, dossierMedical_id,
                                  dateCreation, dateMiseAJour, creePar, modifierPar)
            VALUES(?,?,?,NOW(),NOW(),?,?)
        """;

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            if (ordonnance.getDateOrdonnance() != null) {
                ps.setDate(1, Date.valueOf(ordonnance.getDateOrdonnance()));
            } else {
                ps.setDate(1, null);
            }

            ps.setLong(2, ordonnance.getConsultation() != null ? ordonnance.getConsultation().getId() : null);
            ps.setLong(3, ordonnance.getDossierMedical() != null ? ordonnance.getDossierMedical().getId() : null);
            ps.setString(4, ordonnance.getCreePar());
            ps.setString(5, ordonnance.getModifierPar());

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) ordonnance.setId(keys.getLong(1));
            }

        } catch (SQLException e) { 
            throw new RuntimeException(e); 
        }
    }

    @Override
    public void update(Ordonnance ordonnance) {
        String sql = """
            UPDATE Ordonnance 
            SET dateOrdonnance=?, consultation_id=?, dossierMedical_id=?,
                dateMiseAJour=NOW(), modifierPar=? 
            WHERE id=?
        """;

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            if (ordonnance.getDateOrdonnance() != null) {
                ps.setDate(1, Date.valueOf(ordonnance.getDateOrdonnance()));
            } else {
                ps.setDate(1, null);
            }

            ps.setLong(2, ordonnance.getConsultation() != null ? ordonnance.getConsultation().getId() : null);
            ps.setLong(3, ordonnance.getDossierMedical() != null ? ordonnance.getDossierMedical().getId() : null);
            ps.setString(4, ordonnance.getModifierPar());
            ps.setLong(5, ordonnance.getId());

            ps.executeUpdate();

        } catch (SQLException e) { 
            throw new RuntimeException(e); 
        }
    }

    @Override
    public void delete(Ordonnance ordonnance) {
        if (ordonnance != null) deleteById(ordonnance.getId());
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM Ordonnance WHERE id = ?";

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
    public List<Ordonnance> findByDateOrdonnance(LocalDate date) {
        String sql = "SELECT * FROM Ordonnance WHERE dateOrdonnance = ? ORDER BY id DESC";
        List<Ordonnance> out = new ArrayList<>();

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(date));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) out.add(RowMappers.mapOrdonnance(rs));
            }

        } catch (SQLException e) { 
            throw new RuntimeException(e); 
        }

        return out;
    }

    @Override
    public List<Ordonnance> findByDateRange(LocalDate start, LocalDate end) {
        String sql = "SELECT * FROM Ordonnance WHERE dateOrdonnance >= ? AND dateOrdonnance <= ? ORDER BY dateOrdonnance DESC";
        List<Ordonnance> out = new ArrayList<>();

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(start));
            ps.setDate(2, Date.valueOf(end));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) out.add(RowMappers.mapOrdonnance(rs));
            }

        } catch (SQLException e) { 
            throw new RuntimeException(e); 
        }

        return out;
    }

    @Override
    public List<Ordonnance> findByDossierMedical(Long dossierMedicalId) {
        String sql = "SELECT * FROM Ordonnance WHERE dossierMedical_id = ? ORDER BY dateOrdonnance DESC";
        List<Ordonnance> out = new ArrayList<>();

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, dossierMedicalId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) out.add(RowMappers.mapOrdonnance(rs));
            }

        } catch (SQLException e) { 
            throw new RuntimeException(e); 
        }

        return out;
    }

    @Override
    public List<Ordonnance> findByConsultation(Long consultationId) {
        String sql = "SELECT * FROM Ordonnance WHERE consultation_id = ? ORDER BY dateOrdonnance DESC";
        List<Ordonnance> out = new ArrayList<>();

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, consultationId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) out.add(RowMappers.mapOrdonnance(rs));
            }

        } catch (SQLException e) { 
            throw new RuntimeException(e); 
        }

        return out;
    }

    @Override
    public Optional<Ordonnance> findByConsultationAndDate(Long consultationId, LocalDate date) {
        String sql = "SELECT * FROM Ordonnance WHERE consultation_id = ? AND dateOrdonnance = ?";

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, consultationId);
            ps.setDate(2, Date.valueOf(date));

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(RowMappers.mapOrdonnance(rs));
                return Optional.empty();
            }

        } catch (SQLException e) { 
            throw new RuntimeException(e); 
        }
    }

    @Override
    public long countByDate(LocalDate date) {
        String sql = "SELECT COUNT(*) FROM Ordonnance WHERE dateOrdonnance = ?";

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(date));

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getLong(1);
            }

        } catch (SQLException e) { 
            throw new RuntimeException(e); 
        }

        return 0;
    }
}
