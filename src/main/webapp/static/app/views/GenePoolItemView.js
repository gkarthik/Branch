define([
  //Libraries
	'jquery',
	'marionette',
	'backbone',
	//Templates
	'text!static/app/templates/GenePoolItem.html',
	'jqueryui'
    ], function($, Marionette, Backbone, GenePoolItemTmpl) {
GenePoolItemView = Marionette.ItemView.extend({
	tagName: 'li',
	className: 'list-group-item gene-pool-item',
	events: {
	},
	ui: {
	},
	initialize : function() {
		this.listenTo(this.model,'change', this.render);
	},
	template: GenePoolItemTmpl,
	onRender: function(){
		this.$el.attr("data-shortname", this.model.get('short_name'));
		this.$el.attr("data-longname", this.model.get('long_name'));
		this.$el.attr("data-uniqueid", this.model.get('unique_id'));
		this.$el.draggable({
			revert: 'invalid',
			helper: "clone",
			opacity: 0.8
		});
	}
});

return GenePoolItemView;
});
