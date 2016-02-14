package de.donuz.wiki

import grails.plugin.springsecurity.annotation.Secured
import org.springframework.dao.DataIntegrityViolationException

@Secured(['ROLE_ADMINISTRATORS', 'IS_AUTHENTICATED_REMEMBERED'])
class AuthorityController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [authorityInstanceList: Authority.list(params), authorityInstanceTotal: Authority.count()]
    }

    def create() {
        [authorityInstance: new Authority(params)]
    }

    def save() {
        def authorityInstance = new Authority(params)
        if (!authorityInstance.save(flush: true)) {
            render(view: "create", model: [authorityInstance: authorityInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'authority.label', default: 'Authority'), authorityInstance.id])
        redirect(action: "list")
    }

    def show(Long id) {
        def authorityInstance = Authority.get(id)
        if (!authorityInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'authority.label', default: 'Authority'), id])
            redirect(action: "list")
            return
        }

        [authorityInstance: authorityInstance]
    }

    def delete(Long id) {
        def authorityInstance = Authority.get(id)
        if (!authorityInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'authority.label', default: 'Authority'), id])
            redirect(action: "list")
            return
        }

        try {
            authorityInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'authority.label', default: 'Authority'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'authority.label', default: 'Authority'), id])
            redirect(action: "show", id: id)
        }
    }
}
