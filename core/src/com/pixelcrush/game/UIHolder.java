package com.pixelcrush.game;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class UIHolder {
    private static UIHolder INSTANCE;
    public final Skin UI_SKIN = PixelCrushCore.manager.get("data/ui/uiskin.json");

    public static UIHolder getInstance() {
        if(INSTANCE == null) INSTANCE = new UIHolder();
        return INSTANCE;
    }

    private UIHolder() {}
}
