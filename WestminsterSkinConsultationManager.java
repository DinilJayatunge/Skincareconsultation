import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;

//implementing interface SkinConsultationManager
interface SkinConsultationManager{

    void addNewDoctor();
    void deleteDoctor();
    void printListOfDoctors();
    void saveInAFile();
    void displayMenu();
}

//implementing class WestminsterSkinConsultationManager
public class WestminsterSkinConsultationManager implements SkinConsultationManager{

    ArrayList<Doctor> DoctorInformation = new ArrayList<>();  //creating an array list to store information of the doctors
    ArrayList<String> DoctorInformationProcess = new ArrayList<>();  //creating an array list to save and read information of the doctors
    ArrayList<Consultation> ConsultationDetails = new ArrayList<>(); //creating an array list to store consultation information in the gui
    @Override
    //menu add a new doctor to the system
    public void addNewDoctor() {
        int DoctorsCount = DoctorInformation.size(); //allowing the centre to allocate maximum of 10 doctors only
        if(DoctorsCount>9){
            System.out.println();
            System.out.println("Error! The maximum number of doctors are added.");
        }
        else{
            int valuePhone;
            int valueLicence;
            int mobileNumber;
            int licenceNumber;
            String mobileNumberString;
            while (true) {
                try {
                    Scanner insert = new Scanner(System.in);
                    //Medical licence number validation
                    while (true) {
                        System.out.print("Enter medical licence number of the doctor: ");
                        licenceNumber = insert.nextInt();
                        valueLicence = checkMedicalLicenceNumber(licenceNumber);
                        if(valueLicence == -1){  //input validation to check if the same medical licence number exists
                            break;
                        }
                        else{
                            System.out.println("Error! A doctor with the same medical licence number exists.");
                        }
                    }
                    //First name validation
                    insert.nextLine();
                    String firstName;
                    while(true){
                        System.out.print("Enter first name of the doctor: ");
                        firstName = insert.nextLine();
                        if (firstName.matches("[a-zA-Z]+")){  //input validation to check first name should only contain alphabets
                            break;
                        }
                        else{
                            System.out.println("Error! First name should only contain alphabets.");
                        }
                    }
                    //Surname validation
                    String surname;
                    while(true){
                        System.out.print("Enter surname of the doctor: ");
                        surname = insert.nextLine();
                        if (surname.matches("[a-zA-Z]+")){  //input validation to check surname should only contain alphabets
                            break;
                        }
                        else{
                            System.out.println("Error! Surname should only contain alphabets.");
                        }
                    }
                    //Date of birth validation
                    String dateOfBirth;
                    while (true) {
                        try {
                            System.out.print("Enter date of birth of the doctor in the format (dd-mm-yyyy): ");
                            SimpleDateFormat days = new SimpleDateFormat("dd-MM-yyyy");
                            dateOfBirth = days.format(days.parse(insert.nextLine())); //input validation to print date of birth in the given format only
                            break;
                        } catch (ParseException e1) {
                            // TODO Auto-generated catch block
                            System.out.println("Error! Date of birth is not in valid format.");
                        }
                    }
                    //Mobile number validation
                    while (true) {
                        System.out.print("Enter mobile number of the doctor: ");
                        mobileNumberString = insert.nextLine();
                        mobileNumber = Integer.parseInt(mobileNumberString);
                        valuePhone = checkPhoneNumber(mobileNumber);
                        if(mobileNumberString.length() != 10){  //input validation to check if the mobile number is valid by consisting 10 digits
                            System.out.println("Error! Mobile number should contain 10 numbers.");
                        }
                        else{
                            if(valuePhone == -1){
                                break;
                            }
                            else{
                                System.out.println(); //input validation to check if the same mobile number exist
                                System.out.println("Error! A doctor with the same mobile number exists.");
                            }
                        }
                    }
                    //Specialisation
                    System.out.print("Enter specialisation of the doctor: ");
                    String specialisation = insert.nextLine();
                    Doctor Details = new Doctor(firstName,surname,dateOfBirth,mobileNumber,licenceNumber,specialisation);
                    DoctorInformation.add(Details);
                    System.out.println("Successfully added the doctor.");
                    break;
                } catch (Exception e) {
                    System.out.println("Error! Invalid data format.");
                }
            }
        }
    }

    @Override
    //menu delete a doctor and their information
    public void deleteDoctor() {

        try {
            int present = 0;
            if (DoctorInformation.size() == 0) {  //details of the doctors are not entered
                System.out.println("Error! There are no doctors to delete.");
            } else {
                Scanner input = new Scanner(System.in);
                System.out.print("Enter the medical licence number of the doctor you want to delete: ");
                int licenceNumber = input.nextInt();
                for (Doctor drDelete : DoctorInformation) {
                    int licenceCheck = drDelete.getMedicalLicenceNumber();
                    if (licenceNumber == licenceCheck) {
                        //printing the information about the deleted doctor in tabular format
                        System.out.println();
                        System.out.println("INFORMATION ABOUT THE DELETED DOCTOR");
                        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------");
                        System.out.printf("%-12s %-13s %-14s %-16s %-12s %-12s", "First Name          ", "Surname          ", "Specialisation          ", "Medical Licence Number          ", "Date Of Birth          ", "Mobile Number          ");
                        System.out.println();
                        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------");
                        System.out.format("%-20s %-17s %-24s %-32s %-23s %13s", drDelete.getFirstName(), drDelete.getSurname(), drDelete.getSpecialisation(), drDelete.getMedicalLicenceNumber(), drDelete.getDateOfBirth(), drDelete.getMobileNumber());
                        System.out.println();
                        System.out.println();
                        System.out.println("Successfully deleted the doctor with the medical licence number: "+ drDelete.getMedicalLicenceNumber());
                        DoctorInformation.remove(drDelete);  //removing the selected doctor from the array list
                        System.out.println("Total number of doctors = " + DoctorInformation.size());  //displaying the total number of available doctors
                        present = 1;
                        break;
                    }
                }
                if(present == 0) {
                    System.out.println();
                    System.out.println("Error! A doctor with that medical licence number doesn't exist.");
                }
            }
        }catch (Exception e){
            System.out.println("Error! Enter a valid medical licence number.");
        }
    }

    //menu printing list of doctors and their information
    @Override
    public void printListOfDoctors() {
        SurnameSort();  //information of the doctors ordered alphabetically according to the surname
        try {
            if (DoctorInformation.size() == 0) {  //details of the doctors are not entered
                System.out.println("Error! There are no doctors to print.");
            }
            else {
                //printing the information about the doctors in the system using a tabular format
                System.out.println();
                System.out.println("INFORMATION ABOUT THE DOCTORS IN THE SYSTEM");
                System.out.println("--------------------------------------------------------------------------------------------------------------------------------------");
                System.out.printf("%-12s %-13s %-14s %-16s %-12s %-12s", "First Name          ", "Surname          ", "Specialisation          ", "Medical Licence Number          ", "Date Of Birth          ", "Mobile Number          ");
                System.out.println();
                System.out.println("--------------------------------------------------------------------------------------------------------------------------------------");
                for (Doctor drList : DoctorInformation) {
                    System.out.format("%-20s %-17s %-24s %-32s %-23s %13s", drList.getFirstName(), drList.getSurname(), drList.getSpecialisation(), drList.getMedicalLicenceNumber(), drList.getDateOfBirth(), drList.getMobileNumber());
                    System.out.println();
                }
            }
        } catch (Exception e) {
            System.out.println("Error! Invalid data format.");
        }
    }

    //menu save information in a text file
    @Override
    public void saveInAFile() {

        try {
            File data = new File("DoctorInformation.txt");
            if (data.createNewFile()) {  //creating a file with the name DoctorInformation.txt
                System.out.println("File created:" + data.getName());
            }
            DoctorInformationProcess.clear();
            FileWriter writer = new FileWriter("DoctorInformation.txt");
            for (Doctor drSave : DoctorInformation) {  //inserting all the data in the array list to the file
                int count = 0;
                DoctorInformationProcess.add(count, drSave.getFirstName() + "\n" + drSave.getSurname() + "\n" + drSave.getDateOfBirth() + "\n" + drSave.getMobileNumber() + "\n" + drSave.getMedicalLicenceNumber() + "\n" + drSave.getSpecialisation() + "\n");
            }
            for (String writeInfo : DoctorInformationProcess) {
                writer.write(writeInfo);  //writing all the information to the text file DoctorInformation.txt
            }
            writer.close();
            DoctorInformationProcess.clear();
            System.out.println("Successfully stored the information in the file.");
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch(IOException e){
            System.out.println("Error! Invalid data format.");
        }
    }

    //menu read saved information in the text file
    public void loadFile() {

        try{
            File DoctorInformation= new File("DoctorInformation.txt");  //file path is passed as a parameter
            Scanner Reader= new Scanner(DoctorInformation);
            if (Reader.hasNextLine()) {
                int lineCount;
                String doctorInformationFromFile;
                FileReader readFile = new FileReader("DoctorInformation.txt");
                BufferedReader readBuffer = new BufferedReader(readFile);  //creating an object of bufferReader class
                int count = 0;
                while(Reader.hasNextLine()) {
                    Reader.nextLine();
                    count++;
                }
                //declaring the loop variables
                int count1=count/6;
                int r=1;
                int t=6;
                int m;
                int z=0;
                DoctorInformationProcess.clear();
                this.DoctorInformation.clear();
                for (m =0; m <=count1-1; m++){
                    for (lineCount = 1; lineCount < count+1; lineCount++) {
                        if (lineCount >= r && lineCount<= t) {
                            doctorInformationFromFile = readBuffer.readLine();
                            DoctorInformationProcess.add(doctorInformationFromFile);
                        }
                    }
                    //reading the information in the file to the system
                    r = r +6;
                    t = t +6;
                    Doctor drRead = new Doctor(DoctorInformationProcess.get(z),DoctorInformationProcess.get(z +1),DoctorInformationProcess.get(z +2),Integer.parseInt(DoctorInformationProcess.get(z +3)),Integer.parseInt(DoctorInformationProcess.get(z +4)),DoctorInformationProcess.get(z +5));
                    this.DoctorInformation.add(drRead);
                    z = z +6;
                }
                System.out.println("Successfully loaded the saved information in the file.");
            }
            else{
                System.out.println("Error! The file is empty.");
            }
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            System.out.println("Error! Invalid data format.");
        }
    }

    //display menu system
    @Override
    public void displayMenu() {

        System.out.println("----- MENU -----");
        System.out.println("1 : Add a new doctor");
        System.out.println("2 : Delete a doctor");
        System.out.println("3 : Print the list of the doctors");
        System.out.println("4 : Save in a file");
        System.out.println("5 : Load saved information");
        System.out.println("6 : Open Graphical User Interface");
        System.out.println("7 : Exit the program");
    }

    //checking the doctor mobile number whether it exist already
    public int checkPhoneNumber(int phoneNO) {
        int index = -1;
        for (Doctor drPhone : DoctorInformation) {
            int check = drPhone.getMobileNumber();
            if (check == (phoneNO)) {
                index = DoctorInformation.indexOf(drPhone);
                break;
            }
        }
        return index;
    }
    //checking the doctor medical licence number whether it exist already
    public int checkMedicalLicenceNumber(int medicalLicenceNO){

        int index = -1;
        for(Doctor drLicence : DoctorInformation){
            int licenseNO = drLicence.getMedicalLicenceNumber();
            if(licenseNO == medicalLicenceNO){
                index = DoctorInformation.indexOf(drLicence);
                break;
            }
        }
        return index;
    }

    //sorting alphabetically
    public void SurnameSort(){
        drSurnameSort snSort = new drSurnameSort();
        DoctorInformation.sort(snSort);
    }
    public void FirstNameSort(){
        drFirstNameSort fnSort = new drFirstNameSort();
        DoctorInformation.sort(fnSort);
    }

    //calling method GUI
    public void openGUI(){
        // TODO Auto-generated method stub
        GUI gui = new GUI();
        gui.frame1.setVisible(true);
    }

    //implementing graphical user interface
    class GUI extends JFrame implements ActionListener {

        //initialising
        JFrame frame1, frame2;
        JButton checkAvailability,button1,drSelectBtn1,drSelectBtn2,drSelectBtn3,drSelectBtn4,drSelectBtn5,drSelectBtn6,drSelectBtn7,drSelectBtn8,drSelectBtn9,drSelectBtn10,bookConsultation;
        JLabel label1,label2,labelFrame2,labelDate,labelMonth,labelYear,labelHours,labelMinutes,label2Frame2,labelHoursAfter,labelMinutesAfter,labelPatientFName,labelPatientSurName,labelPatientDOB,labelPatientMobileNumber,labelPatientID,labelPatientPrompt,labelCost,labelNotes;
        JTable table1,table2;
        JTextField date,month,year,hours,minutes,hoursAfter,minutesAfter,TextPatientFName,TextPatientSurName,TextPatientDOB,TextPatientMobileNumber,TextPatientID,TextCost,TextNotes;
        public GUI()
        {
            initialize();
        }
        public void initialize() {
            // TODO Auto-generated method stub
            //implementing frame 1
            frame1 = new JFrame("Skin Consultation Centre");
            frame1.setBounds(50,50,1200,550);
            frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame1.getContentPane().setLayout(null);
            label1 = new JLabel("Skin Consultation Centre GUI");
            label1.setBounds(454,10,400,25);
            label1.setFont(new Font("Arial",Font.PLAIN,24));
            frame1.getContentPane().add(label1);
            button1 = new JButton("Sort Alphabetically");
            button1.addActionListener(this);
            button1.setBounds(30,60,145,39);
            button1.setFocusPainted(false);
            frame1.getContentPane().add(button1);
            label2 = new JLabel("SELECT DOCTOR");
            label2.setBounds(1032,140,400,16);
            label2.setFont(new Font("Arial",Font.PLAIN,14));
            frame1.getContentPane().add(label2);
            //creating select buttons to select the doctor
            drSelectBtn1 = new JButton("select");
            drSelectBtn1.addActionListener(this);
            drSelectBtn1.setBounds(1056,172,70,15);
            drSelectBtn2 = new JButton("select");
            drSelectBtn2.addActionListener(this);
            drSelectBtn2.setBounds(1056,189,70,15);
            drSelectBtn3 = new JButton("select");
            drSelectBtn3.addActionListener(this);
            drSelectBtn3.setBounds(1056,206,70,15);
            drSelectBtn4 = new JButton("select");
            drSelectBtn4.addActionListener(this);
            drSelectBtn4.setBounds(1056,223,70,15);
            drSelectBtn5 = new JButton("select");
            drSelectBtn5.addActionListener(this);
            drSelectBtn5.setBounds(1056,240,70,15);
            drSelectBtn6 = new JButton("select");
            drSelectBtn6.addActionListener(this);
            drSelectBtn6.setBounds(1056,257,70,15);
            drSelectBtn7 = new JButton("select");
            drSelectBtn7.addActionListener(this);
            drSelectBtn7.setBounds(1056,273,70,15);
            drSelectBtn8 = new JButton("select");
            drSelectBtn8.addActionListener(this);
            drSelectBtn8.setBounds(1056,290,70,15);
            drSelectBtn9 = new JButton("select");
            drSelectBtn9.addActionListener(this);
            drSelectBtn9.setBounds(1056,307,70,15);
            drSelectBtn10 = new JButton("select");
            drSelectBtn10.addActionListener(this);
            drSelectBtn10.setBounds(1056,323,70,15);
            //selecting the doctors according to the instructions from the buttons
            int numberOfDoctors = DoctorInformation.size();
            switch (numberOfDoctors) {
                case 1 -> frame1.getContentPane().add(drSelectBtn1);
                case 2 -> {
                    frame1.getContentPane().add(drSelectBtn1);
                    frame1.getContentPane().add(drSelectBtn2);
                }
                case 3 -> {
                    frame1.getContentPane().add(drSelectBtn1);
                    frame1.getContentPane().add(drSelectBtn2);
                    frame1.getContentPane().add(drSelectBtn3);
                }
                case 4 -> {
                    frame1.getContentPane().add(drSelectBtn1);
                    frame1.getContentPane().add(drSelectBtn2);
                    frame1.getContentPane().add(drSelectBtn3);
                    frame1.getContentPane().add(drSelectBtn4);
                }
                case 5 -> {
                    frame1.getContentPane().add(drSelectBtn1);
                    frame1.getContentPane().add(drSelectBtn2);
                    frame1.getContentPane().add(drSelectBtn3);
                    frame1.getContentPane().add(drSelectBtn4);
                    frame1.getContentPane().add(drSelectBtn5);
                }
                case 6 -> {
                    frame1.getContentPane().add(drSelectBtn1);
                    frame1.getContentPane().add(drSelectBtn2);
                    frame1.getContentPane().add(drSelectBtn3);
                    frame1.getContentPane().add(drSelectBtn4);
                    frame1.getContentPane().add(drSelectBtn5);
                    frame1.getContentPane().add(drSelectBtn6);
                }
                case 7 -> {
                    frame1.getContentPane().add(drSelectBtn1);
                    frame1.getContentPane().add(drSelectBtn2);
                    frame1.getContentPane().add(drSelectBtn3);
                    frame1.getContentPane().add(drSelectBtn4);
                    frame1.getContentPane().add(drSelectBtn5);
                    frame1.getContentPane().add(drSelectBtn6);
                    frame1.getContentPane().add(drSelectBtn7);
                }
                case 8 -> {
                    frame1.getContentPane().add(drSelectBtn1);
                    frame1.getContentPane().add(drSelectBtn2);
                    frame1.getContentPane().add(drSelectBtn3);
                    frame1.getContentPane().add(drSelectBtn4);
                    frame1.getContentPane().add(drSelectBtn5);
                    frame1.getContentPane().add(drSelectBtn6);
                    frame1.getContentPane().add(drSelectBtn7);
                    frame1.getContentPane().add(drSelectBtn8);
                }
                case 9 -> {
                    frame1.getContentPane().add(drSelectBtn1);
                    frame1.getContentPane().add(drSelectBtn2);
                    frame1.getContentPane().add(drSelectBtn3);
                    frame1.getContentPane().add(drSelectBtn4);
                    frame1.getContentPane().add(drSelectBtn5);
                    frame1.getContentPane().add(drSelectBtn6);
                    frame1.getContentPane().add(drSelectBtn7);
                    frame1.getContentPane().add(drSelectBtn8);
                    frame1.getContentPane().add(drSelectBtn9);
                }
                case 10 -> {
                    frame1.getContentPane().add(drSelectBtn1);
                    frame1.getContentPane().add(drSelectBtn2);
                    frame1.getContentPane().add(drSelectBtn3);
                    frame1.getContentPane().add(drSelectBtn4);
                    frame1.getContentPane().add(drSelectBtn5);
                    frame1.getContentPane().add(drSelectBtn6);
                    frame1.getContentPane().add(drSelectBtn7);
                    frame1.getContentPane().add(drSelectBtn8);
                    frame1.getContentPane().add(drSelectBtn9);
                    frame1.getContentPane().add(drSelectBtn10);
                }
            }
            //creating table 1 for the frame 1
            table1 = new JTable();
            table1.setBounds(20,160,980,300);
            frame1.getContentPane().add(table1);
            tableDisplay();
            //implementing frame 2
            frame2 = new JFrame("Book Consultation");
            frame2.setBounds(50,50,1200,550);
            frame2.getContentPane().setLayout(null);
            table2 = new JTable();
            table2.setBounds(300,100,850,300);
            frame2.getContentPane().add(table2);
            tableDisplayConsultations();
            labelFrame2 = new JLabel();
            labelFrame2.setBounds(25,0,400,25);
            label2Frame2 = new JLabel();
            label2Frame2.setBounds(300,0,400,25);
            labelDate = new JLabel();
            labelDate.setText("Day");
            labelDate.setBounds(10,27,100,20);
            labelMonth = new JLabel();
            labelMonth.setText("Month");
            labelMonth.setBounds(90,27,100,20);
            labelYear = new JLabel();
            labelYear.setText("Year");
            labelYear.setBounds(170,27,100,20);
            labelHours = new JLabel();
            labelHours.setText("Start hour(1-24)");
            labelHours.setBounds(10,77,150,20);
            labelMinutes = new JLabel();
            labelMinutes.setText("Start minute(0-60)");
            labelMinutes.setBounds(120,77,150,20);
            labelHoursAfter = new JLabel();
            labelHoursAfter.setText("End hour(1-24)");
            labelHoursAfter.setBounds(10,127,150,20);
            labelMinutesAfter = new JLabel();
            labelMinutesAfter.setText("End minute(0-60)");
            labelMinutesAfter.setBounds(120,127,150,20);
            checkAvailability = new JButton("Check Availability");
            checkAvailability.addActionListener(this);
            checkAvailability.setBounds(10,187,145,20);
            date = new JTextField();
            date.setBounds(10,52,50,20);
            month = new JTextField();
            month.setBounds(90,52,50,20);
            year = new JTextField();
            year.setBounds(170,52,50,20);
            hours = new JTextField();
            hours.setBounds(10,102,100,20);
            minutes = new JTextField();
            minutes.setBounds(120,102,100,20);
            hoursAfter = new JTextField();
            hoursAfter.setBounds(10,152,100,20);
            minutesAfter = new JTextField();
            minutesAfter.setBounds(120,152,100,20);
            //creating attributes of the patient information
            labelPatientPrompt = new JLabel();
            labelPatientPrompt.setText("Patient Details");
            labelPatientPrompt.setBounds(63,227,400,20);
            labelPatientFName = new JLabel();
            labelPatientFName.setText("First Name");
            labelPatientFName.setBounds(10,250,100,20);
            labelPatientSurName = new JLabel();
            labelPatientSurName.setText("Surname");
            labelPatientSurName.setBounds(120,250,100,20);
            labelPatientDOB = new JLabel();
            labelPatientDOB.setText("Date Of Birth");
            labelPatientDOB.setBounds(10,300,100,20);
            labelPatientMobileNumber = new JLabel();
            labelPatientMobileNumber.setText("Mobile Number");
            labelPatientMobileNumber.setBounds(120,300,150,20);
            labelPatientID = new JLabel();
            labelPatientID.setText("Patient ID");
            labelPatientID.setBounds(10,350,150,20);
            labelCost = new JLabel();
            labelCost.setText("Cost (£)");
            labelCost.setBounds(120,350,100,20);
            labelNotes = new JLabel();
            labelNotes.setText("Notes");
            labelNotes.setBounds(10,400,100,20);
            TextPatientFName = new JTextField();
            TextPatientFName.setBounds(10,275,100,20);
            TextPatientSurName = new JTextField();
            TextPatientSurName.setBounds(120,275,100,20);
            TextPatientDOB = new JTextField();
            TextPatientDOB.setBounds(10,325,100,20);
            TextPatientMobileNumber = new JTextField();
            TextPatientMobileNumber.setBounds(120,325,100,20);
            TextPatientID = new JTextField();
            TextPatientID.setBounds(10,375,100,20);
            TextCost = new JTextField();
            TextCost.setBounds(120,375,100,20);
            TextNotes = new JTextField();
            TextNotes.setBounds(10,425,212,40);
            bookConsultation = new JButton("Book Consultation");
            bookConsultation.addActionListener(this);
            bookConsultation.setBounds(10,480,150,20);
        }
        //displaying frame 2
        int DoctorLicense;
        public void DisplayFrame2(int index){
            String DocName = DoctorInformation.get(index).getFirstName();
            String DocSurname = DoctorInformation.get(index).getSurname();
            DoctorLicense = DoctorInformation.get(index).getMedicalLicenceNumber();
            labelFrame2.setText("Selected Doctor: "+DocName+" "+DocSurname);
            frame2.getContentPane().add(labelFrame2);
            frame2.getContentPane().add(checkAvailability);
            frame2.getContentPane().add(bookConsultation);
            frame2.getContentPane().add(label2Frame2);
            frame2.getContentPane().add(labelDate);
            frame2.getContentPane().add(labelMonth);
            frame2.getContentPane().add(labelYear);
            frame2.getContentPane().add(labelHours);
            frame2.getContentPane().add(labelMinutes);
            frame2.getContentPane().add(labelHoursAfter);
            frame2.getContentPane().add(labelMinutesAfter);
            frame2.getContentPane().add(month);
            frame2.getContentPane().add(date);
            frame2.getContentPane().add(year);
            frame2.getContentPane().add(hours);
            frame2.getContentPane().add(minutes);
            frame2.getContentPane().add(hoursAfter);
            frame2.getContentPane().add(minutesAfter);
            frame2.getContentPane().add(labelPatientPrompt);
            frame2.getContentPane().add(labelPatientFName);
            frame2.getContentPane().add(labelPatientSurName);
            frame2.getContentPane().add(labelPatientDOB);
            frame2.getContentPane().add(labelPatientMobileNumber);
            frame2.getContentPane().add(labelPatientID);
            frame2.getContentPane().add(TextPatientFName);
            frame2.getContentPane().add(TextPatientSurName);
            frame2.getContentPane().add(TextPatientDOB);
            frame2.getContentPane().add(TextPatientMobileNumber);
            frame2.getContentPane().add(TextPatientID);
            frame2.getContentPane().add(labelCost);
            frame2.getContentPane().add(labelNotes);
            frame2.getContentPane().add(TextCost);
            frame2.getContentPane().add(TextNotes);
            frame2.setVisible(true);
        }
        //displaying table 1 in frame 1
        void tableDisplay (){

            List<Object[]> list = new ArrayList<>();
            list.add(new Object[]{"    Medical Licence Number", "                First Name", "                  Surname","             Date Of Birth","            Mobile Number","             Specialization"});
            for (Doctor doc : DoctorInformation) {
                list.add(new Object[]{
                        doc.getMedicalLicenceNumber(),
                        doc.getFirstName(),
                        doc.getSurname(),
                        doc.getDateOfBirth(),
                        doc.getMobileNumber(),
                        doc.getSpecialisation()
                });
            }
            table1.setModel(new DefaultTableModel(list.toArray(new Object[][] {}),
                    new String[] {"    Medical Licence Number", "                First Name", "                  Surname","             Date Of Birth","            Mobile Number","             Specialization"}));
        }
        //displaying table 2 in frame 2
        void tableDisplayConsultations (){

            List<Object[]> list = new ArrayList<>();
            list.add(new Object[]{"     Consultation ID", "           Doctor ID", "          Patient ID","         Start Time","          End Time","             Notes"});
            for (Consultation Con : ConsultationDetails) {
                list.add(new Object[]{
                        Con.getConsultationNumber(),
                        Con.getDoctorMedicalLicenceNumber(),
                        Con.getPatientIDConsultation(),
                        Con.getStartDateTime(),
                        Con.getEndDateTime(),
                        Con.getNotes()
                });
            }
            table2.setModel(new DefaultTableModel(list.toArray(new Object[][] {}),
                    new String[]{"     Consultation ID", "           Doctor ID", "          Patient ID","         Start Time","          End Time","             Notes"}));
        }
        int timeSlotFilled ;
        int consultationNBR;

        public void actionPerformed(ActionEvent e) {

            int values =0;
            // TODO Auto-generated method stub
            String action = ((JButton) e.getSource()).getActionCommand();
            //sorting alphabetically
            if(action.equals("Sort Alphabetically"))
            {
                FirstNameSort();
                tableDisplay();
            }
            //input validation - no values entered
            if(action.equals("Check Availability")) {
                if(date.getText().equals("") ||  month.getText().equals("") ||  year.getText().equals("") ||  hours.getText().equals("") ||  minutes.getText().equals("")||  hoursAfter.getText().equals("") ||  minutesAfter.getText().equals("") ){
                    label2Frame2.setText("Error! Please fill all the fields.");
                    label2Frame2.setForeground(Color.red);
                    frame2.getContentPane().add(label2Frame2);
                }
                else {
                    label2Frame2.setText("");
                    //initialising variables
                    int date1 = Integer.parseInt(date.getText());
                    int month1 = Integer.parseInt(month.getText());
                    int year1 = Integer.parseInt(year.getText());
                    int hours1 = Integer.parseInt(hours.getText());
                    int minutes1 = Integer.parseInt(minutes.getText());
                    int hoursAfter1 = Integer.parseInt(hoursAfter.getText());
                    int minutesAfter1 = Integer.parseInt(minutesAfter.getText());
                    //date should be between 1 and 31
                    if (date1 > 31 || date1 < 1) {
                        labelDate.setForeground(Color.red);
                        date.setForeground(Color.red);
                        label2Frame2.setText("Error! Invalid input.");
                        label2Frame2.setForeground(Color.red);
                        frame2.getContentPane().add(label2Frame2);
                    }
                    else{
                        labelDate.setForeground(Color.black);
                        date.setForeground(Color.black);
                        label2Frame2.setText("");
                        label2Frame2.setForeground(Color.black);
                        values++;
                    }
                    //month should be between 1 and 12
                    if (month1 > 12 || month1 < 1) {
                        labelMonth.setForeground(Color.red);
                        month.setForeground(Color.red);
                        label2Frame2.setText("Error! Invalid input.");
                        label2Frame2.setForeground(Color.red);
                        frame2.getContentPane().add(label2Frame2);
                    }
                    else{
                        labelMonth.setForeground(Color.black);
                        month.setForeground(Color.black);
                        label2Frame2.setText("");
                        label2Frame2.setForeground(Color.black);
                        values++;
                    }
                    //year should be 2023
                    if (year1 != 2023) {
                        labelYear.setForeground(Color.red);
                        year.setForeground(Color.red);
                        label2Frame2.setText("Error! Invalid input.");
                        label2Frame2.setForeground(Color.red);
                        frame2.getContentPane().add(label2Frame2);
                    }
                    else{
                        labelYear.setForeground(Color.black);
                        year.setForeground(Color.black);
                        label2Frame2.setText("");
                        label2Frame2.setForeground(Color.black);
                        values++;
                    }
                    //minutes should be between 0 and 60
                    if (minutes1 > 60 || minutes1 < 0) {
                        labelMinutes.setForeground(Color.red);
                        minutes.setForeground(Color.red);
                        label2Frame2.setText("Error! Invalid input.");
                        label2Frame2.setForeground(Color.red);
                        frame2.getContentPane().add(label2Frame2);
                    }
                    else{
                        labelMinutes.setForeground(Color.black);
                        minutes.setForeground(Color.black);
                        label2Frame2.setText("");
                        label2Frame2.setForeground(Color.black);
                        values++;
                    }
                    //hours should be between 1 and 24
                    if (hours1 > 24 || hours1 < 1) {
                        labelHours.setForeground(Color.red);
                        hours.setForeground(Color.red);
                        label2Frame2.setText("Error! Invalid input.");
                        label2Frame2.setForeground(Color.red);
                        frame2.getContentPane().add(label2Frame2);
                    }
                    else{
                        labelHours.setForeground(Color.black);
                        hours.setForeground(Color.black);
                        label2Frame2.setText("");
                        label2Frame2.setForeground(Color.black);
                        values++;
                    }
                    //minutes should be between 0 and 60
                    if (minutesAfter1 > 60 || minutesAfter1 < 0) {
                        labelMinutesAfter.setForeground(Color.red);
                        minutesAfter.setForeground(Color.red);
                        label2Frame2.setText("Error! Invalid input.");
                        label2Frame2.setForeground(Color.red);
                        frame2.getContentPane().add(label2Frame2);
                    }
                    else{
                        labelMinutesAfter.setForeground(Color.black);
                        minutesAfter.setForeground(Color.black);
                        label2Frame2.setText("");
                        label2Frame2.setForeground(Color.black);
                        values++;
                    }
                    //hours should be between 1 and 24
                    if (hoursAfter1 > 24 || hoursAfter1 < 1) {
                        labelHoursAfter.setForeground(Color.red);
                        hoursAfter.setForeground(Color.red);
                        label2Frame2.setText("Error! Invalid input.");
                        label2Frame2.setForeground(Color.red);
                        frame2.getContentPane().add(label2Frame2);
                    }
                    else{
                        labelHoursAfter.setForeground(Color.black);
                        hoursAfter.setForeground(Color.black);
                        label2Frame2.setText("");
                        label2Frame2.setForeground(Color.black);
                        values++;
                    }
                    //doctor not available at the same time
                    LocalDateTime startDT = LocalDateTime.of(year1,month1,date1,hours1,minutes1);
                    LocalDateTime endDT = LocalDateTime.of(year1,month1,date1,hoursAfter1,minutesAfter1);
                    for (Consultation consultation : ConsultationDetails) {
                        int DocID = consultation.getDoctorMedicalLicenceNumber();
                        if (DoctorLicense == DocID) {
                            if(consultation.getStartDateTime().equals(startDT) && consultation.getEndDateTime().equals(endDT)){
                                label2Frame2.setText("The doctor is not available at that time.");
                                label2Frame2.setForeground(Color.red);
                            }
                            else {
                                label2Frame2.setText("");
                                label2Frame2.setForeground(Color.black);
                            }
                            break;
                        }
                    }
                    //cost
                    if(values == 7) {
                        frame2.getContentPane().add(labelPatientID);
                        int timeslot = endDT.getHour()-startDT.getHour();
                        int cost1 = 15;
                        int cost2 = ((timeslot-1)*25)+15;
                        TextCost.setText(String.valueOf(cost2));
                        consultationNBR = ConsultationDetails.size()+1;
                        Consultation c = new Consultation(cost1,startDT,endDT);
                        c.setConsultationNumber(consultationNBR);
                        c.setDoctorMedicalLicenceNumber(DoctorLicense);
                        ConsultationDetails.add(c);
                    }
                }
            }
            if (values == 7){
                timeSlotFilled = 7;
            }
            if(e.getSource()== bookConsultation){
                if (timeSlotFilled == 7){
                    for (Consultation consultation : ConsultationDetails) {
                        int consultationNumber = consultation.getConsultationNumber();
                        //input validation - no values are entered
                        if (consultationNumber == consultationNBR) {
                            if(TextPatientFName.getText().equals("") ||  TextPatientSurName.getText().equals("") ||  TextPatientDOB.getText().equals("") ||  TextPatientMobileNumber.getText().equals("") ||  TextPatientID.getText().equals("")||  TextNotes.getText().equals("")){
                                label2Frame2.setText("Error! Please fill all the fields.");
                                label2Frame2.setForeground(Color.red);
                                frame2.getContentPane().add(label2Frame2);
                            }
                            else {
                                //adding values to the table
                                String patientName = (TextPatientFName.getText());
                                String patientSurname = (TextPatientSurName.getText());
                                String pDateOfBirth = (TextPatientDOB.getText());
                                String pNotes = TextNotes.getText();
                                int pMobileNumber = Integer.parseInt(TextPatientMobileNumber.getText());
                                int patientID = Integer.parseInt(TextPatientID.getText());
                                Patient p = new Patient();
                                p.setPatientID(patientID);
                                p.setFirstName(patientName);
                                p.setSurname(patientSurname);
                                p.setDateOfBirth(pDateOfBirth);
                                p.setMobileNumber(pMobileNumber);
                                consultation.setNotes(pNotes);
                                consultation.setPatientIDConsultation(patientID);
                                tableDisplayConsultations();
                            }
                            break;
                        }
                    }
                }
            }
            //allocating doctor
            if(e.getSource()== drSelectBtn1){
                DisplayFrame2(0);
            }
            else if(e.getSource()== drSelectBtn2){
                DisplayFrame2(1);
            }
            else if(e.getSource()== drSelectBtn3){
                DisplayFrame2(2);
            }
            else if(e.getSource()== drSelectBtn4){
                DisplayFrame2(3);
            }
            else if(e.getSource()== drSelectBtn5){
                DisplayFrame2(4);
            }
            else if(e.getSource()== drSelectBtn6){
                DisplayFrame2(5);
            }
            else if(e.getSource()== drSelectBtn7){
                DisplayFrame2(6);
            }
            else if(e.getSource()== drSelectBtn8){
                DisplayFrame2(7);
            }
            else if(e.getSource()== drSelectBtn9){
                DisplayFrame2(8);
            }
            else if(e.getSource()== drSelectBtn10){
                DisplayFrame2(9);
            }
        }
    }
}

