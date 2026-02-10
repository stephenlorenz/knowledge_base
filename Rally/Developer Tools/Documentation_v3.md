# Rally with Mass General Brigham 
## Developer Tools - Version 2.1.0
<br/><br/>

### Overview

This document outlines how you may integrate into your web site selected information from version 2.1.0 of the Rally with Mass General Brigham web site. Integration is supported through [Web Components](https://www.webcomponents.org/introduction).

### Registration

To access the Rally Developer Tools, you must first register your project with Rally with Mass General Brigham and obtain an authorization code. The authorization code is required for the Rally Developer Tools to function. You can register at [https://rally.partners.org/developer/register](https://rally.partners.org/developer/register) to obtain your authorization code.

### Web Components

[Web Components](https://www.webcomponents.org/introduction) allow you to extend the standard HTML tag set with custom tags. These tags can be imported into web pages with minimal configuration. Web Components can exist in an isolated scope which will prevent the bleeding of styles and scripting between the components and your page. As with standard HTML tags, the styling and behavior of the components can be modified with attributes.

There are essentially three steps to including the Rally Projects web component on your page:

#### Integration Steps

1.  Include the Rally web component stylesheet (css)
    
```html
<link rel="stylesheet" href="https://rally.partners.org/webjars/rally-web-components/ResearchAllyWebComponents.css">
```
              
    
2.  Import the Rally web component JavaScript
    
```JavaScript
<script> function checkForES6() {
     "use strict";
    
            if (typeof Symbol == "undefined") return false;
            try {
                eval("class Foo {}");
                eval("var bar = (x) => x+1");
            } catch (e) { return false; }
    
            return true;
        }
    
        if (checkForES6()) {
            // Your JavaScript engine supports the ES6+ features required for Web Components
            const s = document.createElement('script');
            s.src = "https://rally.partners.org/webjars/rally-web-components/ResearchAllyWebComponentsES2017.js";
            document.head.appendChild(s);
        } else {
            // Your JavaScript engine does not support ES6+ features; reverting to ES5.
            const s = document.createElement('script');
            s.src = "https://rally.partners.org/webjars/rally-web-components/ResearchAllyWebComponentsES5.js";
            document.head.appendChild(s);
        } 
</script>
```
              
<br/><br/><br/><br/>
3.  Add the Rally Projects web component to your web page
    
```markup
<research-ally-projects locale=""
                        auth=""
                        concepts=""
                        keywords=""
></research-ally-projects>
```



### Specification

The Rally with Mass General Brigham Projects web component supports the attributes summarized in the table below. The attributes `auth` and either `concepts` or `keywords` are required.

"Keyword" searches include active projects that reference the keywords anywhere in the posting. Projects which include those keywords as tagged concepts are prioritized at the top of the results. "Concept" searches return active projects which are specifically tagged with those concepts in the Rally with Mass General Brigham database. The concepts are derived from the [MedlinePlus](https://medlineplus.gov/healthtopics.html) vocabulary. The Rally with Mass General Brigham Developer Tool web page will include a builder tool to allow for easy selection of concepts from the MedlinePlus vocabulary.

  
| Name |  Description | Required  | Default  | Example  |
|---|---|---|---|---|
| concepts | A comma-separated list of MedLinePlus concept codes.  |  true * |   | C0030193,C0030319 |   |
| keywords | A simple keyword search. | true *  |   |  diabetes |
| auth  | The authorization code obtained by registering with Rally. | true  |   | C8142BDF-E1FA-4687-BC32-12E5763574D7 |
| locale | The locale to display labels and content. | false | en | es |

\* Either the `concepts` or `keywords` attributes are required, but not both.

### Example

The following example displays searches for Rally projects which reference the keyword `diabetes`:

```markup
<research-ally-projects locale="en"
                        auth="C8142BDF-E1FA-4687-BC32-12E5763574D7"
                        concepts=""
                        keywords="diabetes"
></research-ally-projects>
```

If there were one active project that included the keyword `diabetes`, the output could look like this:

![[Screen Shot 2021-08-26 at 07.12.27.png]]

<br/><br/><br/><br/><br/><br/>

A similar example, using the MedlinePlus concept code for diabetes, would look like this:


```markup
<research-ally-projects locale="en"
                        auth="C8142BDF-E1FA-4687-BC32-12E5763574D7"
                        concepts="C0011849"
                        keywords=""
></research-ally-projects>
```

![[Screen Shot 2021-08-26 at 07.12.27.png]]


<div class="divFooter">© 2021 Mass General Brigham Incorporated. All Rights Reserved.</div>