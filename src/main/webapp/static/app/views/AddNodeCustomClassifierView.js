define([
  //Libraries
	'jquery',
	'marionette',
	'app/views/SearchFeatures',
	//Plugins
	'myGeneAutocomplete',
	'jqueryui'
    ], function($, Marionette, searchFeature) {
	AddNodeCustomClassifier = searchFeature.extend({
	selectTree: function(event, ui){},
	selectCustomCLassifier: function(event, ui){},
	selectCustomFeature: function(event, ui){},
	selectCf: function(event, ui) {
		console.log(ui.item);
		if(ui.item.short_name != undefined){//To ensure "no gene name has been selected" is not accepted.
				$("#SpeechBubble").remove();
				Cure.appLayout.AggNodeRegion.currentView.newGeneCollection.add([{
					unique_id: ui.item.unique_id,
					short_name: ui.item.short_name.replace(/_/g," "),
					long_name: ui.item.description
				}]);
				$(this.ui.cf_query).val("");
			}
	},
	selectGene: function(event, ui){
		if(ui.item.name != undefined){//To ensure "no gene name has been selected" is not accepted.
			$("#SpeechBubble").remove();
			Cure.appLayout.AggNodeRegion.currentView.newGeneCollection.add([{
				unique_id: ui.item.entrezgene,
				short_name: ui.item.symbol,
				long_name: ui.item.label
			}]);
			$(this.ui.gene_query).val("");
		}
	}
});
return AddNodeCustomClassifier;
});


