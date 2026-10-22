package ua.poltava.energy.server.dao;

import ua.poltava.energy.server.model.ZapysSpozhyvannia;
import ua.poltava.energy.server.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ZapysSpozhyvanniaDao {

    public List<ZapysSpozhyvannia> findAll() throws SQLException {
        List<ZapysSpozhyvannia> list = new ArrayList<>();
        String sql = "SELECT * FROM zapys_spozhyvannia";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        }
        return list;
    }

    public void create(ZapysSpozhyvannia z) throws SQLException {
        String sql = "INSERT INTO zapys_spozhyvannia (obiekt_id, data_zapysu, spozhyvannia_kvt_hod, vartist_hryvnia) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, z.getObiektId());
            stmt.setString(2, z.getDataZapysu());
            stmt.setDouble(3, z.getSpozhyvanniaKvtHod());
            stmt.setDouble(4, z.getVartistHryvnia());
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) z.setId(rs.getInt(1));
            }
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM zapys_spozhyvannia WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    private ZapysSpozhyvannia mapRow(ResultSet rs) throws SQLException {
        return new ZapysSpozhyvannia(
                rs.getInt("id"),
                rs.getInt("obiekt_id"),
                rs.getString("data_zapysu"),
                rs.getDouble("spozhyvannia_kvt_hod"),
                rs.getDouble("vartist_hryvnia")
        );
    }
}