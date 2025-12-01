package ma.WhiteLab.repository.modules.planning.impl.mySQL;

import ma.WhiteLab.entities.planning.RendezVous;
import ma.WhiteLab.entities.enums.Status;
import ma.WhiteLab.conf.SessionFactory;
import ma.WhiteLab.repository.common.RowMappers;
import ma.WhiteLab.repository.modules.planning.api.RendezVousRepository;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RendezVousRepositoryImpl implements RendezVousRepository {

    // -------- CRUD --------
    @Override
    public List<RendezVous> findAll() {
        String sql = "SELECT * FROM RendezVous ORDER BY date, time";
        List<RendezVous> out = new ArrayList<>();

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                out.add(RowMappers.mapRendezVous(rs));
            }

        } catch (SQLException e) { 
            throw new RuntimeException(e); 
        }

        return out;
    }

    @Override
    public RendezVous findById(Long id) {
        String sql = "SELECT * FROM RendezVous WHERE id = ?";

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return RowMappers.mapRendezVous(rs);
                return null;
            }

        } catch (SQLException e) { 
            throw new RuntimeException(e); 
        }
    }

    @Override
    public void create(RendezVous rdv) {
        String sql = """
            INSERT INTO RendezVous(date, time, motif, status, noteMedecin, 
                                  dossierMed_id, consultation_id,
                                  dateCreation, dateMiseAJour, creePar, modifierPar)
            VALUES(?,?,?,?,?,?,?,NOW(),NOW(),?,?)
        """;

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            if (rdv.getDate() != null) {
                ps.setTimestamp(1, Timestamp.valueOf(rdv.getDate()));
            } else {
                ps.setTimestamp(1, null);
            }

            if (rdv.getTime() != null) {
                ps.setTime(2, Time.valueOf(rdv.getTime()));
            } else {
                ps.setTime(2, null);
            }

            ps.setString(3, rdv.getMotif());
            ps.setString(4, rdv.getStatus() != null ? rdv.getStatus().name() : null);
            ps.setString(5, rdv.getNoteMedecin());
            
            ps.setLong(6, rdv.getDossierMed() != null ? rdv.getDossierMed().getId() : null);
            ps.setLong(7, rdv.getConsultation() != null ? rdv.getConsultation().getId() : null);
            
            ps.setString(8, rdv.getCreePar());
            ps.setString(9, rdv.getModifierPar());

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) rdv.setId(keys.getLong(1));
            }

        } catch (SQLException e) { 
            throw new RuntimeException(e); 
        }
    }

    @Override
    public void update(RendezVous rdv) {
        String sql = """
            UPDATE RendezVous 
            SET date=?, time=?, motif=?, status=?, noteMedecin=?, 
                dossierMed_id=?, consultation_id=?,
                dateMiseAJour=NOW(), modifierPar=? 
            WHERE id=?
        """;

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            if (rdv.getDate() != null) {
                ps.setTimestamp(1, Timestamp.valueOf(rdv.getDate()));
            } else {
                ps.setTimestamp(1, null);
            }

            if (rdv.getTime() != null) {
                ps.setTime(2, Time.valueOf(rdv.getTime()));
            } else {
                ps.setTime(2, null);
            }

            ps.setString(3, rdv.getMotif());
            ps.setString(4, rdv.getStatus() != null ? rdv.getStatus().name() : null);
            ps.setString(5, rdv.getNoteMedecin());
            
            ps.setLong(6, rdv.getDossierMed() != null ? rdv.getDossierMed().getId() : null);
            ps.setLong(7, rdv.getConsultation() != null ? rdv.getConsultation().getId() : null);
            
            ps.setString(8, rdv.getModifierPar());
            ps.setLong(9, rdv.getId());

            ps.executeUpdate();

        } catch (SQLException e) { 
            throw new RuntimeException(e); 
        }
    }

    @Override
    public void delete(RendezVous rdv) {
        if (rdv != null) deleteById(rdv.getId());
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM RendezVous WHERE id = ?";

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
    public List<RendezVous> findByDate(LocalDate date) {
        String sql = "SELECT * FROM RendezVous WHERE DATE(date) = ? ORDER BY time";
        List<RendezVous> out = new ArrayList<>();

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(date));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) out.add(RowMappers.mapRendezVous(rs));
            }

        } catch (SQLException e) { 
            throw new RuntimeException(e); 
        }

        return out;
    }

    @Override
    public List<RendezVous> findByDossierMedical(Long dossierMedicalId) {
        String sql = "SELECT * FROM RendezVous WHERE dossierMed_id = ? ORDER BY date DESC, time DESC";
        List<RendezVous> out = new ArrayList<>();

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, dossierMedicalId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) out.add(RowMappers.mapRendezVous(rs));
            }

        } catch (SQLException e) { 
            throw new RuntimeException(e); 
        }

        return out;
    }

    @Override
    public List<RendezVous> findByStatus(Status status) {
        String sql = "SELECT * FROM RendezVous WHERE status = ? ORDER BY date, time";
        List<RendezVous> out = new ArrayList<>();

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, status.name());

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) out.add(RowMappers.mapRendezVous(rs));
            }

        } catch (SQLException e) { 
            throw new RuntimeException(e); 
        }

        return out;
    }

    @Override
    public List<RendezVous> findByDateRange(LocalDateTime start, LocalDateTime end) {
        String sql = "SELECT * FROM RendezVous WHERE date >= ? AND date <= ? ORDER BY date, time";
        List<RendezVous> out = new ArrayList<>();

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setTimestamp(1, Timestamp.valueOf(start));
            ps.setTimestamp(2, Timestamp.valueOf(end));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) out.add(RowMappers.mapRendezVous(rs));
            }

        } catch (SQLException e) { 
            throw new RuntimeException(e); 
        }

        return out;
    }

    @Override
    public List<RendezVous> findByConsultation(Long consultationId) {
        String sql = "SELECT * FROM RendezVous WHERE consultation_id = ? ORDER BY date DESC";
        List<RendezVous> out = new ArrayList<>();

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, consultationId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) out.add(RowMappers.mapRendezVous(rs));
            }

        } catch (SQLException e) { 
            throw new RuntimeException(e); 
        }

        return out;
    }

    @Override
    public Optional<RendezVous> findByDateAndTime(LocalDateTime dateTime) {
        String sql = "SELECT * FROM RendezVous WHERE date = ? AND time = ?";

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setTimestamp(1, Timestamp.valueOf(dateTime));
            ps.setTime(2, Time.valueOf(dateTime.toLocalTime()));

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(RowMappers.mapRendezVous(rs));
                return Optional.empty();
            }

        } catch (SQLException e) { 
            throw new RuntimeException(e); 
        }
    }

    @Override
    public long countByStatus(Status status) {
        String sql = "SELECT COUNT(*) FROM RendezVous WHERE status = ?";

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, status.name());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getLong(1);
            }

        } catch (SQLException e) { 
            throw new RuntimeException(e); 
        }

        return 0;
    }

    @Override
    public long countByDate(LocalDate date) {
        String sql = "SELECT COUNT(*) FROM RendezVous WHERE DATE(date) = ?";

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
