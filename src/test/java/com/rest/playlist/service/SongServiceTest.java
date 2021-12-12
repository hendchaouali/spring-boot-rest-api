package com.rest.playlist.service;

import com.rest.playlist.enums.SongCategory;
import com.rest.playlist.exception.AlreadyExistException;
import com.rest.playlist.exception.ResourceNotFoundException;
import com.rest.playlist.model.Song;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class SongServiceTest {
    private static final Logger log = LoggerFactory.getLogger(SongServiceImpl.class);


    private SongServiceImpl playlistService;

    private Song mySong = Song.builder()
            .title("test Song #1")
            .description("test description Song #1")
            .category(SongCategory.CLASSICAL)
            .duration("3:01")
            .artistName("test artist1")
            .build();


    @Before
    public void setup() {
        playlistService = new SongServiceImpl();
    }

    @Test
    public void getSongs() {
        List<Song> songs = playlistService.getAllSongs();
        assertFalse(songs.isEmpty());
        assertThat(songs).size().isNotZero();
        assertThat(songs).contains(songs.get(0));
        assertThat(songs.get(0).getId()).isNotNull();
        assertThat(songs.get(0).getTitle()).isNotNull();
        assertThat(songs.get(0).getDescription()).isNotNull();
        assertThat(songs.get(0).getArtistName()).isNotNull();
        assertThat(songs.get(0).getDuration()).isNotNull();
        assertThat(songs.get(0).getCategory()).isNotNull();
    }

    @Test
    public void testGetSongsByCategory() {

        List<Song> songs = playlistService.getSongsByCategory("CLASSICAL");
        assertFalse(songs.isEmpty());
        assertThat(songs.get(0).getId()).isNotNull();
        assertThat(songs.get(0).getTitle()).isNotNull();
        assertThat(songs.get(0).getDescription()).isNotNull();
        assertThat(songs.get(0).getArtistName()).isNotNull();
        assertThat(songs.get(0).getDuration()).isNotNull();
        assertThat(songs.get(0).getCategory()).isNotNull();

    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetSongsWithNonExistCategory() {

        List<Song> songs = playlistService.getSongsByCategory("Popy");
        assertTrue(songs.isEmpty());
    }


    @Test
    public void testGetSongsByArtistName() {

        List<Song> songs = playlistService.getSongsByArtistName("sam");
        assertFalse(songs.isEmpty());

    }


    @Test
    public void testGetSongById() {
        List<Song> songs = playlistService.getAllSongs();
        mySong.setId(1000);
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
    public void testCreateSong() {

        Song savedSong = playlistService.createSong(mySong);
        assertThat(savedSong).isNotNull();
        assertThat(savedSong.getId()).isNotNull();
        assertThat(savedSong.getTitle()).isEqualTo(mySong.getTitle());
        assertThat(savedSong.getDescription()).isEqualTo(mySong.getDescription());
        assertThat(savedSong.getCategory()).isEqualTo(mySong.getCategory());
        assertThat(savedSong.getDuration()).isEqualTo(mySong.getDuration());
        assertThat(savedSong.getArtistName()).isEqualTo(mySong.getArtistName());

    }

    @Test(expected = AlreadyExistException.class)
    public void testCreateExistingSongs() {
        Song savedSong = playlistService.createSong(mySong);
        assertThat(savedSong).isNotNull();
        assertThat(savedSong.getId()).isNotNull();
        assertThat(savedSong.getTitle()).isEqualTo(mySong.getTitle());
        assertThat(savedSong.getDescription()).isEqualTo(mySong.getDescription());
        assertThat(savedSong.getCategory()).isEqualTo(mySong.getCategory());
        assertThat(savedSong.getDuration()).isEqualTo(mySong.getDuration());
        assertThat(savedSong.getArtistName()).isEqualTo(mySong.getArtistName());

        Song existedSong = playlistService.createSong(mySong);
        assertThat(existedSong).isNull();

    }


    @Test
    public void testUpdateSong() {

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
    public void testDeleteSongById() {

        Song songToDelete = playlistService.createSong(mySong);
        List<Song> playlistList = playlistService.getAllSongs();

        int sizeBeforeDelete = playlistList.size();

        playlistService.deleteSongById(songToDelete.getId());

        assertThat(playlistList.size()).isEqualTo(sizeBeforeDelete - 1);
    }
}
