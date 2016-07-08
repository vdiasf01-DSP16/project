package app.core.dataSet;

import java.util.List;

/**
 * The File Parser to work in conjunction with the FileAttributes at the 
 * DataSetFileGUIReader, to attempt parsing the file with the given 
 * information.
 * 
 * @author Vasco
 *
 */
public interface FileParser {
	/**
	 * Returns the list of header rows with all parsed columns per 
	 * supplied separator.
	 * 
	 * @return List of List of String
	 */
	public List<List<String>> getHeaderDataRows();
	
	/**
	 * The First data rows parsed as a list of String.
	 * By default, returns by default the first 10.
	 * 
	 * @param dataRows int
	 * @return List of String
	 */
	public List<List<String>> getFirstDataRows(int dataRows);
}
