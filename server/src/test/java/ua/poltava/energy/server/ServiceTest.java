package ua.poltava.energy.server;

import org.junit.jupiter.api.Test;
import ua.poltava.energy.server.model.StanZakhodu;
import ua.poltava.energy.server.model.Zakhid;
import ua.poltava.energy.server.service.ZakhidService;

import static org.junit.jupiter.api.Assertions.*;

public class ServiceTest {

    private final ZakhidService service = new ZakhidService();

    @Test
    public void testCalculateIntegral() {
        Zakhid z = new Zakhid();
        z.setPotentsialEkonomii(80);
        z.setTerminOkupnosti(20);
        z.setPotentsialSkorochenniaVykydiv(70);

        double result = service.calculateIntegral(z);
        assertEquals(0.4 * 80 + 0.35 * 80 + 0.25 * 70, result, 0.01);
    }

    @Test
    public void testIntegralNullFactors() {
        Zakhid z = new Zakhid();
        z.setPotentsialEkonomii(0);
        z.setTerminOkupnosti(100);
        z.setPotentsialSkorochenniaVykydiv(0);

        double result = service.calculateIntegral(z);
        assertEquals(0.0, result, 0.01);
    }

    @Test
    public void testChangeStateValid() {
        assertDoesNotThrow(() -> {
            service.changeState(1, StanZakhodu.SKHVALENO);
        });
    }

    @Test
    public void testChangeStateInvalid() {
        Exception exception = assertThrows(Exception.class, () -> {
            service.changeState(1, StanZakhodu.ZAVERSHENO);
        });
        assertTrue(exception.getMessage().contains("Недопустимий перехід"));
    }

    @Test
    public void testCreateZakhidEmptyName() {
        Zakhid z = new Zakhid();
        z.setNazva("");
        Exception exception = assertThrows(Exception.class, () -> {
            service.create(z);
        });
        assertEquals("Назва заходу не може бути порожньою", exception.getMessage());
    }

    @Test
    public void testCreateZakhidNegativeCost() {
        Zakhid z = new Zakhid();
        z.setNazva("Test");
        z.setVartist(-100);
        Exception exception = assertThrows(Exception.class, () -> {
            service.create(z);
        });
        assertEquals("Вартість не може бути від'ємною", exception.getMessage());
    }

    @Test
    public void testCreateZakhidValid() {
        Zakhid z = new Zakhid();
        z.setNazva("Новий захід");
        z.setVartist(1000);
        z.setPotentsialEkonomii(50);
        z.setTerminOkupnosti(10);
        z.setPotentsialSkorochenniaVykydiv(50);
        z.setStan(StanZakhodu.ZAPROPONOVANO);
        z.setEnerhoaudytId(2);

        assertDoesNotThrow(() -> service.create(z));
    }

    @Test
    public void testGetAll() {
        assertDoesNotThrow(() -> service.getAll());
    }

    @Test
    public void testCalculateIntegralLimits() {
        Zakhid z = new Zakhid();
        z.setPotentsialEkonomii(150);
        z.setTerminOkupnosti(-10);
        z.setPotentsialSkorochenniaVykydiv(200);

        double result = service.calculateIntegral(z);
        assertTrue(result >= 0 && result <= 100);
    }

    @Test
    public void testStateEnumValues() {
        assertEquals(4, StanZakhodu.values().length);
    }
}