import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Arena {
    private int height;
    private int width;
    private Hero hero = new Hero(10, 10);
    private List<Wall> walls;
    private int numCoins = 5;
    private List<Coin> coins;
    private int numMonsters = 40;
    private List<Monster> monsters;


    public Arena(int width, int height){
        this.height = height;
        this.width = width;
        this.walls = createWalls();
        this.coins = createCoins();
        this.monsters = createMonsters();
    }

    // --------- General ---------
    public void draw(TextGraphics graphics){
       graphics.setBackgroundColor(new TextColor.RGB(96, 253, 249));
       graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(width, height), ' ');
        for (Wall wall : walls)
            wall.draw(graphics);
        for (Coin coin : coins)
            coin.draw(graphics);
        for (Coin coin : coins)
            if(hero.getPosition().equals(coin.getPosition())){
                retrieveCoins(coin);
                break;
            }
        for (Monster monster: monsters)
            monster.draw(graphics);
        hero.draw(graphics);
    }

    void processKey(KeyStroke key) {
        //System.out.println(key);
        moveMonsters();
        switch (key.getKeyType()) {
            case ArrowLeft:
                moveHero(hero.moveLeft());
                break;
            case ArrowRight:
                moveHero(hero.moveRight());
                break;
            case ArrowUp:
                moveHero(hero.moveUp());
                break;
            case ArrowDown:
                moveHero(hero.moveDown());
                break;
        }
    }

    private boolean canMove(Position position){
        boolean notInArena = (position.getX() < width) &&
                (position.getY() < height) &&
                (position.getX() >= 0) &&
                (position.getY() >= 0);

        boolean notInWall = true;
        for (Wall wall : walls)
            notInWall = notInWall && !wall.getPosition().equals(position);

        return notInArena && notInWall;
    }

    // --------- Hero ---------
    private void moveHero(Position position) {
        if (canMove(position))
            hero.setPosition(position);
    }

    // --------- Walls ---------
    private List<Wall> createWalls() {
        List<Wall> walls = new ArrayList<>();

        for (int c = 0; c < width; c++) {
            walls.add(new Wall(c, 0));
            walls.add(new Wall(c, height - 1));
        }

        for (int r = 1; r < height - 1; r++) {
            walls.add(new Wall(0, r));
            walls.add(new Wall(width - 1, r));
        }

        return walls;
    }

    // --------- Coins ---------
    private List<Coin> createCoins() {
        Random random = new Random();
        ArrayList<Coin> coins = new ArrayList<>();
        for (int i = 0; i < numCoins; i++) {
            int coinX = random.nextInt(width - 2) + 1;
            int coinY = random.nextInt(height - 2) + 1;
            while(coinX == 10 && coinY == 10){
                coinX = random.nextInt(width - 2) + 1;
                coinY = random.nextInt(height - 2) + 1;
            }
            Coin nextCoin = new Coin(coinX, coinY);
            while(coins.contains(nextCoin)){
                coinX = random.nextInt(width - 2) + 1;
                coinY = random.nextInt(height - 2) + 1;
                nextCoin = new Coin(coinX, coinY);
            }
            coins.add(nextCoin);
        }
        return coins;
    }

    public void retrieveCoins(Coin coinToRetrieve){
        coins.remove(coinToRetrieve);
    }

    // --------- Monsters ---------
    private List<Monster> createMonsters() {
        Random random = new Random();
        ArrayList<Monster> monsters = new ArrayList<>();
        for (int i = 0; i < numMonsters; i++) {
            int monsterX = random.nextInt(width - 2) + 1;
            int monsterY = random.nextInt(height - 2) + 1;
            while(monsterX == 10 && monsterY == 10){
                monsterX = random.nextInt(width - 2) + 1;
                monsterY = random.nextInt(height - 2) + 1;
            }
            Monster nextMonster = new Monster(monsterX, monsterY);
            while(monsters.contains(nextMonster)){
                monsterX = random.nextInt(width - 2) + 1;
                monsterY = random.nextInt(height - 2) + 1;
                nextMonster = new Monster(monsterX, monsterY);
            }
            monsters.add(nextMonster);
        }
        return monsters;
    }

    public void moveMonsters(){
        for(Monster monster : monsters){
            Position newPosition;

            do {
                newPosition = monster.move();
            } while(!canMove(newPosition));

            monster.setPosition(newPosition);
        }
    }

    public boolean verifyMonsterCollisions(){
        for (Monster monster : monsters)
            if(monster.getPosition().equals(hero.getPosition()))
                return true;
        return false;
    }


}
