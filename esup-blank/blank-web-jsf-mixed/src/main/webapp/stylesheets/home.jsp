<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://myfaces.apache.org/trinidad" prefix="tr"%>

<f:view>
    <tr:document>
        <tr:form id="blank_switch_desktop" rendered="#{not agentUtil.mobile}">
            <tr:commandButton text="desktop" action="desktopWelcome" immediate="true"
                id="blank_welcome_desktop" inlineStyle="display:none;" />
        </tr:form>
        <tr:form id="blank__switch_mobile" rendered="#{agentUtil.mobile}">
            <tr:commandButton text="mobile" action="mobileWelcome" immediate="true"
                id="blank_welcome_mobile" inlineStyle="display:none;" />
        </tr:form>
 
        <f:facet name="meta">
            <script type="text/javascript">
                var buttons = new Array();
                buttons = document.getElementsByTagName('button');                
                for (i = 0; i < buttons.length; i++) {
                    var name = buttons.item(i).id; 
                    if (name.indexOf('blank_welcome_desktop') != -1) {
                        buttons.item(i).click();;
                    } else {
                        if(name.indexOf('blank_welcome_mobile') != -1) {
                            buttons.item(i).click();
                        }
                    }
                }   
                inputs = document.getElementsByTagName('input');                
                for (i = 0; i < inputs.length; i++) {
                    name = inputs.item(i).id; 
                    if (name.indexOf('blank_welcome_desktop') != -1) {
                        inputs.item(i).click();;
                    } else {
                        if(name.indexOf('blank_welcome_mobile') != -1) {
                            inputs.item(i).click();
                        }
                    }
                }                                
            </script>
        </f:facet>

    </tr:document>
</f:view>
