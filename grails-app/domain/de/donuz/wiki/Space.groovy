package de.donuz.wiki

class Space
{
    String title
    
    // Permissions:
    String readers
    String writers
    String admins

    static constraints = {
        title blank:false
    }

    static mapping = {
        readers type:"text"
        writers type:"text"
        admins type:"text"
    }
}
