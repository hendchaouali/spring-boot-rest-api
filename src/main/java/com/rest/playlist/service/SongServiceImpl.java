package com.rest.playlist.service;

import com.rest.playlist.enums.SongCategory;
import com.rest.playlist.model.Song;
import com.rest.playlist.exception.AlreadyExistException;
import com.rest.playlist.exception.ResourceNotFoundException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.lang3.EnumUtils;

/**
TODO:
-    private final List<Song> mySongs = Stream.of(
            new Song("The Falls", "Album musical d'Ennio Morricone", SongCategory.CLASSICAL, "7:10", "Morricone"),
            new Song("Oblivion", "Album musical d'Astor Piazzolla", SongCategory.CLASSICAL, "6:05", "Piazzolla"),
            new Song("14 Romances", "Album musical de Sergueï Rachmaninov", SongCategory.CLASSICAL, "7:00", "Rachmaninov"),
            new Song("For The Lover That I Lost", "For the Lover That I Lost (Live At Abbey Road Studios)",SongCategory.POP,  "3:01", "Sam Smith"),
            new Song("Burning", "Burning (Live From The Hackney Round Chapel)", SongCategory.POP,"4:05", "Sam Smith"),
            new Song("I'll Play The Blues For You", "No Surrender", SongCategory.JAZZ, "7:42", "Daniel Castro"),
            new Song("Blues In My Bottle", "Boogie Woogie and Some Blues", SongCategory.JAZZ, "7:03", "Christian Willisohn")
    ).collect(Collectors.toList());   ==> we can put this part of code in another class; example SongDataGenerator to respect "Single Responsability" in SOLID
    
-    
-    getSongsByArtistName: risk to haveNullPointerException     
-    getSongById: risk to haveNullPointerException     
-    createSong: risk to haveNullPointerException  
-    updateSong : risk to haveNullPointerException  
    
    **/

@Service
public class SongServiceImpl implements ISongService {
    private static final Logger log = LoggerFactory.getLogger(SongServiceImpl.class);

    private final List<Song> mySongs = Stream.of(
            new Song("The Falls", "Album musical d'Ennio Morricone", SongCategory.CLASSICAL, "7:10", "Morricone"),
            new Song("Oblivion", "Album musical d'Astor Piazzolla", SongCategory.CLASSICAL, "6:05", "Piazzolla"),
            new Song("14 Romances", "Album musical de Sergueï Rachmaninov", SongCategory.CLASSICAL, "7:00", "Rachmaninov"),
            new Song("For The Lover That I Lost", "For the Lover That I Lost (Live At Abbey Road Studios)",SongCategory.POP,  "3:01", "Sam Smith"),
            new Song("Burning", "Burning (Live From The Hackney Round Chapel)", SongCategory.POP,"4:05", "Sam Smith"),
            new Song("I'll Play The Blues For You", "No Surrender", SongCategory.JAZZ, "7:42", "Daniel Castro"),
            new Song("Blues In My Bottle", "Boogie Woogie and Some Blues", SongCategory.JAZZ, "7:03", "Christian Willisohn")
    ).collect(Collectors.toList());



    @Override
    public List<Song> getAllSongs() {
        return mySongs;
    }

    @Override
    public List<Song> getSongsByCategory(String category) {
        SongCategory songCategory = EnumUtils.getEnumIgnoreCase(SongCategory.class,category);
        if (songCategory == null) {
            throw new ResourceNotFoundException("Not found Category with value = " + category);
        }

        return mySongs.stream().filter(s -> s.getCategory() == songCategory).collect(Collectors.toList());
    }

    @Override
    public List<Song> getSongsByArtistName(String name) {
        return mySongs.stream()
                .filter(s -> s.getArtistName().toUpperCase().contains(name.toUpperCase()))
                .collect(Collectors.toList());
    }

    @Override
    public Song getSongById(int id) {
        return mySongs.stream()
                .filter(p -> id == p.getId())
                .findAny()
                .orElseThrow(() -> new ResourceNotFoundException("Not found Song with id = " + id));
    }

    @Override
    public Song createSong(Song song) {
        Song searchedSong = mySongs.stream()
                .filter(s -> StringUtils.equals(s.getTitle(), song.getTitle()) &&
                            s.getCategory() == song.getCategory()
                )
                .findAny()
                .orElse(null);
        if (searchedSong != null) {
            throw new AlreadyExistException("Song Already Exists.");
        }
        mySongs.add(song);
        return song;
    }

    @Override
    public void updateSong(Song song) {
        Song foundedSong = getSongById(song.getId());
            foundedSong.setTitle(song.getTitle());
            foundedSong.setDescription(song.getDescription());
            foundedSong.setCategory(song.getCategory());
            foundedSong.setDuration(song.getDuration());
            foundedSong.setArtistName(song.getArtistName());
    }

    @Override
    public void deleteSongById(int id) {
        Song foundedSong = getSongById(id);
        mySongs.remove(foundedSong);
    }
}
