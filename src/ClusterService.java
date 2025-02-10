import java.text.DecimalFormat;
import java.util.*;

public  class ClusterService {
    private int k;
    private boolean Compilable = true;
    private List<Attribute> Attributes;
    private List<Cluster> Clusters = new ArrayList<>();
    private List<Double> oldSums = new ArrayList<>();
    private List<Double> sums = new ArrayList<>();
    private Map<Integer, double[]> DistancesToCluster = new HashMap<>();
    private final DecimalFormat numberFormat = new DecimalFormat("#.00");

    public ClusterService() {

    }

    public void AssignRandomCluster() {
        Random random = new Random();
        for (Attribute i : Attributes) i.setAssignedCluster(random.nextInt(k) + 1);
    }

    public void Iterate() {
        calculateDistanceForEachCluster();
        ReAssign();
        iterateClusterPoints();
        printSums();
        PrintPurity();
        updateCompilableStatus();
    }

    private void updateCompilableStatus() {
        if (oldSums.equals(sums)) {
            Compilable = false;
        } else {
            oldSums = new ArrayList<>(sums);
        }
    }

    private void ReAssign() {
        int numPositions = 0;
        oldSums = new ArrayList<>(sums);
        sums.clear();
        for (double[] values : DistancesToCluster.values()) {
            numPositions = Math.max(numPositions, values.length);
        }
        for (int i = 0; i < numPositions; i++) {
            double minValue = Double.MAX_VALUE;
            Integer minKey = null;
            for (Map.Entry<Integer, double[]> entry : DistancesToCluster.entrySet()) {
                double[] values = entry.getValue();
                if (i < values.length && values[i] < minValue) {
                    minValue = values[i];
                    minKey = entry.getKey();
                }
            }
            Attributes.get(i).setAssignedCluster(minKey + 1);
        }
    }

    private void printSums() {
        for (int i = 0; i < Clusters.size(); i++) {
            System.out.println("Cluster " + i);
            System.out.println(numberFormat.format(sums.get(i)));
        }
    }

    private void PrintPurity() {
        for (Cluster cluster : Clusters) {
            Map<String, Integer> labelCounts = new HashMap<>();
            int totalLabels = 0;

            for (Attribute attribute : Attributes) {
                if (attribute.getAssignedCluster() == cluster.getNumber()) {
                    String label = attribute.getType();
                    totalLabels++;
                    labelCounts.put(label, labelCounts.getOrDefault(label, 0) + 1);
                }
            }

            System.out.println("Cluster " + cluster.getNumber() + ":");
            for (Map.Entry<String, Integer> entry : labelCounts.entrySet()) {
                String label = entry.getKey();
                int count = entry.getValue();
                double percentage = (double) count / totalLabels * 100;
                System.out.println(label + ": " + numberFormat.format(percentage) + "%");
            }
        }
    }

    private void iterateClusterPoints() {
        double[][] toGet = new double[Attributes.size()][Attributes.get(1).getPoints().size()];

        for (Cluster i : Clusters) {
            double sum = 0;
            i.getPoints().clear();
            int assigned = 0;
            for (int j = 0; j < Attributes.size(); j++) {
                if (Attributes.get(j).getAssignedCluster() == i.getNumber()) {
                    assigned++;
                    for (int lix = 0; lix < Attributes.get(j).getPoints().size(); lix++) {
                        toGet[j][lix] = Attributes.get(j).getPoints().get(lix);
                    }
                }
            }
            for (int k = 0; k < Attributes.get(0).getPoints().size(); k++) {
                double point = 0.0;
                for (int j = 0; j < Attributes.size(); j++) {
                    if (Attributes.get(j).getAssignedCluster() == i.getNumber()) {
                        point += toGet[j][k];
                    }
                }
                sum += point;
                point = point / assigned;
                i.getPoints().add(point);
            }
            sums.add(sum);
        }
    }

    private void calculateDistanceForEachCluster() {
        for (int i = 0; i < Clusters.size(); i++) {
            List<Double> clusterPoints = Clusters.get(i).getPoints();
            double[] distances = new double[Attributes.size()];
            for (int j = 0; j < Attributes.size(); j++) {
                Attribute attribute = Attributes.get(j);
                List<Double> attributePoints = attribute.getPoints();
                distances[j] = calculateDistance(attributePoints, clusterPoints);
            }
            DistancesToCluster.put(i, distances);
        }
    }

    private double calculateDistance(List<Double> points1, List<Double> points2) {
        if (points1.size() != points2.size()) {
            throw new IllegalArgumentException("Dimensions are not equal");
        }
        double sumOfSquares = 0;
        for (int i = 0; i < points1.size(); i++) {
            double diff = points1.get(i) - points2.get(i);
            sumOfSquares += Math.pow(diff, 2);
        }
        return Math.sqrt(sumOfSquares);
    }

    public void GenerateClusters() {
        double[][] toGet = new double[Attributes.size()][Attributes.get(1).getPoints().size()];
        AssignRandomCluster();
        for (int i = 1; i <= k; i++) {
            int assigned = 0;
            Cluster cluster = new Cluster(i);
            for (int j = 0; j < Attributes.size(); j++) {
                if (Attributes.get(j).getAssignedCluster() == cluster.getNumber()) {
                    assigned++;
                    for (int lix = 0; lix < Attributes.get(j).getPoints().size(); lix++) {
                        toGet[j][lix] = Attributes.get(j).getPoints().get(lix);
                    }
                }
            }

            for (int k = 0; k < Attributes.get(0).getPoints().size(); k++) {
                double point = 0.0;
                for (int j = 0; j < Attributes.size(); j++) {
                    if (Attributes.get(j).getAssignedCluster() == cluster.getNumber()) {
                        point += toGet[j][k];
                    }
                }
                point = point / assigned;
                cluster.getPoints().add(point);

            }
            Clusters.add(cluster);
        }

    }

    public boolean isCompilable() {
        return Compilable;
    }

    public void setCompilable(boolean compilable) {
        Compilable = compilable;
    }

    public void setAttributes(List<Attribute> attributes) {
        Attributes = attributes;
    }

    public void setK(int k) {
        this.k = k;
    }
}