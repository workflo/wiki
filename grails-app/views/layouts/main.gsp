<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title><g:layoutTitle default="Grails" /></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" type="image/x-icon">
<link rel="apple-touch-icon" href="${resource(dir: 'images', file: 'apple-touch-icon.png')}">
<link rel="apple-touch-icon" sizes="114x114" href="${resource(dir: 'images', file: 'apple-touch-icon-retina.png')}">
<r:require modules="bootstrap" />
<r:require modules="modernizr" />
<g:layoutHead />
<r:layoutResources />
<jqui:resources />
<jqueryui:javascript />
<link rel="stylesheet" href="${resource(dir: 'css', file: 'main.css')}" type="text/css">
<link rel="stylesheet" href="${resource(dir: 'css', file: 'mobile.css')}" type="text/css">
<style>
body {
	padding-top: 60px;
	/* 60px to make the container go all the way to the bottom of the topbar */
}
</style>
</head>
<body>

    <div class="navbar navbar-inverse navbar-fixed-top">
        <div class="navbar-inner">
            <div class="container">
                <button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
                    <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span>
                </button>
                <a class="brand" href="${createLink(uri: '/')}">Wiki</a>
                <div class="nav-collapse collapse">
                    <ul class="nav">
                        <sec:ifLoggedIn>
                            <li><g:link controller="page" action="create" params="${[parent: pageInstance?.id]}">Neue Seite</g:link></li>
                        </sec:ifLoggedIn>
                        <sec:ifNotLoggedIn>
                            <li><g:link controller="Login">Login</g:link></li>
                        </sec:ifNotLoggedIn>
                        <sec:ifLoggedIn>
                            <li><g:link controller="Logout">Logout</g:link></li>
                        </sec:ifLoggedIn>
                    </ul>
                </div>

                <form class="navbar-search pull-right">
                    <input type="text" class="span2 search-query" placeholder="Suchen">
                </form>
            </div>
        </div>
    </div>

    <div class="container-fluid">
        <div class="row-fluid">
            <div class="span3">
                <g:render template="/layouts/doctree" />
            </div>
            <div class="span7">
                <g:layoutBody />
            </div>
        </div>
    </div>

    <div class="footer" role="contentinfo"></div>
    <g:javascript library="application" />
    <r:layoutResources />
</body>
</html>