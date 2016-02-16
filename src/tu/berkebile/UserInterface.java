package tu.berkebile;

public class UserInterface {
	

	public static void main(String[] args) {
		
		double dryBulbTemperature = 10.0;
		double wetBulbTemperature = 20;
		boolean snowOnTheGround = false;
		double precipitation = 1.5;
		double windSpeed = 20.;
		int herbState = 3;
		double buildupIndexYesterday = 1.5;
		
		//getFireDangerIndex(double dryBulbTemperature, double wetBulbTemperature, boolean snowOnTheGround, double precipitation, 
		//		double windSpeed, int herbState, double buildupIndexYesterday
		
		Firedanger	today = new Firedanger();
		
		double index = today.getFireDangerIndex(dryBulbTemperature, wetBulbTemperature, snowOnTheGround, precipitation,
				windSpeed, herbState, buildupIndexYesterday);
		
		System.out.println("The fire danger index is " + index + ".");

	}

}
