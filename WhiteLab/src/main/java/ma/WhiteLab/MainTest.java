package ma.WhiteLab;

import ma.WhiteLab.conf.SessionFactory;
import ma.WhiteLab.entities.enums.Assurance;
import ma.WhiteLab.entities.enums.Sexe;
import ma.WhiteLab.entities.patient.Patient;
import ma.WhiteLab.repository.modules.patient.inMemDB_implementation.PatientRepositoryImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class MainTest {
    public static void main(String[] args) {
        String sql = "INSERT INTO Patients(nom, prenom, email, telephone, dateNaissance, sexe, assurance, dateCreation) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, "Salma");
            ps.setString(2, "E.");
            ps.setString(3, "salma@example.com");
            ps.setString(4, "0655-555555");
            ps.setDate(5, java.sql.Date.valueOf(LocalDate.of(1998,3,10)));
            ps.setString(6, Sexe.Femme.name());
            ps.setString(7, Assurance.CNSS.name());
            ps.setTimestamp(8, java.sql.Timestamp.valueOf(LocalDateTime.now()));

            int rows = ps.executeUpdate();
            System.out.println("Patient ajouté : " + rows);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}