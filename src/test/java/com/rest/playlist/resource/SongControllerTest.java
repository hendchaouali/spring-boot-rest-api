package com.rest.playlist.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.playlist.enums.SongCategory;
import com.rest.playlist.model.Song;
import com.rest.playlist.service.ISongService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(SongController.class)
public class SongControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ISongService playlistService;
    private Song mySong = new Song("test Song #1", "test description Song #1", SongCategory.CLASSICAL, "3:01", "test artist #1");
    private List<Song> songs = Stream.of(
            new Song("test Song #2", "test description Song #2", SongCategory.CLASSICAL, "3:03", "test artist #2")
    ).collect(Collectors.toList());

    @Test
    public void testGetSongs() throws Exception {

        given(playlistService.getAllSongs()).willReturn(songs);
        mockMvc.perform(get("/api/songs")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAllSongsByCategory() throws Exception {
        given(playlistService.getAllSongsByCategory(mySong.getCategory().toString())).willReturn(songs);
        mockMvc.perform(get("/api/songs/category/" + mySong.getCategory().toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAllSongsByArtist() throws Exception {
        given(playlistService.getAllSongsByArtist("sam")).willReturn(songs);
        mockMvc.perform(get("/api/songs/artist/sam")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetSongById() throws Exception {
        given(playlistService.getSongById(mySong.getId())).willReturn(mySong);
        mockMvc.perform(get("/api/songs/" + mySong.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateSong() throws Exception {
        given(playlistService.createSong(mySong)).willReturn(mySong);
        mockMvc.perform(post("/api/songs")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(mySong)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testUpdateSong() throws Exception {
        doNothing().when(playlistService).updateSong(mySong);
        mockMvc.perform(put("/api/songs")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(mySong)))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteSongById() throws Exception {
        doNothing().when(playlistService).deleteSongById(mySong.getId());
        mockMvc.perform(delete("/api/songs/", mySong.getId()))
                .andExpect(status().isOk());
    }


    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
