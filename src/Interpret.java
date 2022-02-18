import java.util.ArrayList;

public class Interpret {
    private String name;
    private String biographie = "keine Biographie";

    public Interpret(String name, String biographie) {
        this.name = name;
        this.biographie = biographie;
    }


    public String toString(){
        if(name == "Anderer Interpret"){
            return name;
        }else {
            String interpretenAusgabe = "Name: " + name + ", Biographie: "
                    + biographie;
            return interpretenAusgabe;
        }
    }


    public Interpret(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBiographie() {
        return biographie;
    }

    public void setBiographie(String biographie) {
        this.biographie = biographie;
    }

}
