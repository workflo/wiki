package de.donuz.wiki

import java.util.Collection;

class Space
{
    String name
    String title
    
    // Permissions:
    String readers
    String writers
    String admins

    static constraints = {
        name blank: false, unique: true, matches: /[a-zA-Z0-9_-]+/
        title blank: false
    }

    static mapping = {
        name index: "space_name_idx"
        readers type: "text"
        writers type: "text"
        admins type: "text"
    }
    
    
    static getVisibleSpaces()
    {
        Space.list(sort: 'title')    
    }
    
    
    static byName(String name)
    {
        Space.findByName(name)
    }

    Collection<Page> getTopPages()
    {
        Page.findAll("FROM Page WHERE state=? AND space=? ORDER BY title", [PageState.Public, this], [max: 20])
    }
}
