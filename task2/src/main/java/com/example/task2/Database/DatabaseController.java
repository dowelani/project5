package com.example.task2.Database;

import com.example.task2.Track;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;

public class DatabaseController {
    private Connection con;
    private Statement stmt;
    private boolean isValidConnection;
    public DatabaseController() {isValidConnection=false;con=null;stmt=null;}

    public void connectToDatabase(String username,String password){
    System.out.println("Establishing connection to database...");

    try {
        Class.forName("net.sourceforge.jtds.jdbc.Driver");
    } catch (Exception e) {
        Stage errorMessage=new Stage();
        VBox vb=new VBox();
        vb.setSpacing(5);
        Label error=new Label("Unable to load JDBC driver");
        Button ok=new Button("Ok");
        vb.getChildren().addAll(error,ok);
        errorMessage.setMinWidth(200);
        errorMessage.setTitle("Error");
        errorMessage.setScene(new Scene(vb));
        errorMessage.show();
        ok.setOnAction(event-> errorMessage.close());
        return;
    }

    if (true) {
        try {

            String connectionString = "jdbc:jtds:sqlserver://postsql.mandela.ac.za/WRAP301Music;instance=WRR";

            con = DriverManager.getConnection(connectionString, username, password);

            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            isValidConnection=true;
            System.out.println("Connection to database established ");
        } catch (Exception e) {
            Stage errorMessage=new Stage();
            VBox vb=new VBox();
            vb.setSpacing(5);
            Label error=new Label("Unable to connect to DB...");
            Button ok=new Button("Ok");
            vb.getChildren().addAll(error,ok);
            errorMessage.setMinWidth(200);
            errorMessage.setScene(new Scene(vb));
            errorMessage.setTitle("Error");
            errorMessage.show();
            ok.setOnAction(event-> errorMessage.close());
        }
    } else {
    try {
        SQLServerDataSource ds = new SQLServerDataSource();
        ds.setUser(username);
        ds.setPassword(password);
        ds.setServerName("postsql.mandela.ac.za");
        ds.setInstanceName("WRR");
        ds.setDatabaseName("WRAP301Music");
        con = ds.getConnection();
        stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
    } catch (Exception e) {
        e.printStackTrace();
    }
    }
}

    public boolean isValidConnection() {
        return isValidConnection;
    }

    public void albums(Stage stage){
        try
        {
            String sql="SELECT Album.Title,Album.AID FROM Album";
            ResultSet results=stmt.executeQuery(sql);
            VBox root=new VBox();
            root.setSpacing(5);
            Button menu=new Button("Menu");
            menu.setMinWidth(300);
            menu.setMaxWidth(300);
            menu.setOnAction(event->{ menu();stage.close();});

            Label lbl=new Label("List of buttons with album names as text");
            Label instruction=new Label("Click on button to know more about the album");

            root.getChildren().addAll(menu,lbl,instruction);
            while (results.next())
            {
                String name=results.getString("Title");
                int id=results.getInt("AID");
                if(!name.equals(null)){
                Button current=new Button(name);
                current.setId(Integer.toString(id));
                current.setMinWidth(300);
                current.setMaxWidth(300);
                current.setOnAction(event->{ albumDetails(Integer.parseInt(current.getId()));});
                root.getChildren().add(current);}
            }

            ScrollPane scrollPane=new ScrollPane();
            scrollPane.setContent(root);
            stage.setTitle("Albums");
            stage.setScene(new Scene(scrollPane));
            stage.setMinWidth(300);
            stage.setMaxHeight(800);
            stage.show();
        }
        catch (Exception e)
        {
            System.out.println("unable to query for Songs");
        }
    }
    public void albumDetails(int id){
        try
        {
            Stage stage=new Stage();
            String sql="SELECT * FROM Track WHERE Track.AID='"+id+"'";
            ResultSet results=stmt.executeQuery(sql);
            VBox root=new VBox();
            root.setSpacing(5);
            TableView<Track> table=new TableView();
            table.setMinWidth(300);
            table.setEditable(false);
            TableColumn<Track,Integer> c1=new TableColumn("SID");
            c1.setCellValueFactory(new PropertyValueFactory<>("SID"));
            TableColumn<Track,Integer> c2=new TableColumn("AID");
            c2.setCellValueFactory(new PropertyValueFactory<>("AID"));
            TableColumn<Track,Integer> c3=new TableColumn("SongNo");
            c3.setCellValueFactory(new PropertyValueFactory<>("SongNo"));
            TableColumn<Track,Integer> c4=new TableColumn("Name");
            c4.setCellValueFactory(new PropertyValueFactory<>("Name"));
            TableColumn<Track,Integer> c5=new TableColumn("Artist");
            c5.setCellValueFactory(new PropertyValueFactory<>("Artist"));
            ObservableList<Track> tracks = FXCollections.observableArrayList();
            while (results.next())
            {
                String sid=results.getString("SID");
                String aid=results.getString("AID");
                String songNo=results.getString("SongNo");
                String name=results.getString("Name");
                String artist=results.getString("Artist");
                tracks.add(new Track(Integer.parseInt(sid),Integer.parseInt(aid),Integer.parseInt(songNo),name,artist));
            }
            table.setItems(tracks);
            table.getColumns().addAll(c1,c2,c3,c4,c5);
            Button ok=new Button("Ok");
            root.getChildren().addAll(table,ok);
            stage.setTitle("Album Details");
            stage.setScene(new Scene(root));
            stage.setMinWidth(300);
            stage.setMaxHeight(800);
            stage.show();
            ok.setOnAction(event->{stage.close();});
        }
        catch (Exception e)
        {
            System.out.println("unable to query for Songs");
        }
    }

    public void menu(){
        Stage stage= new Stage();
        VBox root=new VBox();
        root.setSpacing(5);
        Button s=new Button("Search for specific song");
        s.setMinWidth(300);
        s.setMaxWidth(300);
        s.setOnAction(event->{ specificWordSongs();stage.close();});
        root.getChildren().add(s);

        Button a=new Button("Search for specific album");
        a.setMinWidth(300);
        a.setMaxWidth(300);
        a.setOnAction(event->{ specificWordAlbums();stage.close();});
        root.getChildren().add(a);

        Button addA=new Button("Add album");
        addA.setMinWidth(300);
        addA.setMaxWidth(300);
        addA.setOnAction(event->{ createNewAlbum();stage.close();});
        root.getChildren().add(addA);

        Button addS=new Button("Add song");
        addS.setMinWidth(300);
        addS.setMaxWidth(300);
        addS.setOnAction(event->{ createNewSong();stage.close();});
        root.getChildren().add(addS);

        Button edit_album=new Button("Edit album");
        edit_album.setMinWidth(300);
        edit_album.setMaxWidth(300);
        edit_album.setOnAction(event->{ editAlbum();stage.close();});
        root.getChildren().add(edit_album);

        Button edit_song=new Button("Edit song");
        edit_song.setMinWidth(300);
        edit_song.setMaxWidth(300);
        edit_song.setOnAction(event->{ editSong();stage.close();});
        root.getChildren().add(edit_song);

        Button delete_album=new Button("Delete album");
        delete_album.setMinWidth(300);
        delete_album.setMaxWidth(300);
        delete_album.setOnAction(event->{ deleteAlbum();stage.close();});
        root.getChildren().add(delete_album);

        Button delete_song=new Button("Delete song");
        delete_song.setMinWidth(300);
        delete_song.setMaxWidth(300);
        delete_song.setOnAction(event->{ deleteSong();stage.close();});
        root.getChildren().add(delete_song);

        ScrollPane scrollPane=new ScrollPane();
        scrollPane.setContent(root);
        stage.setTitle("Menu");
        stage.setScene(new Scene(scrollPane));
        stage.setMinWidth(300);
        stage.setMaxHeight(800);
        stage.show();
    }

    public void editSong(){
        Stage stage=new Stage();
        HBox hBox=new HBox();
        VBox v=new VBox();
        Label lbl=new Label("Enter SongID of the song that is to be edited");
        TextField textField=new TextField();
        Button button=new Button("Edit");
        hBox.getChildren().addAll(textField,button);
        v.getChildren().addAll(lbl,hBox);
        stage.setScene(new Scene(v));
        stage.setMinWidth(300);
        stage.show();
        StringProperty sid=new SimpleStringProperty("");
        textField.textProperty().bindBidirectional(sid);
        button.setOnAction(event -> {editS(Integer.parseInt(sid.getValue()));stage.close();});
    }
    public void editS(int sid){
        Stage stage=new Stage();
        VBox vBox=new VBox();
        vBox.setSpacing(5);
        vBox.setMaxWidth(300);
        Label id=new Label("AID");
        Label sn=new Label("songNo");
        Label n=new Label("Name");
        Label a=new Label("Artist");
        Label l=new Label("Length");

        TextField textField=new TextField();
        TextField textField2=new TextField();
        TextField textField3=new TextField();
        TextField textField4=new TextField();
        TextField textField5=new TextField();
        Button button=new Button("Edit");
        vBox.getChildren().addAll(id,textField,sn,textField2,n,textField3,a,textField4,l,textField5,button);
        stage.setScene(new Scene(vBox));
        stage.setTitle("Edit Song");
        stage.setMinWidth(300);
        stage.show();
        StringProperty aID=new SimpleStringProperty("");
        StringProperty songNo=new  SimpleStringProperty("");
        StringProperty name=new SimpleStringProperty("");
        StringProperty artist=new  SimpleStringProperty("");
        StringProperty length=new SimpleStringProperty("");
        textField.textProperty().bindBidirectional(aID);
        textField2.textProperty().bindBidirectional(songNo);
        textField3.textProperty().bindBidirectional(name);
        textField4.textProperty().bindBidirectional(artist);
        textField5.textProperty().bindBidirectional(length);
        button.setOnAction(event -> {
            stage.close();
            try
            {
                String stm="UPDATE Track SET AID=?,SongNo=?,Name=?,Artist=?,Length=? WHERE SID="+sid;
                PreparedStatement statement=con.prepareStatement(stm);
                statement.setInt(1,Integer.parseInt(aID.getValue()));
                statement.setInt(2,Integer.parseInt(songNo.getValue()));
                statement.setString(3,name.getValue());
                statement.setString(4,artist.getValue());
                statement.setFloat(5,Float.parseFloat(length.getValue()));
                if(isValidAlbum(Integer.parseInt(aID.getValue())))
                {
                    statement.executeUpdate();
                    Stage Message=new Stage();
                    VBox vb=new VBox();
                    vb.setSpacing(5);
                    Label msg=new Label("Song edited");
                    Button ok=new Button("Ok");
                    vb.getChildren().addAll(msg,ok);
                    Message.setMinWidth(200);
                    Message.setTitle("Message");
                    Message.setScene(new Scene(vb));
                    Message.show();
                    ok.setOnAction(event1-> {Message.close();menu();});
                }

            }
            catch (Exception e)
            {
                Stage Message=new Stage();
                VBox vb=new VBox();
                vb.setSpacing(5);
                Label msg=new Label("Song not edited");
                Button ok=new Button("Ok");
                vb.getChildren().addAll(msg,ok);
                Message.setMinWidth(200);
                Message.setTitle("Message");
                Message.setScene(new Scene(vb));
                Message.show();
                ok.setOnAction(event2-> {Message.close();menu();});
            }
        });
    }

    public void editAlbum(){
        Stage stage=new Stage();
        HBox hBox=new HBox();
        VBox v=new VBox();
        Label lbl=new Label("Enter AlbumID of the Album that is to be edited");
        TextField textField=new TextField();
        Button button=new Button("Edit");
        hBox.getChildren().addAll(textField,button);
        v.getChildren().addAll(lbl,hBox);
        stage.setScene(new Scene(v));
        stage.setMinWidth(300);
        stage.show();
        StringProperty aid=new SimpleStringProperty("");
        textField.textProperty().bindBidirectional(aid);
        button.setOnAction(event -> {editA(Integer.parseInt(aid.getValue()));stage.close();});
    }
    public void editA(int aid){
        Stage stage=new Stage();
        VBox vBox=new VBox();
        vBox.setMaxWidth(300);
        vBox.setSpacing(5);
        Label t=new Label("Title");
        Label y=new Label("Year");
        TextField textField=new TextField();
        TextField textField2=new TextField();
        Button button=new Button("Edit");
        vBox.getChildren().addAll(t,textField,y,textField2,button);
        stage.setScene(new Scene(vBox));
        stage.setTitle("Edit Album");
        stage.setMinWidth(300);
        stage.show();
        StringProperty title=new SimpleStringProperty("");
        StringProperty year=new  SimpleStringProperty("");
        textField.textProperty().bindBidirectional(title);
        textField2.textProperty().bindBidirectional(year);
        button.setOnAction(event -> {
            stage.close();
            try
            {
                String stm="UPDATE Album SET Title=?,Year=? WHERE AID="+aid;
                PreparedStatement statement=con.prepareStatement(stm);
                statement.setString(1,title.getValue());
                statement.setInt(2,Integer.parseInt(year.getValue()));
                statement.executeUpdate();
                Stage Message=new Stage();
                VBox vb=new VBox();
                vb.setSpacing(5);
                Label msg=new Label("Album edited");
                Button ok=new Button("Ok");
                vb.getChildren().addAll(msg,ok);
                Message.setMinWidth(200);
                Message.setTitle("Message");
                Message.setScene(new Scene(vb));
                Message.show();
                ok.setOnAction(event1-> {Message.close();menu();});
            }
            catch (Exception e)
            {
                Stage Message=new Stage();
                VBox vb=new VBox();
                vb.setSpacing(5);
                Label msg=new Label("Album not edited");
                Button ok=new Button("Ok");
                vb.getChildren().addAll(msg,ok);
                Message.setMinWidth(200);
                Message.setTitle("Message");
                Message.setScene(new Scene(vb));
                Message.show();
                ok.setOnAction(event2-> {Message.close();menu();});
            }
        });
    }

    public void deleteSong(){
        Stage stage=new Stage();
        HBox hBox=new HBox();
        VBox v=new VBox();
        Label lbl=new Label("Enter SongID of the song that is to be deleted");
        TextField textField=new TextField();
        Button button=new Button("Delete");
        hBox.getChildren().addAll(textField,button);
        v.getChildren().addAll(lbl,hBox);
        stage.setScene(new Scene(v));
        stage.setMinWidth(300);
        stage.show();
        StringProperty sid=new SimpleStringProperty("");
        textField.textProperty().bindBidirectional(sid);
        button.setOnAction(event -> {deleteS(Integer.parseInt(sid.getValue()));stage.close();});
    }
    public void deleteS(int sid){
        Stage stage=new Stage();
        VBox v=new VBox();
        Label lbl=new Label("Are you sure you wish to delete the song");
        HBox h=new HBox();
        Button button=new Button("No");
        Button button1=new Button("Yes");
        h.getChildren().addAll(button,button1);
        h.setSpacing(150);
        v.getChildren().addAll(lbl,h);
        stage.setScene(new Scene(v));
        stage.setMinWidth(300);
        stage.show();
        button1.setOnAction(event -> {
            stage.close();
            try{
                String query="DELETE FROM Track WHERE SID="+sid;
                stmt.executeUpdate(query);
                Stage Message=new Stage();
                VBox vb=new VBox();
                vb.setSpacing(5);
                Label msg=new Label("Song deleted");
                Button ok=new Button("Ok");
                vb.getChildren().addAll(msg,ok);
                Message.setMinWidth(200);
                Message.setTitle("Message");
                Message.setScene(new Scene(vb));
                Message.show();
                ok.setOnAction(event1-> {Message.close();menu();});
            }catch (Exception e){
                Stage Message=new Stage();
                VBox vb=new VBox();
                vb.setSpacing(5);
                Label msg=new Label("Song not deleted");
                Button ok=new Button("Ok");
                vb.getChildren().addAll(msg,ok);
                Message.setMinWidth(200);
                Message.setTitle("Message");
                Message.setScene(new Scene(vb));
                Message.show();
                ok.setOnAction(event1-> {Message.close();menu();});
            }
        });
        button.setOnAction(event -> {
            stage.close();menu();
        });
    }

    public void deleteAlbum(){
        Stage stage=new Stage();
        HBox hBox=new HBox();
        VBox v=new VBox();
        Label lbl=new Label("Enter AlbumID of the Album that is to be deleted");
        TextField textField=new TextField();
        Button button=new Button("Delete");
        hBox.getChildren().addAll(textField,button);
        v.getChildren().addAll(lbl,hBox);
        stage.setScene(new Scene(v));
        stage.setMinWidth(300);
        stage.show();
        StringProperty aid=new SimpleStringProperty("");
        textField.textProperty().bindBidirectional(aid);
        button.setOnAction(event -> {deleteA(Integer.parseInt(aid.getValue()));stage.close();});
    }
    public void deleteA(int aid){
        Stage stage=new Stage();
        VBox v=new VBox();
        Label lbl=new Label("Are you sure you wish to delete the album");
        HBox h=new HBox();
        Button button=new Button("No");
        Button button1=new Button("Yes");
        h.getChildren().addAll(button,button1);
        h.setSpacing(150);
        v.getChildren().addAll(lbl,h);
        stage.setScene(new Scene(v));
        stage.setMinWidth(300);
        stage.show();
        button.setOnAction(event -> {
            stage.close();menu();
        });
        button1.setOnAction(event -> {
        stage.close();
        try{
            String query="DELETE FROM Album WHERE AID="+aid;
            if(isValidAlbum(aid)){
            stmt.executeUpdate(query);
            deleteAlbumSongs(aid);
            Stage Message=new Stage();
            VBox vb=new VBox();
            vb.setSpacing(5);
            Label msg=new Label("Album deleted");
            Button ok=new Button("Ok");
            vb.getChildren().addAll(msg,ok);
            Message.setMinWidth(200);
            Message.setTitle("Message");
            Message.setScene(new Scene(vb));
            Message.show();
            ok.setOnAction(event1-> {Message.close();menu();});}
        }catch (Exception e){
            Stage Message=new Stage();
            VBox vb=new VBox();
            vb.setSpacing(5);
            Label msg=new Label("Album not deleted");
            Button ok=new Button("Ok");
            vb.getChildren().addAll(msg,ok);
            Message.setMinWidth(200);
            Message.setTitle("Message");
            Message.setScene(new Scene(vb));
            Message.show();
            ok.setOnAction(event1-> {Message.close();menu();});
        }
    });
    }
    public  void deleteAlbumSongs(int aid){
        try{
            String query="DELETE FROM Track WHERE SID="+aid;
            stmt.executeUpdate(query);}catch (Exception e){e.printStackTrace();}
    }

    public void createNewAlbum() {
        Stage stage=new Stage();
        VBox vBox=new VBox();
        vBox.setMaxWidth(300);
        vBox.setSpacing(5);
        Label t=new Label("Title");
        Label y=new Label("Year");
        TextField textField=new TextField();
        TextField textField2=new TextField();
        Button button=new Button("Insert");
        vBox.getChildren().addAll(t,textField,y,textField2,button);
        stage.setScene(new Scene(vBox));
        stage.setTitle("Insert Album");
        stage.setMinWidth(300);
        stage.show();
        StringProperty title=new SimpleStringProperty("");
        StringProperty year=new  SimpleStringProperty("");
        textField.textProperty().bindBidirectional(title);
        textField2.textProperty().bindBidirectional(year);
        button.setOnAction(actionEvent ->{
            stage.close();
        try
        {

            PreparedStatement insertStatement=con.prepareStatement("INSERT INTO Album VALUES(?,?)");
            insertStatement.setString(1,title.getValue());
            insertStatement.setInt(2,Integer.parseInt(year.getValue()));
            insertStatement.executeUpdate();
            Stage Message=new Stage();
            VBox vb=new VBox();
            vb.setSpacing(5);
            Label msg=new Label("Album added");
            Button ok=new Button("Ok");
            vb.getChildren().addAll(msg,ok);
            Message.setMinWidth(200);
            Message.setTitle("Message");
            Message.setScene(new Scene(vb));
            Message.show();
            ok.setOnAction(event-> {Message.close();menu();});
        }
        catch (Exception e)
        {
            Stage Message=new Stage();
            VBox vb=new VBox();
            vb.setSpacing(5);
            Label msg=new Label("Album not added");
            Button ok=new Button("Ok");
            vb.getChildren().addAll(msg,ok);
            Message.setMinWidth(200);
            Message.setTitle("Message");
            Message.setScene(new Scene(vb));
            Message.show();
            ok.setOnAction(event-> {Message.close();menu();});
        }
        });
    }

    public void createNewSong() {
        Stage stage=new Stage();
        VBox vBox=new VBox();
        vBox.setSpacing(5);
        vBox.setMaxWidth(300);
        Label id=new Label("AID");
        Label sn=new Label("songNo");
        Label n=new Label("Name");
        Label a=new Label("Artist");
        Label l=new Label("Length");

        TextField textField=new TextField();
        TextField textField2=new TextField();
        TextField textField3=new TextField();
        TextField textField4=new TextField();
        TextField textField5=new TextField();
        Button button=new Button("Insert");
        vBox.getChildren().addAll(id,textField,sn,textField2,n,textField3,a,textField4,l,textField5,button);
        stage.setScene(new Scene(vBox));
        stage.setTitle("Insert Song");
        stage.setMinWidth(300);
        stage.show();
        StringProperty aID=new SimpleStringProperty("");
        StringProperty songNo=new  SimpleStringProperty("");
        StringProperty name=new SimpleStringProperty("");
        StringProperty artist=new  SimpleStringProperty("");
        StringProperty length=new SimpleStringProperty("");
        textField.textProperty().bindBidirectional(aID);
        textField2.textProperty().bindBidirectional(songNo);
        textField3.textProperty().bindBidirectional(name);
        textField4.textProperty().bindBidirectional(artist);
        textField5.textProperty().bindBidirectional(length);
        button.setOnAction(actionEvent ->{
            stage.close();
        try
        {

            PreparedStatement insertStatement=con.prepareStatement("INSERT INTO Track(AID,SongNo,Name,Artist,Length) VALUES(?,?,?,?,?)");
            insertStatement.setInt(1,Integer.parseInt(aID.getValue()));
            insertStatement.setInt(2,Integer.parseInt(songNo.getValue()));
            insertStatement.setString(3,name.getValue());
            insertStatement.setString(4,artist.getValue());
            insertStatement.setFloat(5,Float.parseFloat(length.getValue()));
            if(isValidAlbum(Integer.parseInt(aID.getValue())))
            {
                insertStatement.executeUpdate();
                Stage Message=new Stage();
                VBox vb=new VBox();
                vb.setSpacing(5);
                Label msg=new Label("Song added");
                Button ok=new Button("Ok");
                vb.getChildren().addAll(msg,ok);
                Message.setMinWidth(200);
                Message.setTitle("Message");
                Message.setScene(new Scene(vb));
                Message.show();
                ok.setOnAction(event-> {Message.close();menu();});
            }

        }
        catch (Exception e)
        {
            Stage Message=new Stage();
            VBox vb=new VBox();
            vb.setSpacing(5);
            Label msg=new Label("Song not added");
            Button ok=new Button("Ok");
            vb.getChildren().addAll(msg,ok);
            Message.setMinWidth(200);
            Message.setTitle("Message");
            Message.setScene(new Scene(vb));
            Message.show();
            ok.setOnAction(event-> {Message.close();menu();});
        }
        });
    }

    private boolean isValidAlbum(int aID) throws SQLException {
        boolean exists=false;
        String sql="SELECT AID FROM Album WHERE AID="+aID;
        ResultSet results=stmt.executeQuery(sql);
        if (results.next()){
            exists=true;
        }
        return exists;
    }

    public void specificWordSongs() {

            Stage stage=new Stage();
            HBox hBox=new HBox();
            TextField textField=new TextField();
            Button button=new Button("⌕");
            hBox.getChildren().addAll(textField,button);
            stage.setScene(new Scene(hBox));
            stage.setTitle("Search");
            stage.setMinWidth(300);
            stage.show();
        StringProperty search=new SimpleStringProperty("");
        textField.textProperty().bindBidirectional(search);
            button.setOnAction(actionEvent ->{
                try
                {

                String word=search.getValue();
                VBox vBox=new VBox();
                vBox.setSpacing(5);
                    Button menu=new Button("Menu");
                    menu.setMinWidth(300);
                    menu.setMaxWidth(300);
                    menu.setOnAction(event->{ menu();stage.close();});
                    vBox.getChildren().add(menu);
                String sql="SELECT * FROM Track WHERE Track.Name LIKE '%"+word+"%'";
                ResultSet results=stmt.executeQuery(sql);

                    TableView<Track> table=new TableView();
                    table.setMinWidth(300);
                    table.setEditable(false);
                    TableColumn<Track,Integer> c1=new TableColumn("SID");
                    c1.setCellValueFactory(new PropertyValueFactory<>("SID"));
                    TableColumn<Track,Integer> c2=new TableColumn("AID");
                    c2.setCellValueFactory(new PropertyValueFactory<>("AID"));
                    TableColumn<Track,Integer> c3=new TableColumn("SongNo");
                    c3.setCellValueFactory(new PropertyValueFactory<>("SongNo"));
                    TableColumn<Track,Integer> c4=new TableColumn("Name");
                    c4.setCellValueFactory(new PropertyValueFactory<>("Name"));
                    TableColumn<Track,Integer> c5=new TableColumn("Artist");
                    c5.setCellValueFactory(new PropertyValueFactory<>("Artist"));
                    ObservableList<Track> tracks = FXCollections.observableArrayList();
                    Label label=new Label("albums with "+word+" in their titles:");
                    vBox.getChildren().add(label);
                while (results.next())
                {
                    String sid=results.getString("SID");
                    String aid=results.getString("AID");
                    String songNo=results.getString("SongNo");
                    String name=results.getString("Name");
                    String artist=results.getString("Artist");
                    tracks.add(new Track(Integer.parseInt(sid),Integer.parseInt(aid),Integer.parseInt(songNo),name,artist));
                }
                    table.setItems(tracks);
                    table.getColumns().addAll(c1,c2,c3,c4,c5);
                    Button ok=new Button("Ok");
                    vBox.getChildren().addAll(table,ok);
                    ScrollPane scrollPane=new ScrollPane();
                    scrollPane.setContent(vBox);
                    stage.setTitle("Songs");
                    stage.setScene(new Scene(scrollPane));
                    stage.setMinWidth(300);
                    stage.setMaxHeight(800);
                    stage.show();
                }
                    catch (Exception e)
                    {
                        System.out.println("unable to query for Songs");
                    }
            });

    }

    public void specificWordAlbums() {
        Stage stage=new Stage();
        HBox hBox=new HBox();
        TextField textField=new TextField();
        Button button=new Button("⌕");
        hBox.getChildren().addAll(textField,button);
        stage.setScene(new Scene(hBox));
        stage.setTitle("Search");
        stage.setMinWidth(300);
        stage.show();StringProperty search=new SimpleStringProperty("");
        textField.textProperty().bindBidirectional(search);
        button.setOnAction(actionEvent ->{
        try
        {

            String word=search.getValue();
            VBox vBox=new VBox();
            vBox.setSpacing(5);
            Button menu=new Button("Menu");
            menu.setMinWidth(300);
            menu.setMaxWidth(300);
            menu.setOnAction(event->{ menu();stage.close();});
            vBox.getChildren().add(menu);
            String sqlStatement="SELECT * FROM Album WHERE Album.Title LIKE '%"+word+"%'";
            ResultSet results=stmt.executeQuery(sqlStatement);
            Label label=new Label("albums with "+word+" in their titles:");
            vBox.getChildren().add(label);
            while (results.next())
            {
                String name=results.getString("Title");
                int id=results.getInt("AID");
                if(!name.equals(null)){
                    Button current=new Button(name);
                    current.setId(Integer.toString(id));
                    current.setMinWidth(300);
                    current.setMaxWidth(300);
                    current.setOnAction(event->{ albumDetails(Integer.parseInt(current.getId()));});
                    vBox.getChildren().add(current);}
            }
            ScrollPane scrollPane=new ScrollPane();
            scrollPane.setContent(vBox);
            stage.setTitle("Albums");
            stage.setScene(new Scene(scrollPane));
            stage.setMinWidth(300);
            stage.setMaxHeight(800);
            stage.show();
        }
        catch (Exception e)
        {
            System.out.println("unable to query for albums");
        }
        });
    }

    public void disconnectDB() {
        System.out.println("Disconnecting from database...");

        try {
            con.close();
            System.out.println("Disconnected from database...");
        } catch (Exception ex) {
            System.out.println("   Unable to disconnect from database");
        }
    }

}
