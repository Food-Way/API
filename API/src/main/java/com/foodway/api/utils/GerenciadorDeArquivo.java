package com.foodway.api.utils;

import com.foodway.api.model.Establishment;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Formatter;
import java.util.FormatterClosedException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class GerenciadorDeArquivo {
    public static void gravaArquivoCsv(ListaObj<Establishment> lista, String nomeArq) {
        FileWriter arq = null;
        Formatter saida = null;
        Boolean deuRuim = false;

        nomeArq += ".csv";

        // Bloco try-catch para abrir o arquivo
        try {
            arq = new FileWriter(nomeArq);
            saida = new Formatter(arq);
        } catch (IOException erro) {
            System.out.println("Erro ao abrir o arquivo");
            System.exit(1);
        }

        // Bloco try-catch para gravar o arquivo
        try {
            for (int i = 0; i < lista.getTamanho(); i++) {

                //Recupere um elemento da lista e formate aqui:
                Establishment establishment = lista.getElemento(i);
                saida.format("%s;%s;%s;%s;%s;%s;%s;%s;%.2f;%s;%s;%s\n",

                        establishment.getIdUser(),
                        establishment.getName(),
                        establishment.getEstablishmentName(),
                        establishment.getEmail(),
                        establishment.getTypeUser(),
                        establishment.getCep(),
                        establishment.getNumber(),
                        establishment.getComplement(),
                        establishment.getRate(),
                        establishment.getCnpj(),
                        establishment.getCreatedAt(),
                        establishment.getUpdatedAt()
                );
            }
        } catch (FormatterClosedException erro) {
            System.out.println("Erro ao gravar o arquivo");
            deuRuim = true;
        } finally {
            saida.close();
            try {
                arq.close();
            } catch (IOException erro) {
                System.out.println("Erro ao fechar o arquivo");
                deuRuim = true;
            }
            if (deuRuim) {
                System.exit(1);
            }
        }
    }

    public static void leArquivoCsv(String nomeArq) {
        FileReader arq = null;
        Scanner entrada = null;
        Boolean deuRuim = false;

        nomeArq += ".csv";

        // Bloco try-catch para abrir o arquivo
        try {
            arq = new FileReader(nomeArq);
            entrada = new Scanner(arq).useDelimiter(";|\\n");
        } catch (FileNotFoundException erro) {
            System.out.println("Arquivo nao encontrado");
            System.exit(1);
        }

        // Bloco try-catch para ler o arquivo
        try {
            //Leia e formate a saída no console aqui:
            // Cabeçalho
            System.out.printf("%-40s %-20s %-30s %-30s %-15s %-10s %-8s %-15s %-5s %-15s %-28s %-28s\n",
                    "ID", "NAME", "ESTABLISHMENT NAME", "EMAIL", "TYPE USER", "CEP", "NUMBER", "COMPLEMENT", "RATE", "CNPJ", "CREATED AT", "UPDATED AT");
            while (entrada.hasNext()) {

                String id = entrada.next();
                String name = entrada.next();
                String establishmentName = entrada.next();
                String email = entrada.next();
                String typeUser = entrada.next();
                String cep = entrada.next();
                String number = entrada.next();
                String complement = entrada.next();
                Double rate = entrada.nextDouble();
                String cnpj = entrada.next();
                String createdAt = entrada.next();
                String updatedAt = entrada.next();

                System.out.printf("%-40s %-20s %-30s %-30s %-15s %-10s %-8s %-15s %-5.2f %-15s %-28s %-28s\n",
                        id, name, establishmentName, email, typeUser, cep, number, complement, rate, cnpj, createdAt, updatedAt);
            }
        } catch (NoSuchElementException erro) {
            System.out.println("Arquivo com problemas");
            deuRuim = true;
        } catch (IllegalStateException erro) {
            System.out.println("Erro na leitura do arquivo");
            deuRuim = true;
        } finally {
            entrada.close();
            try {
                arq.close();
            } catch (IOException erro) {
                System.out.println("Erro ao fechar o arquivo");
                deuRuim = true;
            }
            if (deuRuim) {
                System.exit(1);
            }
        }
    }

    public static void gravaArquivoTxt(ListaObj<Establishment> lista, String nomeArq) {
        FileWriter arq = null;
        Formatter saida = null;
        Boolean deuRuim = false;

        String version = "01";
        String context = "ESTABLISHMENTS";
        String actualDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));

        nomeArq += ".txt";

        // Bloco try-catch para abrir o arquivo
        try {
            arq = new FileWriter(nomeArq);
            saida = new Formatter(arq);
        } catch (IOException erro) {
            System.out.println("Erro ao abrir o arquivo");
            System.exit(1);
        }

        // Bloco try-catch para gravar o arquivo
        try {
            saida.format("00%s%s%s\n", context, actualDateTime, version);
            for (int i = 0; i < lista.getTamanho(); i++) {

                //Recupere um elemento da lista e formate aqui:
                Establishment establishment = lista.getElemento(i);
                saida.format("02%s%s%s%s%s%s%s%s%s\n",
                        establishment.getIdUser(),
                        establishment.getName(),
                        establishment.getEmail(),
                        establishment.getTypeUser(),
                        establishment.getCep(),
                        establishment.getNumber(),
                        establishment.getComplement(),
                        establishment.getRate(),
                        establishment.getCnpj());
            }
            saida.format("01%010d", lista.getTamanho());
        } catch (FormatterClosedException erro) {
            System.out.println("Erro ao gravar o arquivo");
            deuRuim = true;
        } finally {
            saida.close();
            try {
                arq.close();
            } catch (IOException erro) {
                System.out.println("Erro ao fechar o arquivo");
                deuRuim = true;
            }
            if (deuRuim) {
                System.exit(1);
            }
        }
    }

    // TODO - Implementar leitura de arquivo txt
    public static void leArquivoTxt(String nomeArq) {
        FileReader arq = null;
        Scanner entrada = null;
        Boolean deuRuim = false;

        nomeArq += ".txt";

        // Bloco try-catch para abrir o arquivo
        try {
            arq = new FileReader(nomeArq);
            entrada = new Scanner(arq).useDelimiter(";|\\n");
        } catch (FileNotFoundException erro) {
            System.out.println("Arquivo nao encontrado");
            System.exit(1);
        }

        // Bloco try-catch para ler o arquivo
        try {
            //Leia e formate a saída no console aqui:

            // Cabeçalho
            System.out.printf("%-40s %-20s %-30s %-15s %-10s %-8s %-15s %-5s %-15s\n",
                    "Id", "Name", "Email", "TypeUser", "Cep", "Number", "Complement", "Rate", "Cnpj");

            while (entrada.hasNext()) {

                String id = entrada.next();
                String name = entrada.next();
                String email = entrada.next();
                String typeUser = entrada.next();
                String cep = entrada.next();
                String number = entrada.next();
                String complement = entrada.next();
                String rate = entrada.next();
                String cnpj = entrada.next();

                System.out.printf("%-40s %-20s %-30s %-15s %-10s %-8s %-15s %-5s %-15s\n",
                        id, name, email, typeUser, cep, number, complement, rate, cnpj);

            }
        } catch (NoSuchElementException erro) {
            System.out.println("Arquivo com problemas");
            deuRuim = true;
        } catch (IllegalStateException erro) {
            System.out.println("Erro na leitura do arquivo");
            deuRuim = true;
        } finally {
            entrada.close();
            try {
                arq.close();
            } catch (IOException erro) {
                System.out.println("Erro ao fechar o arquivo");
                deuRuim = true;
            }
            if (deuRuim) {
                System.exit(1);
            }
        }
    }
}
