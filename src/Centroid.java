import java.util.List;

public class Centroid {
    int index;
    List<Double> vectors;

    public Centroid(int index, List<Double> vectors){
        this.index = index;
        this.vectors = vectors;
    }

    public List<Double> getVectors() {
        return vectors;
    }

    public void setVectors(List<Double> vectors) {
        this.vectors = vectors;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return "Centroid{" +
                "index=" + index +
                ", vectors=" + vectors +
                '}' + "\n";
    }
}
