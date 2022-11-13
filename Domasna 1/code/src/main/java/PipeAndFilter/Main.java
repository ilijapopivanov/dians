package PipeAndFilter;
import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws IOException {
        Pipe<String> pipe = new Pipe<>();
        Pipe<String> pipe2 = new Pipe<>();
        Pipe<String> pipe3 = new Pipe<>();
        LowercaseAllLetters lowercaseAllLetters = new LowercaseAllLetters();
        UppercaseFirstLetter uppercaseFirstLetter = new UppercaseFirstLetter();
        UppercaseAllLetters uppercaseAllLetters = new UppercaseAllLetters();
        Transliterate transliterate =new Transliterate();
        pipe.addFilter(lowercaseAllLetters);
        pipe.addFilter(uppercaseFirstLetter);
        pipe2.addFilter(uppercaseAllLetters);
        pipe3.addFilter(lowercaseAllLetters);
        pipe3.addFilter(transliterate);
        pipe3.addFilter(uppercaseFirstLetter);

        String[] teststring;
        String line = " ";
        List<List<String>> glavnaLista = new LinkedList<>();

        Scanner scanner = new Scanner(new File("src/main/resources/map.csv"));
        scanner.useDelimiter(",");
        while(scanner.hasNextLine()){
            line = scanner.nextLine();
            teststring = line.split(",");
            if(!(teststring[5].isEmpty()))
                teststring[5]=teststring[5].substring(1,teststring[5].length()-1);

            if(Objects.equals(teststring[5], "cafe") || Objects.equals(teststring[5], "pub") || Objects.equals(teststring[5],"restaurant") || Objects.equals(teststring[5],"bar")){
                List<String> tmp = new LinkedList<>();
               teststring[0]=teststring[0].substring(1,teststring[0].length()-1);
                tmp.add(teststring[0]);
                tmp.add(pipe.runFilter(teststring[5]));
                if((!teststring[6].isEmpty())){
                    teststring[6]=teststring[6].substring(1,teststring[6].length()-1);
                    if(Pattern.matches(".*\\p{InCyrillic}.*", teststring[6]) && !(teststring[7].isEmpty())){
                        teststring[7]=teststring[7].substring(1,teststring[7].length()-1);
                        tmp.add(pipe.runFilter(teststring[7]));
                    }else{
                        tmp.add(pipe3.runFilter(teststring[6]));
                    }
                }
                if(!(teststring[27].isEmpty())) {
                    teststring[27] = teststring[27].substring(1, teststring[27].length() - 1);
                    tmp.add(teststring[27]);
                }
                if(!(teststring[teststring.length-1].isEmpty())){
                    int br=0;
                    for(int i= teststring.length-1;i>50;i--){
                        if(teststring[i].contains("POLYGON")){
                            br=i;
                            break;
                        }
                    }
                    String pomoshen = "";
                    for(int i=br;i< teststring.length;i++){
                        pomoshen+=teststring[i];
                    }
                    if(br==0) {
                        pomoshen = teststring[teststring.length - 1];
                    }
                    pomoshen=pomoshen.substring(1,pomoshen.length()-1);
                    tmp.add(pipe2.runFilter(pomoshen));
                }
               if(!(tmp.isEmpty())){
                   glavnaLista.add(tmp);
               }
            }
        }
        File file = new File("Output.csv");
         PrintStream printStream = new PrintStream(file);

        for(List<String> string : glavnaLista){
            printStream.println(string.toString());
        }
    }
}