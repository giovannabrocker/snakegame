package br.ifsul.ads;
import java.util.Random;

public class Apple {
    Random rand = new Random();
    private int x;
    private int y;

    public Apple(){
        trocarPosicao();
    }
    public int getPosicaoX(){
        return this.x;
    }
    public int getPosicaoY(){
        return this.y;
    }

    public void trocarPosicao(){
        this.x = numeroAleatorio();
        this.y = numeroAleatorio();
    }

    public int numeroAleatorio(){
        return rand.nextInt(10);
    }
}
