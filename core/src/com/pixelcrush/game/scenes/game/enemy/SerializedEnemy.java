package com.pixelcrush.game.scenes.game.enemy;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

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
}
