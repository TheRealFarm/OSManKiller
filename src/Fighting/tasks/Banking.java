package Fighting.tasks;

import java.util.concurrent.Callable;

import org.powerbot.script.Condition;
import org.powerbot.script.rt4.Bank;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GameObject;

import script.ManKiller;
import script.Task;
import Fighting.Wrapper.*;

public class Banking extends Task {

	public Banking(ClientContext ctx) {
		super(ctx);
		
	}

	@Override
	public boolean activate() {
		
		return Fighting.getBankArea().contains(ctx.players.local()) 
				&& ctx.inventory.select().id(Fighting.getFoodID()).count() == 0;
	}

	@Override
	public void execute() {
		GameObject booth = ctx.objects.select(Fighting.getBankTile(), 2).nearest().poll();
		ManKiller.ScriptStatus = "Banking";
		if (!ctx.bank.open())
		{
			booth.interact("Bank", "Bank booth");
			
			Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return ctx.bank.opened();
                }
            });
		}
		else
		{
			ctx.bank.depositInventory();
			// cannot withdraw specified amount
			if (!ctx.bank.withdraw(Fighting.getFoodID(), Fighting.getFoodAmount()))
			{
				// withdraw the rest
				if (!ctx.bank.withdraw(Fighting.getFoodID(), Bank.Amount.ALL))
				{
					// none left
					System.out.println("Item is not in bank, stopping script");
					ctx.controller.stop();
				}
			}
			ctx.bank.close();
		}
	}

}
