package patientApp.patient.record
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.Includes._
import scalafx.scene.image.Image
import scalafxml.core.{NoDependencyResolver, FXMLView, FXMLLoader}
import scalafx.collections.ObservableBuffer
import patientApp.patient.record.model.Patient
import scalafx.stage.{Stage, Modality}
import patientApp.patient.record.view.PatientEditDialogController



import javafx.{scene => jfxs} 

object MainApp extends JFXApp {
    
  //The data as an observable list of Patient.
   val patientData = new ObservableBuffer[Patient]()
   
  patientData += new Patient("Melvin", "Chong")
  patientData += new Patient("Sam", "Smith")
  patientData += new Patient("Harry", "Mendes")
  patientData += new Patient("Bella", "Zhao")
  patientData += new Patient("Emily", "Payen")
  patientData += new Patient("Anishta", "Ramasamy")
  patientData += new Patient("Edwards", "Swift")
  patientData += new Patient("Gary", "Chen")
  patientData += new Patient("Chris", "Miller")
 


  // Transform path of RootLayout.fxml to URI for resource location
  val rootResource = getClass.getResourceAsStream("view/RootLayout.fxml")

  // Initialize loader object
  val loader = new FXMLLoader(null, NoDependencyResolver)

  // Load root layout from fxml file
  loader.load(rootResource);

  // From FXML: retrieve root component BorderPane
  val roots = loader.getRoot[jfxs.layout.BorderPane]

  // Initialize stage
  stage = new PrimaryStage {
    title = "+Care app"
    icons.add(new Image("patientApp/icons/icon.png"))
    scene = new Scene {
      root = roots
      stylesheets = List(getClass.getResource("view/welcomeTheme.css").toExternalForm())
    }
  }
  
  //the fucntion below is to display the overview of the app 
  def showPatientOverview() = {
    val resource = getClass.getResourceAsStream("view/PatientOverview.fxml")
    val loader = new FXMLLoader(null, NoDependencyResolver)
    loader.load(resource);
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    MainApp.roots.setCenter(roots)
  } 

//the fucntion below is to display the app's welcome page
  def showWelcome() = {
    val resource = getClass.getResourceAsStream("view/Welcome.fxml")
    val loader = new FXMLLoader(null, NoDependencyResolver)
    loader.load(resource);
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    MainApp.roots.setCenter(roots)
  } 
  
//the fucntion below is to display the patient edit dialog
def showPatientEditDialog(patient: Patient): Boolean = {
    val resource = getClass.getResourceAsStream("view/PatientEditDialog.fxml")
    val loader = new FXMLLoader(null, NoDependencyResolver)
    loader.load(resource);
    val roots2  = loader.getRoot[jfxs.Parent]
    val control = loader.getController[PatientEditDialogController#Controller]
    val dialog = new Stage() {
      initModality(Modality.APPLICATION_MODAL)
      initOwner(stage)
      scene = new Scene {
        root = roots2
      }
    }
    control.dialogStage = dialog
    control.patient = patient
    dialog.showAndWait()
    control.okClicked
  } 

 //Display the welcome page 
  showWelcome()
  
}