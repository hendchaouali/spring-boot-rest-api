package com.rest.playlist.service;

import com.rest.playlist.enums.SongCategory;
import com.rest.playlist.model.Song;
import lombok.Getter;

import java.util.concurrent.CopyOnWriteArrayList;

@Getter
class SongDataGenerator {
    private CopyOnWriteArrayList<Song> data = new CopyOnWriteArrayList<>(new Song[]{
            Song.builder()
                    .title("The Falls")
                    .description("Album musical d'Ennio Morricone")
                    .category(SongCategory.CLASSICAL)
                    .duration("7:10")
                    .artistName("Morricone")
                    .build(),

            Song.builder()
                    .title("Oblivion")
                    .description("Album musical d'Astor Piazzolla")
                    .category(SongCategory.CLASSICAL)
                    .duration("6:05")
                    .artistName("Piazzolla")
                    .build(),

            Song.builder()
                    .title("14 Romances")
                    .description("Album musical de Sergueï Rachmaninov")
                    .category(SongCategory.CLASSICAL)
                    .duration("7:00")
                    .artistName("Rachmaninov")
                    .build(),

            Song.builder()
                    .title("For The Lover That I Lost")
                    .description("Live At Abbey Road Studios")
                    .category(SongCategory.POP)
                    .duration("3:01")
                    .artistName("Sam Smith")
                    .build(),

            Song.builder()
                    .title("Burning")
                    .description("Burning Hackney Round Chapel")
                    .category(SongCategory.POP)
                    .duration("4:05")
                    .artistName("Sam Smith")
                    .build(),

            Song.builder()
                    .title("I'll Play The Blues For You")
                    .description("No Surrender")
                    .category(SongCategory.JAZZ)
                    .duration("7:42")
                    .artistName("Daniel Castro")
                    .build(),


            Song.builder()
                    .title("Blues In My Bottle")
                    .description("Boogie Woogie and Some Blues")
                    .category(SongCategory.JAZZ)
                    .duration("7:03")
                    .artistName("Christian Willisohn")
                    .build()
    });

}
