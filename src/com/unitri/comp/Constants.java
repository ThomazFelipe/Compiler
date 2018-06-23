package com.unitri.comp;

import java.util.List;

public class Constants {

    protected final static List< String > RESERVED_WORD = List.of( "real", "int", "string", "..." );

    protected final static List< String > DELIMITERS = List.of( ")", "(", ",", "{", "}", ";", "[", "]", ":" );

    protected final static List< String > OPERATORS = List.of( "&", "?", ":<<", ":>>", "=", ">", "<", "<=", "+", "*" );

    protected final static List< String > BOOLEAN = List.of( "true", "false" );

    protected final static String INTEGER = "^\\d+$";

    protected final static String DECIMAL = "^\\d+\\.\\d+$";

    protected final static String STRING = "^[a-zA-Z][a-zA-Z0-9{., _}]*$";

    protected final static List< String > TYPES = List.of( "int", "real", "string", "bool" );
}
