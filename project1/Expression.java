// Starter code for Project 1

// Change this to your NetId
package dsa;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.LinkedList;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.File;
import java.lang.Math;

/** Class to store a node of expression tree
 For each internal node, element contains a binary operator
 List of operators: +|*|-|/|%|^
 Other tokens: (|)
 Each leaf node contains an operand (long integer)
 */

public class Expression {
    public enum TokenType {  // NIL is a special token that can be used to mark bottom of stack
        PLUS, TIMES, MINUS, DIV, MOD, POWER, OPEN, CLOSE, NIL, NUMBER
    }

    public static class Token {
        TokenType token;
        int priority; // for precedence of operator
        Long number;  // used to store number of token = NUMBER
        String string;

        Token(TokenType op, int pri, String tok) {
            token = op;
            priority = pri;
            number = null;
            string = tok;
        }

        // Constructor for number.  To be called when other options have been exhausted.
        Token(String tok) {
            token = TokenType.NUMBER;
            number = Long.parseLong(tok);
            string = tok;
        }

        boolean isOperand() { return token == TokenType.NUMBER; }

        public long getValue() {
            return isOperand() ? number : 0;
        }

        public String toString() { return string; }
    }

    Token element;
    Expression left, right;

    // Create token corresponding to a string
    // tok is "+" | "*" | "-" | "/" | "%" | "^" | "(" | ")"| NUMBER
    // NUMBER is either "0" or "[-]?[1-9][0-9]*
    static Token getToken(String tok) {  // To do
        Token result;
        switch(tok) {
            case "+":
                result = new Token(TokenType.PLUS, 0, tok);
                break;
            case "-":
                result = new Token(TokenType.MINUS, 0, tok);
                break;
            case "*":
                result = new Token(TokenType.TIMES, 1, tok);
                break;
            case "/":
                result = new Token(TokenType.DIV, 1, tok);
                break;
            case "%":
                result = new Token(TokenType.MOD, 1, tok);
                break;
            case "^":
                result = new Token(TokenType.POWER, 2, tok);
                break;
            case "(":
                result = new Token(TokenType.OPEN, -1, tok);
                break;
            case ")":
                result = new Token(TokenType.CLOSE, -1, tok);
                break;
            // Complete rest of this method
            default:
                result = new Token(tok);
                break;
        }
        return result;
    }

    private static long evaluateOperator(Token operator, long operand1, long operand2) {
        switch (operator.toString()) {
            case "+":
                return operand1 + operand2;
            case "-":
                return operand1 - operand2;
            case "*":
                return operand1 * operand2;
            case "/":
                return operand1 / operand2;
            case "%":
                return operand1 % operand2;
            case "^":
                return (long) Math.pow(operand1, operand2);
            default:
                return 0;
        }
    }

    private Expression() {
        element = null;
    }

    private Expression(Token oper, Expression left, Expression right) {
        this.element = oper;
        this.left = left;
        this.right = right;
    }

    private Expression(Token num) {
        this.element = num;
        this.left = null;
        this.right = null;
    }

    // Given a list of tokens corresponding to an infix expression,
    // return the expression tree corresponding to it.
    public static Expression infixToExpression(List<Token> exp) {
        Deque<Expression> expressionStack = new ArrayDeque<>();
        Deque<Token> stack = new ArrayDeque<>();

        for(Token token: exp) {
            if(token.isOperand()) expressionStack.push(new Expression(token));
            else if(token.toString().equals("(")) stack.push(token);
            else if(token.toString().equals(")")) {
                while (!stack.peek().toString().equals("(")) pushToStack(stack.pop(), expressionStack);
                stack.pop();
            } else {
                while(!stack.isEmpty() && !stack.peek().toString().equals("(") &&
                        stack.peek().priority >= token.priority) {
                    pushToStack(stack.pop(), expressionStack);
                }
                stack.push(token);
            }
        }

        while(!stack.isEmpty()) pushToStack(stack.pop(), expressionStack);

        return expressionStack.pop();
    }

    private static void pushToStack(Token token, Deque<Expression> expressionStack) {
        Expression right = expressionStack.pop();
        Expression left = expressionStack.pop();
        expressionStack.push(new Expression(token, left, right));
    }

    // Given a list of tokens corresponding to an infix expression,
    // return its equivalent postfix expression as a list of tokens.
    public static List<Token> infixToPostfix(List<Token> exp) {
        List<Token> output = new ArrayList<>();
        Deque<Token> stack = new ArrayDeque<>();

        for(Token token: exp) {
            if(token.isOperand()) output.add(token);
            else if(token.toString().equals("(")) stack.push(token);
            else if(token.toString().equals(")")) {
                while (!stack.peek().toString().equals("(")) output.add(stack.pop());
                stack.pop();
            } else {
                while(!stack.isEmpty() && !stack.peek().toString().equals("(") &&
                        stack.peek().priority >= token.priority) {
                    output.add(stack.pop());
                }
                stack.push(token);
            }
        }

        while(!stack.isEmpty()) output.add(stack.pop());
        return output;
    }

    // Given a postfix expression, evaluate it and return its value.
    public static long evaluatePostfix(List<Token> exp) {
        Deque<Long> stack = new ArrayDeque<>();

        for(Token token: exp) {
            if(token.isOperand()) stack.push(token.getValue());
            else {
                long operand1 = stack.pop();
                long operand2 = stack.pop();
                stack.push(evaluateOperator(token, operand2, operand1));
            }
        }

        return stack.pop();
    }

    // Given an expression tree, evaluate it and return its value.
    public static long evaluateExpression(Expression tree) {
        if(tree == null) return 0;
        else if(tree.left == null && tree.right == null) return tree.element.getValue();

        long left = evaluateExpression(tree.left);
        long right = evaluateExpression(tree.right);
        return evaluateOperator(tree.element, left, right);
    }

    // sample main program for testing
    public static void main(String[] args) throws FileNotFoundException {
        Scanner in;

        if (args.length > 0) {
            File inputFile = new File(args[0]);
            in = new Scanner(inputFile);
        } else {
            in = new Scanner(System.in);
        }

        int count = 0;
        while(in.hasNext()) {
            String s = in.nextLine();
            List<Token> infix = new LinkedList<>();
            Scanner sscan = new Scanner(s);
            int len = 0;
            while(sscan.hasNext()) {
                infix.add(getToken(sscan.next()));
                len++;
            }
            if(len > 0) {
                count++;
                System.out.println("Expression number: " + count);
                System.out.println("Infix expression: " + infix);
                Expression exp = infixToExpression(infix);
                List<Token> post = infixToPostfix(infix);
                System.out.println("Postfix expression: " + post);
                long pval = evaluatePostfix(post);
                long eval = evaluateExpression(exp);
                System.out.println("Postfix eval: " + pval + " Exp eval: " + eval + "\n");
            }
        }
    }
}