package io.github.katarem;

import java.util.Scanner;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class App {

    private static final Executor executor = Executors.newFixedThreadPool(2);
    private static final long DELAY = 600L;
    private static final char[] abecedary = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'ñ', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    private static boolean continuarContando = true;

    // valores para el programa
    private static int conteoInicial;
    private static char letra;

    private static Runnable conteoRegresivo(int initialValue) {
        return () -> {
            try {
                int conteo = initialValue;
                while (continuarContando) {
                    System.out.println("[HILO 2]: Conteo " + conteo + " desde " + initialValue);
                    conteo--;
                    Thread.sleep(DELAY);
                }
                System.out.println("Trabajo del hilo 2 terminado.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        };
    }

    private static Runnable buscarLetra(char characterToFind) {
        return () -> {
            try {
                int index = 0;
                while (abecedary[index] != characterToFind) {
                    System.out.println("[HILO 1]: " + abecedary[index]);
                    index++;
                    Thread.sleep(DELAY);
                }
                System.out.println("Letra encontrada! " + abecedary[index]);
                System.out.println("Trabajo del hilo 1 terminado.");
                continuarContando = false;
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        };
    }

    private static void obtenerValores(){
        boolean continuar = true;
        Scanner entrada = new Scanner(System.in);
        while(continuar){
            try {
                System.out.println("Bienvenido al busca letras, escriba la letra a buscar en el abecedario");
                letra = entrada.next().charAt(0);
                System.out.println("Ahora escriba un número para contar para atrás");
                conteoInicial = entrada.nextInt();
                continuar = false;
            } catch (Exception e) {
                System.out.println("El valor introducido es inválido, se reinicia el programa.");
                entrada.nextLine();
            }
        }
        entrada.close();
    }

    public static void main(String[] args) {
        obtenerValores();
        executor.execute(buscarLetra(letra));
        executor.execute(conteoRegresivo(conteoInicial));
        System.out.println("Programa esperando a que terminen los hilos por su cuenta");
    }
}
