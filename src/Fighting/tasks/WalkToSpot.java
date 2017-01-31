package Fighting.tasks;

import java.util.concurrent.Callable;

import org.powerbot.script.Condition;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Npc;

import script.ManKiller;
import Fighting.Wrapper.Fighting;
import methods.Walker;
import script.Task;

public class WalkToSpot extends Task {

	private Walker walk = new Walker(ctx);
	
	public WalkToSpot(ClientContext ctx) {
		super(ctx);
		
	}

	@Override
	public boolean activate() {
		
		if (ctx.players.local().interacting().valid() || ctx.players.local().inCombat()){
			for(Npc npc : ctx.npcs.select().id(Fighting.getMenIDs()).nearest()){
				if(npc.interacting().equals(ctx.players.local()))
					return false;
			}
			return false;
		}
		
		if (!Fighting.getEating())
		{
			return !Fighting.getManArea().contains(ctx.players.local());
		}
		
		return ctx.inventory.select().id(Fighting.getFoodID()).count() > 0 && 
				!Fighting.getManArea().contains(ctx.players.local());
	}

	@Override
	public void execute() {
		ManKiller.ScriptStatus = "Walking to Man Area";
		if (!Fighting.getBanking())
		{
			Tile[] toArea = {
					new Tile(ctx.players.local().tile().x(), ctx.players.local().tile().y(), ctx.players.local().tile().floor()), 
					Fighting.getCentralArea().getRandomTile()
					};
			walk.walkPath(toArea);
			Condition.wait(new Callable<Boolean> () {
				
				@Override
				public Boolean call() throws Exception {
					return !ctx.players.local().inMotion();
				}
				
			});
		}
		else
		{
			walk.walkPath(Fighting.getPathToSpot());
		}
		
	}

}
