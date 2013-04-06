package de.donuz.wiki

class Authority {

    String authority

    static mapping = { cache true }

    static constraints = {
        authority blank: false, unique: true
    }
}
