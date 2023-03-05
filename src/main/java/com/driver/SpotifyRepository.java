package com.driver;

import java.util.*;

import org.springframework.stereotype.Repository;

@Repository
public class SpotifyRepository {
    public HashMap<Artist, List<Album>> artistAlbumMap;
    public HashMap<Album, List<Song>> albumSongMap;
    public HashMap<Playlist, List<Song>> playlistSongMap;
    public HashMap<Playlist, List<User>> playlistListenerMap;
    public HashMap<User, Playlist> creatorPlaylistMap;
    public HashMap<User, List<Playlist>> userPlaylistMap;
    public HashMap<Song, List<User>> songLikeMap;

    public List<User> users;
    public List<Song> songs;
    public List<Playlist> playlists;
    public List<Album> albums;
    public List<Artist> artists;

    public SpotifyRepository(){
        //To avoid hitting apis multiple times, initialize all the hashmaps here with some dummy data
        artistAlbumMap = new HashMap<>();
        albumSongMap = new HashMap<>();
        playlistSongMap = new HashMap<>();
        playlistListenerMap = new HashMap<>();
        creatorPlaylistMap = new HashMap<>();
        userPlaylistMap = new HashMap<>();
        songLikeMap = new HashMap<>();

        users = new ArrayList<>();
        songs = new ArrayList<>();
        playlists = new ArrayList<>();
        albums = new ArrayList<>();
        artists = new ArrayList<>();
    }

    public User createUser(String name, String mobile) {
        User user=new User();
        user.setName(name);
        user.setMobile(mobile);
        users.add(user); // added user in the user list
        return user;
    }

    public Artist createArtist(String name) {
        Artist artist=new Artist();
        artist.setName(name);
        artists.add(artist);  // added artist in the artists list
        return artist;
    }

    public Album createAlbum(String title, String artistName) {

        boolean ifArtistExists=false;
        Album album=new Album();
        for (int i=0;i<artists.size();i++)
        {
            if (artistName.equals(artists.get(i)))
            {
                ifArtistExists=true;
            }
        }

        if (ifArtistExists==false)
        {
            Artist artist=new Artist();
            artist.setName(artistName);

            album.setTitle(title);

            List<Album> list=new ArrayList<>();
            for (int i=0;i<albums.size();i++)
            {
                if (albums.get(i).equals(artistName))
                {
                    list.add(albums.get(i));
                }
            }
            artistAlbumMap.put(artist,list);
        }
        else {

            Artist artist=new Artist();
            for (int i=0;i<artists.size();i++)
            {
                if (artists.get(i).equals(artistName))
                {
                    artist=artists.get(i);
                }
            }

            album.setTitle(title);
            List<Album> list=new ArrayList<>();
            for (int i=0;i<albums.size();i++)
            {
                if (albums.get(i).equals(artistName))
                {
                    list.add(albums.get(i));
                }
            }
            artistAlbumMap.put(artist,list);
        }
            albums.add(album);
            return album;
    }

    public Song createSong(String title, String albumName, int length) throws Exception{
        int a=-1;
        boolean albumExists=false;
        for (int i=0;i<albums.size();i++)
        {
            if (albums.get(i).equals(albumName))
            {
                a=i;
                albumExists=true;
            }
        }
        Song song = new Song();

         if (albumExists) {
             Album album = albums.get(a);
             song.setLength(length);
             song.setTitle(title);
             songs.add(song);
             List<Song> list = new ArrayList<>();

             list = albumSongMap.get(albumName);
             list.add(song);
             albumSongMap.put(album, list);
         }
         else {
         throw new Exception("Album does not exist");
     }
       return song;
    }

    public Playlist createPlaylistOnLength(String mobile, String title, int length) throws Exception {

        Playlist playlist=new Playlist(title);
        List<Song> list =new ArrayList<>();
        for(int i=0;i<songs.size();i++)
        {
            if (songs.get(i).getLength()==length)
            {
                list.add(songs.get(i));
            }
        }

        playlistSongMap.put(playlist,list);
        playlists.add(playlist);

        boolean userExists=false;
        List<User>userList=new ArrayList<>();
        for (int i=0;i<users.size();i++)
        {
           if (users.get(i).getMobile().equals(mobile))
           {    userExists=true;
               userList.add(users.get(i));
           }
        }
       if(userList.size()>0){ playlistListenerMap.put(playlist,userList);}
            if (!userExists)
            {
                throw new Exception("User does not exist");
            }
       return playlist;

    }

    public Playlist createPlaylistOnName(String mobile, String title, List<String> songTitles) throws Exception {

        Playlist playlist=new Playlist(title);
        List<Song> list=new ArrayList<>();
         for (int i=0;i<songs.size();i++)
        {
            for (int j=0;j<songTitles.size();j++)
            {
               if (songs.get(i).getTitle().equals(songTitles))
               {
                   list.add(songs.get(i));
               }
            }
        }
         playlistSongMap.put(playlist,list);
         playlists.add(playlist);
        boolean userExists=false;
        List<User>userList=new ArrayList<>();
        for (int i=0;i<users.size();i++)
        {
            if (users.get(i).getMobile().equals(mobile))
            {    userExists=true;
                userList.add(users.get(i));
            }
        }
        if(userList.size()>0){ playlistListenerMap.put(playlist,userList);}
        if (!userExists)
        {
            throw new Exception("User does not exist");
        }
        return playlist;

    }

    public Playlist findPlaylist(String mobile, String playlistTitle) throws Exception {

    }

    public Song likeSong(String mobile, String songTitle) throws Exception {

    }

    public String mostPopularArtist() {
    }

    public String mostPopularSong() {
    }
}
