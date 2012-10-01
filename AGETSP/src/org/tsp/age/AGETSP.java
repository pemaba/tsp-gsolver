package org.tsp.age;

import java.util.Random;

/**
 * Representa la clase principal del Algoritmo
 * Genético Simple con Modificaciones
 * para resolver el Problema del Agente Viajero
 */
public class AGETSP 
{
    /**
     * Entrada principal del AGE
     * @param args 
     */
    public static void main(String[] args) 
    {
        Algoritmo.rutaArchivoCiudades = "p01_d.txt";
        run();
    }
    
    public static void run()
    {
        
        Fitness.init();
        //23898932
        Algoritmo.rnd = new Random(23898932);
        Poblacion pob = new Poblacion(Algoritmo.tamPoblacion, true);
        Genotipo mejor = pob.getFittest();
        int contadorGen;
        int numGen = 1;
        
        for( contadorGen = 1; contadorGen <= Algoritmo.numGeneraciones; contadorGen++ ) {
            pob = Algoritmo.evoluciona(pob, mejor.getCopia());
            if(pob.getFittest().getFitness() < mejor.getFitness()) {
                mejor = pob.getFittest().getCopia();
                numGen = contadorGen;
            }
            System.out.println("Generación: " +contadorGen +"; Fitness del fittest: " +pob.getFittest().getFitness());
        }
        int [] var = mejor.getArreglo();
        System.out.println("Mejor Generacion: " + numGen  +"; Fitness del mejor Camino: " +mejor.getFitness());
        for(int i: var)
            System.out.print(i + " ");
        System.out.println();
        //System.out.println("El mejor camino debe ser 1,13,2,15,9,5,7,3,12,14,10,8,6,4,11");
        //int[] arr = {1,13,2,15,9,5,7,3,12,14,10,8,6,4,11};
        //System.out.println("... y su fitness es: " +Fitness.evaluaCamino(arr));        
    }
}
