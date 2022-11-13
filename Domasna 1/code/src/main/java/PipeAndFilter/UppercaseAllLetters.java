package PipeAndFilter;

public class UppercaseAllLetters implements Filter<String>{
    @Override
    public String execute(String input) {
        return input.toUpperCase();
    }
}
