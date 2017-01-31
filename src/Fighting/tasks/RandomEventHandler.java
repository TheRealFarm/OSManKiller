package Fighting.tasks;

import org.powerbot.script.rt4.Npc;

import script.Task;

import java.util.concurrent.ThreadLocalRandom;

import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Interactive;

public class RandomEventHandler extends Task {

	private final int[] randomNpcBounds = {-48, 48, -88, 0, -32, 48};
	private Npc randomNpc = ctx.npcs.select().each(Interactive.doSetBounds(randomNpcBounds)).within(2.0).action("Dismiss").poll();
	
	public RandomEventHandler(ClientContext ctx) {
		super(ctx);
		
	}

	@Override
	public boolean activate() {
		
		if (randomNpc.valid())
		{
			try {
				Thread.sleep(ThreadLocalRandom.current().nextLong(1000, 4500));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return true;
		}
		
		return false;
	}

	@Override
	public void execute() {
		
		Condition.sleep(Random.nextInt(3500, 5500));
		String action = randomNpc.name().equalsIgnoreCase("genie") ? "Talk-to" : "Dismiss";
		if (randomNpc.interact(action))
		{
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("attempted to perform interaction : " + action + " on " + randomNpc.name());
	}

}
