//implementing superclass person
public class Person {
    private String firstName;
    private String surname;
    private String dateOfBirth;
    private int mobileNumber;
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public String getDateOfBirth() {
        return dateOfBirth;
    }
    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    public int getMobileNumber() {
        return mobileNumber;
    }
    public void setMobileNumber(int mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
}

//implementing subclass doctor
class Doctor extends Person{
    private int medicalLicenceNumber;
    private String specialisation;
    public int getMedicalLicenceNumber() {
        return medicalLicenceNumber;
    }
    public void setMedicalLicenceNumber(int medicalLicenceNumber) {
        this.medicalLicenceNumber = medicalLicenceNumber;
    }
    public String getSpecialisation() {
        return specialisation;
    }
    public void setSpecialisation(String specialisation) {
        this.specialisation = specialisation;
    }
    Doctor(String firstName,String surname,String dateOfBirth,int mobileNumber,int medicalLicenceNumber,String specialisation){
        setFirstName(firstName);
        setSurname(surname);
        setDateOfBirth(dateOfBirth);
        setMobileNumber(mobileNumber);
        setMedicalLicenceNumber(medicalLicenceNumber);
        setSpecialisation(specialisation);
    }
}

//implementing subclass patient
class Patient extends Person{
    private int patientID;
    public void setPatientID(int patientID){
        this.patientID = patientID;
    }
    Patient(){
        setPatientID(patientID);
    }
}
