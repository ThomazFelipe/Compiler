package com.unitri.comp;

public class Symbol {

    private String image;

    private IdentifierEnum type;

    public String getImage() {
        return image;
    }

    public Symbol image( String image ) {

        this.image = image;
        return this;
    }

    public IdentifierEnum getType() {
        return type;
    }

    public Symbol type( IdentifierEnum type ) {

        this.type = type;
        return this;
    }
}
