package de.donuz.wiki

import grails.plugins.springsecurity.Secured
import org.springframework.dao.DataIntegrityViolationException

class PageController {
    
    def springSecurityService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [pageInstanceList: Page.list(params), pageInstanceTotal: Page.count()]
    }

    @Secured(['ROLE_USERS', 'IS_AUTHENTICATED_REMEMBERED'])
    def create() {
        def p = [title: 'Neue Seite',
            body: '',
            state: PageState.New,
            creator: currentUser()
        ]

        Page pageInstance = new Page(p)
        
        pageInstance.save(flush: true, failOnError: true)
        
        [pageInstance: pageInstance]
    }

//    @Secured(['ROLE_USERS', 'IS_AUTHENTICATED_REMEMBERED'])
//    def save() {
//        def p = [title: params.title,
//            body: params.body,
//            state: PageState.Public,
//            creator: currentUser()
//        ]
//
//        Page pageInstance = new Page(p)
//        
//        if (!pageInstance.save(flush: true)) {
//            render(view: "create", model: [pageInstance: pageInstance])
//            return
//        }
//        Version v = pageInstance.createVersion(currentUser())
//        pageInstance.save(flush: true)
//        
//        flash.message = message(code: 'default.created.message', args: [
//            message(code: 'page.label', default: 'Page'),
//            pageInstance.id
//        ])
//        redirect(action: "show", id: pageInstance.id)
//    }

    def show(Long id) {
        def pageInstance = Page.get(id)
        if (!pageInstance || !pageInstance.isVisible()) {
            flash.message = message(code: 'default.not.found.message', args: [
                message(code: 'page.label', default: 'Page'),
                id
            ])
            redirect(action: "list")
            return
        }

        [pageInstance: pageInstance]
    }

    @Secured(['ROLE_USERS', 'IS_AUTHENTICATED_REMEMBERED'])
    def edit(Long id) {
        def pageInstance = Page.get(id)
        if (!pageInstance) {
            flash.message = message(code: 'default.not.found.message', args: [
                message(code: 'page.label', default: 'Page'),
                id
            ])
            redirect(action: "list")
            return
        }

        [pageInstance: pageInstance]
    }

    @Secured(['ROLE_USERS', 'IS_AUTHENTICATED_REMEMBERED'])
    def update(Long id, Long version) {
        Page.lock(id)
        Page pageInstance = Page.get(id)
        if (!pageInstance) {
            flash.message = message(code: 'default.not.found.message', args: [
                message(code: 'page.label', default: 'Page'),
                id
            ])
            redirect(action: "list")
            return
        }

        // TODO: Richtige Konfliktaufloesung:
        // Titel nur uebernehmen, wenn wirklich geaendert
        // Attachments einzeln hinzufuegen (und damit bestehende ueberschreiben)
        // Body mergen
        
//        if (version != null) {
//            if (pageInstance.version > version) {
//                pageInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
//                        [
//                            message(code: 'page.label', default: 'Page')] as Object[],
//                        "Another user has updated this Page while you were editing")
//                render(view: "edit", model: [pageInstance: pageInstance])
//                return
//            }
//        }

        pageInstance.title = params.title
        pageInstance.body = params.body
        pageInstance.state = PageState.Public
        Version v = pageInstance.createVersion(currentUser())

        if (!pageInstance.save(flush: true)) {
            render(view: "edit", model: [pageInstance: pageInstance])
            return
        }
        
        flash.message = message(code: 'default.updated.message', args: [
            message(code: 'page.label', default: 'Page'),
            pageInstance.id
        ])
        redirect(action: "show", id: pageInstance.id)
    }

    @Secured(['ROLE_USERS', 'IS_AUTHENTICATED_REMEMBERED'])
    def delete(Long id) {
        def pageInstance = Page.get(id)
        if (!pageInstance) {
            flash.message = message(code: 'default.not.found.message', args: [
                message(code: 'page.label', default: 'Page'),
                id
            ])
            redirect(action: "list")
            return
        }

        try {
            pageInstance.moveToTrash()
            flash.message = message(code: 'default.deleted.message', args: [
                message(code: 'page.label', default: 'Page'),
                id
            ])
            redirect(uri: '/')
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [
                message(code: 'page.label', default: 'Page'),
                id
            ])
            redirect(action: "show", id: id)
        }
    }
    
    private currentUser()
    {
        Person.get(springSecurityService.principal.id)
    }
}
