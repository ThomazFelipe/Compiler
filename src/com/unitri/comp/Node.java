package com.unitri.comp;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Node {

    private String type;

    private Token token;

    private Node dad;

    private List< Node > sons = new ArrayList<>();

    Node( String type ) {
        this.type = type;
    }

    Node( Token token ) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType( String type ) {
        this.type = type;
    }

    public Token getToken() {
        return token;
    }

    public void setToken( Token token ) {
        this.token = token;
    }

    public Node getDad() {
        return dad;
    }

    public void setDad( Node dad ) {
        this.dad = dad;
    }

    List< Node > getSons() {
        return sons;
    }

    public void setSons( List< Node > sons ) {
        this.sons = sons;
    }

    void addSon( Node son ) {
        sons.add( son );
        son.dad = this;
    }

    public Node getSon( int index ) {
        return sons.get( index );
    }

    @Override
    public String toString() {

        return Objects.nonNull( type ) ? type : token.getImage();
    }
}
