<%@ page import="de.donuz.wiki.Page"%>

<g:set var="elId">bodyField</g:set>

<div class="controls controls-row">
    <label class="span1" for="title"><g:message code="page.title.label" default="Title" /></label>
    <g:textField class="input-block-level ${isNew ? 'focus select' : ''}" name="title" pattern="${pageInstance.constraints.title.matches}" required=""
        value="${pageInstance?.title}" />
</div>

<div class="fieldcontain ${hasErrors(bean: pageInstance, field: 'body', 'error')} ">
    <div class="btn-toolbar">
        <div class="btn-group">
            <a class="btn" href="#" id="${elId}-btnH1" title="Überschrift Ebene 1">h1</a>
            <a class="btn" href="#" id="${elId}-btnH2" title="Überschrift Ebene 2">h2</a>
            <a class="btn" href="#" id="${elId}-btnH3" title="Überschrift Ebene 3">h3</a>
        </div>
        <div class="btn-group">
            <a class="btn" href="#" id="${elId}-btnBold" title="Fett"><i class="icon-bold"></i></a>
            <a class="btn" href="#" id="${elId}-btnItalic" title="Kursiv"><i class="icon-italic"></i></a> 
            <a class="btn" href="#" id="${elId}-btnQuote" title="Zitat">&gt;</a> 
            <a class="btn" href="#" id="${elId}-btnCode" title="Code">$</a> 
        </div>
        <div class="btn-group">
            <a class="btn" href="#" id="${elId}-btnList" title="Aufzählung"><i class="icon-list"></i></a> 
            <a class="btn" href="#" id="${elId}-btnNumberedList" title="Numerierung">1.</a> 
        </div>
        <div class="btn-group">
            <a class="btn" href="#" id="${elId}-btnLink" title="Link einfügen... '['"><i class="icon-globe"></i></a>
            <a class="btn" href="#" id="${elId}-btnImage" title="Bild einfügen..."><i class="icon-picture"></i></a>
        </div>
    </div>
    <g:textArea name="body" class="${isNew ? '' : 'focus'}" value="${pageInstance?.body}" style="width: 100%;" rows="30" id="${elId}" />
</div>
