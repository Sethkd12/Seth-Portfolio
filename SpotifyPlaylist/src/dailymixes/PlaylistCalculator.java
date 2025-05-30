package dailymixes;

import java.util.Arrays;
import java.util.Collections;
import list.AList;

// -------------------------------------------------------------------------
/**
 * Playlist calculator class, more logic into adding songs to playlists
 * 
 * @author Seth
 * @version Nov 10, 2024
 */
public class PlaylistCalculator
{
    // ~ Fields ................................................................
    private Playlist[] playlists;
    /**
     * constant of 3 playlists in the playlist array
     */
    public static final int NUM_PLAYLISTS = 3;
    /**
     * min percent value constant set to 0
     */
    public static final int MIN_PERCENT = 0;
    private AList<Song> rejectedTracks;
    private ArrayQueue<Song> songQueue;
    /**
     * max percent value constant set to 100
     */
    public static final int MAX_PERCENT = 100;

    // ~ Constructors ..........................................................

    // ----------------------------------------------------------
    /**
     * Create a new PlaylistCalculator object.
     * 
     * @param songs
     *            array queue of songs
     * @param playlistArray
     *            array of playlists
     */
    public PlaylistCalculator(ArrayQueue<Song> songs, Playlist[] playlistArray)
    {
        if (songs == null)
        {
            throw new IllegalArgumentException();
        }
        this.songQueue = songs;
        this.playlists = playlistArray;
        this.rejectedTracks = new AList<Song>();
    }

    // ~Public Methods ........................................................


    // ----------------------------------------------------------
    /**
     * returns what playlist the song fits best on
     * 
     * @param nextSong
     *            next song in queue
     * @return playlist that the song best fits on
     */
    public Playlist getPlaylistForSong(Song nextSong)
    {
        if (nextSong == null)
        {
            return null;
        }
        Playlist suggPlaylist =
            getSuggestedPlaylist(nextSong.getPlaylistName());
        if (suggPlaylist != null && !suggPlaylist.isFull()
            && suggPlaylist.isQualified(nextSong))
        {
            return suggPlaylist;
        }
        return getPlaylistWithMaximunCapacity(nextSong);

    }


    private Playlist getPlaylistWithMaximunCapacity(Song aSong)
    {
        Playlist[] playlistCopy = Arrays.copyOf(playlists, playlists.length);
        Arrays.sort(playlistCopy, Collections.reverseOrder());
        for (Playlist playlist : playlistCopy)
        {
            if (!playlist.isFull() && playlist.isQualified(aSong))
            {
                return playlist;
            }
        }
        return null;
    }


    private Playlist getSuggestedPlaylist(String name)
    {
        for (Playlist playlist : playlists)
        {
            if (playlist.getName().equals(name))
            {
                return playlist;
            }
        }
        return null;
    }


    // ----------------------------------------------------------
    /**
     * determines whether or not a song is added to a playlist
     * 
     * @return true or false if song is added
     */
    public boolean addSongToPlaylist()
    {
        if (!songQueue.isEmpty())
        {
            Song topSong = songQueue.getFront();
            Playlist p = getPlaylistForSong(topSong);
            if (p != null)
            {
                p.addSong(topSong);
                songQueue.dequeue();
                return true;
            }
        }
        return false;
    }


    // ----------------------------------------------------------
    /**
     * rejects song at front of the queue
     */
    public void reject()
    {
        Song song = songQueue.dequeue();
        rejectedTracks.add(song);
    }


    // ----------------------------------------------------------
    /**
     * gets index of the playlist in the playlist array
     * 
     * @param playlist
     *            playlist to be checked for it's index
     * @return index value of the playlist in the playlist array
     */
    public int getPlaylistIndex(String playlist)
    {
        for (int i = 0; i < playlists.length; i++)
        {
            if (playlists[i].getName().equals(playlist))
            {
                return i;
            }
        }
        return -1;
    }


    // ----------------------------------------------------------
    /**
     * returns song queue
     * 
     * @return queue of songs
     */
    public ArrayQueue<Song> getQueue()
    {
        return songQueue;
    }


    // ----------------------------------------------------------
    /**
     * returns playlist array
     * 
     * @return array of playlists
     */
    public Playlist[] getPlaylists()
    {
        return playlists;
    }


    // ----------------------------------------------------------
    /**
     * returns rejected tracks
     * 
     * @return list of rejected tracks
     */
    public AList<Song> getRejectedTracks()
    {
        return rejectedTracks;
    }
}
