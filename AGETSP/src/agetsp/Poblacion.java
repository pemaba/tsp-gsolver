package agetsp;

import java.util.LinkedList;
import java.util.Iterator;

public class Poblacion {
    
   /**
     * Contiene a los individuos que integran la población
     */
    Genotipo[] individuos;
    /**
     * Referencia al mejor individuo de esta población
     */
    Genotipo mejor;
    
    LinkedList<Genotipo> lista;
    
    public Poblacion(int tamanoPoblacion, boolean empezar) {
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
    
    public void pegaArreglo()
    {
        //this.individuos = (Genotipo[])this.lista.toArray();
        this.lista.toArray(this.individuos);
    }
    
    public void insertaOrdenado(Genotipo a) {
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
    
    public Genotipo ruleta(double[] acumulado) {
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
    
    public double [] linealRanking(){
        double [] res = new double [this.size()];
        int tam = this.size();
        double nmas, nmen;
        nmas = 1.1;
        nmen = 2 - nmas;
        for (int i = 0; i < tam; i++) {
            res[i] = (1 / tam) * (nmas- (nmas -nmen)*(i)/(tam - 1));
        }
        return res;
    }
    
    public Genotipo getGenotipo(int index) {
        return individuos[index];
    }
    
    public Genotipo getFittest() {
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
    
    public int size() {
        return individuos.length;
    }
    
    public void guardarIndividuo(int index, Genotipo gen) {
        individuos[index] = gen;
    }
    
}
