package com.pixelcrush.game.scenes.game.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.google.gson.Gson;

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

        System.out.println(possibleSpawnTypes.size());
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
        for (int i = 0; i < enemies.size(); i++) {
            Enemy enemy = enemies.get(i);
            enemy.spawn(i);
            enemySprites.add(enemy.getSprite());
        }
    }

    public void updatePositions(float delta) {
        this.enemies.forEach(enemy -> enemy.updatePosition(delta));
    }

    public ArrayList<FileHandle> getFilesRecursively(String path) throws IOException {
        ArrayList<FileHandle> files = new ArrayList<>();
        try (Stream<Path> stream = Files.walk(Paths.get(path))) {
            stream.filter(Files::isRegularFile).forEach(file -> files.add(Gdx.files.getFileHandle(file.toAbsolutePath().toString(), com.badlogic.gdx.Files.FileType.Absolute)));
        }
        return files;
    }

    public void loadAllEnemies(FileHandle fileHandle) throws IOException {
        getFilesRecursively(fileHandle.path()).forEach(file -> {
            try {
                SerializedEnemy enemyType = gson.fromJson(file.readString(), SerializedEnemy.class);
                System.out.println(enemyType);
                enemyTypes.add(enemyType);
            } catch (GdxRuntimeException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
