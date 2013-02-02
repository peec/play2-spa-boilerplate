This repository is using best practices for creating Single Page Applications using Play! Framework! and quite a bit of frontend 
javascript libraries. 

It's built for big applications, requirejs is used in the whole application and provides a modular architecture for the frontend and 
simple API architecture and helper methods included.


## Included frontend libraries

- requirejs: for modular architecture and automagic optimized at production.
- jquery: DOM modifying and event handlers.
- underscore: Utility belt and required for "backbone"
- backbone: MVC system for javascript
- backbone.marionette: Write less, better achitecture.
- backbone.wreqr: event system to listen on events.
- backbone.babysitter
- handlebars: Templating system, templates are in separate html files.
- handlebars-requirejs: Precompile templates for optimization in production!
- json2: For compability for serializing JSON in javascript.
- bootstrap.js: To use Twitters bootstrap awesome features.



## Authentication

Authentication api is already included in the application. You won't need to create this yourself. Uses JSON API ofcourse. 
Just start the app and try login with "admin", "admin". See "vent.md" for more information about events to listen on.







