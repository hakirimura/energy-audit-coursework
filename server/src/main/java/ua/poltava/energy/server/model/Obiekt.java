package ua.poltava.energy.server.model;

public class Obiekt {
    private int id;
    private String nazva;
    private String adresa;
    private String typ;
    private double ploshcha;

    public Obiekt() {}

    public Obiekt(int id, String nazva, String adresa, String typ, double ploshcha) {
        this.id = id;
        this.nazva = nazva;
        this.adresa = adresa;
        this.typ = typ;
        this.ploshcha = ploshcha;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNazva() { return nazva; }
    public void setNazva(String nazva) { this.nazva = nazva; }

    public String getAdresa() { return adresa; }
    public void setAdresa(String adresa) { this.adresa = adresa; }

    public String getTyp() { return typ; }
    public void setTyp(String typ) { this.typ = typ; }

    public double getPloshcha() { return ploshcha; }
    public void setPloshcha(double ploshcha) { this.ploshcha = ploshcha; }
}