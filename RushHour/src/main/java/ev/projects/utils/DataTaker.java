package ev.projects.utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class DataTaker {

    public static void takeSamples(String path, int countOfEachGroup) {
        File directory = new File(path);
        File[] maps = directory.listFiles();
        HashMap<Integer, List<File>> groupedMaps = new HashMap<>();
        for(File map : maps) {
            String[] data = map.getName().split("_");
            int moves = Integer.parseInt(data[0]);
            List<File> gm = groupedMaps.get(moves);
            if(gm == null) {
                groupedMaps.put(moves, new ArrayList<>(Arrays.asList(map)));
            }
            else {
                gm.add(map);
            }
        }
        for(int i = 1; i <= 60; i++) {
            List<File> current = groupedMaps.get(i);
            if(current == null) {
                System.out.println("moves: " + i  + " is empty");
                continue;
            }
            for(int r = 0; r < countOfEachGroup; r++) {
                if(r >= current.size()) {
                    System.out.println("moves: " + i + " found: " + r);
                    break;
                }
                File source = current.get(r);
                File dest = new File(source.getAbsolutePath() + "/../../../partial_data/" + source.getName());
                try {
                    FileUtils.copyFile(source, dest);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
