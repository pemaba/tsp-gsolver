package agetsp;

import java.io.*;
import java.math.*;
import java.util.HashSet;
import java.util.Iterator;

public class Genotipo {

    /**
     * Cadena de enteros que representa el genotipo del individuo
     */
    int[] genes;
    Double fitness;

    public Genotipo() {
        genes = new int[Algoritmo.numCiudades];
        fitness = null;
    }

    public Genotipo(boolean iniciar) {
        this();
        if (iniciar) {
            this.generaAleatorio();
            this.getFitness();
        }
    }

    //TODO: CHECAR
    public final void generaAleatorio() {
        boolean[] usados = new boolean[Algoritmo.numCiudades];
        int pos, i;
        for (i = 0; i < Algoritmo.numCiudades; i++) {
            usados[i] = false;
        }
        i = 0;
        while (i < Algoritmo.numCiudades) {
            pos = (int) (Algoritmo.numCiudades * Algoritmo.rnd.nextDouble());
            if (!usados[pos]) {
                genes[i] = pos;
                i++;
                usados[pos] = true;
            }
        }

        //for(int i =0 ; i<Algoritmo.numCiudades;i++)
        //genes[i]=i;
    }

    //Regresa el genotipo como un arreglo de enteros
    public int[] getArreglo() {
        return genes;
    }

    public int getGen(int index) {
        return genes[index];
    }

    public void setGen(int index, int value) {
        genes[index] = value;
    }

    public int size() {
        return genes.length;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        int i = 0;
        sb.append(this.getGen(i));
        for (i = 1; i < this.size(); i++) {
            sb.append("," + this.getGen(i));
        }

        return sb.toString();
    }

    public double getFitness() {
        if (fitness == null) {
            fitness = Fitness.evaluaCamino(this);
        }

        return fitness;
    }

    public Genotipo getCopia() {
        Genotipo res = new Genotipo(false);
        System.arraycopy(this.genes, 0, res.genes, 0, genes.length);
        if (this.fitness != null) {
            res.fitness = new Double(this.fitness.doubleValue());
        }

        return res;
    }
}
