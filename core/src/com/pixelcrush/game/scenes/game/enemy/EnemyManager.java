package com.pixelcrush.game.scenes.game.enemy;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

public class EnemyManager {
    private static EnemyManager INSTANCE;
    public Gson gson = new Gson();
    public ArrayList<SerializedEnemy> enemyTypes = new ArrayList<>();

    public synchronized static EnemyManager getInstance() {
        if (INSTANCE == null) INSTANCE = new EnemyManager();
        return INSTANCE;
    }

    public ArrayList<File> getFilesRecursively(String path) throws IOException {
        ArrayList<File> files = new ArrayList<>();
        try (Stream<Path> stream = Files.walk(Paths.get(path))) {
            stream.filter(Files::isRegularFile).forEach(file -> files.add(file.toFile()));
        }
        return files;
    }

    private String getFileContent(File file) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line);
            }
        }
        return content.toString();
    }

    public void loadAllEnemies(String path) throws IOException {
        getFilesRecursively(path).forEach(file -> {
            try {
                enemyTypes.add(gson.fromJson(getFileContent(file), SerializedEnemy.class));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
