package patientApp.patient.record.model
import scalafx.beans.property.{StringProperty, IntegerProperty, ObjectProperty}
import java.time.LocalDate;
import scalafx.collections.ObservableBuffer

class Patient ( firstNameS : String, lastNameS : String ){
  //Patient details
  var firstName  = new StringProperty(firstNameS)
  var lastName   = new StringProperty(lastNameS) 
  var address     = new StringProperty("some Address")
  var age = ObjectProperty[Int](1234)
  var phoneNum = ObjectProperty[Int](1234)
  var gender = new StringProperty("M or F")
  var email       = new StringProperty("some email")

  //Medical Description

  var bloodGroup = new StringProperty("some blood group")
  var medicalCon = new StringProperty("any medical conditions")
  var underMed = new StringProperty("under any medications?")

  //Appointment 

  var currentAptDAte  = ObjectProperty[LocalDate](LocalDate.now())
  var nextAppointment = ObjectProperty[LocalDate](LocalDate.of(2021, 8, 11))

  //Payment

  var fee = ObjectProperty[Int](1234)
  var paidAmt = ObjectProperty[Int](1234)
  var balance = ObjectProperty[Int](1234)



  //consultation overview 

  var consultationOver = new StringProperty("wrtie consultation overview")

}
