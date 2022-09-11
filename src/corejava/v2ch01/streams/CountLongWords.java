package corejava.v2ch01.streams;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class CountLongWords {
    public static void main(String[] args) throws IOException {
        // прочитать текст из файла в символьную строку
        String contents = new String(Files.readAllBytes(
                Paths.get("src/corejava/gutenberg/alice30.txt")), StandardCharsets.UTF_8);
        // разбить полученную символьную строку на слова;
        // небуквенные символы считаются разделителями
        List<String> words = Arrays.asList(contents.split("\\PL+"));

        // перебираем слова таким образом
        long count = 0;
        for (String w : words) {
            if (w.length() > 12) count++;
        }
        System.out.println(count);

        // то же самое, с помощью потоков
        count = words.stream()
                .filter(w -> w.length() > 12)
                .count();
        System.out.println(count);

        // параллельное выполнение операции фильтрации и подсчета слов
        count = words.parallelStream()
                .filter(w -> w.length() > 12)
                .count();
        System.out.println(count);
    }
}