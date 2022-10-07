import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

public class Wall extends Element{

    public Wall(int x, int y) {
        super(x, y);
    }

    public void draw(TextGraphics graphics) {
        graphics.setBackgroundColor(new TextColor.RGB(2, 9, 249));
        graphics.enableModifiers(SGR.FRAKTUR);
        graphics.putString(new TerminalPosition(position.getX(), position.getY()), " ");
    }
}
