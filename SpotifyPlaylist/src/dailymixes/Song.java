package dailymixes;

// -------------------------------------------------------------------------
/**
 * Write a one-sentence summary of your class here. Follow it with additional
 * details about its purpose, what abstraction it represents, and how to use it.
 * 
 * @author Seth
 * @version Nov 4, 2024
 */
public class Song
{
    // ~ Fields ................................................................
    private String name;
    private String suggestedPlaylist;
    private GenreSet genreSet;
    // ~ Constructors ..........................................................

    // ~Public Methods ........................................................

    // ----------------------------------------------------------
    /**
     * Create a new Song object.
     * 
     * @param songName
     *            name of song
     * @param pop
     *            pop composition
     * @param rock
     *            rock composition
     * @param country
     *            country composition
     * @param suggested
     *            suggested playlist
     */
    public Song(
        String songName,
        int pop,
        int rock,
        int country,
        String suggested)
    {
        this.name = songName;
        this.genreSet = new GenreSet(pop, rock, country);
        this.suggestedPlaylist = suggested;

    }


    // ----------------------------------------------------------
    /**
     * returns name of suggested playlist
     * 
     * @return name of suggested playlist
     */
    public String getPlaylistName()
    {
        return suggestedPlaylist;
    }


    // ----------------------------------------------------------
    /**
     * sets name of suggested playlist
     * 
     * @param playlistName
     *            name of playlsit
     */
    public void setPlaylistName(String playlistName)
    {
        suggestedPlaylist = playlistName;
    }


    // ----------------------------------------------------------
    /**
     * returns name of song
     * 
     * @return returns name of song
     */
    public String getName()
    {
        return name;
    }


    // ----------------------------------------------------------
    /**
     * sets the name of the song
     * 
     * @param name
     *            name of song
     */
    public void setName(String name)
    {
        this.name = name;
    }


    // ----------------------------------------------------------
    /**
     * gets the genre set for the song
     * 
     * @return returns genreSet
     */
    public GenreSet getGenreSet()
    {
        return genreSet;
    }


    // ----------------------------------------------------------
    /**
     * sets the values for a genre set
     * 
     * @param pop
     *            pop composition
     * @param rock
     *            rock composition
     * @param country
     *            country composition
     */
    public void setGenreSet(int pop, int rock, int country)
    {
        GenreSet gs = new GenreSet(pop, rock, country);
        genreSet = gs;
    }


    /**
     * Creates a string representation of a song
     * 
     * @return returns string representation of a song
     */
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(" ");
        sb.append("Pop:").append(genreSet.getPop()).append(" ");
        sb.append("Rock:").append(genreSet.getRock()).append(" ");
        sb.append("Country:").append(genreSet.getCountry());
        if (suggestedPlaylist.length() == 0)
        {
            sb.insert(0, "No-Playlist ");
        }
        else
        {
            sb.append(" Suggested: ").append(suggestedPlaylist);
        }
        return sb.toString();
    }


    /**
     * equals method for song
     * 
     * @param obj
     *            object to be compared
     * @return returns if object is equal
     */
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass())
        {
            return false;
        }
        Song other = (Song)obj;
        return (this.getName().equals(other.getName())
            && ((this.getPlaylistName() == null
                && other.getPlaylistName() == null)
                || (this.getPlaylistName() != null
                    && this.getPlaylistName().equals(other.getPlaylistName())))
            && this.getGenreSet().equals(other.getGenreSet()));
    }
}
