package com.unitri.comp;

import java.util.List;

class Constants {

    final static List< String > RESERVED_WORD = List.of( "real", "int", "string", "..." );

    final static List< String > DELIMITERS = List.of( ")", "(", ",", "{", "}", ";", "[", "]", ":" );

    final static List< String > OPERATORS = List.of( "&", "?", ":<<", ":>>", "=", ">", "<", "<=", "+", "*" );

    final static List< String > BOOLEAN = List.of( "true", "false" );

    final static String INTEGER = "^\\d+$";

    final static String DECIMAL = "^\\d+\\.\\d+$";

    final static String STRING = "^[a-zA-Z][a-zA-Z0-9{., _}]*$";

    final static List< String > TYPES = List.of( "int", "real", "string", "bool" );
}
