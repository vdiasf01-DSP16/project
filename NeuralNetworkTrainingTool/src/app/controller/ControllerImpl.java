package app.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
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
	private boolean projectIsSaved = true;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isAllSaved() {
		if ( projectIsSaved ) return true;
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveAll() {
		// TODO Auto-generated method stub
		projectIsSaved = true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public File getProjectFile() {
		return projectFile;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setProjectFile(File file) {
		this.projectFile = file;
		projectIsSaved = false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void closeProject() {
		projectIsSaved = false;
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
				FileOutputStream fout = null;
				ObjectOutputStream oos = null;
				try {
					fout = new FileOutputStream(projectFile);
					oos = new ObjectOutputStream(fout);
					oos.writeObject(projectData);
					projectIsSaved = true;
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						fout.close();
						oos.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void loadProjectFile(File file) {
		if ( file != null ) {
			if ( file.exists() & file.canRead() ) {
				projectFile = file;
				FileInputStream fin = null;
				ObjectInputStream ois = null;
				try {
					fin = new FileInputStream(file);
					ois = new ObjectInputStream(fin);
					try {
						projectData = (ProjectData) ois.readObject();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
					projectIsSaved = true;
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						fin.close();
						ois.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}
