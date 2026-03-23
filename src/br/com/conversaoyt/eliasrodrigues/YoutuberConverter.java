package br.com.conversaoyt.eliasrodrigues;

import java.util.Scanner;
import java.io.IOException;

public class YoutuberConverter {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);

        System.out.println("Converta seu vídeo!!");

        System.out.println("Cole a sua URl: ");
        String URL = sc.nextLine().trim(); // .trim() remove espaços extras

        if(URL.isEmpty()){
            System.out.println("Insira uma URl válida: ");
            return;
        }

        System.out.println("Digite 1 para MP4 ou 2 para MP3");
        String escolha = sc.nextLine().trim();

        String []comando;

        if(escolha.equals("1")){
            comando = new String[]{
                "yt-dlp",
                "--format", "bestvideo[ext=mp4]+bestaudio[ext=mp4a]/best[ext=mp4]",
                "--merge-output-format", "mp4", URL
            };
        System.out.println("\nBaixando em MP4...");
        }
        else if(escolha.equals("2")){
            comando = new String[]{
                "yt-dlp",
                "--extract-audio",
                "--audio-format", "mp3",
                "--audio-quality", "0", URL
            };
        System.out.println("\nBaixando em MP3...");
        } else {
            System.out.println("Digite a opção 1 ou 2.");
            return;
        }

        executarComando(comando);

        sc.close();
    }

    static void executarComando(String [] comando){
        try {
            ProcessBuilder pb = new ProcessBuilder(comando);
            pb.redirectErrorStream(true);
            Process processo = pb.start();

            java.io.BufferedReader leitor = new java.io.BufferedReader(
                    new java.io.InputStreamReader(processo.getInputStream())
            );

            String linha;
            while((linha = leitor.readLine()) != null){
                System.out.println(linha);
            }

            int codigoSaida = processo.waitFor();

            if (codigoSaida == 0){
                System.out.println("Download feito com sucesso.");
                System.out.println("O arquivo foi salvo onde o programa foi rodado");
            } else {
                System.out.println("Algo deu Errado. Código de erro: " + codigoSaida);
            }

        } catch(IOException e){
            System.out.println("Erro: não foi possível executar o yt-dlp.");
            System.out.println("Verifique se está instalado no PATH do sistema.");
            System.out.println(e.getMessage());

        } catch(InterruptedException e){
            System.out.println("Aconteceu algo errado.");
        }
    }
}
