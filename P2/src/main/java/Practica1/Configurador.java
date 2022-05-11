/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Practica1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 *
 */
public class Configurador {

    private ArrayList<String> archivos;
    private ArrayList<String> algoritmos;
    private ArrayList<Long> semillas;
    private int numerocromosomas;
    private int cromosomasaleatorios;
    private float probabilidadmutacion;
    private float probabilidadcruce;
    private int numevaluaciones;
    private int numiteraciones;

    public Configurador(String ruta) {
        archivos = new ArrayList<>();
        algoritmos = new ArrayList<>();
        semillas = new ArrayList<>();

        String linea;
        FileReader f = null;
        try {
            f = new FileReader(ruta);
            BufferedReader b = new BufferedReader(f);
            while ((linea = b.readLine()) != null) {
                String[] split = linea.split("=");
                switch (split[0]) {
                    case "archivos":
                        String[] _archivos = split[1].split(",");
                        for (int i = 0; i < _archivos.length; i++) {
                            archivos.add(_archivos[i]);
                        }
                        break;
                    case "algoritmos":
                        String[] _algoritmos = split[1].split(",");
                        for (int i = 0; i < _algoritmos.length; i++) {
                            algoritmos.add(_algoritmos[i]);
                        }
                        break;
                    case "semillas":
                        String[] _semillas = split[1].split(",");
                        for (int i = 0; i < _semillas.length; i++) {
                            semillas.add(Long.parseLong(_semillas[i]));
                        }
                        break;
                    case "numerocromosomas":
                        numerocromosomas = Integer.parseInt(split[1]);
                        break;
                    case "cromosomasaleatorios":
                        cromosomasaleatorios = Integer.parseInt(split[1]);
                        break;
                    case "probabilidadmutacion":
                        probabilidadmutacion = Float.parseFloat(split[1]);
                        break;
                    case "probabilidadcruce":
                        probabilidadcruce = Float.parseFloat(split[1]);
                        break;
                    case "numevaluaciones":
                        numevaluaciones = Integer.parseInt(split[1]);
                        break;
                    case "numiteraciones":
                        numiteraciones = Integer.parseInt(split[1]);
                        break;
                }
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public ArrayList<String> getArchivos() {
        return archivos;
    }

    public ArrayList<String> getAlgoritmos() {
        return algoritmos;
    }

    public ArrayList<Long> getSemillas() {
        return semillas;
    }

    public int getNumerocromosomas() {
        return numerocromosomas;
    }

    public void setNumerocromosomas(int numerocromosomas) {
        this.numerocromosomas = numerocromosomas;
    }

    public int getCromosomasaleatorios() {
        return cromosomasaleatorios;
    }

    public void setCromosomasaleatorios(int cromosomasaleatorios) {
        this.cromosomasaleatorios = cromosomasaleatorios;
    }

    public float getProbabilidadmutacion() {
        return probabilidadmutacion;
    }

    public void setProbabilidadmutacion(int probabilidadmutacion) {
        this.probabilidadmutacion = probabilidadmutacion;
    }

    public float getProbabilidadcruce() {
        return probabilidadcruce;
    }

    public void setProbabilidadcruce(int probabilidadcruce) {
        this.probabilidadcruce = probabilidadcruce;
    }

    public int getNumevaluaciones() {
        return numevaluaciones;
    }

    public void setNumevaluaciones(int numevaluaciones) {
        this.numevaluaciones = numevaluaciones;
    }

    public int getNumiteraciones() {
        return numiteraciones;
    }

    public void setNumiteraciones(int numiteraciones) {
        this.numiteraciones = numiteraciones;
    }

}
