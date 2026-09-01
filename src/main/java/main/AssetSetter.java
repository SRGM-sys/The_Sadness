package main;

import entity.NPC_OldMan;
import monster.MON_GreenSlime;
import object.Obj_Door;

public class AssetSetter {
    
    private int maxSlime = 5;
    
    GamePanel gp;
    
    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }
    
    public void setObject(){
        
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
        gp.mon[i].worldX = gp.tileSize * 23;
        gp.mon[i].worldY = gp.tileSize * 37;
        i++;
        gp.mon[i] = new MON_GreenSlime(gp);
        gp.mon[i].worldX = gp.tileSize * 22;
        gp.mon[i].worldY = gp.tileSize * 32;
        i++;
        gp.mon[i] = new MON_GreenSlime(gp);
        gp.mon[i].worldX = gp.tileSize * 25;
        gp.mon[i].worldY = gp.tileSize * 42;
        i++;
        gp.mon[i] = new MON_GreenSlime(gp);
        gp.mon[i].worldX = gp.tileSize * 25;
        gp.mon[i].worldY = gp.tileSize * 32;
        
    }
    
    
}
