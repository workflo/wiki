<html>
<head>
<meta name='layout' content='main' />
<title><g:message code="springSecurity.login.title" /></title>
</head>
<body>
    
    
            <g:if test="${flash.message}">
                <div class="alert alert-error">
                    <h4 class="alert-heading">Fehler</h4>
                    ${flash.message}
                </div>
            </g:if>
            <g:form action='${postUrl}' method='POST' useToken="true" class='well form-horizontal'>
                <h1>Willkommen zum Wiki</h1>
                <br />
                <br />
                <div class="control-group ${hasErrors(bean: loginCmd, field: 'login', 'error')}">
                    <label class="control-label">Username</label>
                    <div class="controls">
                        <g:textField name="j_username" class="focus input-block-level" value="${loginCmd?.login}"></g:textField>
                    </div>
                </div>
                <div class="control-group ${hasErrors(bean: loginCmd, field: 'password', 'error')}">
                    <label class="control-label">Passwort</label>
                    <div class="controls">
                        <g:passwordField name="j_password" class="input-block-level"></g:passwordField>
                    </div>
                </div>
                <div class="control-group">
                    <div class="controls">
                        <label class="checkbox"><input type='checkbox' class='chk' name='${rememberMeParameter}' id='remember_me'
                            <g:if test='${hasCookie}'>checked='checked'</g:if> /> Angemeldet bleiben</label>
                    </div>
                </div>

                <div class="form-actions">
                    <button class="btn btn-primary" type="submit">Login</button>
                </div>
            </g:form>
</body>
</html>
