<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="a4j"uri="https://ajax4jsf.dev.java.net/ajax"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib prefix="mf" uri="http://mapfaces.org/taglib"%>
<%@taglib prefix="mf-model" uri="http://mapfaces.org/taglib-models"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        </head>
        <body>                   
            <h:form id="form">               
                <mf-model:Context id="owsContext" service="data/context/owc030Cut.xml">                       
                    <mf:MapPane id="mappane"></mf:MapPane>
                    <mf:ButtonBar id="bar" styleClass="mfButtonBar horizontal"></mf:ButtonBar>
                </mf-model:Context>
            </h:form>
        </body>
    </html>
</f:view>