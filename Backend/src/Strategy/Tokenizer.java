package src.Strategy;

import java.util.ArrayList;
import java.util.List;

public class Tokenizer {//OK
    private final List<String> tokens;
    private int index;

    public Tokenizer(String input) {
        this.tokens = tokenize(input);
        this.index = 0;
    }

    private List<String> tokenize(String input) {
        List<String> tokenList = new ArrayList<>();
        int pos = 0;

        while (pos < input.length()) {
            char c = input.charAt(pos);
            if (Character.isWhitespace(c)) {
                pos++;
            } else if (Character.isLetter(c)) {
                StringBuilder sb = new StringBuilder();
                while (pos < input.length() && Character.isLetterOrDigit(input.charAt(pos))) {
                    sb.append(input.charAt(pos++));
                }
                tokenList.add(sb.toString());
            } else if (Character.isDigit(c)) {
                StringBuilder sb = new StringBuilder();
                while (pos < input.length() && Character.isDigit(input.charAt(pos))) {
                    sb.append(input.charAt(pos++));
                }
                tokenList.add(sb.toString());
            } else {
                String op = String.valueOf(c);
                if ((c == '<' || c == '>' || c == '!' || c == '=') && pos + 1 < input.length() &&
                        input.charAt(pos + 1) == '=') {
                    op += "=";
                    pos++;
                }
                tokenList.add(op);
                pos++;
            }
        }
        System.out.println("Tokenized list: " + tokenList);
        return tokenList;
    }

    public String nextToken() {
        return hasNext() ? tokens.get(index++) : null;
    }

    public String peekToken() {
        return hasNext() ? tokens.get(index) : null;
    }

    public boolean hasNext() {
        return index < tokens.size();
    }
}
