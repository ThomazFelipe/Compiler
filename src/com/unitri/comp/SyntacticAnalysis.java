package com.unitri.comp;

public class SyntacticAnalysis {

    private Token token = new Token();
    private int index = -1;
    private Node root;

    protected void start() {

        nextToken();
        root = commands();

        if ( token.getCategoryEnum().equals( CategorizationEnum.END_OF_CHAIN ) ) {

            Log.info( "Main", "Success" );
        }
    }

    private Node commands() {

        Node node = new Node( "commands" );

        node.addSon( new Node( token ) );

        node.addSon( command() );
        node.addSon( commands() );

        return node;
    }

    private Node command() {

        Node node = new Node( "command" );

        if ( token.getImage().equals( "(" ) ) {

            node.addSon( new Node( token ) );
            nextToken();

            if ( Constants.TYPES.contains( token.getImage() ) ) {

                node.addSon( declaration() );
            } else {

                switch ( token.getImage() ) {

                    case "=":

                        node.addSon( attribution() );
                        break;
                    case "...":

                        node.addSon( loop() );
                        break;
                    case "?":

                        node.addSon( condition() );
                        break;
                    case ":<<":

                        node.addSon( input() );
                        break;
                    default:
                        Log.error( "SyntacticAnalysis", "Expected identifier, '=', '...', '?' ou ':<<' but found: " + token.getImage() );
                }
            }

            if ( token.getImage().equals( ")" ) ) {

                node.addSon( new Node( token ) );
                nextToken();
            } else {

                Log.error( "SyntacticAnalysis", "Expected ')', but found: " + token.getImage() );
            }
        } else {
            Log.error( "SyntacticAnalysis", "Expected '(', but found: " + token.getImage() );
        }

        return node;
    }

    private Node declaration() {

        Node node = new Node( "declaration" );
        node.addSon( type() );
        node.addSon( ids() );

        return node;
    }

    private Node attribution() {

        Node node = new Node( "attribution" );

        if ( token.getImage().equals( "=" ) ) {

            node.addSon( new Node( token ) );
            nextToken();

            if ( token.getCategoryEnum().equals( CategorizationEnum.IDENTIFIER ) ) {

                node.addSon( new Node( token ) );
                nextToken();
                node.addSon( expression() );
            } else {

                Log.error( "SyntacticAnalysis", "Expected identifier, but found: " + token.getImage() );
            }
        } else {

            Log.error( "SyntacticAnalysis", "Expected '=', but found: " + token.getImage() );
        }

        return node;
    }

    private Node loop() {

        Node node = new Node( "loop" );

        if ( token.getImage().equals( "..." ) ) {

            node.addSon( new Node( token ) );
            nextToken();

            node.addSon( expression() );
            node.addSon( commands() );
        } else {

            Log.error( "SyntacticAnalysis", "Expected '...', but found: " + token.getImage() );
        }

        return node;
    }

    private Node condition() {

        Node node = new Node( "condition" );

        if ( token.getImage().equals( "?" ) ) {

            node.addSon( new Node( token ) );
            nextToken();

            node.addSon( expression() );

            if ( token.getImage().equals( "(" ) ) {

                node.addSon( new Node( token ) );
                nextToken();

                node.addSon( commands() );

                if ( token.getImage().equals( ")" ) ) {

                    node.addSon( new Node( token ) );
                    nextToken();
                    node.addSon( elseCondition() );
                } else {

                    Log.error( "SyntacticAnalysis", "Expected ')', but found: " + token.getImage() );
                }
            } else {

                Log.error( "SyntacticAnalysis", "Expected '(', but found: " + token.getImage() );
            }
        } else {

            Log.error( "SyntacticAnalysis", "Expected '?', but found: " + token.getImage() );
        }

        return node;
    }

    private Node input() {

        Node node = new Node( "input" );

        if ( token.getImage().equals( ":<<" ) ) {

            node.addSon( new Node( token ) );
            nextToken();

            if ( token.getImage().equals( ")" ) ) {

                node.addSon( new Node( token ) );
                nextToken();
            } else {

                Log.error( "SyntacticAnalysis", "Expected ')', but found: " + token.getImage() );
            }
        }

        return node;
    }

    private Node elseCondition() {

        Node node = new Node( "elseCondition" );

        if ( token.getImage().equals( "(" ) ) {

            node.addSon( new Node( token ) );
            nextToken();
            node.addSon( commands() );

            if ( token.getImage().equals( ")" ) ) {

                node.addSon( new Node( token ) );
                nextToken();
            } else {

                Log.error( "SyntacticAnalysis", "Expected ')', but found: " + token.getImage() );
            }
        }

        return node;
    }

    private Node type() {

        Node node = new Node( "type" );

        if ( Constants.TYPES.contains( token.getImage() ) ) {

            node.addSon( new Node( token ) );
            nextToken();
        } else {

            Log.error( "SyntacticAnalysis", "Expected 'int', 'real', 'string' ou 'bool', but found: " + token.getImage() );
        }

        return node;
    }

    private Node ids() {

        Node node = new Node( "ids" );

        if ( token.getCategoryEnum().equals( CategorizationEnum.IDENTIFIER ) ) {

            node.addSon( new Node( token ) );
            nextToken();
            node.addSon( ids2() );
        } else {

            Log.error( "SyntacticAnalysis", "Expected identifier, but found: " + token.getImage() );
        }

        return node;
    }

    private Node ids2() {

        Node node = new Node( "ids2" );

        if ( token.getCategoryEnum().equals( CategorizationEnum.IDENTIFIER ) ) {

            node.addSon( new Node( token ) );
            nextToken();
            node.addSon( ids2() );
        }

        return node;
    }

    private Node expression() {

        Node node = new Node( "expression" );

        if ( token.getImage().equals( "" ) ) { //TODO revision comparision because CLI, CLS, CLR, CLL and ID

            node.addSon( operands() );
        } else {

            if ( token.getImage().equals( "(" ) ) {

                node.addSon( new Node( token ) );
                nextToken();

                node.addSon( operator() );
                node.addSon( expression() );
                node.addSon( expression() );

                if ( token.getImage().equals( ")" ) ) {

                    nextToken();
                } else {

                    Log.error( "SyntacticAnalysis", "Expected ')', but found: " + token.getImage() );
                }
            } else {

                Log.error( "SyntacticAnalysis", "Expected '(', but found: " + token.getImage() );
            }
        }

        return node;
    }

    private Node operands() {

        Node node = new Node( "operands" ); //TODO revision this method
        return node;
    }

    private Node operator() {

        Node node = new Node( "operator" );

        if ( Constants.OPERATORS.contains( token.getImage() ) ) {

            node.addSon( new Node( token ) );
            nextToken();
        } else {

            Log.error( "SyntacticAnalysis", "Expected a operator, but found: " + token.getImage() );
        }

        return node;
    }

    private void nextToken() {

        token = Main.tokens.get( ++index );
    }

    Token getToken() {
        return token;
    }

    public void setToken( Token token ) {
        this.token = token;
    }

    public void showTree() {

        showNode( root, "   " );
    }

    private void showNode( Node node, String space ) {

        System.out.println( space + node );

        for ( Node son : node.getSons() ) {
            showNode( son, space + "   " );
        }
    }
}
