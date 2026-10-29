package com.example.calculatorapp;
public class ExpressionValidator {

    public static boolean isValid(String expression) {
        if (expression == null || expression.trim().isEmpty()) {
            return false;
        }

        try {
            Parser parser = new Parser(expression);
            parser.parseExpression();

            // Η έκφραση πρέπει να έχει καταναλωθεί ολόκληρη.
            return parser.isAtEnd();
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private static class Parser {
        private final String input;
        private int pos = 0;

        Parser(String input) {
            this.input = input;
        }
        boolean isAtEnd() {
            skipSpaces();
            return pos == input.length();
        }

        private void skipSpaces() {
            while (pos < input.length()
                    && Character.isWhitespace(input.charAt(pos))) {
                pos++;
            }
        }

        private boolean match(char c) {
            skipSpaces();

            if (pos < input.length() && input.charAt(pos) == c) {
                pos++;
                return true;
            }

            return false;
        }

        void parseExpression() {
            parseTerm();

            while (true) {
                if (match('+') || match('-')) {
                    parseTerm();
                } else {
                    break;
                }
            }
        }

        private void parseTerm() {
            parseUnary();

            while (true) {
                if (match('*') || match('/')) {
                    parseUnary();
                } else {
                    break;
                }
            }
        }

        private void parseUnary() {
            if (match('+') || match('-')) {
                parseUnary();
            } else {
                parsePower();
            }
        }

        private void parsePower() {
            parsePrimary();

            // Υποστηρίζει εκθέτες, π.χ. 2^3.
            if (match('^')) {
                parseUnary();
            }
        }

        private void parsePrimary() {
            skipSpaces();

            // 1. Έλεγχος για παρενθέσεις
            if (match('(')) {
                parseExpression();

                if (!match(')')) {
                    throw new IllegalArgumentException(
                            "Missing closing parenthesis"
                    );
                }

                return;
            }

            // 2. Έλεγχος για τετραγωνική ρίζα (√)
            if (match('√')) {
                parseUnary();
                return;
            }

            // 3. Έλεγχος για συναρτήσεις ή σταθερές (γράμματα)
            if (pos < input.length() && Character.isLetter(input.charAt(pos))) {
                int start = pos;
                while (pos < input.length() && Character.isLetter(input.charAt(pos))) {
                    pos++;
                }
                String word = input.substring(start, pos).toLowerCase();

                // Έλεγχος σταθερών (pi, π, e)
                if (word.equals("pi") || word.equals("π") || word.equals("e")) {
                    return;
                }

                // Έλεγχος επιστημονικών συναρτήσεων (sin, cos, tan, log, ln)
                if (word.equals("sin") || word.equals("cos") || word.equals("tan")
                        || word.equals("log") || word.equals("ln")) {
                    skipSpaces();
                    if (!match('(')) {
                        throw new IllegalArgumentException("Expected '(' after function " + word);
                    }
                    parseExpression();
                    if (!match(')')) {
                        throw new IllegalArgumentException("Missing closing parenthesis for function " + word);
                    }
                    return;
                }

                throw new IllegalArgumentException("Unknown identifier: " + word);
            }

            // 4. Έλεγχος για αριθμούς
            parseNumber();
        }

        private void parseNumber() {
            skipSpaces();

            int start = pos;
            boolean hasDigit = false;
            boolean hasDot = false;

            while (pos < input.length()) {
                char c = input.charAt(pos);

                if (Character.isDigit(c)) {
                    hasDigit = true;
                    pos++;
                } else if (c == '.' && !hasDot) {
                    hasDot = true;
                    pos++;
                } else {
                    break;
                }
            }

            if (!hasDigit) {
                throw new IllegalArgumentException(
                        "Expected a number at position " + start
                );
            }
        }
    }
}

