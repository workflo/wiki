package de.donuz.wiki

import grails.plugins.springsecurity.Secured


class SpaceController
{
    def list() {
        
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
}
