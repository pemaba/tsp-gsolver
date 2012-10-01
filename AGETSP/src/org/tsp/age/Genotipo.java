package org.tsp.age;

/**
 * Representación de un individuo que es una solución (un posible camino)
 * Utilizados por el AGE para generar posibles soluciones
 */
public class Genotipo 
{
    /**
     * Cadena de enteros que representa el genotipo del individuo
     */
    int[] genes;
    
    /**
     * Medida de bondad del individuo (camino)
     */
    Double fitness;

    /**
     * Constructor Default. Inicializa los genes y asigna a fitness
     * el valor nulo
     */
    public Genotipo() 
    {
        genes = new int[Algoritmo.numCiudades];
        fitness = null;
    }

    /**
     * Constructor. Inicializa las variables miembro genes y fitness.
     * @param iniciar Indica si se debe o no generar un individuo (camino)
     * de manera aleatoria.
     */
    public Genotipo(boolean iniciar)
    {
        this();
        if (iniciar) {
            this.generaAleatorio();
            this.getFitness();
        }
    }

    /**
     * Genera aleatoriamente un individuo (camino) y lo guarda
     * en el genoma del objeto que llama a este método
     */
    public final void generaAleatorio() 
    {
        boolean[] usados = new boolean[Algoritmo.numCiudades + 1];
        int pos, i;
        for (i = 0; i < Algoritmo.numCiudades; i++) {
            usados[i] = false;
        }
        i = 0;
        while (i < Algoritmo.numCiudades) {
            pos = (int) (Algoritmo.numCiudades * Algoritmo.rnd.nextDouble()) + 1;
            if (!usados[pos]) {
                genes[i] = pos;
                i++;
                usados[pos] = true;
            }
        }
    }

    /**
     * Obtiene la solución que representa este individuo
     * @return Un arreglo de enteros con el orden en que se visitan
     * las ciudades para formar un camino.
     */
    public int[] getArreglo() 
    {
        return genes;
    }

    /**
     * Obtiene una ciudad guardadada en la posición dada por el parámetro.
     * @param index Índice que empieza en cero del gen (Ciudad) a obtener
     * @return Un entero que indica el número de ciudad guardad en el gen
     * index.
     */
    public int getGen(int index) 
    {
        return genes[index];
    }

    /**
     * Establece una ciudad guardadada en la posición dada por el parámetro.
     * @param index Índice que empieza en cero del gen (Ciudad) a guardar
     * @param value Entero que indica qué ciudad guardar en el gen
     */
    public void setGen(int index, int value)
    {
        genes[index] = value;
    }

    /**
     * Otiene el tamaño del genotipo
     * @return Un entero que indica el número de ciudades que contiene
     * el genotipo
     */
    public int size() 
    {
        return genes.length;
    }

    /**
     * Obtiene la representación en texto del genotipo
     * @return Un string con las ciudades separadas por comas.
     */
    @Override
    public String toString() 
    {
        StringBuffer sb = new StringBuffer();
        int i = 0;
        sb.append(this.getGen(i));
        for (i = 1; i < this.size(); i++) {
            sb.append("," + this.getGen(i));
        }

        return sb.toString();
    }

    /**
     * Obtiene la medida de bondad (fitness) relativa del
     * individuo representado por este Genotipo
     * @return 
     */
    public double getFitness() 
    {
        if (fitness == null)
            fitness = Fitness.evaluaCamino(this);

        return fitness;
    }

    /**
     * Obtiene una copia exacta de este indiviudo
     * @return Un nuevo objeto Genotipo con el mismo genotipo (y por 
     * consiguiente, mismo fitness) que el del objeto que lo 
     * manda llamar.
     */
    public Genotipo getCopia()
    {
        Genotipo res = new Genotipo(false);
        System.arraycopy(this.genes, 0, res.genes, 0, genes.length);
        if (this.fitness != null) {
            res.fitness = new Double(this.fitness.doubleValue());
        }

        return res;
    }
}
