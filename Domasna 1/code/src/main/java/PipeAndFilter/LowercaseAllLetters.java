package PipeAndFilter;

public class LowercaseAllLetters implements Filter<String>{
    @Override
    public String execute(String input) {
        return input.toLowerCase();
    }
}
