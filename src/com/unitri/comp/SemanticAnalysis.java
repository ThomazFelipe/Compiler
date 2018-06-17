package com.unitri.comp;

import java.util.List;

public class SemanticAnalysis {

    private StringBuilder code = new StringBuilder();

    private void declaration ( Node node ) {

        String type = type( node.getSon( 1 ) );

        List< Token > ids = ids( node.getSon( 2 ) );
    }

    private String type( Node node ) {

        return node.getSon( 0 ).getToken().getImage();
    }

    private List< Token > ids( Node node ) {

        return null;
    }
}
