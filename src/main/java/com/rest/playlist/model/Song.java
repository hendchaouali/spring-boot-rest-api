package com.rest.playlist.model;

import com.rest.playlist.enums.SongCategory;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Setter
public class Song {

    private static final AtomicInteger count = new AtomicInteger(0);

    private int id;

    @NotBlank(message = "titre ne doit pas être null ou vide")
    @Size(min = 3, max = 50, message = "titre doit être compris entre 3 et 50 caractères")
    private String title;

    @NotBlank(message = "description ne doit pas être nulle ou vide")
    @Size(min = 3, max = 50, message = "description doit être compris entre 3 et 50 caractères")
    private String description;

    @NotNull(message = "categorie<JAZZ, POP, CLASSICAL> ne doit pas être nulle")
    private SongCategory category;

    @NotBlank(message = "duration ne doit pas être nulle ou vide")
    private String duration;

    @NotBlank(message = "artistname ne doit pas être null ou vide")
    private String artistName;

    @Builder
    private Song(String title, String description, SongCategory category, String duration, String artistName) {
        this.id = count.incrementAndGet();
        this.title = title;
        this.description = description;
        this.category = category;
        this.duration = duration;
        this.artistName = artistName;
    }
}
