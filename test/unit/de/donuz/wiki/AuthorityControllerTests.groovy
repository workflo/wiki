package de.donuz.wiki



import org.junit.*
import grails.test.mixin.*

@TestFor(AuthorityController)
@Mock(Authority)
class AuthorityControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/authority/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.authorityInstanceList.size() == 0
        assert model.authorityInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.authorityInstance != null
    }

    void testSave() {
        controller.save()

        assert model.authorityInstance != null
        assert view == '/authority/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/authority/show/1'
        assert controller.flash.message != null
        assert Authority.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/authority/list'

        populateValidParams(params)
        def authority = new Authority(params)

        assert authority.save() != null

        params.id = authority.id

        def model = controller.show()

        assert model.authorityInstance == authority
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/authority/list'

        populateValidParams(params)
        def authority = new Authority(params)

        assert authority.save() != null

        params.id = authority.id

        def model = controller.edit()

        assert model.authorityInstance == authority
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/authority/list'

        response.reset()

        populateValidParams(params)
        def authority = new Authority(params)

        assert authority.save() != null

        // test invalid parameters in update
        params.id = authority.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/authority/edit"
        assert model.authorityInstance != null

        authority.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/authority/show/$authority.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        authority.clearErrors()

        populateValidParams(params)
        params.id = authority.id
        params.version = -1
        controller.update()

        assert view == "/authority/edit"
        assert model.authorityInstance != null
        assert model.authorityInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/authority/list'

        response.reset()

        populateValidParams(params)
        def authority = new Authority(params)

        assert authority.save() != null
        assert Authority.count() == 1

        params.id = authority.id

        controller.delete()

        assert Authority.count() == 0
        assert Authority.get(authority.id) == null
        assert response.redirectedUrl == '/authority/list'
    }
}
