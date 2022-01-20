package patientApp.patient.record.view
import patientApp.patient.record.MainApp
import scalafx.scene.control.{Alert, ButtonType}
import scalafxml.core.macros.sfxml
import scalafx.event.ActionEvent
import scalafx.stage.FileChooser


@sfxml
class RootController(){
    
    // deleteAll() is a function that reset the table completely by removing all records
    def deleteAll() {
        
        // If there are no records, alert the user
        if (MainApp.patientData.isEmpty) {
            new Alert(Alert.AlertType.Information, "No Patient Records Found!").showAndWait()
        } 
        
        // else just ask for confirmation to proceed to reset
        else {
            val alert : Alert = new Alert(Alert.AlertType.Confirmation);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Are you sure you want to clear all the patient records?");
            

            val result = alert.showAndWait();
            
            if (result.get == ButtonType.OK){
                MainApp.patientData.clear()
                MainApp.showPatientOverview()
            }
        }
    }
    
    //The saveItem function displays the save dialogue when we click on the save under file in the menu bar.
    
    def saveItem(action : ActionEvent) = {

        val fileChooser = new FileChooser
        val selectedFile = fileChooser.showSaveDialog(MainApp.stage)
    }


    //give an explanation of the +Care
    def handleAbout() {
            new Alert(Alert.AlertType.Information, "+Care is an app for keeping medical records! \n\n  You can keep records of the details of consultations with your patients. \n\n Stay in track with +Care!").showAndWait()
    }
}