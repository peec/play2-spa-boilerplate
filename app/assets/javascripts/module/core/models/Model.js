/**
 * Abstract Model for all models.
 */
define(['backbone'], function(Backbone) {
	
	var Model = Backbone.Model.extend({
		idAttribute: 'id',
		parse: function(response){
			return response.result;
		}
	});
	
	return Model;
});