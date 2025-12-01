package ma.WhiteLab.repository.modules.planning.impl.mySQL;

import ma.WhiteLab.entities.planning.AgendaMensuel;
import ma.WhiteLab.entities.enums.Mois;
import ma.WhiteLab.conf.SessionFactory;
import ma.WhiteLab.repository.common.RowMappers;
import ma.WhiteLab.repository.modules.planning.api.AgendaMensuelRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AgendaMensuelRepositoryImpl implements AgendaMensuelRepository {

    // -------- CRUD --------
    @Override
    public List<AgendaMensuel> findAll() {
        String sql = "SELECT * FROM AgendaMensuel ORDER BY mois, id";
        List<AgendaMensuel> out = new ArrayList<>();

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                out.add(RowMappers.mapAgendaMensuel(rs));
            }

        } catch (SQLException e) { 
            throw new RuntimeException(e); 
        }

        return out;
    }

    @Override
    public AgendaMensuel findById(Long id) {
        String sql = "SELECT * FROM AgendaMensuel WHERE id = ?";

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return RowMappers.mapAgendaMensuel(rs);
                return null;
            }

        } catch (SQLException e) { 
            throw new RuntimeException(e); 
        }
    }

    @Override
    public void create(AgendaMensuel agenda) {
        String sql = """
            INSERT INTO AgendaMensuel(mois, joursNonDisponible, medecin_id, 
                                    dateCreation, dateMiseAJour, creePar, modifierPar)
            VALUES(?,?,?,NOW(),NOW(),?,?)
        """;

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, agenda.getMois() != null ? agenda.getMois().name() : null);
            
            String joursNonDispo = null;
            if (agenda.getJoursNonDisponible() != null && !agenda.getJoursNonDisponible().isEmpty()) {
                joursNonDispo = String.join(",", agenda.getJoursNonDisponible());
            }
            ps.setString(2, joursNonDispo);
            
            ps.setLong(3, agenda.getMedecin() != null ? agenda.getMedecin().getId() : null);
            ps.setString(4, agenda.getCreePar());
            ps.setString(5, agenda.getModifierPar());

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) agenda.setId(keys.getLong(1));
            }

        } catch (SQLException e) { 
            throw new RuntimeException(e); 
        }
    }

    @Override
    public void update(AgendaMensuel agenda) {
        String sql = """
            UPDATE AgendaMensuel 
            SET mois=?, joursNonDisponible=?, medecin_id=?, 
                dateMiseAJour=NOW(), modifierPar=? 
            WHERE id=?
        """;

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, agenda.getMois() != null ? agenda.getMois().name() : null);
            
            String joursNonDispo = null;
            if (agenda.getJoursNonDisponible() != null && !agenda.getJoursNonDisponible().isEmpty()) {
                joursNonDispo = String.join(",", agenda.getJoursNonDisponible());
            }
            ps.setString(2, joursNonDispo);
            
            ps.setLong(3, agenda.getMedecin() != null ? agenda.getMedecin().getId() : null);
            ps.setString(4, agenda.getModifierPar());
            ps.setLong(5, agenda.getId());

            ps.executeUpdate();

        } catch (SQLException e) { 
            throw new RuntimeException(e); 
        }
    }

    @Override
    public void delete(AgendaMensuel agenda) {
        if (agenda != null) deleteById(agenda.getId());
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM AgendaMensuel WHERE id = ?";

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
    public Optional<AgendaMensuel> findByMedecinAndMois(Long medecinId, Mois mois) {
        String sql = "SELECT * FROM AgendaMensuel WHERE medecin_id = ? AND mois = ?";

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, medecinId);
            ps.setString(2, mois.name());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(RowMappers.mapAgendaMensuel(rs));
                return Optional.empty();
            }

        } catch (SQLException e) { 
            throw new RuntimeException(e); 
        }
    }

    @Override
    public List<AgendaMensuel> findByMedecin(Long medecinId) {
        String sql = "SELECT * FROM AgendaMensuel WHERE medecin_id = ? ORDER BY mois";
        List<AgendaMensuel> out = new ArrayList<>();

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, medecinId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) out.add(RowMappers.mapAgendaMensuel(rs));
            }

        } catch (SQLException e) { 
            throw new RuntimeException(e); 
        }

        return out;
    }

    @Override
    public List<AgendaMensuel> findByMois(Mois mois) {
        String sql = "SELECT * FROM AgendaMensuel WHERE mois = ? ORDER BY id";
        List<AgendaMensuel> out = new ArrayList<>();

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, mois.name());

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) out.add(RowMappers.mapAgendaMensuel(rs));
            }

        } catch (SQLException e) { 
            throw new RuntimeException(e); 
        }

        return out;
    }

    @Override
    public boolean existsByMedecinAndMois(Long medecinId, Mois mois) {
        String sql = "SELECT 1 FROM AgendaMensuel WHERE medecin_id = ? AND mois = ?";

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, medecinId);
            ps.setString(2, mois.name());

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) { 
            throw new RuntimeException(e); 
        }
    }
}
