package agstsp;
import java.util.HashSet;
import java.util.Iterator;

public class Genotipo 
{
    /**
     * Cadena de bits que representa el genotipo del individuo
     */
    boolean[] genes;

    /**
     * Medida de bondad del genotipo
     */
    Double fitness;
    
    public Genotipo() 
    {
        genes = new boolean[Algoritmo.numCiudades * Algoritmo.tamCodificacion];
        fitness = null;
    }
    
    public Genotipo(boolean iniciar)
    {
        this();
        if(iniciar) {
            this.generaAleatorio();
            this.arregla();
            this.getFitness();
        }
    }
    
    public final void generaAleatorio()
    {
        for(int i =0 ; i<Algoritmo.numCiudades*Algoritmo.tamCodificacion;i++)
            if (Algoritmo.rnd.nextDouble() > 0.5)
                genes[i] = true;
            else
                genes[i] = false;
    }
    
    public int[] interpretaCamino()
    {
        int[] result = new int[Algoritmo.numCiudades];
        int potencia = 0;
        for(int j =0; j<Algoritmo.numCiudades; j++) {
            potencia = 1 << (Algoritmo.tamCodificacion - 1);
            result[j] = 0;
            for(int i= 0; i<Algoritmo.tamCodificacion;i++) {
                if(genes[j*Algoritmo.tamCodificacion+i]==true){
                    result[j] += potencia;  
                } 
                potencia=potencia/2;
            }
        }
        return result;
    }
    
    public final void arregla() 
    {
        int[] camino = this.interpretaCamino();
        boolean[] semaforo = new boolean[Algoritmo.numCiudades + 1];
        boolean[] visitados = new boolean[Algoritmo.numCiudades + 1];
        HashSet<Integer> vacios = new HashSet<>();
        for(int i=0; i<Algoritmo.numCiudades; i++)
            vacios.add(i);
        for(int i=0; i<Algoritmo.numCiudades; i++) {
            if(camino[i]<Algoritmo.numCiudades)  {
                if(!visitados[camino[i]]) {
                    visitados[camino[i]] = true;
                    semaforo[i] = true;
                    vacios.remove(camino[i]);
                }
                else
                    semaforo[i] = false;
            }
            else
                semaforo[i] = false;
        }
        int elem;
        Iterator<Integer> it = vacios.iterator();
        for(int i=0; i<Algoritmo.numCiudades; i++) {
            if(!semaforo[i]) {
                elem = it.next(); //asumimos que es random
                for(int j=Algoritmo.tamCodificacion-1; j>=0; j--) {
                    genes[i*Algoritmo.tamCodificacion + j] = elem%2==1? true: false;
                    elem /= 2;
                }
            }
        }
        fitness = null; //para que lo recalcule
    }
    
    public boolean getGen(int index)
    {
        return genes[index];
    }
    
    public void setGen(int index, boolean value)
    {
        genes[index] = value;
    }
    
    public int size() 
    {
        return genes.length;
    }
    
    @Override
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("F=" +this.getFitness() + "; G=");
        for (int i = 0; i<this.size(); i++) {
            if(i>0 && i%Algoritmo.tamCodificacion==0)
                sb.append(",");
            sb.append(this.getGen(i)? "1": "0");
        }
        
        return sb.toString();
    }
    
    public double getFitness()
    {
        if(fitness==null)
            fitness = Fitness.evaluaCamino(this);

        return fitness;
    }
    
    public Genotipo getCopia()
    {
        Genotipo res = new Genotipo(false);
        System.arraycopy(this.genes, 0, res.genes, 0, genes.length);
        if(this.fitness!=null)
            res.fitness = new Double(this.fitness.doubleValue());
        
        return res;
    }
}
