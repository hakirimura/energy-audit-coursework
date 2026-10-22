package ua.poltava.energy.client.model;

public class Zakhid {
    private int id;
    private int enerhoaudytId;
    private String nazva;
    private double vartist;
    private double potentsialEkonomii;
    private double terminOkupnosti;
    private double potentsialSkorochenniaVykydiv;
    private StanZakhodu stan;

    public Zakhid() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getEnerhoaudytId() { return enerhoaudytId; }
    public void setEnerhoaudytId(int enerhoaudytId) { this.enerhoaudytId = enerhoaudytId; }

    public String getNazva() { return nazva; }
    public void setNazva(String nazva) { this.nazva = nazva; }

    public double getVartist() { return vartist; }
    public void setVartist(double vartist) { this.vartist = vartist; }

    public double getPotentsialEkonomii() { return potentsialEkonomii; }
    public void setPotentsialEkonomii(double potentsialEkonomii) { this.potentsialEkonomii = potentsialEkonomii; }

    public double getTerminOkupnosti() { return terminOkupnosti; }
    public void setTerminOkupnosti(double terminOkupnosti) { this.terminOkupnosti = terminOkupnosti; }

    public double getPotentsialSkorochenniaVykydiv() { return potentsialSkorochenniaVykydiv; }
    public void setPotentsialSkorochenniaVykydiv(double potentsialSkorochenniaVykydiv) { this.potentsialSkorochenniaVykydiv = potentsialSkorochenniaVykydiv; }

    public StanZakhodu getStan() { return stan; }
    public void setStan(StanZakhodu stan) { this.stan = stan; }

    public double getIntegralnyiPokaznyk() {
        return 0.40 * potentsialEkonomii + 0.35 * (100 - terminOkupnosti) + 0.25 * potentsialSkorochenniaVykydiv;
    }
}