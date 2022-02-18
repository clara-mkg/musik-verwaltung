public class SongMitText extends Song{
    private String songtest;

    public SongMitText(String name, int sekunden, int nummer, String songtest) {
        super(name, sekunden, nummer);
        this.songtest = songtest;
    }

    public SongMitText(String songtest) {
        this.songtest = songtest;
    }

    public String getSongtest() {
        return songtest;
    }

    public void setSongtest(String songtest) {
        songtest = songtest;
    }
}

