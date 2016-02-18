package tu.berkebile.science;



public class Firedanger {

	private static final double[][] constantsFineFuelMoisture = {{30.0, 19.2, 13.8, 22.5},{-1.859, -0.859, -0.579, -.774}};
	private static final double[] constantsTemperature = {4.5, 12.5, 27.5};
	private static final double[] constantsDryingFactor = {16., 10., 7., 5., 4., 3.};
	
	public enum HerbState {CURED, TRANSITION, GREEN};

	public Firedanger() {
		super();
	}


	public SpreadIndexVector getFireDangerIndexVector(double dryBulbTemperature, double wetBulbTemperature, boolean snowOnTheGround, double precipitation, 
			double windSpeed, HerbState herbState, double buildupIndexYesterday) {

		double fineFuelMoisture=0.;
		double adjustedFuelMoisture=0;
		double buildupIndex=buildupIndexYesterday;
		double timberSpreadIndex=0;
		double grassSpreadIndex=0;
		double dryingFactor=0;
		double fireLoadIndex=0;

		// determine if there is snow on the ground
		if (snowOnTheGround == false){

			fineFuelMoisture = calculateFineFuelMoisture(dryBulbTemperature, wetBulbTemperature, herbState);

			dryingFactor = calculateDryingFactor(fineFuelMoisture); 

			if (precipitation > 0.1){
				buildupIndex = calculateBuildupIndex(buildupIndexYesterday, precipitation) + dryingFactor;
			}
			else{
				buildupIndex = buildupIndexYesterday + dryingFactor;
			}

			adjustedFuelMoisture = calculateAdjustedFuelMoisture (fineFuelMoisture, buildupIndex);

			grassSpreadIndex = calculateGrassSpreadIndex(fineFuelMoisture, adjustedFuelMoisture, windSpeed);

			timberSpreadIndex = calculateTimberSpreadIndex(adjustedFuelMoisture, windSpeed);	
			
			if ((timberSpreadIndex > 0.) && (buildupIndex > 0.)){
				fireLoadIndex = calculateFireLoadIndex(timberSpreadIndex, buildupIndex);
			}
			
		}

		return new SpreadIndexVector(fineFuelMoisture, adjustedFuelMoisture, buildupIndex, 
				                     grassSpreadIndex, timberSpreadIndex, fireLoadIndex);
	}


	private double calculateFireLoadIndex(double timberSpreadIndex,
			double buildupIndex) {
		
		double fireLoadIndex = 1.75*Math.log10(timberSpreadIndex) + .32*Math.log10(buildupIndex) - 1.64;
		
		return (fireLoadIndex > 99.) ? 99. : fireLoadIndex;
	}


	private double calculateTimberSpreadIndex(double adjustedFuelMoisture,
			double windSpeed) {
		
		double timberSpreadIndex=0;
		
		if (adjustedFuelMoisture > 0.3){
			return 1.;
		}
		else if (windSpeed >= 14.){

			timberSpreadIndex = .00918 * (windSpeed + 14.) * Math.pow((33. - adjustedFuelMoisture), 1.65) - 3.;
			
			return (timberSpreadIndex > 99.) ? 99. : timberSpreadIndex;
		}
		else{
			timberSpreadIndex = .01312 * (windSpeed + 6.) * Math.pow((33. - adjustedFuelMoisture), 1.65) - 3.;
		}
		
		return timberSpreadIndex;
	}


	private double calculateGrassSpreadIndex(double fineFuelMoisture, double adjustedFuelMoisture, double windSpeed) {

		double grassSpreadIndex = 1.;
		
		if (adjustedFuelMoisture > 0.3){
			return 1.;
		}
		else if (windSpeed >= 14.){

			grassSpreadIndex = .00918 * (windSpeed + 14.) * Math.pow((33. - fineFuelMoisture), 1.65) - 3.;
			
			return (grassSpreadIndex > 99.) ? 99. : grassSpreadIndex;
		}
		else{
			grassSpreadIndex = .01312 * (windSpeed + 6.) * Math.pow((33. - fineFuelMoisture), 1.65) - 3.;
		}
		return grassSpreadIndex;
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
			double wetBulbTemperature, Herbstate herbState) {

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
		if (herbState == HerbState.CURED){
			ffm = ffm + 5.;
		}
		else if (herbState == HerbState.TRANSITION){
			ffm = ffm + 10.;
		}

		return ffm;
	}

}
