package com.almasb.fxglgames.spaceinvaders.level;

import static com.almasb.fxgl.app.DSLKt.geti;
import static com.almasb.fxglgames.spaceinvaders.Config.ENEMIES_PER_ROW;
import static com.almasb.fxglgames.spaceinvaders.Config.ENEMY_ROWS;

import java.util.ArrayList;
import java.util.List;

import com.almasb.fxgl.animation.Animation;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.entity.GameEntity;
import com.almasb.fxgl.entity.control.RandomMoveControl;
import com.almasb.fxglgames.spaceinvaders.Config;

import javafx.geometry.Rectangle2D;

public class Level1 extends SpaceLevel {


	@Override
	public void init() {
		// TODO Auto-generated method stub
		// calculate the occupation width of one enemy through the whole screen width 
		int enemy_width = (int)(Config.WIDTH/Config.ENEMIES_PER_LEVEL);
		
		// all enemies set in one row on the middle of the screen
		for (int x = 0; x < Config.ENEMIES_PER_LEVEL; x++) {
			GameEntity enemy = spawnEnemy(x*enemy_width, Config.HEIGHT/2);
        }
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

}
