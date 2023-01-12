/* Name: Coco Ren
 * Date: January 2nd, 2023
 * Description:   This program reads in a DNA data file and identifies genetic markers
 *                such as skin type or diabetes risk.
 */

import java.io.IOException;
import java.util.Scanner;

public class Main
{

    public static int getUserInput() {
        System.out.println("\nHello! Welcome to Analysis of Human DNA");
        System.out.println("=======================================");
        System.out.print("\nPlease enter 1 to continue, and 2 to exit: ");
        //learned how to simplify code here
        return new Scanner(System.in).nextInt();
    }

    public static void main(String args[]) throws IOException
    {
        //learned how to create an object
        dnaResults user = new dnaResults();
        String checkResult = " ";


        int userChoice = getUserInput();
        while (userChoice != 2)
        {

            if (userChoice == 1) {
                Scanner two = new Scanner(System.in);
                System.out.println("Please input the file name of your DNA file: ");
                String userFile = two.nextLine();
                System.out.println("Parsing " + userFile + "\n");
                user.parseFile(userFile);
                //learned how to call a file
                System.out.println(user.checkSkinType(user.rsid, user.genotypes));
                System.out.println(user.checkType2Diabetes(user.rsid, user.genotypes));
                user.getDiet();

            Scanner three = new Scanner(System.in);
            System.out.println("Enter 'Yes' if you want to save your results to a file, or any key to continue. ");
            String userSave = three.nextLine();
            if (userSave.equals("Yes")) {
                user.saveResultsToFile(userFile);
                System.out.println("Your file has been saved in result.txt!");
            }

            } else {
                System.out.println("Invalid input. Please input again.");
            }

            userChoice = getUserInput();

        }


        System.out.println("Thank you for using this program. I hope it was useful!");



    }
}