package dailymixes;

import queue.EmptyQueueException;
import queue.QueueInterface;

// -------------------------------------------------------------------------
/**
 * Write a one-sentence summary of your class here. Follow it with additional
 * details about its purpose, what abstraction it represents, and how to use it.
 * 
 * @author Seth
 * @version Nov 4, 2024
 * @param <T>
 */
public class ArrayQueue<T>
    implements QueueInterface<T>
{
    // ~ Fields ................................................................
    private T[] contents;
    private int frontIndex;
    private int backIndex;
    /**
     * Default capcity for array queue is 20
     */
    public static final int DEFAULT_CAPACITY = 20;

    // ~ Constructors ..........................................................

    // ----------------------------------------------------------
    /**
     * Create a new ArrayQueue object.
     */
    public ArrayQueue()
    {
        this(DEFAULT_CAPACITY);
    }


    // ----------------------------------------------------------
    /**
     * Create a new ArrayQueue object.
     * 
     * @param capacity
     *            capcity of the array queue
     */
    @SuppressWarnings("unchecked")
    public ArrayQueue(int capacity)
    {
        contents = (T[])new Object[capacity + 1];
        frontIndex = 0;
        backIndex = contents.length - 1;
    }


    // ~Public Methods ........................................................
    @SuppressWarnings("unchecked")
    @Override
    public void clear()
    {
        for (int i = 0; i < contents.length; i++)
        {
            contents[i] = null;
        }
        frontIndex = 0;
        backIndex = contents.length - 1;
    }


    @Override
    public T dequeue()
    {
        T front = getFront();
        if (front == null)
        {
            throw new IllegalArgumentException("Front index is null");
        }
        contents[frontIndex] = null;
        frontIndex = (frontIndex + 1) % contents.length;
        return front;
    }


    @Override
    public void enqueue(T newEntry)
    {
        if (newEntry == null)
        {
            throw new IllegalArgumentException("Cannot enqueue null entry");
        }
        ensureCapacity();
        backIndex = (backIndex + 1) % contents.length;
        contents[backIndex] = newEntry;
    }


    @Override
    public T getFront()
    {
        if (isEmpty())
        {
            throw new EmptyQueueException();
        }
        if (contents[frontIndex] == null)
        {
            throw new IllegalArgumentException("frontIndex is null");
        }
        return contents[frontIndex];
    }


    @Override
    public boolean isEmpty()
    {
        return (((backIndex + 1) % contents.length) == frontIndex);
    }


    @SuppressWarnings("unchecked")
    private void ensureCapacity()
    {
        if ((backIndex + 2) % contents.length == frontIndex)
        {
            T[] oldContents = contents;
            int oldLength = oldContents.length;
            int newLength = 2 * oldLength - 1;
            contents = (T[])new Object[newLength];

            int j = frontIndex;
            for (int i = 0; i < oldLength - 1; i++)
            {
                contents[i] = oldContents[j];
                j = (j + 1) % oldLength;
            }
            frontIndex = 0;
            backIndex = oldLength - 2;
        }
    }


    // ----------------------------------------------------------
    /**
     * returns amount of items in the array
     * 
     * @return size
     */
    public int getSize()
    {
        if (isEmpty())
        {
            return 0;
        }
        if (frontIndex <= backIndex)
        {
            return (backIndex - frontIndex + 1);
        }
        return (backIndex + 1) + (contents.length - frontIndex);
    }


    // ----------------------------------------------------------
    /**
     * gets length of array
     * 
     * @return length of array
     */
    public int getLengthOfUnderlyingArray()
    {
        return contents.length;
    }


    // ----------------------------------------------------------
    /**
     * returns array version of the circular array
     * 
     * @return toArray array
     */
    public Object[] toArray()
    {
        if (isEmpty())
        {
            return new Object[0];
        }
        Object[] array = new Object[getSize()];
        int currIndex = frontIndex;
        for (int i = 0; i < array.length; i++)
        {
            array[i] = contents[currIndex];
            currIndex = (currIndex + 1) % contents.length;
        }
        return array;
    }


    /**
     * returns a String adaptation of code
     * 
     * @return string adaptation
     */
    public String toString()
    {
        if (isEmpty())
        {
            return "[]";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        int currIndex = frontIndex;
        for (int i = 0; i < this.getSize(); i++)
        {
            if (contents[currIndex] != null)
            {
                sb.append(contents[currIndex].toString());
            }
            if (i < this.getSize() - 1)
            {
                sb.append(", ");
            }
            currIndex = (currIndex + 1) % contents.length;
        }
        sb.append("]");
        return sb.toString();
    }


    /**
     * checks if two objects are equal
     * 
     * @param obj
     *            object to be compared
     * @return returns boolean if two are equal
     */
    @SuppressWarnings("unchecked")
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (!(obj instanceof ArrayQueue<?>))
        {
            return false;
        }
        ArrayQueue<T> other = (ArrayQueue<T>)obj;
        if (other.getSize() != this.getSize())
        {
            return false;
        }
        Object[] thisArray = this.toArray();
        Object[] otherArray = other.toArray();
        for (int i = 0; i < thisArray.length; i++)
        {
            if (!thisArray[i].equals(otherArray[i]))
            {
                return false;
            }
        }
        return true;
    }
}
