import java.util.NoSuchElementException;

/**
 * Your implementation of a Min Heap
 *
 * @author Vernon Buck
 * @version 1.1
 */
public class MinHeap<T extends Comparable<? super T>>
    implements HeapInterface<T> {

    // Do NOT add or modify any of these instance variables
    private T[] backingArray;
    private int size;

    /**
     * Creates a Heap with an initial capacity of {@code INITIAL_CAPACITY}
     * Do NOT hardcode this value. Use the CONSTANT provided in the interface
     *
     * Should be O(1)
     */
    public MinHeap() {
        this(INITIAL_CAPACITY);
    }

    /**
     * Creates a Heap with an initial capacity of initialCapacity
     *
     * @param initialCapacity capacity of the new array to initialize
     *                        This value should be the length of the array
     *                        regardless of our heap being 1-indexed
     *
     * Should be O(1)
     */
    public MinHeap(int initialCapacity) {
        backingArray = (T[]) new Comparable[initialCapacity];
        size = 0;
    }

    /**
     * Creates a Heap from an initial set of values
     * 
     * !!! UPDATED in version 1.1 !!!
     * For this constructor, initialize the backing array to fit the passed in
     * data exactly.
     * Example:
     *   If an 5 elements are passed in, your backing array should be of size 6
     *   since the backing array is 1-indexed.
     *
     * When this constructors returns, the backing array should satisfy all
     * the properties of a heap
     *
     * You should implement this the way it was mentioned in lecture
     * The BuildHeap algorithm visualized on the following page is how it should
     * be implemented and is the same method that was taught in class
     * https://www.cs.usfca.edu/~galles/visualization/Heap.html
     *
     * @param values values to initialize the heap with
     *               T... values is the same as T[] values
     *               You may assume that none of the arguments passed in
     *               will be null
     */
    @SafeVarargs
    public MinHeap(T... values) {
        this(values.length + 1);
        int j = 0;
        for (int i = 1; i < values.length + 1; i++) {
            backingArray[i] = values[j];
            j++;
            size++;
        }
        for (int t = size; t > 1; t--) {
            upheap(backingArray, t);
        }
    }

    @Override
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("item is null");
        }
        if (backingArray.length - 1 == size) {
            T[] temp = (T[]) new Comparable[backingArray.length * 2];
            for (int i = 0; i < backingArray.length; i++) {
                temp[i] = backingArray[i];
            }
            backingArray = temp;
        }
        backingArray[++size] = data;
        upheap(backingArray, size);
    }

    /**
     * Method used in for add pushes items up until in proper placement
     * @param arr the array being passed in
     * @param i the size of the array
     * @return Properly ordered array
     */
    private T[] upheap(T[] arr, int i) {
        while (i / 2 >= 1 && arr[i / 2].compareTo(arr[i]) > 0) {
            T temp = arr[i / 2];
            arr[i / 2] = arr[i];
            arr[i] = temp;
            i = i / 2;
        }
        return arr;
    }

    @Override
    public T remove() {
        if (size == 1) {
            T temp = backingArray[1];
            backingArray[1] = null;
            size--;
            return temp;
        }
        T temp = backingArray[1];
        backingArray[1] = backingArray[size];
        backingArray[size] = null;
        size--;
        downheap(1);
        return temp;
    }

    /**
     * Progressess down the array putting items in proper spots
     * @param i the min array spot of the minheap
     */
    private void downheap(int i) {
        if (backingArray[i] == null) {
            throw new NoSuchElementException("Element not there");
        }
        boolean end = false;
        while ((2 * i) <= size && !end) {
            if ((2 * i) + 1 <= size) {
                if (backingArray[(2 * i) + 1]
                        .compareTo(backingArray[2 * i]) < 0) {
                    T temp = backingArray[(2 * i) + 1];
                    backingArray[(2 * i) + 1] = backingArray[i];
                    backingArray[i] = temp;
                    i = (2 * i) + 1;
                } else {
                    T temp = backingArray[2 * i];
                    backingArray[2 * i] = backingArray[i];
                    backingArray[i] = temp;
                    i = 2 * i;
                }
            } else {
                if (backingArray[i].compareTo(backingArray[2 * i]) > 0) {
                    T temp = backingArray[2 * i];
                    backingArray[2 * i] = backingArray[i];
                    backingArray[i] = temp;
                    i = 2 * i;
                }
                end = true;
            }
        }
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        size = 0;
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
    }

    // Do NOT edit or use this method in your code
    @Override
    public Comparable[] getBackingArray() {
        return backingArray;
    }
}
