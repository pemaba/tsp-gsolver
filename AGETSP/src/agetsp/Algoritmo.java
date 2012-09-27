
package agetsp;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Algoritmo {
    
    private static double tasaUniforme = 1;
    private static double tasaMutacion = .03;
    //private static final int tamanoTorneo = 5;
    private static boolean elitismo = true;
    public static int numCiudades = 15;
    public static int numGeneraciones = 10000;
    public static Random rnd;
    
    public static Poblacion evoluciona(Poblacion pob, Genotipo mejor) {
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
            if(Algoritmo.rnd.nextDouble() <= tasaUniforme) {
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
    
    private static void mutacion(Genotipo gen){
        int primero, segundo;
        //for (int i = 0; i < gen.size(); i++){
            if(Algoritmo.rnd.nextDouble() <= tasaMutacion ){
                primero = 0;
                segundo = 0;
                while(primero==segundo){
                    primero = (int)(Algoritmo.rnd.nextDouble()*numCiudades);
                    segundo = (int)(Algoritmo.rnd.nextDouble()*numCiudades);
                }
                int var = gen.getGen(primero);
                gen.setGen(primero,gen.getGen(segundo));
                gen.setGen(segundo, var);
                
            }
        //}
    }
    
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
        int i = 0, j = 0;
        while(i < lugar1 && j<numCiudades) {
            if (!pasadas.contains(B.getGen(j))) {
                hijo.setGen(i,B.getGen(i));
                i++;
            }
            j++;
        }
        i = lugar2 + 1;
        while(i < numCiudades && j<numCiudades) {
            if( !pasadas.contains(B.getGen(j)) ) {
                hijo.setGen(i,B.getGen(i));
                i++;
            }
            j++;
        }
        
        return hijo;
    }

}
