package de.donuz.wiki

import grails.plugins.springsecurity.Secured
import grails.converters.JSON
import hungry.wombat.*;

class WikiUploadController {
    def index = { render('') }

    def warning = {
        render(plugin: 'uploadr', template: '/upload/warning')
    }

    @Secured(['ROLE_USER', 'IS_AUTHENTICATED_REMEMBERED'])
    def handle = {
        def contentType = request.getHeader("Content-Type") as String
        def fileName = URLDecoder.decode(request.getHeader('X-File-Name'), 'UTF-8') as String
        def fileSize = request.getHeader('X-File-Size') as Long
        def name = URLDecoder.decode(request.getHeader('X-Uploadr-Name'), 'UTF-8') as String
        def info        = session.getAttribute('uploadr')
        
        Page.lock(params.page_id)
        Page pageInstance = Page.get(params.page_id)
        def prevVersion = pageInstance.version

        // set response content type to json
        response.contentType = 'application/json'

        if (pageInstance == null) {
            response.setStatus(500)
            render([written: false, fileName: fileName, message: "wiki page could not be found: ${params.page_id}"] as JSON)
            return false
        }

        // define input and output streams
        final InputStream inStream = request.getInputStream()

        // handle file upload
        try {
            final Attachment attachment = pageInstance.createAttachment(fileName, contentType, inStream)
            pageInstance.save(flush: true, failOnError: true)
        } catch (Exception ex) {
            log.error('Failed creating Attachment:', ex)
            response.setStatus(500)
            render([written: false, fileName: fileName, message: ex.toString()] as JSON)
            return false
        } finally {
            inStream.close()
        }

        // render json response
        response.setStatus(200)
        render([written: true, fileName: fileName, message: "'${fileName}' upload successful!",
            prevVersion: prevVersion, newVersion: pageInstance.version] as JSON)
    }
}