package de.donuz.wiki

class Version
{
    String title
    String body
    Date dateCreated
    Date lastUpdated
    Integer number
    Person author

    static mapping = {
        body type:"text"
        cache 'nonstrict-read-write'
        cache usage:'read-only'
    }

    def getLatestVersion() {
        Version.withCriteria(uniqueResult: true) {
            eq("current", this)
            order "number", "desc"
            maxResults 1
        }
    }
    
    static belongsTo = [page: Page]    
}
