package dailymixes;

// -------------------------------------------------------------------------
/**
 * Write a one-sentence summary of your class here. Follow it with additional
 * details about its purpose, what abstraction it represents, and how to use it.
 * 
 * @author Seth
 * @version Nov 4, 2024
 */
public class Playlist
    implements Comparable<Playlist>
{

    // ----------------------------------------------------------

    // ~ Fields ................................................................
    private GenreSet minGenreSet;
    private GenreSet maxGenreSet;
    private Song[] songs;
    private int capacity;
    private int numberOfSongs;
    private String name;

    // ~ Constructors ..........................................................
    // ----------------------------------------------------------
    /**
     * Create a new Playlist object.
     * 
     * @param playlistName
     *            name of playlist
     * @param minPop
     *            min for pop
     * @param minRock
     *            min for rock
     * @param minCountry
     *            min for country
     * @param maxPop
     *            max for pop
     * @param maxRock
     *            max for rock
     * @param maxCountry
     *            max for country
     * @param playlistCap
     *            capacity for song array
     */
    public Playlist(
        String playlistName,
        int minPop,
        int minRock,
        int minCountry,
        int maxPop,
        int maxRock,
        int maxCountry,
        int playlistCap)
    {
        this.name = playlistName;
        minGenreSet = new GenreSet(minPop, minRock, minCountry);
        maxGenreSet = new GenreSet(maxPop, maxRock, maxCountry);
        this.capacity = playlistCap;
        songs = new Song[capacity];
        numberOfSongs = 0;

    }


    // ~Public Methods ........................................................
    // ----------------------------------------------------------
    /**
     * returns array of songs
     * 
     * @return array of songs
     */
    public Song[] getSongs()
    {
        return songs;
    }


    // ----------------------------------------------------------
    /**
     * returns playlist name
     * 
     * @return name name of playlist
     */
    public String getName()
    {
        return name;
    }


    // ----------------------------------------------------------
    /**
     * sets playlist name
     * 
     * @param name
     *            name of playlist
     */
    public void setName(String name)
    {
        this.name = name;
    }


    // ----------------------------------------------------------
    /**
     * returns capacity
     * 
     * @return capacity
     */
    public int getCapacity()
    {
        return capacity;
    }


    // ----------------------------------------------------------
    /**
     * gets min genre set
     * 
     * @return returns min genre set
     */
    public GenreSet getMinGenreSet()
    {
        return minGenreSet;
    }


    // ----------------------------------------------------------
    /**
     * sets the values for min genre set
     * 
     * @param pop
     *            pop value
     * @param rock
     *            rock value
     * @param country
     *            country value
     */
    public void setMinGenreSet(int pop, int rock, int country)
    {
        minGenreSet.setPop(pop);
        minGenreSet.setRock(rock);
        minGenreSet.setCountry(country);
    }


    /**
     * gets max genre set
     * 
     * @return returns max genre set
     */
    public GenreSet getMaxGenreSet()
    {
        return maxGenreSet;
    }


    // ----------------------------------------------------------
    /**
     * sets the values for max genre set
     * 
     * @param pop
     *            pop value
     * @param rock
     *            rock value
     * @param country
     *            country value
     */
    public void setMaxGenreSet(int pop, int rock, int country)
    {
        maxGenreSet.setPop(pop);
        maxGenreSet.setRock(rock);
        maxGenreSet.setCountry(country);
    }


    /**
     * returns amount of spaces left on playlist
     * 
     * @return amount of spaces left
     */
    public int getSpacesLeft()
    {
        return capacity - numberOfSongs;
    }


    // ----------------------------------------------------------
    /**
     * returns if playlist is full
     * 
     * @return if numberOfSongs equals capacity
     */
    public boolean isFull()
    {
        return numberOfSongs == capacity;
    }


    // ----------------------------------------------------------
    /**
     * returns if qualified for a playlist
     * 
     * @param possibleSong
     *            songs that could be qualified
     * @return boolean for if it's qualified for a playlist
     */
    public boolean isQualified(Song possibleSong)
    {
        GenreSet songSet = possibleSong.getGenreSet();
        return songSet.isWithinRange(minGenreSet, maxGenreSet);
    }


    // ----------------------------------------------------------
    /**
     * returns number of songs
     * 
     * @return number of songs
     */
    public int getNumberOfSongs()
    {
        return numberOfSongs;
    }


    // ----------------------------------------------------------
    /**
     * add a song to songs
     * 
     * @param newSong
     *            song to be added
     * @return if the song was added
     */
    public boolean addSong(Song newSong)
    {
        if (!isFull() && isQualified(newSong))
        {
            songs[numberOfSongs] = newSong;
            numberOfSongs++;
            return true;
        }
        return false;
    }


    /**
     * returns toString of playlist
     * 
     * @return string of Playlist object
     */
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Playlist: ").append(name);
        sb.append(", # of songs: ").append(numberOfSongs).append(" (cap: ")
            .append(capacity).append("), ");
        sb.append("Requires: ");
        sb.append("Pop:").append(minGenreSet.getPop()).append("%-")
            .append(maxGenreSet.getPop()).append("%, ");
        sb.append("Rock:").append(minGenreSet.getRock()).append("%-")
            .append(maxGenreSet.getRock()).append("%, ");
        sb.append("Country:").append(minGenreSet.getCountry()).append("%-")
            .append(maxGenreSet.getCountry()).append("%");
        return sb.toString();
    }


    /**
     * returns if two objects are equal
     * 
     * @param obj
     *            object to be compared
     * @return boolean for if they are equal
     */
    public boolean equals(Object obj)
    {
        if (obj == null || obj.getClass() != this.getClass())
        {
            return false;
        }
        if (this == obj)
        {
            return true;
        }
        if (!this.getMinGenreSet().equals(((Playlist)obj).getMinGenreSet()))
        {
            return false;
        }
        if (!this.getMaxGenreSet().equals(((Playlist)obj).getMaxGenreSet()))
        {
            return false;
        }
        if (this.capacity != ((Playlist)obj).getCapacity()
            || this.numberOfSongs != ((Playlist)obj).getNumberOfSongs()
            || !this.name.equals(((Playlist)obj).getName()))
        {
            return false;
        }
        for (int i = 0; i < this.numberOfSongs; i++)
        {
            if (!songs[i].equals(((Playlist)obj).getSongs()[i]))
            {
                return false;
            }
        }
        return true;
    }


    /**
     * compares to playlist objects
     * 
     * @param other
     *            playlist to be compared
     * @return int if they are greater less are equal
     */
    public int compareTo(Playlist other)
    {
        if (this.getCapacity() != other.getCapacity())
        {
            return this.getCapacity() - other.getCapacity();
        }
        if (this.getSpacesLeft() != other.getSpacesLeft())
        {
            return this.getSpacesLeft() - other.getSpacesLeft();
        }
        int minComparison = this.minGenreSet.compareTo(other.minGenreSet);
        if (minComparison != 0)
        {
            return minComparison;
        }
        int maxComparison = this.maxGenreSet.compareTo(other.maxGenreSet);
        if (maxComparison != 0)
        {
            return maxComparison;
        }
        return this.getName().compareTo(other.getName());
    }
}
