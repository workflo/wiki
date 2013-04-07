<%@ page import="de.donuz.wiki.Page"%>

<div class="controls controls-row">
    <label class="span1" for="title"><g:message code="page.title.label" default="Title" /></label>
    <g:textField class="input-block-level ${isNew ? 'focus select' : ''}" name="title" pattern="${pageInstance.constraints.title.matches}" required=""
        value="${pageInstance?.title}" />
</div>

<div class="fieldcontain ${hasErrors(bean: pageInstance, field: 'body', 'error')} ">
    <div class="btn-toolbar">
        <div class="btn-group">
            <a class="btn" href="javascript:editPage.insertH1();" title="Überschrift Ebene 1">h1</a>
            <a class="btn" href="javascript:editPage.insertH2();" title="Überschrift Ebene 2">h2</a>
            <a class="btn" href="javascript:editPage.insertH3();" title="Überschrift Ebene 3">h3</a>
        </div>
        <div class="btn-group">
            <a class="btn" href="javascript:editPage.insertBold();" title="Fett"><i class="icon-bold"></i></a>
            <a class="btn" href="javascript:editPage.insertItalic();" title="Kursiv"><i class="icon-italic"></i></a> 
            <a class="btn" href="javascript:editPage.insertQuote();" title="Zitat">&gt;</a> 
        </div>
        <div class="btn-group">
            <a class="btn" href="javascript:editPage.insertList();" title="Aufzählung"><i class="icon-list"></i></a> 
            <a class="btn" href="javascript:editPage.insertNumberedList();" title="Numerierung">1.</a> 
        </div>
        <div class="btn-group">
            <a class="btn" href="#" title="Link einfügen... '['"><i class="icon-globe"></i></a>
            <a class="btn" href="#" title="Bild einfügen..."><i class="icon-picture"></i></a>
        </div>
    </div>
    <g:textArea name="body" class="${isNew ? '' : 'focus'}" value="${pageInstance?.body}" style="width: 100%;" rows="30" id="bodyField" />
</div>
