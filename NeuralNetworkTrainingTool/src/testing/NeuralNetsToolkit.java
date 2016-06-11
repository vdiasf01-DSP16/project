package testing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;

import app.controller.Controller;
import app.controller.ControllerImpl;
import app.model.project.ProjectData;
import app.model.project.ProjectDetails;
import app.view.project.Project;
import app.view.widget.Message;

public class NeuralNetsToolkit extends JFrame {

	/**
     * Serialising the UID version.
     */
    private static final long serialVersionUID = 7852511315176274493L;

    /**
     * The controller to be used with this view.
     */
    private Controller mainController;

    /**
     * Application width size.
     */
    private final int APPLICATION_WIDTH = 1200;

    /**
     * Application height size.
     */
    private final int APPLICATION_HEIGHT = 780;
    
    
    /*************************************************************************
     *                                                                       *
     *                    M  E  N  U     L  A  B  E  L  S                    *
     *                                                                       *
     *************************************************************************/

    /**
     * Menu File label.
     */
    private final String MENU_FILE = "File";

    /**
     * Menu File New label.
     */
    private final String MENU_FILE_NEW = "New Project ..";

    /**
     * Menu File Edit label.
     */
    private final String MENU_FILE_EDIT = "Edit Project ..";

    /**
     * Menu File Load label.
     */
    private final String MENU_FILE_LOAD = "Load Project ..";

    /**
     * Menu File Save label.
     */
    private final String MENU_FILE_SAVE = "Save Project ..";

    /**
     * Menu File Close label.
     */
    private final String MENU_FILE_CLOSE = "Close Project";

    /**
     * Menu File Import label.
     */
    private final String MENU_FILE_IMPORT = "Import";

    /**
     * Menu File Import Neural Network configuration label.
     */
    private final String MENU_FILE_IMPORT_NNCONFIG = "Neural Network Config ..";

    /**
     * Menu File Export label.
     */
    private final String MENU_FILE_EXPORT = "Export";

    /**
     * Menu File Export Neural Network Configuration label.
     */
    private final String MENU_FILE_EXPORT_NNCONFIG = "Neural Network Config ..";

    /**
     * Menu File Export Encog label.
     */
    private final String MENU_FILE_EXPORT_ENCOG = "Encog ..";

    /**
     * Menu File Export Report label.
     */
    private final String MENU_FILE_EXPORT_REPORT = "Report ..";

    /**
     * Menu File Preferences label.
     */
    private final String MENU_FILE_PREFERENCES = "Preferences ..";

    /**
     * Menu File Quit label.
     */
    private final String MENU_FILE_QUIT = "Quit";

    /**
     * Menu Neural Network label.
     */
    private final String MENU_NEURAL_NETWORK = "Neural Network";

    /**
     * Menu Neural Network New label.
     */
    private final String MENU_NEURAL_NETWORK_NEW = "New Config ..";

    /**
     * Menu Neural Network Edit label.
     */
    private final String MENU_NEURAL_NETWORK_EDIT = "Edit Config ..";

    /**
     * Menu Neural Network Close label.
     */
    private final String MENU_NEURAL_NETWORK_CLOSE = "Close Config ..";

    /**
     * Menu Run label.
     */
    private final String MENU_RUN = "Run";

    /**
     * Menu Run Training label.
     */
    private final String MENU_RUN_TRAINING = "Training ..";

    /**
     * Menu Run Test label.
     */
    private final String MENU_RUN_TEST = "Test";

    /**
     * Menu Run Reset Training label.
     */
    private final String MENU_RUN_RESET_TRAINING = "Reset Training";

    /**
     * Menu Window label.
     */
    private final String MENU_WINDOW = "Window";

    /**
     * Menu Window View label.
     */
    private final String MENU_WINDOW_VIEW = "View";

    /**
     * Menu Window Show Progress label.
     */
    private final String MENU_WINDOW_SHOW_PROGRESS = "Show Progress";

    /**
     * Menu Window Show Training Report label.
     */
    private final String MENU_WINDOW_SHOW_TRAINING_REPORT = "Show Training Report";

    /**
     * Menu Window Show Testing Report label.
     */
    private final String MENU_WINDOW_SHOW_TESTING_REPORT = "Show Testing Report";

    /**
     * Menu Window Show Player label.
     */
    private final String MENU_WINDOW_SHOW_PLAYER = "Show Player";

    /**
     * Menu Window Show Plot label.
     */
    private final String MENU_WINDOW_SHOW_PLOT = "Show Plot";

    /**
     * Menu Window Show Log label.
     */
    private final String MENU_WINDOW_SHOW_LOG = "Show Log";

    /**
     * Menu Help label.
     */
    private final String MENU_HELP = "Help";

    /**
     * Menu Help Contents label.
     */
    private final String MENU_HELP_CONTENTS = "Help Contents ..";

    /**
     * Menu Help About label.
     */
    private final String MENU_HELP_ABOUT = "About ..";

    /**
     * The main frame.
     */
    private static JFrame frame;
    private JDesktopPane desktopPane;
    private JMenuBar menuBar;
    private JMenu mnFile;
    private JMenuItem mntmFileNewProject;
    private JMenuItem mntmFileEditProject;
    private JMenuItem mntmFileLoadProject;
    private JMenuItem mntmFileSaveProject;
    private JMenuItem mntmFileCloseProject;
    private JMenu mnFileImport;
    private JMenuItem mntmFileImportNNConfig;
    private JMenu mnFileExport;
    private JMenuItem mntmFileExportNeuralNetworkConfig;
    private JMenuItem mntmFileExportEncog;
    private JMenuItem mntmFileExportReport;
    private JMenuItem mntmFileQuit;
    private JMenu mnNNConfig;
    private JMenuItem mntmNNNewConfig;
    private JMenuItem mntmNNEditConfig;
    private JMenuItem mntmNNCloseConfig;
    private JMenu mnRun;
    private JMenuItem mntmRunTraining;
    private JMenuItem mntmRunTesting;
    private JMenuItem mntmRunReset;
    private JMenu mnWindow;
    private JMenuItem mntmWindowShowProgress;
    private JMenuItem mntmFilePreferences;
    private JMenuItem mntmWindowShowTestingReport;
    private JMenuItem mntmWindowShowPlot;
    private JMenuItem mntmWindowShowLog;
    private JMenu mnHelp;
    private JMenuItem mntmHelpHelpContents;
    private JMenuItem mntmHelpAbout;
    private JMenuItem mntmWindowShowPlayer;
    private JMenuItem mntmWindowShowTrainingReport;
    private JMenu mnWindowView;
    
    // Project specific global variables
    JProgressBar progressBar;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    // TODO Move this to Guice.
                    frame = new NeuralNetsToolkit(new ControllerImpl());
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Initialise it all.
     */
    public NeuralNetsToolkit(Controller controller) {
        this.mainController = controller;

        // Get all JVariables instantiated.
        instantiate();

        // Setup each menu visually
        initMenuFile();
        initMenuNeuralNetwork();
        initMenuRun();
        initMenuWindow();
        initMenuHelp();

        // Add shortcuts to menu items
        initKeyStrokeAccelerators();
        
        // Add listeners.
        addListeners();
        
        // Add all to content pane.
        initAddToContentPane();

    }

    private void initAddToContentPane() {
        getContentPane().add(desktopPane);
        getContentPane().add(menuBar, BorderLayout.NORTH);
    }

    /**
     * Instantiating all variables.
     */
    private void instantiate() {
    	// Find the screen size and centre the application in it.
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width / 2) - ( APPLICATION_WIDTH / 2 ), 
        		(screenSize.height / 2) - ( APPLICATION_HEIGHT / 2 ), 
        		APPLICATION_WIDTH, APPLICATION_HEIGHT);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Desktop Pane
        desktopPane = new JDesktopPane();
        desktopPane.setBorder(null);
        desktopPane.setBackground(Color.BLACK);
        desktopPane.setLayout(null);

        // **** MENU BAR ****
        menuBar = new JMenuBar();

        // The File menu
        mnFile = new JMenu(MENU_FILE);
        mntmFileNewProject = new JMenuItem(MENU_FILE_NEW);
        mntmFileEditProject = new JMenuItem(MENU_FILE_EDIT);
        mntmFileLoadProject = new JMenuItem(MENU_FILE_LOAD);
        mntmFileSaveProject = new JMenuItem(MENU_FILE_SAVE);
        mntmFileCloseProject = new JMenuItem(MENU_FILE_CLOSE);
        mnFileImport = new JMenu(MENU_FILE_IMPORT);
        mntmFileImportNNConfig = new JMenuItem(MENU_FILE_IMPORT_NNCONFIG);
        mnFileExport = new JMenu(MENU_FILE_EXPORT);
        mntmFileExportNeuralNetworkConfig = new JMenuItem(MENU_FILE_EXPORT_NNCONFIG);
        mntmFileExportEncog = new JMenuItem(MENU_FILE_EXPORT_ENCOG);
        mntmFileExportReport = new JMenuItem(MENU_FILE_EXPORT_REPORT);
        mntmFilePreferences = new JMenuItem(MENU_FILE_PREFERENCES);
        mntmFileQuit = new JMenuItem(MENU_FILE_QUIT);
        
        // The Neural Network menu
        mnNNConfig = new JMenu(MENU_NEURAL_NETWORK);
        mntmNNNewConfig = new JMenuItem(MENU_NEURAL_NETWORK_NEW);
        mntmNNEditConfig = new JMenuItem(MENU_NEURAL_NETWORK_EDIT);
        mntmNNCloseConfig = new JMenuItem(MENU_NEURAL_NETWORK_CLOSE);
        
        // The Run menu
        mnRun = new JMenu(MENU_RUN);
        mntmRunTraining = new JMenuItem(MENU_RUN_TRAINING);
        mntmRunTesting = new JMenuItem(MENU_RUN_TEST);
        mntmRunReset = new JMenuItem(MENU_RUN_RESET_TRAINING);

        // The Window menu
        mnWindow = new JMenu(MENU_WINDOW);
        mnWindowView = new JMenu(MENU_WINDOW_VIEW);
        
        mntmWindowShowProgress = new JMenuItem(MENU_WINDOW_SHOW_PROGRESS);
        mntmWindowShowTrainingReport = new JMenuItem(MENU_WINDOW_SHOW_TRAINING_REPORT);
        mntmWindowShowTestingReport = new JMenuItem(MENU_WINDOW_SHOW_TESTING_REPORT);
        mntmWindowShowPlayer = new JMenuItem(MENU_WINDOW_SHOW_PLAYER);
        mntmWindowShowPlot = new JMenuItem(MENU_WINDOW_SHOW_PLOT);
        mntmWindowShowLog = new JMenuItem(MENU_WINDOW_SHOW_LOG);

        // The Help menu
        mnHelp = new JMenu(MENU_HELP);
        mntmHelpHelpContents = new JMenuItem(MENU_HELP_CONTENTS);
        mntmHelpAbout = new JMenuItem(MENU_HELP_ABOUT);
    }

    /**
     * The File menu visual.
     */
    private void initMenuFile() {
        mnFile.setMnemonic('F');
        menuBar.add(mnFile);
        mnFile.add(mntmFileNewProject);
        mnFile.add(new JSeparator());
        mnFile.add(mntmFileEditProject);
        mnFile.add(mntmFileLoadProject);
        mnFile.add(mntmFileSaveProject);
        mnFile.add(mntmFileCloseProject);
        mnFile.add(new JSeparator());
        mnFileImport.setMnemonic('I');
        mnFile.add(mnFileImport);
        mnFileImport.add(mntmFileImportNNConfig);
        mnFile.add(new JSeparator());
        mnFileExport.setMnemonic('E');
        mnFile.add(mnFileExport);
        mnFileExport.add(mntmFileExportNeuralNetworkConfig);
        mnFileExport.add(mntmFileExportEncog);
        mnFileExport.add(mntmFileExportReport);
        mnFile.add(new JSeparator());
        mnFile.add(mntmFilePreferences);
        mnFile.add(new JSeparator());
        mnFile.add(mntmFileQuit);
    }
    
    /**
     * The Neural Network menu visual.
     */
    private void initMenuNeuralNetwork() {
        mnNNConfig.setMnemonic('K');
        menuBar.add(mnNNConfig);
        mnNNConfig.add(mntmNNNewConfig);
        mnNNConfig.add(new JSeparator());
        mnNNConfig.add(mntmNNEditConfig);
        mnNNConfig.add(new JSeparator());
        mnNNConfig.add(mntmNNCloseConfig);
    }
    
    /**
     * The Run menu visual.
     */
    private void initMenuRun() {
        mnRun.setMnemonic('R');
        menuBar.add(mnRun);
        mnRun.add(mntmRunTraining);
        mnRun.add(new JSeparator());
        mnRun.add(mntmRunTesting);
        mnRun.add(new JSeparator());
        mnRun.add(mntmRunReset);
    }

    /**
     * The Window menu visual.
     */
    private void initMenuWindow() {
        mnWindow.setMnemonic('V');
        menuBar.add(mnWindow);
        mnWindow.add(mnWindowView);
        mnWindow.add(new JSeparator());
        mnWindow.add(mntmWindowShowTrainingReport);
        mnWindow.add(mntmWindowShowTestingReport);
        mnWindow.add(new JSeparator());
        mnWindow.add(mntmWindowShowPlayer);
        mnWindow.add(mntmWindowShowPlot);
        mnWindow.add(mntmWindowShowProgress);
        mnWindow.add(mntmWindowShowLog);
    }
    
    /**
     * The Help menu visual.
     */
    private void initMenuHelp() {
        mnHelp.setMnemonic('H');
        menuBar.add(mnHelp);
        mnHelp.add(mntmHelpHelpContents);
        mnHelp.add(mntmHelpAbout);
    }
    
    /**
     * Accelerator keys.
     */
    private void initKeyStrokeAccelerators() {
    	// File menu
        mntmFileNewProject.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.ALT_MASK));
        mntmFileEditProject.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.ALT_MASK));
        mntmFileLoadProject.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.ALT_MASK));
        mntmFileSaveProject.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.ALT_MASK));
        mntmFileCloseProject.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.ALT_MASK));
        mntmFilePreferences.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK | InputEvent.ALT_MASK | InputEvent.SHIFT_MASK));
        mntmFileQuit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.ALT_MASK));

        // Neural Network menu
        mntmNNNewConfig.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.ALT_MASK | InputEvent.SHIFT_MASK));
        mntmNNEditConfig.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.ALT_MASK | InputEvent.SHIFT_MASK));
        mntmNNCloseConfig.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.ALT_MASK | InputEvent.SHIFT_MASK));
        
        // Run menu
        mntmRunTraining.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.ALT_MASK | InputEvent.SHIFT_MASK));
        mntmRunTesting.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.ALT_MASK | InputEvent.SHIFT_MASK));
        mntmRunReset.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.ALT_MASK | InputEvent.SHIFT_MASK));
        
        // Window menu
        mntmWindowShowProgress.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.SHIFT_MASK));
        mntmWindowShowTrainingReport.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.SHIFT_MASK));
        mntmWindowShowTestingReport.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.SHIFT_MASK));
        mntmWindowShowPlayer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.SHIFT_MASK));
        mntmWindowShowPlot.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.SHIFT_MASK));
        mntmWindowShowLog.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, InputEvent.SHIFT_MASK));
    }
    

    /*************************************************************************
     *                                                                       *
     *                      L  I  S  T  E  N  E  R  S                        *
     *                                                                       *
     *************************************************************************/

    /**
     * Add all required listeners.
     */
    private void addListeners() {
    	// Menu listeners triggering first calls.
    	addFileMenuListeners();
    	addNeuralNetworkConfigListeners();
    	addRunListeners();
    	addWindowListeners();
    	addHelpListeners();
    	
    	// Other listeners not part of the menu
    	refreshInternalFrameListeners();
    	refreshWindowViewMenuAndListeners();
    }
    
    /**
     * All File menu listeners.
     */
    private void addFileMenuListeners() {
    	// File -> New Project
        mntmFileNewProject.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                fileNewProjectAction(event);
            }
        });
        
        // File -> Edit Project
        mntmFileEditProject.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent event) {
        		fileEditProjectAction(event);
        	}
        });

        // File -> Load Project
        mntmFileLoadProject.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent event) {
        		fileLoadProjectAction(event);
        	}
        });

        // File -> Save Project
        mntmFileSaveProject.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent event) {
        		fileSaveProjectAction(event);
        	}
        });

        // File -> Close Project
        mntmFileCloseProject.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent event) {
        		fileCloseProjectAction(event);
        	}
        });

        // File -> Import -> Neural Network Configuration
        mntmFileImportNNConfig.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent event) {
        		fileImportNNConfigAction(event);
        	}
        });

        // File -> Export -> Neural Network Configuration
        mntmFileExportNeuralNetworkConfig.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent event) {
        		fileExportNNConfigAction(event);
        	}
        });

        // File -> Export -> Encog
        mntmFileExportEncog.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent event) {
        		fileExportEncogAction(event);
        	}
        });

        // File -> Export -> Report
        mntmFileExportReport.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent event) {
        		fileExportReportAction(event);
        	}
        });

        // File -> Preferences
        mntmFilePreferences.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent event) {
        		filePreferencesAction(event);
        	}
        });

        // File -> Quit
        mntmFileQuit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
            	fileQuitAction(event);
            }
        });
    }

    /**
     * All Neural Network Configuration menu listeners.
     */
    private void addNeuralNetworkConfigListeners() {
    	// Neural Network -> New Config
        mntmNNNewConfig.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent event) {
        		neuralNetworkNewConfigAction(event);
        	}
        });

    	// Neural Network -> Edit Config
        mntmNNEditConfig.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent event) {
        		neuralNetworkEditConfigAction(event);
        	}
        });

    	// Neural Network -> Close Config
        mntmNNCloseConfig.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent event) {
        		neuraNetworkCloseConfigAction(event);
        	}
        });
    }

    /**
     * Add Run menu listeners.
     */
    private void addRunListeners() {
    	// Run -> Training
        mntmRunTraining.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent event) {
        		runTrainingAction(event);
        	}
        });

    	// Run -> Testing
        mntmRunTesting.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent event) {
        		runTestingAction(event);
        	}
        });

    	// Run -> Reset
        mntmRunReset.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent event) {
        		runResetAction(event);
        	}
        });
    }

    /**
     * All Window menu listeners.
     */
    private void addWindowListeners() {
    	// Window -> View -> ??? will be dynamically assigned and de-assigned

    	// Window -> Show Training Report
        mntmWindowShowTrainingReport.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent event) {
        		windowShowTrainingReportAction(event);
        	}
        });

    	// Window -> Show Testing Report
        mntmWindowShowTestingReport.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent event) {
        		windowShowTestingReportAction(event);
        	}
        });
	
    	// Window -> Show Log
        mntmWindowShowLog.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent event) {
        		windowShowLogAction(event);
        	}
        });
	
    	// Window -> Show Player
        mntmWindowShowPlayer.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent event) {
        		windowShowPlayerAction(event);
        	}
        });
	
    	// Window -> Show Plot
        mntmWindowShowPlot.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent event) {
        		windowShowPlotAction(event);
        	}
        });
	
    	// Window -> Show Progress
        mntmWindowShowProgress.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent event) {
        		windowShowProgressAction(event);
        	}
        });
    }

    /**
     * All Help menu listeners.
     */
    private void addHelpListeners() {
    	// Help -> Help Contents
        mntmHelpHelpContents.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent event) {
        		helpHelpContentsAction(event);
        	}
        });

        // Help -> About
        mntmHelpAbout.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent event) {
        		helpAboutAction(event);
        	}
        });
    }

    /**
     * Listener for when user selects a different project internal frame.
     */
    private void refreshInternalFrameListeners() {
    	// For each desktopPane internal frame, add a listener to know it when 
    	// selected, to load all its data required by main application to act 
    	// upon at any user action on it.
        for ( JInternalFrame internalFrame : desktopPane.getAllFrames() ) {
            if ( internalFrame.getVetoableChangeListeners().length > 0 ) continue;
        	// Add the listener for when the user selects this internal frame.
            internalFrame.addVetoableChangeListener(new VetoableChangeListener() {
        		@Override
        		public void vetoableChange(PropertyChangeEvent event) throws PropertyVetoException {
        			if ( event.getNewValue().equals(true)) {
        				internalFrameSelectedAction(internalFrame);
        			}
        		}
        	});
        }
    }

    private void refreshWindowViewMenuAndListeners() {
    	// Window -> View has a list of all the internal frames opened.

    	// First remove all
    	mnWindowView.removeAll();
    	
    	// Now add the ones we actually have
        for ( JInternalFrame internalFrame : desktopPane.getAllFrames() ) {
        	// Create a new sub-menu for Window -> View with this one.
        	JMenuItem menuInternalFrame = new JMenuItem(internalFrame.getName());
        	mnWindowView.add(menuInternalFrame);

        	// Add listener for when the user selects this frame from Window -> View.
            menuInternalFrame.addActionListener(new ActionListener() {
            	public void actionPerformed(ActionEvent event) {
    				try {
						internalFrame.setSelected(true);
	    				internalFrameSelectedAction(internalFrame);
	    				System.out.println("you called me");
					} catch (PropertyVetoException e) {
						e.printStackTrace();
					}
            	}
            });
        }
    }
    
    /*************************************************************************
     *                                                                       *
     *                         A  C  T  I  O  N  S                           *
     *                              File menu                                *
     *                                                                       *
     *************************************************************************/

    /**
     * Creates a new project, calling all New Project class to deal with all 
     * the details.
     * 
     * @param event ActionEvent
     */
    private void fileNewProjectAction(ActionEvent event) {
    	// First we need all the project details from the user so that we can
    	// then create the project with all the given specifications.
    	
//   	 ProjectDetails projectDetails = getUserProjectDetails(this);
        ProjectData projectDetails = new ProjectDetails();
        projectDetails.setProjectName("This is a test");

        // Now create the project with all the required information supplied.
    	JInternalFrame newProjectInternalFrame = new JInternalFrame("Project - "+projectDetails.getProjectName());
        Project newP = new Project(mainController, this);
    	newP.setVisible(true);
//        newProjectInternalFrame.getContentPane().add(newP,BorderLayout.NORTH);
        newProjectInternalFrame.setName("Project - "+projectDetails.getProjectName());
        newProjectInternalFrame.getContentPane().setBackground(Color.BLACK);
        newProjectInternalFrame.setClosable(true);
        newProjectInternalFrame.setMaximizable(true);
        newProjectInternalFrame.setIconifiable(true);
        newProjectInternalFrame.setEnabled(false);
        newProjectInternalFrame.setResizable(true);
        newProjectInternalFrame.setBounds(0, 0, 250, 150);
//        newProjectInternalFrame.getContentPane().setLayout(new BorderLayout(4,4));
        newProjectInternalFrame.setVisible(true);
        desktopPane.add(newProjectInternalFrame);
        
        progressBar = new JProgressBar();
        progressBar.setStringPainted(true);
        progressBar.setBorderPainted(false);
        progressBar.setForeground(Color.BLUE);
        progressBar.setOpaque(false);
        progressBar.setBorder(null);
        progressBar.setString("Processing Training Epoch 1 / 50");
        progressBar.setToolTipText("");
        progressBar.setValue(50);
        progressBar.setVisible(true);
        newProjectInternalFrame.add(progressBar, BorderLayout.SOUTH);
//        newProjectInternalFrame.setLayout(new BorderLayout(0, 0));
//          
////        desktopPane.setLayer(progressBar, 200);
//
////          newProject = new JInternalFrame("Project");
//////        newProject.getContentPane().setBackground(Color.BLACK);
////          newProject.setName("New Project");
////          newProject.setClosable(true);
////          newProject.setMaximizable(true);
////          newProject.setIconifiable(true);
////          newProject.setEnabled(false);
////          newProject.setResizable(true);
////          newProject.setBounds(0, 0, 1184, 717);
////          desktopPane.add(newProject);
////          newProject.getContentPane().setLayout(null);
////        
//
//          JInternalFrame plot = new JInternalFrame("Plot");
//          plot.setBounds(270, 12, 904, 610);
//          newProjectInternalFrame.add(plot);
////          desktopPane.setLayer(plot, 50);
//          plot.setOpaque(false);
//          plot.setForeground(Color.WHITE);
//          plot.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
//          plot.setBackground(Color.BLACK);
//          plot.setResizable(true);
//          plot.setClosable(true);
////          
////          player = new JInternalFrame("Player");
////          player.setBounds(26, 238, 244, 72);
////          newProject.getContentPane().add(player);
////          desktopPane.setLayer(player, 100);
////          player.setClosable(true);
////          player.setBackground(Color.BLACK);
////          
////          logs = new JInternalFrame("Logs");
////          logs.setBounds(47, 541, 904, 72);
////          newProject.getContentPane().add(logs);
////          logs.setResizable(true);
////          logs.setIconifiable(true);
////          logs.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
////          logs.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
////          logs.setClosable(true);
////          logs.setAutoscrolls(true);
////          desktopPane.setLayer(logs, 101);
////          logs.setForeground(Color.WHITE);
////          logs.setBackground(Color.BLACK);
////          logs.setVisible(true);
////          player.setVisible(true);
//          plot.setVisible(true);
////          newProject.setVisible(true);
//          progressBar.setVisible(false);
//        
//        
//        
//        
//        
//        
        
        
        
        
        System.out.println("File new project pressed");
        
        // Refresh all internal frame listeners to keep them in synch with changes.
    	refreshInternalFrameListeners();
    	refreshWindowViewMenuAndListeners();
    }

    /**
     * Edits info on an already existing project.
     * 
     * @param event ActionEvent
     */
    private void fileEditProjectAction(ActionEvent event) {
    	System.out.println("File edit project pressed");
    }

    /**
     * Loads a new project.
     * 
     * @param event ActionEvent
     */
    private void fileLoadProjectAction(ActionEvent event) {
    	System.out.println("File load project pressed");
    }

    /**
     * Saves current project.
     * 
     * @param event ActionEvent
     */
    private void fileSaveProjectAction(ActionEvent event) {
    	System.out.println("File save project pressed");
    }

    /**
     * Closes current project.
     * 
     * @param event ActionEvent
     */
    private void fileCloseProjectAction(ActionEvent event) {
    	System.out.println("File close project pressed");
    }

    /**
     * Imports a new Neural Network configuration.
     * 
     * @param event ActionEvent
     */
    private void fileImportNNConfigAction(ActionEvent event) {
    	System.out.println("File Import NN Config pressed");
    }

    /**
     * Exports current Neural Network Configuration.
     * 
     * @param event ActionEvent
     */
    private void fileExportNNConfigAction(ActionEvent event) {
    	System.out.println("File Export NN Config pressed");
    }

    /**
     * Exports current Neural Network Encog compatible.
     * 
     * @param event ActionEvent.
     */
    private void fileExportEncogAction(ActionEvent event) {
    	System.out.println("File Export Encog pressed");
    }

    /**
     * Export Report.
     * 
     * @param event ActionEvent
     */
    private void fileExportReportAction(ActionEvent event) {
    	System.out.println("File Export Report pressed");
    }

    /**
     * Change application preferences.
     * 
     * @param event ActionEvent
     */
    private void filePreferencesAction(ActionEvent event) {
    	System.out.println("File Preferences pressed");
    }

    /**
     * User wants to quit the application.
     * 
     * @param event ActionEvent
     */
    private void fileQuitAction(ActionEvent event) {
        // First ask controller if anything needs saving...
        if ( mainController.isChanged() ) {

            // Ask user what to do.
            int answer = Message.createWarningMessageThreeOptionalYNC(frame, "Discard unsaved changes?");

            // A cancel was probably a mistake. Keep calm and carry on...
            if ( answer == JOptionPane.CANCEL_OPTION ) return;

            // Let's quit the application but first save if required...
            if ( answer == JOptionPane.YES_OPTION ) mainController.saveAll();
        }

        // All good.. and good night.
        frame.dispose();
    }

    /*************************************************************************
     *                                                                       *
     *                         A  C  T  I  O  N  S                           *
     *                  Neural Network Configuration menu                    *
     *                                                                       *
     *************************************************************************/

    /**
     * Creates a new Neural Network Configuration.
     * 
     * @param event ActionEvent
     */
    private void neuralNetworkNewConfigAction(ActionEvent event) {
    	System.out.println("Neural Network New config pressed.");
    }
    
    /**
     * Edits current Neural Network Configuration.
     * 
     * @param event ActionEvent
     */
    private void neuralNetworkEditConfigAction(ActionEvent event) {
    	System.out.println("Neural Network Edit config pressed.");
    }

    /**
     * Closes currently open Neural Network configuration.
     * 
     * @param event ActionEvent
     */
    private void neuraNetworkCloseConfigAction(ActionEvent event) {
    	System.out.println("Neural Network Close config pressed.");
    }


    /*************************************************************************
     *                                                                       *
     *                         A  C  T  I  O  N  S                           *
     *                              Run menu                                 *
     *                                                                       *
     *************************************************************************/

    /**
     * Triggers the run training action.
     * 
     * @param event ActionEvent
     */
    private void runTrainingAction(ActionEvent event) {
    	System.out.println("Run Training pressed.");
    }
    
    /**
     * Triggers the testing action.
     * 
     * @param event ActionEvent
     */
    private void runTestingAction(ActionEvent event) {
    	System.out.println("Run Testing pressed.");
    }
    
    /**
     * Resets Neural Network training done so far.
     * 
     * @param event ActionEvent
     */
    private void runResetAction(ActionEvent event) {
    	System.out.println("Run Reset pressed.");
    }

    
    /*************************************************************************
     *                                                                       *
     *                         A  C  T  I  O  N  S                           *
     *                             Window menu                               *
     *                                                                       *
     *************************************************************************/


    /**
     * Show / hide the training report.
     * 
     * @param event ActionEvent
     */
    private void windowShowTrainingReportAction(ActionEvent event) {
    	System.out.println("Window show training report pressed.");

    }
    
    /**
     * Show / hide the testing report.
     * 
     * @param event ActionEvent
     */
    private void windowShowTestingReportAction(ActionEvent event) {
    	System.out.println("Window show testing report pressed.");
    }

    /**
     * Show / hide the log.
     * 
     * @param event ActionEvent
     */
    private void windowShowLogAction(ActionEvent event) {
    	System.out.println("Window show log pressed.");
    }

    /**
     * Show / hide the player.
     * 
     * @param event ActionEvent
     */
    private void windowShowPlayerAction(ActionEvent event) {
    	System.out.println("Window show player pressed.");
    }
    
    /**
     * Show / hide the plot.
     * 
     * @param event ActionEvent
     */
    private void windowShowPlotAction(ActionEvent event) {
    	System.out.println("Window show plot pressed.");
    }

    /**
     * User wants to toggle the process to show or hide.
     * 
     * @param event ActionEvent
     */
    private void windowShowProgressAction(ActionEvent event) {
    	System.out.println("Window show progress pressed.");
    	if ( event.getActionCommand().equals("Show Progress")) {
            progressBar.setVisible(true);
            mntmWindowShowProgress.setText("Hide Progress");
        }
        else {
            progressBar.setVisible(false);
            mntmWindowShowProgress.setText("Show Progress");
        }
    }

    
    /*************************************************************************
     *                                                                       *
     *                         A  C  T  I  O  N  S                           *
     *                              Help menu                                *
     *                                                                       *
     *************************************************************************/

    /**
     * Show a summary of the application contents to help user with task.
     * 
     * @param event ActionEvent
     */
    private void helpHelpContentsAction(ActionEvent event) {
    	System.out.println("Helo Contents pressed.");
    }

    /**
     * Show user credits and references for the present built application.
     * 
     * @param event ActionEvent
     */
	private void helpAboutAction(ActionEvent event) {
		System.out.println("Help About pressed.");
	}

    
    /*************************************************************************
     *                                                                       *
     *                         A  C  T  I  O  N  S                           *
     *              A different internal frame was selected                  *
     *                                                                       *
     *************************************************************************/

	/**
	 * When user selects a different project internal frame, this is triggered.
	 * All data is then inherited and loaded against the current variables on
	 * the main application to then be acted upon on user requests.
	 * 
	 * @param internalFrameSelected JInternalFrame
	 */
    private void internalFrameSelectedAction(JInternalFrame internalFrameSelected) {
    	// TODO: grab all internal variables from this internal frame and load against the main application
    	System.out.println("I was selected: "+internalFrameSelected.getName());
    	for( Component c : internalFrameSelected.getComponents() ) {
    		System.out.println("I have component: "+c.getName());
    	}
    }
}
