package Fighting.tasks;

import org.powerbot.script.rt4.ClientContext;

import Fighting.Wrapper.Fighting;
import methods.Walker;
import script.ManKiller;
import script.Task;

public class WalkToBank extends Task {

	private Walker walk = new Walker(ctx);
	
	public WalkToBank(ClientContext ctx) {
		super(ctx);
		
	}

	@Override
	public boolean activate() {
		
		return ctx.inventory.select().id(Fighting.getFoodID()).count() == 0 &&
				!Fighting.getBankArea().contains(ctx.players.local());
	}

	@Override
	public void execute() {
		ManKiller.ScriptStatus = "Walking to Bank Area";
		walk.walkPath(Fighting.getPathToBank());
		
	}

}
