package com.unitri.comp;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SemanticAnalysis {

    private StringBuilder code = new StringBuilder();

    private void declaration ( Node node ) {

        if ( !Objects.equals( node.getType(), "declaration" ) ) {
            Log.error( "SemanticAnalysis", "Invalid node in declaration!" );
            return;
        }
        
        String type = type( node.getSon( 0 ) );

        List< Token > ids = ids( node.getSon( 1 ) );

        append( type );
    }

    private String type( Node node ) {

        if ( !Objects.equals( node.getType(), "type" ) ) {
            Log.error( "SemanticAnalysis", "Invalid node in type!" );
        }

        return node.getSon( 0 ).getToken().getImage();
    }

    private List< Token > ids( Node node ) {

        if ( !Objects.equals( node.getType(), "ids" ) ) {
            Log.error( "SemanticAnalysis", "Invalid node in ids!" );
        }

        List< Token > tokens = new ArrayList<>();

        tokens.add( node.getSon( 0 ).getToken() );
        tokens.addAll( ids2( node.getSon( 1 ) ) );

        return tokens;
    }

    private List< Token > ids2( Node node ) {

        if ( !Objects.equals( node.getType(), "ids2" ) ) {
            Log.error( "SemanticAnalysis", "Invalid node in ids2!" );
        }

        List< Token > tokens = new ArrayList<>();

        if ( node.getSons().size() > 0 ) {

            tokens.add( node.getSons().get( 0 ).getToken() );

            if ( node.getSons().get( 1 ).getSons().size() > 0 ) {

                tokens.addAll( ids2( node.getSons().get( 1 ) ) );
            }
        }

        return tokens;
    }

    private void append( String text ) {

        code.append( text + " " );
    }
}
