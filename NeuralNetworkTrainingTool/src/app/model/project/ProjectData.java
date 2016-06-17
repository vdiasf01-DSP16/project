package app.model.project;

import java.io.Serializable;
import java.util.Date;

/**
 * The Project details data.
 * 
 * @author Vasco
 *
 */
public interface ProjectData extends Serializable {

	/**
	 * The project name.
	 * 
	 * @return String
	 */
	public String getProjectName();

	/**
	 * Setting the project name.
	 * 
	 * @param projectName String
	 */
	public void setProjectName(String projectName);

	/**
	 * The Project abstract.
	 * 
	 * @return String
	 */
	public String getProjectAbstract();

	/**
	 * Setting the project abstract.
	 * 
	 * @param projectAbstract String
	 */
	void setProjectAbstract(String projectAbstract);

	/**
	 * The project summary.
	 * 
	 * @return String
	 */
	public String getProjectSummary();

	/**
	 * Setting the project summary.
	 * 
	 * @param projectSummary String
	 */
	public void setProjectSummary(String projectSummary);

	/**
	 * The project description.
	 * 
	 * @return String
	 */
	public String getProjectDescription();

	/**
	 * Setting the project description.
	 * 
	 * @param projectDescription String
	 */
	public void setProjectDescription(String projectDescription);

	/**
	 * The project authors.
	 * 
	 * @return String
	 */
	public String getProjectAuthors();

	/**
	 * Setting the project authors.
	 * 
	 * @param projectAuthors String
	 */
	public void setProjectAuthors(String projectAuthors);

	/**
	 * The project creation date.
	 * 
	 * @return Date
	 */
	public Date getCreationDate();

	/**
	 * Setting the project creation date.
	 * 
	 * @param creationDate Date
	 */
	public void setCreationDate(Date creationDate);

}