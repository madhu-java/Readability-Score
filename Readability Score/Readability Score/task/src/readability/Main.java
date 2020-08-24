package readability;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        // System.out.print("Hello world!");
        String fileName = args[0];
        File file = new File(fileName);
        int numOfwords = 0;
        int numOfSentences = 0;
        int numOfchars = 0;
        double score = 0;
        int numOfSyallable = 0;
        int poly = 0;
        String input = "";

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                input = scanner.nextLine();
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        numOfSentences = input.split("[.?!]").length;
        numOfwords = input.split(" ").length;
        numOfchars = input.replace(" ", "").length();
       //calculating syllables and polysyllables
        for (String s : input.split(" ")) {
            int syllableNumInWord = 0;
            if (s.endsWith("e")) {
                s = s.substring(0, s.length() - 1);
            }
            if (s.length() == 1 && String.valueOf(s.charAt(0)).matches("[aeiouyAEIOUY]")) {
                numOfSyallable += 1;

            } else {
                if (String.valueOf(s.charAt(0)).matches("[aeiouyAEIOUY]")) {
                    numOfSyallable += 1;
                    syllableNumInWord += 1;

                }
                for (int i = 1; i < s.length(); i++) {
                    if (String.valueOf(s.charAt(i - 1)).matches("[aeiouyAEIOUY]") &&
                            String.valueOf(s.charAt(i)).matches("[aeiouyAEIOUY]")) {
                        numOfSyallable += 0;
                        syllableNumInWord += 0;

                    } else if (String.valueOf(s.charAt(i)).matches("[aeiouyAEIOUY]")) {
                        numOfSyallable += 1;
                        syllableNumInWord += 1;
                    }
                }
            }
            if (syllableNumInWord > 2) {
                poly += 1;
            }
        }

        System.out.println("Words: " + numOfwords);
        System.out.println("Sentences: " + numOfSentences);
        System.out.println("Characters: " + numOfchars);
        System.out.println("Syllables: " + numOfSyallable);
        System.out.println("Polysyllables: " + poly);
        //System.out.println("The score is: "+score);

        System.out.println("Enter the score you want to calculate (ARI, FK, SMOG, CL, all): ");
        Scanner scan = new Scanner(System.in);
        String scoreType = scan.nextLine();
        switch (scoreType) {
            case "ARI":
                aRI(numOfchars, numOfSentences, numOfwords);
                break;
            case "FK":
                fK(numOfSentences, numOfwords, numOfSyallable);
                break;
            case "SMOG":
                sMOG(numOfSentences, poly);
                break;
            case "CL":
                cL(numOfchars, numOfSentences, numOfwords);
                break;
            case "all":
                int a = aRI(numOfchars, numOfSentences, numOfwords);
                int b = fK(numOfSentences, numOfwords, numOfSyallable);
                int c = sMOG(numOfSentences, poly);
                int d = cL(numOfchars, numOfSentences, numOfwords);
                double average = (double) (a + b + c + d) / 4;
                System.out.printf("\nThis text should be understood in average by %.2f year olds.", average);
        }
    }
// caluculating score following different scientific approaches
    private static int fK(int numOfSentences, int numOfwords, int numOfSyallable) {
        double fkScore = 0.39 * ((double) numOfwords / numOfSentences) + 11.8 *
                ((double) numOfSyallable / numOfwords) - 15.59;
        int age = getAge(fkScore);
        System.out.printf("\nFlesch–Kincaid readability tests: %.2f (about %d year olds).", fkScore, age);
        return age;
    }

    private static int sMOG(int numOfSentences, int poly) {
        double smogScore = 1.043 * (Math.sqrt(poly * ((double) 30 / numOfSentences)) + 3.1291);
        int age = getAge(smogScore);
        System.out.printf("\nSimple Measure of Gobbledygook: %.2f  (about %d year olds).", smogScore, age);
        return age;
    }

    private static int cL(int numOfchars, int numOfSentences, int numOfwords) {
        // L is the average number of characters per 100 words and 
        //S is the average number of sentences per 100 words. Like all other indices
        //score=0.0588∗L−0.296∗S−15.8
        double l = (double) numOfchars / numOfwords * 100;
        double s = (double) numOfSentences / numOfwords * 100;
        double cLScore = (0.0588 * l) - (0.296 * s) - 15.8;
        int age = getAge(cLScore);
        System.out.printf("\nColeman–Liau index: %.2f (about %d year olds).", cLScore, age);
        return age;
    }


    private static int getAge(double score) {
        int[] array = new int[]{6, 7, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 24, 24};
        int level = (int) Math.ceil(score);
        int age = array[Math.min(14, Math.max(1, level)) - 1];
        return age;
    }
    // System.out.println("Automated Readability Index: %d (about %d year olds.)",score,ariScore);

    private static int aRI(int numOfchars, int numOfSentences, int numOfwords) {
        double score = 4.71 * ((double) numOfchars / numOfwords) + 0.5 *
                ((double) numOfwords / numOfSentences) - 21.43;
        int age = getAge(score);
        System.out.printf("\nAutomated Readability Index: %.2f (about %d year olds.)", score, age);
        return age;

    }
}

