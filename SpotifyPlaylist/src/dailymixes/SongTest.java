package dailymixes;

import junit.framework.TestCase;

// -------------------------------------------------------------------------
/**
 * Test class for song class
 * 
 * @author Seth
 * @version Nov 4, 2024
 */
public class SongTest
    extends TestCase
{
    // ~ Fields ................................................................
    private Song song1;
    private Song song2;
    private Song song3;
    // ~ Constructors ..........................................................

    // ~Public Methods ........................................................
    /**
     * set up for tests
     */
    public void setUp()
    {
        song1 = new Song("Everyday", 50, 1, 30, "Chill Mix");
        song2 = new Song("Sticky", 1, 1, 1, "");
        song3 = new Song("Red", 50, 1, 50, "Country Pop Mix");
    }


    // ----------------------------------------------------------
    /**
     * tests getName()
     */
    public void testGetName()
    {
        song1.setName("Movies");
        assertEquals(song1.getName(), "Movies");
    }


    // ----------------------------------------------------------
    /**
     * tests getGenreSet
     */
    public void testGetGenreSet()
    {
        song2.setGenreSet(1, 1, 1);
        assertEquals(song2.getGenreSet(), new GenreSet(1, 1, 1));
    }


    // ----------------------------------------------------------
    /**
     * tests getPlaylistName
     */
    public void testGetPlaylistName()
    {
        song3.setPlaylistName("Indie");
        assertEquals(song3.getPlaylistName(), "Indie");
    }


    // ----------------------------------------------------------
    /**
     * tests toString
     */
    public void testToString()
    {
        // if playlist is empty
        assertEquals(
            song2.toString(),
            "No-Playlist Sticky Pop:1 Rock:1 Country:1");
        // if playlist is not empty
        assertEquals(
            song1.toString(),
            "Everyday Pop:50 Rock:1 Country:30 Suggested: Chill Mix");
    }


    // ----------------------------------------------------------
    /**
     * tests equals
     */
    public void testEquals()
    {
        // if compared to itself
        assertTrue(song3.equals(song3));
        // if different class
        assertFalse(song2.equals("4"));
        // if they are equal
        Song song4 = new Song("Everyday", 50, 1, 30, "Chill Mix");
        assertTrue(song1.equals(song4));
        // if they are not equal
        assertFalse(song1.equals(song2));
        // if null
        Song nullSong = null;
        assertFalse(song1.equals(nullSong));
        // if genresets are not equal
        Song song5 = new Song("Everyday", 54, 1, 30, "Chill Mix");
        assertFalse(song1.equals(song5));
        // if playlists names are null
        Song song6 = new Song("Everyday", 54, 1, 30, null);
        Song song7 = new Song("Everyday", 54, 1, 30, null);
        assertTrue(song6.equals(song7));
        // if one is null and the other isnt
        assertFalse(song5.equals(song6));
    }
}
