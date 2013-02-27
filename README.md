Play 2 - Single Page Application - boilerplate
=====================



Play 2.1+ boilerplate for Single-page application websites. 

Good boilerplate with minimal code to start a SPA.



### Integrates the following JS libraries: 

 - *requirejs:* For modularity
 - *underscore:* Requirement for backbone and awesome language tools + templating system. Precompiled templates in production thanks to underscore-tpl, requirejs r.js and play!
 - *backbone:* Awesome MV* framework for javascript
 - *marionette:* To keep architechture and reduce boilerplate code.
 - *jquery:* To maniplate the DOM.


### Starting in development (with amazon s3 upload functionality)

Signup on Amazon S3 for storage first. Then get your access.key and secret.key.

This is how you start the app in development.

	play \
	-Daws.access.key="XXXXXXX" \
	-Daws.secret.key="XXXXXXXXXXX+xxxxxxxxxxxxxxxxxxxxxxxxxx" \
	run


### Starting in production (standalone server)

First install postgresql and create a database.
Here is a simple example of how to start in production mode. Assets are compiled, all css to one file, minified. All javascript files and templates are precompiled to a single javascript file! Awesome!

	play \
	-Daws.access.key="XXXXXXX" \
	-Daws.secret.key="XXXXXXXXXXX+xxxxxxxxxxxxxxxxxxxxxxxxxx" \
	-Ddb.default.url="jdbc:postgresql://localhost:5432/myapp" \
	-Ddb.default.user="postgres" \
	-Ddb.default.password="my_password" \
	-Dconfig.resource="production.conf" \
	start


### Deploy on heroku.

Setup heroku configurations for AWS_ACCESS_KEY and AWS_SECRET_KEY. Publish it to heroku!


### Comes with some functionality.

 - Login system.
 - Uploading to Amazon S3, both backend signing + frontend library.
 
### Ready for HEROKU

Files needed for heroku is already added, just push the code to heroku and set some config variables and youre ready!

### Bootstrap CSS

Bootstrap less files is also added as less files, so that variables and mixins can be used in custom stylesheets.



