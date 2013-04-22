<%@ page import="de.donuz.wiki.Person"%>

<div class="control-group ${hasErrors(bean: personInstance, field: 'username', 'error')} ">
    <label class="control-label required" for="inputUsername">Username</label>
    <div class="controls">
        <g:textField name="username" id="inputUsername" value="${personInstance?.username}" type="text" placeholder="Username" required="" />
    </div>
</div>

<div class="control-group ${hasErrors(bean: personInstance, field: 'fullname', 'error')} ">
    <label class="control-label required" for="inputFullname">Fullname</label>
    <div class="controls">
        <g:textField name="fullname" id="inputFullname" value="${personInstance?.fullname}" type="text" placeholder="Fullname" />
    </div>
</div>

<div class="control-group ${hasErrors(bean: personInstance, field: 'email', 'error')} ">
    <label class="control-label required" for="inputEmail">Email</label>
    <div class="controls">
        <g:textField name="email" id="inputEmail" value="${personInstance?.email}" type="text" placeholder="Email" />
    </div>
</div>

<div class="control-group ${hasErrors(bean: personInstance, field: 'password', 'error')} ">
    <label class="control-label required" for="inputPassword">Password</label>
    <div class="controls">
        <g:textField name="password" id="inputPassword" value="${personInstance?.username}" type="text" placeholder="Password" />
    </div>
</div>

<div class="control-group">
    <div class="controls">
        <label class="checkbox" for="inputEnabled"> <g:checkBox name="enabled" id="inputEnabled" value="${personInstance?.enabled}" /> <g:message
                code="person.enabled.label" default="Enabled" />
        </label>
    </div>
</div>