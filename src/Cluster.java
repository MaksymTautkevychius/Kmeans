import java.util.ArrayList;
import java.util.List;

public  class Cluster {
    private int number;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Cluster(int number) {
        this.number = number;
    }

    private List<Double> Points = new ArrayList<>();

    public List<Double> getPoints() {
        return Points;
    }

    public void setPoints(List<Double> points) {
        Points = points;
    }
    public void printPoints() {
        System.out.println("Points in Cluster " + number + ":");
        for (Double point : Points) {
            System.out.println(point);
        }
    }
}