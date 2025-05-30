package dailymixes;

import student.TestCase;

// -------------------------------------------------------------------------
/**
 * test class for array queue
 * 
 * @author Seth
 * @version Nov 4, 2024
 */
public class ArrayQueueTest
    extends TestCase
{
    // ~ Fields ................................................................
    private ArrayQueue<Song> queue1;
    private ArrayQueue<Song> queue2;
    private ArrayQueue<Song> queue3;
    // ~ Constructors ..........................................................

    // ~Public Methods ........................................................
    /**
     * set up for tests
     */
    public void setUp()
    {
        Song song1 = new Song("Everyday", 50, 1, 30, "Chill Mix");
        Song song2 = new Song("Sticky", 1, 1, 1, "");
        Song song3 = new Song("Red", 50, 1, 50, "Country Pop Mix");
        queue1 = new ArrayQueue<Song>(5);
        queue1.enqueue(song1);
        queue1.enqueue(song2);
        queue1.enqueue(song3);
        queue2 = new ArrayQueue<Song>(3);
        queue2.enqueue(song2);
        queue2.enqueue(song3);
        queue2.enqueue(song1);
        queue3 = new ArrayQueue<Song>();
    }


    // ----------------------------------------------------------
    /**
     * test method for clear
     */
    public void testClear()
    {
        queue1.clear();
        assertTrue(queue1.isEmpty());
    }


    // ----------------------------------------------------------
    /**
     * tests dequeue
     */
    public void testDequeue()
    {
        assertEquals(
            queue1.dequeue(),
            new Song("Everyday", 50, 1, 30, "Chill Mix"));
        assertEquals(queue1.getSize(), 2);
        Exception thrown = null;
        try
        {
            queue3.dequeue();
        }
        catch (Exception e)
        {
            thrown = e;
        }
        assertNotNull(thrown);
    }


    // ----------------------------------------------------------
    /**
     * tests enqueue
     */
    public void testEnqueue()
    {
        Exception thrown = null;
        Song song = null;
        try
        {
            queue3.enqueue(song);
        }
        catch (Exception e)
        {
            thrown = e;
        }
        assertNotNull(thrown);
        Song songAdd = new Song("AHAP", 50, 1, 1, "Turn Up");
        queue3.enqueue(songAdd);
        assertEquals(queue3.getSize(), 1);
        assertEquals(queue3.getFront(), songAdd);
    }


    // ----------------------------------------------------------
    /**
     * tests ensurecapacity()
     */
    public void testEnsureCapacity()
    {
        Song songAdd = new Song("AHAP", 50, 1, 1, "Turn Up");
        queue2.enqueue(songAdd);
        assertEquals(queue2.getLengthOfUnderlyingArray(), 7);
        assertEquals(queue2.getSize(), 4);
    }


    // ----------------------------------------------------------
    /**
     * tests getsize()
     */
    public void testGetSize()
    {
        Song songAdd = new Song("AHAP", 50, 1, 1, "Turn Up");
        assertEquals(queue3.getSize(), 0);
        assertEquals(queue1.getSize(), 3);
        queue1.dequeue();
        queue1.dequeue();
        queue1.enqueue(songAdd);
        queue1.enqueue(songAdd);
        assertEquals(queue1.getSize(), 3);
    }


    // ----------------------------------------------------------
    /**
     * tests for getFront
     */
    public void testGetFront()
    {
        assertEquals(
            queue1.getFront(),
            new Song("Everyday", 50, 1, 30, "Chill Mix"));
        Exception thrown = null;
        try
        {
            queue3.getFront();
        }
        catch (Exception e)
        {
            thrown = e;
        }
        assertNotNull(thrown);
    }


    // ----------------------------------------------------------
    /**
     * tests getLengthOfArray()
     */
    public void testGetLengthOfArray()
    {
        assertEquals(queue1.getLengthOfUnderlyingArray(), 6);
    }


    // ----------------------------------------------------------
    /**
     * tests toArray()
     */
    public void testToArray()
    {
        Song song1 = new Song("Everyday", 50, 1, 30, "Chill Mix");
        Song song2 = new Song("Sticky", 1, 1, 1, "");
        Song song3 = new Song("Red", 50, 1, 50, "Country Pop Mix");
        Object[] expected = queue1.toArray();
        Song[] array = { song1, song2, song3 };
        for (int i = 0; i < array.length; i++)
        {
            assertEquals(array[i], expected[i]);
        }
        Object[] arrayEmpty = queue3.toArray();
        assertEquals(arrayEmpty.length, 0);
    }


    // ----------------------------------------------------------
    /**
     * tests toString()
     */
    public void testToString()
    {
        queue1.dequeue();
        Song song2 = new Song("Sticky", 1, 1, 1, "");
        Song song3 = new Song("Red", 50, 1, 50, "Country Pop Mix");
        String toString = queue1.toString();
        String expected =
            "[" + song2.toString() + ", " + song3.toString() + "]";
        assertEquals(toString, expected);
        assertEquals(queue3.toString(), "[]");
    }


    /**
     * tests Equals()
     */
    public void testEquals()
    {
        Song song1 = new Song("Everyday", 50, 1, 30, "Chill Mix");
        Song song2 = new Song("Sticky", 1, 1, 1, "");
        Song song3 = new Song("Red", 50, 1, 50, "Country Pop Mix");
        assertFalse(queue1.equals(queue3));
        assertFalse(queue1.equals("3"));
        ArrayQueue<Song> queue4 = null;
        // null
        assertFalse(queue1.equals(queue4));
        queue4 = new ArrayQueue<Song>(5);
        queue4.enqueue(song2);
        queue4.enqueue(song1);
        queue4.enqueue(song3);
        // same contents different order
        assertFalse(queue1.equals(queue4));
        queue3.enqueue(song1);
        queue3.enqueue(song2);
        queue3.enqueue(song3);
        //different 
        assertEquals(queue1, queue3);
    }
}
