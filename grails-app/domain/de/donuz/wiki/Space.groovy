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
        name nullable: true, blank: true//, unique: true, matches: /[a-zA-Z0-9_-]+/
        title blank: false
    }

    static mapping = {
        readers type: "text"
        writers type: "text"
        admins type: "text"
    }
}
