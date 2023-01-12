/* Name: Coco Ren
 * Date: January 2nd, 2023
 * Description:   This program reads in a DNA data file and identifies genetic markers
 *                such as skin type or diabetes risk.
 */

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.*;
import java.io.FileWriter;

public class dnaResults {
    String checkResult = "";
    //simplifying code, saving myself a conditional
    String output = "No DNA info on Skin Type";
    String output2 = "No DNA info on Type-2 Diabetes";

    static ArrayList<String> rsid = new ArrayList<String>();
    static ArrayList<String> genotypes = new ArrayList<String>();

    //parseFile method
    public void parseFile(String fileName) throws IOException {

        File file = new File(fileName);
        Scanner scan = new Scanner(file);
        int i = 0;
        String userLine = " ";
        while (scan.hasNextLine()) {
            userLine = scan.nextLine();
            i++;
            char firstChar = userLine.charAt(0);
            if (firstChar != '#') {
                //something new i learned
                String[] allLines = userLine.split("\t");
                dnaResults.rsid.add(allLines[0]);
                dnaResults.genotypes.add(allLines[3]);
            }

        }
        scan.close();
    }

    //checkSkinType method
    public String checkSkinType(ArrayList<String> rsid, ArrayList<String> genotypes) {
        int index = 0;
        output = "No DNA info on Skin Type.";
        for (String element : rsid) {
            if (element.contains("rs1426654")) {
                index = rsid.indexOf(element);
                String finalGenotype = genotypes.get(index);

                if (finalGenotype.equals("AA")) {
                    output = "Probably light-skinned, European ancestry.";
                } else if (finalGenotype.equals("AG")) {
                    output = "Probably mixed African/European ancestry.";
                } else if (finalGenotype.equals("GG")) {
                    output = "Probably darker-skinned, Asian or African ancestry.";
                }
                break;
            }
        }
        return output;
    }


    //checkType2Diabetes method
    public String checkType2Diabetes(ArrayList<String> rsid, ArrayList<String> genotypes) {
        int index2 = 0;
        output2 = "No DNA info on Type-2 Diabetes";
        for (String element : rsid) {
            if (element.contains("rs7754840")) {
                index2 = rsid.indexOf(element);
                String genotypeForDiabetes = genotypes.get(index2);

                if (genotypeForDiabetes.equals("GG")) {
                    output2 = "Normal risk for Type-2 Diabetes.";
                } else if (genotypeForDiabetes.equals("CC")) {
                    output2 = "1.3x Increased risk for Type-2 Diabetes.";
                } else if (genotypeForDiabetes.equals("CG")) {
                    output2 = "1.3x Increased risk for Type-2 Diabetes.";
                }
                break;
            }

        }
        return output2 + "\nRefer to this link for more information on Type-2 Diabetes: https://www.mayoclinic.org/diseases-conditions/type-2-diabetes/symptoms-causes/syc-20351193";
    }

    public String getGenotype(String rsidName) {
        int rsidIndex = rsid.indexOf(rsidName);
        String genoType = " ";
        //for outliers, if rsid is not found, -1 will be returned
        if (rsidIndex > -1) {
            genoType = genotypes.get(rsidIndex);
        }
        return genoType.trim();
    }

    public void getDiet() {
        String rs4994 = getGenotype("rs4994");
        String rs1042713 = getGenotype("rs1042713");
        String rs1801282 = getGenotype("rs1801282");
        String rs1042714 = getGenotype("rs1042714");
        String rs1799883 = getGenotype("rs1799883");


        int directionGo = 1;
        if (rs4994.equals("AA") || rs4994.equals("TT")) {
            if (rs1042713.equals("AA") || rs1042713.equals("TT")) {
                directionGo = 2;
            }
        }

        if (directionGo == 1) {
            checkResult = checkResult + " \n" + ("[88%] Genetic Disprivilege: Only High Intensity Exercise Will Help You Lose Weight.\t");
        } else {
            checkResult = checkResult + " \n" + ("[12%] Genetic Privilege: Any Exercise Works For You.\n");
        }


        if (directionGo == 1)
        {
            if (rs1799883.equals("GG"))
            {
                directionGo = 2;
            }
        }

        if (directionGo == 1) {
            if (rs1801282.equals("CC")) {
                checkResult = checkResult + " \n" + ("[39%] Genetic Disprivilege: You Will Lose 2.5x As Much Weight On A Low Fat Diet.\n");
            } else {
                checkResult = checkResult + " \n" + ("[45%] Genetic Disprivilege: You Will Lose 2.5x As Much Weight On A Low Carb Diet.\n");
            }
        } else {
            if (rs1801282.equals("CC") && (rs1042713.equals("CC"))) {
                checkResult = checkResult + " \n" + ("[16%] Genetic Privilege: Any Diet Works For You.\n");
            } else {
                checkResult = checkResult + " \n" + ("[39%] Genetic Disprivilege: You Will Lose 2.5x As Much Weight On A Low Fat Diet.\n");
            }

        }
        //saved everything to checkResult to make it easier for later when we write the checkResult into a file
        System.out.println(checkResult);


    }
    public void saveResultsToFile(String file) throws IOException {
        File destFile = new File("result.txt");
        FileWriter fw = new FileWriter(destFile, true);
        fw.write(file + " results:  \n");
        fw.write("===========================");
        fw.write("\n\n" + output);
        fw.write("\n" + output2);
        fw.write(checkResult + "\n");
        fw.close();

    }

}