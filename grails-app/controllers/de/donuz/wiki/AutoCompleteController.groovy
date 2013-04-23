package de.donuz.wiki

import grails.converters.JSON

class AutoCompleteController {
    def pageByTitle() {
        String query = '%' + params.remove('term') + '%';
        List pages = Page.findAll("FROM Page WHERE state=? AND LOWER(title) LIKE LOWER(?)", [PageState.Public, query]).collect() {
            return [
                id: it.id,
                label: it.title,
                value: it.id
            ]
        }
        render pages as JSON
    }
}
