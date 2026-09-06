package main;

import entity.NPC_OldMan;
import monster.MON_GreenSlime;
import object.*;
import tile_interactive.IT_DryTree;

public class AssetSetter {
    
    private int maxSlime = 5;
    
    GamePanel gp;
    
    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }
    
    public void setObject(){
        
        int i = 0;
        int mapNum = 0;
        
        gp.obj[mapNum][i] = new Obj_Coin(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*30;
        gp.obj[mapNum][i].worldY = gp.tileSize*10;
        i++;
  
        gp.obj[mapNum][i] = new Obj_Key(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*19;
        gp.obj[mapNum][i].worldY = gp.tileSize*19;
        i++;
        
        gp.obj[mapNum][i] = new Obj_Coin(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*20;
        gp.obj[mapNum][i].worldY = gp.tileSize*12;
        i++;
        
        gp.obj[mapNum][i] = new Obj_Coin(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*20;
        gp.obj[mapNum][i].worldY = gp.tileSize*8;
        i++;
        
        gp.obj[mapNum][i] = new Obj_Coin(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*26;
        gp.obj[mapNum][i].worldY = gp.tileSize*14;
        i++;
        
        gp.obj[mapNum][i] = new Obj_Axe(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*10;
        gp.obj[mapNum][i].worldY = gp.tileSize*13;
        i++;
        
        gp.obj[mapNum][i] = new Obj_Door(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*12;
        gp.obj[mapNum][i].worldY = gp.tileSize*13;
        i++;
        
        gp.obj[mapNum][i] = new Obj_Potion_Red(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*40;
        gp.obj[mapNum][i].worldY = gp.tileSize*26;
        i++;
        
        gp.obj[mapNum][i] = new Obj_Door(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*14;
        gp.obj[mapNum][i].worldY = gp.tileSize*32;
        i++;
        
        gp.obj[mapNum][i] = new Obj_Door(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*21;
        gp.obj[mapNum][i].worldY = gp.tileSize*35;
        i++;
        
        gp.obj[mapNum][i] = new Obj_Key(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*14;
        gp.obj[mapNum][i].worldY = gp.tileSize*35;  
        
    }
    
    public void setNPC(){
        
        int mapNum = 0;
        gp.npc[mapNum][0] = new NPC_OldMan(gp);
        gp.npc[mapNum][0].worldX = gp.tileSize*10;
        gp.npc[mapNum][0].worldY = gp.tileSize*12;
        
    }
    
    public void setMonster(){
        
        int i = 0;
        int mapNum = 0;
        gp.mon[mapNum][i] = new MON_GreenSlime(gp);
        gp.mon[mapNum][i].worldX = gp.tileSize * 29;
        gp.mon[mapNum][i].worldY = gp.tileSize * 39;
        i++;
        gp.mon[mapNum][i] = new MON_GreenSlime(gp);
        gp.mon[mapNum][i].worldX = gp.tileSize * 31;
        gp.mon[mapNum][i].worldY = gp.tileSize * 39;
        i++;
        gp.mon[mapNum][i] = new MON_GreenSlime(gp);
        gp.mon[mapNum][i].worldX = gp.tileSize * 31;
        gp.mon[mapNum][i].worldY = gp.tileSize * 36;
        i++;
        gp.mon[mapNum][i] = new MON_GreenSlime(gp);
        gp.mon[mapNum][i].worldX = gp.tileSize * 28;
        gp.mon[mapNum][i].worldY = gp.tileSize * 40;
        i++;
        gp.mon[mapNum][i] = new MON_GreenSlime(gp);
        gp.mon[mapNum][i].worldX = gp.tileSize * 32;
        gp.mon[mapNum][i].worldY = gp.tileSize * 37;
        
        
    }
    
    public void setInteractiveTile(){
        
        int i = 0;
        int mapNum = 0;
        
        
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 11,19); i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp,11,20); i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp,11,21); i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 12,21); i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp,13,21); i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp,14,21); i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp,14,22); i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp,22,25); i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp,22,26); i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp,27,35); i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp,33,37); i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp,33,38); i++;
        
    }
    
    
    public void setRespawnObjects(){
        int i = 0;
        int mapNum = 0;
        
        setMonster();
        gp.iTile[mapNum][i] = new IT_DryTree(gp,27,35); i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp,33,37); i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp,33,38); i++;
        gp.obj[mapNum][i] = new Obj_Door(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*21;
        gp.obj[mapNum][i].worldY = gp.tileSize*35;
        i++;
    }
    
    
}
