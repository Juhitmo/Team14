/**
 * Platform.java - Loads resources for a new platform
 * 
 * Very important: When defining the body vertices in each textfile, the biggest
 * body should be the first one. This is the body on which we'll determine where
 * to place the platform on the screen. The second body vertices should be of the
 * body that is smaller, creating an obstacle or passage.
 * 
 * Platform shapes are LibGDX ChainShapes, using the createLoop() method.
 * 
 * File format
 * -----------
 * First line : Dimensions of texture region within texture file, "x y" format (no quotes)
 * Second line: Position on platform which to place obstacle, "x y" format (no quotes)
 * Remaining lines: Vertices (end points) of collision lines for each body, "x y" format (no quotes)
 */
package com.team14;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class Platform
{
	private Body body;
    private Texture texture;
	private Obstacle obstacle;
    public TextureRegion tr;
    private World world;
    
    private static int xSize, ySize;		// Dimensions of the platform texture region

	public static final int MAX_PLATFORMS = 8; // Total platforms, must be updated when new platform is added

	/**
	 * Platform(): Constructs a new, single platform using the data provided. The platform
	 * 			   consists of a texture and physical body, just like everything else in the game.
	 * @param w - The world into which we'll place the platform
	 * @param num - Platform number, used for loading resources
	 * @param fixtureDef - The pre-cached FixtureDef from the Platforms instance
	 * @param dimensions - Dimensions of this platform
	 * @param obstaclePos - Position of obstacle on this platform, (0, 0) if none
	 * @param xPos - X position at which to place platform 
	 * @param yPos - Y position at which to place platform
	 */
	public Platform(World w, int num, FixtureDef[] fixtureDef, Vector2 dimensions, Vector2 obstaclePos, float xPos, float yPos)
	{
		world = w;
		
		texture = new Texture(Gdx.files.internal("assets/platforms/platform" + num + ".png"));
			
		xSize = (int) dimensions.x;
		ySize = (int) dimensions.y;
		tr = new TextureRegion(texture, 0, 0, xSize, ySize);

		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.StaticBody;
		float yRandomized = Utils.getRandomNum(75) * Utils.getRandomSign();
		bodyDef.position.set(xPos / Utils.PIXELS_PER_METER, (yPos - ySize + yRandomized) / Utils.PIXELS_PER_METER);

		body = world.createBody(bodyDef);
		
		for (int i = 0; i < fixtureDef.length; i++)
			if (fixtureDef[i] != null)
				body.createFixture(fixtureDef[i]);
		if (obstaclePos.x != 0.0)
			obstacle = new Obstacle(world, (obstaclePos.x + xPos), yPos + yRandomized - obstaclePos.y);

		body.setUserData(this);
	}

	public void render(SpriteBatch spriteBatch)
	{
		spriteBatch.draw(tr, Utils.PIXELS_PER_METER * body.getPosition().x, Utils.PIXELS_PER_METER * body.getPosition().y);
		if (obstacle != null)
			obstacle.draw(spriteBatch);
	}
	
	/* Return x coordinate of left end of platform */
	public float getStart()
	{
		return body.getPosition().x * Utils.PIXELS_PER_METER;
	}
	
	/* Return x coordinate of right end of platform */
	public float getEnd()
	{
		return body.getPosition().x * Utils.PIXELS_PER_METER + xSize;
	}
	
	@Override
	public String toString()
	{
		return "PLATFORM";
	}
}
