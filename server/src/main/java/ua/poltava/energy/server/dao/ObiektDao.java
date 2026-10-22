package ua.poltava.energy.server.dao;

import ua.poltava.energy.server.model.Obiekt;
import ua.poltava.energy.server.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ObiektDao {

    public List<Obiekt> findAll() throws SQLException {
        List<Obiekt> list = new ArrayList<>();
        String sql = "SELECT * FROM obiekt";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        }
        return list;
    }

    public Obiekt findById(int id) throws SQLException {
        String sql = "SELECT * FROM obiekt WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }
        return null;
    }

    public void create(Obiekt o) throws SQLException {
        String sql = "INSERT INTO obiekt (nazva, adresa, typ, ploshcha) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, o.getNazva());
            stmt.setString(2, o.getAdresa());
            stmt.setString(3, o.getTyp());
            stmt.setDouble(4, o.getPloshcha());
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) o.setId(rs.getInt(1));
            }
        }
    }

    public void update(Obiekt o) throws SQLException {
        String sql = "UPDATE obiekt SET nazva=?, adresa=?, typ=?, ploshcha=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, o.getNazva());
            stmt.setString(2, o.getAdresa());
            stmt.setString(3, o.getTyp());
            stmt.setDouble(4, o.getPloshcha());
            stmt.setInt(5, o.getId());
            stmt.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM obiekt WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    private Obiekt mapRow(ResultSet rs) throws SQLException {
        return new Obiekt(
                rs.getInt("id"),
                rs.getString("nazva"),
                rs.getString("adresa"),
                rs.getString("typ"),
                rs.getDouble("ploshcha")
        );
    }
}