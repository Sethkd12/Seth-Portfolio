package dailymixes;

// -------------------------------------------------------------------------
/**
 * Write a one-sentence summary of your class here. Follow it with additional
 * details about its purpose, what abstraction it represents, and how to use it.
 * 
 * @author Seth
 * @version Nov 3, 2024
 */
public class GenreSet
    implements Comparable<GenreSet>
{

    // ----------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(GenreSet o)
    {
        int thisSum = this.getPop() + this.getRock() + this.getCountry();
        int otherSum = o.getPop() + o.getRock() + o.getCountry();
        return thisSum - otherSum;
    }

    // ----------------------------------------------------------
    // ~ Fields ................................................................
    private int pop;
    private int rock;
    private int country;

    // ~ Constructors ..........................................................
    /**
     * Create a new GenreSet object.
     * 
     * @param pop pop value
     * @param rock rock value
     * @param country country value
     */
    public GenreSet(int pop, int rock, int country)
    {
        this.pop = pop;
        this.rock = rock;
        this.country = country;
    }
    // ~Public Methods ........................................................


    // ----------------------------------------------------------
    /**
     * returns value of pop field
     * 
     * @return pop value
     */
    public int getPop()
    {
        return pop;
    }
    
    // ----------------------------------------------------------
    /**
     * sets the value of pop
     * @param value pop  value
     */
    public void setPop(int value)
    {
        pop = value;
    }
    // ----------------------------------------------------------
    /**
     * sets the value of rock
     * @param value rock value 
     */
    public void setRock(int value)
    {
        rock = value;
    }
    // ----------------------------------------------------------
    /**
     * sets the value of country
     * @param value country value
     */
    public void setCountry(int value)
    {
        country = value;
    }


    /**
     * returns value of rock field
     * 
     * @return rock value
     */
    public int getRock()
    {
        return rock;
    }


    /**
     * returns value of country field
     * 
     * @return country value
     */
    public int getCountry()
    {
        return country;
    }


    private boolean isLessThanOrEqualTo(GenreSet other)
    {
        return (this.pop <= other.getPop() && this.rock <= other.getRock()
            && this.country <= other.getCountry());
    }


    // ----------------------------------------------------------
    /**
     * @param minGenreSet min genre set 
     * @param maxGenreSet max genre set
     * @return returns whether the genre set is in range
     */
    public boolean isWithinRange(GenreSet minGenreSet, GenreSet maxGenreSet)
    {
        boolean isBelow = false;
        boolean isAbove = false;
        if (this.isLessThanOrEqualTo(maxGenreSet))
        {
            isBelow = true;
        }
        if (minGenreSet.isLessThanOrEqualTo(this))
        {
            isAbove = true;
        }
        return isBelow && isAbove;
    }

    /**
     * equals method for genre set class
     * @param obj object to be compared
     * @return true or false if two are equal or not
     */
    public boolean equals(Object obj)
    {
        if (obj == null || this.getClass() != obj.getClass())
        {
            return false;
        }
        if (this == obj)
        {
            return true;
        }
        return (this.getPop() == ((GenreSet)obj).getPop()
            && this.getRock() == ((GenreSet)obj).getRock()
            && this.getCountry() == ((GenreSet)obj).getCountry());

    }

    /**
     * toString method for genre set
     * @return string adaptation of genreset
     */
    public String toString()
    {
        return "Pop:" + this.getPop() + " Rock:" + this.getRock() + " Country:"
            + this.getCountry();
    }

}
