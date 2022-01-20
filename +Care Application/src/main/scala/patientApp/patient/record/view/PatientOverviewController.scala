package patientApp.patient.record.view
import patientApp.patient.record.model.Patient
import patientApp.patient.record.MainApp
import scalafx.scene.control.{TableView, TableColumn, Label, TextArea, Alert, TextField}
import java.io.File
import scalafxml.core.macros.sfxml
import scalafx.scene.media.{Media, MediaPlayer}
import scalafx.collections.transformation
import scalafx.collections.transformation.{FilteredBuffer, SortedBuffer}
import scalafx.beans.property.{StringProperty}
import scalafx.scene.control.Alert.AlertType
import scalafx.event.ActionEvent
import patientApp.patient.record.util.DateUtil._
import scalafx.Includes._


@sfxml
class PatientOverviewController(
  
    private val patientTable : TableView[Patient],
    private val firstNameColumn : TableColumn[Patient, String],  
    private val lastNameColumn : TableColumn[Patient, String],
    private val firstNameLabel : Label,    
    private val lastNameLabel : Label,
    private val addressLabel : Label,
    private val ageLabel : Label,
    private val genderLabel: Label,
    private val phoneNumLabel :  Label,
    private val  emailLabel : Label,
    private val  bloodGroupLabel : Label,
    private val  medicalConditionLabel : Label,
    private val  underMedLabel : Label,
    private val  currentAptLabel : Label,
    private val  nextAptLabel : Label,
    private val  feeLabel : Label,
    private val  paidAmtLabel : Label,
    private val  balanceLabel : Label,
    private val  consultationOverTextarea : TextArea,
    private val  searchField: TextField,
     
    
    ) {
  // initialize Table View display contents model
  patientTable.items = MainApp.patientData
  
  // initialize columns's cell values
  firstNameColumn.cellValueFactory = (x) => x.value.firstName
  lastNameColumn.cellValueFactory  = {_.value.lastName} 

  private def showPatientDetails (patient : Option[Patient]) = {
    patient match {
      case Some(x) =>
      // Fill the labels with data from the patient object.
      firstNameLabel.text <== x.firstName
      lastNameLabel.text  <== x.lastName
      addressLabel.text    <== x.address
      emailLabel.text      <== x.email
      genderLabel.text = x.gender.value

      bloodGroupLabel.text  = x.bloodGroup.value
      medicalConditionLabel.text      <== x.medicalCon
      underMedLabel.text      <== x.underMed

      feeLabel.text = x.fee.value.toString
      paidAmtLabel.text = x.paidAmt.value.toString
      balanceLabel.text = x.balance.value.toString


      consultationOverTextarea.text      <== x.consultationOver

      ageLabel.text = x.age.value.toString
      phoneNumLabel.text = x.phoneNum.value.toString

      currentAptLabel.text = x.currentAptDAte.value.asString
      nextAptLabel.text = x.nextAppointment.value.asString
      
      case None =>

      firstNameLabel.text.unbind()
      lastNameLabel.text.unbind()
      addressLabel.text.unbind()
      emailLabel.text.unbind()
      medicalConditionLabel.text.unbind()
      underMedLabel.text.unbind()

     

      consultationOverTextarea.text.unbind()
        
     // patient is null, remove all the text.
      firstNameLabel.text           = ""
      lastNameLabel.text            = ""
      addressLabel.text             = ""
      emailLabel.text               = ""
      genderLabel.text              = ""
      bloodGroupLabel.text          = ""
      medicalConditionLabel.text    = ""
      underMedLabel.text            = ""

      feeLabel.text                 = ""
      paidAmtLabel.text             = ""
      balanceLabel.text             = ""

      consultationOverTextarea.text = ""

      ageLabel.text                 = ""
      phoneNumLabel.text            = ""
      currentAptLabel.text          = ""
      nextAptLabel.text             = ""
    }    
  }

showPatientDetails(None)
  patientTable.selectionModel().selectedItem.onChange(
      (_, _, newValue) => showPatientDetails(Some(newValue))
  )

//Below is the implementation of search
//The reference for the search is: https://code.patient.ch/blog/javafx-8-tableview-sorting-filtering/

  val filteredPatient = new FilteredBuffer(MainApp.patientData) //Add the patientData observable buffer in a FilteredBuffer
  searchField.text.onChange ({
    filteredPatient.predicate = (Patient => {
      if ( searchField.text.apply == null || searchField.text.apply.isEmpty()){
        true
      } //if statement is to display all patients whenever the searchField is empty

      //this function is implemented for comparing the patient's firstname and lastname
      //with the applied filter text
      val lowerCaseFilter : String = searchField.text.apply.toLowerCase()

      if (Patient.firstName.value.toLowerCase().indexOf(lowerCaseFilter)!= -1){
        true
      } else if (Patient.lastName.value.toLowerCase().indexOf(lowerCaseFilter)!= -1){
        true
      }
    else 
      false 

    })

  })
  
  val sortedPatient =new SortedBuffer(filteredPatient) //Add filteredPatient FilteredBuffer in a sortedBuffer
  sortedPatient.comparator <== patientTable.comparator // Binding of the sortedPatient with the patientTable
  patientTable.items = sortedPatient //Adding the sortedPatient in the patientTable

//The function below is used to delete a selected patient. 
  def handleDeletePatient(action : ActionEvent) = {

    val selectedIndex = patientTable.selectionModel().selectedIndex.value
    if (selectedIndex >= 0) {
      
      val sourceIndex = sortedPatient.getSourceIndexFor(MainApp.patientData, selectedIndex)
      MainApp.patientData.remove(sourceIndex);

    } 
    else {

      // alert displayed is displayed if no patient is selected to be delected.
        val alert = new Alert(AlertType.Warning){
          sound(1).stop
          sound(1).play

          initOwner(MainApp.stage)
          title       = "No Selection"
          headerText  = "No Patient Selected"
          contentText = "Please select a patient in the table."
        }.showAndWait()
    }
  }

  val file = new File("src/main/resources/patientApp/sound/new.wav")
	val media = new Media(source = file.toURI().toString) 
	val player = new MediaPlayer(media = media)

  val file2 = new File("src/main/resources/patientApp/sound/error.mp3")
	val media2= new Media(source = file2.toURI().toString) 
	val player2= new MediaPlayer(media = media2)


  var sound: List[MediaPlayer] = List(player, player2)


  //The fuction below is used to add new patient into the patientData.
  def handleNewPatient(action : ActionEvent) = {
    val patient = new Patient("","")
    val okClicked = MainApp.showPatientEditDialog(patient);
        if (okClicked) {
            sound(0).stop
				    sound(0).play
            MainApp.patientData += patient
        }
  }
  //The function below is used to edit a selected patient. 
  def handleEditPatient(action : ActionEvent) = {
    val selectedPatient = patientTable.selectionModel().selectedItem.value
    if (selectedPatient != null) {
        val okClicked = MainApp.showPatientEditDialog(selectedPatient)

        if (okClicked) showPatientDetails(Some(selectedPatient))

    } else {
        // alert displayed is displayed if no patient is selected to be edited.

        sound(1).stop
				sound(1).play

        val alert = new Alert(Alert.AlertType.Warning){
          initOwner(MainApp.stage)
          title       = "No Selection"
          headerText  = "No Patient Selected"
          contentText = "Please select a Patient in the table."
        }.showAndWait()
    }


  } 

 
  
}




