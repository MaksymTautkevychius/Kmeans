import java.util.List;

public  class Attribute {
    private List<Double> Points;
    private String Type;
    private int AssignedCluster;

    public Attribute(List<Double> points, String type) {
        Points = points;
        Type = type;
    }

    public int getAssignedCluster() {
        return AssignedCluster;
    }

    public void setAssignedCluster(int assignedCluster) {
        AssignedCluster = assignedCluster;
    }

    public List<Double> getPoints() {
        return Points;
    }

    public void setPoints(List<Double> points) {
        Points = points;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }
}