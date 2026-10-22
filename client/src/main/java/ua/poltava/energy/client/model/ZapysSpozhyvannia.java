package ua.poltava.energy.client.model;

public class ZapysSpozhyvannia {
    private int id;
    private int obiektId;
    private String dataZapysu;
    private double spozhyvanniaKvtHod;
    private double vartistHryvnia;

    public ZapysSpozhyvannia() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getObiektId() { return obiektId; }
    public void setObiektId(int obiektId) { this.obiektId = obiektId; }

    public String getDataZapysu() { return dataZapysu; }
    public void setDataZapysu(String dataZapysu) { this.dataZapysu = dataZapysu; }

    public double getSpozhyvanniaKvtHod() { return spozhyvanniaKvtHod; }
    public void setSpozhyvanniaKvtHod(double spozhyvanniaKvtHod) { this.spozhyvanniaKvtHod = spozhyvanniaKvtHod; }

    public double getVartistHryvnia() { return vartistHryvnia; }
    public void setVartistHryvnia(double vartistHryvnia) { this.vartistHryvnia = vartistHryvnia; }
}