/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Practica1;

import java.util.ArrayList;

/**
 *
 * @author Juanmy
 */
public class AGGcruceOx2 {

    private Ramdom rand;
    private StringBuilder logg;

    public AGGcruceOx2(long _semilla) {
        rand = new Ramdom();
        rand.Set_random(_semilla);
                logg = new StringBuilder();
    }

    void AGG0x2(float[][] flu, float[][] loc, int tam, int ncromosomas, int ncromosomasaleatorios, long semilla, float probmuta, float probcruce, int evaluaciones, int[] s, int iteraciones) {
        logg.append("INICIO EJECUCION: Algoritmo AGGcruceOX2\n");
        logg.append(" - Nombre del fichero: " + "\n");
        logg.append(" - Semilla utilizada: " + semilla + "\n");
        // Guardamos el momento de inicio del algoritmo.
        long inicioGR = System.currentTimeMillis();

        int t = 0;
        int[][] cromosomas, nuevag;  //poblacion de padres y de hijos
        float[] costes, costesH;  // costes de padres e hijos
        int[] posi, mejorCr = null;
        long peorCoHijo;
        int mejorCrHijo = 0, peorCrHijo = 0;
        float mejorCo = Integer.MAX_VALUE, mejorCoHijo;
        //  reservamos del tamaño del vector de cromosomas original, nueva gen., costes y posic.
        cromosomas = new int[ncromosomas][tam];
        nuevag = new int[ncromosomas][tam];
        costes = new float[ncromosomas];
        costesH = new float[ncromosomas];
        posi = new int[ncromosomas];

        //Carga de los cromosomas iniciales
        // 5 greedy aleatorizados
        GreedyAleatorizado gr = new GreedyAleatorizado(semilla);
        for (int i = 0; i < ncromosomasaleatorios; i++) {
            gr.SoluGreedyAleatorizado(flu, loc, tam, 1000, cromosomas[i]);
            costes[i] = coste(cromosomas[i], flu, loc);
            if (costes[i] < mejorCo) {
                mejorCo = costes[i];
                mejorCr = cromosomas[i];
            }
        }
        for (int i = ncromosomasaleatorios; i < ncromosomas; i++) {
            barajar(cromosomas[i], tam);
            costes[i] = coste(cromosomas[i], flu, loc);
            if (costes[i] < mejorCo) {
                mejorCo = costes[i];
                mejorCr = cromosomas[i];
            }
        }

        //nos quedamos con el mejor cromosoma y su coste
        float mejorCosteGlobal = mejorCo;
        int[] mejorCroGlobal = mejorCr;

        //Calculo de la probabilidad de mutacion
        float probMutacion = probmuta * tam;

        //Contador de evaluaciones de la poblacion
        int conte = ncromosomas;

        //PRINCIPAL: Comienzan las iteraciones
        while (conte < evaluaciones) {
            t++;

            //TORNEO: Calculo de los cromosomas mas prometedores entre cada 2 parejas aleatorias
            //durante ncromosomas enfrentamientos
            for (int k = 0, i, j; k < ncromosomas; k++) {
                i = rand.Randint(0, ncromosomas - 1);
                i=i%ncromosomas;
                while (i == (j = rand.Randint(0, ncromosomas - 1)%ncromosomas));
                posi[k] = (costes[i] < costes[j]) ? i : j;
            }

            //Nos quedamos con los cromosomas mas prometedores y sus costes
            for (int i = 0; i < ncromosomas; i++) {
                nuevag[i] = cromosomas[posi[i]];
                costesH[i] = costes[posi[i]];
            }

            //CRUZAMOS los padres seleccionados con una probabilidad 
            float x;
            int p1;
            boolean[] marcados = new boolean[ncromosomas];
            for (int i = 0; i < ncromosomas; i++) {
                marcados[i] = false;
            }
            for (int i = 0; i < ncromosomas; i++) {
                x = rand.Randfloat(0, (float) 1.01);
                if (x < probcruce) {

                    while (i == (p1 = rand.Randint(0, (int) (ncromosomas - 1))%ncromosomas));

                    cruceOx2(nuevag[i], nuevag[p1]);//aqui se genera el cruce 0x2 y el fallo que no encuentro
                    marcados[i] = marcados[p1] = true;  //marcarlos

                }
            }

            //MUTAMOS los genes de los dos padres ya cruzados con probabilidad probMutacion
            for (int i = 0; i < ncromosomas; i++) {
                boolean m = false;
                for (int j = 0; j < tam; j++) {
                    x = rand.Randfloat(0, (float) 1.01);
                    if (x < probMutacion) {
                        m = true;
                        while (j == (p1 = rand.Randint(0, tam - 1))%ncromosomas);
                        Mutacion(nuevag[i], j, p1);
                    }
                }
                if (m) {
                    marcados[i] = true;
                }

            }

            //calculamos el peor y el mejor de la nueva poblacion
            peorCoHijo = 0;
            mejorCoHijo = Integer.MAX_VALUE;
            for (int i = 0; i < ncromosomas; i++) {
                if (marcados[i]) {

                    costesH[i] = coste(nuevag[i], flu, loc);
                    conte++;
                }

                if (costesH[i] > peorCoHijo) {
                    peorCoHijo = (long) costesH[i];
                    peorCrHijo = i;
                } else {
                    if (costesH[i] < mejorCoHijo) {
                        mejorCoHijo = costesH[i];
                        mejorCrHijo = i;
                    }
                }
            }

            //Mantenemos el elitismo del mejor de P(t) para P(t') si no sobrevive
            //buscamos el mejor padre en la población de hijos
            boolean enc = false;
            for (int i = 0; i < nuevag.length && !enc; i++) {
                if (mejorCr == nuevag[i]) {
                    enc = true;
                }
            }
            if (!enc) {
                nuevag[peorCrHijo] = mejorCr;
                costesH[peorCrHijo] = mejorCo;
            }

            //Actualizamos el mejor cromosoma y su coste con el mejor hijo de la NUEVA POBLACION
            if (mejorCoHijo < mejorCosteGlobal) {
                mejorCosteGlobal = mejorCoHijo;
                mejorCroGlobal = nuevag[mejorCrHijo];
            }

            //actualizamos la anterior población con la nueva ya actualizada
            costes = costesH;
            cromosomas = nuevag;
//        if(t==1){
//            System.out.println("Me quedo en la vuelta ");
//        }
        }
        s = mejorCroGlobal;
        long finalGR = System.currentTimeMillis();
        logg.append("Coste Final AGGcruceOx2: " + mejorCo + "\n");
        logg.append("Solucion AGGcruceOx2:" + "\n");
        for (int i = 0; i < tam; i++) {
            logg.append(" - solucionAGGcruceOx2[" + i + "] = " + s[i] + "\n");
        }
        logg.append("Tiempo de Ejecucion: " + (finalGR - inicioGR) + " ms\n");


    }

    void Mutacion(int[] sol, int r, int s
    ) {
        int aux;
        aux = sol[r];
        sol[r] = sol[s];
        sol[s] = aux;

    }

    int buscaEnArray(int []v,int x){
        for(int i=0;i<v.length;i++){
            if(v[i]==x){
                return i;
            }
        }
        return -1;
    }

    int busca(int[] v, int x, int p1, int p2, int p
    ) {

        for (int i = p1; i <= p2; i++) {
            if (x == v[i]) {
                p = i;
                return p;
            }
        }
        p = -1;
        return p;
    }

    float coste(int[] s, float[][] flu, float[][] dis) {
        float coste = 0;
        for (int i = 0; i < s.length; i++) {
            for (int j = 0; j < s.length; j++) {
                if (i != j) {
                    coste += dis[s[i]][s[j]] * flu[i][j];
                }
            }
        }

        return coste;
    }

    void barajar(int[] v, int tam
    ) {
        for (int i = 0; i < tam; i++) {
            v[i] = i;
        }
        int a;

        int aux;
        for (int i = tam - 1; i > 0; i--) {
            a = rand.Randint(0, i);
            aux = v[i];
            v[i] = v[a];
            v[a] = aux;

        }

    }

    int[] cruce_OX2(int[] solPadre1, int[] solPadre2, int[] solHijo) {
        int dimension=solPadre1.length;

        for (int i = 0; i < dimension; ++i) {
            solHijo[i] = solPadre1[i];
        }

        ArrayList<Integer> seleccionadosPadre2 = new ArrayList<>();

        for (int i = 0; i < dimension; ++i) {
            if (rand.nextBoolean()) {
                seleccionadosPadre2.add(solPadre2[i]);
            }
        }

        int pos;
        for (int i : seleccionadosPadre2) {
            pos = buscaEnArray(solHijo, i);
            if (pos != -1) {
                solHijo[pos] = -1;
            } else {
                System.out.println("ERROR EN OX2 (AGG)");
                break;
            }
        }

        pos = 0;
        for (int i = 0; i < dimension; ++i) {
            if (solHijo[i] == -1) {
                solHijo[i] = seleccionadosPadre2.get(pos);
                ++pos;
            }
        }
        return solHijo;
    }



    void obtenerHijoOx2(int[] a, int[] b, int[] r) {
        int x = 0;
        float p;
        ArrayList<Integer> pos1;

        int tam = a.length;
        pos1 = new ArrayList<Integer>();
        r = new int[tam];

        r = a;     //copia del original

        for (int i = 0; i < tam; i++) {
            p = (rand.Randfloat(0, (float) 1.01));
            if (p < 0.5) {

                x = busca(a, b[i], 0, tam, x);
                if (x >= 0) {
                    r[x] = -1;
                    pos1.add(i);
                }
            }
        }
        int j = 0;
        for (int i = 0; i < tam; i++) {
            if (r[i] == -1 && j <= pos1.size()) {
                r[i] = b[pos1.get(j++)];
            }
        }

    }

//Cruce OX2
    void cruceOx2(int[] a, int[] b) {
        int tam = a.length;
        int[] r1, r2;
        r1 = new int[tam];
        r2 = new int[tam];
        cruce_OX2(a, b, r1);
        cruce_OX2(b, a, r2);

        a = r1;
        b = r2;
    }

    public String getLog() {
        return logg.toString();
    }
}
