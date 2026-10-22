package ua.poltava.energy.server.service;

import ua.poltava.energy.server.dao.ZakhidDao;
import ua.poltava.energy.server.model.StanZakhodu;
import ua.poltava.energy.server.model.Zakhid;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

public class ZakhidService {
    private static final Logger LOG = Logger.getLogger(ZakhidService.class.getName());
    private final ZakhidDao dao = new ZakhidDao();

    public List<Zakhid> getAll() throws SQLException {
        return dao.findAll();
    }

    public double calculateIntegral(Zakhid z) {
        double f1 = Math.max(0, Math.min(100, z.getPotentsialEkonomii()));
        double f2 = Math.max(0, Math.min(100, 100 - z.getTerminOkupnosti()));
        double f3 = Math.max(0, Math.min(100, z.getPotentsialSkorochenniaVykydiv()));
        return 0.40 * f1 + 0.35 * f2 + 0.25 * f3;
    }

    public void changeState(int id, StanZakhodu newState) throws Exception {
        Zakhid z = dao.findById(id);
        if (z == null) {
            throw new Exception("Захід не знайдено");
        }

        StanZakhodu current = z.getStan();
        boolean isValid = false;

        if (current == StanZakhodu.ZAPROPONOVANO && newState == StanZakhodu.SKHVALENO) isValid = true;
        if (current == StanZakhodu.SKHVALENO && newState == StanZakhodu.VPROVADZHENNIA) isValid = true;
        if (current == StanZakhodu.VPROVADZHENNIA && newState == StanZakhodu.ZAVERSHENO) isValid = true;

        if (!isValid) {
            LOG.warning("Спроба недопустимого переходу: " + current + " -> " + newState);
            throw new Exception("Недопустимий перехід стану: " + current + " -> " + newState);
        }

        z.setStan(newState);
        dao.update(z);
        LOG.info("Стан заходу ID=" + id + " змінено на " + newState);
    }

    public void create(Zakhid z) throws Exception {
        if (z.getNazva() == null || z.getNazva().trim().isEmpty()) {
            throw new Exception("Назва заходу не може бути порожньою");
        }
        if (z.getVartist() < 0) {
            throw new Exception("Вартість не може бути від'ємною");
        }
        dao.create(z);
        LOG.info("Створено новий захід: " + z.getNazva());
    }
}