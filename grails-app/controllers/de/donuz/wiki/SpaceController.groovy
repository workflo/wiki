package de.donuz.wiki

import grails.plugin.springsecurity.annotation.Secured
import grails.converters.JSON


class SpaceController
{
    def list() {
        render(template: 'list', model: [spaceInstances: Space.getVisibleSpaces()])
    }
    
    
    def homepage() {        
        def spaceInstance = Space.byName(params.name)
        if (!spaceInstance) {
            flash.message = "Space nicht gefunden: ${params.name?.encodeAsHTML()}" 
            
            redirect(uri: '/')
            return
        }

        [spaceInstance: spaceInstance]
    }
    
    
    @Secured(['ROLE_ADMINISTRATORS', 'IS_AUTHENTICATED_REMEMBERED'])
    def ajaxCreate() {
        def spaceInstance = new Space(name: params.name, title: params.title, readers: '', writers: '', admins: '')
        if (spaceInstance.save(flush: true)) {
            render([name: params.name, title: params.title, success: true] as JSON)
        } else {
            render([name: params.name, title: params.title, success: false, message: spaceInstance.errors] as JSON)
        }        
    }
}
