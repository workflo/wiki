import de.donuz.wiki.*;

class BootStrap {

    def init = { servletContext ->
        (Authority.findByAuthority('ROLE_ADMINISTRATORS') ?: new Authority(authority: 'ROLE_ADMINISTRATORS')).save(flush: true)
        (Authority.findByAuthority('ROLE_USERS') ?: new Authority(authority: 'ROLE_USERS')).save(flush: true)
        
        
        if (Person.count() == 0) {
            def adminUser = new Person(username: 'admin', enabled: true, password: 'admin')
            adminUser.save(flush: true)

            PersonAuthority.create(adminUser, Authority.findByAuthority('ROLE_ADMINISTRATORS'), true)
        }
    }
    
    def destroy = {
    }
}
