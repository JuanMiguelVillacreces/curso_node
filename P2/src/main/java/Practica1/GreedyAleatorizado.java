/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Practica1;

import Practica1.Ramdom;
import java.util.ArrayList;

/**
 *
 * @author Juanmy
 */
public class GreedyAleatorizado {

    private Ramdom rand;

    public GreedyAleatorizado(long _semilla) {
        rand = new Ramdom();
        rand.Set_random(_semilla);
    }

    int menorDist(float[] v, long tam, boolean[] marcaD) {
        int pmenor = 0;
        float menor = 99999999;
        for (int i = 0; i < tam; i++) {
            if (!marcaD[i] && v[i] <= menor) {
                pmenor = i;
                menor = v[i];
            }
        }
        marcaD[pmenor] = true;
        return pmenor;
    }

//Devolver la posici�n del menor del vector y lo marcamos
    int mayorFluj(float[] v, long tam, boolean[] marcaF) {
        int pmayor = 0;
        float mayor = 0;
        for (int i = 0; i < tam; i++) {
            if (!marcaF[i] && v[i] >= mayor) {
                pmayor = i;
                mayor = v[i];
            }
        }
        marcaF[pmayor] = true;
        return pmayor;
    }

//calcular vectores de potenciales, suma de flujos y distancias por cada unidad y localizacion
    void CrearPotenciales(float[] potflu, float[] potdis, int tam, int[] s,
            float[][] flu, float[][] loc) {

        for (int i = 0; i < tam; i++) {
            potflu[i] = 0;
            potdis[i] = 0;
            for (int j = 0; j < tam; j++) {
                potflu[i] = potflu[i] + flu[i][j];
                potdis[i] = potdis[i] + loc[i][j];
            }
        }
    }

//CONSTRUIR UNA MEJOR solucion Greedy aleatorizada 
    void SoluGreedyAleatorizado(float[][] flu, float[][] loc, int tam, int evaluaciones, int[] s) {
        int menord, mayorf;
        int pos, cuantos;
        float[] potflu;
        float[] potdis;
        int t = 5;
        potflu = new float[tam];
        potdis = new float[tam];
        ArrayList<Integer> auxF;
        auxF = new ArrayList<Integer>(t);
        ArrayList<Integer> auxD;
        auxD = new ArrayList<Integer>(t);
        boolean[] marcaF;
        boolean[] marcaD;
        marcaF = new boolean[tam];
        marcaD = new boolean[tam];
        for (int i = 0; i < tam; i++) {
            marcaF[i] = false;
            marcaD[i] = false;
        }

        CrearPotenciales(potflu, potdis, tam, s, flu, loc);

        int hay;
        for (int i = 0; i < tam; i++) {
            if (tam - i >= 5) {
                hay = 5;
            } else {
                hay = tam - i;
            }

            for (int j = 0; j < hay; j++) {
                pos = mayorFluj(potflu, tam, marcaF);
                auxF.add(pos);

                pos = menorDist(potdis, tam, marcaD);
                auxD.add(pos);
            }

            pos = rand.Randint(0, auxF.size() - 1);
            mayorf = auxF.get(pos);
            for (int j = 0; j < hay; j++) {
                if (auxF.get(j) != mayorf) {
                    marcaF[auxF.get(j)] = false;
                }
            }
            pos = rand.Randint(0, auxD.size() - 1);
            menord = auxD.get(pos);
            for (int j = 0; j < hay; j++) {
                if (auxD.get(j) != menord) {
                    marcaD[auxD.get(j)] = false;
                }
            }
            auxF.clear();
            auxD.clear();
            s[mayorf] = menord;

        }
    }
}
