package trabalho;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);

        System.out.println("Digite o nome do arquivo:");
        String fileName = scn.next();

        if (!fileName.contains(".bas")) {
            System.out.println("Dica: add .bas no final do nome do arquivo.");
        }

        String rnFileName = rnFileName(fileName);

        List<String> lines = loadFile(fileName);
        renum(lines);
        saveRnFile(lines, rnFileName);

        System.out.println("Feito!");
    }

    private static List<String> loadFile(String fileName) {
        List<String> lines = new ArrayList<>();
        Path path = Path.of(fileName).toAbsolutePath();

        try (Scanner reader = new Scanner(Files.newBufferedReader(path, Charset.defaultCharset()))) {
            while (reader.hasNextLine()) {
                lines.add(reader.nextLine());
            }
        } catch (IOException e) {
            System.err.format("Erro de E/S: %s%n", e);
            return null;
        }

        return lines;
    }

    private static void saveRnFile(List<String> lines, String rnFileName) {
        if (lines == null) {
            throw new RuntimeException("Não foi possível encontrar o arquivo.");
        }

        Path path = Path.of(rnFileName).toAbsolutePath();
        try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(path, Charset.defaultCharset()))) {
            for (String line : lines) {
                writer.println(line);
            }
        } catch (IOException e) {
            System.err.format("Erro de E/S: %s%n", e);
        }
    }

    private static void renum(List<String> lines) {
        if (lines == null) {
            throw new RuntimeException("Não foi possível encontrar o arquivo.");
        }

        for (int i = 0; i < lines.size(); i++) {
            String[] tokens = lines.get(i).split(" ");

            if (lines.size() < 9) {
                tokens[0] = String.valueOf((i + 1) * 10);
            }

            else if (lines.size() < 99) {
                if (i < 9) {
                    tokens[0] = "0" + ((i + 1) * 10);
                } else {
                    tokens[0] = String.valueOf((i + 1) * 10);
                }
            }

            else if (lines.size() < 999) {
                if (i < 9) {
                    tokens[0] = "00" + ((i + 1) * 10);
                } else if (i < 99) {
                    tokens[0] = "0" + ((i + 1) * 10);
                } else {
                    tokens[0] = String.valueOf((i + 1) * 10);
                }
            }

            else {
                if (i < 9) {
                    tokens[0] = "000" + ((i + 1) * 10);
                } else if (i < 99) {
                    tokens[0] = "00" + ((i + 1) * 10);
                } else if (i < 999) {
                    tokens[0] = "0" + ((i + 1) * 10);
                } else {
                    tokens[0] = String.valueOf((i + 1) * 10);
                }
            }

            StringBuilder stringBuilder = new StringBuilder();
            for (String token : tokens) {
                stringBuilder.append(token);
                stringBuilder.append(" ");
            }

            lines.set(i, stringBuilder.toString());
        }
    }

    private static String rnFileName(String fileName) {
        StringBuilder rnFileNameBuilder = new StringBuilder();

        rnFileNameBuilder.append(fileName);
        rnFileNameBuilder.delete(rnFileNameBuilder.length() - 4, rnFileNameBuilder.length());
        rnFileNameBuilder.append("-rn.bas");

        return rnFileNameBuilder.toString();
    }
}
