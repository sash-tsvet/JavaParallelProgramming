import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by kitty on 31.05.2017.
 */
public class Solution {
    public static void main(String[] args) {
        File rootDir = new File("C:\\Users\\kitty\\IdeaProjects");
        List<String> result = new ArrayList<>();
        Queue<File> fileTree = new PriorityQueue<>();

        Collections.addAll(fileTree, rootDir.listFiles());

        while (!fileTree.isEmpty())
        {
            File currentFile = fileTree.remove();
            if(currentFile.isDirectory()){
                Collections.addAll(fileTree, currentFile.listFiles());
            } else {
                result.add(currentFile.getAbsolutePath());
            }
        }
        for (String element : result) {
//            System.out.println(element);
            new Thread(new Worker(element)).start();
        }
    }

    public static class Worker implements Runnable {
        String path;
        Worker(String path){
            this.path = path;
        }

        @Override
        public void run() {
            Charset charset = StandardCharsets.UTF_8;
            String search = "implements";
            String replace = "**********";
            try {
//                System.out.println(new String(Files.readAllBytes(Paths.get(path))).contains(search.toLowerCase()));
                if (new String(Files.readAllBytes(Paths.get(path))).contains(search.toLowerCase())) {
//                    System.out.println(new String(Files.readAllBytes(Paths.get(path))).replace(search, replace));
                    System.out.println(path);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
