package dailymixes;

import student.TestCase;

// -------------------------------------------------------------------------
/**
 * Test class for Playlist class
 * 
 * @author Seth
 * @version Nov 4, 2024
 */
public class PlaylistTest
    extends TestCase
{
    // ~ Fields ................................................................
    private Playlist p1;
    private Playlist p2;
    private Playlist p3;
    private Playlist p4;
    private Song song1;
    private Song song2;
    private Song song4;
    private Song song5;
    // ~ Constructors ..........................................................

    // ~Public Methods ........................................................
    /**
     * set up for tests
     */
    public void setUp()
    {
        p1 = new Playlist("Country", 1, 1, 70, 20, 20, 99, 10);

        p2 = new Playlist("Pop", 70, 1, 1, 90, 20, 20, 10);

        p3 = new Playlist("Rock", 1, 70, 1, 20, 99, 20, 2);

        p4 = new Playlist("Country", 1, 1, 70, 20, 20, 99, 10);
        song1 = new Song("CountrySong", 4, 4, 75, "Country");
        song2 = new Song("CountrySong2", 5, 5, 76, "Country");

        song4 = new Song("RockSong", 5, 75, 5, "roque");

        song5 = new Song("RockSong2", 5, 75, 5, "roque");
    }


    // ----------------------------------------------------------
    /**
     * tests Songs()
     */
    public void testGetSongs()
    {
        p1.addSong(song1);
        p1.addSong(song2);
        Song[] songs = p1.getSongs();
        Song[] expected = { song1, song2 };
        for (int i = 0; i < p1.getNumberOfSongs(); i++)
        {
            assertEquals(songs[i], expected[i]);
        }
    }


    // ----------------------------------------------------------
    /**
     * tests get name
     */
    public void testGetName()
    {
        p1.setName("CountryPlaylist");
        assertEquals(p1.getName(), "CountryPlaylist");
    }


    // ----------------------------------------------------------
    /**
     * get capacity
     */
    public void testGetCapacity()
    {
        assertEquals(p1.getCapacity(), 10);
    }


    // ----------------------------------------------------------
    /**
     * tests getMinGenreSet
     */
    public void testGetMinGenreSet()
    {
        p2.setMinGenreSet(1, 1, 1);
        GenreSet g = new GenreSet(1, 1, 1);
        assertEquals(p2.getMinGenreSet(), g);
    }


    /**
     * tests getMaxGenreSet
     */
    public void testGetMaxGenreSet()
    {
        p2.setMaxGenreSet(70, 70, 70);
        GenreSet g = new GenreSet(70, 70, 70);
        assertEquals(p2.getMaxGenreSet(), g);
    }


    /**
     * tests for getSpacesLeft()
     */
    public void testGetSpacesLeft()
    {
        p1.addSong(song1);
        p1.addSong(song2);
        assertEquals(p1.getSpacesLeft(), 8);
    }


    // ----------------------------------------------------------
    /**
     * tests for isFull
     */
    public void testIsFull()
    {
        // not full
        p1.addSong(song1);
        p1.addSong(song2);
        assertFalse(p1.isFull());
        // full
        p3.addSong(song4);
        p3.addSong(song5);
        assertTrue(p3.isFull());
    }


    // ----------------------------------------------------------
    /**
     * tests for isQualified
     */
    public void testIsQualified()
    {
        // qualified
        assertTrue(p1.isQualified(song1));
        // not qualified
        assertFalse(p2.isQualified(song1));
    }


    // ----------------------------------------------------------
    /**
     * tests for GetNumberOfSongs
     */
    public void testGetNumberOfSongs()
    {
        p1.addSong(song1);
        p1.addSong(song2);
        assertEquals(p1.getNumberOfSongs(), 2);
    }


    // ----------------------------------------------------------
    /**
     * tests for addSongs()
     */
    public void testAddSong()
    {
        // if qualified
        assertTrue(p1.addSong(song1));
        // if not qualified
        assertFalse(p1.addSong(song4));
        // if full
        p3.addSong(song4);
        p3.addSong(song5);
        assertFalse(p3.addSong(song5));
    }


    // ----------------------------------------------------------
    /**
     * tests for toString
     */
    public void testToString()
    {
        p1.addSong(song1);
        p1.addSong(song2);
        String string = p1.toString();
        String expected = "Playlist: Country, # of songs: 2 (cap: 10), "
            + "Requires: Pop:1%-20%, Rock:1%-20%, Country:70%-99%";
        assertEquals(string, expected);
    }


    // ----------------------------------------------------------
    /**
     * tests for equals
     */
    public void testEquals()
    {
        Playlist p5 = null;
        p1.addSong(song1);
        p4.addSong(song1);
        p3.addSong(song4);
        // null
        assertFalse(p1.equals(p5));
        // diff class
        assertFalse(p1.equals("p1"));
        // to itself
        assertTrue(p1.equals(p1));
        // different playlists diff length
        assertFalse(p1.equals(p3));
        // same
        assertTrue(p1.equals(p4));
        p4.addSong(song2);
        // different lengths
        assertFalse(p1.equals(p4));
        // different maxGenreSet
        Playlist p6 = new Playlist("Country", 1, 1, 70, 20, 45, 99, 10);
        Playlist p7 = new Playlist("Country", 1, 1, 70, 20, 28, 99, 10);
        assertFalse(p6.equals(p7));
        // different capacities
        Playlist p8 = new Playlist("Country", 1, 1, 70, 20, 45, 99, 19);
        assertFalse(p6.equals(p8));
        // different names
        Playlist p9 = new Playlist("Bountry", 1, 1, 70, 20, 45, 99, 19);
        assertFalse(p8.equals(p9));
    }


    // ----------------------------------------------------------
    /**
     * tests for compareTo
     */
    public void testCompareTo()
    {
        Playlist pCompare = new Playlist("Country", 1, 1, 70, 20, 20, 99, 9);
        // differentCapacities
        assertEquals(p1.compareTo(pCompare), 1);
        // different spaces left
        pCompare = new Playlist("Country", 1, 1, 70, 20, 20, 99, 10);
        pCompare.addSong(song1);
        assertEquals(p1.compareTo(pCompare), 1);
        // diff min genre set
        p1.addSong(song1);
        pCompare = new Playlist("Country", 1, 1, 71, 20, 20, 99, 10);
        assertEquals(p1.compareTo(pCompare), -1);
        // diff max
        pCompare = new Playlist("Country", 1, 1, 70, 20, 20, 100, 10);
        pCompare.addSong(song1);
        assertEquals(p1.compareTo(pCompare), -1);
        // diffName
        pCompare = new Playlist("Bountry", 1, 1, 70, 20, 20, 99, 10);
        pCompare.addSong(song1);
        assertEquals(p1.compareTo(pCompare), 1);

    }
}
