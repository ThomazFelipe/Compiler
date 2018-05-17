package com.unitri.comp;

public class SyntacticAnalysis {

    private Token token = new Token();
    private int index = -1;

    protected void commands() {

        nextToken();

        command();
        commands();
    }

    private void command() {

        if ( token.getImage().equals( "(" ) ) {

            nextToken();

            if ( Constants.TYPES.contains( token.getImage() ) ) {

                declaration();
            } else {

                switch ( token.getImage() ) {

                    case "=":

                        attribution();
                        break;
                    case "...":

                        loop();
                        break;
                    case "?":

                        condition();
                        break;
                    case ":<<":

                        input();
                        break;
                    default:
                        Log.error( "SyntacticAnalysis", "Expected identifier, '=', '...', '?' ou ':<<' but found: " + token.getImage() );
                }
            }

            if ( token.getImage().equals( ")" ) ) {

                nextToken();
            } else {

                Log.error( "SyntacticAnalysis", "Expected ')', but found: " + token.getImage() );
            }
        } else {
            Log.error( "SyntacticAnalysis", "Expected '(', but found: " + token.getImage() );
        }
    }

    private void declaration() {

        type();
        ids();
    }

    private void attribution() {

        if ( token.getImage().equals( "=" ) ) {

            nextToken();

            if ( token.getCategoryEnum().equals( CategorizationEnum.IDENTIFIER ) ) {

                nextToken();
                expression();
            } else {

                Log.error( "SyntacticAnalysis", "Expected identifier, but found: " + token.getImage() );
            }
        } else {

            Log.error( "SyntacticAnalysis", "Expected '=', but found: " + token.getImage() );
        }
    }

    private void loop() {

        if ( token.getImage().equals( "..." ) ) {

            nextToken();

            expression();
            commands();
        } else {

            Log.error( "SyntacticAnalysis", "Expected '...', but found: " + token.getImage() );
        }
    }

    private void condition() {

        if ( token.getImage().equals( "?" ) ) {

            nextToken();

            expression();

            if ( token.getImage().equals( "(" ) ) {

                nextToken();

                commands();

                if ( token.getImage().equals( ")" ) ) {

                    nextToken();
                    elseCondition();
                } else {

                    Log.error( "SyntacticAnalysis", "Expected ')', but found: " + token.getImage() );
                }
            } else {

                Log.error( "SyntacticAnalysis", "Expected '(', but found: " + token.getImage() );
            }
        } else {

            Log.error( "SyntacticAnalysis", "Expected '?', but found: " + token.getImage() );
        }
    }

    private void input() {

        if ( token.getImage().equals( ":<<" ) ) {

            nextToken();

            if ( token.getImage().equals( ")" ) ) {

                nextToken();
            } else {

                Log.error( "SyntacticAnalysis", "Expected ')', but found: " + token.getImage() );
            }
        }
    }

    private void elseCondition() {

        if ( token.getImage().equals( "(" ) ) {

            nextToken();
            commands();

            if ( token.getImage().equals( ")" ) ) {

                nextToken();
            } else {

                Log.error( "SyntacticAnalysis", "Expected ')', but found: " + token.getImage() );
            }
        }
    }

    private void type() {

        if ( Constants.TYPES.contains( token.getImage() ) ) {

            nextToken();
        } else {

            Log.error( "SyntacticAnalysis", "Expected 'int', 'real', 'string' ou 'bool', but found: " + token.getImage() );
        }
    }

    private void ids() {

        if ( token.getCategoryEnum().equals( CategorizationEnum.IDENTIFIER ) ) {

            nextToken();
            ids2();
        } else {

            Log.error( "SyntacticAnalysis", "Expected identifier, but found: " + token.getImage() );
        }
    }

    private void ids2() {

        if ( token.getCategoryEnum().equals( CategorizationEnum.IDENTIFIER ) ) {

            nextToken();
            ids2();
        }
    }

    private void expression() {

        if ( token.getImage().equals( "" ) ) { //TODO rever esta comparação

            operands();
        } else {

            if ( token.getImage().equals( "(" ) ) {

                nextToken();

                operator();
                expression();
                expression();

                if ( token.getImage().equals( ")" ) ) {

                    nextToken();
                } else {

                    Log.error( "SyntacticAnalysis", "Expected ')', but found: " + token.getImage() );
                }
            } else {

                Log.error( "SyntacticAnalysis", "Expected '(', but found: " + token.getImage() );
            }
        }
    }

    private void operands() {

    }

    private void operator() {

        if ( Constants.OPERATORS.contains( token.getImage() ) ) {

            nextToken();
        } else {

            Log.error( "SyntacticAnalysis", "Expected a operator, but found: " + token.getImage() );
        }
    }

    private void nextToken() {

        token = Main.tokens.get( ++index );
    }

    public Token getToken() {
        return token;
    }

    public void setToken( Token token ) {
        this.token = token;
    }
}
