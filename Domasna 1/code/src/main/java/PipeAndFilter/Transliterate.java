package PipeAndFilter;

import java.util.List;

public class Transliterate implements Filter<String> {
    public String execute(String input) {
        char[] abcCyr =   {' ','а','б','в','г','д','ѓ','е','ж','з','ѕ','и','ј','к','л','љ','м','н','њ','о','п','р','с','т','ќ','у','ф','х','ц','ч','џ','ш','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','0','1','2','3','4','5','6','7','8','9'};
        String[] abcLat = {" ","a","b","v","g","d","gj","e","zh","z","dz","i","j","k","l","lj","m","n","nj","o","p","r","s","t","kj","u","f","h","c","ch","dzh","sh","a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z","0","1","2","3","4","5","6","7","8","9"};
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            for (int x = 0; x < abcCyr.length; x++ ) {
                if (input.charAt(i) == abcCyr[x]) {
                    builder.append(abcLat[x]);
                }
            }
        }
        return builder.toString();
    }
}
