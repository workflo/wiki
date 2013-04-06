package de.donuz.wiki

import java.sql.Blob

class Attachment
{
    String filename
    String title
    String mimeType
    byte[] content
    
    static constraints = {
        filename(blank:false)
        title(blank: true)
        content(blank: true)
        mimeType(blank: true)
        content(maxSize: 1024 * 1024 * 10)
    }
    
    static mapping = {
//        content type: 'blob'
    }
    
    static belongsTo = [page: Page]    
}
