import java.io.IOException;
import java.util.Scanner;

public  class kMeansController {
    private final ClusterService clusterService;
    private final ReadService readService;

    public kMeansController(ClusterService clusterService, ReadService readService) {
        this.clusterService = clusterService;
        this.readService = readService;
    }

    public void StartProgram() throws IOException {
        Scanner read = new Scanner(System.in);
        System.out.println("Please Write path to a file");
        String path = read.nextLine();
        readService.setPath(path);
        System.out.println("Please write k");
        int k = read.nextInt();
        clusterService.setK(k);
        clusterService.setAttributes(readService.ReadData());
        clusterService.GenerateClusters();
        while (clusterService.isCompilable())
        {
            clusterService.Iterate();
        }


    }

}