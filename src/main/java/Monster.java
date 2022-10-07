import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

import java.util.Random;

public class Monster extends Element{
    public Monster(int x, int y){
        super(x, y);
    }

    public void setPosition(Position newPosition){ position = newPosition; }

    public Position move(){
        Random random = new Random();
        int rand = random.nextInt(4);
        int x = position.getX();
        int y = position.getY();
        if(rand == 0) x++;
        if(rand == 1) y++;
        if(rand == 2) x--;
        if(rand == 3) y--;

        return new Position(x, y);
    }

    public void draw(TextGraphics graphics){
        graphics.setBackgroundColor(new TextColor.RGB(96, 253, 249));
        graphics.setForegroundColor(new TextColor.RGB(173, 0, 38));
        graphics.putString(new TerminalPosition(position.getX(), position.getY()), "M");
    }
}
