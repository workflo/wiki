package de.donuz.wiki

import de.donuz.wiki.Page;
import de.donuz.wiki.PageState;


/**
 * Housekeeping job. 
 * Deletes {@link PageState#New} {@link Page}s that are older than 24h.
 */
class CleanupJob 
{
    static triggers = {
        simple repeatInterval: 1000 * 3600 * 5, startDelay: 1000 * 3600
    }

    def execute() {
        def limit = new Date() -1
        
        log.debug("Running CleanupJob: Deleting New pages created prior to ${limit}...")
        
        Page.findAll("FROM Page WHERE state=${PageState.New} AND dateCreated<?", [limit]).each {
            log.info("Deleting unpublished page ${it.id}")
            it.delete(flush: true)
        }
    }
}
