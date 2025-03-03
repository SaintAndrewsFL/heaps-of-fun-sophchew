import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

/**
 * HospitalHeapVisualizer - GUI application for visualizing MinHeap and MaxHeap operations
 * This class provides a graphical interface for students to test their heap implementations
 * and see the operations in action.
 */
public class HospitalHeapVisualizer extends JFrame {
     private JPanel mainPanel;
    private HeapPanel heapPanel;
    private JPanel controlPanel;

    private JComboBox<String> heapTypeComboBox;
    private JTextField patientNameField;
    private JSpinner severitySpinner;
    private JButton insertButton;
    private JButton extractButton;
    private JButton randomPatientButton;
    private JButton clearButton;
    private JTextArea operationLogArea;

    private PatientMinHeap minHeap;
    private PatientMaxHeap maxHeap;

    private enum HeapType { MIN_HEAP, MAX_HEAP }
    private HeapType currentHeapType = HeapType.MIN_HEAP;

    private static final int MAX_HEAP_SIZE = 31; // Enough for a complete binary tree with 5 levels
    private static final String[] FIRST_NAMES = {"John", "Mary", "James", "Patricia", "Robert", "Jennifer", "Michael", "Linda", "William", "Elizabeth"};
    private static final String[] LAST_NAMES = {"Smith", "Johnson", "Brown", "Jones", "Garcia", "Miller", "Davis", "Rodriguez", "Martinez", "Wilson"};

    public HospitalHeapVisualizer() {
        setTitle("Hospital Heap Visualizer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);

        // Initialize heaps
        minHeap = new PatientMinHeap(MAX_HEAP_SIZE);
        maxHeap = new PatientMaxHeap(MAX_HEAP_SIZE);

        // Create main components
        mainPanel = new JPanel(new BorderLayout());
        heapPanel = new HeapPanel();
        controlPanel = createControlPanel();

        // Add components to main panel
        mainPanel.add(heapPanel, BorderLayout.CENTER);
        mainPanel.add(controlPanel, BorderLayout.EAST);

        // Set content pane
        setContentPane(mainPanel);

        // Set up initial state
        updateHeapView();
    }

    private JPanel createControlPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setPreferredSize(new Dimension(300, 600));

        // Heap Type Selection - Fix the layout
        JPanel heapTypePanel = new JPanel();
        heapTypePanel.setLayout(new BoxLayout(heapTypePanel, BoxLayout.Y_AXIS));
        heapTypePanel.setBorder(BorderFactory.createTitledBorder("Heap Type"));

        JPanel comboBoxPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        heapTypeComboBox = new JComboBox<>(new String[]{"Min Heap (Routine Care)", "Max Heap (Emergency Care)"});
        heapTypeComboBox.setMaximumSize(new Dimension(280, 25));
        heapTypeComboBox.addActionListener(e -> {
            currentHeapType = heapTypeComboBox.getSelectedIndex() == 0 ? HeapType.MIN_HEAP : HeapType.MAX_HEAP;
            updateHeapView();
        });
        comboBoxPanel.add(heapTypeComboBox);
        heapTypePanel.add(comboBoxPanel);
        panel.add(heapTypePanel);
        panel.add(Box.createVerticalStrut(10));

        // Patient Information Panel
        JPanel patientInfoPanel = new JPanel();
        patientInfoPanel.setLayout(new BoxLayout(patientInfoPanel, BoxLayout.Y_AXIS));
        patientInfoPanel.setBorder(BorderFactory.createTitledBorder("Patient Information"));

        // Name field
        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        namePanel.add(new JLabel("Name:"));
        patientNameField = new JTextField(15);
        namePanel.add(patientNameField);
        patientInfoPanel.add(namePanel);

        // Severity spinner
        JPanel severityPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        severityPanel.add(new JLabel("Severity (1-10):"));
        SpinnerModel severityModel = new SpinnerNumberModel(5, 1, 10, 1);
        severitySpinner = new JSpinner(severityModel);
        severityPanel.add(severitySpinner);
        patientInfoPanel.add(severityPanel);

        panel.add(patientInfoPanel);
        panel.add(Box.createVerticalStrut(10));

        // Operation Buttons
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 5, 5));

        insertButton = new JButton("Insert Patient");
        insertButton.addActionListener(e -> handleInsertPatient());

        extractButton = new JButton("Extract Patient");
        extractButton.addActionListener(e -> handleExtractPatient());

        randomPatientButton = new JButton("Add Random Patient");
        randomPatientButton.addActionListener(e -> handleAddRandomPatient());

        clearButton = new JButton("Clear Heap");
        clearButton.addActionListener(e -> handleClearHeap());

        buttonPanel.add(insertButton);
        buttonPanel.add(extractButton);
        buttonPanel.add(randomPatientButton);
        buttonPanel.add(clearButton);

        panel.add(buttonPanel);
        panel.add(Box.createVerticalStrut(10));

        // Operation Log
        JPanel logPanel = new JPanel(new BorderLayout());
        logPanel.setBorder(BorderFactory.createTitledBorder("Operation Log"));
        operationLogArea = new JTextArea(10, 20);
        operationLogArea.setEditable(false);
        JScrollPane logScrollPane = new JScrollPane(operationLogArea);
        logPanel.add(logScrollPane, BorderLayout.CENTER);

        panel.add(logPanel);

        return panel;
    }

    private void handleInsertPatient() {
        try {
            String name = patientNameField.getText().trim();
            if (name.isEmpty()) {
                name = generateRandomName();
            }

            int severity = (Integer) severitySpinner.getValue();
            Patient patient = new Patient(name, severity, 0);

            if (currentHeapType == HeapType.MIN_HEAP) {
                minHeap.insert(patient);
                logOperation("Inserted: " + patient + " into MinHeap");
            } else {
                maxHeap.insert(patient);
                logOperation("Inserted: " + patient + " into MaxHeap");
            }

            updateHeapView();
        } catch (Exception ex) {
            logOperation("Error: " + ex.getMessage());
        }
    }

    private void handleExtractPatient() {
        try {
            Patient extracted = null;

            if (currentHeapType == HeapType.MIN_HEAP) {
                extracted = minHeap.extractMin();
                logOperation("Extracted: " + extracted + " from MinHeap");
            } else {
                extracted = maxHeap.extractMax();
                logOperation("Extracted: " + extracted + " from MaxHeap");
            }

            updateHeapView();
        } catch (Exception ex) {
            logOperation("Error: " + ex.getMessage());
        }
    }

    private void handleAddRandomPatient() {
        String name = generateRandomName();
        int severity = new Random().nextInt(10) + 1; // 1-10

        Patient patient = new Patient(name, severity, 0);

        if (currentHeapType == HeapType.MIN_HEAP) {
            minHeap.insert(patient);
            logOperation("Inserted random: " + patient + " into MinHeap");
        } else {
            maxHeap.insert(patient);
            logOperation("Inserted random: " + patient + " into MaxHeap");
        }

        updateHeapView();
    }

    private void handleClearHeap() {
        if (currentHeapType == HeapType.MIN_HEAP) {
            minHeap = new PatientMinHeap(MAX_HEAP_SIZE);
            logOperation("MinHeap cleared");
        } else {
            maxHeap = new PatientMaxHeap(MAX_HEAP_SIZE);
            logOperation("MaxHeap cleared");
        }

        updateHeapView();
    }

    private String generateRandomName() {
        Random random = new Random();
        String firstName = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
        String lastName = LAST_NAMES[random.nextInt(LAST_NAMES.length)];
        return lastName + ", " + firstName;
    }

    private void logOperation(String message) {
        operationLogArea.append(message + "\n");
        // Scroll to the bottom
        operationLogArea.setCaretPosition(operationLogArea.getDocument().getLength());
    }

    private void updateHeapView() {
        heapPanel.repaint();
    }

    /**
     * Custom panel for visualizing the heap as a binary tree
     */
    private class HeapPanel extends JPanel {
        private static final int NODE_RADIUS = 30;
        private static final int LEVEL_HEIGHT = 80;
        private Font nodeFont = new Font("Arial", Font.BOLD, 12);
        private Font severityFont = new Font("Arial", Font.BOLD, 14);

        public HeapPanel() {
            setBackground(Color.WHITE);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Get the current heap
            Object[] heap;
            int size;
            String heapTypeText;

            if (currentHeapType == HeapType.MIN_HEAP) {
                heap = minHeap.getHeapArray();
                size = minHeap.getSize();
                heapTypeText = "MinHeap (Routine Care - Lower Severity First)";
            } else {
                heap = maxHeap.getHeapArray();
                size = maxHeap.getSize();
                heapTypeText = "MaxHeap (Emergency Care - Higher Severity First)";
            }

            // Draw heap type title
            g2d.setFont(new Font("Arial", Font.BOLD, 18));
            g2d.drawString(heapTypeText, 20, 30);

            if (size == 0) {
                g2d.setFont(new Font("Arial", Font.ITALIC, 16));
                g2d.drawString("Heap is empty. Add patients using the controls on the right.",
                        getWidth() / 2 - 200, getHeight() / 2);
                return;
            }

            // Calculate root position
            int rootX = getWidth() / 2;
            int rootY = 60;

            // Draw the heap recursively
            drawHeapNode(g2d, (Patient[])heap, 0, rootX, rootY, getWidth() / 4, 0);
        }

        private void drawHeapNode(Graphics2D g2d, Patient[] heap, int index, int x, int y,
                                  int horizontalOffset, int level) {
            if (index >= heap.length || heap[index] == null) {
                return;
            }

            Patient patient = heap[index];

            // Draw connections to children first (so they appear behind nodes)
            int leftChildIndex = 2 * index + 1;
            int rightChildIndex = 2 * index + 2;

            if (leftChildIndex < heap.length && heap[leftChildIndex] != null) {
                int childX = x - horizontalOffset;
                int childY = y + LEVEL_HEIGHT;
                g2d.drawLine(x, y + NODE_RADIUS, childX, childY - NODE_RADIUS);
            }

            if (rightChildIndex < heap.length && heap[rightChildIndex] != null) {
                int childX = x + horizontalOffset;
                int childY = y + LEVEL_HEIGHT;
                g2d.drawLine(x, y + NODE_RADIUS, childX, childY - NODE_RADIUS);
            }

            // Determine node color based on severity
            Color nodeColor;
            if (patient.getSeverityScore() <= 3) {
                nodeColor = new Color(144, 238, 144); // Light green for low severity
            } else if (patient.getSeverityScore() <= 7) {
                nodeColor = new Color(255, 255, 0);   // Yellow for medium severity
            } else {
                nodeColor = new Color(255, 99, 71);   // Tomato red for high severity
            }

            // Draw the node circle
            g2d.setColor(nodeColor);
            g2d.fillOval(x - NODE_RADIUS, y - NODE_RADIUS, 2 * NODE_RADIUS, 2 * NODE_RADIUS);
            g2d.setColor(Color.BLACK);
            g2d.drawOval(x - NODE_RADIUS, y - NODE_RADIUS, 2 * NODE_RADIUS, 2 * NODE_RADIUS);

            // Draw severity score
            g2d.setFont(severityFont);
            String severityText = Integer.toString(patient.getSeverityScore());
            FontMetrics severityMetrics = g2d.getFontMetrics(severityFont);
            int severityWidth = severityMetrics.stringWidth(severityText);
            g2d.drawString(severityText, x - severityWidth / 2, y + 5);

            // Draw patient name below node
            g2d.setFont(nodeFont);
            String displayName = patient.getName();
            if (displayName.length() > 15) {
                displayName = displayName.substring(0, 12) + "...";
            }
            FontMetrics metrics = g2d.getFontMetrics(nodeFont);
            int nameWidth = metrics.stringWidth(displayName);
            g2d.drawString(displayName, x - nameWidth / 2, y + NODE_RADIUS + 15);

            // Recursively draw children with reduced horizontal offset
            if (leftChildIndex < heap.length && heap[leftChildIndex] != null) {
                drawHeapNode(g2d, heap, leftChildIndex, x - horizontalOffset, y + LEVEL_HEIGHT,
                        horizontalOffset / 2, level + 1);
            }

            if (rightChildIndex < heap.length && heap[rightChildIndex] != null) {
                drawHeapNode(g2d, heap, rightChildIndex, x + horizontalOffset, y + LEVEL_HEIGHT,
                        horizontalOffset / 2, level + 1);
            }
        }
    }

    /**
     * Extensions to the MinHeap and MaxHeap classes to provide access to internal data
     * for visualization purposes.
     */
    public static class PatientMinHeap extends PatientMinHeapOriginal {
        public PatientMinHeap(int capacity) {
            super(capacity);
        }

        public Patient[] getHeapArray() {
            return heap;
        }

        public int getSize() {
            return size;
        }
    }

    public static class PatientMaxHeap extends PatientMaxHeapOriginal {
        public PatientMaxHeap(int capacity) {
            super(capacity);
        }

        public Patient[] getHeapArray() {
            return heap;
        }

        public int getSize() {
            return size;
        }
    }

    /**
     * Entry point for the application
     */
    public static void main(String[] args) {
        // Set look and feel to the system look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create and display the form
        SwingUtilities.invokeLater(() -> {
            HospitalHeapVisualizer visualizer = new HospitalHeapVisualizer();
            visualizer.setVisible(true);
        });
    }
}
