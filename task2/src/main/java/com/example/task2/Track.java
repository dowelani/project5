package com.example.task2;

import javafx.beans.property.*;

public class Track {
    private final IntegerProperty SID = new SimpleIntegerProperty();
    private final IntegerProperty AID = new SimpleIntegerProperty();
    private final IntegerProperty SongNo = new SimpleIntegerProperty();
    private final StringProperty Name = new SimpleStringProperty();
    private final StringProperty Artist = new SimpleStringProperty();

    public Track(int sid, int aid, int sn, String name, String artist ) {
        this.SID.set(sid);
        this.AID.set(aid);
        this.SongNo.set(sn);
        this.Name.set(name);
        this.Artist.set(artist);
    }

    public int getSID() {
        return SID.get();
    }

    public String getName() {
        return Name.get();
    }

    public int getAID() {
        return AID.get();
    }

    public int getSongNo() {
        return SongNo.get();
    }

    public String getArtist() {
        return Artist.get();
    }
}
