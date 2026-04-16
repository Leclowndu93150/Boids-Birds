package com.leclowndu93150.boids.fabric;

import com.leclowndu93150.boids.config.BoidsConfigScreen;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

public class BoidsModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return BoidsConfigScreen::create;
    }
}
