package com.rest.playlist.service;

import com.rest.playlist.enums.SongCategory;
import com.rest.playlist.model.Song;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;


public class SongServiceTest {
    private static final Logger log = LoggerFactory.getLogger(SongServiceImpl.class);


    private SongServiceImpl playlistService;

    private Song mySong = 
            new Song("test Song #1", "test description Song #1", SongCategory.CLASSICAL, "3:01", "test artist #1");

    @Before
    public void setup() {
        playlistService = new SongServiceImpl();
    }

    @Test
    public void getSongs() throws Exception {
        List<Song> songs = playlistService.getAllSongs();
        assertFalse(songs.isEmpty());
        Assertions.assertThat(songs);
        Assertions.assertThat(songs).contains(songs.get(0));
    }


    @Test
    public void testIsCategoryExist() {
        assertEquals(SongCategory.CLASSICAL, mySong.getCategory());
        mySong.setCategory(SongCategory.POP);
        assertEquals(SongCategory.POP, mySong.getCategory());
        mySong.setCategory(SongCategory.JAZZ);
        assertEquals(SongCategory.JAZZ, mySong.getCategory());
    }

    @Test
    public void testGetSongsByCategory() throws Exception {

        List<Song> songs = playlistService.getAllSongsByCategory("CLASSICAL");
        assertFalse(songs.isEmpty());

    }


    @Test
    public void testGetSongsByArtist() throws Exception {

        List<Song> songs = playlistService.getAllSongsByArtist("sam");
        assertFalse(songs.isEmpty());

    }


    @Test
    public void testGetSongById() throws Exception {

        List<Song> songs = playlistService.getAllSongs();
        songs.add(mySong);


        Song foundedSong = playlistService.getSongById(mySong.getId());
        assertThat(foundedSong.getId()).isNotNull();
        assertThat(foundedSong.getCategory().toString()).isEqualTo(mySong.getCategory().toString());
        assertThat(foundedSong.getDescription()).isEqualTo(mySong.getDescription());
        assertThat(foundedSong.getTitle()).isEqualTo(mySong.getTitle());
        assertThat(foundedSong.getCategory()).isEqualTo(mySong.getCategory());
        assertThat(foundedSong.getDuration()).isEqualTo(mySong.getDuration());
        assertThat(foundedSong.getArtistName()).isEqualTo(mySong.getArtistName());

    }

    @Test
    public void testCreateSong() throws Exception {

        Song savedSong = playlistService.createSong(mySong);
        assertThat(savedSong).isNotNull();
        assertThat(savedSong.getId()).isNotNull();
        assertThat(savedSong.getTitle()).isEqualTo(mySong.getTitle());
        assertThat(savedSong.getDescription()).isEqualTo(mySong.getDescription());
        assertThat(savedSong.getCategory()).isEqualTo(mySong.getCategory());
        assertThat(savedSong.getDuration()).isEqualTo(mySong.getDuration());
        assertThat(savedSong.getArtistName()).isEqualTo(mySong.getArtistName());

    }

    @Test
    public void testUpdateSong() throws Exception {

        Song songToUpdate = playlistService.createSong(mySong);
        mySong.setTitle("test Song #2");
        mySong.setDescription("test description Song #2");
        mySong.setCategory(SongCategory.POP);
        mySong.setDuration("5:05");
        mySong.setArtistName("Sam Smith");

        playlistService.updateSong(songToUpdate);

        assertThat(songToUpdate).isNotNull();
        assertThat(songToUpdate.getId()).isNotNull();
        assertThat(songToUpdate.getTitle()).isEqualTo("test Song #2");
        assertThat(songToUpdate.getDescription()).isEqualTo("test description Song #2");
        assertThat(songToUpdate.getCategory()).isEqualTo(SongCategory.POP);
        assertThat(songToUpdate.getDuration()).isEqualTo("5:05");
        assertThat(songToUpdate.getArtistName()).isEqualTo("Sam Smith");

    }

    @Test
    public void testDeleteSongById() throws Exception {

        Song songToDelete = playlistService.createSong(mySong);
        List<Song> playlistList = playlistService.getAllSongs();

        int sizeBeforeDelete = playlistList.size();

        playlistService.deleteSongById(songToDelete.getId());

        assertThat(playlistList.size()).isEqualTo(sizeBeforeDelete - 1);
    }
}
