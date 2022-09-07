package corejava.v1ch14.threadPool;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;

/**
 * @version 1.02 2015-06-21
 * @author Cay Horstmann
 */
public class ThreadPoolTest {
    public static void main(String[] args) {
        try (Scanner in = new Scanner(System.in)) {
            System.out.print("Enter base directory (e.g. /usr/local/jdk5.0/src): ");
            String directory = in.nextLine();
            System.out.print("Enter keyword (e.g. volatile): ");
            String keyword = in.nextLine();

            ExecutorService pool = Executors.newCachedThreadPool();

            MatchCounter counter = new MatchCounter(new File(directory), keyword, pool);
            Future<Integer> result = pool.submit(counter);

            try {
                System.out.println(result.get() + " matching files.");
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException ignored) { }
            pool.shutdown();

            int largestPoolSize = ((ThreadPoolExecutor) pool).getLargestPoolSize();
            System.out.println("largest pool size=" + largestPoolSize);
        }
    }
}

/**
 * Подсчитывает файлы, содержащие заданное ключевое слово,
 * в каталоге и его подкаталогах
 */
class MatchCounter implements Callable<Integer> {
    private File directory;
    private String keyword;
    private ExecutorService pool;
    private int count;

    /**
    * Конструирует объект типа MatchCounter
    * @param directory Каталог, с которого начинается поиск
    * @param keyword Искомое ключевое слово
    * @param pool Пул потоков исполнения для передачи подзадач
    */
    public MatchCounter(File directory, String keyword, ExecutorService pool) {
        this.directory = directory;
        this.keyword = keyword;
        this.pool = pool;
    }

    @Override
    public Integer call() {
        count = 0;
        try {
            File[] files = directory.listFiles();
            List<Future<Integer>> results = new ArrayList<>();

            for (File file : files) {
                if (file.isDirectory()) {
                    MatchCounter counter = new MatchCounter(file, keyword, pool);
                    Future<Integer> result = pool.submit(counter);
                    results.add(result);
                } else {
                    if (search(file)) count++;
                }
            }

            for (Future<Integer> result : results) {
                try {
                    count += result.get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        } catch (InterruptedException ignored) { }
        return count;
    }

    /**
    * Осуществляет поиск заданного ключевого слова в файлах
    * @param file Файл для поиска ключевого слова
    * @return Возвращает логическое значение true, если
     *        ключевое слово содержится в файле
    */
    public boolean search(File file) {
        try (Scanner in = new Scanner(file, StandardCharsets.UTF_8)) {
            boolean found = false;
            while (!found && in.hasNextLine()) {
                String line = in.nextLine();
                if (line.contains(keyword)) found = true;
            }
            return found;
        } catch (IOException e) {
            return false;
        }
    }
}