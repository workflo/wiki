
<div id="navbar-example" class="navbar navbar-static navbar-fixed-top">
    <div class="navbar-inner">
        <div class="container" style="width: auto;">
            <ul class="nav" role="navigation">
                <li><g:link uri="/"><strong>Wiki</strong></g:link></li>
                <sec:ifLoggedIn>
                    <li><g:link controller="page" action="create">Neue Seite</g:link></li>
                </sec:ifLoggedIn>
                <sec:ifAllGranted roles="ROLE_ADMIN">
                    <li class="dropdown"><a href="#" id="drop2" role="button" class="dropdown-toggle" data-toggle="dropdown">Administration<b
                            class="caret"></b></a>
                        <ul class="dropdown-menu" role="menu" aria-labelledby="drop2">
                            <li role="presentation"><g:link controller="person" role="menuitem">Benutzer</g:link></li>
                            <li role="presentation"><g:link controller="authority" role="menuitem">Gruppen</g:link></li>
                            <li role="presentation" class="divider"></li>
                            <li role="presentation"><g:link controller="search" role="menuitem">Suche</g:link></li>
                        </ul></li>
                </sec:ifAllGranted>
            </ul>
            <ul class="nav pull-right">

                <li>
                    <form class="navbar-search pull-right" action="${createLink(controller: 'searchable')}">
                        <input type="text" name="q" class="span2 search-query" placeholder="Suchen" id="quicksearch">
                    </form>
                </li>

                <sec:ifLoggedIn>
                    <li>
                        <p class="navbar-text">
                            Hallo, <a href="#" class="navbar-link"><sec:username /></a>
                        </p>
                    </li>
                </sec:ifLoggedIn>
                <!--
                <li id="fat-menu" class="dropdown"><a href="#" id="drop3" role="button" class="dropdown-toggle" data-toggle="dropdown">Dropdown 3 <b
                        class="caret"></b></a>
                    <ul class="dropdown-menu" role="menu" aria-labelledby="drop3">
                        <li role="presentation"><a role="menuitem" tabindex="-1" href="#">Action</a></li>
                        <li role="presentation"><a role="menuitem" tabindex="-1" href="#">Another action</a></li>
                        <li role="presentation"><a role="menuitem" tabindex="-1" href="#">Something else here</a></li>
                        <li role="presentation" class="divider"></li>
                        <li role="presentation"><a role="menuitem" tabindex="-1" href="#">Separated link</a></li>
                    </ul></li>
                    -->
                <sec:ifNotLoggedIn>
                    <li><g:link controller="Login">Login</g:link></li>
                </sec:ifNotLoggedIn>
                <sec:ifLoggedIn>
                    <li><g:link controller="Logout">Logout</g:link></li>
                </sec:ifLoggedIn>
            </ul>
        </div>
    </div>
</div>
