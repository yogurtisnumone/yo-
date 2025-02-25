package src.Strategy;

import java.util.ArrayList;
import java.util.List;

import src.Position.Direction;

public class Parser {//OK
    private final Tokenizer tokenizer;

    public Parser(String input) {
        this.tokenizer = new Tokenizer(input);
    }

    public static Statement parse(String input) {
        return new Parser(input).parse();
    }

    public Statement parse() {
        List<Statement> statements = new ArrayList<>();
        while (tokenizer.hasNext()) {
            statements.add(parseStatement());
        }
        return new BlockStatement(statements);
    }

    private boolean isVariable(String token) {
        return token.matches("[a-zA-Z_][a-zA-Z0-9_]*");
    }

    private Statement parseStatement() {
        if (tokenizer.peekToken().equals("if")) {
            return parseIf();
        } else if (tokenizer.peekToken().equals("else")) {
            tokenizer.nextToken();
            if (tokenizer.peekToken().equals("if")) {
                return parseIf();
            } else {
                return parseElse();
            }
        } else if (tokenizer.peekToken().equals("while")) {
            return parseWhile();
        } else if (tokenizer.peekToken().equals("{")) {
            return parseBlock();
        } else if (tokenizer.peekToken().equals("move") || tokenizer.peekToken().equals("shoot") || tokenizer.peekToken().equals("done")) {
            return parseAction();
        } else if (isVariable(tokenizer.peekToken())) {
            return parseAssignment();
        } else {
            throw new RuntimeException("Unexpected token in statement: " + tokenizer.peekToken());
        }
    }

    private Statement parseIf() {
        tokenizer.nextToken(); // ข้าม "if"

        if (!tokenizer.peekToken().equals("(")) {
            throw new RuntimeException("Expected '(' after 'if', but found: " + tokenizer.peekToken());
        }
        tokenizer.nextToken(); // ข้าม '('
        Expression condition = parseExpression();

        if (!tokenizer.peekToken().equals(")")) {
            throw new RuntimeException("Expected ')' after condition, but found: " + tokenizer.peekToken());
        }
        tokenizer.nextToken(); // ข้าม ')'

        Statement thenBranch;
        if (tokenizer.peekToken().equals("then")) {
            tokenizer.nextToken(); // ข้าม "then"
            thenBranch = parseStatement();
        } else if (tokenizer.peekToken().equals("{")) {
            thenBranch = parseBlock();
        } else {
            throw new RuntimeException("Expected 'then' or '{' after if condition, found: " + tokenizer.peekToken());
        }

        Statement elseBranch = null;
        if (tokenizer.peekToken().equals("else")) {
            tokenizer.nextToken(); // ข้าม "else"
            if (tokenizer.peekToken().equals("{")) {
                elseBranch = parseBlock();
            } else {
                elseBranch = parseStatement();
            }
        }

        return new IfStatement(condition, thenBranch, elseBranch);
    }

    private Statement parseElse() {
        if (tokenizer.peekToken().equals("{")) {
            return parseBlock();
        } else if (tokenizer.peekToken().equals("done")) {
            tokenizer.nextToken();
            return new DoneCommand();
        } else {
            return parseStatement();
        }
    }

    private Statement parseWhile() {
        tokenizer.nextToken();

        if (!tokenizer.peekToken().equals("(")) {
            throw new RuntimeException("Expected '(' after 'while', but found: " + tokenizer.peekToken());
        }
        tokenizer.nextToken();
        Expression condition = parseExpression();

        if (!tokenizer.peekToken().equals(")")) {
            throw new RuntimeException("Expected ')' after condition, but found: " + tokenizer.peekToken());
        }
        tokenizer.nextToken();
        if (!tokenizer.peekToken().equals("{")) {
            throw new RuntimeException("Expected '{' after while condition, but found: " + tokenizer.peekToken());
        }
        Statement body = parseBlock();

        return new WhileStatement(condition, (BlockStatement) body);
    }

    private Statement parseBlock() {
        if (!tokenizer.peekToken().equals("{")) {
            throw new RuntimeException("Expected '{' at the beginning of block, but found: " + tokenizer.peekToken());
        }
        tokenizer.nextToken();
        List<Statement> statements = new ArrayList<>();
        while (!tokenizer.peekToken().equals("}")) {
            Statement stmt = parseStatement();
            if (stmt != null) {
                statements.add(stmt);
            }
        }
        tokenizer.nextToken();
        return new BlockStatement(statements);
    }

    private Statement parseAction() {
        String action = tokenizer.nextToken();
        return switch (action) {
            case "move" -> {
                String directionString = tokenizer.nextToken();
                Direction direction = Direction.valueOf(directionString.toUpperCase());
                yield new MoveCommand(direction);
            }
            case "shoot" -> {
                String directionString = tokenizer.nextToken();
                Direction direction = Direction.valueOf(directionString.toUpperCase());
                Expression power = parseExpression();
                yield new ShootCommand(direction, power);
            }
            case "done" -> new DoneCommand();
            default -> throw new RuntimeException("Unknown action: " + action);
        };

    }
    private Statement parseAssignment() {
        String varName = tokenizer.nextToken();
        if (!tokenizer.hasNext()) {
            throw new RuntimeException("Unexpected end of input after variable: " + varName);
        }
        String next = tokenizer.nextToken();
        if (!next.equals("=")) {
            parseExpression();
        }
        Expression value = parseExpression();
        return new AssignmentStatement(varName, value);

    }

    private Expression parseExpression() {
        Expression left = parseTerm();
        while (tokenizer.hasNext()) {
            String operator = tokenizer.peekToken();
            if (!operator.equals("+") && !operator.equals("-")) break;
            tokenizer.nextToken();
            Expression right = parseTerm();
            left = new BinaryExpression(left, operator, right);
        }
        return left;
    }
    private Expression parseTerm() {
        Expression left = parseFactor();
        while (tokenizer.hasNext()) {
            String operator = tokenizer.peekToken();
            if (!operator.equals("*") && !operator.equals("/") && !operator.equals("%")) break;
            tokenizer.nextToken();
            Expression right = parseFactor();
            left = new BinaryExpression(left, operator, right);
        }
        return left;
    }
    private Expression parseFactor() {
        Expression left = parsePower();
        while (tokenizer.hasNext() && tokenizer.peekToken().equals("^")) {
            tokenizer.nextToken();
            if (!tokenizer.hasNext()) {
                throw new RuntimeException("Expected exponent after '^', but found end of input.");
            }
            Expression right = parseFactor();
            left = new BinaryExpression(left, "^", right);
        }
        return left;
    }

    private Expression parsePower() {
        if (tokenizer.peekToken().equals("(")) {
            tokenizer.nextToken();
            Expression ex = parseExpression();

            if (!tokenizer.peekToken().equals(")")) {
                throw new RuntimeException("Expected ')' but found: " + tokenizer.peekToken());
            }
            tokenizer.nextToken();
            return ex;
        }
        String token = tokenizer.nextToken();
        if (token.matches("\\d+")) {
            return new NumberExpression(Integer.parseInt(token));
        } else if (token.matches("[a-zA-Z_][a-zA-Z0-9_]*")) {
            return switch (token) {
                case "ally", "opponent" -> new InfoExpression(token);
                case "nearby" -> {
                    String directionString = tokenizer.nextToken();
                    Direction direction = Direction.valueOf(directionString.toUpperCase());
                    yield new InfoExpression("nearby", direction);
                }
                case "random" -> new SpecialExpression("random");
                case "budget" -> new SpecialExpression("budget");
                case "row" -> new SpecialExpression("row");
                case "col" -> new SpecialExpression("col");
                case "int" -> new SpecialExpression("int");
                case "maxbudget" -> new SpecialExpression("maxbudget");
                case "spawnsleft" -> new SpecialExpression("spawnsleft");
                default -> new VariableExpression(token);
            };
        }

        throw new RuntimeException("Unexpected token: " + token);
    }
}
