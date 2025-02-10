import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        ReadService readService = new ReadService();
        ClusterService clusterService = new ClusterService();
        kMeansController kMeansController = new kMeansController(clusterService,readService);
        kMeansController.StartProgram();
    }








}