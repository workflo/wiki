package de.donuz.wiki

import javax.servlet.http.HttpServletResponse;

class AttachmentController {
    def download = {
        Page pageInstance = Page.get(params.pageId)

        if (pageInstance != null) {
            final Attachment a = pageInstance.getAttachment(params.filename)
            if (a) {
                response.contentType = a.mimeType
                final File file = a.getFileObject(params.size)
                
                if (file) {
                    // TODO: If-Modified-Since auswerten und ggf. 304 senden!
                    response.addHeader('Content-Length', Long.toString(file.length()))
                    response.addDateHeader('Last-Modified', file.lastModified())
                    response.outputStream << new FileInputStream(file)
                } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid thumbnail size: ${params.size}")
                }
            } else {
                response.sendError(404, "${params.filename}")
                return false
            }
        } else {
            response.sendError(404, "Page ${params.pageId}")
            return false
        }
    }
    
    
    def imageGallery = {
        Page pageInstance = Page.get(params.pageId)
                
        if (pageInstance != null) {
            [pageInstance: pageInstance, images: pageInstance.getImages()]
        } else {
            response.sendError(404, "Page ${params.pageId}")
            return false
        }
    }
}
