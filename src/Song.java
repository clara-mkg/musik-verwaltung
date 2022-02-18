public class Song {
    private String name;
    private int sekunden;
    private int nummer;

    public Song(String name, int sekunden, int nummer) {
        this.name = name;
        this.sekunden = sekunden;
        this.nummer = nummer;
    }

    public Song() {
        this.name = "";
        this.sekunden = 0;
        this.nummer = 0;
    }

    public String toString(){
        return  nummer + ". Name: " + name + ", Dauer: " + getDauer();
    }

    public String getDauer() {
        int dauer = sekunden / 60;
        int rest = sekunden % 60;
        return dauer + "min " + rest + "sekunden";

    }

    public int getSekunden() {
        return sekunden;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSekunden(int sekunden) {
        this.sekunden = sekunden;
    }

    public int getNummer() {
        return nummer;
    }

    public void setNummer(int nummer) {
        this.nummer = nummer;
    }
}
