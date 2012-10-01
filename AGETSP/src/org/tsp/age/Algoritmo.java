package org.tsp.age;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;

/**
 * Representa el Genético Simple con Modificaciones para mejorar
 * la resolución del problema del agente viajero (TSP)
 */
public class Algoritmo 
{
    /**
     * Probabilidad de cruzamiento
     */
    public static double tasaCruzamiento = 1;

    /**
     * Probabilidad de mutación
     */
    public static double tasaMutacion = .03;
    
    /**
     * Indica si se debe guardar el mejor individuo en
     * la nueva poblacion
     */
    private static boolean elitismo = true;
    
    /**
     * El número de ciudades que procesa el algoritmo
     */
    public static int numCiudades = 15;
    
    /**
     * Número de individuos que tendrá cada generación
     */
    public static int tamPoblacion = 100;
    
    /**
     * Número de iteraciones (generaciones) que fabricará
     * el Algoritmo Genético Simple con Modificaciones
     */
    public static int numGeneraciones = 10000;
    
    /**
     * Ruta del archivo de donde se encuentran la 
     * matriz de ciudades
     */
    public static String rutaArchivoCiudades;
    
    /**
     * Objeto auxiliar para controlar la generación de números
     * aleatorios
     */
    public static Random rnd;
    
    /**
     * Obtiene una nueva población aplicando los pasos del 
     * Algoritmo Genético Simple con Modificaciones
     * @param pob La población Inicial
     * @param mejor El mejor individuo
     * @return Una nueva población evolucionada 
     */
    public static Poblacion evoluciona(Poblacion pob, Genotipo mejor)
    {
        Poblacion nuevaPob = new Poblacion(pob.size(), false);
        if (elitismo) {
            //nuevaPob.guardarIndividuo(0, pob.getFittest().getCopia());
            nuevaPob.insertaOrdenado(mejor.getCopia());
        }
        int offsetElitismo;
        if(elitismo)
            offsetElitismo = 1;
        else
            offsetElitismo = 0;
        double[] dist = pob.linealRanking();
        for (int i = offsetElitismo; i < pob.size(); i++) {
            Genotipo A = pob.ruleta(dist);
            Genotipo B = pob.ruleta(dist);
            if(Algoritmo.rnd.nextDouble() <= tasaCruzamiento) {
                nuevaPob.insertaOrdenado(crossover(A, B));
                //nuevaPob.guardarIndividuo(i, crossover(A, B));
            }
            else
                nuevaPob.insertaOrdenado(pob.getGenotipo(i));
                //nuevaPob.guardarIndividuo(i, pob.getGenotipo(i));
        }
        nuevaPob.pegaArreglo();
        for(int i = 0; i < nuevaPob.size(); i++) {
            mutacion(nuevaPob.getGenotipo(i));
        }
        return nuevaPob;
        
    }
    
    /**
     * Muta los genes de un individuo
     * @param gen El individuo a mutar
     */
    private static void mutacion(Genotipo gen)
    {
        int primero, segundo;
        
        if(Algoritmo.rnd.nextDouble() <= tasaMutacion) {
            primero = 0;
            segundo = 0;
            while(primero==segundo) {
                primero = (int)(Algoritmo.rnd.nextDouble()*numCiudades);
                segundo = (int)(Algoritmo.rnd.nextDouble()*numCiudades);
            }
            int var = gen.getGen(primero);
            gen.setGen(primero,gen.getGen(segundo));
            gen.setGen(segundo, var);
        }
    }
    
    /**
     * Crea un individuo por medio del cruzamiendo OX de
     * otros dos individuos
     * @param A El padre
     * @param B El padre
     * @return Un nuevo Genotipo con el resultado del
     * cruzamiento
     */
    private static Genotipo crossover(Genotipo A, Genotipo B)
    {
        Genotipo hijo = new Genotipo();
        int lugar1 = 0, lugar2 = 0, l1, l2;
        HashSet<Integer> pasadas = new HashSet<>();
        while(lugar1 == lugar2) {
            lugar1 = (int)(Algoritmo.rnd.nextDouble()*numCiudades);
            lugar2 = (int)(Algoritmo.rnd.nextDouble()*numCiudades);
        }
        l1 = Math.min(lugar1, lugar2);
        l2 = Math.max(lugar1, lugar2);
        lugar1 = l1;
        lugar2 = l2;
        for(int i = lugar1; i <= lugar2; i++) {
            pasadas.add(A.getGen(i));
            hijo.setGen(i, A.getGen(i));
        }
        LinkedList<Integer> faltantes = new LinkedList<>();
        for(int i=0; i<B.size(); i++) {
            if(!pasadas.contains(B.getGen(i)))
                faltantes.addLast(B.getGen(i));
        }
        for(int i=0; i<hijo.size(); i++) {
           if(hijo.getGen(i)==0)
               hijo.setGen(i, faltantes.pop());
        }
        
        
        return hijo;
    }
}
