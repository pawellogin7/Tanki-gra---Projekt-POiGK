
package tanksgame;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
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
    private boolean mouse1Clicked, mouse2Clicked, mouseLocked, paused, levelActive, levelCompleted;
    private boolean tankSelected, tankSelectedP1, tankSelectedP2;
    private Equipment eq;
    private Player player;
    private static Background bg1;
    private ArrayList<Tile> tileArray = new ArrayList<Tile>();
    private ArrayList<Enemy> enemyArray = new ArrayList<Enemy>();
    
    public static int score = 0;
    private Font font = new Font(null, Font.BOLD, 30);
    
    boolean multiplayer, host, connected, connectionError;
    private Enemy enemyMulti;
    private String ip = "localHost";
    private int port = 22222;
    private Socket socket;
    private DataOutputStream dos;
    private DataInputStream dis;
    private ServerSocket serverSocket;
    private int connectionErrorNumber = 0;
    private int scorePlayer, scoreEnemy;
    
    LevelWindow(int level, Equipment equipment, Image player1, Image enemy1) {
        currentLevel = level;
        paused = true;
        tankSelected = false;
        tankSelectedP1 = false;
        tankSelectedP2 = false;
        levelActive = false;
        levelCompleted = false;
        mouseLocked = true;
        eq = equipment;
        
        character = player1;
        enemyTank = enemy1;

        connected = false;
        connectionError = false;        
        moneyReward = 0;
        
        bg1 = new Background(0, 0);
        player = new Player(eq, 1, 0, 0);
        String mapPath = "";
        
        if(currentLevel == 100) {
            initializeServer();
            multiplayer = true;
            host = true;
            
            mapPath = "data/maps/mapMulti1.txt";
        }
        else if(currentLevel == 101) {
            multiplayer = true;
            host = false;
           
            mapPath = "data/maps/mapMulti1.txt";
        }
        else {
            multiplayer = false;
            host = false;
            
            maxMoneyReward = 2000;
            mapPath = "data/maps/mapLevel1.txt";
        }
        
        //Load Map
        try {
            loadMap(mapPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        enemyNumber = enemyArray.size();
    }
    
    //Loading map
    private void loadMap(String filename) throws IOException {
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
                        Tile t = new Tile(bg1.getBgX() + 250 * i, bg1.getBgY() + 250 * j, type);
                        tileArray.add(t);
                    }
                    else {
                        type = 0;
                        Tile t = new Tile(bg1.getBgX() + 250 * i, bg1.getBgY() + 250 * j, type);
                        tileArray.add(t);
                        
                        switch(ch) {
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
                            case 'a':
                                Weapon weapon = new Weapon(100, 0.20, 91, 50, 1000, 14, 1);
                                weapon.setProjectileType(0);
                                TankBody body = new TankBody(750, 30, 0, 30, 1, 1);
                                Enemy e = new Enemy(bg1.getBgX() + 250 * i + 125, bg1.getBgY() + 250 * j + 125, body, weapon);
                                enemyArray.add(e);
                                break;
                        }
                  
                    }

                }
            }
        }
    }
    
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
        
        if(multiplayer && host && !connected) {
            Graphics2D g2d = (Graphics2D) g.create();
            listenForServerRequest(g2d);
        }
        else if(multiplayer && !host && !connected) {
            if(!connect())
                buttonClicked = 1;   
        }
        else if(multiplayer && connected) {
            sendMultiplayerValues();
            loadMultiplayerValues();
        }
        
        bg1.update(player.getSpeedX(), player.getSpeedY());
        paint(g);
    }

    //--------------Paint--------------------------
    public void paint(Graphics g) {        
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.drawImage(background, bg1.getBgX(), bg1.getBgY(), null);
        updateTiles();
        paintTiles(g2d);
        updateEnemies();
        
        if(!tankSelected) {
            paused = true;
            drawTankSelection(g2d);
        }
        else if(!multiplayer){
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
                updateProjectiles();
            }
            drawLevel(g2d);
            if(paused)
                drawPauseMenu(g2d);
        }
        else if(multiplayer) {
            if(paused) {
                player.setMovingDown(false);
                player.setMovingUp(false);
                player.setMovingRight(false);
                player.setMovingLeft(false);
                player.updateBackground();
            }
            else if(!paused) { 
                if(mouse1Clicked)
                    player.shootPrimary();
                if(mouse2Clicked)
                    player.shootSecondary();
                player.update(mouseX, mouseY);
                updateProjectiles();
            }
            drawLevelMulti(g2d);
            if(paused)
                drawPauseMenu(g2d);
        }
            
    }
    
    //--------Multiplayer methods-------------------
    private void listenForServerRequest(Graphics2D g2d) {
        Socket socket = null;
        try {
            socket = serverSocket.accept();
            dos = new DataOutputStream(socket.getOutputStream());
            dis = new DataInputStream(socket.getInputStream());
            connected = true;
        }
        catch (IOException e) {
            drawWaitingForPlayersScreen(g2d);
        }
    }
    
    private boolean connect() {
        try {
            socket = new Socket(ip, port);
            dos = new DataOutputStream(socket.getOutputStream());
            dis = new DataInputStream(socket.getInputStream());
            connected = true;
        }
        catch(IOException e) {
            return false;
        }
        return true;
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
            int t1X = dis.readInt();
            enemyMulti.setCenterX(t1X + bg1.getBgX());
            int t1Y = dis.readInt();
            enemyMulti.setCenterY(t1Y + bg1.getBgY());
            double bodyAngle = dis.readDouble();
            enemyMulti.setBodyRotationAngle(bodyAngle);
            double turretAngle = dis.readDouble();
            enemyMulti.setTurretRotationAngle(turretAngle);
            int maxProjectilesNumber = dis.readInt();
            for(int i = 0; i < maxProjectilesNumber; i++) {
                connectionError = false;
                
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
                
                int speedX = dis.readInt();
                int speedY = dis.readInt();
                Projectile p = new Projectile(startX, startY, angle, velocity, range, damage, armorPen, projType, team);
                p.setSpeedX(speedX);
                p.setSpeedY(speedY);
                enemyMulti.getProjectiles().add(p);
            }
        }
        catch (IOException e) {
            if(host) {
                try {
                    socket.close();
                }
                catch(IOException ex) {
                }
            }
            else {
                try {
                    socket.close();
                }
                catch(IOException ex) {
                }
            }
            buttonClicked = 1;
            connectionError = true;
        }
    }
    
    private void sendMultiplayerValues() {
        try {
            connectionError = false;
            
            dos.writeInt(player.getPosX());
            dos.writeInt(player.getPosY());
            dos.writeDouble(player.getBodyRotationAngle());
            dos.writeDouble(player.getTurretRotationAngle());
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
                
                dos.writeInt(p.getSpeedX());
                dos.writeInt(p.getSpeedY());
            }
            dos.flush();
        }
        catch (IOException e) {
            if(host) {
                try {
                    socket.close();
                }
                catch(IOException ex) {
                }
            }
            else {
                try {
                    socket.close();
                }
                catch(IOException ex) {
                }
            }
            buttonClicked = 1;
            connectionError = true;
        }
    }
    
    private void drawWaitingForPlayersScreen(Graphics2D g2d) {
        g2d.setColor(Color.white);
        g2d.fillRect(0, 0, 1400, 1000);
        
        drawCenteredString(g2d, 0, 470, 1400, 24, Color.black, "Waiting for players...");
    }
    
    //------------Drawing level methods-----------------------
    public void drawLevel(Graphics2D g2d) {
        drawEnemies(g2d);
        
        g2d.drawRect((int)player.rect1.getX(), (int)player.rect1.getY(), (int)player.rect1.getWidth(), (int)player.rect1.getHeight());
        g2d.drawRect((int)player.rect.getX(), (int)player.rect.getY(), (int)player.rect.getWidth(), (int)player.rect.getHeight());    
        g2d.drawRect((int)player.rect2.getX(), (int)player.rect2.getY(), (int)player.rect2.getWidth(), (int)player.rect2.getHeight()); 
        g2d.drawRect((int)player.rect3.getX(), (int)player.rect3.getY(), (int)player.rect3.getWidth(), (int)player.rect3.getHeight()); 
        drawPlayer(g2d);
        
        drawProjectiles(g2d);
        drawHuds(g2d);
        
        singleplayerCheckForEnd(g2d);
    }
    
    public void drawLevelMulti(Graphics2D g2d) {   
        paintTiles(g2d);
        drawEnemyMulti(g2d);
        drawPlayer(g2d);
        drawProjectiles(g2d);
        drawHuds(g2d);
        drawConnectionError(g2d);
    }
    
    private void drawConnectionError(Graphics2D g2d){
        if(connectionError) {
            connectionErrorNumber++;
            drawCenteredString(g2d, 0, 470, 1400, 24, Color.red, "Unable to connect with opponent...");
        }
        else
            connectionErrorNumber = 0;
    } 
    
    //
    private void singleplayerCheckForEnd(Graphics2D g2d) {
        int levelEnd = 0;
        if(enemyArray.isEmpty() && !player.isDead()) {
            moneyReward = maxMoneyReward;
            levelEnd = 1;
        }
        else if(player.isDead()) {
            levelEnd = -1;
            StatusEffects endGameStun = new StatusEffects();
            endGameStun.setParalyzed(true);
            endGameStun.setParalyzedDuration(10);
            endGameStun.setWeaponJammed(true);
            endGameStun.setWeaponJammedDuration(10);
            player.setStatus(endGameStun);
        }
        
        if(levelEnd != 0) {
            int buttonX = 500;
            int buttonY = 400;
            int buttonWidth = 400;
            int buttonHeight = 200;
            drawButton(g2d, buttonX, buttonY, buttonWidth, buttonHeight, 5, Color.lightGray, Color.black);
            String levelString = "";
            switch(levelEnd) {
                case 1:
                    levelString = "Level completed!";
                    buttonY += 50;
                    drawCenteredString(g2d, buttonX, buttonY, buttonWidth, 32, Color.black, levelString);
                    buttonY += 60;
                    levelString = "Money received: " + Integer.toString(moneyReward);
                    drawCenteredString(g2d, buttonX, buttonY, buttonWidth, 24, Color.black, levelString);
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
            levelCompleted = true;
        }
        else {
            g2d.setColor(Color.black);
            g2d.setFont(new Font(null, Font.BOLD, 26));
            String text = "Enemies left: " + Integer.toString(enemyArray.size());
            g2d.drawString(text, 20, 40);
        }
            
    } 
    
    //Loading map, updating and drawing tiles
    private void updateTiles() {
        for (int i = 0; i < tileArray.size(); i++) {
            Tile t = (Tile) tileArray.get(i);
            t.update(bg1.getSpeedX(), bg1.getSpeedY());
            Rectangle r = new Rectangle(0, 0, 0, 0);
            r.setBounds(t.getTileX(), t.getTileY(), 250, 250);
            if (r.intersects(Player.yellowRed) && t.getType() != 0){
                //checkCollision(Player.rect,Player.rect1,Player.rect2,Player.rect3, r, t);
            }
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
    
    public  void checkCollision(Rectangle rplayer, Rectangle rplayer1, Rectangle rplayer2, Rectangle rplayer3, Rectangle rTile, Tile t1){
    	if (rplayer.intersects(rTile)){
//            int jio = player.getCenterY();
            player.setCenterX(t1.getTileX()+314);
//            player.setCenterY(jio);
//            player.setSpeedX(0);
            System.out.println("kolizja");
        }
        if (rplayer1.intersects(rTile)){       
//            int jio = player.getCenterX();     
            player.setCenterY(t1.getTileY()+282);
//            player.setCenterX(jio);
//            player.setSpeedY(0);
            System.out.println("kolizja1");
        }
        if (rplayer2.intersects(rTile)){
//            int jio = player.getCenterY();
            player.setCenterX(t1.getTileX()+64);
//            player.setCenterY(jio);
//            player.setSpeedX(0);
            System.out.println("kolizja2");
        }
        if (rplayer3.intersects(rTile)){
//            int jio = player.getCenterX();
            player.setCenterY(t1.getTileY()-32);
//            player.setCenterX(jio);
//            player.setSpeedY(0);
            System.out.println("kolizja3");
        }
    }
    
    //Update and draw enemies and player
    public void drawPlayer(Graphics2D g2d) {       
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
    
    //Update and draw projectiles methods
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
            if (p.isVisible() == true) {
		p.update(bg1.getSpeedX(), bg1.getSpeedY());
                int range = (int) (p.getRange() * 1.2);
                
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
                if (p.isVisible() == true) {
                    p.update(bg1.getSpeedX(), bg1.getSpeedY());
                    if(player.projectileInArea(p)) {
                        if(p.checkPlayerBulletCollision(player) == true)
                            player.dealDamage(p);
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
                if (p.isVisible() == true) {
                    p.update(bg1.getSpeedX(), bg1.getSpeedY());
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
    
    //---------------------------------------------------------------------
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
    
    //-----------------------------------------------------------------------------
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
    
    
    
}
