/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Practica1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
//import static jdk.nashorn.internal.objects.NativeString.trim;

/**
 *
 *
 */
public class ArchivoDatos {

    private String nombreFichero;
    private float matrizFlujos[][];
    private float matrizDistancias[][];
    private int tam;

    public ArchivoDatos(String ruta) {

        nombreFichero = ruta;
        FileReader f = null;
        try {
            f = new FileReader(ruta);
            Scanner s = new Scanner(f);
            tam = s.nextInt();
            matrizFlujos = new float[tam][tam];
            matrizDistancias = new float[tam][tam];

            for (int i = 0; i < tam; i++) {
                for (int j = 0; j < tam; j++) {

                    matrizFlujos[i][j] = s.nextFloat();
                }
            }
            for (int i = 0; i < tam; i++) {
                for (int j = 0; j < tam; j++) {

                    matrizDistancias[i][j] = s.nextFloat();
                }
            }
        } catch (IOException e) {
            System.out.println(e);
        }
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

    /**
     * @return the nombreFichero
     */
    public String getNombreFichero() {
        return nombreFichero;
    }

    /**
     * @param nombreFichero the nombreFichero to set
     */
    public void setNombreFichero(String nombreFichero) {
        this.nombreFichero = nombreFichero;
    }

    /**
     * @return the matrizFlujos
     */
    public float[][] getMatrizFlujos() {
        return matrizFlujos;
    }

    /**
     * @param matrizFlujos the matrizFlujos to set
     */
    public void setMatrizFlujos(float[][] matrizFlujos) {
        this.matrizFlujos = matrizFlujos;
    }

    /**
     * @return the matrizDistancias
     */
    public float[][] getMatrizDistancias() {
        return matrizDistancias;
    }

    /**
     * @param matrizDistancias the matrizDistancias to set
     */
    public void setMatrizDistancias(float[][] matrizDistancias) {
        this.matrizDistancias = matrizDistancias;
    }

    /**
     * @return the tam
     */
    public int getTam() {
        return tam;
    }

    /**
     * @param tam the tam to set
     */
    public void setTam(int tam) {
        this.tam = tam;
    }

}
