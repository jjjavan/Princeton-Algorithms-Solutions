
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;


public class RandomizedQueue<Item> implements Iterable<Item>
{
    //Implementation is similar to a stack where enqueue == push &&
    ///dequeue removes a random item, copies the item from the top to the location of
    //random item them pops top item.
    private Item[] items;
    private int size;


    public RandomizedQueue()                 // construct an empty randomized queue
    {

        items = (Item[]) new Object[2];
        size = 0;
    }

    public boolean isEmpty()                 // is the randomized queue empty?
    {
        return size == 0;
    }

    public int size()                        // return the number of items on the randomized queue
    {
        return size;
    }

    public void enqueue(Item item)           // add the item
    {
        if(item == null) throw new IllegalArgumentException();

        if(isFull())
            resize(items.length*2);
        items[size++] = item;
    }

    public Item dequeue()                    // remove and return a random item
    {
        if(isEmpty()) throw new NoSuchElementException();

        Item temp;

        int location =  StdRandom.uniform(size);
       temp = items[location];
       items[location] = items[--size];
       items[size] = null; //prevents loitering
       if(size > 0 && size == items.length/4)
           resize(items.length/2);
       return temp;
    }

    public Item sample()                     // return a random item (but do not remove it)
    {
        if(isEmpty()) throw new NoSuchElementException();

        int location = StdRandom.uniform(size);
        Item temp;
        temp = items[location];
        return temp;
    }

    public Iterator<Item> iterator()         // return an independent iterator over items in random order
    {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item>
    {
        private Item[] aux = (Item[]) new Object[items.length];
        private int index = 0;

       public boolean hasNext()
       {
           return index < size;

       }

        public Item next()
        {
            if(index == size) throw new NoSuchElementException("Element does not exist.");

            if(index == 0)
            {
                for (int i = 0; i < size; ++i)
                    aux[i] = items[i];

                StdRandom.shuffle(aux, 0, size);
            }
            return aux[index++];
        }

       public void remove() {throw new UnsupportedOperationException();}

    }

    private boolean isFull()
    {

        return size == items.length;
    }

    private void resize(int max)
    {
        Item[] tmp = (Item[]) new Object[max];
        for(int i = 0; i < size; ++i)
            tmp[i] = items[i];
        items = tmp;
    }


}

