package com.pixelcrush.game.scenes.game.enemy;

import org.jetbrains.annotations.Range;

public record Stage(@Range(from = 1, to = Integer.MAX_VALUE) int stage, int minEnemies, int maxEnemies) {
}
