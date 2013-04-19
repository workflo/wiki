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
                        <li><g:link controller="page" action="create">Neue Seite</g:link></li>
                    </sec:ifLoggedIn>
                    <sec:ifNotLoggedIn>
                        <li><g:link controller="Login">Login</g:link></li>
                    </sec:ifNotLoggedIn>
                    <sec:ifLoggedIn>
                        <li><g:link controller="Logout">Logout</g:link></li>
                    </sec:ifLoggedIn>
                </ul>

                <form class="navbar-search pull-right" action="${createLink(controller: 'searchable')}">
                    <input type="text" name="q" class="span2 search-query" placeholder="Suchen" id="quicksearch">
                </form>

                <sec:ifLoggedIn>
                    <p class="navbar-text pull-right">
                        Hallo, <a href="#" class="navbar-link"><sec:username /></a>
                    </p>
                </sec:ifLoggedIn>
            </div>
        </div>
    </div>
</div>