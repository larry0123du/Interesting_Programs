import java.util.Scanner;
/**
 * LifeSpan Calculator Client Interface
 * version 0.0.1
 * @author Ruihang Du, du113@purdue.edu
 * This is the Client Interface of the LifeSpan Calculator
 * Created by Ruihang Du on 15-9-17.
 */
public class LifeSpanClient {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.println("Welcome to LifeSpan Calculator\n" + "Please enter your date of birth (MM/DD/YYYY): ");
        String birthday = s.nextLine();
        System.out.println("Please enter the exact time of your birth (HH:MM:SS): ");
        String birthTime = s.nextLine();
        LifeSpanCalc l = new LifeSpanCalc(birthday, birthTime);
        while (!l.checkBirthdayValid(birthday) && !l.checkBirthTimeValid(birthTime)) {
            System.out.println("Invalid birthday or birth time, please try again!");
            System.out.println("Please enter your date of birth (MM/DD/YYYY): ");
            birthday = s.nextLine();
            System.out.println("Please enter the exact time of your birth (HH:MM:SS): ");
            birthTime = s.nextLine();
        }
        System.out.println("You have lived " + l.calcSecondsLived(birthday, birthTime) + " seconds!");
    }

}
