import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

public class Coin extends Element{
    public Coin(int x, int y){
        super(x, y);
    }

    public void draw(TextGraphics graphics){
        graphics.setBackgroundColor(new TextColor.RGB(96, 253, 249));
        graphics.setForegroundColor(new TextColor.RGB(242, 137, 22));
        graphics.putString(new TerminalPosition(position.getX(), position.getY()), "o");
    }
}
