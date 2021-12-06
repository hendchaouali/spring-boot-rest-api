package com.rest.playlist.model;

import com.rest.playlist.enums.SongCategory;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.concurrent.atomic.AtomicInteger;
/**
TODO: 
    -preferably use emutable models: 
                                    -delete the setters methods
                                    - use private default constructor
                                    
    - use a builder to avoid the complexity of constructors
*/
public class Song {

    private static final AtomicInteger count = new AtomicInteger(0);

    private int id;

    @NotNull(message = "titre ne doit pas être null")
    @Size(min = 3, max = 50, message = "titre doit être compris entre 3 et 50 caractères")
    private String title;

    @NotNull(message = "description ne doit pas être nulle")
    @Size(min = 3, max = 50, message = "description doit être compris entre 3 et 50 caractères")
    private String description;

    @NotNull(message = "categorie<JAZZ, POP, CLASSICAL> ne doit pas être nulle")
    private SongCategory category;

    @NotNull(message = "duration ne doit pas être nulle")
    private String duration;

    @NotNull(message = "artistname ne doit pas être null")
    private String artistName;

    public Song(String title, String description, SongCategory category, String duration, String artistName) {
        this.id = count.incrementAndGet();
        this.title = title;
        this.description = description;
        this.category = category;
        this.duration = duration;
        this.artistName = artistName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SongCategory getCategory() {
        return category;
    }

    public void setCategory(SongCategory category) {
        this.category = category;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }
}
