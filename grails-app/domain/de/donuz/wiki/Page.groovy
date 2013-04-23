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
    SortedSet attachments

    Page originalPage
    Integer state = PageState.New

    Space space
    
    static constraints = {
        title blank: false, matches: /[^\/\\]+/
        body blank: true
        originalPage blank: true
        state nullable: false
        space nullable: false
    }

    static mapping = {
        body type: "text"
        cache 'nonstrict-read-write'
        title index: "title_idx"
    }

    static belongsTo = [space: Space]
    
    static hasMany = [attachments: Attachment, versions: Version]

    static transients = ['cacheService', "latestVersion"]

    static searchable = {
        only = ['body', 'title']
        title boost: 2.0
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

    def getLatestVersion() {
        Version.withCriteria(uniqueResult: true) {
            eq("current", this)
            order "number", "desc"
            maxResults 1
        }
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

    List<Attachment> getImages()
    {
        final List<Attachment> list = new ArrayList<Attachment>(attachments.size())

        for (final Attachment a : attachments) {
            if (a.isImage()) list.add(a)
        }

        return list;
    }


    Attachment createAttachment(String name, String contentType, InputStream inStream)
    {
        final String origName = name
        final File dataDir = new File(grailsApplication.config.wiki.dataDir)
        final File attachmentDir = new File(dataDir, "attachments")
        final DateFormat FORMAT = new SimpleDateFormat("yyyy${File.separator}MM${File.separator}dd")
        String relFilename = FORMAT.format(new Date())
        final String uuid = UUID.randomUUID().toString()
        final File todaysDir = new File(attachmentDir, relFilename)
        final File file = new File(todaysDir, uuid)
        relFilename += "${File.separator}${uuid}"

        name = makeValidAttachmentName(name)

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
            long bytesWritten = 0;
            while (true) {
                int amountRead = inStream.read(buffer);

                if (amountRead == -1) {
                    break
                }
                outStream.write(buffer, 0, amountRead)
                bytesWritten += amountRead

                if (bytesWritten > grailsApplication.config.wiki.attachments.maxSize) {
                    file.delete()
                    throw new IOException("Upload too large")
                }
            }
        } finally {
            outStream.close()
        }

        Attachment a = getAttachment(name)
        if (a) {
            a.file = relFilename
            a.mimeType = mimeType
        } else {
            a = new Attachment(name: name, file: relFilename, title: origName, mimeType: mimeType)
            addToAttachments(a)
        }

        return a
    }


    static String makeValidAttachmentName(String orig)
    {
        orig.replaceAll("[^a-zA-Z0-9.-_+]", '_')
    }


    public void moveToTrash()
    {
        state = PageState.Deleted
        save(flush: true, failOnError: true)
    }


    public boolean isVisible()
    {
        return state == PageState.Public
    }


    def afterUpdate()
    {
        switch (state) {
            case PageState.Public:
                index()
                break
            default:
                unindex()
        }
    }


    def afterDelete()
    {
        unindex();
    }
}
