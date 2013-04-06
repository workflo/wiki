package de.donuz.wiki

class Page
{
    String title
    String body
    Date dateCreated
    Date lastUpdated
    Person creator
    
    static mapping = {
        body type:"text"
        cache 'nonstrict-read-write'
        title index: "title_idx"
    }

    def getLatestVersion() {
        Version.withCriteria(uniqueResult: true) {
            eq("current", this)
            order "number", "desc"
            maxResults 1
        }
    }
    
    
    static hasMany = [attachments: Attachment, versions: Version]//, children: Page]
        
    static belongsTo = [parent: Page]
    
    static searchable = {
        only = ['body', 'title']
        title boost: 2.0
    }

    static transients = ['cacheService', "latestVersion"]
    
    static constraints = {
        title(blank:false, matches:/[^\/\\]+/)
        body(blank:true)
    }

    String toString() {
        "Page #${id} (${title})"
    }
    
    Version createVersion(Person author) {
        def verObject = new Version(number: version, page: this, author: author)
        verObject.title = title
        verObject.body = body
        addToVersions(verObject)
        return verObject
    }
    
//    def getLatestVersion() {
//        Version.withCriteria(uniqueResult: true) {
//            eq("current", this)
//            order "number", "desc"
//            maxResults 1
//        }
//    }
    
    Attachment getAttachment(String filename)
    {
        for (final Attachment a : attachments) {
            if (a.filename.equals(filename)) return a
        }
        return null
    }
    
    static Collection<Page> getTopLevelPages()
    {
        Page.findAllByParentIsNull([sort: 'title'])
    }
}
