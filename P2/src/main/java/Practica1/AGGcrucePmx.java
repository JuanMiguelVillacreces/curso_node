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
public class AGGcrucePmx {

    private Ramdom rand;
    private StringBuilder log;

    public AGGcrucePmx(long _semilla) {
        rand = new Ramdom();
        rand.Set_random(_semilla);
        log = new StringBuilder();
    }

    void AGG0pmx(float[][] flu, float[][] loc, int tam, int ncromosomas, int ncromosomasaleatorios, long semilla, float probmuta, float probcruce, int evaluaciones, int[] s, int iteraciones) {

        log.append("INICIO EJECUCION: Algoritmo AGGcrucePMX\n");
        log.append(" - Nombre del fichero: " + "\n");
        log.append(" - Semilla utilizada: " + semilla + "\n");
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
            gr.SoluGreedyAleatorizado(flu, loc, tam, iteraciones, cromosomas[i]);
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
            //durante kCromosomas enfrentamientos
            for (int k = 0, i, j; k < ncromosomas; k++) {
                i = rand.Randint(0, ncromosomas - 1);
                while (i == (j = rand.Randint(0, ncromosomas - 1)));
                if( j>=ncromosomas){
                    j = rand.Randint(0, ncromosomas - 1);
                }
                posi[k] = (costes[i] < costes[j]) ? i : j;
            }

            //Nos quedamos con los cromosomas mas prometedores y sus costes
            for (int i = 0; i < ncromosomas; i++) {
                nuevag[i] = cromosomas[posi[i]];
                costesH[i] = costes[posi[i]];
            }

            //CRUZAMOS los padres seleccionados con una probabilidad kProbAGG
            float x;
            int p1;
            boolean[] marcados = new boolean[ncromosomas];
            for (int i = 0; i < ncromosomas; i++) {
                marcados[i] = false;
            }
            for (int i = 0; i < ncromosomas; i++) {
                x = rand.Randfloat(0, (float) 1.01);
                if (x < probcruce) {
                    while (i == (p1 = rand.Randint(0, (int) (ncromosomas - 1))));
                    if (p1 >= ncromosomas ) {
                        p1 = rand.Randint(0, (int) (ncromosomas - 1));
                    }
                    crucePmx(nuevag[i], nuevag[p1]);
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
                        while (j == (p1 = rand.Randint(0, tam - 1)));
                        if (p1 >= tam) {
                            p1 = tam-1;
                        }
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
        }
        s = mejorCroGlobal;

        long finalGR = System.currentTimeMillis();
        log.append("Coste Final AGGcrucePMX: " + mejorCo + "\n");
        log.append("Solucion AGGcrucePMX:" + "\n");
        for (int i = 0; i < tam; i++) {
            log.append(" - solucionAGGcrucePMX[" + i + "] = " + s[i] + "\n");
        }
        log.append("Tiempo de Ejecucion: " + (finalGR - inicioGR) + " ms\n");

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

    void crucePmx(int[] a, int[] b) {
        int[] r1, r2;
        int p1, p2;
        int p = 0;
        int tam = a.length;
        r1 = new int[tam];
        r2 = new int[tam];
        p1 = rand.Randint(1, tam - 2);
        while (p1 == (p2 = rand.Randint(0, tam - 1)));  //probarlo

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

    public String getLog() {
        return log.toString();
    }
}
