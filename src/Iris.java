import java.util.List;
import java.util.Objects;

public class Iris {
    private List<Double> data;
    private String name;

    public Iris(List<Double> data, String name){
        this.data = data;
        this.name = name;
    }

    public List<Double> getData() {
        return data;
    }

    public void setData(List<Double> data) {
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "Iris{" +
                "data=" + data +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Iris)) return false;
        Iris iris = (Iris) o;
        return data.equals(iris.data) &&
                name.equals(iris.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data, name);
    }
}
