package Fighting.Wrapper;

import org.powerbot.script.Area;
import org.powerbot.script.Tile;

public class Fighting {
	
	private static int[] manBounds = {-12, 16, -180, 0, -4, 20}; 
	private static int[] menIDs = {3078, 3079, 3080};
	private static int foodID;
	private static int foodAmount;
	private static boolean isBanking = false;
	private static boolean isEating = false;
	private static Area manArea = new Area(new Tile(3103, 3507, 0), new Tile(3091, 3513, 0));
	private static Area bankArea = new Area(new Tile(3097, 3497, 0), new Tile(3092, 3492, 0));
	private static Area insideArea = new Area(new Tile(3101, 3507, 0), new Tile(3090, 3513, 0));
	private static Area centralArea = new Area(new Tile(3099, 3511, 0), new Tile(3095, 3509, 0));
	private final static Tile[] pathToBank = new Tile[] { 
			new Tile(3100, 3500, 0),
			new Tile(3096, 3494, 0)
	};
	
	private final static Tile[] pathToSpot = new Tile[] {
			new Tile(3101, 3502, 0),
			new Tile(3097, 3510, 0)
	};
	private static Tile bankTile = new Tile(3096, 3494, 0);
	
	public static Tile getBankTile()
	{
		return bankTile;
	}
	public static Tile[] getPathToBank()
	{
		return pathToBank;
	}
	
	public static Tile[] getPathToSpot()
	{
		return pathToSpot;
	}
	
	public static Area getBankArea()
	{
		return bankArea;
	}
	public static Area getManArea()
	{
		return manArea;
	}
	public static int[] getManBounds()
	{
		return manBounds;
	}
	public static int getFoodID()
	{
		return foodID;
	}
	public static void setFoodID (int FoodID)
	{
		foodID = FoodID;
	}
	public static int[] getMenIDs()
	{
		return menIDs;
	}
	public static int getFoodAmount()
	{
		return foodAmount;
	}
	public static void setFoodAmount(int FoodAmnt)
	{
		foodAmount = FoodAmnt;
	}
	public static boolean getBanking() {
		return isBanking;
	}
	public static void setBanking(boolean isBanking) {
		Fighting.isBanking = isBanking;
	}
	public static boolean getEating() {
		return isEating;
	}
	public static void setEating(boolean isEating) {
		Fighting.isEating = isEating;
	}
	public static Area getInsideArea() {
		return insideArea;
	}
	public static Area getCentralArea() {
		return centralArea;
	}
}
