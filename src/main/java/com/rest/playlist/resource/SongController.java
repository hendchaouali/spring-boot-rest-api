package com.rest.playlist.resource;

import com.rest.playlist.model.Song;
import com.rest.playlist.service.ISongService;
import com.rest.playlist.service.SongServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/songs")
public class SongController {

    final private ISongService ISongService;
    private static final Logger log = LoggerFactory.getLogger(SongServiceImpl.class);

    public SongController(ISongService ISongService) {
        this.ISongService = ISongService;
    }

    @GetMapping
    public ResponseEntity<List<Song>> getAllSongs() {

        return new ResponseEntity<>(ISongService.getAllSongs(), HttpStatus.OK);
    }


    @GetMapping("/category/{category}")
    public ResponseEntity<List<Song>> getAllSongsByCategory(@PathVariable String category) {
        List<Song> songs = ISongService.getAllSongsByCategory(category);
        return new ResponseEntity<>(songs, HttpStatus.OK);
    }


    @GetMapping("/artist/{artistName}")
    public ResponseEntity<List<Song>> getAllSongsByArtist(@PathVariable String artistName) {
        List<Song> songs = ISongService.getAllSongsByArtist(artistName);
        return new ResponseEntity<>(songs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Song> getSongById(@PathVariable int id) {
        Song song = ISongService.getSongById(id);
        return new ResponseEntity<>(song, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Song> createSong(@Valid @RequestBody Song song) {
        Song addedSong = ISongService.createSong(song);
        return new ResponseEntity<>(addedSong, HttpStatus.CREATED);
    }


    @PutMapping
    public ResponseEntity updateSong(@Valid @RequestBody Song song) {
        ISongService.updateSong(song);
        return new ResponseEntity<>(song, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteSongById(@PathVariable int id) {
        ISongService.deleteSongById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
