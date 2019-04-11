<#assign jsf='#{'/>
<?xml version='1.0' encoding='UTF-8' ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:h="http://xmlns.jcp.org/jsf/html"
      xmlns:p="http://primefaces.org/ui"
      xmlns:f="http://xmlns.jcp.org/jsf/core"
      xmlns:form="http://xmlns.jcp.org/jsf/composite/form/${entityDirectory}">
    <h:head>
        <title></title>
    </h:head>
    <h:body>
        <h:form id="${entity.className}Table">
        <p:messages/>  

            <p:commandLink value="New"   actionListener="${jsf}${mb}.new${entity.className}()}" update=":${entity.className}Dialog"   oncomplete="PF('${entity.className}Dialog').show()"/>

            <p:dataTable value="${jsf}${mb}}" var="a"
                        paginator="true"
                         rows="5"
                         paginatorTemplate="{RowsPerPageDropdown} {FirstPageLink} {PreviousPageLink} {CurrentPageReport} {NextPageLink} {LastPageLink}"
                         rowsPerPageTemplate="5,10,15"
                         lazy="true"
 

            >
                <#list entity.cols as col>
                     <#if col.isPreview() = true>   
                      <#if col.relation = 'ManyToOne'>
                        <p:column headerText="${col.name}"><p:outputLabel value="${jsf}a.${col.name}${col.firstStringOfManyToOne}}"/></p:column>
                      <#else>
                          <p:column headerText="${col.name}"><p:outputLabel value="${jsf}a.${col.name}}"/></p:column>
                      </#if>            

                     
                     </#if>
                </#list>
                
                <p:column>
                    <p:commandButton icon="ui-icon-circle-close"  actionListener="${jsf}${entity.simpleName2}MB.remove(a)}"  update="@form"/> 
             
                    <p:commandButton icon="ui-icon-pencil"   update=":${entity.className}Dialog"    oncomplete="PF('${entity.className}Dialog').show()"  >
                        <f:setPropertyActionListener target="${jsf}${entity.simpleName2}MB.${entity.simpleName2}}" value="${jsf}a}"/>
                    </p:commandButton>

                </p:column>
            </p:dataTable>
        


        </h:form>


          <p:dialog id="${entity.className}Dialog"  widgetVar="${entity.className}Dialog" dynamic="true">
                <h:form>
                 <form:${entity.simpleName2} object = "${jsf}${mb}.${object}}"/>
                 <p:commandLink value="save" actionListener="${jsf}${entity.simpleName2}MB.save()}" process="@parent" update=":${entity.className}Table" oncomplete="PF('${entity.className}Dialog').hide()"/>       
                </h:form>                 
           </p:dialog>


         
    </h:body>
</html>

