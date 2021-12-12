package com.rest.playlist.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.playlist.enums.SongCategory;
import com.rest.playlist.exception.ResourceNotFoundException;
import com.rest.playlist.model.Song;
import com.rest.playlist.service.ISongService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.concurrent.CopyOnWriteArrayList;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(SongController.class)
public class SongControllerTest {
    private static final Logger log = LoggerFactory.getLogger(SongControllerTest.class);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ISongService songService;
    private Song mySong = Song.builder()
            .title("test Song #1")
            .description("test description Song #1")
            .category(SongCategory.CLASSICAL)
            .duration("3:01")
            .artistName("artist1")
            .build();

    private CopyOnWriteArrayList<Song> songs = new CopyOnWriteArrayList<>(new Song[]{
            Song.builder()
                    .title("test Song #2")
                    .description("test description Song #2")
                    .category(SongCategory.CLASSICAL)
                    .duration("3:03")
                    .artistName("artist2")
                    .build()});

    @Test
    public void testGetSongs() throws Exception {

        given(songService.getAllSongs()).willReturn(songs);
        mockMvc.perform(get("/api/songs")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[*].title").value(songs.get(0).getTitle()))
                .andExpect(jsonPath("$[*].description").value(songs.get(0).getDescription()))
                .andExpect(jsonPath("$[*].category").value(songs.get(0).getCategory().toString()))
                .andExpect(jsonPath("$[*].artistName").value(songs.get(0).getArtistName()))
                .andExpect(jsonPath("$[*].duration").value(songs.get(0).getDuration()));
    }

    @Test
    public void testGetSongsByCategory() throws Exception {
        given(songService.getSongsByCategory(mySong.getCategory().toString())).willReturn(songs);
        mockMvc.perform(get("/api/songs/category/" + mySong.getCategory().toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[*].title").value(songs.get(0).getTitle()))
                .andExpect(jsonPath("$[*].description").value(songs.get(0).getDescription()))
                .andExpect(jsonPath("$[*].category").value(songs.get(0).getCategory().toString()))
                .andExpect(jsonPath("$[*].artistName").value(songs.get(0).getArtistName()))
                .andExpect(jsonPath("$[*].duration").value(songs.get(0).getDuration()));
    }

    @Test
    public void testGetSongsWithNonExistingCategory() throws Exception {
        doThrow(new ResourceNotFoundException("Not found Category with value = popy")).when(songService).getSongsByCategory("popy");
        mockMvc.perform(get("/api/songs/category/popy")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("message").value("Not found Category with value = popy"));
    }

    @Test
    public void testGetNoContentSongsByCategory() throws Exception {
        mockMvc.perform(get("/api/songs/category/POP")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetSongsByArtistName() throws Exception {
        given(songService.getSongsByArtistName(songs.get(0).getArtistName())).willReturn(songs);
        mockMvc.perform(get("/api/songs/artist/" + songs.get(0).getArtistName())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[*].title").value(songs.get(0).getTitle()))
                .andExpect(jsonPath("$[*].description").value(songs.get(0).getDescription()))
                .andExpect(jsonPath("$[*].category").value(songs.get(0).getCategory().toString()))
                .andExpect(jsonPath("$[*].artistName").value(songs.get(0).getArtistName()))
                .andExpect(jsonPath("$[*].duration").value(songs.get(0).getDuration()));
    }

    @Test
    public void testGetNoContentSongsArtistName() throws Exception {
        mockMvc.perform(get("/api/songs/artist/sam")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @Test
    public void testGetSongById() throws Exception {
        given(songService.getSongById(mySong.getId())).willReturn(mySong);
        mockMvc.perform(get("/api/songs/" + mySong.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(mySong.getId()))
                .andExpect(jsonPath("$.title").value(mySong.getTitle()))
                .andExpect(jsonPath("$.description").value(mySong.getDescription()))
                .andExpect(jsonPath("$.category").value(mySong.getCategory().toString()))
                .andExpect(jsonPath("$.artistName").value(mySong.getArtistName()))
                .andExpect(jsonPath("$.duration").value(mySong.getDuration()));

    }

    @Test
    public void testGetSongByNonExistingId() throws Exception {
        doThrow(new ResourceNotFoundException("Not found Song with id = 10000")).when(songService).getSongById(10000);
        mockMvc.perform(get("/api/songs/10000")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("message").value("Not found Song with id = 10000"));
    }

    @Test
    public void testCreateSong() throws Exception {
        when(songService.createSong(mySong)).thenReturn(mySong);
        mockMvc.perform(post("/api/songs")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(mySong)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testCreateSongWithTitleSizeLessThanThree() throws Exception {
        mySong.setTitle("S");
        doThrow(new ResourceNotFoundException("Size: titre doit être compris entre 3 et 50 caractères")).when(songService).createSong(mySong);
        mockMvc.perform(post("/api/songs")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(mySong)))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("fieldErrors[0].message").value("Size: titre doit être compris entre 3 et 50 caractères"));
    }

    @Test
    public void testCreateSongWithDescriptionSizeLessThanThree() throws Exception {
        mySong.setDescription("S");
        doThrow(new ResourceNotFoundException("Size: description doit être compris entre 3 et 50 caractères")).when(songService).createSong(mySong);
        mockMvc.perform(post("/api/songs")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(mySong)))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("fieldErrors[0].message").value("Size: description doit être compris entre 3 et 50 caractères"));
    }

    @Test
    public void testCreateSongWithTitleNull() throws Exception {
        mySong.setTitle(null);
        doThrow(new ResourceNotFoundException("NotBlank: titre ne doit pas être null ou vide")).when(songService).createSong(mySong);
        mockMvc.perform(post("/api/songs")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(mySong)))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("fieldErrors[0].message").value("NotBlank: titre ne doit pas être null ou vide"));
    }

    @Test
    public void testUpdateSong() throws Exception {
        doNothing().when(songService).updateSong(mySong);
        mockMvc.perform(put("/api/songs")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(mySong)))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateSongWithTitleSizeLessThanThree() throws Exception {
        mySong.setTitle("S");
        doThrow(new ResourceNotFoundException("Size: titre doit être compris entre 3 et 50 caractères")).when(songService).updateSong(mySong);
        mockMvc.perform(post("/api/songs")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(mySong)))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("fieldErrors[0].message").value("Size: titre doit être compris entre 3 et 50 caractères"));
    }

    @Test
    public void testUpdateSongWithDescriptionSizeLessThanThree() throws Exception {
        mySong.setDescription("S");
        doThrow(new ResourceNotFoundException("Size: description doit être compris entre 3 et 50 caractères")).when(songService).updateSong(mySong);
        mockMvc.perform(post("/api/songs")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(mySong)))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("fieldErrors[0].message").value("Size: description doit être compris entre 3 et 50 caractères"));
    }

    @Test
    public void testUpdateSongWithTitleNull() throws Exception {
        mySong.setTitle(null);
        doThrow(new ResourceNotFoundException("NotBlank: titre ne doit pas être null ou vide")).when(songService).updateSong(mySong);
        mockMvc.perform(post("/api/songs")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(mySong)))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("fieldErrors[0].message").value("NotBlank: titre ne doit pas être null ou vide"));
    }

    @Test
    public void testDeleteSongById() throws Exception {
        doNothing().when(songService).deleteSongById(mySong.getId());
        mockMvc.perform(delete("/api/songs/" + mySong.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteNotFoundSong() throws Exception {
        doThrow(new ResourceNotFoundException("Not found Song with id = 10000")).when(songService).deleteSongById(10000);
        mockMvc.perform(delete("/api/songs/10000"))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("message").value("Not found Song with id = 10000"));
    }


    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
