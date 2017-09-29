package com.alaskalinuxuser.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class MyGdxGame extends Game {

    //static public Skin gameSkin;

    @Override
	public void create () {
        //gameSkin = new Skin();
        this.setScreen(new secondScreen(this));
	}

	@Override
	public void render () {
        super.render();
	}
	
	@Override
	public void dispose () {

	}
}
