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

    // Returns the minimum patient without removing it
    public Patient getMin() {
      return heap[0];
    }

    // Inserts a new patient into the heap
    public void insert(Patient patient) {
        heap[size] = patient;
        if(size != 0){
            siftUp(size);
        }
        size++;
    }


    // Removes and returns the minimum patient
    public Patient extractMin() {
        Patient min = getMin();
        System.out.println("Extracting: " + min.getName());
        heap[0] = null;
        if(size != 1) {
            heap[0] = heap[size-1];
            heap[size-1] = null;
            siftDown(0);
        }
        size--;
        System.out.println(heap);
        return min;
    }

    // Sifts a patient down from index i to maintain the min-heap property
    private void siftDown(int i) {
        int smallerIndex = 0;
        if(heap[leftChild(i)] != null && heap[rightChild(i)] != null) {
            if (heap[leftChild(i)].compareTo(heap[rightChild(i)]) < 0) { // if left child is smaller than right
                smallerIndex = leftChild(i);
            } else {
                smallerIndex = rightChild(i);
            }

            if (heap[smallerIndex].compareTo(heap[i]) < 0) { // if child is smaller than parent
                // float parent down
                Patient current = heap[i];
                heap[i] = heap[smallerIndex];
                heap[smallerIndex] = current;
                siftDown(smallerIndex);
            }
        }

    }

    // Sifts a patient up from index i to maintain the min-heap property
    private void siftUp(int i) {
        int index = i;
        while(heap[parent(index)].compareTo(heap[index]) > 0){ // check greater
            // if parent is greater, float up
            Patient parent = heap[parent(index)];
            heap[parent(index)] = heap[index];
            heap[index] = parent;
            index = parent(index);
        }
    }
}