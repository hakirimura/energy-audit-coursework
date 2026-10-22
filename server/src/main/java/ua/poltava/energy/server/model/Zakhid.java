package ua.poltava.energy.server.model;

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

    public Zakhid(int id, int enerhoaudytId, String nazva, double vartist, double potentsialEkonomii, double terminOkupnosti, double potentsialSkorochenniaVykydiv, StanZakhodu stan) {
        this.id = id;
        this.enerhoaudytId = enerhoaudytId;
        this.nazva = nazva;
        this.vartist = vartist;
        this.potentsialEkonomii = potentsialEkonomii;
        this.terminOkupnosti = terminOkupnosti;
        this.potentsialSkorochenniaVykydiv = potentsialSkorochenniaVykydiv;
        this.stan = stan;
    }

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
}