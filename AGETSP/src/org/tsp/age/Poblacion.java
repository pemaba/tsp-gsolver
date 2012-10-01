package org.tsp.age;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Representa un conjunto de genotipos que son usados por el 
 * Algoritmo Genético Simple Modificado para encontrar nuevas
 * soluciones.
 */
public class Poblacion 
{
   /**
     * Arreglo que contiene a los individuos que integran la población
     */
    Genotipo[] individuos;
    
    /**
     * Referencia al mejor individuo de esta población
     */
    Genotipo mejor;
    
    /**
     * Lista ligada que es utilizada como auxilizar para insertar
     * ordenadamente de acuerdo a su fitness a los individuos (genotipos)
     */
    LinkedList<Genotipo> lista;
    
    /**
     * Constructor
     * @param tamanoPoblacion Número de individuos en la población
     * @param empezar Indica si se debe generar aleatoriamente 
     * los individuos de esta población
     */
    public Poblacion(int tamanoPoblacion, boolean empezar) 
    {
        individuos = new Genotipo[tamanoPoblacion];
        lista = new LinkedList<Genotipo>();
        mejor = null;
        if(empezar) {
            for(int i = 0; i < tamanoPoblacion; i++) {
                this.insertaOrdenado(new Genotipo(empezar));
            }
            this.pegaArreglo();
        }
    }
    
    /**
     * Escribe en el arreglo los individuos insertados en la
     * lista, en el mismo orden en que están presentes en dicha lista ligada.
     */
    public void pegaArreglo()
    {
        //this.individuos = (Genotipo[])this.lista.toArray();
        this.lista.toArray(this.individuos);
    }
    
    /**
     * Inserta un individuo en la lista ordenados por su fitness de manera
     * ascendente.
     * @param a El individuo (genotipo) a insertar
     */
    public void insertaOrdenado(Genotipo a) 
    {
        if(lista.isEmpty())
            lista.addFirst(a);
        else {
            Iterator<Genotipo> it = lista.iterator();
            Genotipo b;
            int i = 0;
            boolean insertado = false;
            while(!insertado && it.hasNext()) {
                b = it.next();
                if(a.getFitness() < b.getFitness()) {
                    lista.add(i, a);
                    insertado = true;
                }
                i++;
            }
            if(!insertado)
                lista.addLast(a);
        }
    }
    
    /**
     * Selecciona un indiviudo aleatoriamente por medio del mecanismo de 
     * ruleta, de acuerdo a la distribución de probabilidad
     * otorgada por el parámetro.
     * @param acumulado Arreglo de doubles que contiene la distribución 
     *      de probabilidad que se le asigna a cada indiviudo de la pobación
     * @return Referencia al Genotipo (individuo) seleccionado.
     */
    public Genotipo ruleta(double[] acumulado) 
    {
        //Genotipo ind = null; //no tienes que crear nuevos individuos
        //solo eliges de la poblacion
        double porcion = Algoritmo.rnd.nextDouble(), acum = 0;
        int i;
        for (i = 0; i < this.size(); i++) {
            acum += acumulado[i];
            if(acum>=porcion) {
                break;
            }
        }
        if(i==this.size())
            i--;
        return this.getGenotipo(i);
    }
    
    /**
     * Calcula la distribución de probabilidad de los individuos de
     * esta población de acuerdo al mecanismo de Linear Ranking.
     * Este mecanismo REQUIERE que los individuos estén ordenados en el arreglo 
     * para que funcione correctamente
     * @return Un arreglo de double de tamaño igual al número de individuos
     * de esta población que indica la probabilidad de individuo i
     * de ser muestreado.
     */
    public double [] linealRanking()
    {
        double [] res = new double [this.size()];
        int tam = this.size();
        double nmas, nmen;
        nmas = 1.1;
        nmen = 2 - nmas;
        for (int i = 0; i < tam; i++) {
            res[i] = (1 /(double) tam) * (nmas- (nmas -nmen)*(i)/(tam - 1));
        }
        return res;
    }
    
    /**
     * Obtiene el Genotipo guardado en la posición del arreglo index.
     * @param index La posición del arreglo a ser obtenida.
     * @return El Genotipo guardado en la posición del arreglo index.
     */
    public Genotipo getGenotipo(int index) 
    {
        return individuos[index];
    }
    
    /**
     * Obtiene al individuo con el fitness más bajo de la población.
     * @return Una referencia a un Genotipo con el fitness más bajo de la población.
     */
    public Genotipo getFittest() 
    {
        if(this.mejor==null) {
            mejor = individuos[0];
            for(int i = 0; i < size(); i++){
                if(mejor.getFitness() >= getGenotipo(i).getFitness()) {
                    mejor = getGenotipo(i);
                }
            }
        }
        return mejor;
    }
    
    /**
     * Devuelve el tamaño de la población.
     * @return Un int que indica el número de individuos guardados
     * en esta población, basado en el tamaño del arreglo de individuos
     */
    public int size() 
    {
        return individuos.length;
    }
    
    /**
     * Guarda el individuo (Genotipo) en la posición del arreglo indicada
     * @param index Lugar del arreglo en donde se guardará el individuo.
     * @param gen Individuo (Genotipo) a Guardar
     */
    public void guardarIndividuo(int index, Genotipo gen) 
    {
        individuos[index] = gen;
    }
    
}
