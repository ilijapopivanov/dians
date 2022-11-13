package PipeAndFilter;

public class UppercaseFirstLetter implements Filter<String>{
    @Override
    public String execute(String input) {
        String s1 = input.substring(0,1).toUpperCase();
        String s2 = input.substring(1);
        return s1 + s2;
    }
}
