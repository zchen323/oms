
oms.report={}; // oms report module

oms.report.openPRPanel=function(){
	// build grid panel
	var prstore=Ext.create('Ext.data.JsonStore', {
		// store configs
		fields: [
			{name: 'projID'}, 
			{name: 'projname'},
			{name: 'mgr'}, 
			{name: 'status' },
			{name: 'agency'},
			{name: 'Org'},
			{name: 'SubOrg'},
			{name: 'loc'},
			{name: 'office'},
			{name: 'category'},
			{name: 'throughPrime'},
			{name: 'prime'},
			{name: 'targetDT',type:'date'},
			{name: 'startDT',type:'date'},
			{name: 'completeDT',type:'date'}
			]
	});
	var prgrid=Ext.create('Ext.grid.Panel',{
		scrollable:true,
		store:prstore,
		columns: [
			{name: 'projname'},
			{name: 'mgr'}, 
			{name: 'status' },
			{name: 'agency'},
			{name: 'Org'},
			{name: 'SubOrg'},
			{name: 'loc'},
			{name: 'office'},
			{name: 'category'},
			{name: 'throughPrime'},
			{name: 'prime'},
			{name: 'targetDT'},
			{name: 'startDT'},
			{name: 'completeDT'}
		]
	});
	var mainpanel=Ext.create('Ext.panel.Panel',{
		layout:'hbox',
		title:"[PROJECT Report Panel]",
		padding:'5 5 5 5',
		border:true,
		items:[
			{xtype:'form',	
				width:'95%',
				frame:false,
				border:0,
				layout:'hbox',
				items:[
					{
						name:'prFromdate',
						fieldLabel:'From After:', 
						margin: '0 2 2 15',
						xtype:'datefield',
						labelCls:'omslabelstyle',
						allowBlank:false,
						fieldCls:'omsfieldstyle'
						},
						{
							name:'prTodate',
							fieldLabel:'To Before:', 
							margin: '0 2 2 15',
							xtype:'datefield',
							labelCls:'omslabelstyle',
							allowBlank:false,
							fieldCls:'omsfieldstyle'
							},	
				],
				buttons:[
					{
						text:"Load Report Data",
						handler: function(){
						}
						
					}]
			},
			]
	});
	Ext.getCmp('centerViewPort').add(mainpanel);
};