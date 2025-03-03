class PatientMaxHeapOriginal {
    protected Patient[] heap;
    protected int size;
    protected int capacity;

    public PatientMaxHeapOriginal(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        this.heap = new Patient[capacity];
    }

    // Returns the index of the parent node
    private int parent(int i) {
        return (i-1)/2;
    }

    // Returns the index of the left child
    private int leftChild(int i) {
        return (2*i) + 1;
    }

    // Returns the index of the right child
    private int rightChild(int i) {
      return (2*i) + 2;
    }

    // Checks if the heap is empty
    public boolean isEmpty() {
        return heap.length == 0;
    }

    // Returns the maximum patient without removing it
    public Patient getMax() {
       return heap[0];
    }

    // TODO: Students implement this method
    // Inserts a new patient into the heap
    public void insert(Patient patient) {
        // Your code here
    }

    // TODO: Students implement this method
    // Removes and returns the maximum patient
    public Patient extractMax() {
        // Your code here
        return null;
    }

    // TODO: Students implement this method
    // Sifts a patient down from index i to maintain the max-heap property
    private void siftDown(int i) {
        // Your code here
    }

    // TODO: Students implement this method
    // Sifts a patient up from index i to maintain the max-heap property
    private void siftUp(int i) {
        // Your code here
    }
}