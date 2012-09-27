
package agetsp;

import java.util.Random;

public class AGETSP {

     public static void main(String[] args) 
    {
        Fitness.init("p01_d.txt");
        //23898932
        Algoritmo.rnd = new Random();
        //Poblacion pob = new Poblacion(70, true);
        Poblacion pob = new Poblacion(100, true);
        Genotipo mejor = pob.getFittest();
        int contadorGen;
        int numGen = 1;
        
        //System.out.print("c(" +mejor.getFitness());
        for( contadorGen = 1; contadorGen <= Algoritmo.numGeneraciones; contadorGen++ ) {
            pob = Algoritmo.evoluciona(pob, mejor.getCopia());
            if(pob.getFittest().getFitness() < mejor.getFitness()) {
                mejor = pob.getFittest().getCopia();
                numGen = contadorGen;
            }
            System.out.println("Generación: " +contadorGen +"; Fitness del fittest: " +pob.getFittest().getFitness());
            //System.out.print(", " +pob.getFittest().getFitness());
        }
        int [] var = mejor.getArreglo();
        System.out.println("Mejor Generacion: " + numGen  +"; Fitness del mejor Camino: " +mejor.getFitness());
        for(int i: var)
            System.out.print(i + " ");
        System.out.println();
        System.out.println("El mejor camino debe ser 1,13,2,15,9,5,7,3,12,14,10,8,6,4,11");
        int[] arr = {1,13,2,15,9,5,7,3,12,14,10,8,6,4,11};
        System.out.println("... y su fitness es: " +Fitness.evaluaCamino(arr));
    }
}
