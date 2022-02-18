import java.util.Comparator;

public class SortSongs implements Comparator<Song> {
    @Override
    public int compare(Song o1, Song o2) {
        if(o1.getNummer() > o2.getNummer()){
            return 1;
        }else if(o1.getNummer() < o2.getNummer()){
            return -1;
        }else{
            return 0;
        }

    }
}
