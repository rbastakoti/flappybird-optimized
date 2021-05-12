package com.rbastakoti.honors;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;


import java.util.Random;

public class FlappyBird extends ApplicationAdapter {
	Bird flappy;
	Tube tube;
	SpriteBatch batch;
	Texture background;
	Circle birdCircle;
	Integer score=0;
	Integer scoreTube=0;

	Integer gameState = 0;

	Float maxTubeOff;
	Random random;
	BitmapFont font;


	float[] tubeX = new float[GameSettings.numberTubes];
	float[] tubeOffset = new float[GameSettings.numberTubes];

	float distanceBetweenTubes;

	Rectangle[] topRectangles;
	Rectangle[] bottomRectangles;


	@Override
	public void create() {
		batch = new SpriteBatch();
		background = new Texture("background.png");
		//shaperenderer = new ShapeRenderer();
		birdCircle = new Circle();

		//font of Score
		font = new BitmapFont();
		font.setColor(Color.BROWN);
		font.getData().scale(10);

		//bird setup
		flappy = new Bird();
		flappy.birdsArray();
		tube = new Tube();


		maxTubeOff = Gdx.graphics.getHeight() / 2 - GameSettings.Gap / 2 - 100;
		random = new Random();
		distanceBetweenTubes = Gdx.graphics.getWidth() * 3 / 4;

		topRectangles = new Rectangle[GameSettings.numberTubes];
		bottomRectangles = new Rectangle[GameSettings.numberTubes];

		Start();

	}





	@Override
	public void render() {
		batch.begin();
		batch.draw(background,0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		if (gameState == 1) {

			scoreChecker();

			if (Gdx.input.justTouched()) {
				flappy.setBirdVeocity(flappy.getBirdVelocity()-25f);

			}

			moveTubes();
			dropCheck();

		} else if (gameState == 0) {


			if (Gdx.input.justTouched()) {
				gameState = 1;
			}
		} else if (gameState == 2) {
			if (Gdx.input.justTouched()) {
				gameState = 1;
				score = 0;
				scoreTube = 0;
				flappy.setBirdVeocity(0f);
				Start();
			}
		}

		flappy.changeFlap();



		batch.draw(flappy.birds[flappy.getFlapState()], Gdx.graphics.getWidth() / 2 - flappy.birds[flappy.getFlapState()].getWidth() / 2, flappy.getBirdStartPosition());
		font.draw(batch, String.valueOf(score), 100, 300);

		batch.end();
		birdCircle.set(Gdx.graphics.getWidth() / 2, flappy.getBirdStartPosition() + flappy.birds[flappy.getFlapState()].getHeight() / 2, flappy.birds[flappy.getFlapState()].getWidth() / 2);

		collisionTest();

		//Shaperender to check contacts
		//shaperenderer.begin(ShapeRenderer.ShapeType.Filled);
		//shaperenderer.setColor(Color.RED);
		//shaperenderer.circle(birdCircle.x,birdCircle.y,birdCircle.radius);
		//shaperenderer.rect(tubeX[i],Gdx.graphics.getHeight() / 2 + gap / 2 + tubeOffset[i],topTube.getWidth(),topTube.getHeight());
		//shaperenderer.rect(tubeX[i],Gdx.graphics.getHeight() / 2 - gap / 2 - bottomTube.getHeight() + tubeOffset[i],bottomTube.getWidth(),bottomTube.getHeight());
		//shaperenderer.end();


	}



	public void Start() {
		float birdY = Gdx.graphics.getHeight() / 2 - (flappy.birds[flappy.getFlapState()]).getHeight() / 2;
		flappy.setBirdStartPosition(birdY);

		for (int i = 0; i < GameSettings.numberTubes; i++) {
			tubeX[i] = Gdx.graphics.getWidth() / 2 - tube.topTube.getWidth() / 2 + Gdx.graphics.getWidth() + i * distanceBetweenTubes;

			tubeOffset[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - GameSettings.Gap - 200);
			topRectangles[i] = new Rectangle();
			bottomRectangles[i] = new Rectangle();

		}
	}



	public void collisionTest(){
		for (int i = 0; i < GameSettings.numberTubes; i++) {

			if (Intersector.overlaps(birdCircle, topRectangles[i]) || Intersector.overlaps(birdCircle, bottomRectangles[i])) {
				gameState = 2;
			}
		}
	}



	public void scoreChecker(){
		if (tubeX[scoreTube] < Gdx.graphics.getWidth() / 2) {
			score++;

			if (scoreTube < GameSettings.numberTubes) {
				scoreTube++;
			} else {
				scoreTube = 0;
			}

		}
	}



	public void moveTubes(){
		for (int i = 0; i < GameSettings.numberTubes; i++) {

			if (tubeX[i] < -tube.topTube.getWidth()) {
				tubeX[i] += GameSettings.numberTubes * distanceBetweenTubes;
			} else {

				tubeX[i] = tubeX[i] - tube.getTubeVelocity();
				if (tubeX[i] < Gdx.graphics.getWidth()) {
					score++;
				}
			}

			batch.draw(tube.topTube, tubeX[i], Gdx.graphics.getHeight() / 2 + GameSettings.Gap / 2 + tubeOffset[i]);
			batch.draw(tube.bottomTube, tubeX[i], Gdx.graphics.getHeight() / 2 - GameSettings.Gap / 2 - tube.bottomTube.getHeight() + tubeOffset[i]);
			//makeRectangle to test
			topRectangles[i] = new Rectangle(tubeX[i], Gdx.graphics.getHeight() / 2 + GameSettings.Gap/ 2 + tubeOffset[i], tube.topTube.getWidth(), tube.topTube.getHeight());
			bottomRectangles[i] = new Rectangle(tubeX[i], Gdx.graphics.getHeight() / 2 - GameSettings.Gap / 2 - tube.bottomTube.getHeight() + tubeOffset[i], tube.bottomTube.getWidth(), tube.bottomTube.getHeight());

		}
	}



	public void dropCheck(){
		if (flappy.getBirdStartPosition() > 0f) {


			flappy.setBirdVeocity(flappy.getBirdVelocity() + GameSettings.gravity);
			flappy.setBirdStartPosition(flappy.getBirdStartPosition()-flappy.getBirdVelocity());
		} else {
			gameState = 2;
		}
	}


}