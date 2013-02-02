
## User Authenication

There are events assigned to the global `vent` object registered to user authentication and logout procedures.


### auth:update

This vent is triggered once logout or login was successful.


### auth:login

Triggered on successful login


### auth:logout 

Triggered on successful logout.


### auth:expired

auth:expired is triggered if session has exipiered on server and returning a unauthorized response in the API.
userSession is cleared and auth:logout + auth:update is triggered aswell.

