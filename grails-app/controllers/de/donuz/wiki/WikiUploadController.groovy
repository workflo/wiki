package de.donuz.wiki

import grails.converters.JSON
import hungry.wombat.*;

class WikiUploadController {
    static final int BUFF_SIZE = 100000;

    def index = { render('') }

    def warning = {
        render(plugin: 'uploadr', template: '/upload/warning')
    }

    def handle = {
        def contentType = request.getHeader("Content-Type") as String
        def fileName = URLDecoder.decode(request.getHeader('X-File-Name'), 'UTF-8') as String
        def fileSize = request.getHeader('X-File-Size') as Long
        def name = URLDecoder.decode(request.getHeader('X-Uploadr-Name'), 'UTF-8') as String
        def info        = session.getAttribute('uploadr')
        def savePath    = "data/uploads"
        def dir = new File(savePath)
        def file        = new File(savePath, UUID.randomUUID().toString())
        Page pageInstance = Page.get(params.page_id)
        int status = 0
        def statusText = ""

        // set response content type to json
        response.contentType = 'application/json'

        if (pageInstance == null) {
            response.setStatus(500)
            render([written: false, fileName: fileName, message: "wiki page could not be found: ${params.page_id}"] as JSON)
            return false
        }
        
        // does the path exist?
        if (!dir.exists()) {
            // attempt to create the path
            try {
                dir.mkdirs()
            } catch (Exception e) {
                response.setStatus(500)
                render([written: false, fileName: fileName, message: "could not create upload path ${savePath}"] as JSON)
                return false
            }
        }

        // do we have enough space available for this upload?
        def freeSpace = dir.getUsableSpace()
        if (fileSize > freeSpace) {
            // not enough free space
            response.setStatus(500)
            render([written: false, fileName: fileName, message: "cannot store '${fileName}' (${fileSize} bytes), only ${freeSpace} bytes of free space left on device"] as JSON)
            return false
        }

        // is the file writable?
        if (!dir.canWrite()) {
            // no, try to make it writable
            if (!dir.setWritable(true)) {
                response.setStatus(500)
                render([written: false, fileName: fileName, message: "'${savePath}' is not writable, and unable to change rights"] as JSON)
                return false
            }
        }

        // define input and output streams
        InputStream inStream = null
        OutputStream outStream = null

        // handle file upload
        final byte[] buffer = new byte[BUFF_SIZE];
        try {
            inStream = request.getInputStream()
            outStream = new ByteArrayOutputStream()

            while (true) {
                int amountRead = inStream.read(buffer);
                if (amountRead == -1) {
                    break
                }
                outStream.write(buffer, 0, amountRead)
            }

            Attachment attachment = new Attachment(filename: fileName, title: "", mimeType: contentType, page: pageInstance, content: outStream.toByteArray())
            pageInstance.addToAttachments(attachment)
            pageInstance.save(flush: true, failOnError: true)
        } catch (Exception e) {
            response.setStatus(500)
            render([written: false, fileName: fileName, message: e.toString()] as JSON)
            return false
        } finally {
            if (inStream != null) inStream.close()
        }

        // make sure the file was properly written
//        if (status == 200 && fileSize > file.size()) {
//            // whoops, looks like the transfer was aborted!
//            status = 500
//            statusText = "'${fileName}' transfer incomplete, received ${file.size()} of ${fileSize} bytes"
//        }

        // render json response
        response.setStatus(200)
        render([written: true, fileName: fileName, uuid: file.getName(), message: "'${fileName}' upload successful!"] as JSON)
    }
}