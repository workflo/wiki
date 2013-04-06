class UrlMappings {

    static mappings = {
        "/a/$pageId/$filename" {
            controller = 'attachment'
            action = 'download'
        }
        "/p/$pageId/$filename" {
            controller = 'attachment'
            action = 'download'
        }
        "/p/$id" {
            controller = 'page'
            action = 'show'
        }

        "/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
	}

	"/"(view:"/index")
	"500"(view:'/error')
    }
}
