package com.unitri.comp;

public class Token {

    private String image;

    private CategorizationEnum categorizationEnum;

    private Long line;

    private Long column;

    private Long index;

    public String getImage() {
        return image;
    }

    public Token image( String image ) {

        this.image = image;
        return this;
    }

    public CategorizationEnum getCategoryEnum() {
        return categorizationEnum;
    }

    public Token categorizationEnum( CategorizationEnum categorizationEnum ) {

        this.categorizationEnum = categorizationEnum;
        return this;
    }

    public Long getLine() {
        return line;
    }

    public Token line( Long line ) {

        this.line = line;
        return this;
    }

    public Long getColumn() {
        return column;
    }

    public Token column( Long column ) {

        this.column = column;
        return this;
    }

    public Long getIndex() {
        return index;
    }

    public Token index( Long index ) {

        this.index = index;
        return this;
    }
}
