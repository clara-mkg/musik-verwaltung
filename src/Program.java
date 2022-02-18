import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.util.*;

public class Program extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    ChangeListener changeListener;
    ChangeListener changeListener2;
    ScrollPane scrollPane;
    ScrollPane scrollPane2;
    Musikverwaltung MV;
    ListView listView;
    ListView listView2;
    HBox hBox2;
    Button songsAnlegenButton;
    Album album;
    TextFlow textFlow;
    VBox vBox1;
    VBox vBoxMenü;
    VBox vBoxEintragBearbeiten;
    VBox vBoxRechts;
    BorderPane root;
    Song song;
    boolean neuerInterpret = false;


    @Override
    public void start(Stage primaryStage) {
        listView = new ListView();
        listView.setMinSize(350, 500);
        listView.setMaxSize(500, 500);
        listView2 = new ListView();
        listView2.setMaxSize(350, 500);
        listView2.setMinSize(500, 500);
        scrollPane2 = new ScrollPane();
        scrollPane2.fitToWidthProperty();
        scrollPane2.fitToHeightProperty();
        MV = new Musikverwaltung();
        scrollPane = new ScrollPane();
        scrollPane.fitToWidthProperty();
        scrollPane.fitToHeightProperty();
        scrollPane.setContent(listView);
        root = new BorderPane();
        vBoxMenü = new VBox();
        vBoxEintragBearbeiten = new VBox();
        vBoxRechts = new VBox();
        vBoxRechts.setMinWidth(400);
        root.setRight(vBoxRechts);
        root.setLeft(vBoxMenü);
        hBox2 = new HBox();
        hBox2.setMaxHeight(1000);
        root.setCenter(hBox2);
        vBox1 = new VBox();
        scrollPane.setVisible(false);
        vBox1.setVisible(false);
        vBoxRechts.setVisible(false);
        vBoxEintragBearbeiten.setVisible(false);
        changeListener = new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if(newValue != null) {
                    try {
                        albenBearbeiten((Album) newValue);
                    }catch (ClassCastException exception){
                        interpretenBearbeiten((Interpret) newValue);
                    }
                }
            }
        };
        changeListener2 = new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if(newValue instanceof Album){
                    album = (Album) listView.getSelectionModel().getSelectedItem();
                }
                if (album != null) {
                    try{
                        listViewRechtsFürSongs(album.songs);
                        songsAnlegenButton.setVisible(true);
                        listView2.getSelectionModel().select(0);
                    }catch (ClassCastException exception){
                        System.out.println("nichts");
                    }
                }
            }
        };
        primaryStage.setTitle("Musikverwaltung");
        primaryStage.setScene(new Scene(root, 960, 480));
        primaryStage.show();
        hBox2.getChildren().addAll(scrollPane, vBox1);
        songsAnlegenButton = new Button("Song hinzufügen");
        songsAnlegenButton.setVisible(false);
        songsAnlegenButton.setOnAction(actionEvent -> {
            vBoxEintragBearbeiten.setVisible(false);
            vBoxRechts.setVisible(true);
            vBox1.setVisible(true);
            scrollPane.setVisible(true);
            songsAnlegen(album);
        });
        vBox1.getChildren().addAll(
                scrollPane2,
                songsAnlegenButton
        );
        Button showAlbumViewButton = new Button("Alben anzeigen");
        showAlbumViewButton.setOnAction(actionEvent -> {
            vBox1.setVisible(false);
            scrollPane.setVisible(true);
            vBoxRechts.setVisible(false);
            vBoxEintragBearbeiten.setVisible(false);
            albenAnzeigen(MV.getAlben());
            songsAnzeigen();
        });
        Button showInterpretViewButton = new Button("Interpreten anzeigen");
        showInterpretViewButton.setOnAction(actionEvent -> {
            System.out.println("test");
            scrollPane.setVisible(true);
            vBox1.setVisible(false);
            vBoxRechts.setVisible(false);
            showInterpreten();
        });
        Button createAlbumViewButton = new Button("Album anlegen");
        createAlbumViewButton.setOnAction(actionEvent -> {
            vBoxEintragBearbeiten.setVisible(false);
            vBox1.setVisible(false);
            scrollPane.setVisible(false);
            vBoxRechts.setVisible(true);
            albenAnlegen();
        });
        Button editViewButton = new Button("Eintrag bearbeiten");
        editViewButton.setOnAction(actionEvent -> {
            vBoxEintragBearbeiten.setVisible(true);
            vBoxEintragBearbeiten.getChildren().clear();
            scrollPane.setVisible(true);
            vBox1.setVisible(false);
            vBoxRechts.setVisible(false);
            eintragBearbeiten1();
        });
        vBoxMenü.getChildren().addAll(
                showAlbumViewButton,
                showInterpretViewButton,
                createAlbumViewButton,
                editViewButton);


        HBox hBox = new HBox();
        root.setTop(hBox);
        Button searchSong = new Button("Song suchen");
        TextField textField = new TextField();
        searchSong.setOnAction(actionEvent -> {
            listView.getItems().clear();
            scrollPane.setVisible(true);
            vBox1.setVisible(false);
            vBoxRechts.setVisible(false);
            vBoxEintragBearbeiten.setVisible(false);
            showSongs((textField.getText()));
        });
        hBox.getChildren().addAll(
                textField,
                searchSong);
    }

    public void eintragBearbeiten1(){
        vBoxEintragBearbeiten.setVisible(true);
        scrollPane.setContent(vBoxEintragBearbeiten);
        Button alben = new Button("Alben bearbeiten");
        alben.setOnAction(actionEvent -> {
            albenAnzeigen(MV.getAlben());
            listView.getSelectionModel().selectedItemProperty().addListener(changeListener);

        });
        Button songs = new Button("Songs bearbeiten");
        songs.setOnAction(actionEvent -> {
            albenAnzeigen(MV.getAlben());
            songsAnzeigen();
            listView2.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
                @Override
                public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                    if(newValue != null) {
                        if(newValue instanceof Song){
                            try{
                                song = (Song) newValue;
                                songsBearbeiten((Album) listView.getSelectionModel().getSelectedItem());
                            }catch (ClassCastException e){
                            }
                        }
                    }
                }
            });
            listView.getSelectionModel().select(0);

        });
        Button interpreten = new Button("Interpreten bearbeiten");
        interpreten.setOnAction(actionEvent -> {
            showInterpreten();
            listView.getSelectionModel().selectedItemProperty().addListener(changeListener);
        });
        vBoxEintragBearbeiten.getChildren().addAll(
             alben,
             songs,
             interpreten
        );

    }

    public void interpretenBearbeiten(Interpret interpret){
        vBoxRechts.getChildren().clear();
        vBoxEintragBearbeiten.setVisible(false);
        scrollPane.setVisible(true);
        vBoxRechts.setVisible(true);
        vBox1.setVisible(false);
        Label labelName = new Label("Name");
        TextField nameFeld = new TextField(interpret.getName());
        Label labelBiographie = new Label("Biographie:");
        TextArea biographieFeld = new TextArea(interpret.getBiographie());
        Button änderungSpeichern = new Button("Änderung speichern");
        änderungSpeichern.setOnAction(actionEvent -> {
            interpret.setName(nameFeld.getText());
            interpret.setBiographie(biographieFeld.getText());
            showInterpreten();
        });
        vBoxRechts.getChildren().addAll(
                labelName,
                nameFeld,
                labelBiographie,
                biographieFeld,
                änderungSpeichern
        );
    }

    public void songsBearbeiten(Album albumZugehörig){
        vBoxRechts.getChildren().clear();
        vBoxEintragBearbeiten.setVisible(false);
        scrollPane.setVisible(true);
        vBox1.setVisible(true);
        vBoxRechts.setVisible(true);
        Label lableName = new Label("Name des Songs:");
        TextField songNameFeld = new TextField(song.getName());
        Label lableDauer = new Label("Dauer des Songs (in Sekunden:");
        TextField dauerFeld = new TextField(String.valueOf(song.getSekunden()));
        Label lableNummer = new Label("Nummer des im Album:");
        TextField nummerFeld = new TextField(String.valueOf(song.getNummer()));
        Label lableSongtext = new Label("Songtext (optional):");
        TextField songtextFeld;
        if(song.getClass() == SongMitText.class){
            songtextFeld = new TextField(((SongMitText) song).getSongtest());
        }else{
            songtextFeld = new TextField();
        }
        Button änderungSpeichern = new Button("Änderung speichern");
        änderungSpeichern.setOnAction(actionEvent -> {
            Song song1;
            if(songtextFeld.getText() == null){
                song1 = new Song();
            }
            else{
                song1 = new SongMitText(songtextFeld.getText());
            }
            song1.setName(songNameFeld.getText());
            song1.setSekunden(Integer.parseInt(dauerFeld.getText()));
            song1.setNummer(Integer.parseInt(nummerFeld.getText()));
            albumZugehörig.getSongs().remove(song);
            albumZugehörig.addSong(song1);
        });
        vBoxRechts.getChildren().addAll(
                lableName,
                songNameFeld,
                lableDauer,
                dauerFeld,
                lableNummer,
                nummerFeld,
                lableSongtext,
                songtextFeld,
                änderungSpeichern
        );
    }

    public void albenBearbeiten(Album album){
        vBoxRechts.getChildren().clear();
        vBoxEintragBearbeiten.setVisible(false);
        scrollPane.setVisible(true);
        vBox1.setVisible(false);
        vBoxRechts.setVisible(true);
        Label lableName = new Label("Name des Albums:");
        TextField nameFeld = new TextField(album.getName());
        Label lableErscheinungsjahr = new Label("Erscheinungsjahr:");
        TextField erscheinungsjahrFeld = new TextField(String.valueOf(album.getErscheinungsjahr()));
        Label lableGenre = new Label("Genre:");
        TextField genreFeld = new TextField(album.getGenre());
        Label lableInterpret = new Label("neuer Interpret:");
        Label lableNameInterpret = new Label("Name des Interpreten:");
        TextField nameInterpretFeld = new TextField(album.getInterpret().getName());
        Label lableBiographie = new Label("Biographie:");
        TextArea biographieFeld = new TextArea(album.getInterpret().getBiographie());
        Button interpretenBearbeiten = new Button("Zugeordneten Interpreten bearbeiten");
        interpretenBearbeiten.setOnAction(actionEvent -> {
            interpretenBearbeiten(album.getInterpret());
        });
        Button änderungSpreichernButton = new Button("Änderung speichern");
        änderungSpreichernButton.setOnAction(actionEvent -> {
            String name = nameFeld.getText();
            Interpret interpret = new Interpret(nameInterpretFeld.getText(), biographieFeld.getText());
            int erscheinungsjahr = Integer.parseInt(erscheinungsjahrFeld.getText());
            String genre = genreFeld.getText();
            MV.albumBearbeiten(album, name, interpret, erscheinungsjahr, genre);
            albenAnzeigen(MV.getAlben());});
        vBoxRechts.getChildren().addAll(
                lableName,
                nameFeld,
                lableErscheinungsjahr,
                erscheinungsjahrFeld,
                lableGenre,
                genreFeld,
                lableInterpret,
                lableNameInterpret,
                nameInterpretFeld,
                lableBiographie,
                biographieFeld,
                interpretenBearbeiten,
                änderungSpreichernButton
        );

    }

    public void albenAnzeigen(ArrayList<Album> alben) {
        listView(alben);
    }


    public void songsAnzeigen() {
        listView.getSelectionModel().selectedItemProperty().addListener(changeListener2);
    }

    public void songsAnlegen(Album album) {
        vBoxRechts.setVisible(true);
        vBoxRechts.getChildren().clear();
        vBox1.setVisible(true);
        scrollPane.setVisible(true);
        Label lableName = new Label("Name des Songs:");
        TextField songNameFeld = new TextField();
        Label lableDauer = new Label("Dauer des Songs (in Sekunden:");
        TextField dauerFeld = new TextField();
        Label lableNummer = new Label("Nummer des im Album:");
        TextField nummerFeld = new TextField();
        Label lableSongtext = new Label("Songtext (optional):");
        TextField songtextFeld = new TextField();
        Button hinzufügenButton = new Button("Hinzufügen");
        if(songtextFeld.getText() == null) {
            hinzufügenButton.setOnAction(actionEvent -> {
                String songName = songNameFeld.getText();
                int dauer = Integer.parseInt(dauerFeld.getText());
                int nummer = Integer.parseInt(nummerFeld.getText());
                MV.songAnlegenOhneSongtext(album, songName, dauer, nummer);
                songsAnzeigen();
            });
        }else {
            hinzufügenButton.setOnAction(actionEvent -> {
                String songName = songNameFeld.getText();
                int dauer = Integer.parseInt(dauerFeld.getText());
                int nummer = Integer.parseInt(nummerFeld.getText());
                String songtext = songtextFeld.getText();
                MV.songAnlegen(album, songName, dauer, nummer, songtext);
                songsAnzeigen();
            });
        }

        vBoxRechts.getChildren().addAll(
                lableName,
                songNameFeld,
                lableDauer,
                dauerFeld,
                lableNummer,
                nummerFeld,
                lableSongtext,
                songtextFeld,
                hinzufügenButton
        );

    }

    public void albenAnlegen() {
        vBox1.setVisible(false);
        vBoxRechts.setVisible(true);
        vBoxRechts.getChildren().clear();
        Label lableName = new Label("Name des Albums:");
        TextField nameFeld = new TextField();
        Label lableErscheinungsjahr = new Label("Erscheinungsjahr:");
        TextField erscheinungsjahrFeld = new TextField();
        Label lableGenre = new Label("Genre:");
        TextField genreFeld = new TextField();
        Label lableInterpret = new Label("Interpret:");
        ComboBox<Interpret> comboBoxInterpret = new ComboBox<>();
        comboBoxInterpret.getItems().addAll(MV.getInterpretenFürAuswahl());
        Label lableNameInterpret = new Label("Name des Interpreten:");
        lableNameInterpret.setVisible(false);
        TextField nameInterpretFeld = new TextField();
        nameInterpretFeld.setVisible(false);
        Label lableBiographie = new Label("Biographie:");
        lableBiographie.setVisible(false);
        TextArea biographieFeld = new TextArea();
        biographieFeld.setVisible(false);
        comboBoxInterpret.getSelectionModel().select(0);
        comboBoxInterpret.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if(newValue.toString().equals("Anderer Interpret")){
                    neuerInterpret = true;
                    lableNameInterpret.setVisible(true);
                    nameInterpretFeld.setVisible(true);
                    lableBiographie.setVisible(true);
                    biographieFeld.setVisible(true);
                }else{
                    neuerInterpret = false;
                    lableNameInterpret.setVisible(false);
                    nameInterpretFeld.setVisible(false);
                    lableBiographie.setVisible(false);
                    biographieFeld.setVisible(false);
                }
            }
        });
        Button hinzufügenButton = new Button("Album hinzufügen");
        hinzufügenButton.setOnAction(actionEvent -> {
            String name = nameFeld.getText();
            Interpret interpret;
            if(neuerInterpret){
                interpret = new Interpret(nameInterpretFeld.getText(), biographieFeld.getText());
            }else{
                interpret = comboBoxInterpret.getSelectionModel().getSelectedItem();
            }
            int erscheinungsjahr = Integer.parseInt(erscheinungsjahrFeld.getText());
            String genre = genreFeld.getText();
            MV.albenAnlegen(name, interpret, erscheinungsjahr, genre);
            albenAnzeigen(MV.getAlben());});
        vBoxRechts.getChildren().addAll(
                lableName,
                nameFeld,
                lableErscheinungsjahr,
                erscheinungsjahrFeld,
                lableGenre,
                genreFeld,
                lableInterpret,
                comboBoxInterpret,
                lableNameInterpret,
                nameInterpretFeld,
                lableBiographie,
                biographieFeld,
                hinzufügenButton
        );

    }


    public void showInterpreten() {
        listView(MV.getInterpreten());
    }

    public void showSongs(String songErgebnis) {
        listView(MV.getSongs(songErgebnis));
    }

    private void listView(ArrayList list) {
        scrollPane.setVisible(true);
        listView.getItems().clear();
        listView.getItems().addAll(list);
        scrollPane.setContent(listView);
    }

    private void listViewRechtsFürSongs(ArrayList list) {
        scrollPane.setVisible(true);
        vBox1.setVisible(true);
        listView2.getItems().clear();
        for (int i = 0; i < list.size(); i++) {
            listView2.getItems().add(list.get(i));
        }
        scrollPane2.setContent(listView2);

    }

}
