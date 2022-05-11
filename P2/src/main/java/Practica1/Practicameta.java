/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Practica1;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 *
 */
public class Practicameta {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Creamos el configurador y cargamos la configuracion.
        Configurador config = new Configurador(args[0]);
        int ncromosomas = config.getNumerocromosomas();
        int ncromosomasaleatorios = config.getCromosomasaleatorios();
        float probmuta = config.getProbabilidadmutacion();
        float probcruce = config.getProbabilidadcruce();
        int iteraciones = config.getNumiteraciones();
        int evaluaciones = config.getNumevaluaciones();
        // Por cada archivo.
        for (int i = 0; i < config.getArchivos().size(); i++) {
            // Por cada algoritmo.

            for (int j = 0; j < config.getAlgoritmos().size(); j++) {
                // Por cada semilla.
                for (int k = 0; k < config.getSemillas().size(); k++) {
                    // Creamos el archivo de datos correspondiente.
                    ArchivoDatos archivo = new ArchivoDatos(config.getArchivos().get(i));
                    // Creamos la ruta para el log.

                    String texto[] = config.getArchivos().get(i).split("/");
                    String nombreFichero = texto[1];
                    long semilla = config.getSemillas().get(k);
                    String fichero = "logs/" + config.getAlgoritmos().get(j) + "_" + nombreFichero + "_" + semilla + ".txt";
                    //  Verificamos que algoritmo vamos a usar, lo creamos y lo ejecutamos.
//,data/nissan.dat,data/nissan.dat,data/nissan03.dat,data/nissan04.dat
//,95902650,26507736,67663773
//data/ford01.dat,data/ford02.dat,AGEcruceOx,,AGGcrucePMXdata/nissan01.dat,
//,data/nissan03.dat,data/nissan04.dat
//77367663,73676637,76637736,36766377,
//77367663,73676637,36766377,
                    switch (config.getAlgoritmos().get(j)) {
                        case "AGEcruceOx":
                            int[] s = new int[archivo.getTam()];
                            AGEcruceOx age = new AGEcruceOx(semilla);
                            age.cruceox(archivo.getMatrizFlujos(), archivo.getMatrizDistancias(), archivo.getTam(), ncromosomas, ncromosomasaleatorios, semilla, probmuta, evaluaciones, s, iteraciones);
                            System.out.println(age.getLog());
                            createLog(fichero, age.getLog());
                            break;
                        case "AGEcrucePMX":
                            int[] v = new int[archivo.getTam()];
                            AGEcrucePmx agepmx = new AGEcrucePmx(semilla);
                            agepmx.crucepmx(archivo.getMatrizFlujos(), archivo.getMatrizDistancias(), archivo.getTam(), ncromosomas, ncromosomasaleatorios, semilla, probmuta, evaluaciones, v, iteraciones);
                            System.out.println(agepmx.getLog());
                            createLog(fichero, agepmx.getLog());
                            break;
                        case "AGGcrucePMX":
                            int[] w = new int[archivo.getTam()];
                            AGGcrucePmx agGpmx = new AGGcrucePmx(semilla);
                            agGpmx.AGG0pmx(archivo.getMatrizFlujos(), archivo.getMatrizDistancias(), archivo.getTam(), ncromosomas, ncromosomasaleatorios, semilla, probmuta, probcruce, evaluaciones, w, iteraciones);
                            System.out.println(agGpmx.getLog());
                            createLog(fichero, agGpmx.getLog());
                            break;

                        case "AGGcruceOX2":
                            int[] t = new int[archivo.getTam()];
                            AGGcruceOx2 agGox2 = new AGGcruceOx2(semilla);
                            agGox2.AGG0x2(archivo.getMatrizFlujos(), archivo.getMatrizDistancias(), archivo.getTam(), ncromosomas, ncromosomasaleatorios, semilla, probmuta, probcruce, evaluaciones, t, iteraciones);
                            System.out.println(agGox2.getLog());
                            createLog(fichero, agGox2.getLog());
                            break;

                    }
                }
            }
        }


    }

    public static void createLog(String fichero, String texto) {
        try {
            File file = new File(fichero);
            if (!file.exists()) {
                file.createNewFile();
            }
            PrintWriter pw = new PrintWriter(file);
            pw.println(texto);
            pw.close();
            System.out.println("Log escrito con exito.");
        } catch (IOException e) {
            System.out.println("No se pudo escribir el log.");
        }
    }
}
