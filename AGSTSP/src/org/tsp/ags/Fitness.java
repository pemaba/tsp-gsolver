package org.tsp.ags;

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
    private static int [][] distancias;

    /**
     * Dada una lista de ciudades obtiene el costo de recorrer dicho camino.
     * @param path Arreglo de enteros con las ciudades que componen el camino
     * @return El costo de recorrer el camino de manera circular.
     */    
    public static double evaluaCamino(int[] path)
    {
        long suma = 0;
        int[] camino = path;
        boolean[] visitados = new boolean[Algoritmo.numCiudades];
        for(int i=0; i<Algoritmo.numCiudades; i++)
            visitados[i] = false;
        int resto = Algoritmo.numCiudades;
        
        if(camino[0] >= Algoritmo.numCiudades) { 
            return resto*100000;
        }
        else {
            visitados[camino[0]] = true;
            resto--;
        }
        for (int i = 1; i<Algoritmo.numCiudades; i++){
            if(camino[i]>=Algoritmo.numCiudades || camino[i-1] >= Algoritmo.numCiudades){
                //return (double)suma + (double)1/resto;
                return resto*100000;
            }
            else {
                if(visitados[camino[i]] == false) {
                    suma += distEntre2(camino[i], camino[i-1]);
                    visitados[camino[i]] = true;
                    resto--;
                }
                else {
                    //return suma + 200000;
                    return resto*100000;
                }
            }
        }
        if(resto!=0)
            suma = 50000 + suma;
        else {
            //para que sea cerrado
            suma += distEntre2(camino[Algoritmo.numCiudades-1], camino[0]);
        }
        
        return suma;
    }
    
    public static double evaluaCamino(Genotipo A)
    {        
        return evaluaCamino(A.interpretaCamino());
    }
    
    private static int distEntre2(int cdA, int cdB)
    {
        return distancias[cdA][cdB];
    }
    
    public static void init()
    {
        try {
            Scanner s = new Scanner(new File(Algoritmo.rutaArchivoCiudades));
            Algoritmo.numCiudades = s.nextInt();
            Algoritmo.tamCodificacion = (int)Math.ceil( Math.log(Algoritmo.numCiudades) / Math.log(2) );
            Fitness.distancias = new int[Algoritmo.numCiudades][Algoritmo.numCiudades];
            
            for(int i=0; i<Algoritmo.numCiudades; i++)
                for(int j=0; j<Algoritmo.numCiudades; j++)
                    distancias[i][j] = s.nextInt();

            System.out.println("NumCiudades = " +Algoritmo.numCiudades);
            System.out.println("TamCodificacion = " +Algoritmo.tamCodificacion);
        }
        catch(Exception exc) {
            exc.getMessage();
        }
    }
}
