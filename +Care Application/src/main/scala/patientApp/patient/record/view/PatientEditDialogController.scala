package patientApp.patient.record.view
import patientApp.patient.record.model.Patient
import patientApp.patient.record.MainApp
import scalafx.scene.control.{TextField, TableColumn, Label, Alert, TextArea, DatePicker,ChoiceBox}
import scalafx.scene.control.Alert.AlertType
import scalafxml.core.macros.sfxml
import scalafx.stage.Stage
import scalafx.Includes._
import java.io.File
import scalafx.collections.ObservableBuffer
import scalafx.scene.media.{Media, MediaPlayer}
import patientApp.patient.record.util.DateUtil._
import scalafx.event.ActionEvent

@sfxml
class PatientEditDialogController (
  
    private val firstNameField : TextField,    
    private val lastNameField : TextField,
    private val addressField : TextField,
    private val ageField : TextField,
    private val genderField : ChoiceBox[String],
    private val phoneNumField :  TextField,
    private val  emailField : TextField,

    private val  bloodGroupField : ChoiceBox[String],
    private val  medicalConditionField : TextField,
    private val  underMedField : TextField,

    private val  currentAptField : TextField,
    private val nextAptField: DatePicker, 

    private val  feeField : TextField,
    private val  paidAmtField : TextField,
    private val  balanceField : TextField,

    

    private val  consultationOverField : TextArea
     


){

  //set up observableBuffer to display the choices of gender in the choice box
  val genderChoice = new ObservableBuffer[String]()
  genderChoice.append("Male")
  genderChoice.append("Female")
  genderField.items = genderChoice

  //set up observableBuffer to display the choices of blood group in the choice box
  val bloodChoice = new ObservableBuffer[String]()
  bloodChoice.append("A+")
  bloodChoice.append("A-")
  bloodChoice.append("B+")
  bloodChoice.append("B-")
  bloodChoice.append("O+")
  bloodChoice.append("O-")
  bloodChoice.append("AB+")
  bloodChoice.append("AB-")
  
  bloodGroupField.items = bloodChoice


  // Display selected patient details in dialog
  var         dialogStage  : Stage  = null 
  private var _patient     : Patient = null 
  var         okClicked            = false 

  def patient = _patient

  def patient_=(x : Patient) {

        _patient = x

        firstNameField.text         = _patient.firstName.value
        lastNameField.text          = _patient.lastName.value
        addressField.text           = _patient.address.value
        ageField.text               = _patient.age.value.toString
        phoneNumField.text          = _patient.phoneNum.value.toString
        emailField.text             = _patient.email.value
        genderField.value            = _patient.gender.value

        bloodGroupField.value        = _patient.bloodGroup.value
        medicalConditionField.text  = _patient.medicalCon.value
        underMedField.text          = _patient.underMed.value

        currentAptField.text        = _patient.currentAptDAte.value.asString
        currentAptField.setPromptText("dd.mm.yyyy");

        nextAptField.value           = _patient.nextAppointment.value
        nextAptField.setPromptText("dd.mm.yyyy");

        feeField.text               = _patient.fee.value.toString
        paidAmtField.text           = _patient.paidAmt.value.toString
        balanceField.text           = _patient.balance.value.toString


        consultationOverField.text  = _patient.consultationOver.value

  }
  val file2 = new File("src/main/resources/patientApp/sound/error.mp3")
	val media2= new Media(source = file2.toURI().toString) 
	val player2= new MediaPlayer(media = media2)


  var sound: List[MediaPlayer] = List(player2)

  // Called when Ok clicked in dialog
  def handleOk(action :ActionEvent){
    
    //If input information of the patient are valid, update the patient details.
     if (isInputValid()) {

      _patient.firstName.value        = firstNameField.text.value
      _patient.lastName.value         = lastNameField.text.value
      _patient.address.value          = addressField.text.value
      _patient.age.value              = ageField.text().toInt
      _patient.phoneNum.value         = phoneNumField.text().toInt
      _patient.email.value            = emailField.text.value
      _patient.gender.value           = genderField.getValue 
      _patient.bloodGroup.value       = bloodGroupField.getValue
      _patient.medicalCon.value       = medicalConditionField.text.value
      _patient.underMed.value         = underMedField.text.value
      _patient.currentAptDAte.value   = currentAptField.text.value.parseLocalDate
      _patient.nextAppointment.value  = nextAptField.getValue
      _patient.fee.value              = feeField.text().toInt
      _patient.balance.value          = balanceField.text().toInt
      _patient.paidAmt.value          = paidAmtField.text().toInt
      _patient.consultationOver.value = consultationOverField.text.value


        okClicked = true;
        dialogStage.close()
    }
  }

  //When clicked on cancel button, there are no changes 
  def handleCancel(action :ActionEvent) {
        dialogStage.close();
  }
  def nullChecking (x : String) = x == null || x.length == 0
// below is the fuction to check if the field imput are in the correct format
// if the function return true, the patient will be added or updated in the patientData
  def isInputValid() : Boolean = {
    var errorMessage = ""

    if (nullChecking(firstNameField.text.value))
     
      errorMessage += "No valid first name!\n"

    if (nullChecking(lastNameField.text.value))
   
      errorMessage += "No valid last name!\n"

    if (nullChecking(addressField.text.value))

      errorMessage += "No valid address!\n"

    if (nullChecking(ageField.text.value)) 

      errorMessage += "No valid age!\n"

    else {
      try {
        Integer.parseInt(ageField.getText());
      } catch {
          case e : NumberFormatException =>
            errorMessage += "No valid age (must be an integer)!\n"
      }
    }

    if (nullChecking(phoneNumField.text.value)){
    
      errorMessage += "No valid phone number!\n"}

    else {
      try {
        Integer.parseInt(phoneNumField.getText());
      } catch {
          case e : NumberFormatException =>
            errorMessage += "No valid phone number (must be an integer)!\n"
      }
    }

    if (nullChecking(emailField.text.value))
    
      errorMessage += "No valid email!\n"

    if (nullChecking(genderField.getValue))
        
        errorMessage += "Fill gender!\n"

    if (nullChecking(bloodGroupField.getValue))
      
      errorMessage += "Fill the blood group!\n"
    
    if (nullChecking(medicalConditionField.text.value))
    
      errorMessage += "Write the medical conditions in the correct format(text)!\n"

    if (nullChecking(underMedField.text.value))

      errorMessage += "Write the under medication response in the correct format(text)!!\n"

    if (nullChecking(currentAptField.text.value))
      errorMessage += "No valid appointment date! Use the format mm/dd/yyyy!\n"

    else {
      if (!currentAptField.text.value.isValid) {
          errorMessage += "No valid appointment date! Use the format mm/dd/yyyy!\n";
      }

    }

   if (nullChecking(feeField.text.value))
      errorMessage += "Write the fee amount properly!\n"
    else {
      try {
        Integer.parseInt(feeField.getText());
      } catch {
          case e : NumberFormatException =>
            errorMessage += "Write the fee amount properly (must be an integer)!\n"
      }
    }

   if (nullChecking(paidAmtField.text.value))
      errorMessage += "Write the paid amount properly!\n"
    else {
      try {
        Integer.parseInt(paidAmtField.getText());
      } catch {
          case e : NumberFormatException =>
            errorMessage += "Write the paid amount properly (must be an integer)!\n"
      }
    }

   if (nullChecking(balanceField.text.value))
      errorMessage += "Write the balance amount properly!\n"
    else {
      try {
        Integer.parseInt(balanceField.getText());
      } catch {
          case e : NumberFormatException =>
            errorMessage += "Write the balance properly (must be an integer)!\n"
      }
    }

   if (nullChecking(consultationOverField.text.value))
      errorMessage += "Write the consultation overview in the correct format(text)!\n"
    

     if (errorMessage.length() == 0) {
        return true;
    } else {
        sound(0).stop
        sound(0).play
        // Show the error message.
        val alert = new Alert(Alert.AlertType.Error){
          initOwner(dialogStage)
          title = "Invalid Fields"
          headerText = "Please correct invalid fields"
          contentText = errorMessage
        }.showAndWait()

        return false;
    }
   }
} 
