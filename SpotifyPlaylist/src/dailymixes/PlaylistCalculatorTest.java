package dailymixes;

import list.AList;
import student.TestCase;

// -------------------------------------------------------------------------
/**
 * test class for playlist calculator
 * 
 * @author Seth
 * @version Nov 11, 2024
 */
public class PlaylistCalculatorTest
    extends TestCase
{
    // ~ Fields ................................................................
    private PlaylistCalculator pc1;
    private ArrayQueue<Song> queue;
    private Playlist[] playlists;

    // ~ Constructors ..........................................................
    /**
     * set up for tests
     */
    public void setUp()
    {
        // set up queue
        queue = new ArrayQueue<Song>();
        Song song1 = new Song("Charli", 99, 20, 10, "Pop");
        Song song2 = new Song("CC", 10, 20, 75, "Country");
        Song song3 = new Song("RadioHead", 20, 80, 10, "Rock");
        queue.enqueue(song1);
        // set up playlist array
        playlists = new Playlist[PlaylistCalculator.NUM_PLAYLISTS];
        Playlist p1 = new Playlist("Pop", 50, 1, 1, 100, 20, 20, 5);
        Playlist p2 = new Playlist("Country", 1, 1, 50, 20, 20, 100, 2);
        p2.addSong(new Song("song", 2, 2, 51, ""));
        p2.addSong(new Song("song2", 2, 2, 51, ""));
        Playlist p3 = new Playlist("Rock", 1, 50, 1, 20, 100, 20, 5);
        playlists[0] = p1;
        playlists[1] = p2;
        playlists[2] = p3;
        // set up pc1
        pc1 = new PlaylistCalculator(queue, playlists);

    }


    // ~Public Methods ........................................................
    /**
     * tests for getPlaylistForSong
     */
    public void testGetPlaylistForSong()
    {
        // if the song is null
        assertNull(pc1.getPlaylistForSong(null));
        // if the playlist is valid
        Song song1 = new Song("Charli", 99, 20, 10, "Pop");
        Playlist p1 = new Playlist("Pop", 50, 1, 1, 100, 20, 20, 5);
        assertEquals(p1, pc1.getPlaylistForSong(song1));
        // if playlist is valid and deeper in the array
        Song rock = new Song("RockSong", 3, 55, 3, "Rock");
        Playlist p3 = new Playlist("Rock", 1, 50, 1, 20, 100, 20, 5);
        assertEquals(p3, pc1.getPlaylistForSong(rock));
        // if the suggested playlist is wrong
        Song song2 = new Song("Charli", 99, 20, 10, "Country");
        assertEquals(p1, pc1.getPlaylistForSong(song2));
        // if the song cannot be added into any playlist
        Song song3 = new Song("Charli", 99, 99, 99, "Country");
        assertNull(pc1.getPlaylistForSong(song3));
    }


    // ----------------------------------------------------------
    /**
     * tests for addSongToPlaylist
     */
    public void testAddSongToPlaylist()
    {
        // when it is not empty and a song can be added
        assertTrue(pc1.addSongToPlaylist());
        // when it is empty
        assertFalse(pc1.addSongToPlaylist());
    }


    // ----------------------------------------------------------
    /**
     * tests for reject
     */
    public void testReject()
    {
        Song song1 = new Song("Charli", 99, 20, 10, "Pop");
        pc1.reject();
        AList<Song> list = pc1.getRejectedTracks();
        assertTrue(list.contains(song1));
    }


    // ----------------------------------------------------------
    /**
     * tests for getPlaylistIndex
     */
    public void testGetPlaylistIndex()
    {
        // for the playlists that exist
        assertEquals(0, pc1.getPlaylistIndex("Pop"));
        assertEquals(1, pc1.getPlaylistIndex("Country"));
        assertEquals(2, pc1.getPlaylistIndex("Rock"));
        // if a playlist doesn't exist
        assertEquals(-1, pc1.getPlaylistIndex("Flop"));
    }


    // ----------------------------------------------------------
    /**
     * tests getQueue
     */
    public void testGetQueue()
    {
        assertEquals(pc1.getQueue(), queue);
    }


    // ----------------------------------------------------------
    /**
     * tests get Playlist
     */
    public void testGetPlaylists()
    {
        assertEquals(pc1.getPlaylists(), playlists);
    }


    // ----------------------------------------------------------
    /**
     * tests getRejectedTracks
     */
    public void getRejectedTracks()
    {
        Song song1 = new Song("Charli", 99, 20, 10, "Pop");
        pc1.reject();
        AList<Song> list = new AList<Song>();
        list.add(song1);
        assertEquals(pc1.getRejectedTracks(), list);
    }


    /**
     * test constructor exception
     */
    public void testConstructor()
    {
        Exception thrown = null;
        try
        {
            PlaylistCalculator pc2 = new PlaylistCalculator(null, playlists);
        }
        catch (Exception e)
        {
            thrown = e;
        }
        assertNotNull(thrown);
    }
}
