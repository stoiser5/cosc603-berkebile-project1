package tu.berkebile.ui;

import tu.berkebile.science.HerbState;

// TODO: Auto-generated Javadoc
/**
 * stores parameters describing the current environmental conditions important for calculating the 
 * current fire danger
 * 
 * <ul>
 * <li>dryBulbTemperature The dry-bulb temperature (DBT) is the temperature of air measured
 *                           by a thermometer freely exposed to the air but shielded from radiation
 *                           and moisture</li>
 * <li>wetBulbTemperature The temperature a parcel of air would have if it were cooled to saturation 
 *                           (100% relative humidity).</li>
 * <li>snowOnTheGround a value of <code>true</code> means there is snow lying on the ground. </li>
 * <li>precipitation Amount of rain in the last 24 hours measured in inches.</li>
 * <li>windSpeed Current windspeed in miles per hour.</li>
 * <li>herbState the herb state of the district(either cured, transition, green).</li>
 * <li>buildupIndexYesterday Yesterdays BUI reflecting the combined cumulative effects of daily drying 
 *                              and precipitation in fuels with a 10 day time lag constant.</li>
 * </ul>
 */
public class Condition {
	
	/** The dry bulb temperature. */
	private double dryBulbTemperature;
	
	/** The wet bulb temperature. */
	private double wetBulbTemperature;
	
	/** The snow on the ground. */
	private boolean snowOnTheGround;
	
	/** The precipitation. */
	private double precipitation; 
	
	/** The wind speed. */
	private double windSpeed;
	
	/** The herb state. */
	private HerbState herbState;
	
	/** The buildup index yesterday. */
	private double buildupIndexYesterday;
	
	/**
	 * Instantiates a new condition.
	 *
	 * @param dryBulbTemperature the dry bulb temperature
	 * @param wetBulbTemperature the wet bulb temperature
	 * @param snowOnTheGround the snow on the ground
	 * @param precipitation the precipitation
	 * @param windSpeed the wind speed
	 * @param herbState the herb state
	 * @param buildupIndexYesterday the buildup index yesterday
	 */
	public Condition(double dryBulbTemperature, double wetBulbTemperature,
			boolean snowOnTheGround, double precipitation, double windSpeed,
			HerbState herbState, double buildupIndexYesterday) {
		super();
		this.dryBulbTemperature = dryBulbTemperature;
		this.wetBulbTemperature = wetBulbTemperature;
		this.snowOnTheGround = snowOnTheGround;
		this.precipitation = precipitation;
		this.windSpeed = windSpeed;
		this.herbState = herbState;
		this.buildupIndexYesterday = buildupIndexYesterday;
	}

	/**
	 * Gets the dry bulb temperature.
	 *
	 * @return the dry bulb temperature
	 */
	public double getDryBulbTemperature() {
		return dryBulbTemperature;
	}

	/**
	 * Sets the dry bulb temperature.
	 *
	 * @param dryBulbTemperature the new dry bulb temperature
	 */
	public void setDryBulbTemperature(double dryBulbTemperature) {
		this.dryBulbTemperature = dryBulbTemperature;
	}

	/**
	 * Gets the wet bulb temperature.
	 *
	 * @return the wet bulb temperature
	 */
	public double getWetBulbTemperature() {
		return wetBulbTemperature;
	}

	/**
	 * Sets the wet bulb temperature.
	 *
	 * @param wetBulbTemperature the new wet bulb temperature
	 */
	public void setWetBulbTemperature(double wetBulbTemperature) {
		this.wetBulbTemperature = wetBulbTemperature;
	}

	/**
	 * Checks if is snow on the ground.
	 *
	 * @return true, if is snow on the ground
	 */
	public boolean isSnowOnTheGround() {
		return snowOnTheGround;
	}

	/**
	 * Sets the snow on the ground.
	 *
	 * @param snowOnTheGround the new snow on the ground
	 */
	public void setSnowOnTheGround(boolean snowOnTheGround) {
		this.snowOnTheGround = snowOnTheGround;
	}

	/**
	 * Gets the precipitation.
	 *
	 * @return the precipitation
	 */
	public double getPrecipitation() {
		return precipitation;
	}

	/**
	 * Sets the precipitation.
	 *
	 * @param precipitation the new precipitation
	 */
	public void setPrecipitation(double precipitation) {
		this.precipitation = precipitation;
	}

	/**
	 * Gets the wind speed.
	 *
	 * @return the wind speed
	 */
	public double getWindSpeed() {
		return windSpeed;
	}

	/**
	 * Sets the wind speed.
	 *
	 * @param windSpeed the new wind speed
	 */
	public void setWindSpeed(double windSpeed) {
		this.windSpeed = windSpeed;
	}

	/**
	 * Gets the herb state.
	 *
	 * @return the herb state
	 */
	public HerbState getHerbState() {
		return herbState;
	}

	/**
	 * Sets the herb state.
	 *
	 * @param herbState the new herb state
	 */
	public void setHerbState(HerbState herbState) {
		this.herbState = herbState;
	}

	/**
	 * Gets the buildup index yesterday.
	 *
	 * @return the buildup index yesterday
	 */
	public double getBuildupIndexYesterday() {
		return buildupIndexYesterday;
	}

	/**
	 * Sets the buildup index yesterday.
	 *
	 * @param buildupIndexYesterday the new buildup index yesterday
	 */
	public void setBuildupIndexYesterday(double buildupIndexYesterday) {
		this.buildupIndexYesterday = buildupIndexYesterday;
	}

	
}
