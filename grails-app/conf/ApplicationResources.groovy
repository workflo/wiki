modules = {
    application {
        resource url: 'css/main.css'
        resource url: 'js/application.js'
    }
    
    editPage {
        resource url: 'js/editPage.js', disposition: 'head'
    }
    
    uploadr {
        resource url: 'js/jquery.tipTip.minified.js'
        resource url: 'js/jquery.uploadr.js'
        resource url: 'css/jquery.uploadr.css'
    }
}