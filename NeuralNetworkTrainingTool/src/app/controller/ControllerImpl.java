package app.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import app.model.project.ProjectData;

/**
 * The Main Application Controller.
 * 
 * @author Vasco
 *
 */
public class ControllerImpl implements Controller {

	private File projectFile;
	private ProjectData projectData;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isAllSaved() {
		// TODO Auto-generated method stub
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveAll() {
		// TODO Auto-generated method stub
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public File getProjectFile() {
		// TODO Auto-generated method stub
		return projectFile;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setProjectFile(File file) {
		this.projectFile = file;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void closeProject() {
		// TODO Auto-generated method stub
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setProjectData(ProjectData projectData) {
		this.projectData = projectData;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveProject() {
		// TODO: Deal with the exceptions
		if ( projectFile != null ) {
			if ( ! projectFile.exists() ) {
				try {
					projectFile.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} 
			if ( projectFile.canWrite() ) {
				try {
					FileOutputStream fout = new FileOutputStream(projectFile);
					ObjectOutputStream oos = new ObjectOutputStream(fout);
					oos.writeObject(projectData);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProjectData getProjectData() {
		return projectData;
	}
}
