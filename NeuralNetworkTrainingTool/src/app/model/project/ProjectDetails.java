package app.model.project;

import java.util.Date;

/**
 * Data object collecting required data from user to be added to the project.
 */
public class ProjectDetails implements ProjectData {
    /**
     * The project name.
     */
    private String projectName;
    
    /**
     * The project abstract
     */
    private String projectAbstract;
    
    /**
     * The project summary.
     */
    private String projectSummary;
    
    /**
     * The project description.
     */
    private String projectDescription;
    
    /**
     * The project authors.
     */
    private String projectAuthors;
    
    /**
     * The project creation date.
     */
    private Date creationDate;

    /* (non-Javadoc)
     * @see app.model.project.ProjectData#getProjectName()
     */
    @Override
    public String getProjectName() {
        return projectName;
    }
    
    /* (non-Javadoc)
     * @see app.model.project.ProjectData#setProjectName(java.lang.String)
     */
    @Override
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
    
    /* (non-Javadoc)
     * @see app.model.project.ProjectData#getProjectAbstract()
     */
    @Override
    public String getProjectAbstract() {
        return projectAbstract;
    }
    
    /* (non-Javadoc)
     * @see app.model.project.ProjectData#setProjectAbstract(java.lang.String)
     */
    @Override
    public void setProjectAbstract(String projectAbstract) {
        this.projectAbstract = projectAbstract;
    }
    
    /* (non-Javadoc)
     * @see app.model.project.ProjectData#getProjectSummary()
     */
    @Override
    public String getProjectSummary() {
        return projectSummary;
    }
    
    /* (non-Javadoc)
     * @see app.model.project.ProjectData#setProjectSummary(java.lang.String)
     */
    @Override
    public void setProjectSummary(String projectSummary) {
        this.projectSummary = projectSummary;
    }
    
    /* (non-Javadoc)
     * @see app.model.project.ProjectData#getProjectDescription()
     */
    @Override
    public String getProjectDescription() {
        return projectDescription;
    }
    
    /* (non-Javadoc)
     * @see app.model.project.ProjectData#setProjectDescription(java.lang.String)
     */
    @Override
    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }
    
    /* (non-Javadoc)
     * @see app.model.project.ProjectData#getProjectAuthors()
     */
    @Override
    public String getProjectAuthors() {
        return projectAuthors;
    }
    
    /* (non-Javadoc)
     * @see app.model.project.ProjectData#setProjectAuthors(java.lang.String)
     */
    @Override
    public void setProjectAuthors(String projectAuthors) {
        this.projectAuthors = projectAuthors;
    }
    
    /* (non-Javadoc)
     * @see app.model.project.ProjectData#getCreationDate()
     */
    @Override
    public Date getCreationDate() {
        return creationDate;
    }
    
    /* (non-Javadoc)
     * @see app.model.project.ProjectData#setCreationDate(java.util.Date)
     */
    @Override
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
