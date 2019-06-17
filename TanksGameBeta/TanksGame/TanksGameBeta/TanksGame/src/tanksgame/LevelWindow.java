
package tanksgame;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class LevelWindow {
    private Image character, background, enemyTank, turret; 
    private Image playerBullet, projectileFire, projectileLaser;
    private Image wall, container;
    private int currentLevel, mouseX, mouseY, buttonClicked;
    private int moneyReward, maxMoneyReward, enemyNumber;
    private boolean mouse1Clicked, mouse2Clicked, mouseLocked, paused, levelEnd, levelCompleted;
    private boolean tankSelected;
    private Equipment eq;
    private Player player;
    private static Background bg1;
    private ArrayList<Tile> tileArray = new ArrayList<Tile>();
    private ArrayList<Enemy> enemyArray = new ArrayList<Enemy>();
    int[][] map = new int[20][20];
    private int difficulty;
    
    private Font font = new Font(null, Font.BOLD, 30);
    
    Thread networkConnectionThread;
    boolean multiplayer, host, connected, connectionError;
    private Enemy enemyMulti;
    private String ip = "localHost";
    private int port = 22222;
    private Socket socket;
    private DataOutputStream dos;
    private DataInputStream dis;
    private ServerSocket serverSocket;
    private int playerScore, enemyScore;
    private boolean tankSelectedP1, tankSelectedP2;
    
    //Class that creates, updates and draws level that is currently played
    LevelWindow(int level, Equipment equipment, Image player1, Image enemy1, String IP1, int port1) {
        currentLevel = level;
        paused = true;
        tankSelected = false;
        tankSelectedP1 = false;
        tankSelectedP2 = false;
        levelEnd = false;
        levelCompleted = false;
        mouseLocked = true;
        eq = equipment;
        difficulty = 1;
        
        character = player1;
        enemyTank = enemy1;

        connected = false;
        connectionError = false;   
        playerScore = 0;
        enemyScore = 0;
        moneyReward = 0;
        
        ip = IP1;
        port = port1;
        
        String mapPath = "";
        
        switch (currentLevel) {
            //Multiplayer game, player is host
            case 100:
                initializeServer();
                listenForServerRequest();
                multiplayer = true;
                host = true;
                mapPath = "data/maps/mapMulti1.txt";
                break;
            //multiplayer game, player joins the server
            case 101:
                connect();
                multiplayer = true;
                host = false;
                mapPath = "data/maps/mapMulti1.txt";
                break;
            default:
                if(currentLevel >= 5)
                    difficulty = 2;
                if(currentLevel >= 10)
                    difficulty = 3;
                multiplayer = false;
                host = false;
                maxMoneyReward = 2000 * currentLevel;
                mapPath = "data/maps/mapLevel" + Integer.toString(currentLevel) + ".txt";
                break;
        }
        
        //Load Map
        try {
            loadMap(mapPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        enemyNumber = enemyArray.size();
        
        if(multiplayer) {
            networkConnectionThread = new Thread() {
                public void run() {
                    if(connected) {
                        sendMultiplayerValues();
                        loadMultiplayerValues();
                    }
                }  
            };
            networkConnectionThread.start();
        }
    }
    
    //Loading map from file
    private void loadMap(String filename) throws IOException {
        map = new int[20][20];
        bg1 = new Background(0, 0);
        player = new Player(eq, 1, 0, 0);
        tileArray = new ArrayList<Tile>();
        enemyArray = new ArrayList<Enemy>();
        
        ArrayList lines = new ArrayList();
        int width = 0;
        int height = 0;
        

        BufferedReader reader = new BufferedReader(new FileReader(filename));
        while (true) {
            String line = reader.readLine();
            // no more lines to read
            if (line == null) {
                reader.close();
                break;
            }

            if (!line.startsWith("!")) {
                lines.add(line);
                width = Math.max(width, line.length());

            }
        }
        height = lines.size();
        
        for (int j = 0; j < 20; j++) {
            String line = (String) lines.get(j);
            for (int i = 0; i < width; i++) {
                if (i < line.length()) {
                    char ch = line.charAt(i);
                    if(ch == 'P') {
                        if(!multiplayer || (multiplayer && host) ) {
                            player = new Player(eq, 1, 250 * i + 125, 250 * j + 125);
                            break;
                        }
                    }
                    else if(ch == 'E') {
                        if(multiplayer && !host) {
                            player = new Player(eq, 1, 250 * i + 125, 250 * j + 125);
                            break;
                        }
                    }      
                }
            }
        }

        bg1.setBgX(player.getCenterX() - player.getPosX());
        bg1.setBgY(player.getCenterY() - player.getPosY());
        
        for (int j = 0; j < 20; j++) {
            String line = (String) lines.get(j);
            
            for (int i = 0; i < width; i++) {
                if (i < line.length()) {
                    char ch = line.charAt(i);
                    int type = Character.getNumericValue(ch);
                    if(type < 10) {
                        if(type < 0)
                            type = 0;
                        Tile t = new Tile(bg1.getBgX() + 250 * i, bg1.getBgY() + 250 * j, type);
                        tileArray.add(t);
                        map[j][i] = type;
                    }
                    else {
                        type = 0;
                        Tile t = new Tile(bg1.getBgX() + 250 * i, bg1.getBgY() + 250 * j, type);
                        tileArray.add(t);
                        map[j][i] = type;
                        
                        switch(ch) {
                            //Multiplayer enemy
                            case 'E':
                                if(multiplayer && host) {
                                    Weapon weapon = new Weapon(35, 0.20, 91, 5, 800, 12, 1);
                                    weapon.setProjectileType(0);
                                    TankBody body = new TankBody(750, 30, 0, 30, 1, 1);
                                    enemyMulti = new Enemy(bg1.getBgX() + 250 * i + 125, bg1.getBgY() + 250 * j + 125, body, weapon);
                                }
                                break;
                            case 'P':
                                if(multiplayer && !host) {
                                    Weapon weapon = new Weapon(35, 0.20, 91, 5, 800, 12, 1);
                                    weapon.setProjectileType(0);
                                    TankBody body = new TankBody(750, 30, 0, 30, 1, 1);
                                    enemyMulti = new Enemy(bg1.getBgX() + 250 * i + 125, bg1.getBgY() + 250 * j + 125, body, weapon);
                                }
                                break;
                            //Machinegun enemy
                            case 'a':
                                Weapon weapon = new Weapon(100, 0.20, 91, 50, 1000, 14, 1);
                                weapon.setProjectileType(0);
                                TankBody body = new TankBody(750, 30, 0, 30, 1, 1);
                                if(difficulty == 1) {
                                    weapon = new Weapon(25, 0.25, 91, 5, 800, 12, 1);
                                    weapon.setProjectileType(0);
                                    body = new TankBody(300, 5, 0, 0, 1, 1);
                                }
                                else if(difficulty == 2) {
                                    weapon = new Weapon(60, 0.2, 93, 15, 1000, 13, 1);
                                    weapon.setProjectileType(0);
                                    body = new TankBody(550, 20, 20, 0, 1, 1);
                                }
                                else {
                                    weapon = new Weapon(120, 0.15, 95, 30, 1200, 14, 1);
                                    weapon.setProjectileType(0);
                                    body = new TankBody(800, 40, 40, 0, 1, 1);
                                }
                                Enemy e = new Enemy(bg1.getBgX() + 250 * i + 125, bg1.getBgY() + 250 * j + 125, body, weapon);
                                enemyArray.add(e);
                                break;
                            //Sniper enemy
                            case 'b':
                                if(difficulty == 1) {
                                    weapon = new Weapon(100, 1.5, 97, 25, 1000, 14, 1);
                                    weapon.setProjectileType(0);
                                    body = new TankBody(200, 5, 0, 0, 1, 1);
                                }
                                else if(difficulty == 2) {
                                    weapon = new Weapon(250, 1.25, 98, 50, 1250, 16, 1);
                                    weapon.setProjectileType(0);
                                    body = new TankBody(400, 15, 10, 0, 1, 1);
                                }
                                else {
                                    weapon = new Weapon(400, 1.0, 95, 75, 1500, 18, 1);
                                    weapon.setProjectileType(0);
                                    body = new TankBody(650, 30, 20, 0, 1, 1);
                                }
                                e = new Enemy(bg1.getBgX() + 250 * i + 125, bg1.getBgY() + 250 * j + 125, body, weapon);
                                enemyArray.add(e);
                                break;
                            //Shotgun enemy
                            case 'c':
                                if(difficulty == 1) {
                                    weapon = new Weapon(20, 1.2, 80, 10, 300, 11, 5);
                                    weapon.setProjectileType(0);
                                    body = new TankBody(300, 10, 5, 0, 1, 1);
                                }
                                else if(difficulty == 2) {
                                    weapon = new Weapon(60, 1.0, 80, 15, 500, 12, 7);
                                    weapon.setProjectileType(0);
                                    body = new TankBody(600, 25, 25, 0, 1, 1);
                                }
                                else {
                                    weapon = new Weapon(100, 0.7, 80, 20, 700, 13, 9);
                                    weapon.setProjectileType(0);
                                    body = new TankBody(900, 50, 50, 0, 1, 1);
                                }
                                e = new Enemy(bg1.getBgX() + 250 * i + 125, bg1.getBgY() + 250 * j + 125, body, weapon);
                                enemyArray.add(e);
                                break;
                        }
                  
                    }

                }
            }

        }
    }
    
    //----------update------------------------
    public void update(Graphics g, int mx, int my, boolean m1Click, boolean m2Click) {
        mouseX = mx;
        mouseY = my;
        mouse1Clicked = m1Click;
        mouse2Clicked = m2Click;
        if(!m1Click)
            mouseLocked = false;
        if(mouseLocked) {
            mouse1Clicked = false;
            mouse2Clicked = false;
        }
        buttonClicked = 0;       
        
        bg1.update(player.getSpeedX(), player.getSpeedY());
        paint(g);
    }

    //--------------Paint--------------------------
    public void paint(Graphics g) {        
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.drawImage(background, bg1.getBgX(), bg1.getBgY(), null);
        updateTiles();
        paintTiles(g2d);
        
        
        if(!multiplayer) {
            if(!tankSelected) {
                paused = true;
                drawTankSelection(g2d);
            }
            else {
                if(paused || player.isDead()) {
                    player.setMovingDown(false);
                    player.setMovingUp(false);
                    player.setMovingRight(false);
                    player.setMovingLeft(false);
                    player.updateBackground();
                }
                else if(!paused) { 
                    if(!player.isDead()) {
                        if(mouse1Clicked)
                            player.shootPrimary();
                        if(mouse2Clicked)
                            player.shootSecondary();
                        player.update(mouseX, mouseY);
                    }
                    updateEnemies();
                    updatePlayerCollision();
                    updateProjectiles();
                }
                drawLevel(g2d);
                if(paused)
                    drawPauseMenu(g2d);
            }
        }
        else if(multiplayer) {
            networkConnectionThread.run();
            if(!connected) {
                drawWaitingScreen(g2d);
            }
            if(!tankSelectedP1) {
                paused = true;
                drawTankSelection(g2d);
            }
            else if(!tankSelectedP2) {
                drawLevelMulti(g2d);
                drawCenteredString(g2d, 0, 400, 1400, 30, Color.black, "Waiting for enemy to choose tank...");
            }
            else {                
                if(paused) {
                    player.setMovingDown(false);
                    player.setMovingUp(false);
                    player.setMovingRight(false);
                    player.setMovingLeft(false);
                    player.updateBackground();
                }
                else if(!paused) {
                    if(!player.isDead())
                    {
                        if(mouse1Clicked)
                            player.shootPrimary();
                        if(mouse2Clicked)
                            player.shootSecondary();
                    }
                    else {
                        player.setMovingDown(false);
                        player.setMovingUp(false);
                        player.setMovingRight(false);
                        player.setMovingLeft(false);
                    }
                    player.update(mouseX, mouseY);
                    updatePlayerCollision();
                    updateProjectiles();
                }
                drawLevelMulti(g2d);
                if(paused)
                    drawPauseMenu(g2d);
                drawConnectionError(g2d);
            }
        }
            
    }
    
    //------------Drawing level methods-----------------------
    public void drawLevel(Graphics2D g2d) {
        drawEnemies(g2d);
        drawPlayer(g2d);
        drawProjectiles(g2d);
        drawHuds(g2d);

        singleplayerCheckForEnd(g2d);
    }
    
    public void drawLevelMulti(Graphics2D g2d) {   
        drawEnemyMulti(g2d);
        drawPlayer(g2d);
        drawProjectiles(g2d);
        drawHuds(g2d);
        
        multiplayerCheckForEnd(g2d);
        drawConnectionError(g2d);
    }
    
    //Checking for game end
    private void singleplayerCheckForEnd(Graphics2D g2d) {
        int levelEndType = 0;
        if(enemyArray.isEmpty() && !player.isDead()) {
            moneyReward = maxMoneyReward;
            levelEndType = 1;
        }
        else if(player.isDead()) {
            levelEndType = -1;
            StatusEffects endGameStun = new StatusEffects();
            endGameStun.setParalyzed(true);
            endGameStun.setParalyzedDuration(10);
            endGameStun.setWeaponJammed(true);
            endGameStun.setWeaponJammedDuration(10);
            player.setStatus(endGameStun);
        }
        
        if(levelEndType != 0) {
            int buttonX = 500;
            int buttonY = 400;
            int buttonWidth = 400;
            int buttonHeight = 200;
            drawButton(g2d, buttonX, buttonY, buttonWidth, buttonHeight, 5, Color.lightGray, Color.black);
            String levelString = "";
            switch(levelEndType) {
                case 1:
                    levelString = "Level completed!";
                    buttonY += 50;
                    drawCenteredString(g2d, buttonX, buttonY, buttonWidth, 32, Color.black, levelString);
                    buttonY += 60;
                    levelString = "Money received: " + Integer.toString(moneyReward);
                    drawCenteredString(g2d, buttonX, buttonY, buttonWidth, 24, Color.black, levelString);
                    levelCompleted = true;
                    break;
                case -1:
                    levelString = "Level failed!";
                    buttonY += 50;
                    drawCenteredString(g2d, buttonX, buttonY, buttonWidth, 32, Color.black, levelString);
                    buttonY += 60;
                    levelString = "Money received: " + Integer.toString(moneyReward);
                    drawCenteredString(g2d, buttonX, buttonY, buttonWidth, 24, Color.black, levelString);
                    break;
            }
            levelEnd = true;
        }
        else {
            g2d.setColor(Color.black);
            g2d.setFont(new Font(null, Font.BOLD, 26));
            String text = "Enemies left: " + Integer.toString(enemyArray.size());
            g2d.drawString(text, 20, 40);
        }
            
    }
    
    private double messageTime;
    private double maxMessageTime = 3.0;
    boolean scoreAdded = false;
    private void multiplayerCheckForEnd(Graphics2D g2d) {
        int gameEnd = 0;
        if(player.isDead() && playerScore < 5 && enemyScore < 5) {
            if(!scoreAdded) {
                enemyScore++;
                scoreAdded = true;
            }
            if(messageTime < maxMessageTime) {
                drawCenteredString(g2d, 0, 400, 1400, 30, Color.black, "You have lost the round!");
                messageTime += 0.017;
            }
            else {
                drawLoadingScreen(g2d);
                gameEnd = 1;
            }
        }
        else if(enemyMulti.isDead() && playerScore < 5 && enemyScore < 5) {
            if(!scoreAdded) {
                playerScore++;
                scoreAdded = true;
            }
            if(messageTime < maxMessageTime) {
                drawCenteredString(g2d, 0, 400, 1400, 30, Color.black, "You have won the round!");
                messageTime += 0.017;
            }
            else {
                drawLoadingScreen(g2d);
                gameEnd = 1;
            }
        }
        
        if(playerScore >= 5) {
            drawCenteredString(g2d, 0, 400, 1400, 30, Color.black, "You have won the match!");
            gameEnd = 2;
        }
        else if(enemyScore >= 5) {
            drawCenteredString(g2d, 0, 400, 1400, 30, Color.black, "You have lost the match!");
            gameEnd = 2;
        }
        
        drawMultiplayerScore(g2d);
        
        switch(gameEnd) {
            case 0:
                break;
            case 1:
                scoreAdded = false;
                messageTime = 0;
                //Load Map
                String mapPath = "data/maps/mapMulti1.txt";
                try {
                    loadMap(mapPath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                tankSelected = false;
                tankSelectedP1 = false;
                tankSelectedP2 = false;
                break;
            case 2:
                levelEnd = true;
                break;
        }
        
    }
    
    private void drawConnectionError(Graphics2D g2d){
        if(connectionError) {
            enemyMulti.update(bg1.getBgX(), bg1.getBgY(), 0, 0);
            drawCenteredString(g2d, 0, 470, 1400, 24, Color.red, "Unable to connect with opponent...");
        }
    } 
    
    private void drawLoadingScreen(Graphics2D g2d) {
        g2d.fillRect(0, 0, 1400, 1000);
        drawCenteredString(g2d, 0, 450, 1400, 34, Color.black, "Loading map...");
    }
    
    private void drawMultiplayerScore(Graphics2D g2d) {   
        drawCenteredString(g2d, 20, 30, 100, 24, Color.black, "Score");
        drawCenteredString(g2d, 48, 52, 20, 24, Color.green, Integer.toString(playerScore));
        drawCenteredString(g2d, 68, 52, 4, 24, Color.black, ":");
        drawCenteredString(g2d, 72, 52, 20, 24, Color.red, Integer.toString(enemyScore));
    }
    
    //Loading map, updating and drawing tiles
    private void updateTiles() {
        for (int i = 0; i < tileArray.size(); i++) {
            Tile t = (Tile) tileArray.get(i);
            t.update(bg1.getSpeedX(), bg1.getSpeedY());
        }
    }
    
    private void paintTiles(Graphics2D g2d) {
        for (int i = 0; i < tileArray.size(); i++) {
            Tile t = (Tile) tileArray.get(i);
            switch(t.getType()) {
                case 0:
                    break;
                case 1:
                    g2d.drawImage(wall, t.getTileX(), t.getTileY(), null);
                    break;
                case 2:
                    g2d.drawImage(container, t.getTileX(), t.getTileY(), null);
                    break;
            }
        }
    }
    
    private int getTileType(int posX, int posY) {
        posX = (int) Math.floor((posX - bg1.getBgX()) / 250);
        posY = (int) Math.floor((posY - bg1.getBgY()) / 250);
        int value = map[posY][posX];
        
        return value;
    }
    
    
    //Update and draw enemies and player
    public void updatePlayerCollision() {
        int x = (int) Math.floor(player.getPosX() / 250);
        int y = (int) Math.floor(player.getPosY() / 250);
        player.detectCollision(map[y][x - 1], map[y][x + 1], map[y - 1][x], map[y + 1][x]);
    }
    
    public void drawPlayer(Graphics2D g2d) {
        if(!player.isDead()) {
            AffineTransform at = AffineTransform.getTranslateInstance(player.getCenterX() - character.getWidth(null)/2, player.getCenterY() - character.getHeight(null)/2);
            at.rotate(Math.toRadians(player.getBodyRotationAngle()), character.getWidth(null)/2, character.getHeight(null)/2);
            g2d.drawImage(character, at, null);

            AffineTransform at1 = AffineTransform.getTranslateInstance(player.getCenterX() - 7, player.getCenterY() - 8);
            at1.rotate(player.getTurretRotationAngle(), 7, 8);
            g2d.drawImage(turret, at1, null);

            int hpBarWidth = 40;
            int hpBarHeight = 10;
            int percent = player.getHpPercent();
            int startX = player.getCenterX() - hpBarWidth / 2;
            int startY = player.getCenterY() + 60 + hpBarHeight;
            drawMiniHP(g2d, startX, startY, hpBarWidth, hpBarHeight, percent);
        }
    }
    
    private void updateEnemies() {
        for(int i = 0; i < enemyArray.size(); i++) {
            Enemy e = enemyArray.get(i);
            if(!e.isDead()) {
                e.update(bg1.getSpeedX(), bg1.getSpeedY(), player.getCenterX(), player.getCenterY());
            }
            else if(e.getProjectiles().size() == 0){
                enemyArray.remove(i);
                moneyReward += (int) (0.75 * maxMoneyReward / enemyNumber);
            }
        }
    }
    
    private void drawEnemies(Graphics2D g2d) {
        for(int i = 0; i < enemyArray.size(); i++) {
            Enemy e = enemyArray.get(i);
            
            if(!e.isDead()) {
                AffineTransform at = AffineTransform.getTranslateInstance(e.getCenterX() - enemyTank.getWidth(null)/2, e.getCenterY() - enemyTank.getHeight(null)/2);
                at.rotate(Math.toRadians(e.getBodyRotationAngle()), enemyTank.getWidth(null)/2, enemyTank.getHeight(null)/2);
                g2d.drawImage(enemyTank, at, null);

                AffineTransform at1 = AffineTransform.getTranslateInstance(e.getCenterX() - 7, e.getCenterY() - 8);
                at1.rotate(e.getTurretRotationAngle(), 7, 8);
                g2d.drawImage(turret, at1, null);
                
                int hpBarWidth = 40;
                int hpBarHeight = 10;
                int percent = e.getHpPercent();
                int startX = e.getCenterX() - hpBarWidth / 2;
                int startY = e.getCenterY() + 60 + hpBarHeight / 2;
                drawMiniHP(g2d, startX, startY, hpBarWidth, hpBarHeight, percent);
            }
        }
    }
    
    public void drawEnemyMulti(Graphics2D g2d) {
        if(!enemyMulti.isDead()) {
            AffineTransform at = AffineTransform.getTranslateInstance(enemyMulti.getCenterX() - enemyTank.getWidth(null)/2, enemyMulti.getCenterY() - enemyTank.getHeight(null)/2);
            at.rotate(Math.toRadians(enemyMulti.getBodyRotationAngle()), enemyTank.getWidth(null)/2, enemyTank.getHeight(null)/2);
            g2d.drawImage(enemyTank, at, null);

            AffineTransform at1 = AffineTransform.getTranslateInstance(enemyMulti.getCenterX() - 7, enemyMulti.getCenterY() - 8);
            at1.rotate(enemyMulti.getTurretRotationAngle(), 7, 8);
            g2d.drawImage(turret, at1, null);

            int hpBarWidth = 40;
            int hpBarHeight = 10;
            int percent = enemyMulti.getHpPercent();
            int startX = enemyMulti.getCenterX() - hpBarWidth / 2;
            int startY = enemyMulti.getCenterY() + 60 + hpBarHeight / 2;
            drawMiniHP(g2d, startX, startY, hpBarWidth, hpBarHeight, percent);
        }
    }
    
    //Update and draw projectiles
    private void updateProjectiles(){ 
        ArrayList projectiles = player.getProjectiles();
        ArrayList enemiesVeryShortRange = new ArrayList<Enemy>();
        ArrayList enemiesShortRange = new ArrayList<Enemy>();
        ArrayList enemiesMediumRange = new ArrayList<Enemy>();
        ArrayList enemiesLongRange = new ArrayList<Enemy>();
        ArrayList enemiesVeryLongRange = new ArrayList<Enemy>();
        for(int i = 0; i < enemyArray.size(); i++) {
            Enemy e = enemyArray.get(i);
            int deltaX = player.getCenterX() - e.getCenterX();
            int deltaY = player.getCenterY() - e.getCenterY();
            int distance = (int) Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
            if(distance <= 300)
                enemiesVeryShortRange.add(e);
            else if(distance <= 700)
                enemiesShortRange.add(e);
            else if(distance <= 1200)
                enemiesMediumRange.add(e);
            else if(distance <= 1600)
                enemiesLongRange.add(e);
            else
                enemiesVeryLongRange.add(e);
        }
        
        for (int i = 0; i < projectiles.size(); i++) {
            Projectile p = (Projectile) projectiles.get(i);
            if(getTileType(p.getX(), p.getY()) != 0)
                p.setVisible(false);
            
            if (p.isVisible() == true) {
		p.update(bg1.getSpeedX(), bg1.getSpeedY());
                int range = (int) (p.getRange() * 1.2);
                
                if(multiplayer) {
                    int distance = (int) Math.sqrt(Math.pow(p.getX() - enemyMulti.getCenterX(), 2) + Math.pow(p.getY() - enemyMulti.getCenterY(), 2));
                    if(distance < player.getCornerRadius() + 5) {
                        if(enemyMulti.projectileInArea(p)) {
                            if(p.checkEnemyBulletCollision(enemyMulti) == true)
                                p.setVisible(false);
                        }
                    }
                }
                
                if(range > 0) {
                    for(int j = 0; j < enemiesVeryShortRange.size(); j++) {
                        Enemy e = (Enemy) enemiesVeryShortRange.get(j);
                        if(e.projectileInArea(p)) {
                            if(p.checkEnemyBulletCollision(e) == true)
                                e.dealDamage(p);
                        }
                    }
                }
                if(range > 300) {
                    for(int j = 0; j < enemiesShortRange.size(); j++) {
                        Enemy e = (Enemy) enemiesShortRange.get(j);
                        if(e.projectileInArea(p)) {
                            if(p.checkEnemyBulletCollision(e) == true)
                                e.dealDamage(p);
                        }
                    }
                }
                if(range > 700) {
                    for(int j = 0; j < enemiesMediumRange.size(); j++) {
                        Enemy e = (Enemy) enemiesMediumRange.get(j);
                        if(e.projectileInArea(p)) {
                            if(p.checkEnemyBulletCollision(e) == true)
                                e.dealDamage(p);
                        }
                    }
                }
                if(range > 1200) {
                    for(int j = 0; j < enemiesLongRange.size(); j++) {
                        Enemy e = (Enemy) enemiesLongRange.get(j);
                        if(e.projectileInArea(p)) {
                            if(p.checkEnemyBulletCollision(e) == true)
                                e.dealDamage(p);
                        }
                    }
                }
                if(range > 1600) {
                    for(int j = 0; j < enemiesVeryLongRange.size(); j++) {
                        Enemy e = (Enemy) enemiesVeryLongRange.get(j);
                        if(e.projectileInArea(p)) {
                            if(p.checkEnemyBulletCollision(e) == true)
                                e.dealDamage(p);
                        }
                    }
                }
                
            }
            else {
		projectiles.remove(i);
            }
        }
        
        for (int i = 0; i < enemyArray.size(); i++) {
            Enemy e = enemyArray.get(i);
            projectiles = e.getProjectiles();
            for (int j = 0; j < projectiles.size(); j++) {
                Projectile p = (Projectile) projectiles.get(j);
                if(getTileType(p.getX(), p.getY()) != 0)
                    p.setVisible(false);
                
                if (p.isVisible() == true) {
                    p.update(bg1.getSpeedX(), bg1.getSpeedY());
                    int distance = (int) Math.sqrt(Math.pow(p.getX() - player.getCenterX(), 2) + Math.pow(p.getY() - player.getCenterY(), 2));
                    if(distance < player.getCornerRadius() + 5) {
                        if(player.projectileInArea(p)) {
                            if(p.checkPlayerBulletCollision(player) == true)
                                player.dealDamage(p);
                        }
                    }
                }
                else {
                    projectiles.remove(j);
                }  
            }
        }
        
        if(multiplayer) {
            projectiles = enemyMulti.getProjectiles();
            for (int i = 0; i < projectiles.size(); i++) {
                Projectile p = (Projectile) projectiles.get(i);
                if(getTileType(p.getX(), p.getY()) != 0)
                    p.setVisible(false);
                
                if (p.isVisible() == true) {
                    p.update(bg1.getSpeedX(), bg1.getSpeedY());
                    int distance = (int) Math.sqrt(Math.pow(p.getX() - player.getCenterX(), 2) + Math.pow(p.getY() - player.getCenterY(), 2));
                    if(distance < player.getCornerRadius() + 5) {
                        if(player.projectileInArea(p)) {
                            if(p.checkPlayerBulletCollision(player) == true)
                                player.dealDamage(p);
                        }
                    }
                }
                else {
                    projectiles.remove(i);
                } 
            }
        }
    }
    
    private void drawProjectiles(Graphics2D g2d) {
        ArrayList projectiles = player.getProjectiles();
	for (int i = 0; i < projectiles.size(); i++) {
            Projectile p = (Projectile) projectiles.get(i);
            drawProjectile(p, g2d);
        }
        
        for (int i = 0; i < enemyArray.size(); i++) {
            Enemy e = enemyArray.get(i);
            projectiles = e.getProjectiles();
            for (int j = 0; j < projectiles.size(); j++) {
                Projectile p = (Projectile) projectiles.get(j);
                drawProjectile(p, g2d);
            }
        }
        
        if(multiplayer) {
            projectiles = enemyMulti.getProjectiles();
            for (int i = 0; i < projectiles.size(); i++) {
                Projectile p = (Projectile) projectiles.get(i);
                drawProjectile(p, g2d);
            }
        }
    }
    
    public void drawProjectile(Projectile p, Graphics2D g2d) {
        int typ = p.getProjectileType();
        Image projectile = playerBullet;
        switch(typ)
        {
            case 0:
                projectile = playerBullet;
                break;
            case 1:
                break;
            case 2:
                projectile = projectileLaser;
                break;
            case 3:
                projectile = projectileFire;
                break;
        }
        
        AffineTransform at = AffineTransform.getTranslateInstance(p.getX() - projectile.getWidth(null)/2, p.getY() - projectile.getHeight(null)/2);
        at.rotate(p.getProjectileAngle(), projectile.getWidth(null)/2, projectile.getHeight(null)/2);
        g2d.drawImage(projectile, at, null);
    }
    

    //--------Multiplayer methods-------------------   
    private void listenForServerRequest() {
        Socket socket = null;
        try {
            socket = serverSocket.accept();
            dos = new DataOutputStream(socket.getOutputStream());
            dis = new DataInputStream(socket.getInputStream());
            connected = true;
        }
        catch (IOException e) {
            connected = false;
        }
    }
    
    private void connect() {
        try {
            socket = new Socket(ip, port);
            dos = new DataOutputStream(socket.getOutputStream());
            dis = new DataInputStream(socket.getInputStream());
            connected = true;
        }
        catch(IOException e) {
            connected = false;
        }
    }
    
    private void initializeServer() {
        try {
            serverSocket = new ServerSocket(port, 8, InetAddress.getByName(ip));
        }
        catch(IOException e) { 
        }
    }
    
    private void loadMultiplayerValues() {
        try {
            connectionError = false;
            
            try {
                tankSelectedP2 = dis.readBoolean();
                
                int t1X = dis.readInt();
                enemyMulti.setCenterX(t1X + bg1.getBgX());
                int t1Y = dis.readInt();
                enemyMulti.setCenterY(t1Y + bg1.getBgY());
                double bodyAngle = dis.readDouble();
                enemyMulti.setBodyRotationAngle(bodyAngle);
                double turretAngle = dis.readDouble();
                enemyMulti.setTurretRotationAngle(turretAngle);
                int hp = dis.readInt();
                enemyMulti.setHP(hp);
                if(hp <= 0)
                    enemyMulti.setDead(true);
                int maxHp = dis.readInt();
                enemyMulti.setMaxHP(maxHp);

                int maxProjectilesNumber = dis.readInt();
                for(int i = 0; i < maxProjectilesNumber; i++) {
                    //int startX = dis.readInt() + bg1.getBgX();
                    //int startY = dis.readInt() + bg1.getBgY();
                    int gunX = enemyMulti.getCenterX() + (int) Math.round(93.0 * Math.cos(enemyMulti.getTurretRotationAngle()) );
                    int gunY = enemyMulti.getCenterY() + (int) Math.round(93.0 * Math.sin(enemyMulti.getTurretRotationAngle()) );
                    int startX = gunX;
                    int startY = gunY;
                    double angle = dis.readDouble();
                    int velocity = dis.readInt();
                    int range = dis.readInt();
                    int damage = dis.readInt();
                    int armorPen = dis.readInt();
                    int projType = dis.readInt();
                    int team = 2;

                    int statusID = dis.readInt();
                    double statusTime = dis.readDouble();

                    int speedX = dis.readInt();
                    int speedY = dis.readInt();
                    Projectile p = new Projectile(startX, startY, angle, velocity, range, damage, armorPen, projType, team);
                    p.getStatus().setSingleStatus(statusID, statusTime);
                    p.setSpeedX(speedX);
                    p.setSpeedY(speedY);
                    enemyMulti.getProjectiles().add(p);
                }
            }
            catch(NullPointerException ex) {   
            }
        }
        catch (IOException e) {
            connectionError = true;
        }
    }
    
    private void sendMultiplayerValues() {
        try {
            connectionError = false;
            
            dos.writeBoolean(tankSelectedP1);
            
            dos.writeInt(player.getPosX());
            dos.writeInt(player.getPosY());
            dos.writeDouble(player.getBodyRotationAngle());
            dos.writeDouble(player.getTurretRotationAngle());
            dos.writeInt(player.getHP());
            dos.writeInt(player.getMaxHP());
                
            int numberOfProjectiles = 0; 
            for(int i = 0; i < player.getProjectiles().size(); i++) {
                Projectile p = (Projectile) player.getProjectiles().get(i);
                if(p.isNewProjectile()) {
                    numberOfProjectiles++;
                    p.setNewProjectile(false);
                }
            }
            dos.writeInt(numberOfProjectiles);
            for(int i = 0; i < numberOfProjectiles; i++) {
                Projectile p = (Projectile) player.getProjectiles().get(i);
                //dos.writeInt(p.getX());
                //dos.writeInt(p.getY());
                dos.writeDouble(p.getProjectileAngle());
                dos.writeInt(p.getVelocity());
                dos.writeInt(p.getRange());
                dos.writeInt(p.getDamage());
                dos.writeInt(p.getArmorPen());
                dos.writeInt(p.getProjectileType());
                
                int statusID = p.getStatus().getSingleStatusID();
                dos.writeInt(statusID);
                dos.writeDouble(p.getStatus().getSingleStatusDuration(statusID));
                
                dos.writeInt(p.getSpeedX());
                dos.writeInt(p.getSpeedY());
            }
            dos.flush();
        }
        catch (IOException e) {
            connectionError = true;
        }
    }
    
    private void drawWaitingScreen(Graphics2D g2d) {
        g2d.setColor(Color.white);
        g2d.fillRect(0, 0, 1400, 1000);
        
        if(host)
            drawCenteredString(g2d, 0, 470, 1400, 24, Color.black, "Waiting for players...");
        else
            drawCenteredString(g2d, 0, 470, 1400, 24, Color.black, "Looking for server...");
    }

    //Update and draw hud methods
    public void drawHuds(Graphics2D g2d) {
        Color tierColor = Color.lightGray;
        int hudX = 370;
        int hudY = 890;
        int hudSize = 2;
        int percent = player.getAbility().getPercent();
        if(player.isAbilityChosen()) {
            switch(player.getAbility().getTier()) {
                case 0:
                    tierColor = Color.lightGray;
                    break;
                case 1:
                    tierColor = Color.green;
                    break;
                case 2:
                    tierColor = Color.blue;
                    break;
                case 3:
                    tierColor = Color.magenta;
                    break;
                case 4:
                    tierColor = Color.orange;
                    break;
            }
            drawHud(hudX, hudY, hudSize, percent, g2d, tierColor); //ability hud
        }
        
        hudX = 430;
        if(player.isSecondaryChosen()) {
            percent = player.getSecondaryWeapon().getPercent();
            switch(player.getSecondaryWeapon().getTier()) {
                case 0:
                    tierColor = Color.lightGray;
                    break;
                case 1:
                    tierColor = Color.green;
                    break;
                case 2:
                    tierColor = Color.blue;
                    break;
                case 3:
                    tierColor = Color.magenta;
                    break;
                case 4:
                    tierColor = Color.orange;
                    break;
            }
            drawHud(hudX, hudY, hudSize, percent, g2d, tierColor); //secondary weapon hud
        }
        
        //primary weapons hud
        hudX = 920;
        switch(player.getPrimaryWeapon1().getTier()) {
            case 0:
                tierColor = Color.lightGray;
                break;
            case 1:
                tierColor = Color.green;
                break;
            case 2:
                tierColor = Color.blue;
                break;
            case 3:
                tierColor = Color.magenta;
                break;
            case 4:
                tierColor = Color.orange;
                break;
        }
        if(player.getActiveWeapon() == 1)
            drawHud(hudX, hudY, hudSize, 100, g2d, tierColor);
        else
            drawHud(hudX, hudY, hudSize, 0, g2d, tierColor);
        
        if(player.getWeaponsNumber() >= 2) {
            hudX += 60;
            switch(player.getPrimaryWeapon2().getTier()) {
                case 0:
                    tierColor = Color.lightGray;
                    break;
                case 1:
                    tierColor = Color.green;
                    break;
                case 2:
                    tierColor = Color.blue;
                    break;
                case 3:
                    tierColor = Color.magenta;
                    break;
                case 4:
                    tierColor = Color.orange;
                    break;
            }
            if(player.getActiveWeapon() == 2)
                drawHud(hudX, hudY, hudSize, 100, g2d, tierColor);
            else
                drawHud(hudX, hudY, hudSize, 0, g2d, tierColor);
        }
        
        if(player.getWeaponsNumber() >= 3) {
            hudX += 60;
            switch(player.getPrimaryWeapon3().getTier()) {
                case 0:
                    tierColor = Color.lightGray;
                    break;
                case 1:
                    tierColor = Color.green;
                    break;
                case 2:
                    tierColor = Color.blue;
                    break;
                case 3:
                    tierColor = Color.magenta;
                    break;
                case 4:
                    tierColor = Color.orange;
                    break;
            }
            if(player.getActiveWeapon() == 3)
                drawHud(hudX, hudY, hudSize, 100, g2d, tierColor);
            else
                drawHud(hudX, hudY, hudSize, 0, g2d, tierColor);
        }
        
        
        drawPlayerHP(g2d, 500, 903, 400, 30);
    }

    public void drawHud(int hudX, int hudY, int hudSize, int hudPercent, Graphics2D g2d, Color tierColor) {
        int size = 26 * hudSize;
        int lineSize = 2 * hudSize;
        int startPoint = hudX + size / 2;
        int pointxLeft = hudX;
        int pointxRight = hudX + 26 * hudSize - lineSize;
        int pointyUp = hudY + hudSize;
        int pointyDown = hudY + 26 * hudSize - lineSize / 2;
        
        g2d.setColor(tierColor);
        g2d.fillRect(pointxLeft, pointyUp, size, size);
        g2d.setColor(Color.black);
        g2d.fillRect(pointxLeft, pointyUp, 26 * hudSize, lineSize);
        g2d.fillRect(pointxRight, pointyUp, lineSize, 26 * hudSize);
        g2d.fillRect(pointxLeft, pointyDown, 26 * hudSize, lineSize);
        g2d.fillRect(pointxLeft, pointyUp, lineSize, 26 * hudSize);
        
        g2d.setColor(Color.red);
        if(hudPercent <= 13) {
            g2d.fillRect(startPoint, pointyUp, hudPercent * hudSize, lineSize);
        }
        else if(hudPercent > 13 && hudPercent <= 37) {
            hudPercent -= 13;
            g2d.fillRect(startPoint, pointyUp, 13 * hudSize, lineSize);
            g2d.fillRect(pointxRight, pointyUp, lineSize, hudPercent * hudSize);
        }
        else if(hudPercent > 37 && hudPercent <= 63) {
            hudPercent -= 37;
            g2d.fillRect(startPoint, pointyUp, 13 * hudSize, lineSize);
            g2d.fillRect(pointxRight, pointyUp, lineSize, 26 * hudSize);
            g2d.fillRect(pointxRight - hudPercent * hudSize + lineSize, pointyDown, hudPercent * hudSize, lineSize);
        }
        else if(hudPercent > 63 && hudPercent <= 87) {
            hudPercent -= 63;
            g2d.fillRect(startPoint, pointyUp, 13 * hudSize, lineSize);
            g2d.fillRect(pointxRight, pointyUp, lineSize, 26 * hudSize);
            g2d.fillRect(pointxLeft, pointyDown, 26 * hudSize, lineSize);
            g2d.fillRect(pointxLeft, pointyDown - hudPercent * hudSize, lineSize, hudPercent * hudSize + hudSize);
        }
        else if(hudPercent > 87 && hudPercent <= 100) {
            hudPercent -= 87;
            g2d.fillRect(startPoint, pointyUp, 13 * hudSize, lineSize);
            g2d.fillRect(pointxRight, pointyUp, lineSize, 26 * hudSize);
            g2d.fillRect(pointxLeft, pointyDown, 26 * hudSize, lineSize);
            g2d.fillRect(pointxLeft, pointyUp, lineSize, 26 * hudSize);
            g2d.fillRect(pointxLeft, pointyUp, hudPercent * hudSize + hudSize , lineSize);
        }
    }
       
    public void drawPlayerHP(Graphics2D g2d, int startX, int startY, int width, int heigth) {
        g2d.setColor(Color.black);
        g2d.fillRect(startX - 5, startY - 5, width + 10, heigth + 10);
        g2d.setColor(Color.darkGray);
        g2d.fillRect(startX, startY, width, heigth);
        int percent =  player.getHpPercent(); 
        g2d.setColor(new Color((int) (2.55 * (100 - percent)), 255 - (int) (2.55 * (100 - percent)), 0));
        g2d.fillRect(startX, startY, width * percent / 100, heigth);
        
        int stringHeigth = (int) (heigth * 0.9);
        startY += (heigth - stringHeigth) / 2;
        String text = Integer.toString(player.getHP()) + "/" + Integer.toString(player.getMaxHP());
        drawCenteredString(g2d, startX, startY, width, stringHeigth, Color.white, text);
    }
    
    public void drawMiniHP(Graphics2D g2d, int startX, int startY, int width, int heigth, int percent) {
        g2d.setColor(Color.black);
        g2d.fillRect(startX - 1, startY - 1, width + 2, heigth + 2);
        g2d.setColor(Color.darkGray);
        g2d.fillRect(startX, startY, width, heigth);
        g2d.setColor(new Color((int) (2.55 * (100 - percent)), 255 - (int) (2.55 * (100 - percent)), 0));
        g2d.fillRect(startX, startY, width * percent / 100, heigth);
    }
    
    
    //-----------Drawing tank selection screen---------------------
    public void drawTankSelection(Graphics2D g2d) {
        int startX = 200;
        int startY = 50;
        drawButton(g2d, startX, startY, 1000, 900, 10, Color.white, Color.black);
        g2d.fillRect(530, startY, 10, 900);
        g2d.fillRect(860, startY, 10, 900);
        
        for(int k = 1; k <= 3; k++) {
            int[] tankEq = new int[9];
            int buttonX = 0;
            int buttonY = startY + 15;
            int buttonWidth = 67;
            int buttonHeight = 67;
            switch(k) {
                case 1:
                    tankEq = eq.getTankSlot1();
                    startX = 230;
                    break;
                case 2:
                    tankEq = eq.getTankSlot2();
                    startX = 560;
                    break;
                case 3:
                    tankEq = eq.getTankSlot3();
                    startX = 890;
                    break;
            }
            
            Color outlineColor = Color.black;
            Color tierColor = Color.lightGray;

            
            //Bodies tab
            buttonX = startX + (280 - buttonWidth) / 2;
            buttonY = startY + 32;
            if(tankEq[0] == 0)
                tierColor = Color.lightGray;
            else {
                switch(eq.getTankBodies().get(tankEq[0]).getTier()) {
                        case 0:
                            tierColor = Color.lightGray;
                            break;
                        case 1:
                            tierColor = Color.green;
                            break;
                        case 2:
                            tierColor = Color.blue;
                            break;
                        case 3:
                            tierColor = Color.magenta;
                            break;
                        case 4:
                            tierColor = Color.orange;
                            break;
                }
            }
            drawButton(g2d, buttonX, buttonY, buttonWidth, buttonHeight, 3, tierColor, outlineColor);
        
            
            //Primary weapons tab
            tierColor = Color.lightGray;
            outlineColor = Color.black;
            int eqSlots = eq.getTankBodies().get(tankEq[0]).getPrimaryWeaponSlots();
            buttonX = startX + (280 - buttonWidth * eqSlots) / (eqSlots + 1);
            buttonY += 105;
            int jump = buttonX - startX;
            for(int i = 1; i <= eqSlots; i++) {
                outlineColor = Color.black;
                int weaponTier = 0;
                switch(i) {
                    case 1:
                        if(tankEq[1] == 0)
                            weaponTier = 0;
                        else
                            weaponTier = eq.getPrimaryWeapons().get(tankEq[1]).getTier();
                        break;
                    case 2:
                        if(tankEq[2] == 0)
                            weaponTier = 0;
                        else
                            weaponTier = eq.getPrimaryWeapons().get(tankEq[2]).getTier();
                        break;
                    case 3:
                        if(tankEq[3] == 0)
                            weaponTier = 0;
                        else
                            weaponTier = eq.getPrimaryWeapons().get(tankEq[3]).getTier();
                        break;
                }
                switch(weaponTier) {
                    case 0:
                        tierColor = Color.lightGray;
                        break;
                    case 1:
                        tierColor = Color.green;
                        break;
                    case 2:
                        tierColor = Color.blue;
                        break;
                    case 3:
                        tierColor = Color.magenta;
                        break;
                    case 4:
                        tierColor = Color.orange;
                        break;
                }
                drawButton(g2d, buttonX, buttonY, buttonWidth, buttonHeight, 3, tierColor, outlineColor);
                buttonX += (jump + buttonWidth);
            }
            
            //Secondary weapons tab
            tierColor = Color.lightGray;
            outlineColor = Color.black;
            buttonX = startX + (280 - buttonWidth) / 2;
            buttonY += 105;
            if(tankEq[4] == 0)
                tierColor = Color.lightGray;
            else {
                switch(eq.getSecondaryWeapons().get(tankEq[4]).getTier()) {
                        case 0:
                            tierColor = Color.lightGray;
                            break;
                        case 1:
                            tierColor = Color.green;
                            break;
                        case 2:
                            tierColor = Color.blue;
                            break;
                        case 3:
                            tierColor = Color.magenta;
                            break;
                        case 4:
                            tierColor = Color.orange;
                            break;
                }
            }
            drawButton(g2d, buttonX, buttonY, buttonWidth, buttonHeight, 3, tierColor, outlineColor);
            
            //Abilities tab
            tierColor = Color.lightGray;
            outlineColor = Color.black;
            buttonX = startX + (280 - buttonWidth) / 2;
            buttonY += 105;
            if(tankEq[5] == 0)
                tierColor = Color.lightGray;
            else {
                switch(eq.getAbilities().get(tankEq[5]).getTier()) {
                        case 0:
                            tierColor = Color.lightGray;
                            break;
                        case 1:
                            tierColor = Color.green;
                            break;
                        case 2:
                            tierColor = Color.blue;
                            break;
                        case 3:
                            tierColor = Color.magenta;
                            break;
                        case 4:
                            tierColor = Color.orange;
                            break;
                }
            }
            drawButton(g2d, buttonX, buttonY, buttonWidth, buttonHeight, 3, tierColor, outlineColor);
            
            //Modules tab
            outlineColor = Color.black;
            eqSlots = eq.getTankBodies().get(tankEq[0]).getModuleSlots();
            buttonX = startX + (280 - buttonWidth * eqSlots) / (eqSlots + 1);
            buttonY += 105;
            jump = buttonX - startX;
            for(int i = 1; i <= eqSlots; i++) {
                outlineColor = Color.black;
                int moduleTier = 0;
                switch(i) {
                    case 1:
                        if(tankEq[6] == 0)
                            moduleTier = 0;
                        else
                            moduleTier = eq.getModules().get(tankEq[6]).getTier();
                        break;
                    case 2:
                        if(tankEq[7] == 0)
                            moduleTier = 0;
                        else
                            moduleTier = eq.getModules().get(tankEq[7]).getTier();
                        break;
                    case 3:
                        if(tankEq[8] == 0)
                            moduleTier = 0;
                        else
                            moduleTier = eq.getModules().get(tankEq[8]).getTier();
                        break;
                }
                switch(moduleTier) {
                    case 0:
                        tierColor = Color.lightGray;
                        break;
                    case 1:
                        tierColor = Color.green;
                        break;
                    case 2:
                        tierColor = Color.blue;
                        break;
                    case 3:
                        tierColor = Color.magenta;
                        break;
                    case 4:
                        tierColor = Color.orange;
                        break;
                }
                drawButton(g2d, buttonX, buttonY, buttonWidth, buttonHeight, 3, tierColor, outlineColor);
                buttonX += (jump + buttonWidth);
            }
            
            Color black = Color.black;
            //Tab text
            int stringY = startY + 10;
            int stringX = startX;
            drawCenteredString(g2d, stringX, stringY, 280, 20, black, "Tank Body");
            stringY += 105;
            drawCenteredString(g2d, stringX, stringY, 280, 20, black, "Primary Weapons");
            stringY += 105;
            drawCenteredString(g2d, stringX, stringY, 280, 20, black, "Secondary Weapon");
            stringY += 105;
            drawCenteredString(g2d, stringX, stringY, 280, 20, black, "Ability");
            stringY += 105;
            drawCenteredString(g2d, stringX, stringY, 280, 20, black, "Modules");
            //Tank stats text
            stringX = startX + 10;
            g2d.setFont(new Font("Bold", Font.BOLD, 20));
            stringY += 125;
            g2d.drawString("Tank Stats:", stringX, stringY);
            stringY += 25;
            g2d.setFont(new Font("Bold", Font.BOLD, 16));
            int value = 0;
            value = eq.getTankBodies().get(tankEq[0]).getMaxHp() + checkTankStats(1, tankEq);
            g2d.drawString("HP:" + Integer.toString(value), stringX, stringY);
            stringY += 22;
            value = eq.getTankBodies().get(tankEq[0]).getRegen() + checkTankStats(2, tankEq);
            g2d.drawString("HP regen:" + Integer.toString(value), stringX, stringY);
            stringY += 22;
            value = eq.getTankBodies().get(tankEq[0]).getMoveSpeed() + checkTankStats(3, tankEq);
            g2d.drawString("Movement speed:" + Integer.toString(value), stringX, stringY);
            stringY += 22;
            value = eq.getTankBodies().get(tankEq[0]).getArmor() + checkTankStats(4, tankEq);
            g2d.drawString("Armor:" + Integer.toString(value), stringX, stringY);
            if(checkTankStats(10, tankEq) != 0) {
                stringY += 22;
                g2d.drawString("Damage boost: " + Integer.toString(checkTankStats(10, tankEq))+ "%", stringX, stringY);
            }
            if(checkTankStats(11, tankEq) != 0) {
                stringY += 22;
                g2d.drawString("Reload boost: " + Integer.toString(checkTankStats(11, tankEq)) + "%", stringX, stringY);
            }
            if(checkTankStats(12, tankEq) != 0) {
                stringY += 22;
                g2d.drawString("Accuracy boost: " + Integer.toString(checkTankStats(12, tankEq)) + "%", stringX, stringY);
            }
            if(checkTankStats(13, tankEq) != 0) {
                stringY += 22;
                g2d.drawString("Armor pen boost: " + Integer.toString(checkTankStats(13, tankEq)) + "%", stringX, stringY);
            }
            if(checkTankStats(14, tankEq) != 0) {
                stringY += 22;
                g2d.drawString("Range boost: " + Integer.toString(checkTankStats(14, tankEq)) + "%", stringX, stringY);
            }
            if(checkTankStats(20, tankEq) != 0) {
                stringY += 22;
                g2d.drawString("Cooldown reduction: " + Integer.toString(checkTankStats(20, tankEq)) + "%", stringX, stringY);
            }
            
            
            //Select button
            buttonY = 855;
            buttonWidth = 270;
            buttonHeight = 70;
            buttonX = startX + 280 - buttonWidth;
            outlineColor = Color.black;
            if(mouseX >= buttonX && mouseX < buttonX + buttonWidth 
                 && mouseY >= buttonY && mouseY < buttonY + buttonHeight) {
                outlineColor = Color.red;
                if(mouse1Clicked == true) {
                    tankSelected = true;
                    tankSelectedP1 = true;
                    int x = player.getPosX();
                    int y = player.getPosY();
                    player = new Player(eq, k, x, y);
                    paused = false;
                    mouseLocked = true;
                }
            }
            drawTextButton(g2d, buttonX, buttonY, buttonWidth, buttonHeight, 5, Color.white, outlineColor, outlineColor, "Select tank " + Integer.toString(k));
            
        }
    }
    
    private int checkTankStats(int statType, int[] tankEq) {
            int statEffect = 0;
            for(int i = 0; i < 6; i++) {
                if(eq.getTankBodies().get(tankEq[0]).getStatBoostType()[i] == statType) {
                    statEffect += eq.getTankBodies().get(tankEq[0]).getStatBoostEffect()[i];
                }
            }
            
            for(int i = 6; i <= 8; i++) {
                if(eq.getModules().get(tankEq[i]).getStat1Type() == statType)
                  statEffect += eq.getModules().get(tankEq[i]).getStat1Effect(); 
                else if(eq.getModules().get(tankEq[i]).getStat2Type() == statType)
                  statEffect += eq.getModules().get(tankEq[i]).getStat2Effect();
                else if(eq.getModules().get(tankEq[i]).getStat3Type() == statType)
                  statEffect += eq.getModules().get(tankEq[i]).getStat3Effect();
            }
            
            return statEffect;
        }
    
    //Drawing pause menu
    public void escPause() {
        if(tankSelected) {
            if(paused)
                paused = false;
            else
                paused = true;
        }
        else
            buttonClicked = 1;
    }
    
    private void drawPauseMenu(Graphics2D g2d) {
        drawButton(g2d, 500, 350, 400, 250, 10, Color.white, Color.black);
        
        int buttonWidth = 300;
        int buttonHeight = 50;
        int buttonX = 550;
        int buttonY = 400;
        Color buttonColor = Color.lightGray;
        Color outlineColor = Color.black;
        
        if(mouseX >= buttonX && mouseX < buttonX + buttonWidth 
             && mouseY >= buttonY && mouseY < buttonY + buttonHeight) {
            outlineColor = Color.red;
            if(mouse1Clicked == true)
                paused = false;
        }
        drawTextButton(g2d, buttonX, buttonY, buttonWidth, buttonHeight, 5, buttonColor, outlineColor, outlineColor, "Resume");
        
        buttonY += 100;
        outlineColor = Color.black;
        if(mouseX >= buttonX && mouseX < buttonX + buttonWidth 
             && mouseY >= buttonY && mouseY < buttonY + buttonHeight) {
            outlineColor = Color.red;
            if(mouse1Clicked == true)
                buttonClicked = 1;
        }
        drawTextButton(g2d, buttonX, buttonY, buttonWidth, buttonHeight, 5, buttonColor, outlineColor, outlineColor, "Exit to menu");
    }
    
    //Buttons and strings 
    public void drawButton(Graphics2D g2d, int x, int y, int width, int height, int outlineWidth, Color buttonColor, Color outlineColor) {
        g2d.setColor(buttonColor);
        g2d.fillRect(x, y, width, height);
        g2d.setColor(outlineColor);
        g2d.fillRect(x, y, width, outlineWidth);
        g2d.fillRect(x, y + height - outlineWidth, width, outlineWidth);
        g2d.fillRect(x, y, outlineWidth, height);
        g2d.fillRect(x + width - outlineWidth, y, outlineWidth, height);     
    }
    
    public void drawCenteredString(Graphics2D g2d, int x, int y, int width, int height, Color textColor, String text) {       
        Font font = new Font("Arial", Font.BOLD, height );
        g2d.setFont(font);
        FontMetrics fm = g2d.getFontMetrics();
        int stringX = x + ((width - fm.stringWidth(text)) / 2);
        int stringY = y + ((height - fm.getHeight()) / 2) + fm.getAscent();

        g2d.setColor(textColor);
        g2d.drawString(text, stringX, stringY);
    }
    
    public void drawTextButton(Graphics2D g2d, int x, int y, int width, int height, int outlineWidth, Color buttonColor, Color outlineColor, Color textColor, String text) {
        g2d.setColor(buttonColor);
        g2d.fillRect(x, y, width, height);
        g2d.setColor(outlineColor);
        g2d.fillRect(x, y, width, outlineWidth);
        g2d.fillRect(x, y + height - outlineWidth, width, outlineWidth);
        g2d.fillRect(x, y, outlineWidth, height);
        g2d.fillRect(x + width - outlineWidth, y, outlineWidth, height);
        
        Font font = new Font("Arial", Font.BOLD, (int) Math.round(1.0 * (height - 2 * outlineWidth) * 0.7));
        g2d.setFont(font);
        FontMetrics fm = g2d.getFontMetrics();
        int stringX = x + ((width - fm.stringWidth(text)) / 2);
        int stringY = y + ((height - fm.getHeight()) / 2) + fm.getAscent();

        g2d.setColor(textColor);
        g2d.drawString(text, stringX, stringY);
    }

    //Getters and setters
    public void setPlayerBullet(Image playerBullet) {
        this.playerBullet = playerBullet;
    }

    public void setProjectileFire(Image projectileFire) {
        this.projectileFire = projectileFire;
    }

    public void setProjectileLaser(Image projectileLaser) {
        this.projectileLaser = projectileLaser;
    }

    public void setCharacter(Image character) {
        this.character = character;
    }

    public void setBackground(Image background) {
        this.background = background;
    }

    public void setEnemyTank(Image enemyTank) {
        this.enemyTank = enemyTank;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public boolean isMouseLocked() {
        return mouseLocked;
    }

    public void setMouseLocked(boolean mouseLocked) {
        this.mouseLocked = mouseLocked;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getButtonClicked() {
        return buttonClicked;
    }

    public void setButtonClicked(int buttonClicked) {
        this.buttonClicked = buttonClicked;
    }

    public Image getTurret() {
        return turret;
    }

    public void setTurret(Image turret) {
        this.turret = turret;
    }

    public Image getWall() {
        return wall;
    }

    public void setWall(Image wall) {
        this.wall = wall;
    }

    public Image getContainer() {
        return container;
    }

    public void setContainer(Image container) {
        this.container = container;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public void setServerSocket(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public Socket getSocket() {
        return socket;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public int getMoneyReward() {
        return moneyReward;
    }

    public void setMoneyReward(int moneyReward) {
        this.moneyReward = moneyReward;
    }

    public boolean isLevelCompleted() {
        return levelCompleted;
    }

    public void setLevelCompleted(boolean levelCompleted) {
        this.levelCompleted = levelCompleted;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    public boolean isLevelEnd() {
        return levelEnd;
    }

    public void setLevelEnd(boolean levelEnd) {
        this.levelEnd = levelEnd;
    }
    
    

    public Thread getNetworkConnectionThread() {
        return networkConnectionThread;
    }

    public boolean isMultiplayer() {
        return multiplayer;
    }

    public boolean isConnected() {
        return connected;
    }
    
    
    
}