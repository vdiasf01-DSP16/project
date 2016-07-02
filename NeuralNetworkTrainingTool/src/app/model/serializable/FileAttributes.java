package app.model.serializable;

import java.io.Serializable;

/**
 * The data set file attributes, set for training or testing 
 * on a neural network.
 *  
 * @author Vasco
 *
 */
public interface FileAttributes extends Serializable {

	/**
	 * The Header rows.
	 * 
	 * @return int
	 */
	int getHeaderRows();

	/**
	 * Set the number of file header rows.
	 * 
	 * @param headerRows 
	 */
	void setHeaderRows(int headerRows);

	/**
	 * The Footer rows.
	 * 
	 * @return int
	 */
	int getFooterRows();

	/**
	 * Set the number of file footer rows.
	 * 
	 * @param footerRows 
	 */
	void setFooterRows(int footerRows);

	/**
	 * The file name.
	 * 
	 * @return String
	 */
	String getFilename();

	/**
	 * The file name.
	 * 
	 * @param filename
	 */
	void setFilename(String filename);

	/**
	 * The file data separator.
	 * 
	 * @return String
	 */
	String getSeparator();

	/**
	 * The file data separator.
	 * 
	 * @param separator
	 */
	void setSeparator(String separator);

	/**
	 * Sets the start and end row index for the testing set.
	 * 
	 * @param testingStartIndex
	 * @param testingEndIndex
	 */
	void setTestingRangeIndex(int testingStartIndex, int testingEndIndex);

	/**
	 * The testing starting row index.
	 * 
	 * @return int
	 */
	int getTestingStartIndex();

	/**
	 * The testing end row index.
	 * 
	 * @return int
	 */
	int getTestingEndIndex();

	/**
	 * The training start and end row index.
	 *  
	 * @param trainingStartIndex
	 * @param trainingEndIndex
	 */
	void setTrainingRangeIndex(int trainingStartIndex, int trainingEndIndex);

	/**
	 * The training start row index.
	 * 
	 * @return int
	 */
	int getTrainingStartIndex();

	/**
	 * The training end row index.
	 * 
	 * @return int
	 */
	int getTrainingEndIndex();

	/**
	 * The default print of all data set.
	 */
	String toString();

	/**
	 * If set to false, the Training range is to be ignored.
	 * 
	 * @return true if has valid Training range.
	 */
	boolean isHasTrainingRange();

	/**
	 * Sets the Training range to be considered.
	 * 
	 * @param hasTrainingRange
	 */
	void setHasTrainingRange(boolean hasTrainingRange);

	/**
	 * If set to false, the Testing range is to be ignored.
	 * 
	 * @return true if has valid Traing range.
	 */
	boolean isHasTestingRange();

	/**
	 * Sets the Testing range to be considered.
	 * 
	 * @param hasTestingRange
	 */
	void setHasTestingRange(boolean hasTestingRange);

}