
<div class="well sidebar-nav">
    <ul class="nav nav-list" id="spacesList">
        <g:render template="/layouts/spacesList" />
        <sec:ifAllGranted roles="ROLE_ADMINISTRATORS">
            <button class="btn btn-small" id="spacesList-addSpace-btn">
                <i class="icon-plus-sign"></i>
            </button>
        </sec:ifAllGranted>
    </ul>
</div>