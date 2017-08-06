
oms.report={}; // oms report module

oms.report.openPRPanel=function(){
	// build grid panel
	var prstore=Ext.create('Ext.data.JsonStore', {
		// store configs
		fields: [
			{name: 'projID'}, 
			{name: 'projName'},
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
		width:'90%',
		store:prstore,
		columns: [
			{text: "Name", dataIndex: 'projName'},
			{text: "Mgr", dataIndex: 'projManager'},
			{text: "status", dataIndex: 'projStatus'},
			{text: "Agency", dataIndex: 'projAgency'},
			{text: "Org", dataIndex: 'projOrg'},
			{text: "Sub Org", dataIndex: 'subOrg'},
			{text: "Loc", dataIndex: 'projloc'},
			{text: "office", dataIndex: 'contactoffice'},
			{text: "category", dataIndex: 'projcategory'},
			{text: "isPrimeProject", dataIndex: 'isPrimeProject'},
			{text: "primeName", dataIndex: 'primeName'},
			{text: "Due Date", dataIndex: 'projduedate'},
			{text: "Due Date", dataIndex: 'projduedate'},
			{name: 'completeDT'}
		]
	});
	var mainpanel=Ext.create('Ext.panel.Panel',{
		title:"[PROJECT Report Panel]",
		padding:'5 5 5 5',
		border:true,
		items:[
			{xtype:'form',	
				width:'95%',
				frame:false,
				layout:'hbox',
				border:0,
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
							}	
				]
			},
			prgrid
			]
	});
	// load default data
	Ext.Ajax.request({
		url : "api/report/projectSummary",
		method : 'GET',
		success : function(response, option) {
	
			var respObj = Ext.decode(response.responseText);
			Ext.Msg.alert(respObj.status, respObj.message);
			console.log(respObj.result);
			if (respObj.status === 'success') {
				prstore.setData(respObj.result);
				
			}
		},
		failure : function(response, option) {
			console.log(response);
			Ext.Msg.alert('Error', response.responseText);
		}
	});
	Ext.getCmp('centerViewPort').add(mainpanel);
};