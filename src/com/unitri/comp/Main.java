package com.unitri.comp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Main {

    protected static List< Token > tokens = new ArrayList<>();
    protected static List< Symbol > symbols = new ArrayList<>();
    private static Long countLine = 1L;

    public static void main( String[] args )
            throws IOException {

        BufferedReader bufferedReader = new BufferedReader( new FileReader( new File( "src/code.txt" ) ) );

        tokens = categorizeCode( bufferedReader );
        tokens.add( new Token().image( "$" )
                .line( tokens.get( tokens.size() - 1 ).getLine() )
                .column( tokens.get( tokens.size() - 1 ).getColumn() + 1 )
                .categorizationEnum( CategorizationEnum.END_OF_CHAIN ) );

        tokens = tokens.stream()
                .sorted( Comparator.comparing( Token::getLine ) )
                .collect(Collectors.toList());


        tokens.forEach( token -> Log.info( "Main", "Image: " + token.getImage()
                + ",   Category: " + token.getCategoryEnum()
                + ",   Line: " + token.getLine()
                + ",   Column: " + token.getColumn()
                + ",   Index: " + token.getIndex() ) );

        System.out.println();

        symbols.forEach( symbol -> Log.info( "Main", "Image: " + symbol.getImage() ) );

        SyntacticAnalysis syntacticAnalysis = new SyntacticAnalysis();
        syntacticAnalysis.commands();

        if ( syntacticAnalysis.getToken().getImage().equals( "$" ) ) {

            Log.info( "Main", "Success" );
        }
    }

    private static List< Token > categorizeCode( BufferedReader bufferedReader )
            throws IOException {

        Token token;
        String line = bufferedReader.readLine();

        while( !Objects.equals( line, null ) ) {

            for ( String lexeme : splitLine( line ) ) {

                if ( lexeme.contains( "#" ) )
                    break;

                token = new Token()
                        .image( lexeme )
                        .line( countLine );

                if ( Constants.RESERVED_WORD.contains( lexeme ) ) {

                    tokens.add( token.categorizationEnum( CategorizationEnum.RESERVED_WORD )
                            .column( ( long ) line.indexOf( lexeme ) + 1 )
                            .index( -1L ) );
                    continue;
                }

                if ( Constants.DELIMITERS.contains( lexeme ) ) {

                    tokens.add( token.categorizationEnum( CategorizationEnum.DELIMITER )
                            .column( ( long ) line.indexOf( lexeme ) + 1 )
                            .index( -1L ) );
                    continue;
                }

                if ( Constants.OPERATORS.contains( lexeme ) ) {

                    tokens.add( token.categorizationEnum( CategorizationEnum.OPERATOR )
                            .column( ( long ) line.indexOf( lexeme ) + 1 )
                            .index( -1L ) );
                    continue;
                }

                if ( lexeme.matches( Constants.STRING )
                        || lexeme.matches( Constants.INTEGER )
                        || lexeme.matches( Constants.DECIMAL ) ) {

                    Long index;
                    Symbol symbol = new Symbol().image( lexeme );

                    List< String > images = symbols.stream()
                            .map( Symbol::getImage )
                            .collect( Collectors.toList() );

                    if ( !images.contains( symbol.getImage() ) ) {
                        symbols.add( symbol );

                        index = ( long ) images.size();
                    } else {
                        index = ( long ) images.indexOf( symbol.getImage() );
                    }

                    tokens.add( token.categorizationEnum( CategorizationEnum.IDENTIFIER )
                            .column( ( long ) line.indexOf( lexeme ) + 1 )
                            .index( index + 1 ) );
                    continue;
                }

                if ( !lexeme.equals( "" ) ) {

                    tokens = new ArrayList<>();
                    Log.error( "Main", "Cannot resolve: " + lexeme + " at line: " + countLine
                            + ", column: " + ( line.indexOf( lexeme ) + 1 ) + "\n" );
                    break;
                }
            }

            countLine++;
            line = bufferedReader.readLine();
        }

        return tokens;
    }

    private static List< String > splitLine( String line ) {

        if ( line.contains( "\"" ) ) {

            String cls = line.substring( line.indexOf( "\"" ) + 1,
                    line.lastIndexOf( "\"" ) );

            String lineResult = line.replace( "\"" + cls + "\"", "" );

            tokens.add( new Token().categorizationEnum( CategorizationEnum.CLS )
                    .image( cls )
                    .line( countLine )
                    .column( ( long ) line.indexOf( "\"" ) + 1 )
                    .index( -1L ) );

            return List.of( lineResult.split( " " ) );
        }

        return List.of( line.split( " " ) );
    }
}