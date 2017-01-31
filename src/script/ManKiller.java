package script;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

import javax.swing.JOptionPane;

import org.powerbot.script.Condition;
import org.powerbot.script.MessageEvent;
import org.powerbot.script.MessageListener;
import org.powerbot.script.PaintListener;
import org.powerbot.script.PollingScript;
import org.powerbot.script.Random;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Component;
import org.powerbot.script.rt4.Constants;
import org.powerbot.script.rt4.Game;

import Fighting.Wrapper.Fighting;
import Fighting.tasks.*;

@ManKiller.Manifest(name="ManKiller", description = "Kills Edgeville Men")


public class ManKiller extends PollingScript<ClientContext> implements PaintListener {

private List<Task> taskList = new ArrayList<Task>();
private GUI gui = new GUI(ctx);

public static String ScriptStatus;

private final Color mouseColor = new Color(214, 42, 0), boxColor = new Color(214, 42, 0, 170), textColor = new Color(250,250,250);
private final Font font = new Font(("Verdana"), Font.BOLD, 12);

private long initialTime;
private int hours, minutes, seconds;
private int initExp = ctx.skills.experience(0) + ctx.skills.experience(1) + ctx.skills.experience(2) + ctx.skills.experience(3)
+ ctx.skills.experience(4) + ctx.skills.experience(6);
private int expGained = 0;
private double runTime, expPerHour;
	
	@Override
	public void start(){
		
		if(!ctx.game.loggedIn()){
            JOptionPane.showMessageDialog(null," Please log in. ");
            ctx.controller.stop();
            return;
        }
		
		ScriptStatus = "Waiting for GUI..";
		System.out.println(ScriptStatus);
		gui.setVisible(true);

        Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return gui.guiDone;
            }
        }, 500, 500);
        
	    gui.setVisible(false);
	    initialTime = System.currentTimeMillis();
	        
		if (Fighting.getBanking() && Fighting.getEating())
		{
			taskList.addAll(Arrays.asList(new Banking(ctx), new Eat(ctx), new Fight(ctx),
					new RandomEventHandler(ctx), new ToggleRun(ctx), 
					new WalkToBank(ctx), new WalkToSpot(ctx)));
		}
		else if (Fighting.getEating())
		{
			taskList.addAll(Arrays.asList(new Eat(ctx), new Fight(ctx),
					new RandomEventHandler(ctx), new ToggleRun(ctx), new WalkToSpot(ctx)));
		}
		else
		{
			taskList.addAll(Arrays.asList(new Fight(ctx), new Eat(ctx),
					new RandomEventHandler(ctx), new ToggleRun(ctx), new WalkToSpot(ctx)));
		}
		ctx.camera.pitch(org.powerbot.script.Random.nextInt(30, 45));
		
	}
	
	@Override
	public void poll() {
		if (minutes == 28)
			ctx.controller.stop();
		for (Task task : taskList){
			if(task.activate()){
				task.execute();
				AntiBan();
				System.out.println(ScriptStatus);
			}
		}
		
	}


	private void AntiBan() {
		int rand = Random.nextInt(0, 200);
		int randomInt;
		switch (rand){
		case 0:
			ScriptStatus = "Anti-Ban(1)";
			ctx.camera.angle(Random.nextInt(0, 300));
			break;
		case 1:
			ScriptStatus = "Anti-Ban(2)";
			ctx.camera.pitch(Random.nextInt(30, 59));
			break;
		case 10:
			ScriptStatus = "Anti-Ban(1)";
			ctx.camera.angle(Random.nextInt(0, 300));
			break;
		case 25:
			ScriptStatus = "Anti-Ban(3)";
			randomInt = Random.nextInt(0, 2);
			Condition.sleep(Random.nextInt(200, 500));
			switch (randomInt){
			case 0:
				ctx.game.tab(Game.Tab.STATS);
				break;
			case 1:
				ctx.game.tab(Game.Tab.ATTACK);
				break;
			case 2:
				ctx.game.tab(Game.Tab.EQUIPMENT);
				break;
			}
			break;
		case 50:
			ScriptStatus = "Taking a short break";
			Condition.sleep(4000 + Random.nextInt(0, 10000));
			break;
		case 100:
			ScriptStatus = "Anti-Ban(3)";
			randomInt = Random.nextInt(0, 2);
			Condition.sleep(Random.nextInt(200, 500));
			switch (randomInt){
			case 0:
				ctx.game.tab(Game.Tab.STATS);
				break;
			case 1:
				ctx.game.tab(Game.Tab.ATTACK);
				break;
			case 2:
				ctx.game.tab(Game.Tab.EQUIPMENT);
				break;
			}
			break;
		case 125:
			ScriptStatus = "Taking a long break";
			Condition.sleep(30000 + Random.nextInt(0, 4000));
			break;
		case 150: 
			ScriptStatus = "Taking a long break";
			Condition.sleep(15000 + Random.nextInt(0, 4000));
			break;
		case 175:
			ScriptStatus = "Anti-Ban(1)";
			ctx.camera.angle(Random.nextInt(0, 300));
			break;
		case 198:
			ScriptStatus = "Taking a short break";
			Condition.sleep(6000 + Random.nextInt(0, 10000));
			break;
		}
		
	}

	public void repaint(Graphics g1) {

        Graphics2D g = (Graphics2D)g1;
        int x = (int) ctx.input.getLocation().getX();
        int y = (int) ctx.input.getLocation().getY();

        g.setColor(mouseColor);
        g.drawLine(x, y - 10, x, y + 10);
        g.drawLine(x - 10, y, x + 10, y);

        hours = (int) ((System.currentTimeMillis() - initialTime) / 3600000);
        minutes = (int) ((System.currentTimeMillis() - initialTime) / 60000 % 60);
        seconds = (int) ((System.currentTimeMillis() - initialTime) / 1000) % 60;
        runTime = (double) (System.currentTimeMillis() - initialTime) / 3600000;

        g.setColor(boxColor);
       
        g.fillRoundRect(8, 8, 250, 110, 5,  5);
        g.setColor(textColor);
        g.setFont(font);
        g.drawString("Script Status: " + ScriptStatus, 22, 110);

        expGained = (ctx.skills.experience(0) + ctx.skills.experience(1) + ctx.skills.experience(2) + ctx.skills.experience(3)
        + ctx.skills.experience(4) + ctx.skills.experience(6)) - initExp;
        expPerHour = expGained / runTime;

        g.drawString("Real Man Killer", 58, 24);
        g.drawString("1.00v ", 170, 24);
        g.drawString("Experience Gained: " + expGained, 22, 50);
        g.drawString(String.format("Time Running: %02d:%02d:%02d" , hours, minutes, seconds), 22, 70);
        g.drawString("Experience/Hour : "  + (int)expPerHour, 22, 90);
    }
}

