package ma.WhiteLab.repository.modules.patient.impl.mySQL;

import ma.WhiteLab.conf.SessionFactory;
import ma.WhiteLab.entities.patient.Certificat;
import ma.WhiteLab.repository.common.RowMappers;
import ma.WhiteLab.repository.modules.patient.api.CertificatRepository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
//auteur : Aymane Akarbich

public class CertificatRepositoryImpl implements CertificatRepository {

    @Override
    public List<Certificat> findAll() {
        String sql = "SELECT * FROM Certificats ORDER BY dateDebut DESC";
        List<Certificat> out = new ArrayList<>();
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) out.add(RowMappers.mapCertificat(rs));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return out;
    }

    @Override
    public Certificat findById(Long id) {
        String sql = "SELECT * FROM Certificats WHERE id = ?";
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return RowMappers.mapCertificat(rs);
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void create(Certificat c) {
        String sql = """
                INSERT INTO Certificats(dateDebut, dateFin, dureeRepos, contenu, dateCreation, creePar)
                VALUES(?,?,?,?,?,?)
                """;
        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            if (c.getDateDebut() != null) ps.setDate(1, Date.valueOf(c.getDateDebut()));
            else ps.setNull(1, Types.DATE);

            if (c.getDateFin() != null) ps.setDate(2, Date.valueOf(c.getDateFin()));
            else ps.setNull(2, Types.DATE);

            ps.setInt(3, c.getDureeRepos());
            ps.setString(4, c.getContenu());

            ps.setTimestamp(5, Timestamp.valueOf(c.getDateCreation() != null ? c.getDateCreation() : LocalDateTime.now()));
            ps.setString(6, c.getCreePar());

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) c.setId(keys.getLong(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Certificat c) {
        String sql = """
                UPDATE Certificats
                SET dateDebut=?, dateFin=?, dureeRepos=?, contenu=?, dateMiseAJour=?, modifierPar=?
                WHERE id=?
                """;
        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            if (c.getDateDebut() != null) ps.setDate(1, Date.valueOf(c.getDateDebut()));
            else ps.setNull(1, Types.DATE);

            if (c.getDateFin() != null) ps.setDate(2, Date.valueOf(c.getDateFin()));
            else ps.setNull(2, Types.DATE);

            ps.setInt(3, c.getDureeRepos());
            ps.setString(4, c.getContenu());

            ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(6, c.getModifierPar());
            ps.setLong(7, c.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Certificat c) {
        if (c != null) deleteById(c.getId());
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM Certificats WHERE id = ?";
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}