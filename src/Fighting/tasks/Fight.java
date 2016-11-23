package Fighting.tasks;


import java.util.concurrent.Callable;

import org.powerbot.script.Condition;
import org.powerbot.script.Filter;
import org.powerbot.script.Random;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.Actor;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Interactive;
import org.powerbot.script.rt4.Npc;
import org.powerbot.script.rt4.Player;

import Fighting.Wrapper.Fighting;
import methods.Walker;
import script.ManKiller;
import script.Task;

public class Fight extends Task {
	
	//private static int[] doorIDs = {1524, 1521};
	final int[] doorBounds = {-24, 32, -108, 0, -12, 24};
	/*Area centerArea = new Area(new Tile(3098, 3509), new Tile(3096, 3510));
	Area upperArea = new Area(new Tile(3095, 3511), new Tile(3093, 3512));*/
	private Walker walk = new Walker(ctx);
	int[] ids = Fighting.getMenIDs();
	
	
	private static final Filter<Npc> MANFILTER = new Filter<Npc>() {
		@Override
		public boolean accept(Npc arg0) {
			if (!arg0.inCombat() && !arg0.interacting().valid()){
				return true;
			}
			return false;
		}
	};

	public Fight(ClientContext ctx) {
		super(ctx);
		
	}

	@Override
	public boolean activate() {
		
		// if the player has selected not to bank, getBanking() will return false everytime and short circuit
		// the if statement
		if (Fighting.getBanking() && ctx.inventory.select().id(Fighting.getFoodID()).count() == 0)
			return false;
		
		if(!ctx.players.local().inMotion() && !ctx.players.local().interacting().valid()  &&
				Fighting.getManArea().contains(ctx.players.local()) &&
				!ctx.npcs.select().select(MANFILTER).
				id(ids).each(Interactive.doSetBounds(Fighting.getManBounds())).
				within(Fighting.getManArea()).isEmpty())
		{
			for(Npc npc : ctx.npcs.select().id(ids).nearest()){
				if(npc.interacting().equals(ctx.players.local()))
					return false;
			}
			return true;
		}
		return false;
	}

	@Override
	public void execute() {
		
		Npc man = ctx.npcs.select().select(MANFILTER).
				id(ids).each(Interactive.doSetBounds(Fighting.getManBounds())).
				within(Fighting.getManArea()).nearest().poll();
		
		Tile[] toMan = new Tile[] {
				new Tile(ctx.players.local().tile().x(), ctx.players.local().tile().y(), ctx.players.local().tile().floor()),
				new Tile(man.tile().x(), man.tile().y(), man.tile().floor())
		};
		
		if(man.inViewport())
		{
			if (man.tile().matrix(ctx).reachable())
			{
				if (!Fighting.getInsideArea().contains(ctx.players.local()) &&
						Fighting.getInsideArea().contains(man)){
					if (Random.nextInt(0, 100) <= 49)
					{
						if (ctx.camera.yaw() < 237 || ctx.camera.yaw() > 322)
						{
							ctx.camera.angle(Random.nextInt(237, 322));
						}
					}
					else
					{
						ctx.movement.step(Fighting.getCentralArea().getRandomTile());
					}
					
			
					Condition.sleep(Random.nextInt(250, 750));
				}
				
				if (man.inMotion())
					man.hover();
				man.interact("Attack", "Man");
				Condition.wait(new Callable<Boolean> () {
	
					@Override
					public Boolean call() throws Exception {
						ManKiller.ScriptStatus = "Attacking man";
						return isInCombat(ctx.players.local());
					}
					
				});
			}
			else // not reachable
			{
				ManKiller.ScriptStatus = "Walking to Man";
				walk.walkPath(toMan);
				/*Condition.sleep(500 + rand.nextInt(1500));
				man.interact("Attack", "Man");
				
				Condition.wait(new Callable<Boolean> () {
					
					@Override
					public Boolean call() throws Exception {
						ManKiller.ScriptStatus = "Attacking man";
						return isInCombat(ctx.players.local());
					}
					
				});*/
			}
		}
		else{
			ctx.camera.turnTo(man);
			
		}

	}


	public  boolean isInCombat(Actor actor){
		return isAttacking(actor) || isBeingAttacked(actor);
	}
	
	/* checks if the player is being attacked by another "actor" */
	private boolean isBeingAttacked(Actor actor) {
		return isBeingTargeted(actor) || actor.inCombat();
	}

	private boolean isBeingTargeted(Actor actor) {
		if(!actor.valid()){
			return false;
		}
		ClientContext ctx = actor.ctx;
		
		for(Player player : ctx.players.select()){
			if(player.interacting().equals(actor))
				return true;
		}
		return false;
	
	}
	/* checks if the player is attacking a target */
	private boolean isAttacking(Actor actor) {
		Actor target = actor.interacting();
		return target.valid() && target.inCombat();
	}
	
}