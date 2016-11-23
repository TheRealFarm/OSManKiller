package Fighting.tasks;

import java.util.Random;
import java.util.Random;
import java.util.concurrent.Callable;

import org.powerbot.script.Condition;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Constants;
import org.powerbot.script.rt4.Game;
import org.powerbot.script.rt4.Item;

import script.ManKiller;
import script.Task;
import Fighting.Wrapper.*;

public class Eat extends Task {

	private Random rand = new Random();
	
	public Eat(ClientContext ctx) {
		super(ctx);
	}

	@Override
	public boolean activate() {
		
		if (Fighting.getManArea().contains(ctx.players.local()))
		{
			double curhp = ctx.skills.level(3);
			double maxhp = ctx.skills.realLevel(Constants.SKILLS_HITPOINTS);
	
			double healthPercent = (curhp/maxhp)*100;
			boolean eat = false;
			int randomHealth = rand.nextInt(8) - 5;
			// eat between 40 and 47% health
			if (healthPercent < 45.0 + randomHealth)	
			{
				// if there is food, eat
				if (ctx.inventory.select().id(Fighting.getFoodID()).count() > 0)
				{
					eat = true;
				}
				// no more food, check if eating is selected
				else if (!Fighting.getEating())
				{
					ctx.controller.stop();
				}
				else if (!Fighting.getBanking())
				{
					// if banking isn't selected, stop script
					System.out.println("Out of food and low health, stopping script");
					ctx.controller.stop();
				}
			}
			return eat;
		}
		return false;
	}

	@Override
	public void execute() {
		ManKiller.ScriptStatus = "Eating..";
		
		ctx.game.tab(Game.Tab.INVENTORY);
		
		Item edible = ctx.inventory.select().id(Fighting.getFoodID()).poll();
		// if there is enough food in the inventory, eat

		edible.interact("Eat");
		// sleep the thread
		if (rand.nextInt(100) < 40)
		{
			ctx.npcs.select().id(Fighting.getMenIDs()).nearest().poll().interact("Attack", "Man");
		}
		Condition.sleep(rand.nextInt(500) + 250);
		
	}

}