package ua.poltava.energy.server.service;

import ua.poltava.energy.server.dao.ZapysSpozhyvanniaDao;
import ua.poltava.energy.server.model.ZapysSpozhyvannia;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

public class ZapysSpozhyvanniaService {
    private static final Logger LOG = Logger.getLogger(ZapysSpozhyvanniaService.class.getName());
    private final ZapysSpozhyvanniaDao dao = new ZapysSpozhyvanniaDao();

    public List<ZapysSpozhyvannia> getAll() throws SQLException {
        return dao.findAll();
    }

    public void create(ZapysSpozhyvannia z) throws Exception {
        if (z.getSpozhyvanniaKvtHod() < 0 || z.getVartistHryvnia() < 0) {
            throw new Exception("Показники споживання не можуть бути від'ємними");
        }
        dao.create(z);
        LOG.info("Додано запис споживання для об'єкта ID=" + z.getObiektId());
    }
}