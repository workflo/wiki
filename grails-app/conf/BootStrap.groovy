import de.donuz.wiki.*;

class BootStrap {

    def init = { servletContext ->
        (Authority.findByAuthority('ROLE_ADMIN') ?: new Authority(authority: 'ROLE_ADMIN')).save(flush: true)
        (Authority.findByAuthority('ROLE_USER') ?: new Authority(authority: 'ROLE_USER')).save(flush: true)
        
        
        if (Person.count() == 0) {
            def adminUser = new Person(username: 'admin', enabled: true, password: 'admin')
            adminUser.save(flush: true)

            PersonAuthority.create(adminUser, Authority.findByAuthority('ROLE_ADMIN'), true)
        }
    }
    
    def destroy = {
    }
}
