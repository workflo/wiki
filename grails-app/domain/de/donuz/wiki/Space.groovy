package de.donuz.wiki

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
}
