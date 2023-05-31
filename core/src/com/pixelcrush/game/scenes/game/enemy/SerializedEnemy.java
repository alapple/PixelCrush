package com.pixelcrush.game.scenes.game.enemy;

import java.util.ArrayList;

enum AttackType {
    MELEE,
    BOW,
}

public class SerializedEnemy {
    public float speed;
    public int firstStage;
    public ArrayList<Float> stageProbability;
    public String name;
    public String textureAtlasPath;
    public float damage;
    public AttackType attackType;
    public float rechargeTime;
    public float attackRadius;
    public float stopRadius;
    public float followRadius;
    public float groupFollowCircle;
    public boolean stayAttachedAfterFirstContact;
    public TextureRegionData textureRegions;
    public static class TextureRegionData {
        public String idle;
        public String up;
        public String down;
        public String left;
        public String right;
    }

    public Enemy toEnemy() {
        return new Enemy(this);
    }

    @Override
    public String toString() {
        return "SerializedEnemy{" + "speed=" + speed +
                ",\n firstStage=" + firstStage +
                ",\n stageProbability=" + stageProbability +
                ",\n name='" + name + '\'' +
                ",\n textureAtlasPath='" + textureAtlasPath + '\'' +
                ",\n damage=" + damage +
                ",\n attackType=" + attackType +
                ",\n rechargeTime=" + rechargeTime +
                ",\n attackRadius=" + attackRadius +
                ",\n stopRadius=" + stopRadius +
                ",\n followRadius=" + followRadius +
                ",\n groupFollowCircle=" + groupFollowCircle +
                ",\n stayAttachedAfterFirstContact=" + stayAttachedAfterFirstContact +
                ",\n textureRegions=" + textureRegions +
                '}';
    }
}
