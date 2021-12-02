package com.rest.playlist.service;

import com.rest.playlist.model.Song;

import java.util.List;

public interface ISongService {

    List<Song> getAllSongs();
    List<Song> getSongsByCategory(String category);
    List<Song> getSongsByArtistName(String artistName);
    Song getSongById(int id);
    Song createSong(Song playlist);
    void updateSong(Song playlist);
    void deleteSongById(int id);
}
