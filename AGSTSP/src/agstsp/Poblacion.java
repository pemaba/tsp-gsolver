package agstsp;

public class Poblacion 
{
    /**
     * Contiene a los individuos que integran la población
     */
    Genotipo[] individuos;
    /**
     * Referencia al mejor individuo de esta población
     */
    Genotipo mejor;
    
    public Poblacion(int tamanoPoblacion, boolean empezar) 
    {
        individuos = new Genotipo[tamanoPoblacion];
        mejor = null;
        if(empezar) {
            for(int i = 0; i < tamanoPoblacion; i++) {
                individuos[i] = new Genotipo(true);
            }
        }
    }
    public Genotipo ruleta() {
        //Genotipo ind = null; //no tienes que crear nuevos individuos
        //solo eliges de la poblacion
        double [] acumulado = new double[this.size()];
        double total = 0, porcion, acum = 0;
        int i;
        for (i = 0; i < this.size(); i ++) {
            acumulado[i] = this.getGenotipo(i).getFitness();
            total += acumulado[i];
        }
        porcion = Algoritmo.rnd.nextDouble();
        for (i = 0; i < this.size(); i++) {
            acum += acumulado[i]/total;
            if(acum>=porcion) {
                break;
            }
        }
        return this.getGenotipo(i);
    }
    
    public Genotipo getGenotipo(int index) {
        return individuos[index];
    }
    
    public Genotipo getFittest() {
        if(this.mejor==null) {
            mejor = individuos[0];
            for(int i = 0; i < size(); i++){
                if( getGenotipo(i).getFitness() < mejor.getFitness() ) {
                    mejor = getGenotipo(i);
                }
            }
        }
        
        return mejor;
    }
    
    public int size() {
        return individuos.length;
    }
    
    /**
     * Guarda una copia del individuo con genotipo gen
     * en la posición index del arreglo de individuos que conforman 
     * la población.
     * @param index Zero-based índice en donde  guardar el individuo
     * @param gen El individuo cuya copia se va a guardar
     */
    public void guardarIndividuo(int index, Genotipo gen)
    {
        individuos[index] = gen.getCopia();
    }

    public void arreglaPoblacion() {
        for(int i=0; i<size(); i++)
            individuos[i].arregla();
    }
}
