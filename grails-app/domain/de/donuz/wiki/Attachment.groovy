package de.donuz.wiki

import net.coobird.thumbnailator.Thumbnails

import java.text.*

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
    
    
    File getFileObject(String sizeName)
    {        
        final File dataDir = new File(grailsApplication.config.wiki.dataDir)
        final File attachmentDir = new File(dataDir, "attachments")
        final File origFile = new File(attachmentDir, file)
        
        if (isImage() && sizeName) {
            def size = grailsApplication.config.wiki.thumbnails.dimensions[sizeName]
            
            if (size) {
                final File thumbnailFile = new File(attachmentDir, file + '_' + sizeName)
                
                if (!thumbnailFile.isFile()) {
                    // TODO: In Service umwandeln!
                    final File tmpFile = File.createTempFile('tmp-', name)
                    Thumbnails.of(origFile)
                            .size(size.width, size.height)
                            .toFile(tmpFile);
                
                    tmpFile.renameTo(thumbnailFile)
                }
                
                return thumbnailFile
                
            } else {
                return null
            }            
        } else {
            return origFile
        }
    }
    
    
    boolean isImage()
    {
        mimeType.startsWith('image/')
    }
}
