package main;

import entity.NPC_OldMan;
import monster.MON_GreenSlime;
import object.*;

public class AssetSetter {
    
    private int maxSlime = 5;
    
    GamePanel gp;
    
    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }
    
    public void setObject(){
        int i = 0;
        
        gp.obj[i] = new Obj_Coin(gp);
        gp.obj[i].worldX = gp.tileSize*25;
        gp.obj[i].worldY = gp.tileSize*19;
        i++;
        gp.obj[i] = new Obj_Key(gp);
        gp.obj[i].worldX = gp.tileSize*23;
        gp.obj[i].worldY = gp.tileSize*19;
        i++;
        gp.obj[i] = new Obj_Potion_Blue(gp);
        gp.obj[i].worldX = gp.tileSize*26;
        gp.obj[i].worldY = gp.tileSize*21;
        i++;
        gp.obj[i] = new Obj_Axe(gp);
        gp.obj[i].worldX = gp.tileSize*33;
        gp.obj[i].worldY = gp.tileSize*21;
        i++;
        gp.obj[i] = new Obj_Shield_Blue(gp);
        gp.obj[i].worldX = gp.tileSize*35;
        gp.obj[i].worldY = gp.tileSize*21;
        i++;
        gp.obj[i] = new Obj_Potion_Red(gp);
        gp.obj[i].worldX = gp.tileSize*37;
        gp.obj[i].worldY = gp.tileSize*21;
    }
    
    public void setNPC(){
        gp.npc[0] = new NPC_OldMan(gp);
        gp.npc[0].worldX = gp.tileSize*21;
        gp.npc[0].worldY = gp.tileSize*21;
    }
    
    public void setMonster(){
        int i = 0;
        
        gp.mon[i] = new MON_GreenSlime(gp);
        gp.mon[i].worldX = gp.tileSize * 23;
        gp.mon[i].worldY = gp.tileSize * 36;
        i++;
        gp.mon[i] = new MON_GreenSlime(gp);
        gp.mon[i].worldX = gp.tileSize * 21;
        gp.mon[i].worldY = gp.tileSize * 37;
        i++;
        gp.mon[i] = new MON_GreenSlime(gp);
        gp.mon[i].worldX = gp.tileSize * 23;
        gp.mon[i].worldY = gp.tileSize * 32;
        i++;
        gp.mon[i] = new MON_GreenSlime(gp);
        gp.mon[i].worldX = gp.tileSize * 22;
        gp.mon[i].worldY = gp.tileSize * 42;
        i++;
        gp.mon[i] = new MON_GreenSlime(gp);
        gp.mon[i].worldX = gp.tileSize * 23;
        gp.mon[i].worldY = gp.tileSize * 31;
        
    }
    
    
}
