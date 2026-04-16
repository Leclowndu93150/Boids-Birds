package com.leclowndu93150.birds.fabric;

import com.leclowndu93150.birds.config.BirdsConfigScreen;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

public class BirdsModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return BirdsConfigScreen::create;
    }
}
