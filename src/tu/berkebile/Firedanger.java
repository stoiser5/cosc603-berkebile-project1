package tu.berkebile;


public class Firedanger {

	private static final double[][] constantsFineFuelMoisture = {{30.0, 19.2, 13.8, 22.5},{-1.859, -0.859, -0.579, -.774}};
	private static final double[] constantsTemperature = {4.5, 12.5, 27.5};
	private static final double[] constantsDryingFactor = {16., 10., 7., 5., 4., 3.};
	private double grassSpreadIndex;
	private double timberSpreadIndex;	
	public enum herbState {CURED, TRANSITION, GREEN};

	public Firedanger() {
		super();
		this.grassSpreadIndex = 0;
		this.timberSpreadIndex = 0;
	}


	public double getFireDangerIndex(double dryBulbTemperature, double wetBulbTemperature, boolean snowOnTheGround, double precipitation, 
			double windSpeed, int herbState, double buildupIndexYesterday) {

		double buildupIndex=0.;
		double timberSpreadIndex;
		double grassSpreadIndex;

		// determine if there is snow on the ground
		if (snowOnTheGround == false){

			double fineFuelMoisture = calculateFineFuelMoisture(dryBulbTemperature, wetBulbTemperature, herbState);
			
			double dryingFactor = calculateDryingFactor(fineFuelMoisture); 

			if (precipitation > 0.1){
				buildupIndex = calculateBuildupIndex(buildupIndexYesterday, precipitation) + dryingFactor;
			}
			else{
				buildupIndex = buildupIndexYesterday + dryingFactor;
			}
			
			double adjustedFuelMoisture = calculateAdjustedFuelMoisture (fineFuelMoisture, buildupIndex);
			
			if (adjustedFuelMoisture > 0.3){
				grassSpreadIndex = 1.;
				timberSpreadIndex = 1.;
			}
			else {
				
				if (windSpeed > 14.){
					grassSpreadIndex = .00918 * (windSpeed + 14.) * Math.pow((33. - fineFuelMoisture), 1.65) - 3.;
					timberSpreadIndex = .00918 * (windSpeed + 14.) * Math.pow((33. - adjustedFuelMoisture), 1.65) - 3.;
				}
				else{
					grassSpreadIndex = .01312 * (windSpeed + 6.) * Math.pow((33. - fineFuelMoisture), 1.65) - 3.;
					timberSpreadIndex = .01312 * (windSpeed + 6.) * Math.pow((33. - adjustedFuelMoisture), 1.65) - 3.;
					
				}
			}				

		}

		return 0.;
	}


	private double calculateAdjustedFuelMoisture(double fineFuelMoisture,
			double buildupIndex) {
		
		return 0.9 * fineFuelMoisture + 0.5 + 9.5 * Math.exp(-1.* buildupIndex/50.);
	}


	private double calculateBuildupIndex(double buildupIndexYesterday,
			double precipitation) {

		// double check this equation!!!!
		double factor1 =  1.-(-1. - Math.exp(buildupIndexYesterday/50.));
		double factor2 = Math.exp(-1.175 * (precipitation - 0.1));

		return -50. * Math.log(factor1 * factor2) ;
	}


	private double calculateDryingFactor(double fineFuelMoisture) {

		int i=0;
		for (i=0; i<=5; i++){
			if ((fineFuelMoisture > constantsDryingFactor[i]))
				break;
		}

		return i;
	}


	private double calculateFineFuelMoisture(double dryBulbTemperature,
			double wetBulbTemperature, int herbState) {

		double deltaT = dryBulbTemperature - wetBulbTemperature;
		double ffm = 0.; // fine fuel moisture

		if (deltaT < constantsTemperature[0]){
			ffm = constantsFineFuelMoisture[0][0] * Math.exp(constantsFineFuelMoisture[1][0] * deltaT);
		}
		else if (deltaT >= constantsTemperature[0] && deltaT < constantsTemperature[1]){
			ffm = constantsFineFuelMoisture[0][1] * Math.exp(constantsFineFuelMoisture[1][1] * deltaT);
		}
		else if (deltaT >= constantsTemperature[1] && deltaT <  constantsTemperature[2]){
			ffm = constantsFineFuelMoisture[0][2] * Math.exp(constantsFineFuelMoisture[1][2]* deltaT);
		}
		else{
			ffm = constantsFineFuelMoisture[0][3] * Math.exp(constantsFineFuelMoisture[1][3] * deltaT);
		}

		// if ffm is smaller than one, set it to one
		if (ffm < 1.){
			ffm = 1.;
		}

		// adjust fine fuel moisture according to herb stage
		if ((herbState == 1)){
			ffm = ffm + 5.;
		}
		else if (herbState == 2){
			ffm = ffm + 10.;
		}

		return ffm;
	}

}
