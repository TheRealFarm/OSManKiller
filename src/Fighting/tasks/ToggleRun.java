package Fighting.tasks;

import org.powerbot.script.Condition;
import org.powerbot.script.rt4.ClientContext;

import script.Task;

public class ToggleRun extends Task {

	public ToggleRun(ClientContext ctx) {
		super(ctx);
		
	}

	@Override
	public boolean activate() {
		// if the player isn't running
		if (!ctx.movement.running())
		{
			// re-toggle if energy level is above 30
			if (ctx.movement.energyLevel() > 29)
			{
				// add randomness so it doesn't always toggle immediately at 30
				if (org.powerbot.script.Random.nextInt(0, 100) <= 50)
				{
					Condition.sleep(2250 + org.powerbot.script.Random.nextInt(0, 5000));
					return true;
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public void execute() {
		// set the running to true
		ctx.movement.running(true);
		
	}

}