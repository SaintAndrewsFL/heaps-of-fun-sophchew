/**
 * Patient class representing a hospital patient
 */
class Patient implements Comparable<Patient> {
    private String name;
    private int severityScore; // 1-10 (10 most severe)
    private int waitTime; // minutes

    public Patient(String name, int severityScore, int waitTime) {
        this.name = name;
        this.severityScore = severityScore;
        this.waitTime = waitTime;
    }

    public String getName() {
        return name;
    }

    public int getSeverityScore() {
        return severityScore;
    }

    public int getWaitTime() {
        return waitTime;
    }

    public void incrementWaitTime() {
        waitTime++;
    }

    // Default comparison by severity
    @Override
    public int compareTo(Patient other) {
        return Integer.compare(this.severityScore, other.severityScore);
    }

    @Override
    public String toString() {
        return name + " (Severity: " + severityScore + ", Wait Time: " + waitTime + " min)";
    }
}