package com.mygdx.meow.tower.resource;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.meow.tower.Tower;

public class Resource extends Tower {
    private final ResourceType type;
    private final Rectangle workBox;

    public Resource(float x, float y, Texture texture, ResourceType type, float width, float height) {
        super(x, y, width, height, texture, new Rectangle(x, y, width, height));
        this.type = type;
        workBox = new Rectangle(x + width - 20, y, 15f, 15f);
    }

    public boolean contains(float x, float y) {
        return x >= this.x && x <= this.x + width
                && y >= this.y && y <= this.y + height;
    }

    public ResourceType getType() {
        return type;
    }

    public Rectangle getWorkBox() {
        return workBox;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y, width, height);

        //Отладочная информация
        batch.draw(new Texture(Gdx.files.internal("tmp.png")), workBox.x, workBox.y, workBox.width, workBox.height);
        //batch.draw(new Texture(Gdx.files.internal("tmp.png")), hitBox.x, hitBox.y, hitBox.width, hitBox.height);
    }

}
