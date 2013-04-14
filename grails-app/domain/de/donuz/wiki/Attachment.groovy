package de.donuz.wiki

import java.sql.Blob
import java.text.*;

class Attachment {
    def grailsApplication
    
    
    /** Filename of uploaded file. Unique within a Page. */
    String name

    /** Filename where the actual blob is stored relative to data directory. */
    String file

    /** Optional title. */
    String title

    /** Mime-Type */
    String mimeType

    static constraints = {
        name(blank:false)
        file(blank:false)
        title(blank: true)
        mimeType(blank: true)
    }

    static mapping = {
    }

    static belongsTo = [page: Page]
    
    
    File getFileObject()
    {        
        final File dataDir = new File(grailsApplication.config.wiki.dataDir)
        final File attachmentDir = new File(dataDir, "attachments")
        return new File(attachmentDir, file)
    }
}
