package ua.poltava.energy.server.dao;

import ua.poltava.energy.server.model.StanZakhodu;
import ua.poltava.energy.server.model.Zakhid;
import ua.poltava.energy.server.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ZakhidDao {

    public List<Zakhid> findAll() throws SQLException {
        List<Zakhid> list = new ArrayList<>();
        String sql = "SELECT * FROM zakhid";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        }
        return list;
    }

    public Zakhid findById(int id) throws SQLException {
        String sql = "SELECT * FROM zakhid WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        }
        return null;
    }

    public void create(Zakhid z) throws SQLException {
        String sql = "INSERT INTO zakhid (enerhoaudyt_id, nazva, vartist, potentsial_ekonomii, termin_okupnosti, potentsial_skorochennia_vykydiv, stan) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, z.getEnerhoaudytId());
            stmt.setString(2, z.getNazva());
            stmt.setDouble(3, z.getVartist());
            stmt.setDouble(4, z.getPotentsialEkonomii());
            stmt.setDouble(5, z.getTerminOkupnosti());
            stmt.setDouble(6, z.getPotentsialSkorochenniaVykydiv());
            stmt.setString(7, z.getStan().name());
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) z.setId(rs.getInt(1));
            }
        }
    }

    public void update(Zakhid z) throws SQLException {
        String sql = "UPDATE zakhid SET enerhoaudyt_id=?, nazva=?, vartist=?, potentsial_ekonomii=?, termin_okupnosti=?, potentsial_skorochennia_vykydiv=?, stan=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, z.getEnerhoaudytId());
            stmt.setString(2, z.getNazva());
            stmt.setDouble(3, z.getVartist());
            stmt.setDouble(4, z.getPotentsialEkonomii());
            stmt.setDouble(5, z.getTerminOkupnosti());
            stmt.setDouble(6, z.getPotentsialSkorochenniaVykydiv());
            stmt.setString(7, z.getStan().name());
            stmt.setInt(8, z.getId());
            stmt.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM zakhid WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    private Zakhid mapRow(ResultSet rs) throws SQLException {
        return new Zakhid(
                rs.getInt("id"),
                rs.getInt("enerhoaudyt_id"),
                rs.getString("nazva"),
                rs.getDouble("vartist"),
                rs.getDouble("potentsial_ekonomii"),
                rs.getDouble("termin_okupnosti"),
                rs.getDouble("potentsial_skorochennia_vykydiv"),
                StanZakhodu.valueOf(rs.getString("stan"))
        );
    }
}