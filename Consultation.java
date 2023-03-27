import java.time.LocalDateTime;

public class Consultation {

    private int  cost;
    private String notes;
    private int doctorMedicalLicenceNumber;
    private int patientIDConsultation;
    private int consultationNumber;
    private LocalDateTime startDateTime;

    public int getConsultationNumber() {
        return consultationNumber;
    }

    public void setConsultationNumber(int consultationNumber) {
        this.consultationNumber = consultationNumber;
    }

    public int getPatientIDConsultation() {
        return patientIDConsultation;
    }

    public void setPatientIDConsultation(int patientIDConsultation) {
        this.patientIDConsultation = patientIDConsultation;
    }
    private LocalDateTime endDateTime;
    public int getDoctorMedicalLicenceNumber() {
        return doctorMedicalLicenceNumber;
    }
    public void setDoctorMedicalLicenceNumber(int doctorMedicalLicenceNumber) {
        this.doctorMedicalLicenceNumber = doctorMedicalLicenceNumber;
    }
    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }
    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }
    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }
    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }
    public int getCost() {
        return cost;
    }
    public void setCost(int cost) {
        this.cost = cost;
    }
    public String getNotes() {
        return notes;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }
    Consultation(int cost, LocalDateTime startDateTime,LocalDateTime endDateTime){
        setCost(cost);
        setStartDateTime(startDateTime);
        setEndDateTime(endDateTime);
    }
}
