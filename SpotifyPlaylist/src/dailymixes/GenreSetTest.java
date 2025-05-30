package dailymixes;

import student.TestCase;

// -------------------------------------------------------------------------
/**
 * test class for GenreSet class
 * 
 * @author Seth
 * @version Nov 4, 2024
 */
public class GenreSetTest
    extends TestCase
{
    // ~ Fields ................................................................
    private GenreSet gr1;
    private GenreSet gr2;
    private GenreSet gr3;
    private GenreSet gr4;
    // ~ Constructors ..........................................................

    // ~Public Methods ........................................................
    /**
     * set up for tests
     */
    public void setUp()
    {
        gr1 = new GenreSet(10, 20, 40);
        gr2 = new GenreSet(5, 6, 7);
        gr3 = new GenreSet(80, 80, 80);
        gr4 = new GenreSet(10, 20, 40);
    }


    // ----------------------------------------------------------
    /**
     * tests getCountry
     */
    public void testGetPop()
    {
        gr1.setPop(40);
        assertEquals(gr1.getPop(), 40);
    }


    // ----------------------------------------------------------
    /**
     * tests getCountry
     */
    public void testGetRock()
    {
        gr1.setRock(40);
        assertEquals(gr1.getRock(), 40);
    }


    // ----------------------------------------------------------
    /**
     * tests getCountry
     */
    public void testGetCountry()
    {
        gr1.setCountry(30);
        assertEquals(gr1.getCountry(), 30);
    }


    // ----------------------------------------------------------
    /**
     * tests is within range
     */
    public void testIsWithinRange()
    {
        // if within range
        assertTrue(gr1.isWithinRange(gr2, gr3));
        // if genre values are all below the min values
        assertFalse(gr2.isWithinRange(gr1, gr3));
        // if genre values are all above the max values
        assertFalse(gr3.isWithinRange(gr2, gr1));
    }


    // ----------------------------------------------------------
    /**
     * test cases for equals method
     */
    public void testEquals()
    {
        assertTrue(gr1.equals(gr4));
        // if two are not equal
        assertFalse(gr1.equals(gr2));
        // if two are different classes
        assertFalse(gr3.equals(4));
        // if compared to itself
        assertTrue(gr1.equals(gr1));
    }


    // ----------------------------------------------------------
    /**
     * test for toString
     */
    public void testToString()
    {
        assertEquals(gr1.toString(), "Pop:10 Rock:20 Country:40");
    }


    /**
     * test for compareTo
     */
    public void testCompareTo()
    {
        int compare = gr3.compareTo(gr1);
        assertEquals(compare, 170);
    }
}
