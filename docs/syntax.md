## Endpoint [ENTRY]

* METHOD /PATH or ALIAS:
    - Definition: Endpoint entry, mandatory and unique.
    - When found duplicated entries, only the first occurrence will be processed.

#### examples:    
    YAML:
        GET /path/yaml:
            ...   
        Endpoint YAML:
            ...
         
    JSON:    
        {
            "POST /path/json" : {
                ... 
            },  
            "Endpoint JSON" : {
               ...
            }
        }

___  
#### Endpoint [PROPERTIES]

* **method:** _(Required)_
    > Defines endpoint method, will override previous definitions.  
    Supported HTTP Methods: 
    > * GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS, TRACE
    >#### examples:  
    >     YAML:  
    >       method: GET
    >       
    >     JSON:
    >       "method": "POST"

* **path:** _(Required)_
    > Defines endpoint path, will override previous definitions.  
    Proper syntax= prefixed with "/".
    >#### examples:  
    >     YAML:  
    >       path: /path/to/your/test
    >       
    >     JSON:
    >       "path": "/my/lyre/endpoint"

* **alias | name:** _(Optional)_
    > Endpoint identification.  
    Default value: 
    > * **method** and **path**
    >#### examples:  
    >     YAML:  
    >       alias: Endpoint A
    >       name: Endpoint B
    >       
    >     JSON:
    >       "alias": "Endpoint C"
    >       "name": "Endpoint D"
    
* **consumes:** _(Optional)_
    > Defines consumed Media Type.  
    Default value:
    >* */ *
    >#### examples:  
    >     YAML:  
    >       consumes: application/xml
    >       
    >     JSON:
    >       "consumes": "application/json"
    
* **data:** _(Optional)_
    > Defines the expected data.  
    Data must match with request's data to be accepted.
    >#### examples:  
    >     YAML:  
    >       consumes: '{"data":"123"}'
    >       
    >     JSON:
    >       "consumes": "{'data':'123'}"

___
#### Endpoint [RESPONSE]

* **response | responses**: _(Required)_
    - Definition: Endpoint response.
    #### examples:    
        YAML:
        
        response:
            ...
            
        responses:
            ...
         
         
        JSON:
        
        {
            "response" : {
                ... 
            },  
        
            "responses" : {
               ...
            }
        }

___
#### Endpoint [PROPERTY]

* **property | properties**: _(Optional)_
    - Definition: Endpoint properties.
    #### examples:    
        YAML:
        
        property:
            ...
            
        properties:
            ...
         
         
        JSON:
        
        {
            "property" : {
                ... 
            },  
        
            "properties" : {
               ...
            }
        }

* **idle | timeout:** _(Optional)_
    > Idle property allows to set delay (milliseconds) at endpoint response.  
    >#### examples:  
    >     YAML:  
    >       idle: 1000
    >       timeout: 1500
    >       
    >     JSON:
    >       "idle": 2000
    >       "timeout": 2500


// TODO
header:
        custom_key:
    response(s):
        200:
            header:
                custom_key:
            produces:
            data: 'as string'


GET /api/v{version}/search?data=1&q=2:
    data:
    method:
    consumes:
    idle: in milisseconds (timeout)
    response:
        status:
        header:
            varN:
        produces:
        data: $enpoint.response
    property:
        busy: returns http 429 (number of busy calls)
        forbidden: return http 403 (forbidden) (number of busy calls)
        broken: return http 500 (number of busy calls)
        custom:
            status:
            calls:
