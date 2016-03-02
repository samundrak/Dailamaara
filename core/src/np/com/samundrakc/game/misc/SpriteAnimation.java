package np.com.samundrakc.game.misc;

import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.utils.Array;


/**
 * Created by samundra on 3/3/2016.
 */
public class SpriteAnimation {
    Array<TextureRegion> frames;
    float maxFrameTime;
    float currentFrameTime;
    float frameCount;
    int frame;

    public SpriteAnimation(TextureRegion region, int frameCount, float cycleTime) {
        frames = new Array<TextureRegion>();
        int frameWidth = region.getRegionWidth() / frameCount;
        for (int i = 0; i < frameCount; i++) {
            frames.add(new TextureRegion(region, i * frameWidth, 0, frameWidth, region.getRegionHeight()));
        }
        this.frameCount = frameCount;
        maxFrameTime = cycleTime / frameCount;
        frame = 0;
    }

    public void update(float dt) {
        currentFrameTime += dt;
        if (currentFrameTime > maxFrameTime) {
            frame++;
            currentFrameTime = 0;
        }
        if (frame >= frameCount) {
            frame = 0;
        }
    }

    public TextureRegion getFrame() {
        return frames.get(frame);
    }
}
