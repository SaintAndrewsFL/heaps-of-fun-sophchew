/**
 * Renamed original PatientMinHeap to avoid conflicts.
 * Students should implement this class by completing the TODO sections.
 */
class PatientMinHeapOriginal {
    protected Patient[] heap;
    protected int size;
    protected int capacity;

    public PatientMinHeapOriginal(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        this.heap = new Patient[capacity];
    }

    // TODO: Students implement this method
    // Returns the index of the parent node
    private int parent(int i) {
        return 0;
    }

    // TODO: Students implement this method
    // Returns the index of the left child
    private int leftChild(int i) {
        return 0;
    }

    // TODO: Students implement this method
    // Returns the index of the right child
    private int rightChild(int i) {
        return 0;
    }

    // TODO: Students implement this method
    // Checks if the heap is empty
    public boolean isEmpty() {
       return false;
    }

    // TODO: Students implement this method
    // Returns the minimum patient without removing it
    public Patient getMin() {
      return null;
    }

    // TODO: Students implement this method
    // Inserts a new patient into the heap
    public void insert(Patient patient) {
        // Your code here
    }

    // TODO: Students implement this method
    // Removes and returns the minimum patient
    public Patient extractMin() {
        // Your code here
        return null;
    }

    // TODO: Students implement this method
    // Sifts a patient down from index i to maintain the min-heap property
    private void siftDown(int i) {
        // Your code here
    }

    // TODO: Students implement this method
    // Sifts a patient up from index i to maintain the min-heap property
    private void siftUp(int i) {
        // Your code here
    }
}