package tu.berkebile.science;

/**
 * The Class SpreadIndexVector.
 */
public class SpreadIndexVector {
	
	//instance variables
	/** The fine fuel moisture. */
	private double fineFuelMoisture;
	
	/** The adjusted fuel moisture. */
	private double adjustedFuelMoisture;
	
	/** The drying factor. */
	private double dryingFactor;
	
	/** The build up index. */
	private double buildUpIndex;
	
	/** The fine fuel spread. */
	private double fineFuelSpread;
	
	/** The timber spread index. */
	private double timberSpreadIndex;
	
	/** The fire load index. */
	private double fireLoadIndex;
	
	
	/**
	 * Instantiates a new spread index vector.
	 *
	 * @param fineFuelMoisture  represents the moisture content of litter (forest material that can burn) measured in %
	 * @param adjustedFuelMoisture fine fuel moisture adjusted for precipitation in the last day
	 * @param dryingFactor ranges between 1 an 7 (inversely proportional to ffm)
	 * @param buildUpIndex reflecting the combined cumulative effects of daily drying 
	 *                     and precipitation in fuels (such as dead vegetation) with a 10 day time lag constant.
	 * @param fineFuelSpread fire spread danger for grass fuel environments (measured in %)
	 * @param timberSpreadIndex fire spread danger in environments with timber fuel (forests, measured in %)
	 * @param fireLoadIndex rating of the maximum effort required to contain all probable fires 
	 */
	
	public SpreadIndexVector(double fineFuelMoisture,
			double adjustedFuelMoisture, double dryingFactor,
			double buildUpIndex, double fineFuelSpread,
			double timberSpreadIndex, double fireLoadIndex) {
		super();
		
		this.fineFuelMoisture = fineFuelMoisture;
		this.adjustedFuelMoisture = adjustedFuelMoisture;
		this.dryingFactor = dryingFactor;
		this.buildUpIndex = buildUpIndex;
		this.fineFuelSpread = fineFuelSpread;
		this.timberSpreadIndex = timberSpreadIndex;
		this.fireLoadIndex = fireLoadIndex;
	}
	
	/**
	 * Gets the fine fuel moisture.
	 *
	 * @return the fine fuel moisture
	 */
	//mutators
	public double getFineFuelMoisture() {
		return fineFuelMoisture;
	}

	/**
	 * Sets the fine fuel moisture.
	 *
	 * @param fineFuelMoisture the new fine fuel moisture
	 */
	public void setFineFuelMoisture(double fineFuelMoisture) {
		this.fineFuelMoisture = fineFuelMoisture;
	}

	/**
	 * Gets the adjusted fuel moisture.
	 *
	 * @return the adjusted fuel moisture
	 */
	public double getAdjustedFuelMoisture() {
		return adjustedFuelMoisture;
	}

	/**
	 * Sets the adjusted fuel moisture.
	 *
	 * @param adjustedFuelMoisture the new adjusted fuel moisture
	 */
	public void setAdjustedFuelMoisture(double adjustedFuelMoisture) {
		this.adjustedFuelMoisture = adjustedFuelMoisture;
	}

	/**
	 * Gets the builds the up index.
	 *
	 * @return the builds the up index
	 */
	public double getBuildUpIndex() {
		return buildUpIndex;
	}

	/**
	 * Sets the builds the up index.
	 *
	 * @param buildUpIndex the new builds the up index
	 */
	public void setBuildUpIndex(double buildUpIndex) {
		this.buildUpIndex = buildUpIndex;
	}
	
	/**
	 * Gets the drying factor.
	 *
	 * @return the drying factor
	 */
	public double getDryingFactor() {
		return dryingFactor;
	}
	
	/**
	 * Sets the drying factor.
	 *
	 * @param dryingFactor the new drying factor
	 */
	public void setDryingFactor(double dryingFactor) {
		this.dryingFactor = dryingFactor;
	}

	/**
	 * Gets the fine fuel spread.
	 *
	 * @return the fine fuel spread
	 */
	public double getFineFuelSpread() {
		return fineFuelSpread;
	}

	/**
	 * Sets the fine fuel spread.
	 *
	 * @param fineFuelSpread the new fine fuel spread
	 */
	public void setFineFuelSpread(double fineFuelSpread) {
		this.fineFuelSpread = fineFuelSpread;
	}

	/**
	 * Gets the timber spread index.
	 *
	 * @return the timber spread index
	 */
	public double getTimberSpreadIndex() {
		return timberSpreadIndex;
	}

	/**
	 * Sets the timber spread index.
	 *
	 * @param timberSpreadIndex the new timber spread index
	 */
	public void setTimberSpreadIndex(double timberSpreadIndex) {
		this.timberSpreadIndex = timberSpreadIndex;
	}

	/**
	 * Gets the fire load index.
	 *
	 * @return the fire load index
	 */
	public double getFireLoadIndex() {
		return fireLoadIndex;
	}

	/**
	 * Sets the fire load index.
	 *
	 * @param fireLoadIndex the new fire load index
	 */
	public void setFireLoadIndex(double fireLoadIndex) {
		this.fireLoadIndex = fireLoadIndex;
	}

	/**
	 * Prints out all fire danger indexes.
	 */
	public void printIndexes() {
		
		System.out.println("The fire danger index values are: " + "\n\n" +
				"Fine fuel moisture: " + fineFuelMoisture + "\n" +
				"Adjusted fuel moisture: " + adjustedFuelMoisture + "\n" +
				"Drying factor: " + dryingFactor + "\n" +
				"Buildup index: " + buildUpIndex + "\n" +
				"Fine fuel spread: " + fineFuelSpread + "\n" +
				"Timber spread index: " + timberSpreadIndex + "\n" +
				"Fire load index: " + fireLoadIndex);
		
	}


}
