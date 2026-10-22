package ua.poltava.energy.server.model;

public class Enerhoaudyt {
    private int id;
    private int obiektId;
    private String dataProvedennia;
    private String auditor;
    private String vysnovok;

    public Enerhoaudyt() {}

    public Enerhoaudyt(int id, int obiektId, String dataProvedennia, String auditor, String vysnovok) {
        this.id = id;
        this.obiektId = obiektId;
        this.dataProvedennia = dataProvedennia;
        this.auditor = auditor;
        this.vysnovok = vysnovok;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getObiektId() { return obiektId; }
    public void setObiektId(int obiektId) { this.obiektId = obiektId; }

    public String getDataProvedennia() { return dataProvedennia; }
    public void setDataProvedennia(String dataProvedennia) { this.dataProvedennia = dataProvedennia; }

    public String getAuditor() { return auditor; }
    public void setAuditor(String auditor) { this.auditor = auditor; }

    public String getVysnovok() { return vysnovok; }
    public void setVysnovok(String vysnovok) { this.vysnovok = vysnovok; }
}