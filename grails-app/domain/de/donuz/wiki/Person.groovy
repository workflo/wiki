package de.donuz.wiki

class Person {

    transient springSecurityService

    String username
    String password
    String email
    String fullname
    
    boolean enabled
    boolean accountExpired
    boolean accountLocked
    boolean passwordExpired

    static constraints = {
        username blank: false, unique: true, matches:/[a-zA-Z0-9_.@-]+/
        password blank: false
        email nullable: true, email: true
        fullname nullable: true
    }

    static mapping = { password column: '`password`' }

    Set<Authority> getAuthorities() {
        PersonAuthority.findAllByPerson(this).collect { it.authority } as Set
    }

    def beforeInsert() {
        encodePassword()
    }

    def beforeUpdate() {
        if (isDirty('password')) {
            encodePassword()
        }
    }

    protected void encodePassword() {
        password = springSecurityService.encodePassword(password)
    }
}
