package br.ifsul.ads;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

public class GameThread extends Thread {

    private GameCanvas gameView;
    private boolean running = false;
    private int frameCount;
    private long fpsTime;

    public GameThread(GameCanvas gameView) {
        this.gameView = gameView;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public void run() {
        Canvas canvas;
        SurfaceHolder holder = gameView.getHolder();

        fpsTime = System.currentTimeMillis();

        long targetTime = 1000 / 30; // 1000 = 1 segundo, 30 FPS

        while (running) {
            canvas = null;

            long frameTime = System.currentTimeMillis();

            try {
                canvas = holder.lockCanvas();

                synchronized (holder) {
                    gameView.update();
                    gameView.draw(canvas);
                }
            } finally {
                if (canvas != null) {
                    holder.unlockCanvasAndPost(canvas);
                }
            }

            long elapsedTime = System.currentTimeMillis();

            long timeMillis = elapsedTime - frameTime;
            long waitTime = targetTime - timeMillis;

            if (waitTime > 0) {
                try {
                    Thread.sleep(waitTime);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            frameCount++;

            // Log.d("ads.gamethread", "Now: " + elapsedTime);

            if (elapsedTime - fpsTime >= 1000) {
                int fps = frameCount;
                frameCount = 0;
                fpsTime = elapsedTime;
                Log.d("ads.gamethread", "FPS: " + fps);
            }
        }
    }
}