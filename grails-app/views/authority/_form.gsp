<%@ page import="de.donuz.wiki.Authority" %>



<div class="fieldcontain ${hasErrors(bean: authorityInstance, field: 'authority', 'error')} required">
	<label for="authority">
		<g:message code="authority.authority.label" default="Authority" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="authority" required="" value="${authorityInstance?.authority}"/>
</div>

