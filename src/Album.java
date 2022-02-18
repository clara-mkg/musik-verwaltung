import java.util.ArrayList;
import java.util.Scanner;


public class Album {
    private String name;
    private Interpret interpret;
    private int erscheinungsjahr;
    private String genre;

    public Album() {
    }

    public Album(String name, Interpret interpret, int erscheinungsjahr, String genre){
        this.name = name;
        this.interpret = interpret;
        this.erscheinungsjahr = erscheinungsjahr;
        this.genre = genre;
    }

    ArrayList<Song> songs = new ArrayList<Song>();
    Scanner scanner = new Scanner(System.in);

    public ArrayList<Song> getSongs() {
        return songs;
    }

    @Override
    public String toString(){
        return "Name: " + name + ", Interpret: " + getInterpret().getName() +
                ", Erscheinungsjahr: " + erscheinungsjahr + ", Genre: " + genre;
    }

    public String songListe(){
        boolean songGefunden = false;
        String songErgebnis = "";
        for (int j = 0; j < songs.size(); j++) {
            songGefunden = true;
            songErgebnis += songs.get(j).toString() + "\n";
        }
        if(songGefunden == false){
            songErgebnis = "dieses Album enthält keine Songs";
        }
        return songErgebnis;
    }


    public void addSong(Song song){
        songs.add(song);
        sort();
    }

    public void addSongMitText(SongMitText songMitText){
        songs.add(songMitText);
        sort();
    }



    public void sort(){
        songs.sort(new SortSongs());
    }

    public void gesamteDauerString(){
        int gesamtdauer = 0;
        for (Song song:songs
             ) {
             gesamtdauer = gesamtdauer + song.getSekunden();
        }
        System.out.println("Die Gesamtdauert ist:" + gesamtdauer);
    }


    public String getName() {
        //gibt name zurückt
        //@return name
        return name;
    }
    //setzt Namen
    //@param name
    //neuer name

    public void setName(String name) {
        //
        this.name = name;
    }

    public Interpret getInterpret() {
        return interpret;
    }

    public void setInterpret(Interpret interpret) {
        this.interpret = interpret;
    }

    public int getErscheinungsjahr() {
        return erscheinungsjahr;
    }

    public void setErscheinungsjahr(int erscheinungsjahr) {
        this.erscheinungsjahr = erscheinungsjahr;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
