package com.pixelcrush.game.scenes.game.enemy;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EnemyManager {
    private static EnemyManager INSTANCE;
    public Gson gson = new Gson();
    public ArrayList<SerializedEnemy> enemyTypes = new ArrayList<>();
    public ArrayList<Enemy> enemies = new ArrayList<>();
    public ArrayList<Sprite> enemySprites = new ArrayList<>();

    public synchronized static EnemyManager getInstance() {
        if (INSTANCE == null) INSTANCE = new EnemyManager();
        return INSTANCE;
    }

    public void loadStageEnemies(Stage stage) {
        ArrayList<SerializedEnemy> enemies = new ArrayList<>();

        ArrayList<SerializedEnemy> possibleSpawnTypes = new ArrayList<>();
        for (SerializedEnemy enemyType : enemyTypes) {
            if (enemyType.firstStage <= stage.stage()) possibleSpawnTypes.add(enemyType);
        }

        Random rng = new Random();
        int upperBoundEnemyType = enemyTypes.size() - 1;
        while (true) {
            if (stage.maxEnemies() <= enemies.size()) break;
            if (stage.minEnemies() <= enemies.size() && rng.nextBoolean()) break;

            enemies.add(possibleSpawnTypes.get(possibleSpawnTypes.size() == 1 ? 0 : rng.nextInt(0, upperBoundEnemyType)));
        }

        System.out.printf("Spawned %d enemies with an upper bound of %d and a min of %d%n", enemies.size(), stage.minEnemies(), stage.maxEnemies());

        this.enemies = enemies.stream().map(SerializedEnemy::toEnemy).collect(Collectors.toCollection(ArrayList::new));
        this.enemies.forEach(enemy -> enemy.setPosition(rng.nextInt(0, 30), rng.nextInt(0, 30)));
    }

    public void spawnEnemies() {
        this.enemies.forEach(Enemy::spawn);
        this.enemies.forEach(enemy -> enemySprites.add(enemy.getSprite()));
    }

    public void updatePositions(float delta) {
        this.enemies.forEach(enemy -> enemy.updatePosition(delta));
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
