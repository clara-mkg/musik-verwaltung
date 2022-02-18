
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static javafx.application.Application.launch;

public class Musikverwaltung extends Program{
    Scanner scanner1 = new Scanner(System.in);

    ArrayList<Album> alben = new ArrayList<Album>();

    public Musikverwaltung() {
        erstelleAlben();
    }

    public static void main(String[] args) {
        launch(args);

        Scanner scanner1 = new Scanner(System.in);
        Musikverwaltung MV = new Musikverwaltung();
        Album album = new Album();
        while (true) {
            System.out.println("Was möchtest du tun? 1 = Alben anzeigen, 2 = Album anlegen, 3 = Interpreten anzeigen, ");
            System.out.println("4 = Song suchen, 5 = Eintrag bearbeiten 0 = Programm beenden");
            int a = scanner1.nextInt();
            if (a == 1) {
                MV.albenAnzeigen();
            } else if (a == 2) {
                MV.albenAnlegen();
            } else if (a == 0) {
                System.exit(0);
            } else if (a == 3) {
                MV.interpretenAnzeigen();
            } else if(a == 4){
                MV.songSuchen(scanner1.next());
            }else if(a == 5) {
                MV.eintragBearbeiten();
            }
        }
    }

    public ArrayList getSongs(String search) {
        ArrayList songsVomAlbum = new ArrayList<Song>();
        ArrayList leeresArray = new ArrayList<>();
        for (int i = 0; i < alben.size(); i++) {
            for (int j = 0; j < alben.get(i).songs.size(); j++) {
                if (alben.get(i).songs.get(j).getName().equalsIgnoreCase(search)) {
                    songsVomAlbum.add(alben.get(i).songs.get(j));
                }else{
                    songsVomAlbum.add("kein Song gefunden");
                }
            }
        }
        songsVomAlbum.remove("kein Song gefunden");
        songsVomAlbum.remove("kein Song gefunden");
        songsVomAlbum.remove("kein Song gefunden");
        return songsVomAlbum;
    }

    public void songSuchen(String song){
        System.out.println(getSongs(song));
    }


    public void albumBearbeiten(Album album, String name, Interpret interpret, int erscheinungsjahr, String genre){
        album.setName(name);
        album.setInterpret(interpret);
        album.setErscheinungsjahr(erscheinungsjahr);
        album.setGenre(genre);
    }


    public void eintragBearbeiten(){
        System.out.println("Möchtest du einen Album(drücke die 1), einen Song(drücke die 2) oder einen Interpreten(drücke die 3) bearbeiten?");
        int wasBearbeiten = scanner1.nextInt();
        if(wasBearbeiten == 1){
            for (int i = 0; i < alben.size(); i++) {
                System.out.println(alben.get(i).getName() + " " + i);
            }
            System.out.println("Welches möchtest du bearbeiten. ");
            int albumNummer = scanner1.nextInt();
            while(albumNummer > alben.size() || albumNummer < 0){
                System.out.println("gib eine andere Zahl ein");
            }
            System.out.println(alben.get(albumNummer).getName());
            System.out.println("Möchtest den Namen(drücke 1), den Interpreten(drücke 2), " +
                    "das Erscheinungsjahr(drücke 3) oder das Genre(drücke 4) bearbeiten?");
            int welches = scanner1.nextInt();
            if(welches == 1){
                System.out.println("Zu welchem Namen möchtest du es ändern?");
                String neuerName = scanner1.next();
                alben.get(albumNummer).setName(neuerName);
                System.out.println("dein Album hat jetzt den Namen: " + alben.get(albumNummer).getName());
            }

        }
    }

    public ArrayList<Interpret> getInterpreten(){
        ArrayList<Interpret> interprets = new ArrayList<>();

        for (Album album : alben) {
            if (!interprets.contains(album.getInterpret())) {
                interprets.add(album.getInterpret());
            }
        }
        return interprets;
    }

    public ArrayList<Interpret> getInterpretenFürAuswahl(){
        ArrayList<Interpret> interprets = new ArrayList<>();

        for (Album album : alben) {
            if (!interprets.contains(album.getInterpret())) {
                interprets.add(album.getInterpret());
            }
        }
        Interpret interpret1 = new Interpret("Anderer Interpret");
        interprets.add(interpret1);
        return interprets;
    }


    public String interpretenAnzeigen(){
        ArrayList<Interpret> interprets = getInterpreten();
        String interpretenAusgabe ="";
        for (int i = 0; i < interprets.size(); i++) {
            interpretenAusgabe += i + ". " + alben.get(i).getInterpret().toString()  + ", Alben: ";
            int finalI = i;
            List<Album> gefiltereAlben =  alben.stream().filter((Album album) -> album.getInterpret() == alben.get(finalI).getInterpret()).collect(Collectors.toList());
            for(Album album:gefiltereAlben) {
                interpretenAusgabe += album.getName() + ", ";
            }
            interpretenAusgabe += "\n";
        }
        return interpretenAusgabe;
    }

    public void erstelleAlben() {
        Album album = new Album("a", new Interpret("b"), 5, "c");
        albumHinzufügen(album);
        album.addSong(new Song("test", 130, 1));
        Album album2 = (new Album("d", new Interpret("e"), 5, "f"));
        albumHinzufügen(album2);
        album2.addSong(new Song("test2", 130, 1));
        Interpret interpret1 = new Interpret("e");
        Album album3 = new Album("g", interpret1, 6, "h");
        albumHinzufügen(album3);
        album3.addSong(new Song("test3", 130, 1));
        Album album4 = new Album("w", interpret1, 6, "h");
        albumHinzufügen(album4);
        album4.addSong(new Song("test4", 130, 1));

    }

    public void albenAnzeigen(){
        for (int i = 0; i < alben.size(); i++) {
            System.out.println(alben.get(i).toString());
        }
    }
    public ArrayList<Album> getAlben(){
        return alben;
    }

    public void albenAnlegen(String name, Interpret interpret, int erscheinungsjahr, String genre){

        Album album = new Album(name, interpret, erscheinungsjahr, genre);
        albumHinzufügen(album);
    }

    public void interpretenHinzufügen(String name, String biographie){
        Interpret interpret = new Interpret(name, biographie);
    }


    public void addSong(){

    }

    public void songAnlegen(Album album, String songName, int songSek, int songNummer, String songSongtext){
        String name = songName;
        int sek = songSek;
        int nummer = songNummer;
        String songtext = songSongtext;
        album.addSongMitText(new SongMitText(name, sek, nummer, songtext));

    }
    public void songAnlegenOhneSongtext(Album album, String songName, int songSek, int songNummer){
        String name = songName;
        int sek = songSek;
        int nummer = songNummer;
        album.addSong(new Song(songName, songSek, songNummer));
    }

   /*
        public void songAnlegen(Album album){
        System.out.println("Wie ist der Name des Songs?");
        String songName = scanner1.next();
        System.out.println("Wie lang ist der Song in Sekunden?");
        int songSek = scanner1.nextInt();
        System.out.println("Welche Nummer hat der Song im Album?");
        int songNummer = scanner1.nextInt();
        System.out.println("Möchtest du noch einen Songtext hinzufügen? ja = 1 nein = 0");
        int jaNein = scanner1.nextInt();
        if(jaNein == 1){
            System.out.println("Wie lautet der Songtext?");
            String songtext = scanner1.next();
            album.addSongMitText(new SongMitText(songName, songSek, songNummer, songtext));
        }else if(jaNein == 0){
            album.addSong(new Song(songName, songSek, songNummer));
        }
    }

    */


    public void albumHinzufügen(Album album){
        alben.add(album);
    }


    }