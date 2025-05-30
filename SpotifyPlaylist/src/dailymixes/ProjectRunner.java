package dailymixes;

import java.io.FileNotFoundException;
import java.text.ParseException;

// -------------------------------------------------------------------------
/**
 *  Write a one-sentence summary of your class here.
 *  Follow it with additional details about its purpose, what abstraction
 *  it represents, and how to use it.
 * 
 *  @author Seth
 *  @version Nov 11, 2024
 */
public class ProjectRunner
{
    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     * @param args
     * @throws FileNotFoundException
     * @throws ParseException
     * @throws DailyMixDataException
     */
    public static void main(String[] args) throws FileNotFoundException, ParseException, DailyMixDataException
    {
        String songFileName = "input.txt";
        String playlistFileName = "playlists.txt";
        if(args.length == 2)
        {
            songFileName = args[0];
            playlistFileName = args[1];
        }
        PlaylistReader reader = new PlaylistReader(songFileName, playlistFileName);
    }
}
