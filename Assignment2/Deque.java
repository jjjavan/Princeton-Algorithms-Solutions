import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item>
{
    //Implements doubly linked list with overhead equal to 16B + 8B + 8B + 4B + 4B padding + 48B*n/per Node
    private Node first;
    private Node last;
    private int size;

    private class Node //16B of overhead + 8B of additional overhead for inner class + 24B = 48*n items;
    {
        Item item;
        Node prev;
        Node next;
    }
    public Deque()                           // construct an empty deque
    {
        first = null;
        last = null;
        size = 0;
    }
    public boolean isEmpty()                 // is the deque empty?
    {
        return size == 0;
    }
    public int size()                        // return the number of items on the deque
    {
        return size;
    }
    public void addFirst(Item item)          // add the item to the front
    {
        if(item == null) throw new IllegalArgumentException();

        Node oldFirst = first;
        first = new Node();
        first.item = item;
        if(isEmpty())
            last = first;
        else
        {
            first.next = oldFirst;
            oldFirst.prev = first;
        }
        ++size;
    }
    public void addLast(Item item)// add the item to the end
    {
        if(item == null) throw new IllegalArgumentException();

        Node oldLast = last;
        last = new Node();
        last.item = item;
        if(isEmpty()) first = last;
        else
        {
            last.prev = oldLast;
            oldLast.next = last;
        }
        ++size;
    }
    public Item removeFirst()                // remove and return the item from the front
    {
        if (isEmpty()) throw new NoSuchElementException("Deque is empty.");

        Item item = first.item;
        first = first.next;
        if (size > 1) first.prev = null;
        --size;
        if (isEmpty()) last = null;
        return item;
    }
    public Item removeLast()                 // remove and return the item from the end
    {
        if (isEmpty()) throw new NoSuchElementException("Deque is empty.");

        Item item = last.item;
        last = last.prev;
        if (size > 1) last.next = null;
        --size;
        if (isEmpty()) first = null;
        return item;
    }

    public Iterator<Item> iterator()         // return an iterator over items in order from front to end
    {
        return new ListIterator();
    }
    private class ListIterator implements Iterator<Item>
    {
        private Node current = first;
        public boolean hasNext()
        {
            return current != null;
        }

        public void remove(){throw new UnsupportedOperationException();}

        public Item next()
        {
            if(isEmpty() || current == null) throw new NoSuchElementException("Deque is empty.");

            Item item = current.item;
            current = current.next;
            return item;
        }

    }
    public static void main(String[] args)   // unit testing (optional)
    {
        Deque<Double> test = new Deque<>();
        for (Double temp: test)
        {
            System.out.print(temp);
        }
    }
}
