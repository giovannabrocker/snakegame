package br.ifsul.ads;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

public class GameCanvas extends SurfaceView implements SurfaceHolder.Callback {

    private GameThread gameThread;
    private long lastUpdate = System.currentTimeMillis();

    private Bitmap frog;
    private Apple apple;
    private float frogX, frogY, fDx = 1, fDy = 1;

    public GameCanvas(Context ctx, AttributeSet set){
        super(ctx, set);

        SurfaceHolder surfaceholder = getHolder();
        surfaceholder.addCallback(this);

        frog = BitmapFactory.decodeResource(getResources(), R.drawable.frog_pose1);

        gameThread = new GameThread(this);
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        Log.d("ads.gamecanvas", "surface changed");
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        gameThread.setRunning(true);
        gameThread.start();

        Log.d("ads.gamecanvas", "surface created");
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        gameThread.setRunning(false);
    }

    // logica - estado dos objetos do jogo, entrada de usuario, etc (ANTES de desenhar)
    // chamado pela thread de atualizado do jogo
    public void update() {

        float frogSpeed = 40f, deltaTime = deltaTime();

        // essa parte precisa ser melhorada pois com muitos elementos vira uma bagunã
        // aqui usar bem a orientação a objetos para evitar dor de cabeça

        frogY += frogSpeed * deltaTime * fDy;
        frogX += frogSpeed * deltaTime * fDx;

        if((frogX + 16) >= this.getWidth() || frogX < 0)
            fDx *= -1;
        if((frogY + 16) >= this.getHeight() || frogY < 0)
            fDy *= -1;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        canvas.drawColor(Color.WHITE);

        Paint pincel1 = new Paint();
        pincel1.setColor(Color.RED);

        apple.trocarPosicao();
        canvas.drawCircle(apple.getPosicaoX(), apple.getPosicaoY(), 50, pincel1);

        canvas.drawBitmap(frog, frogX, frogY, new Paint());
    }

    private float deltaTime() {
        long curTime = System.currentTimeMillis();
        float deltaTime = (float) (curTime - lastUpdate) / 1000.0f;

        //Log.d("ads.gamecanvas", "deltaTime: " + (curTime - lastUpdate) + " " + deltaTime + " " + curTime);

        lastUpdate = curTime;

        return deltaTime;
    }
}
