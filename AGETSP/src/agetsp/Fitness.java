
package agetsp;

import java.io.*;
import java.util.Scanner;

public class Fitness {
    
    private static double [][] distancias;

    
    public static double evaluaCamino(int[] path) {
        double suma = 0;
        
        for (int i = 1; i < Algoritmo.numCiudades; i++) 
            suma += distEntre2(path[i],path[i-1]);
        suma += distEntre2(path[Algoritmo.numCiudades-1], path[0]);
        
        return suma;
    }
    
    public static double evaluaCamino(Genotipo A) {
       return evaluaCamino(A.getArreglo());
    }
    
    private static double distEntre2(int cdA, int cdB){
        return distancias[cdA - 1][cdB - 1];
    }
    
    public static void init(String filename)
    {
         try {
            Scanner s = new Scanner(new File(filename));
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
