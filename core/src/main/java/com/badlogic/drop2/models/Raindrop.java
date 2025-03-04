package com.badlogic.drop2.models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Raindrop {
    private Texture texture;
    private Rectangle hitbox;
    private float speed;

    public Raindrop(Texture texture, float x, float y, float speed) {
        this.texture = texture;
        this.speed = speed;
        this.hitbox = new Rectangle(x, y, 64, 64);
    }

    // Moves the raindrop based on its speed
    public void move(float delta) {
        hitbox.y -= speed * delta;
    }

    // Draws the raindrop using the provided SpriteBatch
    public void draw(SpriteBatch batch) {
        batch.draw(texture, hitbox.x, hitbox.y);
    }

    // Checks if the raindrop is off-screen
    public boolean isOffScreen() {
        return hitbox.y + 64 < 0;
    }

    // Checks if the raindrop overlaps with another Rectangle (like the bucket)
    public boolean overlaps(Rectangle other) {
        return hitbox.overlaps(other);
    }

    // Returns the hitbox for collision detection
    public Rectangle getHitbox() {
        return hitbox;
    }
}
