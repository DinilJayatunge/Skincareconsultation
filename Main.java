import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        WestminsterSkinConsultationManager option = new WestminsterSkinConsultationManager();
        String mode;
        option.displayMenu();
        label:
        while (true) {
            Scanner menuOption = new Scanner(System.in);
            System.out.println();
            System.out.print("Enter a menu option: ");
            mode = menuOption.nextLine();
            mode = mode.toUpperCase();
            switch (mode) {
                case "1":
                    option.addNewDoctor();
                    break;
                case "2":
                    option.deleteDoctor();
                    break;
                case "3":
                    option.printListOfDoctors();
                    break;
                case "4":
                    option.saveInAFile();
                    break;
                case "5":
                    option.loadFile();
                    break;
                case "6":
                    option.openGUI();
                    break;
                case "7":
                    System.out.println("Successfully exited the program.");
                    break label;
                default:
                    System.out.println("Menu not identified, enter a valid menu next time.");
                    break;
            }
        }
    }
}
