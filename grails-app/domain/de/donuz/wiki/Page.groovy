package de.donuz.wiki

import java.text.*

class Page
{
    def grailsApplication

    static final int BUFF_SIZE = 8192;

    String title
    String body
    Date dateCreated
    Date lastUpdated
    Person creator
    
    // FIXME: Permissions
    //String readers
    //String writers
    //String admins 
    
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
            if (a.name.equals(filename)) return a
        }
        return null
    }
    
    static Collection<Page> getTopLevelPages()
    {
        Page.findAllByParentIsNull([sort: 'title'])
    }


    Attachment createAttachment(String name, String contentType, InputStream inStream) 
    {
        final File dataDir = new File(grailsApplication.config.wiki.dataDir)
        final File attachmentDir = new File(dataDir, "attachments")
        final DateFormat FORMAT = new SimpleDateFormat("yyyy${File.separator}MM${File.separator}dd")
        String relFilename = FORMAT.format(new Date())
        final String uuid = UUID.randomUUID().toString()
        final File todaysDir = new File(attachmentDir, relFilename)
        final File file = new File(todaysDir, uuid)
        relFilename += "${File.separator}${uuid}"
        
        String mimeType = contentType?:''
        int pos = mimeType.indexOf(';')
        if (pos > -1) mimeType = mimeType.substring(0, pos).trim().toLowerCase()

        todaysDir.mkdirs()

        if (!todaysDir.isDirectory()) {
            throw new IOException("Attachment directory could not be created: ${todaysDir}")
        }

        // handle file upload
        final byte[] buffer = new byte[BUFF_SIZE];
        def outStream = new FileOutputStream(file)
        try {
            while (true) {
                int amountRead = inStream.read(buffer);
                if (amountRead == -1) {
                    break
                }
                outStream.write(buffer, 0, amountRead)
            }
        } finally {
            outStream.close()
        }
        
        final Attachment a = new Attachment(name: name, file: relFilename, title: "", mimeType: mimeType)
        addToAttachments(a)

        return a        
        // FIXME; Name muss eindeutig sein!
    }
}
