package agstsp;

import java.util.Random;

public class AGSTSP 
{
    public static void main(String[] args) 
    {
        Fitness.init("gr5.txt");
        //23898932
        Algoritmo.rnd = new Random(23898932);
        //Poblacion pob = new Poblacion(70, true);
        Poblacion pob = new Poblacion(100, true);
        Genotipo mejor = pob.getFittest();
        int contadorGen;
        int numGen = -1;
        
        System.out.print("c(" +mejor.getFitness());
        for( contadorGen = 0; contadorGen < Algoritmo.numGeneraciones; contadorGen++ ) {
            if(pob.getFittest().getFitness() < mejor.getFitness()) {
                mejor = pob.getFittest().getCopia();
                numGen = contadorGen;
            }
            //System.out.println("Generación: " +contadorGen +"; Fitness del fittest: " +pob.getFittest().getFitness());
            //System.out.print(", " +pob.getFittest().getFitness());
            pob = Algoritmo.evoluciona(pob);
        }
        System.out.println(")");
        int [] var = mejor.interpretaCamino();
        System.out.println("Mejor Generacion: " + numGen  +"; Fitness del mejor Camino: " +mejor.getFitness());
        for(int i: var)
            System.out.print(i + " ");
        System.out.println();
        //System.out.println("El mejor camino debe ser  0,12,1,14,8,4,6,2,11,13,9,7,5,3,10");
        //int[] arr = {0,12,1,14,8,4,6,2,11,13,9,7,5,3,10};
        //System.out.println("... y su fitness es: " +Fitness.evaluaCamino(arr));
    }
}
