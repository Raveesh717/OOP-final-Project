package patientApp.patient.record.view
import scalafxml.core.macros.sfxml
import patientApp.patient.record.MainApp

@sfxml
class WelcomeController() {
  def handleClick(): Unit = {
     MainApp.showPatientOverview()
  }

}