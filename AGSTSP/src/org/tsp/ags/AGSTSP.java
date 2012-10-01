package org.tsp.ags;

import java.util.Random;

/**
 * Representa la clase principal del Algoritmo
 * Genético Simple que resuelve el Problema del Agente Viajero
 */
public class AGSTSP 
{
    
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
        //Poblacion pob = new Poblacion(70, true);
        Poblacion pob = new Poblacion(Algoritmo.tamPoblacion, true);
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
    }
}
