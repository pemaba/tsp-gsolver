package org.tsp.age;

import java.io.File;
import java.util.Scanner;

/**
 * Clase con métodos para calcular la bondad de un individuo 
 * usada en el Algoritmo Genético Simple Modificado
 */
public class Fitness 
{
    /**
     * Matriz que contiene el costo de ir de la ciudad i a la ciudad j.
     */
    private static double [][] distancias;

    /**
     * Dada una lista de ciudades obtiene el costo de recorrer dicho camino.
     * @param path Arreglo de enteros con las ciudades que componen el camino
     * @return El costo de recorrer el camino de manera circular.
     */
    public static double evaluaCamino(int[] path) 
    {
        double suma = 0;
        
        for (int i = 1; i < Algoritmo.numCiudades; i++) 
            suma += distEntre2(path[i],path[i-1]);
        suma += distEntre2(path[Algoritmo.numCiudades-1], path[0]);
        
        return suma;
    }
    
    /**
     * Dada una lista de ciudades obtiene el costo de recorrer dicho camino.
     * @param A Genotipo que es interpretado como camino
     * @return El costo de recorrer el camino de manera circular.
     */
    public static double evaluaCamino(Genotipo A) 
    {
       return evaluaCamino(A.getArreglo());
    }
    
    /**
     * Devuelve el costo de viajar de la ciudad A a la ciudad B, numeradas 
     * desde 1 hasta N
     * @param cdA Número de la ciudad A
     * @param cdB Número de la ciudad B
     * @return Un double con el costo de viajar de la ciudad A a la ciudad B
     */
    private static double distEntre2(int cdA, int cdB)
    {
        return distancias[cdA - 1][cdB - 1];
    }
    
    /**
     * Inicaliza la matriz de costos y el número de ciudades.
     * por medio de los datos otorgados en
     * en un archivo cuya ruta se otorga como parámetro.
     * @param filename Ruta del archivo con latos de inicio
     */
    public static void init()
    {
         try {
            Scanner s = new Scanner(new File(Algoritmo.rutaArchivoCiudades));
            Algoritmo.numCiudades = s.nextInt();
           
            Fitness.distancias = new double[Algoritmo.numCiudades][Algoritmo.numCiudades];
            
            for(int i=0; i<Algoritmo.numCiudades; i++)
                for(int j=0; j<Algoritmo.numCiudades; j++)
                    distancias[i][j] = s.nextDouble();

            System.out.println("NumCiudades = " +Algoritmo.numCiudades);
           
        }
        catch(Exception exc) {
            exc.getMessage();
        }
    }
}
