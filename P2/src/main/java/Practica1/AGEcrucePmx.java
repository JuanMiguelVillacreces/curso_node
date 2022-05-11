/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Practica1;

/**
 *
 * @author Juanmy
 */
public class AGEcrucePmx {

    private Ramdom rand;
    private StringBuilder log;

    public AGEcrucePmx(long _semilla) {
        rand = new Ramdom();
        rand.Set_random(_semilla);
        log = new StringBuilder();
    }

    void crucepmx(float[][] flu, float[][] loc, int tam, int ncromosomas, int ncromosomasaleatorios, long semilla, float probmuta, int evaluaciones, int[] s, int iteraciones) {

        log.append("INICIO EJECUCION: Algoritmo AGEcrucePMX\n");
        log.append(" - Nombre del fichero: " + "\n");
        log.append(" - Semilla utilizada: " + semilla + "\n");
        // Guardamos el momento de inicio del algoritmo.
        long inicioGR = System.currentTimeMillis();

        int[][] cromosomas = new int[ncromosomas][tam];
        float[] costes = new float[ncromosomas];
        s = new int[tam];
        int[] padre1 = new int[tam];
        int[] padre2 = new int[tam];
        int[] hijo1 = new int[tam];
        int[] hijo2 = new int[tam];
        int[] mejor = new int[tam];
        float mejorCo = Integer.MAX_VALUE;
        int s1, s2, s3;
        float cs1, cs2, cs3;
        int cp1, cp2, cp3, cp4;
        int peorpadre;
        float costepeorpadre1;
        int peorpadre2;
        float costepeorpadre2;
        float c1, c2, c3, c4;
        float costeh1, costeh2;
        int cont1 = 0, cont2 = 0;

        GreedyAleatorizado gr = new GreedyAleatorizado(semilla);
        for (int i = 0; i < ncromosomasaleatorios; i++) {
            gr.SoluGreedyAleatorizado(flu, loc, tam, iteraciones, cromosomas[i]);
            costes[i] = coste(cromosomas[i], flu, loc);
            if (costes[i] < mejorCo) {
                mejorCo = costes[i];
                mejor = cromosomas[i];
            }

        }
        for (int i = ncromosomasaleatorios; i < ncromosomas; i++) {
            barajar(cromosomas[i], tam);
            costes[i] = coste(cromosomas[i], flu, loc);
            if (costes[i] < mejorCo) {
                mejorCo = costes[i];
                mejor = cromosomas[i];
            }

        }

        float probmutacion = probmuta * tam;

        int ev = ncromosomas;

        while (ev < evaluaciones) {
            ev++;
            s1 = rand.Randint(0, ncromosomas - 1);
            s2 = rand.Randint(0, ncromosomas - 1);
            s3 = rand.Randint(0, ncromosomas - 1);
            cs1 = coste(cromosomas[s1], flu, loc);
            cs2 = coste(cromosomas[s2], flu, loc);
            cs3 = coste(cromosomas[s3], flu, loc);

            if (cs1 < cs2 && cs1 < cs3) {
                padre1 = cromosomas[s1];
            } else if (cs2 < cs1 && cs2 < cs3) {
                padre1 = cromosomas[s2];
            } else {
                padre1 = cromosomas[s3];
            }

            //eleccion del segundo padre
            s1 = rand.Randint(0, ncromosomas - 1);
            s2 = rand.Randint(0, ncromosomas - 1);
            s3 = rand.Randint(0, ncromosomas - 1);
            cs1 = coste(cromosomas[s1], flu, loc);
            cs2 = coste(cromosomas[s2], flu, loc);
            cs3 = coste(cromosomas[s3], flu, loc);

            if (cs1 < cs2 && cs1 < cs3) {
                padre2 = cromosomas[s1];
            } else if (cs2 < cs1 && cs2 < cs3) {
                padre2 = cromosomas[s2];
            } else {
                padre2 = cromosomas[s3];
            }
            crucePmx(padre1, padre2);
            hijo1 = padre1;
            hijo2 = padre2;

            float x;
            int p;
            for (int i = 0; i < tam; i++) {
                x = rand.Randfloat(0, (float) 1.01);
                if (x < probmutacion) {

                    while (i == (p = rand.Randint(0, tam - 1)));
                    if (p >= tam) {
                        p = tam - 1;
                    }
                    Mutacion(hijo1, i, p);
                }

            }
            for (int i = 0; i < tam; i++) {
                x = rand.Randfloat(0, (float) 1.01);
                if (x < probmutacion) {
                    while (i == (p = rand.Randint(0, tam - 1)));
                    if (p >= tam) {
                        p = tam - 1;
                    }
                    Mutacion(hijo2, i, p);
                }

            }

            costeh1 = coste(hijo1, flu, loc);
            costeh2 = coste(hijo2, flu, loc);

            if (costeh2 < costeh1) {
                int aux;
                aux = (int) costeh1;
                costeh1 = costeh2;
                costeh2 = aux;
                int[] aux2 = new int[hijo1.length];
                aux2 = hijo1;
                hijo1 = hijo2;
                hijo2 = aux2;

            }

            cp1 = rand.Randint(0, ncromosomas - 1);
            cp2 = rand.Randint(0, ncromosomas - 1);
            cp3 = rand.Randint(0, ncromosomas - 1);
            cp4 = rand.Randint(0, ncromosomas - 1);
            c1 = costes[cp1];
            c2 = costes[cp2];
            c3 = costes[cp3];
            c4 = costes[cp4];

            if (c1 > c2 && c1 > c3 && c1 > c4) {
                peorpadre = cp1;
                costepeorpadre1 = costes[cp1];
                costes[cp1] = 1;
            } else if (c2 > c1 && c2 > c3 && c2 > c4) {
                peorpadre = cp2;
                costepeorpadre1 = costes[cp2];
                costes[cp2] = 1;
            } else if (c3 > c1 && c3 > c2 && c3 > c4) {
                peorpadre = cp3;
                costepeorpadre1 = costes[cp3];
                costes[cp3] = 1;
            } else {
                peorpadre = cp4;
                costepeorpadre1 = costes[cp4];
                costes[cp4] = 1;
            }
            //eleccion del segundo padre

            cp1 = rand.Randint(0, ncromosomas - 1);
            if (cp1 >= ncromosomas) {
                cp1 = ncromosomas - 1;
            }
            cp2 = rand.Randint(0, ncromosomas - 1);
            if (cp2 >= ncromosomas) {
                cp2 = ncromosomas - 1;
            }
            cp3 = rand.Randint(0, ncromosomas - 1);
            if (cp3 >= ncromosomas) {
                cp3 = ncromosomas - 1;
            }
            cp4 = rand.Randint(0, ncromosomas - 1);
            if(cp4>=ncromosomas){
                cp4=ncromosomas-1;
            }
            c1 = costes[cp1];
            c2 = costes[cp2];
            c3 = costes[cp3];
            c4 = costes[cp4];

            if (c1 > c2 && c1 > c3 && c1 > c4) {
                peorpadre2 = cp1;
                costepeorpadre2 = costes[cp1];

            } else if (c2 > c1 && c2 > c3 && c2 > c4) {
                peorpadre2 = cp2;
                costepeorpadre2 = costes[cp2];

            } else if (c3 > c1 && c3 > c2 && c3 > c4) {
                peorpadre2 = cp3;
                costepeorpadre2 = costes[cp3];

            } else {
                peorpadre2 = cp4;
                costepeorpadre2 = costes[cp4];

            }

            if (costepeorpadre2 > costepeorpadre1) {
                float aux;
                aux = costepeorpadre1;
                costepeorpadre1 = costepeorpadre2;
                costepeorpadre2 = aux;
                int aux2;
                aux2 = peorpadre2;
                peorpadre2 = peorpadre;
                peorpadre = aux2;
            }

            if (costeh1 < costepeorpadre1) {
                cromosomas[peorpadre] = hijo1;
                costes[peorpadre] = costeh1;
                if (costeh2 < costepeorpadre2) {
                    cromosomas[peorpadre2] = hijo2;
                    costes[peorpadre2] = costeh2;
                }
            }

            if (costeh1 < mejorCo) {
                mejorCo = costeh1;
                mejor = hijo1;

            }

        }

        s = mejor;
        long finalGR = System.currentTimeMillis();
        log.append("Coste Final AGEcrucePMX: " + mejorCo + "\n");
        log.append("Solucion AGEcrucePMX:" + "\n");
        for (int i = 0; i < tam; i++) {
            log.append(" - solucionPMX[" + i + "] = " + s[i] + "\n");
        }
        log.append("Tiempo de Ejecucion: " + (finalGR - inicioGR) + " ms\n");
    }

    void crucePmx(int[] a, int[] b) {
        int[] r1, r2;
        int p1, p2;
        int p = 0;
        int tam = a.length;
        r1 = new int[tam];
        r2 = new int[tam];
        p1 = rand.Randint(1, tam - 2);
        while (p1 == (p2 = rand.Randint(0, tam - 1)));

        if (p1 > p2) {
            int aux;
            aux = p1;
            p1 = p2;
            p2 = aux;
        }

        for (int i = 0; i < tam; i++) {
            if (i >= p1 && i <= p2) {
                r1[i] = b[i];
                r2[i] = a[i];
            }
        }

//parte de reparacion posterior
        for (int i = p2 + 1; i < tam; i++) {
            int x = a[i];
            while ((p = busca2(r1, x, p1, p2, p)) != -1) {   //hijo 1
                x = r2[p];
            }
            r1[i] = x;

            x = b[i];
            while ((p = busca2(r2, x, p1, p2, p)) != -1) {   //hijo 2
                x = r1[p];
            }
            r2[i] = x;
        }
        //parte de reparacion previa
        for (int i = 0; i < p1; i++) {
            int x = a[i];
            while ((p = busca2(r1, x, p1, p2, p)) != -1) {   //hijo 1
                x = r2[p];
            }
            r1[i] = x;

            x = b[i];
            while ((p = busca2(r2, x, p1, p2, p)) != -1) {   //hijo 2
                x = r1[p];
            }
            r2[i] = x;
        }
        a = r1;
        b = r2;
    }

    void Mutacion(int[] sol, int r, int s
    ) {
        int aux;
        aux = sol[r];
        sol[r] = sol[s];
        sol[s] = aux;

    }

    boolean busca(int[] v, int x, int p1, int p2, int p
    ) {

        for (int i = p1; i <= p2; i++) {
            if (x == v[i]) {
                p = i;
                return true;
            }
        }
        return false;
    }

    int busca2(int[] v, int x, int p1, int p2, int p
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

    float coste(int[] s, float[][] flu, float[][] dis
    ) {
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

    void intercambia(int v, int b
    ) {
        int aux;
        aux = v;
        v = b;
        b = aux;

    }

    void muestra(int[] s
    ) {
        for (int i = 0; i < s.length; i++) {

            System.out.println(i);
            System.out.println(s[i]);
        }
    }

    public String getLog() {
        return log.toString();
    }
}
