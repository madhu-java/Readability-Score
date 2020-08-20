package readability;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        // System.out.print("Hello world!");
        String fileName = args[0];
        File file = new File(fileName);
        int numOfwords = 0;
        int numOfSentences =0;
        int numOfchars = 0;
        double score = 0;
        try (Scanner scanner = new Scanner(file)) {
            while(scanner.hasNext()){
                String input = scanner.nextLine();
                String[] sentence = input.split("[.?!]");
                numOfSentences += sentence.length;
                for (String s : sentence) {
                    numOfwords += s.trim().split(" ").length;
                }
                for(char c : input.toCharArray())
                    if(c!=' ') {
                        numOfchars += 1;
                    }
            }

//            String input = scanner.nextLine();
//            String[] sentence = input.split("[.?!]");
//            int numOfwords = 0;
//            for (String s : sentence) {
//                numOfwords += s.split(" ").length;
//            }
//            double score = 4.71 * ()
//       if((numOfwords/sentence.length)>10){
//           System.out.println("HARD");
//       }else{
//            System.out.print("EASY");
//        }
//        if(input.length()>100){
//            System.out.print("HARD");
//        }else{
//             System.out.print("EASY");
//        }
        }catch(FileNotFoundException e){
            System.out.println(e.getMessage());
        }
        score = 4.71 * ((double)numOfchars/numOfwords)+0.5 *
                ((double)numOfwords/numOfSentences)-21.43;
        System.out.println("Words: "+numOfwords);
        System.out.println("Sentences: "+numOfSentences);
        System.out.println("Characters: "+numOfchars);
        System.out.println("The score is: "+score);
        System.out.print("This text should be understood by "+
                adeGroup(score)+" year olds");

//        System.out.println("adgrop "+adeGroup(score));
//        System.out.println("ceil score "+(int) Math.ceil(score));
    }

    private static String adeGroup(double score) {
        switch((int) Math.ceil(score)){
            case 1:
                return "5-6";
            case  2:
                return "6-7";
            case  3:
                return "7-9";
            case  4:
                return "9-10";
            case 5:
                return "10-11";
            case 6:
                return "11-12";
            case  7:
                return "12-13";
            case  8:
                return "13-14";
            case  9:
                return "14-15";
            case 10:
                return "15-16";
            case  11:
                return "16-17";
            case  12:
                return "17-18";
            case 13:
                return "18-24";
            case 14:
                return "24+";
            default: return "";
        }
    }
}

