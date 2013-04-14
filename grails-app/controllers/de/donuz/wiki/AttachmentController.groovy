package de.donuz.wiki

class AttachmentController {
    def download = {
        Page pageInstance = Page.get(params.pageId)

        if (pageInstance != null) {
            final Attachment a = pageInstance.getAttachment(params.filename)
            if (a) {
                response.contentType = a.mimeType
                final File file = a.getFileObject()
                
                // TODO: If-Modified-Since auswerten und ggf. 304 senden!
                response.addHeader('Content-Length', Long.toString(file.length()))
                response.addDateHeader('Last-Modified', file.lastModified())
                response.outputStream << new FileInputStream(file)
            } else {
                response.sendError(404, "${params.filename}")
                return false
            }
        } else {
            response.sendError(404, "Page ${params.pageId}")
            return false
        }
    }
}
