package com.unitri.comp;

class Token {

    private String image;

    private CategorizationEnum categorizationEnum;

    private Long line;

    private Long column;

    private Long index;

    String getImage() {
        return image;
    }

    Token image( String image ) {

        this.image = image;
        return this;
    }

    CategorizationEnum getCategoryEnum() {
        return categorizationEnum;
    }

    Token categorizationEnum( CategorizationEnum categorizationEnum ) {

        this.categorizationEnum = categorizationEnum;
        return this;
    }

    Long getLine() {
        return line;
    }

    Token line( Long line ) {

        this.line = line;
        return this;
    }

    Long getColumn() {
        return column;
    }

    Token column( Long column ) {

        this.column = column;
        return this;
    }

    Long getIndex() {
        return index;
    }

    Token index( Long index ) {

        this.index = index;
        return this;
    }
}
