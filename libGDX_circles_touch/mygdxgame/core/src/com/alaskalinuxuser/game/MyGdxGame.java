package com.alaskalinuxuser.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector3;


public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
    ShapeRenderer shapeRenderer;
    Circle centerCircle, leftCircle, rightCircle, myTouch;
    OrthographicCamera camera;
    int colorChanger;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("aklu.jpg");
        shapeRenderer = new ShapeRenderer();
        centerCircle = new Circle();
        leftCircle = new Circle();
        rightCircle = new Circle();
        myTouch = new Circle();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        colorChanger = 0;
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();

        batch.draw(img, 0, 0);

        /*
         * Notice that we are using "isTouched" instead of "justTouched"
         *
         * justTouched will only register that one touch. If you drag your finger on the screen,
         * the touch doesn't move after the initial touchpoint was declared.
         *
         * with isTouched, it will continue updating your touch, allowing you to "drag" the pointer
         * around.
         *
         * You can probably see how these both might be useful.
         *
         */

        if (Gdx.input.isTouched()) {

            /*
             * Here is the interesting part. This should be:
             * myTouch.set(Gdx.input.getX(), Gdx.input.getY(), 10);
             * But then our touches turn out correct for X, but backwards for Y.
             * Try it both ways to see the difference.
             *
             * Note that this is in portrait mode. It may not be that way in landscape mode.
             */
            myTouch.set(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY(), 10);

            Gdx.app.log("WJH touchpoint", String.valueOf(myTouch)); // Write down for logging/testing.

            // Did we have an overlap of the "myTouch" and a circle?
            if (Intersector.overlaps(myTouch, centerCircle) ||
                   Intersector.overlaps(myTouch, rightCircle) ||
                    Intersector.overlaps(myTouch, leftCircle)) {

                if (colorChanger < 2) {
                    colorChanger++;
                } else {
                    colorChanger = 0;
                } // If we have an overlap, then change the color.
            }

        } else {

            myTouch.set(0, 0, 0); // If not touched, let's move it out of the way.

        } // End input detection.

        batch.end(); // End drawing.

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled); // For visualizing collision shapes.

        if (colorChanger == 0) {
            shapeRenderer.setColor(Color.BLUE); // For visualizing collision shapes.
        } else if (colorChanger == 1) {
            shapeRenderer.setColor(Color.RED); // For visualizing collision shapes.
        } else {
            shapeRenderer.setColor(Color.GREEN); // For visualizing collision shapes.
        } // Cool color changing affect, so you know you touched a circle.


        leftCircle.set(Gdx.graphics.getWidth()*2/3,
                Gdx.graphics.getHeight()*2/3,
                100); // Set the circle.

        centerCircle.set(Gdx.graphics.getWidth()/2,
                Gdx.graphics.getHeight()/2,
                100); // Set the circle.

        rightCircle.set(Gdx.graphics.getWidth()*1/3,
                Gdx.graphics.getHeight()*1/3,
                100); // Set the circle.

        shapeRenderer.circle(centerCircle.x,centerCircle.y,centerCircle.radius); // For visualizing collision shapes.
        shapeRenderer.circle(rightCircle.x,rightCircle.y,rightCircle.radius); // For visualizing collision shapes.
        shapeRenderer.circle(leftCircle.x,leftCircle.y,leftCircle.radius); // For visualizing collision shapes.

        shapeRenderer.setColor(Color.BLACK); // For visualizing touch shapes.
        shapeRenderer.circle(myTouch.x, myTouch.y, myTouch.radius); // for visualizing touches.

        shapeRenderer.end(); // For visualizing collision shapes.


	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
