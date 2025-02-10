import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public  class ReadService {
    String path;
    public ReadService() {

    }

    public List<Attribute> ReadData() throws IOException {
        List<Attribute> Attributes = new ArrayList<>();
        BufferedReader read = new BufferedReader(new FileReader(path));
        String line;
        String type_of_class = null;
        while ((line = read.readLine()) != null) {
            String[] parts = line.split(",");
            List<Double> points = new ArrayList<>();
            for (int i = 0; i < parts.length; i++) {
                if (i == parts.length - 1) {
                    type_of_class = parts[i];
                } else {
                    points.add(Double.valueOf(parts[i]));
                }
            }
            Attribute type = new Attribute(points, type_of_class);
            Attributes.add(type);
                /*for (int i=0;i<Attributes.size();i++)
                {
                    System.out.println(Attributes.get(i).getPoints().get(0));
                }*/

        }
        return Attributes;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}