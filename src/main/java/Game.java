import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

;

public class Game {
    private TerminalScreen screen;
    private Arena arena = new Arena(40, 20);

    public Game() {
        try {
            Terminal terminal = new DefaultTerminalFactory().setInitialTerminalSize(new TerminalSize(40, 20)).createTerminal();
            screen = new TerminalScreen(terminal);
            screen.setCursorPosition(null);
            screen.startScreen();
            screen.doResizeIfNecessary();
            TerminalSize terminalSize = new TerminalSize(40, 20);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void draw() throws IOException {
        screen.clear();
        arena.draw(screen.newTextGraphics());
        screen.refresh();
    }
    public void run() throws IOException {
        while(true) {
            if(arena.verifyMonsterCollisions()) {
                screen.stopScreen();
                System.out.println("Unfortunately our Hero died fighting the monsters :(");
            }

            this.draw();
            KeyStroke key = screen.readInput();
            processKey(key);
            if(key.getKeyType() == KeyType.EOF) break;
            if (key.getKeyType() == KeyType.Character && key.getCharacter() == 'q')
                screen.stopScreen();
        }
    }

    private void processKey(KeyStroke key) {
        arena.processKey(key);
    }
}
