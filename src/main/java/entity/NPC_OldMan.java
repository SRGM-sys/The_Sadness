package entity;

import java.util.Random;
import main.GamePanel;
import object.Obj_Potion_Blue;

public class NPC_OldMan extends Entity{
    
    int pathIndex = 0; // Controla en qué paso de la ruta estamos
    int stepCounter = 0; // Cuenta los píxeles recorridos en el paso actual
    
    // Tu algoritmo traducido a arreglos
    String[] pathDirections = {"down", "right", "up", "right", "down", "left", "down", "right", "down"};
    int[] pathTiles = {1, 4, 1, 2, 5, 5, 4, 3, 13};
    
    public NPC_OldMan(GamePanel gp) {
        super(gp);
        direction = "down"; //Empezará mirando hacia abajo
        speed = 0;
        
        solidArea = new java.awt.Rectangle(8, 16, 24, 24);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getOldManImage();
        setDialogue();
    }
    
    public void getOldManImage(){
        
        up1 = setup("npc", "oldman_up_1", super.px, super.px);
        up2 = setup("npc", "oldman_up_2", super.px, super.px);
        down1 = setup("npc", "oldman_down_1", super.px, super.px);
        down2 = setup("npc", "oldman_down_2", super.px, super.px);
        left1 = setup("npc", "oldman_left_1", super.px, super.px);
        left2 = setup("npc", "oldman_left_2", super.px, super.px);
        right1 = setup("npc", "oldman_right_1", super.px, super.px);
        right2 = setup("npc", "oldman_right_2", super.px, super.px);
    }
    
    public void setDialogue(){

        dialogues[0][0] = "G-gracias por rescatarme, llevo aquí \nencerrado varios años.";
        dialogues[0][1] = "¿Acaso... eres el de la profecía? Un \ncaballero azul vendrá a vengarnos \nde lo que sucedió. Es hora de actuar.";
        dialogues[0][2] = "Pero me encuentro muy débil ahora, \nusa esa hacha y tráeme el elixir de \nvida que se encuentra en el bosque.";

        // Etapa 1: Le das la poción / Te pide que lo sigas a la cabaña
        dialogues[1][0] = "Glup-glup. Mucho mejor. Sígueme, \nharé un ritual para hacerte más \nfuerte.";

        // Etapa 2: Llegada a la cabaña / Abre la puerta y te da magia
        dialogues[2][0] = "Recibe la tristeza de todos aquellos \nque perecieron en el cataclismo.";
        dialogues[2][1] = "Ahora puedes regresar de la muerte \ny hacer magia (clic derecho). \nObtienes 3 pociones azules.";
        dialogues[2][2] = "Trae la tristeza a todos nuestros \nenemigos.";
    }
    
    // En este método trabajaremos la IA del NPC
    @Override
    public void setAction() {
        if (dialogueSet == 1) {
            speed = 1;

            if (pathIndex < pathDirections.length) {
                direction = pathDirections[pathIndex];

                if (!collisionOn) {
                    stepCounter += speed;
                } else {
                    // Si se choca y se atasca, dispara el escáner de puertas
                    openDoor();
                }

                if (stepCounter >= pathTiles[pathIndex] * gp.tileSize) {
                    stepCounter = 0; 
                    pathIndex++; 
                }
            } else {
                // El algoritmo terminó, llegó a la cabaña
                dialogueSet = 2; 
                direction = "down"; 
            }
        } else {
            speed = 0;
            direction = "down";
        }
    }
    
    @Override
    public void speak(){
        // 1. Evalúa si cambias de etapa (ej. si trajiste la poción)
        checkQuestProgress();

        // 2. Llama a la ventana de texto y avanza el índice
        super.speak();

        // 3. Si el texto se acaba, vuelve a la primera línea de la misma etapa
        if (dialogues[dialogueSet][dialogueIndex] == null) {
            dialogueIndex = 0; 
        }
    }
    
    public void checkQuestProgress() {
        // ETAPA 0 -> ETAPA 1: Buscar y entregar la poción
        if (dialogueSet == 0) {
            for (int i = 0; i < gp.player.inventory.size(); i++) {
                if (gp.player.inventory.get(i).name.equals("Poción Roja")) {
                    gp.player.inventory.remove(i); // El anciano se toma la poción

                    dialogueSet = 1; // Avanza la historia a la etapa 1
                    dialogueIndex = 0; // Reinicia el contador de texto para la nueva etapa

                    gp.soundEffect(2); // Sonido de powerup o curación
                    gp.ui.addMessage("Entregaste la Poción Roja al mago");
                    break;
                }
            }
        }
        // ETAPA 2: El mago te restaura los poderes en la cabaña
        else if (dialogueSet == 2) {
            // Si el jugador aún no tiene proyectil, se lo asignamos
            if (gp.player.projectile == null) {
                gp.player.projectile = new object.Obj_Fireball(gp);
                gp.player.inventory.add(new Obj_Potion_Blue(gp));
                gp.player.inventory.add(new Obj_Potion_Blue(gp));
                gp.player.inventory.add(new Obj_Potion_Blue(gp));
                gp.ui.addMessage("¡Poder mágico restaurado!");
                gp.soundEffect(3); // Sonido de desbloqueo
            }
        }
    }
    
    public void openDoor() {
        for (int i = 0; i < gp.obj[gp.currentMap].length; i++) {
            if (gp.obj[gp.currentMap][i] != null && gp.obj[gp.currentMap][i].name.equals("door")) {

                // Calculamos la distancia exacta entre el anciano y la puerta
                int distanceX = Math.abs(worldX - gp.obj[gp.currentMap][i].worldX);
                int distanceY = Math.abs(worldY - gp.obj[gp.currentMap][i].worldY);

                // Si la puerta está a 1 bloque de distancia (contacto físico)
                if (distanceX <= gp.tileSize && distanceY <= gp.tileSize) {
                    gp.obj[gp.currentMap][i] = null; // La puerta desaparece
                    gp.soundEffect(3); 
                    gp.ui.addMessage("El anciano despejó el camino");
                }
            }
        }
    }
    
}
