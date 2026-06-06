package com.gestaopedidos;

import com.gestaopedidos.thread.ProcessadorPedidos;
import com.gestaopedidos.ui.MenuPrincipal;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        ProcessadorPedidos processador = new ProcessadorPedidos();
        Thread thread = new Thread(processador);
        thread.setDaemon(true);
        thread.start();

        Scanner scanner = new Scanner(System.in);
        MenuPrincipal menu = new MenuPrincipal(scanner);
        menu.exibir();

        scanner.close();
    }
}