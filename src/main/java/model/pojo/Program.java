package model.pojo;

public class Program {
    String name;
    Double remainingCapacity = 0.0;
    Double totalCapacity = 0.0;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Double getRemainingCapacity() { return remainingCapacity;}
    public Double getTotalCapacity() { return totalCapacity; }

    public void addRemainingCapacity(Double capacity) { this.remainingCapacity = this.remainingCapacity + capacity; }

    public void addTotalCapacity(Double capacity) { this.totalCapacity = this.totalCapacity + capacity; }

    public Program(String name, Double remaining) {
        this.name = name;
        this.remainingCapacity = remaining;
    }
}
