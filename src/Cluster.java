import java.util.ArrayList;
import java.util.List;

public class Cluster {
    int index;
    List<Iris> listOfIris;

    public Cluster(int index){
        this.index = index;
        listOfIris = new ArrayList<>();
    }
    public Cluster(int index, List<Iris> listOfIris){
        this.index = index;
        this.listOfIris = listOfIris;
    }

    public List<Iris> getListOfIris() {
        return listOfIris;
    }

    public void setListOfIris(List<Iris> listOfIris) {
        this.listOfIris = listOfIris;
    }
    public void setnewList(){
        listOfIris = new ArrayList<>();
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }



    @Override
    public String toString() {
        return "Cluster{" +
                "index=" + index +
                ", listOfIris=" + listOfIris +
                '}' ;
    }
}
