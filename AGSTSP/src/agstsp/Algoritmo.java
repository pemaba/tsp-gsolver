package agstsp;

import java.util.Random;

public class Algoritmo
{
    private static double tasaCruzamiento = 1;
    private static double tasaMutacion = 0.1;
    //private static final int tamanoTorneo = 5;
    private static boolean elitismo = false;
    public static int numCiudades = 15;
    public static int tamCodificacion = 4;
    public static int numGeneraciones = 10000;
    public static Random rnd;
    
    /*
     * Hace una iteración del Algoritmo Genético simple
     * y devuelve como resultado la nueva población obtenida
     */
    public static Poblacion evoluciona(Poblacion pob) 
    {
        Poblacion nuevaPob = new Poblacion(pob.size(), false);
        if (elitismo)
            nuevaPob.guardarIndividuo(0, pob.getFittest());
        
        int offsetElitismo;
        if(elitismo)
            offsetElitismo = 1;
        else
            offsetElitismo = 0;
        
        //sobrescribes dos elementos!
        Genotipo A, B;
        for (int i = offsetElitismo; i < pob.size(); i+=2){ //te comes a 1
            A = pob.ruleta();
            B = pob.ruleta();
            //A = seleccionTorneo(pob);
            //B = seleccionTorneo(pob);
            if(Algoritmo.rnd.nextDouble() <= tasaCruzamiento){
                Genotipo [] C = crossover(A, B);
                C[0].arregla();
                C[1].arregla();
                nuevaPob.guardarIndividuo(i, C[0]);
                if(i+1<pob.size())
                    nuevaPob.guardarIndividuo(i+1, C[1]);
            }
            else {
                if(i+1<pob.size()) {
                    nuevaPob.guardarIndividuo(i, A); //pasan a la sig. sin mutar
                    nuevaPob.guardarIndividuo(i+1, B);
                }
                else {
                    //hay que elegir a 1
                    nuevaPob.guardarIndividuo(i, Algoritmo.rnd.nextDouble() > 0.5? B: A);
                }
            }
        }
        
        for(int i = 0; i < nuevaPob.size(); i++)
            mutacion(nuevaPob.getGenotipo(i));
        nuevaPob.arreglaPoblacion();
        return nuevaPob;
        
    }
    
    private static void mutacion(Genotipo gen)
    {
        for (int i = 0; i < gen.size(); i++) {
            if(Algoritmo.rnd.nextDouble() <= tasaMutacion ) {
                gen.setGen(i, !gen.getGen(i));
            }
        }
    }
    
    private static Genotipo[] crossover(Genotipo A, Genotipo B)
    {
        Genotipo [] solucion = new Genotipo[2];
        int lugar = (int)Algoritmo.rnd.nextDouble()*A.size();
        solucion[0] = A.getCopia(); //para que no modifiques a los papás
        solucion[1] = B.getCopia();
        for(int i = lugar; i < B.size(); i++){
            solucion[0].setGen(i, B.getGen(i));
            solucion[1].setGen(i, A.getGen(i));
        }
   
        return solucion;
    }
 
    private static Genotipo seleccionTorneo(Poblacion pob)
    {
        int tamanoTorneo = 10;
        Poblacion torneo = new Poblacion(tamanoTorneo, false);
        
        for (int i = 0; i<tamanoTorneo; i++) {
            int randomId = (int) (Algoritmo.rnd.nextDouble() * pob.size());
            torneo.guardarIndividuo(i, pob.getGenotipo(randomId));
        }
        Genotipo fittest = torneo.getFittest();
        return fittest;
    }
    
   
}
