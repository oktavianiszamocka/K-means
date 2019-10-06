import java.io.*;
import java.util.*;

public class Main {
    final static int numberOfCluster = 2;
    static List <Iris> listOfIris;
    static List<Cluster> listOfCluster;
    static List<Centroid> listOfCentroid;

    public static void main(String[] args) {
        listOfIris = new ArrayList<>();
        listOfCluster = new ArrayList<>();
        listOfCentroid = new ArrayList<>();
        File file = new File("src/train.txt");
        readFile(file);
        setListOfCluster();
        assignRandomtoCluster();

        System.out.println("---random clustering");
        for(Cluster cluster : listOfCluster){
            System.out.println(cluster);
            showPercentage(cluster);
            System.out.println();
        }
       learning();

    }

    public static void setListOfCluster(){
        for(int i = 0; i < numberOfCluster; i++){
            listOfCluster.add(new Cluster(i+1));
        }
    }

    public static void assignRandomtoCluster(){
        Random random = new Random();
        for(Iris iris : listOfIris){
            int index = random.nextInt(numberOfCluster) +1;
           // int index = (int)( Math.random() * numberOfCluster )+ 1;
            listOfCluster.get(index-1).getListOfIris().add(iris);
            /*
            switch (index){
                case 1 :
                    listOfCluster.get(0).getListOfIris().add(iris);
                    break;
                case 2 :
                    listOfCluster.get(1).getListOfIris().add(iris);
                    break;
                default:
                    listOfCluster.get(2).getListOfIris().add(iris);
                    break;
            }
*/
        }
    }

    public  static void readFile(File file){
        try {
            BufferedReader input = new BufferedReader(new FileReader(file));
            String line;
            while( (line = input.readLine()) != null) {
                inputParse(line);
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }



    public static void inputParse(String line) {
        List <Double> listofValue = null;
        String [] output = line.split(",");
        String name = null;
        listofValue = new ArrayList<>();
        for(int i = 0; i< output.length; i++) {
            if(!output[i].contains("Iris")) {
                listofValue.add(new Double(Double.parseDouble(output[i])));
            }
            else {
                name = output[i];
            }
        }
        listOfIris.add(new Iris(listofValue, name));
    }


    public static double countDistance(Iris iris, Centroid centroid){
        double sum = 0;
        for(int i = 0; i < iris.getData().size(); i++){
            double vector = Math.pow ((iris.getData().get(i) - centroid.getVectors().get(i)), 2);
            sum += vector;
        }
        return sum;
    }

   public static Centroid countCentroid(int index, Cluster cluster){
        List<Double>  vector = new ArrayList<>();
        Centroid centroid;
        int sizeOfCluster = cluster.getListOfIris().size();

           for(int j = 0 ; j < listOfIris.get(0).getData().size(); j++){
               //j = index of vector in iris
               double sum = 0;

               for(int i = 0; i< sizeOfCluster; i++){
                   //open list of iris in cluster
                   // i = index vector list iris in cluster
                   sum += cluster.getListOfIris().get(i).getData().get(j);
               }
               sum  /= sizeOfCluster;
               vector.add(sum);
           }
           centroid = new Centroid(index, vector);
          // System.out.println(centroid);
           return centroid;

   }

   public static void learning(){
        //make temporary cluster
        List<Cluster> tmpCluster = new ArrayList<>();
        for(int i = 0; i < numberOfCluster; i++){
            tmpCluster.add(new Cluster(i+1));
        }

        //asigning centroid from the first cycle cluster
        for(int i = 0; i < numberOfCluster; i++){
            listOfCentroid.add(countCentroid(i+1, listOfCluster.get(i)));
        }
        System.out.println(listOfCentroid);

        Map<Integer, Double> listofDistance ;
        boolean isLearning = true;

        learningLoop:
        while(isLearning){
            double sum =0;
            System.out.println("---------------");
            for(int i = 0; i < listOfIris.size(); i++){
                listofDistance = new HashMap<>();
                for(int j = 0; j < listOfCentroid.size(); j++){
                    //counting distance from all iris to each centroid
                    double distance = countDistance(listOfIris.get(i), listOfCentroid.get(j));

                    listofDistance.put(j, distance);
                }

                //get smallest distance then assign to temporary cluster according index
                int centroidindex = getSmallestDistance(listofDistance);
                sum += listofDistance.get(centroidindex);
                tmpCluster.get(centroidindex).getListOfIris().add(listOfIris.get(i));

            }
            //printing sum between distance each centroid
            System.out.println("Sum : " + sum);

            //printing the temporary cluster
            for(int i= 0; i < tmpCluster.size(); i++){
               System.out.println(tmpCluster.get(i));
               //get the purity
                showPercentage(tmpCluster.get(i) );
                System.out.println();
            }

            //check whether temporary cluster same like previous cluster
           isLearning = compareCluster(tmpCluster);

            if(isLearning == false){
                break learningLoop;
            }
            //set centroid list to null
            listOfCentroid = new ArrayList<>();

            //calculate the centroid again
            for(int c = 0; c < listOfCluster.size(); c++){

                listOfCentroid.add(countCentroid(c+1, listOfCluster.get(c)));

            }

            //print the new centroid
            for(Centroid centroid : listOfCentroid){
                System.out.println(centroid);
            }
            //set tmpcluster to null
            for(Cluster cluster : tmpCluster){
                cluster.setnewList();
            }
        }

   }

   public static int getSmallestDistance( Map<Integer, Double> listofDistance){

        Integer index = listofDistance.entrySet().stream()
                .min(Comparator.comparingDouble(e -> e.getValue()))
                .get()
                .getKey();
        return index;

   }

   public static boolean compareCluster(List<Cluster> list){
        boolean running = true;

       outerloop:

        for(int i = 0; i < listOfCluster.size(); i++){
            //if listofcluster not same with the temporary cluster then change listofcluster to temporary one and keep the loop
            if(!listOfCluster.get(i).equals(list.get(i))){
                listOfCluster.clear();
                listOfCluster.addAll(list);
                running = true;
                break outerloop;

            } else{
                //if same then stop loop
                running = false;
            }

        }
        return running;
   }

   public static void showPercentage(Cluster cluster){
        double irisSentosa = 0;
        double irisverColor = 0;
        double irisVirginica = 0;


        for(Iris iris : cluster.getListOfIris()){
            if(iris.getName().equals("Iris-setosa")){
                irisSentosa++;
            } else if (iris.getName().equals("Iris-versicolor")){
                irisverColor++;
            } else {
                irisVirginica++;
            }
        }

        irisSentosa = irisSentosa / cluster.getListOfIris().size() * 100;
        irisverColor = irisverColor  / cluster.getListOfIris().size() * 100;
        irisVirginica = irisVirginica  / cluster.getListOfIris().size() * 100;

        System.out.println("Iris Sentosa : " + irisSentosa + "%");
       System.out.println("Iris Versi Color : " + irisverColor + "%");
       System.out.println("Iris Virginica : " + irisVirginica + "%");
   }
}
