How to access grails console?
http://localhost:8080/console

How to print names of all beans?
```groovy
ctx.getBeansOfType(Object.class).collect{it.key}.sort().each{println it}
```

How to print all URL mappings?
```groovy
ctx.getBean('grailsUrlMappingsHolder').getUrlMappings()
```

How to get controller name that servers a URL?
```groovy
ctx.getBean('grailsUrlMappingsHolder').match('/oauth2/google/authenticate').getControllerName()
```

How to combine client and server projects into one?

Please see here https://guides.grails.org/angular2-combined/guide/index.html

