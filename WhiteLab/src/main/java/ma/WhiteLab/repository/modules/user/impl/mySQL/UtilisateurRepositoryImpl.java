package ma.WhiteLab.repository.modules.user.impl.mySQL;

import ma.WhiteLab.conf.SessionFactory;
import ma.WhiteLab.entities.user.Utilisateur;
import ma.WhiteLab.repository.common.RowMappers;
import ma.WhiteLab.repository.modules.user.api.UtilisateurRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
//auteur : Aymane Akarbich

public abstract class UtilisateurRepositoryImpl<T extends Utilisateur> implements UtilisateurRepository<T> {

    private final Class<T> clazz;

    protected UtilisateurRepositoryImpl(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public List<T> findAll() {
        String sql = "SELECT * FROM Utilisateurs ORDER BY nom, prenom";
        List<T> out = new ArrayList<>();
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                T utilisateur = clazz.getDeclaredConstructor().newInstance();
                out.add(RowMappers.mapUtilisateur(rs, utilisateur));
            }
        } catch (Exception e) { throw new RuntimeException(e); }
        return out;
    }

    @Override
    public T findById(Long id) {
        String sql = "SELECT * FROM Utilisateurs WHERE id = ?";
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    T utilisateur = clazz.getDeclaredConstructor().newInstance();
                    return RowMappers.mapUtilisateur(rs, utilisateur);
                }
                return null;
            }
        } catch (Exception e) { throw new RuntimeException(e); }
    }

    @Override
    public void create(T u) {
        String sql = "INSERT INTO Utilisateurs(nom, prenom, email, adresse, cin, telephone, dateNaissance, sexe, lastLoginDate, motDePasse, dateCreation, creePar) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, u.getNom());
            ps.setString(2, u.getPrenom());
            ps.setString(3, u.getEmail());
            ps.setString(4, u.getAdresse());
            ps.setString(5, u.getCin());
            ps.setString(6, u.getTelephone());
            ps.setDate(7, u.getDateNaissance() != null ? Date.valueOf(u.getDateNaissance()) : null);
            ps.setString(8, u.getSexe() != null ? u.getSexe().name() : null);
            ps.setTimestamp(9, u.getLastLoginDate() != null ? Timestamp.valueOf(u.getLastLoginDate()) : null);
            ps.setString(10, u.getMotDePasse());
            ps.setTimestamp(11, Timestamp.valueOf(u.getDateCreation() != null ? u.getDateCreation() : java.time.LocalDateTime.now()));
            ps.setString(12, u.getCreePar());

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) u.setId(keys.getLong(1));
            }

        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public void update(T u) {
        String sql = "UPDATE Utilisateurs SET nom=?, prenom=?, email=?, adresse=?, cin=?, telephone=?, dateNaissance=?, sexe=?, lastLoginDate=?, motDePasse=?, dateMiseAJour=?, modifierPar=? WHERE id=?";
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, u.getNom());
            ps.setString(2, u.getPrenom());
            ps.setString(3, u.getEmail());
            ps.setString(4, u.getAdresse());
            ps.setString(5, u.getCin());
            ps.setString(6, u.getTelephone());
            ps.setDate(7, u.getDateNaissance() != null ? Date.valueOf(u.getDateNaissance()) : null);
            ps.setString(8, u.getSexe() != null ? u.getSexe().name() : null);
            ps.setTimestamp(9, u.getLastLoginDate() != null ? Timestamp.valueOf(u.getLastLoginDate()) : null);
            ps.setString(10, u.getMotDePasse());
            ps.setTimestamp(11, Timestamp.valueOf(java.time.LocalDateTime.now()));
            ps.setString(12, u.getModifierPar());
            ps.setLong(13, u.getId());

            ps.executeUpdate();

        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public void delete(T u) { if (u != null) deleteById(u.getId()); }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM Utilisateurs WHERE id = ?";
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public Optional<T> findByEmail(String email) {
        String sql = "SELECT * FROM Utilisateurs WHERE email = ?";
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    T u = clazz.getDeclaredConstructor().newInstance();
                    return Optional.of(RowMappers.mapUtilisateur(rs, u));
                }
                return Optional.empty();
            }
        } catch (Exception e) { throw new RuntimeException(e); }
    }

    @Override
    public Optional<T> findByTelephone(String telephone) {
        String sql = "SELECT * FROM Utilisateurs WHERE telephone = ?";
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, telephone);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    T u = clazz.getDeclaredConstructor().newInstance();
                    return Optional.of(RowMappers.mapUtilisateur(rs, u));
                }
                return Optional.empty();
            }
        } catch (Exception e) { throw new RuntimeException(e); }
    }

    @Override
    public boolean existsByEmail(String email) {
        String sql = "SELECT 1 FROM Utilisateurs WHERE email = ?";
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public boolean existsByTelephone(String telephone) {
        String sql = "SELECT 1 FROM Utilisateurs WHERE telephone = ?";
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, telephone);
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public long count() {
        String sql = "SELECT COUNT(*) FROM Utilisateurs";
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            rs.next();
            return rs.getLong(1);
        } catch (SQLException e) { throw new RuntimeException(e); }
    }
}
