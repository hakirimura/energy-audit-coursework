package ua.poltava.energy.server.dao;

import ua.poltava.energy.server.model.Enerhoaudyt;
import ua.poltava.energy.server.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnerhoaudytDao {

    public List<Enerhoaudyt> findAll() throws SQLException {
        List<Enerhoaudyt> list = new ArrayList<>();
        String sql = "SELECT * FROM enerhoaudyt";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        }
        return list;
    }

    public void create(Enerhoaudyt e) throws SQLException {
        String sql = "INSERT INTO enerhoaudyt (obiekt_id, data_provedennia, auditor, vysnovok) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, e.getObiektId());
            stmt.setString(2, e.getDataProvedennia());
            stmt.setString(3, e.getAuditor());
            stmt.setString(4, e.getVysnovok());
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) e.setId(rs.getInt(1));
            }
        }
    }

    public void update(Enerhoaudyt e) throws SQLException {
        String sql = "UPDATE enerhoaudyt SET obiekt_id=?, data_provedennia=?, auditor=?, vysnovok=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, e.getObiektId());
            stmt.setString(2, e.getDataProvedennia());
            stmt.setString(3, e.getAuditor());
            stmt.setString(4, e.getVysnovok());
            stmt.setInt(5, e.getId());
            stmt.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM enerhoaudyt WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    private Enerhoaudyt mapRow(ResultSet rs) throws SQLException {
        return new Enerhoaudyt(
                rs.getInt("id"),
                rs.getInt("obiekt_id"),
                rs.getString("data_provedennia"),
                rs.getString("auditor"),
                rs.getString("vysnovok")
        );
    }
}