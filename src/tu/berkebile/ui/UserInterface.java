package tu.berkebile.ui;

import tu.berkebile.science.*;


public class UserInterface {
	
	public static void main(String[] args) {
		
		// user data needed for fire danger determination
		double dryBulbTemperature = 60.0;  //deg Fahrenheit
		double wetBulbTemperature = 40.;
		boolean snowOnTheGround = false;
		double precipitation = 0.2;  // inches
		double windSpeed = 20.;  // miles per hour
		HerbState herbState = HerbState.TRANSITION; // CURED, TRANSITION, or GREEN
		double buildupIndexYesterday = 10.;
				
		
		Firedanger	today = new Firedanger();
		
		SpreadIndexVector index = today.getFireDangerIndexVector(dryBulbTemperature, wetBulbTemperature, snowOnTheGround, precipitation, 
				windSpeed, herbState, buildupIndexYesterday);
		
		// print fire danger indexes
		index.printIndexes();

	}

}
