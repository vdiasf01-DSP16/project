package app.model.file;

import app.core.dataSet.FileParser;
import app.model.serializable.FileAttributes;

/**
 * The extension of the FileAttributes for when required to parse the file,
 * and display the results to the user on the attempts made to parse it 
 * correctly.
 * 
 * @author Vasco
 *
 */
public interface DataSetFileGUIReader extends FileAttributes, FileParser {

}