package de.donuz.wiki

class AttachmentController {
    def download = {
        Page pageInstance = Page.get(params.pageId)

        if (pageInstance != null) {
            final Attachment a = pageInstance.getAttachment(params.filename)
            if (a != null) {
                response.contentType = a.mimeType
                response.addIntHeader('Content-Length', a.content.length)
                response.outputStream << a.content
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
