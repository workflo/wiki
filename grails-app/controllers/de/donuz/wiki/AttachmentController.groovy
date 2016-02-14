package de.donuz.wiki

import grails.plugin.springsecurity.annotation.Secured
import javax.servlet.http.HttpServletResponse
import grails.converters.JSON

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
    
    
    @Secured(['ROLE_USERS', 'IS_AUTHENTICATED_REMEMBERED'])
    def delete = {
        Page pageInstance = Page.get(params.pageId)

        if (pageInstance != null) {
            final Attachment a = pageInstance.getAttachment(params.filename)
            if (a) {
                pageInstance.removeFromAttachments(a)
                a.delete()
                pageInstance.save(flush: true, failOnError: true)
                render([deleted: true] as JSON)
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
