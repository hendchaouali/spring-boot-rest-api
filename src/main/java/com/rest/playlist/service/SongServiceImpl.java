package com.rest.playlist.service;

import com.rest.playlist.enums.SongCategory;
import com.rest.playlist.model.Song;
import com.rest.playlist.web.exception.AlreadyExistException;
import com.rest.playlist.web.exception.ResourceNotFoundException;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Service
public class SongServiceImpl implements ISongService {
    private static final Logger log = LoggerFactory.getLogger(SongServiceImpl.class);

    private CopyOnWriteArrayList<Song> mySongs = new SongDataGenerator().getData();

    @Override
    public List<Song> getAllSongs() {
        return mySongs;
    }

    @Override
    public List<Song> getSongsByCategory(String category) {
        SongCategory songCategory = EnumUtils.getEnumIgnoreCase(SongCategory.class, category);
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
        verifyIfSongExist(song);
        mySongs.add(song);
        return song;
    }

    @Override
    public void updateSong(Song song) {
        verifyIfSongExist(song);

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

    private void verifyIfSongExist(Song song) {
        Song searchedSong = mySongs.stream()
                .filter(s -> StringUtils.equals(s.getTitle(), song.getTitle()) &&
                        s.getCategory() == song.getCategory() && (s.getId() != song.getId())
                )
                .findAny()
                .orElse(null);
        if (searchedSong != null) {
            throw new AlreadyExistException("Song Already Exists.");
        }
    }
}
